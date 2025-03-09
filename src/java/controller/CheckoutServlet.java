package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import model.CartItem;
import model.Product;
import service.CartService;
import service.ProductDao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ProductDao productDao;
    private static final Logger LOGGER = Logger.getLogger(CheckoutServlet.class.getName());

    @Override
    public void init() throws ServletException {
        productDao = new ProductDao();
        LOGGER.info("CheckoutServlet initialized.");
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
            // Xử lý thanh toán
            String note = processCheckout(cart);
            
            // Thanh toán thành công, xóa giỏ hàng
            session.removeAttribute("cart");
            LOGGER.info("Checkout successful, cart cleared for session: " + session.getId());
            request.setAttribute("note", note);
            request.getRequestDispatcher("checkout/success.jsp").forward(request, response);

        } catch (SQLException e) {
            handleError("Database error during checkout: " + e.getMessage(), e, request, response);
        } catch (Exception e) {
            handleError("Unexpected error during checkout: " + e.getMessage(), e, request, response);
        }
    }

    // Kiểm tra đăng nhập
    private boolean isUserLoggedIn(HttpSession session, HttpServletResponse response) throws IOException {
        if (session == null || session.getAttribute("user") == null) {
            LOGGER.log(Level.WARNING, "User not logged in, redirecting to login page.");
            response.sendRedirect(getServletContext().getContextPath() + "/login");
            return false;
        }
        return true;
    }

    // Kiểm tra giỏ hàng
    private boolean isCartValid(CartService cart, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (cart == null || cart.getItems().isEmpty()) {
            LOGGER.log(Level.INFO, "Cart is empty or null.");
            request.setAttribute("message", "Giỏ hàng của bạn đang trống!");
            request.getRequestDispatcher("cart/cart.jsp").forward(request, response);
            return false;
        }
        return true;
    }

    // Xử lý thanh toán và tạo note
    private String processCheckout(CartService cart) throws SQLException {
        List<CartItem> items = cart.getItems();
        StringBuilder note = new StringBuilder("Thanh toán thành công! Chi tiết:\n");

        for (CartItem item : items) {
            Product product = productDao.selectProduct(item.getProduct().getId());
            
            // Kiểm tra sản phẩm tồn tại
            if (product == null) {
                throw new IllegalStateException("Sản phẩm " + item.getProduct().getName() + " không tồn tại trong cơ sở dữ liệu!");
            }

            // Kiểm tra số lượng tồn kho
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
            LOGGER.log(Level.INFO, "Updated stock for product: " + product.getName() + ", new stock: " + newStock);

            // Thêm chi tiết vào note
            note.append("- ").append(product.getName())
                .append(": ").append(item.getQuantity())
                .append(" x ").append(product.getPrice())
                .append(" = ").append(item.getQuantity() * product.getPrice()).append("\n");
        }

        note.append("Tổng cộng: ").append(cart.getTotalPrice());
        return note.toString();
    }

    // Xử lý lỗi
    private void handleError(String message, Exception e, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LOGGER.log(Level.SEVERE, message, e);
        request.setAttribute("errorMessage", message.split(": ")[1]); // Chỉ lấy phần thông báo
        request.getRequestDispatcher("checkout/checkout.jsp").forward(request, response);
    }
}