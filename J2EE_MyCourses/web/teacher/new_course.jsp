<%--
  Created by IntelliJ IDEA.
  User: 43796
  Date: 2019/2/10
  Time: 15:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>创建新的课程</title>
    <%@include file="/navbar/style.jsp"%>
    <%@include file="add_delete_courseware.jsp"%>
</head>
<body>
<%@include file="/navbar/teacher_navbar.jsp"%>
<%@include file="/info_page/alert.jsp"%>
<div style="margin-left:25%;padding:1px 16px;">
    <h1 align=center>创建课程</h1>
    <form method="post" action="/teacher/new_course" enctype="multipart/form-data">
        <table align="center">
            <tr>
                <td style='padding:5px'>课程名称：</td>
                <td style='padding:5px'>
                    <input type='text' name='name' value=''>
                </td>
            </tr>
            <tr>
                <td style='padding:5px'>课件：</td>
                <td style='padding:5px'>
                    <div id="coursewares"></div>
                    <input type="button" value="添加.." onclick="addCourseware()">
                </td>
            </tr>
            <tr>
                <td style='padding:5px' colspan='2' align='center'>
                    <input type='submit' value='创建课程'>
                </td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>
