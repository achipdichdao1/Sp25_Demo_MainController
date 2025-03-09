<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login</title>
    <style>
        body { font-family: Arial, sans-serif; background-color: #f2f2f2; }
        .login-container { width: 300px; margin: 100px auto; padding: 20px; background-color: #fff; border-radius: 5px; }
        .login-container h2 { text-align: center; }
        .login-container input[type="text"],
        .login-container input[type="password"] {
            width: 100%; padding: 10px; margin: 5px 0 15px; border: 1px solid #ccc; border-radius: 3px;
        }
        .login-container input[type="submit"] {
            width: 100%; padding: 10px; background-color: #007bff; color: #fff;
            border: none; border-radius: 3px; cursor: pointer;
        }
        .login-container input[type="submit"]:hover { background-color: #0056b3; }
        .error { color: red; text-align: center; }
    </style>
</head>
<body>
    <div class="login-container">
        <h2>Login</h2>
        <form action="login" method="post">
            <label for="username">Username:</label>
            <input type="text" name="username" id="username" required>
            
            <label for="password">Password:</label>
            <input type="password" name="password" id="password" required>
            
            <input type="submit" value="Login">
        </form>
        <p>Don't have an account? <a href="register.jsp">Register here</a></p>
        <c:if test="${not empty errorMessage}">
            <p class="error">${errorMessage}</p>
        </c:if>
    </div>
</body>
</html>
