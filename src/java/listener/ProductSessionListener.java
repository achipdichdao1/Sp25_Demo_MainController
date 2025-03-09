package listener;

import jakarta.servlet.ServletRequestEvent;
import jakarta.servlet.ServletRequestListener;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpServletRequest;

@WebListener
public class ProductSessionListener implements ServletRequestListener {

    private static int activeSessions = 0;

    public static int getActiveSessions() {
        return activeSessions;
    }

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        HttpServletRequest request = (HttpServletRequest) sre.getServletRequest();
        String requestURI = request.getRequestURI();

        // Kiểm tra xem request có phải là để xem chi tiết sản phẩm không
        if (requestURI.endsWith("/products")) {
            activeSessions++;
            System.out.println("Active session incremented. Total active sessions: " + activeSessions);
            sre.getServletContext().setAttribute("activeSessions", activeSessions); // Lưu vào application scope
        }
    }

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        // Không cần làm gì khi request kết thúc
    }
}