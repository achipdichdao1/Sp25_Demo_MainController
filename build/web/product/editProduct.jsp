<%-- editProduct.jsp --%>
<%@page import="model.Product"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
    Product product = (Product) request.getAttribute("product");
%>
<html>
<head>
     <title>Edit Product</title>
</head>
<body>
<h2>Edit Product</h2>
<form action="products?action=edit" method="post">
    <input type="hidden" name="id" value="<%= product.getId()%>">
    Name: <input type="text" name="name" value="<%= product.getName()%>" required><br>  <%-- Added required --%>
    Price: <input type="number" name="price" value="<%= product.getPrice()%>" step="0.01" required><br> <%-- Added required and type number --%>
    Description: <input type="text" name="description" size="40" value="<%= product.getDescription()%>"><br>
    Stock: <input type="number" name="stock" value="<%= product.getStock()%>" required><br><%-- Added required and type number --%>
     Status: <input type="checkbox" name="status" <%= product.isStatus() ? "checked" : "" %>><br>
    <input type="submit" value="Update">
</form>
</body>
</html>