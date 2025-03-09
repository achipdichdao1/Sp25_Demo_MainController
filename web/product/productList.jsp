<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
    
    <!-- Pagination Variables -->
    <c:set var="pageSize" value="10" />
    <c:set var="currentPage" value="${param.page != null ? param.page : 1}" />
    <c:set var="start" value="${(currentPage - 1) * pageSize}" />
    <c:set var="totalProducts" value="${fn:length(listProduct)}" />
    <c:set var="totalPages" value="${Math.ceil(totalProducts / pageSize)}" />
    
    <!-- Product Table -->
    <table border="1">
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
        <c:forEach var="product" items="${listProduct}" varStatus="status">
            <c:if test="${status.index >= start && status.index < start + pageSize}">
                <tr>
                    <td>${product.id}</td>
                    <td>${product.name}</td>
                    <td>${product.price}</td>
                    <td>${product.description}</td>
                    <td>${product.stock}</td>
                    <td>${product.importDate}</td>
                    <td>${product.status ? 'Available' : 'Out of Stock'}</td>
                    <td>
                        <a href="${pageContext.request.contextPath}/products?action=edit&id=${product.id}">
                            Edit
                        </a>
                        <a href="${pageContext.request.contextPath}/products?action=delete&id=${product.id}"
                           onclick="return confirm('Are you sure you want to delete this product?')">
                            Delete
                        </a>
                    </td>
                </tr>
            </c:if>
        </c:forEach>
    </table>

    <!-- Pagination Controls -->
    <div class="pagination">
        <c:if test="${currentPage > 1}">
            <a href="${pageContext.request.contextPath}/products?page=${currentPage - 1}">
                Previous
            </a>
        </c:if>
        
        <c:forEach var="i" begin="1" end="${totalPages}">
            <a href="${pageContext.request.contextPath}/products?page=${i}"
               class="${currentPage == i ? 'active' : ''}">
                ${i}
            </a>
        </c:forEach>
        
        <c:if test="${currentPage < totalPages}">
            <a href="${pageContext.request.contextPath}/products?page=${currentPage + 1}">
                Next
            </a>
        </c:if>
    </div>

    <!-- Additional Actions -->
    <a href="${pageContext.request.contextPath}/accessData">View Access Data</a>
    <br>
    <a href="${pageContext.request.contextPath}/products?action=create">Add New Product</a>

    <!-- Back to Menu Button -->
    <div style="text-align: center; margin-top: 20px;">
        <a href="${pageContext.request.contextPath}/shop">
            <button style="padding: 10px 20px; font-size: 16px;">Back to Menu</button>
        </a>
    </div>
</body>
</html>