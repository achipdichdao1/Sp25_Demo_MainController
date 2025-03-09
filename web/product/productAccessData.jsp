<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Product Access Data</title>
    <style>
        table { width: 80%; margin: 20px auto; border-collapse: collapse; }
        th, td { border: 1px solid #ddd; padding: 8px; text-align: center; }
        th { background-color: #f2f2f2; }
    </style>
</head>
<body>
    <h1>Product Access Data (Sorted by Views)</h1>
    <table>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>View Count</th>
        </tr>
        <c:forEach var="entry" items="${productViewDetails}">
            <tr>
                <td>${entry.key.id}</td>
                <td>${entry.key.name}</td>
                <td>${entry.value}</td>
            </tr>
        </c:forEach>
    </table>
    <a href="${pageContext.request.contextPath}/products">Back to Product List</a>
    <a href="${pageContext.request.contextPath}/shop">Back to Shop</a>
</body>
</html>