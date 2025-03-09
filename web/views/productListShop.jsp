<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>Danh Sách Sản Phẩm</title>
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
        .actions a {
            padding: 5px 10px;
            background-color: #28a745;
            color: white;
            text-decoration: none;
            border-radius: 5px;
        }
        .actions a:hover {
            background-color: #218838;
        }
        .notification {
            background-color: #d4edda;
            color: #155724;
            padding: 10px;
            margin-bottom: 15px;
            border-radius: 5px;
        }
        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 20px;
        }
        .auth-buttons a {
            padding: 8px 15px;
            background-color: #007bff;
            color: white;
            text-decoration: none;
            border-radius: 5px;
        }
        .auth-buttons a:hover {
            background-color: #0056b3;
        }
        .admin-buttons a {
            padding: 8px 15px;
            background-color: #007bff;
            color: white;
            text-decoration: none;
            border-radius: 5px;
            margin-left: 10px;
        }
        .admin-buttons a:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
    <div class="header">
        <div style="display: flex; align-items: center;">
            <h2>Danh Sách Sản Phẩm</h2>
            <c:if test="${not empty sessionScope.user && sessionScope.user.role == 'admin'}">
                <div class="admin-buttons">
                    <a href="${pageContext.request.contextPath}/users">User Management</a>
                    <a href="${pageContext.request.contextPath}/products">Product Management</a>
                </div>
            </c:if>
        </div>
        <div class="auth-buttons">
            <c:choose>
                <c:when test="${not empty sessionScope.user}">
                    <a href="${pageContext.request.contextPath}/logout">Logout</a>
                </c:when>
                <c:otherwise>
                    <a href="${pageContext.request.contextPath}/login">Login</a>
                </c:otherwise>
            </c:choose>
        </div>
    </div>

    <c:if test="${not empty sessionScope.cartMessage}">
        <div class="notification">
            ${sessionScope.cartMessage}
        </div>
        <c:remove var="cartMessage" scope="session" />
    </c:if>

    <c:set var="pageSize" value="10"/>
    <c:set var="currentPage" value="${param.page != null ? param.page : 1}"/>
    <c:set var="start" value="${(currentPage - 1) * pageSize}"/>
    <c:set var="totalProducts" value="${fn:length(listProduct)}"/>
    <c:set var="totalPages" value="${(totalProducts + pageSize - 1) / pageSize}"/>
    <table border="1">
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Price</th>
            <th>Description</th>
            <th>Stock</th>
            <!-- Only show ImportDate header for admin users -->
            <c:if test="${not empty sessionScope.user && sessionScope.user.role != 'user'}">
                <th>ImportDate</th>
            </c:if>
            <th>Status</th>
            <!-- Only show Actions column for users and non-logged in visitors -->
            <c:if test="${empty sessionScope.user || sessionScope.user.role == 'user'}">
                <th>Actions</th>
            </c:if>
        </tr>
        <c:forEach var="product" items="${listProduct}" varStatus="status">
            <c:if test="${status.index >= start && status.index < start + pageSize}">
                <tr>
                    <td>${product.id}</td>
                    <td>
                        <a href="${pageContext.request.contextPath}/products?action=viewProduct&id=${product.id}">
                            ${product.name}
                        </a>
                    </td>
                    <td>${product.price}</td>
                    <td>${product.description}</td>
                    <td>${product.stock}</td>
                    <!-- Only show ImportDate for admin users -->
                    <c:if test="${not empty sessionScope.user && sessionScope.user.role != 'user'}">
                        <td>${product.importDate}</td>
                    </c:if>
                    <td>
                        <c:choose>
                            <c:when test="${product.stock > 0}">
                                <span style="color: green;">Còn hàng</span>
                            </c:when>
                            <c:otherwise>
                                <span style="color: red;">Hết hàng</span>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <!-- Only show Actions column for users and non-logged in visitors -->
                    <c:if test="${empty sessionScope.user || sessionScope.user.role == 'user'}">
                        <td class="actions">
                            <a href="cart?action=add&productId=${product.id}&quantity=1&returnUrl=${pageContext.request.requestURI}">Thêm vào giỏ</a>
                        </td>
                    </c:if>
                </tr>
            </c:if>
        </c:forEach>
    </table>

    <div class="pagination">
        <c:if test="${currentPage > 1}">
            <a href="${pageContext.request.contextPath}/shop?page=${currentPage - 1}">
                Previous
            </a>
        </c:if>
        <c:forEach var="i" begin="1" end="${totalPages}">
            <a href="${pageContext.request.contextPath}/shop?page=${i}"
               class="${currentPage == i ? 'active' : ''}">
                ${i}
            </a>
        </c:forEach>
        <c:if test="${currentPage < totalPages}">
            <a href="${pageContext.request.contextPath}/shop?page=${currentPage + 1}">
                Next
            </a>
        </c:if>
    </div>

    <!-- Only show "View Cart" button for users and non-logged-in visitors -->
    <c:if test="${empty sessionScope.user || sessionScope.user.role == 'user'}">
        <div style="text-align: center; margin-top: 20px;">
            <a href="cart?action=show">
                <button style="padding: 10px 20px; font-size: 16px;">Xem Giỏ Hàng</button>
            </a>
        </div>
    </c:if>
</body>
</html>