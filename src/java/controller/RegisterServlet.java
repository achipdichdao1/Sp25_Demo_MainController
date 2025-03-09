package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.UserDao;
import model.User;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDao userDao;
    
    @Override
    public void init() {
        userDao = new UserDao();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("register.jsp").forward(request, response);
    }
    
@Override
protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    String username = request.getParameter("username");
    String email = request.getParameter("email");
    String country = request.getParameter("country");
    String role = request.getParameter("role"); // Đây sẽ nhận giá trị "user"
    String password = request.getParameter("password");
    
    boolean status = true; // Mặc định tài khoản đang hoạt động

    try {
        if (userDao.isUsernameExists(username)) {
            // Nếu username đã tồn tại, hiển thị thông báo lỗi
            request.setAttribute("errorMessage", "Username đã tồn tại, vui lòng chọn username khác.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
        } else {
            User newUser = new User(username, email, country, role, status, password);
            userDao.insertUser(newUser);
            response.sendRedirect("login.jsp");
        }
    } catch (SQLException e) {
        throw new ServletException(e);
    }
}

}
