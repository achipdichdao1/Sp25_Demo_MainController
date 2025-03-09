package service;

import service.PaymentDAO;
import dto.PaymentRequest;
import java.math.BigDecimal;
import java.util.List;
import model.Payment;

public class PaymentService {
    
    private PaymentDAO paymentDAO;
    
    public PaymentService() {
        paymentDAO = new PaymentDAO();
    }
    
    // Khởi tạo thanh toán mới
    public String initiatePayment(PaymentRequest request) {
        // Gọi DAO để tạo bản ghi thanh toán mới với trạng thái PENDING
        String paymentCode = paymentDAO.createPayment(
                request.getUserId(),
                request.getAmount(), 
                request.getPaymentMethod(), 
                request.getDescription()
        );
        
        // Trả về mã thanh toán để điều hướng đến trang thanh toán
        return paymentCode;
    }
    
    // Xử lý khi thanh toán thành công
    public boolean processSuccessfulPayment(String paymentCode) {
        return paymentDAO.updatePaymentStatus(paymentCode, "COMPLETED");
    }
    
    // Xử lý khi thanh toán thất bại
    public boolean processCancelledPayment(String paymentCode) {
        return paymentDAO.updatePaymentStatus(paymentCode, "FAILED");
    }
    
    // Lấy thông tin thanh toán theo mã
    public Payment getPaymentDetails(String paymentCode) {
        return paymentDAO.getPaymentByCode(paymentCode);
    }
    
    // Lấy lịch sử thanh toán của người dùng
    public List<Payment> getPaymentHistory(int userId) {
        return paymentDAO.getPaymentsByUserId(userId);
    }
    
    // Tạo URL thanh toán với cổng thanh toán VNPay 
    public String createVNPayUrl(PaymentRequest request, String paymentCode) {
        // Đây là một ví dụ đơn giản, thực tế cần tích hợp API của VNPay
        
        String vnpUrl = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";
        String vnpReturnUrl = request.getReturnUrl() + "?paymentCode=" + paymentCode;
        
        // Các tham số cần thiết cho VNPay
        String vnpTmnCode = "YOUR_TMN_CODE"; // Mã website tại VNPay
        String secretKey = "YOUR_SECRET_KEY"; // Secret key của Merchant
        
        // Xây dựng URL với các tham số (giả lập)
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(vnpUrl);
        urlBuilder.append("?vnp_Version=2.1.0");
        urlBuilder.append("&vnp_Command=pay");
        urlBuilder.append("&vnp_TmnCode=").append(vnpTmnCode);
        urlBuilder.append("&vnp_Amount=").append(request.getAmount().multiply(new BigDecimal(100)).intValue());
        urlBuilder.append("&vnp_CurrCode=VND");
        urlBuilder.append("&vnp_TxnRef=").append(paymentCode);
        urlBuilder.append("&vnp_OrderInfo=").append(request.getDescription());
        urlBuilder.append("&vnp_ReturnUrl=").append(vnpReturnUrl);
        
        // Trong thực tế cần thêm nhiều tham số khác và tạo chữ ký
        
        return urlBuilder.toString();
    }
    
    // Có thể thêm các phương thức tạo URL cho các cổng thanh toán khác như MOMO, ZaloPay...
}