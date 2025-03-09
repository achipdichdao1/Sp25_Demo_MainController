package controller.payment;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import model.Payment;
import service.PaymentService;

@WebServlet(name = "PaymentHistoryServlet", urlPatterns = {"/PaymentHistoryServlet"})
public class PaymentHistoryServlet extends HttpServlet {
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = "payment/history.jsp";
        
        try {
            // Lấy thông tin người dùng từ session
            HttpSession session = request.getSession();
            Integer userId = (Integer) session.getAttribute("userId");
            
            if (userId == null) {
                response.sendRedirect("login.jsp"); // Chuyển hướng đến trang đăng nhập nếu chưa đăng nhập
                return;
            }
            
            // Lấy lịch sử thanh toán
            PaymentService paymentService = new PaymentService();
            List<Payment> paymentHistory = paymentService.getPaymentHistory(userId);
            
            // Set attribute để hiển thị trong JSP
            request.setAttribute("paymentHistory", paymentHistory);
            
        } catch (Exception e) {
            log("PaymentHistoryServlet Error: " + e.getMessage());
            request.setAttribute("ERROR", "An error occurred: " + e.getMessage());
        }
        
        request.getRequestDispatcher(url).forward(request, response);
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}