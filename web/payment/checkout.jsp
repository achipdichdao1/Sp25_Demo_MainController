<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Payment Checkout</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5">
        <h1>Payment Checkout</h1>
        
        <c:if test="${not empty ERROR}">
            <div class="alert alert-danger">
                ${ERROR}
            </div>
        </c:if>
        
        <div class="card">
            <div class="card-body">
                <form action="MainController" method="post">
                    <input type="hidden" name="action" value="processPayment">
                    
                    <div class="mb-3">
                        <label for="amount" class="form-label">Amount (VND)</label>
                        <input type="number" class="form-control" id="amount" name="amount" required min="1000">
                    </div>
                    
                    <div class="mb-3">
                        <label for="description" class="form-label">Description</label>
                        <textarea class="form-control" id="description" name="description" rows="3"></textarea>
                    </div>
                    
                    <div class="mb-3">
                        <label class="form-label">Payment Method</label>
                        <div class="form-check">
                            <input class="form-check-input" type="radio" name="paymentMethod" id="vnpay" value="VNPAY" checked>
                            <label class="form-check-label" for="vnpay">
                                VNPAY
                            </label>
                        </div>
                        <div class="form-check">
                            <input class="form-check-input" type="radio" name="paymentMethod" id="momo" value="MOMO">
                            <label class="form-check-label" for="momo">
                                MoMo
                            </label>
                        </div>
                        <div class="form-check">
                            <input class="form-check-input" type="radio" name="paymentMethod" id="bank" value="BANK_TRANSFER">
                            <label class="form-check-label" for="bank">
                                Bank Transfer
                            </label>
                        </div>
                    </div>
                    
                    <button type="submit" class="btn btn-primary">Proceed to Payment</button>
                    <a href="MainController" class="btn btn-secondary">Cancel</a>
                </form>
            </div>
        </div>
    </div>
</body>
</html>