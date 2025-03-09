package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            // Giảm đếm lượt xem nếu lưu trong session (giả định)
            Integer viewCount = (Integer) session.getAttribute("productViewCount");
            if (viewCount != null) {
                session.setAttribute("productViewCount", 0); // Đặt lại về 0
            }
            // Xóa thông tin người dùng và hủy session
            session.removeAttribute("user");
            session.invalidate(); // Hủy session hoàn toàn
        }
        // Chuyển hướng về trang login
        response.sendRedirect(request.getContextPath() + "/shop");
    }
}