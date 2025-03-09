<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head>
    <title>Lỗi</title>
</head>
<body>
    <h2>Đã xảy ra lỗi</h2>
    <p><%= request.getAttribute("errorMessage") %></p>
</body>
</html>
