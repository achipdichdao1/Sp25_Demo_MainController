package controller.payment;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import model.Payment;
import service.PaymentService;

@WebServlet(name = "PaymentCallbackServlet", urlPatterns = {"/PaymentCallbackServlet"})
public class PaymentCallbackServlet extends HttpServlet {
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = "payment/result.jsp";
        
        try {
            // Lấy thông tin từ request
            String status = request.getParameter("status");
            String paymentCode = request.getParameter("paymentCode");
            
            // Kiểm tra thông tin
            if (paymentCode == null || paymentCode.trim().isEmpty()) {
                request.setAttribute("ERROR", "Invalid payment reference");
                request.getRequestDispatcher(url).forward(request, response);
                return;
            }
            
            PaymentService paymentService = new PaymentService();
            boolean success = false;
            
            // Xử lý kết quả theo status
            if ("success".equals(status)) {
                success = paymentService.processSuccessfulPayment(paymentCode);
                request.setAttribute("SUCCESS", success);
                request.setAttribute("MESSAGE", "Payment completed successfully!");
            } else {
                success = paymentService.processCancelledPayment(paymentCode);
                request.setAttribute("SUCCESS", false);
                request.setAttribute("MESSAGE", "Payment was cancelled or failed.");
            }
            
            // Lấy thông tin chi tiết thanh toán
            Payment payment = paymentService.getPaymentDetails(paymentCode);
            request.setAttribute("payment", payment);
            
        } catch (Exception e) {
            log("PaymentCallbackServlet Error: " + e.getMessage());
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