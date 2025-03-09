package filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

@WebFilter("/*") // Áp dụng cho tất cả các URL
public class LoginFilter implements Filter {
    private static final Logger LOGGER = Logger.getLogger(LoginFilter.class.getName());

    // Danh sách các trang công khai không yêu cầu đăng nhập
    private static final Set<String> PUBLIC_PAGES = new HashSet<>(Arrays.asList(
        "/login",
        "/register",
        "/register.jsp",
        "/shop" // Thêm /shop để cho phép truy cập productListShop.jsp mà không cần đăng nhập
    ));

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        LOGGER.info("LoginFilter initialized.");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);

        String requestURI = httpRequest.getRequestURI();
        String contextPath = httpRequest.getContextPath();
        String path = requestURI.substring(contextPath.length());
        String action = httpRequest.getParameter("action");

        LOGGER.info("Filtering request for: " + requestURI);

        // Cho phép truy cập các trang công khai
        if (PUBLIC_PAGES.contains(path)) {
            LOGGER.info("Public page, skipping login check: " + path);
            chain.doFilter(request, response);
            return;
        }

        // Nếu người dùng chưa đăng nhập
        if (session == null || session.getAttribute("user") == null) {
            // Nếu yêu cầu là xem chi tiết sản phẩm, chuyển hướng đến trang đăng nhập
            if (path.startsWith("/products") && "viewProduct".equals(action)) {
                LOGGER.warning("User not logged in, redirecting to login page from: " + requestURI);
                httpResponse.sendRedirect(contextPath + "/login");
            } else {
                // Các trang khác chuyển hướng về /shop
                LOGGER.warning("User not logged in, redirecting to shop page from: " + requestURI);
                httpResponse.sendRedirect(contextPath + "/shop");
            }
            return;
        }

        // Nếu đã đăng nhập, tiếp tục xử lý yêu cầu
        LOGGER.info("User authenticated, proceeding with request: " + requestURI);
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        LOGGER.info("LoginFilter destroyed.");
    }
}