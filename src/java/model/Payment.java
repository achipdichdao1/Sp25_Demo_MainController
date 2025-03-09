package model;

import java.math.BigDecimal;
import java.util.Date;

public class Payment {
    private int paymentId;
    private String paymentCode;
    private int userId;
    private BigDecimal amount;
    private String paymentMethod;
    private Date paymentDate;
    private String status; // 'PENDING', 'COMPLETED', 'FAILED'
    private String description;

    // Constructors
    public Payment() {
    }
    
    public Payment(int paymentId, String paymentCode, int userId, BigDecimal amount, 
                   String paymentMethod, Date paymentDate, String status, String description) {
        this.paymentId = paymentId;
        this.paymentCode = paymentCode;
        this.userId = userId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.paymentDate = paymentDate;
        this.status = status;
        this.description = description;
    }

    // Getters and Setters
    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public String getPaymentCode() {
        return paymentCode;
    }

    public void setPaymentCode(String paymentCode) {
        this.paymentCode = paymentCode;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "paymentId=" + paymentId +
                ", paymentCode='" + paymentCode + '\'' +
                ", userId=" + userId +
                ", amount=" + amount +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", paymentDate=" + paymentDate +
                ", status='" + status + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}