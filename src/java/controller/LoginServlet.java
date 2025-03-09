package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;
import service.UserDao;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDao userDao;
    
    @Override
    public void init() {
        userDao = new UserDao();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Hiển thị trang đăng nhập
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }
    
@Override
protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    String username = request.getParameter("username");
    String password = request.getParameter("password");
    
    try {
        // Kiểm tra đăng nhập và lấy đối tượng User
        User user = userDao.checkLogin(username, password);
        if (user != null) {
            // Kiểm tra trạng thái tài khoản: true (1) là hoạt động, false (0) là hết hạn
            if (!user.isStatus()) {
                // Nếu tài khoản hết hạn (status == 0)
                request.setAttribute("errorMessage", "Tài khoản hết hạn.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            } else {
                // Tài khoản còn hiệu lực
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                // Kiểm tra role để chuyển hướng
                    response.sendRedirect(request.getContextPath() + "/shop");
                
            }
        } else {
            request.setAttribute("errorMessage", "Invalid username or password.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    } catch (SQLException e) {
        throw new ServletException(e);
    }
}
}
