package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import model.Order;
import dao.DBConnection;

public class OrderDAO {
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    
    public int createOrder(Order order) {
        int orderId = 0;
        String query = "INSERT INTO Orders (user_id, total_price, note, shipping_address, status) "
                    + "VALUES (?, ?, ?, ?, ?)";
        
        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, order.getUserId());
            ps.setDouble(2, order.getTotalPrice());
            ps.setString(3, order.getNote());
            ps.setString(4, order.getShippingAddress());
            ps.setString(5, order.getStatus());
            ps.executeUpdate();
            
            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                orderId = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        return orderId;
    }
    
    public boolean updateOrder(Order order) {
        String query = "UPDATE Orders SET status = ? WHERE id = ?";
        boolean success = false;
        
        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, order.getStatus());
            ps.setInt(2, order.getId());
            success = ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        return success;
    }
}