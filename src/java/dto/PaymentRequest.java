package dto;

import java.math.BigDecimal;

public class PaymentRequest {
    private int userId;
    private BigDecimal amount;
    private String paymentMethod; // e.g. 'CREDIT_CARD', 'MOMO', 'BANK_TRANSFER'
    private String description;
    private String returnUrl;
    private String cancelUrl;

    // Constructors
    public PaymentRequest() {
    }

    public PaymentRequest(int userId, BigDecimal amount, String paymentMethod, 
                          String description, String returnUrl, String cancelUrl) {
        this.userId = userId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.description = description;
        this.returnUrl = returnUrl;
        this.cancelUrl = cancelUrl;
    }

    // Getters and Setters
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public String getCancelUrl() {
        return cancelUrl;
    }

    public void setCancelUrl(String cancelUrl) {
        this.cancelUrl = cancelUrl;
    }
}