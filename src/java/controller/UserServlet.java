package controller;

import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.UserDao;
import model.User;

@WebServlet(name = "UserServlet", urlPatterns = "/users")
public class UserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDao userDao;

    @Override
    public void init() throws ServletException {
        userDao = new UserDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) action = "";
        
        try {
            switch (action) {
                case "create":
                    showNewForm(request, response);
                    break;
                case "edit":
                    showEditForm(request, response);
                    break;
                case "delete":
                    deleteUser(request, response);
                    break;
                default:
                    listUsers(request, response);
                    break;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("errorMessage", "Đã xảy ra lỗi: " + ex.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);

        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) action = "";

        try {
            switch (action) {
                case "create":
                    insertUser(request, response);
                    break;
                case "edit":
                    updateUser(request, response);
                    break;
                default:
                    response.sendRedirect("users?action=list");
                    break;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            request.setAttribute("errorMessage", "Lỗi xử lý dữ liệu: " + ex.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

private void listUsers(HttpServletRequest request, HttpServletResponse response)
        throws SQLException, IOException, ServletException {
    UserDao userDao = new UserDao();
    List<User> userList = userDao.selectAllUsers();

    if (userList == null || userList.isEmpty()) {
        request.setAttribute("message", "Không có người dùng nào.");
        System.out.println("Không có người dùng nào trong database!");
    } else {
        request.setAttribute("listUser", userList);
        System.out.println("Truyền danh sách người dùng sang JSP, tổng số: " + userList.size());
    }

    RequestDispatcher dispatcher = request.getRequestDispatcher("/user/userList.jsp");
    dispatcher.forward(request, response);
}


    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("user/createUser.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        User existingUser = userDao.selectUser(id);

        if (existingUser == null) {
            response.sendRedirect("users?action=list");
            return;
        }

        request.setAttribute("user", existingUser);
        RequestDispatcher dispatcher = request.getRequestDispatcher("user/editUser.jsp");
        dispatcher.forward(request, response);
    }

    private void insertUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        User newUser = getUserFromRequest(request);
        userDao.insertUser(newUser);
        response.sendRedirect("users?action=list");
    }

    private void updateUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        User updatedUser = getUserFromRequest(request);
        updatedUser.setId(Integer.parseInt(request.getParameter("id"))); // Cập nhật ID
        userDao.updateUser(updatedUser);
        response.sendRedirect("users?action=list");
    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        userDao.deleteUser(id);
        response.sendRedirect("users?action=list");
    }

    private User getUserFromRequest(HttpServletRequest request) {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String country = request.getParameter("country");
        String role = request.getParameter("role");
        boolean status = request.getParameter("status") != null;
        String password = request.getParameter("password");
        return new User(name, email, country, role, status, password);
    }
}
