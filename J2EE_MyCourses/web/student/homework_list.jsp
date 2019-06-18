<%--
  Created by IntelliJ IDEA.
  User: 43796
  Date: 2019/3/6
  Time: 14:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" import="edu.nju.wsql.beans.HomeworkBean" %>
<html>
<jsp:useBean id="info" type="edu.nju.wsql.beans.CourseHomeworkListBean" scope="request" />
<head>
    <title>课程作业</title>
    <%@include file="/navbar/style.jsp"%>
</head>
<body>
<%@include file="/navbar/student_course_navbar.jsp"%>
<div style="margin-left:25%;padding:1px 16px;">
    <h1 align=center>作业</h1>
    <table border="1" align="center">
        <tr>
            <th style='padding:5px'>标题</th>
            <th style='padding:5px'>截止时间</th>
            <th style='padding:5px'>状态</th>
            <th style='padding:5px'>操作</th>
        </tr>
        <%
            for (HomeworkBean bean: info.getHomeworkBeans()) {
        %>
        <tr>
            <td style='padding:5px'><%= bean.getTitle() %></td>
            <td style='padding:5px'><%= bean.getDdl() %></td>
            <td style='padding:5px'><%= bean.getSubmitId() == -1 ? "未提交" : "已提交" %></td>
            <td style='padding:5px'>
                <a href="/student/homework/<%= select_course_id %>/<%= bean.getId() %>">进入</a>
            </td>
        </tr>
        <%
            }
        %>
    </table>
</div>
</body>
</html>
