package controller.payment;

import dto.PaymentRequest;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import service.PaymentService;

@WebServlet(name = "ProcessPaymentServlet", urlPatterns = {"/ProcessPaymentServlet"})
public class ProcessPaymentServlet extends HttpServlet {
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = "payment/checkout.jsp";
        
        try {
            // Lấy thông tin từ form
            String amountStr = request.getParameter("amount");
            String paymentMethod = request.getParameter("paymentMethod");
            String description = request.getParameter("description");
            
            // Validate input
            if (amountStr == null || amountStr.trim().isEmpty() || paymentMethod == null) {
                request.setAttribute("ERROR", "Missing required payment information");
                request.getRequestDispatcher(url).forward(request, response);
                return;
            }
            
            // Lấy thông tin người dùng từ session
            HttpSession session = request.getSession();
            // Giả sử UserId được lưu trong session khi đăng nhập
            Integer userId = (Integer) session.getAttribute("userId");
            
            if (userId == null) {
                response.sendRedirect("login.jsp"); // Chuyển hướng đến trang đăng nhập nếu chưa đăng nhập
                return;
            }
            
            // Tạo PaymentRequest
            PaymentRequest paymentRequest = new PaymentRequest();
            paymentRequest.setUserId(userId);
            paymentRequest.setAmount(new BigDecimal(amountStr));
            paymentRequest.setPaymentMethod(paymentMethod);
            paymentRequest.setDescription(description);
            
            // Set return URL và cancel URL
            String baseURL = request.getScheme() + "://" + request.getServerName() + ":" + 
                             request.getServerPort() + request.getContextPath();
            paymentRequest.setReturnUrl(baseURL + "/MainController?action=paymentCallback&status=success");
            paymentRequest.setCancelUrl(baseURL + "/MainController?action=paymentCallback&status=cancel");
            
            // Khởi tạo thanh toán
            PaymentService paymentService = new PaymentService();
            String paymentCode = paymentService.initiatePayment(paymentRequest);
            
            if (paymentCode != null) {
                // Nếu phương thức thanh toán là VNPAY
                if ("VNPAY".equals(paymentMethod)) {
                    String vnpayUrl = paymentService.createVNPayUrl(paymentRequest, paymentCode);
                    response.sendRedirect(vnpayUrl);
                    return;
                } 
                // Nếu phương thức thanh toán là MOMO
                else if ("MOMO".equals(paymentMethod)) {
                    // Tạo URL thanh toán MOMO và chuyển hướng
                    // String momoUrl = paymentService.createMomoUrl(paymentRequest, paymentCode);
                    // response.sendRedirect(momoUrl);
                    // return;
                }
                // Nếu là thanh toán nội bộ hoặc phương thức khác
                else {
                    request.setAttribute("paymentCode", paymentCode);
                    url = "payment/confirmation.jsp";
                }
            } else {
                request.setAttribute("ERROR", "Error creating payment. Please try again.");
            }
            
        } catch (NumberFormatException e) {
            request.setAttribute("ERROR", "Invalid amount format");
        } catch (Exception e) {
            log("ProcessPaymentServlet Error: " + e.getMessage());
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