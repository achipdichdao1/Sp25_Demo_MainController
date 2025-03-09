package service;

import dao.DBConnection;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import model.Payment;

public class PaymentDAO {
    
    private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    
    // Tạo mới một thanh toán
    public String createPayment(int userId, BigDecimal amount, String paymentMethod, String description) {
        String sql = "INSERT INTO Payments (paymentCode, userId, amount, paymentMethod, paymentDate, status, description) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        // Tạo mã thanh toán duy nhất
        String paymentCode = "PAY-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        
        try {
            // Thay thế DBUtils bằng DBConnection của bạn
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, paymentCode);
            ps.setInt(2, userId);
            ps.setBigDecimal(3, amount);
            ps.setString(4, paymentMethod);
            ps.setTimestamp(5, new Timestamp(new Date().getTime()));
            ps.setString(6, "PENDING");
            ps.setString(7, description);
            
            int result = ps.executeUpdate();
            if (result > 0) {
                return paymentCode;
            }
        } catch (SQLException e) {
            System.out.println("Error creating payment: " + e.getMessage());
        } finally {
            closeResources();
        }
        return null;
    }
    
    // Cập nhật trạng thái thanh toán
    public boolean updatePaymentStatus(String paymentCode, String status) {
        String sql = "UPDATE Payments SET status = ? WHERE paymentCode = ?";
        
        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, status);
            ps.setString(2, paymentCode);
            
            int result = ps.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            System.out.println("Error updating payment status: " + e.getMessage());
        } finally {
            closeResources();
        }
        return false;
    }
    
    // Lấy thông tin thanh toán theo mã
    public Payment getPaymentByCode(String paymentCode) {
        String sql = "SELECT * FROM Payments WHERE paymentCode = ?";
        
        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, paymentCode);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                return new Payment(
                    rs.getInt("paymentId"),
                    rs.getString("paymentCode"),
                    rs.getInt("userId"),
                    rs.getBigDecimal("amount"),
                    rs.getString("paymentMethod"),
                    rs.getTimestamp("paymentDate"),
                    rs.getString("status"),
                    rs.getString("description")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error getting payment: " + e.getMessage());
        } finally {
            closeResources();
        }
        return null;
    }
    
    // Lấy danh sách thanh toán theo người dùng
    public List<Payment> getPaymentsByUserId(int userId) {
        List<Payment> payments = new ArrayList<>();
        String sql = "SELECT * FROM Payments WHERE userId = ? ORDER BY paymentDate DESC";
        
        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                payments.add(new Payment(
                    rs.getInt("paymentId"),
                    rs.getString("paymentCode"),
                    rs.getInt("userId"),
                    rs.getBigDecimal("amount"),
                    rs.getString("paymentMethod"),
                    rs.getTimestamp("paymentDate"),
                    rs.getString("status"),
                    rs.getString("description")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error getting payments: " + e.getMessage());
        } finally {
            closeResources();
        }
        return payments;
    }
    
    // Đóng tất cả tài nguyên
    private void closeResources() {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            System.out.println("Error closing resources: " + e.getMessage());
        }
    }
}