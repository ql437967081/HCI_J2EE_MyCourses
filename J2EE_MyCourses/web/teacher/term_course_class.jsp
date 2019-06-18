<%--
  Created by IntelliJ IDEA.
  User: 43796
  Date: 2019/3/12
  Time: 11:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" import="edu.nju.wsql.beans.SelectCourseBean" %>
<jsp:useBean id="info" type="edu.nju.wsql.beans.SelectCourseListBean" scope="request" />
<html>
<head>
    <title>班级：<%= info.getName() %>&nbsp;&nbsp;<%= info.getTerm() %>&nbsp;&nbsp;<%= info.getClassId() %>班</title>
    <%@include file="/navbar/style.jsp"%>
</head>
<body>
<%@include file="/navbar/teacher_term_course_class_navbar.jsp"%>
<div style="margin-left:25%;padding:1px 16px;">
    <h1 align="center"><%= info.getName() %></h1>
    <h2 align="center"><%= info.getTerm() %></h2>
    <h3 align="center"><%= info.getClassId() %>班</h3>
    <table border="1" align="center">
        <tr>
            <th style='padding:5px'>学生</th>
            <th style='padding:5px'>邮箱</th>
            <th style='padding:5px'>选课时间</th>
        </tr>
        <%
            for (SelectCourseBean bean: info.getSelectCourseBeans()) {
        %>
        <tr>
            <td style='padding:5px'>
                <img src="<%= bean.getPortrait() %>" width="20" height="20">
                <%= bean.getStudentName() %>
            </td>
            <td style='padding:5px'>
                <%= bean.getEmail() %>
            </td>
            <td style='padding:5px'>
                <%= bean.getSelectTime() %>
            </td>
        </tr>
        <%
            }
        %>
    </table>
</div>
</body>
</html>
