package model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Payment {
    private int id;
    private String paymentMethod;
    private int orderId;
    private BigDecimal amount;
    private String status;
    private Timestamp paymentDate;
    private String cardNumber;
    private String cardName;
    private String expDate;
    
    public Payment() {
        this.paymentDate = new Timestamp(System.currentTimeMillis());
    }
    
    public Payment(int id, String paymentMethod, int orderId, BigDecimal amount, String status, 
                  Timestamp paymentDate, String cardNumber, String cardName) {
        this.id = id;
        this.paymentMethod = paymentMethod;
        this.orderId = orderId;
        this.amount = amount;
        this.status = status;
        this.paymentDate = paymentDate;
        this.cardNumber = cardNumber;
        this.cardName = cardName;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getPaymentMethod() {
        return paymentMethod;
    }
    
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    
    public int getOrderId() {
        return orderId;
    }
    
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
    
    public BigDecimal getAmount() {
        return amount;
    }
    
    public void setAmount(double amount) {
        this.amount = BigDecimal.valueOf(amount);
    }
    
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public Timestamp getPaymentDate() {
        return paymentDate;
    }
    
    public void setPaymentDate(Timestamp paymentDate) {
        this.paymentDate = paymentDate;
    }
    
    public String getCardNumber() {
        return cardNumber;
    }
    
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
    
    public String getCardName() {
        return cardName;
    }
    
    public void setCardName(String cardName) {
        this.cardName = cardName;
    }
    
    public String getExpDate() {
        return expDate;
    }
    
    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }
}