package controller;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import model.Product;
import service.ProductDao;
import listener.ProductViewListener;
import java.io.IOException;
import java.util.*;
public class AccessDataServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy danh sách lượt xem đã sắp xếp giảm dần
        Map<Integer, Integer> viewCounts = ProductViewListener.getSortedProductViewCounts(request);
        if (viewCounts == null) {
            viewCounts = new HashMap<>();
            getServletContext().setAttribute("productViewCounts", viewCounts);
        }
        
        // Lấy danh sách sản phẩm
        ProductDao productDao = new ProductDao();
        List<Product> products = productDao.selectAllProducts();
        
        // Tạo map kết hợp thông tin sản phẩm với lượt xem
        // Chúng ta sẽ sử dụng LinkedHashMap để duy trì thứ tự
        Map<Product, Integer> productViewDetails = new LinkedHashMap<>();
        
        // Duyệt qua các mục trong viewCounts đã sắp xếp
        for (Map.Entry<Integer, Integer> entry : viewCounts.entrySet()) {
            int productId = entry.getKey();
            int viewCount = entry.getValue();
            
            // Tìm sản phẩm với ID tương ứng
            for (Product product : products) {
                if (product.getId() == productId) {
                    productViewDetails.put(product, viewCount);
                    break;
                }
            }
        }
        
        // Thêm các sản phẩm chưa có lượt xem (với số lượt xem = 0)
        for (Product product : products) {
            if (!productViewDetails.containsKey(product)) {
                productViewDetails.put(product, 0);
            }
        }
        
        // Truyền dữ liệu sang JSP
        request.setAttribute("productViewDetails", productViewDetails);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/product/productAccessData.jsp");
        dispatcher.forward(request, response);
    }
}