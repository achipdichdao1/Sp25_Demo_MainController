<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Giỏ Hàng</title>
    <style>
        .button {
            background-color: #4CAF50;
            border: none;
            color: white;
            padding: 10px 20px;
            text-align: center;
            text-decoration: none;
            display: inline-block;
            font-size: 16px;
            margin: 10px 2px;
            cursor: pointer;
            border-radius: 4px;
        }
        .button:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>
    <h2>Giỏ Hàng</h2>
    <table border="1" cellpadding="5">
        <tr>
            <th>Tên Sản Phẩm</th>
            <th>Giá</th>
            <th>Số Lượng</th>
            <th>Tổng</th>
            <th>Hành động</th>
        </tr>
        <c:forEach var="item" items="${cart.items}">
            <tr>
                <td>${item.product.name}</td>
                <td>${item.product.price}</td>
                <td>${item.quantity}</td>
                <td>${item.product.price * item.quantity}</td>
                <td>
                    <a href="${pageContext.request.contextPath}/cart?action=remove&productId=${item.product.id}" class="button" style="background-color: #ff4444;">Xóa</a>
                </td>
            </tr>
        </c:forEach>
        <tr>
            <td colspan="4">Tổng cộng:</td>
            <td>${cart.totalPrice}</td>
        </tr>
    </table>
    
    <a href="javascript:history.back()" class="button">Quay lại</a>
    <!-- Gọi MainController với action=checkout -->
    <a href="${pageContext.request.contextPath}/cart?action=checkout" class="button" style="background-color: #007bff;">Thanh toán</a>
</body>
</html>