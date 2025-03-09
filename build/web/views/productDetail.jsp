<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Chi Tiết Sản Phẩm</title>
</head>
<body>
    <h2>Chi Tiết Sản Phẩm</h2>
    <table border="1">
        <tr>
            <th>Tên:</th>
            <td>${product.name}</td>
        </tr>
        <tr>
            <th>Giá:</th>
            <td>${product.price}</td>
        </tr>
        <tr>
            <th>Mô tả:</th>
            <td>${product.description}</td>
        </tr>
    </table>
    <a href="shop">Quay lại</a>
</body>
</html>
