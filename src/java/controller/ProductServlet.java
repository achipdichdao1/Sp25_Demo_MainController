package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Product;
import service.ProductDao;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "ProductServlet", urlPatterns = {"/products", "/shop"})
public class ProductServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ProductDao productDAO;

    public void init() {
        productDAO = new ProductDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }

        // Xác định nếu đường dẫn là "/shop"
        String requestURI = request.getRequestURI();
        String contextPath = request.getContextPath();
        String path = requestURI.substring(contextPath.length());
        
        try {
            // Nếu đường dẫn là "/shop", tự động chuyển sang hiển thị danh sách sản phẩm dạng shop
            if (path.equals("/shop")) {
                listProductsShop(request, response);
                return;
            }
            
            // Xử lý các action khác
            switch (action) {
                case "create":
                    showNewForm(request, response);
                    break;
                case "edit":
                    showEditForm(request, response);
                    break;
                case "delete":
                    deleteProduct(request, response);
                    break;
                case "shop":
                    listProductsShop(request, response);
                    break;
                case "viewProduct":
                    viewProduct(request, response);
                    break;
                default:
                    listProducts(request, response);
                    break;
            }
        } catch (Exception ex) {
            throw new ServletException(ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        try {
            switch (action) {
                case "create":
                    insertProduct(request, response);
                    break;
                case "edit":
                    updateProduct(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private void listProducts(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        List<Product> products = productDAO.selectAllProducts();
        request.setAttribute("listProduct", products);
        RequestDispatcher dispatcher = request.getRequestDispatcher("product/productList.jsp");
        dispatcher.forward(request, response);
    }

    private void listProductsShop(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        List<Product> products = productDAO.selectAllProducts();
        request.setAttribute("listProduct", products);

        RequestDispatcher dispatcher = request.getRequestDispatcher("views/productListShop.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("product/createProduct.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Product existingProduct = productDAO.selectProduct(id);
        if (existingProduct == null) {
            response.sendRedirect("products");
            return;
        }
        request.setAttribute("product", existingProduct);
        RequestDispatcher dispatcher = request.getRequestDispatcher("product/editProduct.jsp");
        dispatcher.forward(request, response);
    }

    private void insertProduct(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        String name = request.getParameter("name");
        double price = Double.parseDouble(request.getParameter("price"));
        String description = request.getParameter("description");
        int stock = Integer.parseInt(request.getParameter("stock"));
        LocalDateTime importDate = LocalDateTime.now();  
        boolean status = Boolean.parseBoolean(request.getParameter("status"));
        Product newProduct = new Product(name, price, description, stock, importDate, status);
        productDAO.insertProduct(newProduct);
        response.sendRedirect("products");
    }

    private void updateProduct(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        double price = Double.parseDouble(request.getParameter("price"));
        String description = request.getParameter("description");
        int stock = Integer.parseInt(request.getParameter("stock"));
        String importDateStr = request.getParameter("import_date");
        LocalDateTime importDate;
        if (importDateStr != null && !importDateStr.isEmpty()) {
            importDate = LocalDateTime.parse(importDateStr);
        } else {
            Product existingProduct = productDAO.selectProduct(id);
            importDate = existingProduct.getImportDate();
        }
        boolean status = Boolean.parseBoolean(request.getParameter("status"));
        Product product = new Product(id, name, price, description, stock, importDate, status);
        productDAO.updateProduct(product);
        response.sendRedirect("products");
    }

    private void deleteProduct(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        productDAO.deleteProduct(id);
        response.sendRedirect("products");
  
    }
    
private void viewProduct(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    int productId = Integer.parseInt(request.getParameter("id"));
    Product product = productDAO.selectProduct(productId);

    if (product != null) {
        // Xóa đoạn code đếm lượt xem ở đây vì đã có ProductViewListener xử lý
        request.setAttribute("product", product);
        RequestDispatcher dispatcher = 
            request.getRequestDispatcher("views/productDetail.jsp");
        dispatcher.forward(request, response);
    } else {
        response.sendRedirect("products");
    }
}  

    
    
    
    
}

