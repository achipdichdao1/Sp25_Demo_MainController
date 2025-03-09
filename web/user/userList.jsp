<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="listener.SessionCounterListener" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Danh sách Người Dùng</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
            text-align: center;
        }
        .stats-panel {
            background-color: #f8f9fa;
            border: 1px solid #ddd;
            border-radius: 4px;
            padding: 15px;
            margin-bottom: 20px;
            display: inline-block;
        }
        .stat-item {
            display: inline-block;
            margin: 0 15px;
            padding: 8px;
            background-color: white;
            border-radius: 4px;
            box-shadow: 0 1px 3px rgba(0,0,0,0.1);
        }
        table {
            width: 80%;
            margin: auto;
            border-collapse: collapse;
            margin-bottom: 20px;
        }
        th, td {
            border: 1px solid #ddd;
            padding: 10px;
            text-align: center;
        }
        th {
            background-color: #f4f4f4;
        }
        .pagination a {
            margin: 5px;
            padding: 8px 12px;
            text-decoration: none;
            border: 1px solid #ccc;
            background-color: #f9f9f9;
            display: inline-block;
        }
        .pagination a.active {
            font-weight: bold;
            background-color: #ddd;
        }
        .actions a {
            margin: 0 5px;
            text-decoration: none;
            color: blue;
        }
        .actions a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
    <h2>Danh sách Người Dùng</h2>
    
    <!-- Statistics Panel -->
    <div class="stats-panel">
        <div class="stat-item">
            <strong>Phiên đang hoạt động:</strong> 
            <%= SessionCounterListener.getActiveSessions() %>
        </div>
        <div class="stat-item">
            <strong>Tổng số người dùng:</strong> 
            ${listUser.size()}
        </div>
        <div class="stat-item">
            <strong>Lượt xem hồ sơ:</strong> 
            ${applicationScope.userViewCount}
        </div>
    </div>
    
    <c:if test="${empty listUser}">
        <p>Không có người dùng nào.</p>
    </c:if>
    
    <c:if test="${not empty listUser}">
        <table>
            <tr>
                <th>ID</th>
                <th>Tên người dùng</th>
                <th>Email</th>
                <th>Quốc gia</th>
                <th>Vai trò</th>
                <th>Trạng thái</th>
                <th>Hành động</th>
            </tr>
            <c:forEach var="user" items="${listUser}">
                <tr>
                    <td>${user.id}</td>
                    <td>${user.username}</td>
                    <td>${user.email}</td>
                    <td>${user.country}</td>
                    <td>${user.role}</td>
                    <td>${user.status ? 'Hoạt động' : 'Không hoạt động'}</td>
                    <td class="actions">
                        <a href="<c:url value='/users?action=edit&id=${user.id}' />">Sửa</a>
                        <a href="<c:url value='/users?action=delete&id=${user.id}' />" 
                           onclick="return confirm('Bạn có chắc chắn muốn xóa người dùng này?')">Xóa</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
    
    <!-- Phân trang -->
    <c:if test="${totalPages > 1}">
        <div class="pagination">
            <c:if test="${currentPage > 1}">
                <a href="<c:url value='/users?page=${currentPage - 1}' />">Trước</a>
            </c:if>
            <c:forEach var="i" begin="1" end="${totalPages}">
                <a href="<c:url value='/users?page=${i}' />" 
                   class="${i == currentPage ? 'active' : ''}">${i}</a>
            </c:forEach>
            <c:if test="${currentPage < totalPages}">
                <a href="<c:url value='/users?page=${currentPage + 1}' />">Sau</a>
            </c:if>
        </div>
    </c:if>
    
    <h2>Thêm Người Dùng Mới</h2>
    <a href="<c:url value='/users?action=create' />">Thêm Người Dùng</a>
    
<div style="text-align: center; margin-top: 20px;">
    <a href="<%= request.getContextPath() %>/shop">
        <button style="padding: 10px 20px; font-size: 16px;">Back to Menu</button>
    </a>
</div>


</body>
</html>