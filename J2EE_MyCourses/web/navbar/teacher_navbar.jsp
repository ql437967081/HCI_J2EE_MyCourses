<%--
  Created by IntelliJ IDEA.
  User: 43796
  Date: 2019/2/7
  Time: 21:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%
    String home_active = "";
    if (request.getAttribute("home_active") != null)
        home_active = "class=\"active\"";
    String info_active = "";
    if (request.getAttribute("info_active") != null)
        info_active = "class=\"active\"";
    String new_course_active = "";
    if (request.getAttribute("new_course_active") != null)
        new_course_active = "class=\"active\"";
    String publish_course_active = "";
    if (request.getAttribute("publish_course_active") != null)
        publish_course_active = "class=\"active\"";
    String my_courses_active = "";
    if (request.getAttribute("my_courses_active") != null)
        my_courses_active = "class=\"active\"";
%>
<ul>
    <li><a <%= home_active %> href="/teacher">主页</a></li>
    <li><a <%= info_active %> href="/teacher/info">个人信息</a></li>
    <li><a <%= new_course_active %> href="/teacher/new_course">创建课程</a></li>
    <li><a <%= publish_course_active %> href="/teacher/publish_course">发布课程</a></li>
    <li><a <%= my_courses_active %> href="/teacher/my_courses">我的课程</a></li>
    <li><a href="/login/logout">登出</a></li>
</ul>
