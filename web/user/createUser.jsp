<%-- 
    Document   : createUser
    Created on : Feb 6, 2025, 6:17:12 PM
    Author     : PC
--%>

<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <title>User Management Application</title>
    </head>
    <body>
    <center>
        <h1>User Management</h1>
        <h2>
            <a href="users?action=users">List All Users</a>
        </h2>
    </center>
    <div align="center">
        <form method="post" action="users?action=create">
            <table border="1" cellpadding="5">
                <caption>
                    <h2>Add New User</h2>
                </caption>
                <tr>
                    <th>User Name:</th>
                    <td>
                        <input type="text" name="name" id="name" size="45">
                    </td>
                </tr>
                <tr>
                    <th>Email:</th>
                    <td>
                        <input type="text" name="email" id="email" size="45">
                    </td>
                </tr>
                <tr>
                    <th>Country:</th>
                    <td>
                        <input type="text" name="country" id="country" size="15">
                    </td>
                </tr>
                <tr>
                    <th>Role</th>
                    <td>
                        <select name="role" id="role">
                            <option value="user">User</option>
                            <option value="guest">Guest</option>
                            <option value="admin">Admin</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <th>Status</th>
                    <td>
                        <input type="checkbox" name="status" value="1" >
                    </td>
                </tr>
                <tr>
                    <th>Password</th>
                    <td>
                        <input type="password" name="password" id="password" size="45">
                    </td>
                </tr>
                <tr>
                    <td colspan="2" align="center">
                        <input type="submit" value="Save"/>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</body>
</html>
