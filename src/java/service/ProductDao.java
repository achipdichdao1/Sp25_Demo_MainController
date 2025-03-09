package service;

import dao.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import model.Product;

public class ProductDao implements IProductDAO {

    private static final String INSERT_PRODUCT = "INSERT INTO Product(name, price, description, stock,import_date,status) VALUES (?,?,?,?,?,?)";
    private static final String SELECT_PRODUCT_BY_ID = "SELECT * FROM Product WHERE id=?";
    private static final String UPDATE_PRODUCT = "UPDATE Product SET name=?, price=?, description=?, stock=?, status=? WHERE id=?";
private static final String SELECT_ALL_PRODUCTS = "SELECT * FROM Product";


    private static final String DELETE_PRODUCT = "UPDATE Product SET status = 0 WHERE id = ?";

    @Override
    public void insertProduct(Product product) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBConnection.getConnection();
            if (conn != null) {
                ps = conn.prepareStatement(INSERT_PRODUCT);
                ps.setString(1, product.getName());
                ps.setDouble(2, product.getPrice());
                ps.setString(3, product.getDescription());
                ps.setInt(4, product.getStock());
                ps.setObject(5, product.getImportDate());
                ps.setBoolean(6, product.isStatus());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Or log the exception
            throw e; // Re-throw to be handled by the caller
        } finally {
            closeResources(conn, ps, null); // Close resources in finally block
        }
    }


    @Override
    public Product selectProduct(int id) {
        Product product = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.getConnection();
            if (conn != null) {
                ps = conn.prepareStatement(SELECT_PRODUCT_BY_ID);
                ps.setInt(1, id);
                rs = ps.executeQuery();
                if (rs.next()) {
                    LocalDateTime importDate = rs.getTimestamp("import_date") != null
                            ? rs.getTimestamp("import_date").toLocalDateTime()
                            : null;

                    product = new Product(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getDouble("price"),
                            rs.getString("description"),
                            rs.getInt("stock"),
                            importDate,
                            rs.getBoolean("status")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, rs);
        }
        return product;
    }

    @Override
    public List<Product> selectAllProducts() {
        List<Product> products = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            if (conn != null) {
                ps = conn.prepareStatement(SELECT_ALL_PRODUCTS);
                rs = ps.executeQuery(); // Execute the query!

                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    double price = rs.getDouble("price");
                    String description = rs.getString("description");
                    int stock = rs.getInt("stock");
                    LocalDateTime importDate = rs.getTimestamp("import_date") != null
                            ? rs.getTimestamp("import_date").toLocalDateTime()
                            : null;
                    boolean status = rs.getBoolean("status");
                    products.add(new Product(id, name, price, description, stock, importDate, status));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, rs); // Always close resources
        }
        return products;
    }

    @Override
    public boolean deleteProduct(int id) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBConnection.getConnection();
            if (conn != null) {
                ps = conn.prepareStatement(DELETE_PRODUCT);
                ps.setInt(1, id);
                int rowsDeleted = ps.executeUpdate();
                return rowsDeleted > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            closeResources(conn, ps, null);
        }
        return false;
    }
    @Override
    public boolean updateProduct(Product product) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBConnection.getConnection();
            if (conn != null) {
                ps = conn.prepareStatement(UPDATE_PRODUCT);
                ps.setString(1, product.getName());
                ps.setDouble(2, product.getPrice());
                ps.setString(3, product.getDescription());
                ps.setInt(4, product.getStock());
                ps.setBoolean(5, product.isStatus());
                ps.setInt(6, product.getId());  // Set the ID for the WHERE clause
                int rowsUpdated = ps.executeUpdate();
                return rowsUpdated > 0;
            }
        } catch(SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            closeResources(conn, ps, null);
        }
        return false;
    }

    // Helper method to close resources
    private void closeResources(Connection conn, PreparedStatement ps, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
    System.err.println("Lỗi khi thao tác với cơ sở dữ liệu: " + e.getMessage());
    throw new RuntimeException("Lỗi cơ sở dữ liệu, vui lòng thử lại sau!", e);
}

    }

    public Product selectProductById(int productId) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}