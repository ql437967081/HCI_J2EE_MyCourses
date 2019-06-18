<%--
  Created by IntelliJ IDEA.
  User: 43796
  Date: 2018/12/30
  Time: 18:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>MyCourses: 登录本网站</title>
</head>
<body>
<%@include file="/info_page/alert.jsp"%>
<h1 align=center>登录</h1>
<form method="post" action="/login">
    <table align="center">
        <tr>
            <td style='padding:5px'>用户名：</td>
            <td style='padding:5px'>
                <input type='text' name='login' value='<%= request.getAttribute("login") %>'>
            </td>
        </tr>
        <tr>
            <td style='padding:5px'>密码：</td>
            <td style='padding:5px'>
                <input type='password' name='password' value=''>
            </td>
        </tr>
        <tr>
            <td style='padding:5px' colspan='2' align='center'>
                <input type='submit' name='Submit' value='登录'>
            </td>
        </tr>
        <tr>
            <td style='padding:5px' colspan='2' align='center'>
                <a href="/login/register">点击这里注册，开始使用 MyCourses ！</a>
            </td>
        </tr>
    </table>
</form>
</body>
</html>
