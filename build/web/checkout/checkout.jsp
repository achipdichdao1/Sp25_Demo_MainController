<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Thanh Toán</title>
    <style>
        table { width: 80%; margin: 20px auto; border-collapse: collapse; }
        th, td { border: 1px solid #ddd; padding: 8px; text-align: center; }
        th { background-color: #f2f2f2; }
        .button { padding: 10px 20px; background-color: #007bff; color: white; text-decoration: none; border-radius: 5px; }
        .button:hover { background-color: #0056b3; }
        .error { color: red; text-align: center; }
    </style>
</head>
<body>
    <h2>Xác Nhận Thanh Toán</h2>
    
    <c:if test="${not empty errorMessage}">
        <p class="error">${errorMessage}</p>
    </c:if>
    
    <table>
        <tr>
            <th>Tên Sản Phẩm</th>
            <th>Giá</th>
            <th>Số Lượng</th>
            <th>Tổng</th>
        </tr>
        <c:forEach var="item" items="${cart.items}">
            <tr>
                <td>${item.product.name}</td>
                <td>${item.product.price}</td>
                <td>${item.quantity}</td>
                <td>${item.product.price * item.quantity}</td>
            </tr>
        </c:forEach>
        <tr>
            <td colspan="3">Tổng cộng:</td>
            <td>${cart.totalPrice}</td>
        </tr>
    </table>
    
    <form action="${pageContext.request.contextPath}/checkout" method="post">
        <div style="text-align: center; margin-top: 20px;">
            <input type="submit" value="Xác Nhận Thanh Toán" class="button">
            <a href="cart?action=show" class="button" style="background-color: #6c757d;">Quay Lại Giỏ Hàng</a>
        </div>
    </form>
</body>
</html>