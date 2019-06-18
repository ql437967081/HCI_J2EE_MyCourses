<%--
  Created by IntelliJ IDEA.
  User: 43796
  Date: 2019/2/27
  Time: 14:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%
    long course_id = (long) request.getAttribute("course_id");
    long id = (long) request.getAttribute("term_course_id");
    String term_course_active = "";
    if (request.getAttribute("term_course_active") != null)
        term_course_active = "class=\"active\"";
    String publish_homework_active = "";
    if (request.getAttribute("publish_homework_active") != null)
        publish_homework_active = "class=\"active\"";
    String download_homework_active = "";
    if (request.getAttribute("download_homework_active") != null)
        download_homework_active = "class=\"active\"";
    String group_email_active = "";
    if (request.getAttribute("group_email_active") != null)
        group_email_active = "class=\"active\"";
    String publish_grade_active = "";
    if (request.getAttribute("publish_grade_active") != null)
        publish_grade_active = "class=\"active\"";
%>
<ul>
    <li><a href="/teacher">主页</a></li>
    <li><a href="/teacher/course/<%= course_id %>">课程首页</a></li>
    <li><a <%= term_course_active %> href="/teacher/course/<%= course_id %>/term_course/<%= id %>">学期信息</a></li>
    <li><a <%= publish_homework_active %> href="/teacher/course/<%= course_id %>/publish_homework/<%= id %>">发布作业</a></li>
    <li><a <%= group_email_active %> href="/teacher/course/<%= course_id %>/group_email/<%= id %>">群发邮件</a></li>
    <li><a <%= download_homework_active %> href="/teacher/course/<%= course_id %>/download_homework/<%= id %>">下载作业</a></li>
    <li><a <%= publish_grade_active %> href="/teacher/course/<%= course_id %>/publish_grade/<%= id %>">发布成绩</a></li>
    <li><a href="/teacher/course/<%= course_id %>/forum">论坛</a></li>
    <li><a href="/login/logout">登出</a></li>
</ul>
