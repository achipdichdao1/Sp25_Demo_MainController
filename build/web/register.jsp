<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Register</title>
    <style>
              body { font-family: Arial, sans-serif; background-color: #f2f2f2; }
        .register-container { width: 300px; margin: 100px auto; padding: 20px; background-color: #fff; border-radius: 5px; }
        .register-container h2 { text-align: center; }
        .register-container input[type="text"],
        .register-container input[type="email"],
        .register-container input[type="password"] {
            width: 100%; padding: 10px; margin: 5px 0 15px; border: 1px solid #ccc; border-radius: 3px;
        }
        .register-container input[type="submit"] {
            width: 100%; padding: 10px; background-color: #28a745; color: #fff;
            border: none; border-radius: 3px; cursor: pointer;
        }
        .register-container input[type="submit"]:hover { background-color: #218838; } 
        .error { color: red; text-align: center; }
    </style>
</head>
<body>
    <div class="register-container">
        <h2>Register</h2>
        <form action="register" method="post">
            <label for="username">Username:</label>
            <input type="text" name="username" id="username" required>
            
            <label for="email">Email:</label>
            <input type="email" name="email" id="email" required>
            
            <label for="country">Country:</label>
            <input type="text" name="country" id="country" required>
            
            <label for="password">Password:</label>
            <input type="password" name="password" id="password" required>
            
            <!-- Role tự động được đặt là "user" -->
            <input type="hidden" name="role" value="user">
            
            <input type="submit" value="Register">
        </form>
        <p>Already have an account? <a href="login.jsp">Login here</a></p>
        <c:if test="${not empty errorMessage}">
            <p class="error">${errorMessage}</p>
        </c:if>
    </div>
</body>
</html>
