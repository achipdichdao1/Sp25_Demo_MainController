package listener;

import jakarta.servlet.ServletRequestEvent;
import jakarta.servlet.ServletRequestListener;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.Comparator;

@WebListener
public class ProductViewListener implements ServletRequestListener {
    private static final String PRODUCT_VIEWS_KEY = "productViewCounts";
    private static final String LAST_VIEWED_PRODUCT = "lastViewedProductId";

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        HttpServletRequest request = (HttpServletRequest) sre.getServletRequest();
        String requestURI = request.getRequestURI();
        String contextPath = request.getContextPath();
        String action = request.getParameter("action");

        if (isProductViewRequest(requestURI, contextPath, action)) {
            HttpSession session = request.getSession(false);
            // Chỉ tăng lượt xem nếu người dùng đã đăng nhập
            if (session != null && session.getAttribute("user") != null) {
                handleProductView(request);
            } else {
                System.out.println("User not authenticated, skipping view count increment.");
            }
        }
    }

    private boolean isProductViewRequest(String requestURI, String contextPath, String action) {
        return requestURI.endsWith(contextPath + "/products") && "viewProduct".equals(action);
    }

    private void handleProductView(HttpServletRequest request) {
        try {
            int productId = Integer.parseInt(request.getParameter("id"));
            HttpSession session = request.getSession();
            Integer lastViewed = (Integer) session.getAttribute(LAST_VIEWED_PRODUCT);

            Map<Integer, Integer> viewCounts = getViewCountsFromContext(request);
            if (viewCounts == null) {
                viewCounts = new HashMap<>();
                request.getServletContext().setAttribute(PRODUCT_VIEWS_KEY, viewCounts);
            }

            if (lastViewed == null || lastViewed != productId) {
                session.setAttribute(LAST_VIEWED_PRODUCT, productId);
                int currentCount = viewCounts.getOrDefault(productId, 0);
                viewCounts.put(productId, currentCount + 1);
                System.out.println("Đã cập nhật lượt xem cho sản phẩm " + productId + ": " + viewCounts.get(productId));
            } else {
                System.out.println("Sản phẩm " + productId + " đã được xem trong session này, lượt xem không đổi: " + viewCounts.getOrDefault(productId, 0));
            }
        } catch (NumberFormatException e) {
            System.err.println("ID sản phẩm không hợp lệ: " + request.getParameter("id"));
        }
    }

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        // Không cần xử lý
    }

    private Map<Integer, Integer> getViewCountsFromContext(HttpServletRequest request) {
        return (Map<Integer, Integer>) request.getServletContext().getAttribute(PRODUCT_VIEWS_KEY);
    }

    public static Map<Integer, Integer> getProductViewCounts(HttpServletRequest request) {
        return (Map<Integer, Integer>) request.getServletContext().getAttribute(PRODUCT_VIEWS_KEY);
    }

    public static Map<Integer, Integer> getSortedProductViewCounts(HttpServletRequest request) {
        Map<Integer, Integer> viewCounts = getProductViewCounts(request);
        if (viewCounts == null || viewCounts.isEmpty()) {
            return new TreeMap<>();
        }

        TreeMap<Integer, Integer> sortedMap = new TreeMap<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer key1, Integer key2) {
                Integer view1 = viewCounts.getOrDefault(key1, 0);
                Integer view2 = viewCounts.getOrDefault(key2, 0);
                int valueCompare = view2.compareTo(view1);
                return valueCompare != 0 ? valueCompare : key1.compareTo(key2);
            }
        });

        sortedMap.putAll(viewCounts);
        return sortedMap;
    }
}