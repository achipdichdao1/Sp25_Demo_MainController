<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Delete User</title>
    <script>
        function confirmDelete(userId, username) {
            let confirmBox = document.getElementById("confirmBox");
            let confirmText = document.getElementById("confirmText");
            let deleteForm = document.getElementById("deleteForm");
            
            confirmText.innerHTML = "Are you sure you want to delete <strong>" + username + "</strong>?";
            deleteForm.action = "products?action=delete&id=" + userId;
            confirmBox.style.display = "block";
        }

        function closePopup() {
            document.getElementById("confirmBox").style.display = "none";
        }
    </script>
    <style>
        #confirmBox {
            display: none;
            position: fixed;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            background: white;
            padding: 20px;
            box-shadow: 0px 0px 10px gray;
            text-align: center;
            z-index: 1000;
        }
        #overlay {
            display: none;
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background: rgba(0, 0, 0, 0.5);
            z-index: 999;
        }
    </style>
</head>
<body>
    <div id="overlay"></div>
    <div id="confirmBox">
        <p id="confirmText"></p>
        <form id="deleteForm" method="post">
            <input type="submit" value="Delete">
            <button type="button" onclick="closePopup()">Cancel</button>
        </form>
    </div>

    <button onclick="confirmDelete('<c:out value="${user.id}"/>', '<c:out value="${user.username}"/>')">
        Delete User
    </button>
    <a href="users?action=users">Cancel</a>
</body>
</html>
