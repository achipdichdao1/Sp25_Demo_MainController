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
    
    /**
     * Tạo một thanh toán mới
     * 
     * @param payment Đối tượng Payment chứa thông tin thanh toán
     * @return true nếu tạo thành công, false nếu thất bại
     */
    public boolean createPayment(Payment payment) {
        String paymentCode = "PAY-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        
        String sql = "INSERT INTO Payments (paymentCode, userId, orderId, amount, paymentMethod, paymentDate, status, description) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, paymentCode);
            ps.setInt(2, payment.getOrderId()); // Giả định là orderId trong DB là userId trong model
            ps.setInt(3, payment.getOrderId());
            ps.setBigDecimal(4, BigDecimal.valueOf(payment.getAmount().doubleValue()));
            ps.setString(5, payment.getPaymentMethod());
            ps.setTimestamp(6, new Timestamp(new Date().getTime()));
            ps.setString(7, payment.getStatus() != null ? payment.getStatus() : "PENDING");
            ps.setString(8, "Payment for order #" + payment.getOrderId());
            
            int result = ps.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            System.out.println("Error creating payment: " + e.getMessage());
        } finally {
            closeResources();
        }
        return false;
    }
    
    /**
     * Tạo thanh toán mới với thông tin chi tiết
     * 
     * @param userId ID người dùng thanh toán
     * @param amount Số tiền thanh toán
     * @param paymentMethod Phương thức thanh toán
     * @param description Mô tả thanh toán
     * @return Mã thanh toán nếu thành công, null nếu thất bại
     */
    public String createPayment(int userId, BigDecimal amount, String paymentMethod, String description) {
        String sql = "INSERT INTO Payments (paymentCode, userId, amount, paymentMethod, paymentDate, status, description) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        // Tạo mã thanh toán duy nhất
        String paymentCode = "PAY-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        
        try {
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
    
    /**
     * Cập nhật trạng thái thanh toán
     * 
     * @param paymentCode Mã thanh toán cần cập nhật
     * @param status Trạng thái mới
     * @return true nếu cập nhật thành công, false nếu thất bại
     */
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
    
    /**
     * Lấy thông tin thanh toán theo mã
     * 
     * @param paymentCode Mã thanh toán cần tìm
     * @return Đối tượng Payment nếu tìm thấy, null nếu không tìm thấy
     */
    public Payment getPaymentByCode(String paymentCode) {
        String sql = "SELECT * FROM Payments WHERE paymentCode = ?";
        
        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, paymentCode);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                Payment payment = new Payment();
                payment.setId(rs.getInt("paymentId"));
                payment.setOrderId(rs.getInt("orderId"));
                payment.setAmount(rs.getBigDecimal("amount"));
                payment.setPaymentMethod(rs.getString("paymentMethod"));
                payment.setStatus(rs.getString("status"));
                payment.setPaymentDate(rs.getTimestamp("paymentDate"));
                payment.setCardNumber(rs.getString("cardNumber"));
                payment.setCardName(rs.getString("cardName"));
                return payment;
            }
        } catch (SQLException e) {
            System.out.println("Error getting payment: " + e.getMessage());
        } finally {
            closeResources();
        }
        return null;
    }
    
    /**
     * Lấy danh sách thanh toán theo người dùng
     * 
     * @param userId ID người dùng
     * @return Danh sách các thanh toán
     */
    public List<Payment> getPaymentsByUserId(int userId) {
        List<Payment> payments = new ArrayList<>();
        String sql = "SELECT * FROM Payments WHERE userId = ? ORDER BY paymentDate DESC";
        
        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                Payment payment = new Payment();
                payment.setId(rs.getInt("paymentId"));
                payment.setOrderId(rs.getInt("orderId"));
                payment.setAmount(rs.getBigDecimal("amount"));
                payment.setPaymentMethod(rs.getString("paymentMethod"));
                payment.setStatus(rs.getString("status"));
                payment.setPaymentDate(rs.getTimestamp("paymentDate"));
                payment.setCardNumber(rs.getString("cardNumber"));
                payment.setCardName(rs.getString("cardName"));
                payments.add(payment);
            }
        } catch (SQLException e) {
            System.out.println("Error getting payments: " + e.getMessage());
        } finally {
            closeResources();
        }
        return payments;
    }
    
    /**
     * Lấy thông tin thanh toán theo ID đơn hàng
     * 
     * @param orderId ID đơn hàng
     * @return Đối tượng Payment nếu tìm thấy, null nếu không tìm thấy
     */
    public Payment getPaymentByOrderId(int orderId) {
        String sql = "SELECT * FROM Payments WHERE orderId = ?";
        
        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, orderId);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                Payment payment = new Payment();
                payment.setId(rs.getInt("paymentId"));
                payment.setOrderId(rs.getInt("orderId"));
                payment.setAmount(rs.getBigDecimal("amount"));
                payment.setPaymentMethod(rs.getString("paymentMethod"));
                payment.setStatus(rs.getString("status"));
                payment.setPaymentDate(rs.getTimestamp("paymentDate"));
                payment.setCardNumber(rs.getString("cardNumber"));
                payment.setCardName(rs.getString("cardName"));
                return payment;
            }
        } catch (SQLException e) {
            System.out.println("Error getting payment by order ID: " + e.getMessage());
        } finally {
            closeResources();
        }
        return null;
    }
    
    /**
     * Đóng tài nguyên để tránh rò rỉ bộ nhớ
     */
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