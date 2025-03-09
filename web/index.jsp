<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Main Menu</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 20px;
            display: flex;
            flex-direction: column;
            align-items: center;
            min-height: 100vh;
            background-color: #f5f5f5;
        }
        .menu-container {
            background-color: white;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            text-align: center;
            margin-top: 50px;
        }
        h1 {
            color: #333;
            margin-bottom: 30px;
        }
        .menu-options {
            display: flex;
            gap: 20px;
            justify-content: center;
        }
        .menu-button {
            padding: 15px 30px;
            font-size: 16px;
            border: none;
            border-radius: 4px;
            background-color: #007bff;
            color: white;
            cursor: pointer;
            text-decoration: none;
            transition: background-color 0.3s;
        }
        .menu-button:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
    <div class="menu-container">
        <h1>Welcome to Management System</h1>
        <div class="menu-options">
            <a href="users" class="menu-button">User Management</a>
            <a href="products" class="menu-button">Product Management</a>
        </div>
    </div>
</body>
</html>