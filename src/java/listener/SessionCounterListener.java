package listener;

import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import java.util.concurrent.atomic.AtomicInteger;

@WebListener
public class SessionCounterListener implements HttpSessionListener {

    // Sử dụng AtomicInteger để đảm bảo thread-safe khi đếm số phiên
    private static final AtomicInteger activeSessions = new AtomicInteger(0);

    /**
     * Trả về số lượng phiên hoạt động hiện tại
     * @return số phiên hoạt động
     */
    public static int getActiveSessions() {
        return activeSessions.get();
    }

    /**
     * Được gọi khi một session mới được tạo
     * @param se Sự kiện session
     */
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        // Tăng số phiên hoạt động
        int currentSessions = activeSessions.incrementAndGet();
        System.out.println("Session created. Total active sessions: " + currentSessions);
        
        // Lưu số phiên hoạt động vào application scope (ServletContext)
        se.getSession().getServletContext().setAttribute("activeSessions", currentSessions);
    }

    /**
     * Được gọi khi một session bị hủy
     * @param se Sự kiện session
     */
    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        // Giảm số phiên hoạt động, đảm bảo không âm
        int currentSessions = activeSessions.decrementAndGet();
        if (currentSessions < 0) {
            activeSessions.set(0); // Đặt lại về 0 nếu nhỏ hơn 0
            currentSessions = 0;
        }
        System.out.println("Session destroyed. Total active sessions: " + currentSessions);
        
        // Cập nhật lại số phiên hoạt động trong application scope
        se.getSession().getServletContext().setAttribute("activeSessions", currentSessions);
    }
}