<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>Product List</title>
    <style>
        table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 20px;
        }
        th, td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: center;
        }
        th {
            background-color: #f2f2f2;
        }
        .pagination a {
            margin: 0 5px;
            padding: 5px 10px;
            text-decoration: none;
            border: 1px solid #ccc;
            background-color: #f9f9f9;
        }
        .pagination a.active {
            font-weight: bold;
            background-color: #ddd;
        }
        .actions a {
            margin: 0 5px;
        }
    </style>
</head>
<body>
    <h2>Product List</h2>
    <table>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Price</th>
            <th>Description</th>
            <th>Stock</th>
            <th>Import Date</th>
            <th>Status</th>
            <th>Actions</th>
        </tr>
        <c:forEach var="product" items="${products}">
            <tr>
                <td>${product.id}</td>
                <td>${product.name}</td>
                <td>${product.price}</td>
                <td>${product.description}</td>
                <td>${product.stock}</td>
                <td>${product.importDate}</td>
                <td>${product.status ? 'Active' : 'Inactive'}</td>
                <td class="actions">
                    <a href="cart?action=add&id=${product.id}&quantity=1">Add to Cart</a>
                    <a href="<c:url value='/products?action=edit&id=${product.id}' />">Edit</a>
                    <a href="<c:url value='/products?action=delete&id=${product.id}' />" 
                       onclick="return confirm('Are you sure you want to delete this product?')">Delete</a>
                </td>
            </tr>
        </c:forEach>
    </table>
    
    <!-- Pagination -->
    <div class="pagination">
        <c:if test="${currentPage > 1}">
            <a href="<c:url value='/products?page=${currentPage - 1}' />">Previous</a>
        </c:if>
        <c:forEach var="i" begin="1" end="${totalPages}">
            <a href="<c:url value='/products?page=${i}' />" 
               class="${i == currentPage ? 'active' : ''}">${i}</a>
        </c:forEach>
        <c:if test="${currentPage < totalPages}">
            <a href="<c:url value='/products?page=${currentPage + 1}' />">Next</a>
        </c:if>
    </div>
    
    <h2>Add Product</h2>
    <a href="<c:url value='/products?action=create' />">Add Product</a>
    <div style="text-align: center; margin-top: 20px;">
        <a href="<%= request.getContextPath() %>/">
            <button style="padding: 10px 20px; font-size: 16px;">Back to Menu</button>
        </a>
    </div>
</body>
</html>