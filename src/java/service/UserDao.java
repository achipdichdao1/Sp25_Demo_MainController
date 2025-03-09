/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.User;

/**
 *
 * @author PC
 */
public class UserDao implements IUserDAO {

    private static final String INSERT_USER = "INSERT INTO Users(username,email,country,roles,statuss,passwords) VALUES (?,?,?,?,?,?)";
    private static final String SELECT_USER_BY_ID = "SELECT * FROM Users where id=?";
    private static final String UPDATE_USER = "UPDATE Users SET username=?, email=?, country=?,roles=?,statuss=?,passwords=? WHERE id=?";
    private static final String SELECT_ALL_USERS = "SELECT * FROM Users";
    private static final String DELETE_USER = "DELETE FROM Users WHERE id=?";

    public User checkLogin(String username, String password) throws SQLException {
        User user = null;
        String sql = "SELECT * FROM Users WHERE username = ? AND passwords = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
             ps.setString(1, username);
             ps.setString(2, password);
             ResultSet rs = ps.executeQuery();
             if(rs.next()){
                user = new User(rs.getInt("id"),
                                rs.getString("username"),
                                rs.getString("email"),
                                rs.getString("country"),
                                rs.getString("roles"),
                                rs.getBoolean("statuss"),
                                rs.getString("passwords"));
             }
        } catch(SQLException e) {
             e.printStackTrace();
             throw e;
        }
        return user;
    }

    public boolean isUsernameExists(String username) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Users WHERE username = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
        return false;
    }

    @Override
    public void insertUser(User user) throws SQLException {
        try (Connection conn = DBConnection.getConnection()) {
            if (conn != null) {
                PreparedStatement ps = conn.prepareStatement(INSERT_USER);
                ps.setString(1, user.getUsername());
                ps.setString(2, user.getEmail());
                ps.setString(3, user.getCountry());
                // Fix here: Convert int role to String
                ps.setString(4, String.valueOf(user.getRole()));
                ps.setBoolean(5, user.isStatus());
                ps.setString(6, user.getPassword());

                int rowInserted = ps.executeUpdate();
                if (rowInserted > 0) {
                    System.out.println("A new user was inserted successfully!");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public User selectUser(int id) {
        User user = null;
        String sql = "SELECT * FROM Users WHERE id = ?";
        try (Connection conn = DBConnection.getConnection()) {
            if (conn != null) {
                PreparedStatement ps = conn.prepareStatement(SELECT_USER_BY_ID);
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    System.out.println("User found: ");
                    System.out.println("ID: " + rs.getInt("id"));
                    System.out.println("Username: " + rs.getString("username"));
                    System.out.println("Email: " + rs.getString("email"));
                    System.out.println("Country: " + rs.getString("country"));
                    System.out.println("Role: " + rs.getString("roles"));
                    System.out.println("Status: " + rs.getBoolean("statuss"));
                    System.out.println("Password: " + rs.getString("passwords"));
                    
                    // Tạo đối tượng User từ dữ liệu lấy được
                    user = new User(
                            rs.getInt("id"),
                            rs.getString("username"),
                            rs.getString("email"),
                            rs.getString("country"),
                            rs.getString("roles"),
                            rs.getBoolean("statuss"),
                            rs.getString("passwords")
                    );
                } else {
                    System.out.println("User not found");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public List<User> selectAllUsers() {
        List<User> users = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection()) {
            if (conn != null) {
                PreparedStatement ps = conn.prepareStatement(SELECT_ALL_USERS);
                System.out.println(ps);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("username");
                    String email = rs.getString("email");
                    String country = rs.getString("country");
                    String role = rs.getString("roles");
                    Boolean status = rs.getBoolean("statuss");
                    String password = rs.getString("passwords");

                    users.add(new User(id, name, email, country, role, status, password));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();// In lỗi ra console
        }
        // Kiểm tra danh sách có dữ liệu hay không
        System.out.println("Số lượng User lấy ra: " + users.size());
        return users;
    }

    @Override
    public boolean deleteUser(int id) throws SQLException {
        try (Connection conn = DBConnection.getConnection()) {
            if (conn != null) {
                PreparedStatement ps = conn.prepareStatement(DELETE_USER);
                ps.setInt(1, id);
                int rowsDeleted = ps.executeUpdate();
                return rowsDeleted > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateUser(User user) throws SQLException {
        boolean rowUpdated = false;
        try (Connection conn = DBConnection.getConnection()) {
            if (conn != null) {
                PreparedStatement ps = conn.prepareStatement(UPDATE_USER);
                ps.setString(1, user.getUsername());
                ps.setString(2, user.getEmail());
                ps.setString(3, user.getCountry());
                // Fix here: Convert int role to String
                ps.setString(4, String.valueOf(user.getRole()));
                ps.setBoolean(5, user.isStatus());
                ps.setString(6, user.getPassword());
                ps.setInt(7, user.getId());

                int rowsUpdated = ps.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("User updated successfully!");
                    rowUpdated = true;
                } else {
                    System.out.println("No user found with the given ID!");
                }
            }
        }
        return rowUpdated;
    }
}