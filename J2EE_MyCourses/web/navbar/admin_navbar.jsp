<%--
  Created by IntelliJ IDEA.
  User: 43796
  Date: 2019/2/28
  Time: 10:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%
    String home_active = "";
    if (request.getAttribute("home_active") != null)
        home_active = "class=\"active\"";
    String course_create_approval_active = "";
    if (request.getAttribute("course_create_approval_active") != null)
        course_create_approval_active = "class=\"active\"";
    String course_publish_approval_active = "";
    if (request.getAttribute("course_publish_approval_active") != null)
        course_publish_approval_active = "class=\"active\"";
    String teacher_info_active = "";
    if (request.getAttribute("teacher_info_active") != null)
        teacher_info_active = "class=\"active\"";
    String student_info_active = "";
    if (request.getAttribute("student_info_active") != null)
        student_info_active = "class=\"active\"";
%>
<ul>
    <li><a <%= home_active %> href="/admin">主页</a></li>
    <li><a <%= course_create_approval_active %> href="/admin/course_create_approval">课程创建审批</a></li>
    <li><a <%= course_publish_approval_active %> href="/admin/course_publish_approval" >开课信息审批</a></li>
    <li><a <%= teacher_info_active %> href="/admin/teacher_info">教师统计信息</a></li>
    <li><a <%= student_info_active %> href="/admin/student_info">学生统计信息</a></li>
    <li><a href="/login/logout">登出</a></li>
</ul>
