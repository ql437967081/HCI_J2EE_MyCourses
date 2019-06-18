<%--
  Created by IntelliJ IDEA.
  User: 43796
  Date: 2019/2/7
  Time: 21:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<jsp:useBean id="info" type="edu.nju.wsql.beans.UserInfoBean" scope="request" />
<head>
    <title>个人信息</title>
    <%@include file="/navbar/style.jsp"%>
    <%@include file="/info_page/show_preview.jsp"%>
</head>
<body>
<%@include file="/navbar/teacher_navbar.jsp"%>
<%@include file="/info_page/alert.jsp"%>
<div style="margin-left:25%;padding:1px 16px;">
    <h1 align=center>个人信息管理</h1>
    <form method="post" action="/teacher/info" enctype="multipart/form-data">
        <table align="center">
            <tr>
                <td style='padding:5px'>教工号：</td>
                <td style='padding:5px'><%= info.getId() %></td>
            </tr>
            <tr>
                <td style='padding:5px'>姓名：</td>
                <td style='padding:5px'>
                    <input type='text' name='name' value='<%= info.getName() %>'>
                </td>
            </tr>
            <tr>
                <td style='padding:5px'>邮箱：</td>
                <td style='padding:5px'><%= info.getEmail() %></td>
            </tr>
            <tr>
                <td style='padding:5px'>头像：</td>
                <td style='padding:5px'>
                    <img id="portrait" src="<%= info.getPortrait() %>" style="width: 100px; height: 100px;">
                    <input type="file" accept="image/*" name="portrait" onchange="showPreview(this)">
                </td>
            </tr>
            <tr>
                <td style='padding:5px' colspan='2' align='center'>
                    <input type='submit' value='保存修改'>
                </td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>
