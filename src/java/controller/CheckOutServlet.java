package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import model.CartItem;
import model.Product;
import model.User;
import service.CartService;
import service.OrderDAO;
import service.OrderDetailDAO;
import service.PaymentDAO;
import service.ProductDao;
import model.Order;
import model.OrderDetail;
import model.Payment;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "CheckOutServlet", urlPatterns = {"/checkout"})
public class CheckOutServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(CheckOutServlet.class.getName());
    private ProductDao productDao;

    @Override
    public void init() throws ServletException {
        productDao = new ProductDao();
        LOGGER.info("CheckOutServlet initialized.");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LOGGER.info("doGet called for checkout.");
        HttpSession session = request.getSession(false);

        // Kiểm tra đăng nhập
        if (!isUserLoggedIn(session, response)) {
            return;
        }

        // Kiểm tra giỏ hàng
        CartService cart = (CartService) session.getAttribute("cart");
        if (!isCartValid(cart, request, response)) {
            return;
        }

        // Chuyển hướng tới trang xác nhận thanh toán
        request.setAttribute("cart", cart);
        LOGGER.info("Forwarding to checkout.jsp.");
        request.getRequestDispatcher("checkout/checkout.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LOGGER.info("doPost called for checkout.");
        HttpSession session = request.getSession(false);

        // Kiểm tra đăng nhập
        if (!isUserLoggedIn(session, response)) {
            return;
        }

        // Kiểm tra giỏ hàng
        CartService cart = (CartService) session.getAttribute("cart");
        if (!isCartValid(cart, request, response)) {
            return;
        }

        try {
            // Lấy thông tin người dùng
            User user = (User) session.getAttribute("user");
            
            // Lấy thông tin thanh toán từ form
            String paymentMethod = request.getParameter("paymentMethod");
            String cardNumber = request.getParameter("cardNumber");
            String cardName = request.getParameter("cardName");
            String expDate = request.getParameter("expDate");
            String note = request.getParameter("note");
            String address = request.getParameter("address");
            
            // Tạo và lưu đơn hàng
            OrderDAO orderDAO = new OrderDAO();
            Order order = new Order();
            order.setUserId(user.getId());
            order.setTotalPrice(cart.getTotalPrice());
            order.setNote(note);
            order.setShippingAddress(address != null && !address.isEmpty() ? address : user.getAddress());
            int orderId = orderDAO.createOrder(order);
            
            if (orderId <= 0) {
                throw new Exception("Không thể tạo đơn hàng");
            }
            
            // Lưu chi tiết đơn hàng
            OrderDetailDAO orderDetailDAO = new OrderDetailDAO();
            List<CartItem> items = cart.getItems();
            
            for (CartItem item : items) {
                // Kiểm tra và cập nhật tồn kho sản phẩm
                Product product = productDao.selectProduct(item.getProduct().getId());
                
                if (product == null) {
                    throw new IllegalStateException("Sản phẩm " + item.getProduct().getName() + " không tồn tại trong cơ sở dữ liệu!");
                }

                int newStock = product.getStock() - item.getQuantity();
                if (newStock < 0) {
                    throw new IllegalStateException("Sản phẩm " + product.getName() + " không đủ hàng! Còn: " + product.getStock());
                }

                // Cập nhật sản phẩm
                product.setStock(newStock);
                if (newStock == 0) {
                    product.setStatus(false);
                    LOGGER.log(Level.INFO, "Product " + product.getName() + " is now out of stock.");
                }
                productDao.updateProduct(product);
                
                // Lưu chi tiết đơn hàng
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setOrderId(orderId);
                orderDetail.setProductId(item.getProduct().getId());
                orderDetail.setQuantity(item.getQuantity());
                orderDetail.setPrice(item.getProduct().getPrice());
                
                boolean saved = orderDetailDAO.saveOrderDetail(orderDetail);
                if (!saved) {
                    LOGGER.warning("Không thể lưu chi tiết đơn hàng cho sản phẩm: " + item.getProduct().getId());
                }
            }
            
            // Lưu thông tin thanh toán
            if (paymentMethod != null && !paymentMethod.isEmpty()) {
                PaymentDAO paymentDAO = new PaymentDAO();
                Payment payment = new Payment();
                payment.setOrderId(orderId);
                payment.setPaymentMethod(paymentMethod);
                payment.setCardNumber(cardNumber);
                payment.setCardName(cardName);
                payment.setExpDate(expDate);
                payment.setAmount(cart.getTotalPrice());
                payment.setStatus("Completed");
                
                boolean paymentSaved = paymentDAO.createPayment(payment);
                if (!paymentSaved) {
                    LOGGER.warning("Không thể lưu thông tin thanh toán");
                }
            }
            
            // Tạo ghi chú chi tiết đơn hàng
            StringBuilder orderNote = new StringBuilder("Thanh toán thành công! Chi tiết:\n");
            for (CartItem item : items) {
                orderNote.append("- ").append(item.getProduct().getName())
                    .append(": ").append(item.getQuantity())
                    .append(" x ").append(item.getProduct().getPrice())
                    .append(" = ").append(item.getQuantity() * item.getProduct().getPrice())
                    .append("\n");
            }
            orderNote.append("Tổng cộng: ").append(cart.getTotalPrice());
            
            // Thanh toán thành công, xóa giỏ hàng
            session.removeAttribute("cart");
            LOGGER.info("Thanh toán thành công, giỏ hàng đã được xóa cho phiên: " + session.getId());
            
            request.setAttribute("note", orderNote.toString());
            request.setAttribute("orderId", orderId);
            request.getRequestDispatcher("checkout/success.jsp").forward(request, response);

        } catch (SQLException e) {
            handleError("Lỗi cơ sở dữ liệu: " + e.getMessage(), e, request, response);
        } catch (Exception e) {
            handleError("Lỗi không mong muốn: " + e.getMessage(), e, request, response);
        }
    }

    // Kiểm tra đăng nhập
    private boolean isUserLoggedIn(HttpSession session, HttpServletResponse response) throws IOException {
        if (session == null || session.getAttribute("user") == null) {
            LOGGER.log(Level.WARNING, "Người dùng chưa đăng nhập, chuyển hướng đến trang đăng nhập.");
            response.sendRedirect(getServletContext().getContextPath() + "/login");
            return false;
        }
        return true;
    }

    // Kiểm tra giỏ hàng
    private boolean isCartValid(CartService cart, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (cart == null || cart.getItems().isEmpty()) {
            LOGGER.log(Level.INFO, "Giỏ hàng trống hoặc không tồn tại.");
            request.setAttribute("message", "Giỏ hàng của bạn đang trống!");
            request.getRequestDispatcher("cart/cart.jsp").forward(request, response);
            return false;
        }
        return true;
    }

    // Xử lý lỗi
    private void handleError(String message, Exception e, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LOGGER.log(Level.SEVERE, message, e);
        request.setAttribute("errorMessage", message.contains(":") ? message.split(": ")[1] : message);
        request.getRequestDispatcher("checkout/checkout.jsp").forward(request, response);
    }
}