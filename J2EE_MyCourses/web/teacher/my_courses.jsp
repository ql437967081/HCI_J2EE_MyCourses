<%--
  Created by IntelliJ IDEA.
  User: 43796
  Date: 2019/2/26
  Time: 22:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" import="java.util.Map" %>
<html>
<jsp:useBean id="info" type="edu.nju.wsql.beans.CourseListBean" scope="request" />
<head>
    <title>我创建的课程</title>
    <%@include file="/navbar/style.jsp"%>
</head>
<body>
<%@include file="/navbar/teacher_navbar.jsp"%>
<div style="margin-left:25%;padding:1px 16px;">
    <h1 align=center>我的课程</h1>
    <table align="center">
        <%
            for (Map.Entry entry: info.getCourses().entrySet()) {
        %>
        <tr>
            <td style='padding:5px'>
                <a href="/teacher/course/<%= entry.getKey() %>"><%= entry.getValue() %></a>
            </td>
        </tr>
        <%
            }
        %>
    </table>
</div>
</body>
</html>
