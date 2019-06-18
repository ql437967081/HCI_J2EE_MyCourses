<%--
  Created by IntelliJ IDEA.
  User: 43796
  Date: 2019/3/4
  Time: 21:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<jsp:useBean id="info" type="edu.nju.wsql.beans.StudentCourseClassBean" scope="request" />
<head>
    <title>课程：<%= info.getName() %>&nbsp;&nbsp;<%= info.getTerm() %></title>
    <%@include file="/navbar/style.jsp"%>
    <%@include file="/info_page/confirm.jsp"%>
</head>
<body>
<%@include file="/navbar/student_course_navbar.jsp"%>
<%@include file="/info_page/alert.jsp"%>
<div style="margin-left:25%;padding:1px 16px;">
    <h1 align=center>课程：<%= info.getName() %></h1>
    <h2 align="center"><%= info.getTerm() %></h2>
    <table align="center">
        <tr>
            <td style='padding:5px'>班级：</td>
            <td style='padding:5px'><%= info.getClassId() %></td>
        </tr>
        <tr>
            <td style='padding:5px'>教师：</td>
            <td style='padding:5px'><%= info.getTeacher() %></td>
        </tr>
    </table>
    <form id="quit" method="post" action="/student/quit_course/<%=info.getId()%>"></form>
    <div align="center">
        <button onclick="sure('确定从<%= info.getName() %>&nbsp;&nbsp;<%= info.getTerm() %>退出选课吗？', 'quit')">退课</button>
    </div>
</div>
</body>
</html>
