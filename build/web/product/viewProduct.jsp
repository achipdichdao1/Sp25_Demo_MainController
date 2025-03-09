<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Xem Sản Phẩm</title>
</head>
<body>
    <h2>Sản Phẩm</h2>
    <form action="cart?action=add" method="post">
        <input type="hidden" name="productId" value="${product.id}">
        <table border="1" cellpadding="5">
            <tr>
                <th>Tên Sản Phẩm:</th>
                <td>${product.name}</td>
            </tr>
            <tr>
                <th>Giá:</th>
                <td>${product.price}</td>
            </tr>
            <tr>
                <th>Mô Tả:</th>
                <td>${product.description}</td>
            </tr>
            <tr>
                <th>Số Lượng:</th>
                <td><input type="number" name="quantity" value="1" min="1"></td>
            </tr>
            <tr>
                <td colspan="2" align="center">
                    <input type="submit" value="Thêm Vào Giỏ Hàng">
                </td>
            </tr>
        </table>
    </form>
</body>
</html>