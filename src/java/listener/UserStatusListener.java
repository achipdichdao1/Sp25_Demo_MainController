package listener;

import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

@WebListener
public class UserStatusListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        // Khi một session mới được tạo, đánh dấu người dùng là "Đang hoạt động"
        se.getSession().setAttribute("status", "Active");
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        // Khi một session kết thúc, đánh dấu người dùng là "Đang không hoạt động"
        se.getSession().setAttribute("status", "Inactive");
    }
}