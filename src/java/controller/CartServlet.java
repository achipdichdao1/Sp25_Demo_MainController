package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import model.Product;
import service.CartService;
import service.ProductDao;

import java.io.IOException;
import java.util.logging.Logger;

@WebServlet(name = "CartServlet", urlPatterns = "/cart")
public class CartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CartService cartService;
    private static final Logger LOGGER = Logger.getLogger(CartServlet.class.getName());

    @Override
    public void init() throws ServletException {
        cartService = new CartService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }

        LOGGER.info("CartServlet: Processing action - " + action);

        try {
            switch (action) {
                case "add":
                    addProductToCart(request, response);
                    break;
                case "remove":
                    removeProductFromCart(request, response);
                    break;
                case "clear":
                    clearCart(request, response);
                    break;
                case "checkout":
                    processCheckout(request, response);
                    break;
                case "show":
                default:
                    showCart(request, response);
                    break;
            }
        } catch (Exception ex) {
            LOGGER.severe("Error in CartServlet: " + ex.getMessage());
            request.setAttribute("errorMessage", "Đã xảy ra lỗi: " + ex.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    private void processCheckout(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        LOGGER.info("Processing checkout");

        if (session == null || session.getAttribute("user") == null) {
            LOGGER.warning("User not logged in, redirecting to login.");
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        CartService cart = (CartService) session.getAttribute("cart");
        if (cart == null || cart.getItems().isEmpty()) {
            LOGGER.info("Cart is empty or null.");
            request.setAttribute("message", "Giỏ hàng của bạn đang trống!");
            request.getRequestDispatcher("cart/cart.jsp").forward(request, response);
            return;
        }

        // Chuyển hướng tới trang xác nhận thanh toán
        request.setAttribute("cart", cart);
        request.getRequestDispatcher("/checkout/checkout.jsp").forward(request, response);
    }

    // Các phương thức khác giữ nguyên
    private void addProductToCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int productId = Integer.parseInt(request.getParameter("productId"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        HttpSession session = request.getSession();
        CartService cart = (CartService) session.getAttribute("cart");
        if (cart == null) {
            cart = new CartService();
            session.setAttribute("cart", cart);
        }
        Product product = new ProductDao().selectProduct(productId);
        cart.addProduct(product, quantity);
        String referer = request.getHeader("Referer");
        response.sendRedirect(referer != null ? referer : "shop");
    }

    private void removeProductFromCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int productId = Integer.parseInt(request.getParameter("productId"));
        HttpSession session = request.getSession();
        CartService cart = (CartService) session.getAttribute("cart");
        if (cart != null) {
            cart.removeProduct(productId);
        }
        response.sendRedirect("cart?action=show");
    }

    private void clearCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.removeAttribute("cart");
        response.sendRedirect("cart?action=show");
    }

    private void showCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        CartService cart = (CartService) session.getAttribute("cart");
        if (cart == null) {
            cart = new CartService();
            session.setAttribute("cart", cart);
        }
        request.setAttribute("cart", cart);
        request.getRequestDispatcher("cart/cart.jsp").forward(request, response);
    }
}