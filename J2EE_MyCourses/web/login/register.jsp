<%--
  Created by IntelliJ IDEA.
  User: 43796
  Date: 2019/2/1
  Time: 14:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<jsp:useBean id="register" type="edu.nju.wsql.beans.RegisterBean" scope="session" />
<head>
    <title>MyCourses: 注册</title>
</head>
<body>
<h1 align=center>注册</h1>
<form method="post" action="/login/register">
    <table align="center">
        <tr>
            <td style='padding:5px'>邮箱：</td>
            <td style='padding:5px'>
                <input type='email' name='email' value="<%= register.getEmail() %>">
            </td>
        </tr>
        <tr>
            <td style='padding:5px'>密码：</td>
            <td style='padding:5px'>
                <input type='password' name='password' value="<%= register.getPassword() %>">
            </td>
        </tr>
        <tr>
            <td style='padding:5px'>确认密码：</td>
            <td style='padding:5px'>
                <input type='password' name='confirmPassword' value="<%= register.getConfirmPassword() %>">
            </td>
        </tr>
        <tr>
            <td style='padding:5px'>姓名：</td>
            <td style='padding:5px'>
                <input type='text' name='name' value="<%= register.getName() %>">
            </td>
        </tr>
        <tr>
            <td style='padding:5px' colspan='2' align='center'>
                <input type='submit' value='注册'>
            </td>
        </tr>
    </table>
</form>
</body>
</html>
