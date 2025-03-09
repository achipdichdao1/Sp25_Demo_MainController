<%-- 
    Document   : editUser
    Created on : Feb 6, 2025, 6:19:58 PM
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
        <form method="post">
            <table border="1" cellpadding="5">
                <caption>
                    <h2>
                        Edit User
                    </h2>
                </caption>
                <c:if test="${user!=null}">
                    <input type="hidden" name="id" value="<c:out value='${user.id}'/>"/>                  
                </c:if>
                <tr>
                    <th>User Name:</th>
                    <td>
                        <input type="text" name="name" size="45" value="<c:out value='${user.username}'/>"/>
                    </td>
                </tr>
                <tr>
                    <th>Email:</th>
                    <td>
                        <input type="text" name="email" size="45" value="<c:out value='${user.email}'/>"/>
                    </td>
                </tr>
                <tr>
                    <th>Country:</th>
                    <td>
                        <input type="text" name="country" size="15" value="<c:out value='${user.country}'/>"/>
                    </td>
                </tr>
                <tr>
                    <th>Role:</th>
                    <td>
                        <select name="role">
                            <option value="admin" <c:if test="${user.role == 'admin'}">selected</c:if>>Admin</option>
                            <option value="user" <c:if test="${user.role == 'user'}">selected</c:if>>User</option>
                            <option value="guest" <c:if test="${user.role == 'guest'}">selected</c:if>>Guest</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <th>Status:</th>
                        <td><input type="checkbox" name="status" size="15" value="true"/>
                        <c:if test="${user.status}">checked</c:if> 
                        </td>
                    </tr>
                    <tr>
                        <th>Password:</th>
                        <td><input type="password" name="password" size="15" value="<c:out value='${user.password}'/>"/></td>
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