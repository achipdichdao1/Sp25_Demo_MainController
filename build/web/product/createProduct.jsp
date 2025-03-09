<%-- createProduct.jsp --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Add New Product</title>
</head>
<body>
<center>
    <h1>Product Management</h1>
    <h2>
        <a href="products">List All Products</a>
    </h2>
</center>
<div align="center">
    <form method="post" action="products?action=create"> <%--  Add action="create" --%>
        <table border="1" cellpadding="5">
            <caption>
                <h2>Add New Product</h2>
            </caption>
            <tr>
                <th>Product Name:</th>
                <td>
                    <input type="text" name="name" id="name" size="45" required/> <%-- Added required --%>
                </td>
            </tr>
            <tr>
                <th>Product Price:</th>
                <td>
                    <input type="number" name="price" id="price" size="15" step="0.01" required/> <%-- Added required and type number --%>
                </td>
            </tr>
            <tr>
                <th>Description:</th>
                <td>
                    <input type="text" name="description" id="description" size="45"/>
                </td>
            </tr>
            <tr>
                <th>Stock:</th>
                <td>
                    <input type="number" name="stock" id="stock" size="15" required/> <%-- Added required and type number--%>
                </td>
            </tr>
             <tr>
                    <th>Status:</th>
                    <td>
                        <input type="checkbox" name="status" id="status" checked /> 
                    </td>
                </tr>
            <tr>
                <td colspan="2" align="center">
                    <input type="submit" value="Save"/>
                </td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>