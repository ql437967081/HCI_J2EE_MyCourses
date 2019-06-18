<%--
  Created by IntelliJ IDEA.
  User: 43796
  Date: 2019/3/4
  Time: 20:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%
    long select_course_id = (long) request.getAttribute("select_course_id");
    String course_home_active = "";
    if (request.getAttribute("course_home_active") != null)
        course_home_active = "class=\"active\"";
    String courseware_active = "";
    if (request.getAttribute("courseware_active") != null)
        courseware_active = "class=\"active\"";
    String homework_list_active = "";
    if (request.getAttribute("homework_list_active") != null)
        homework_list_active = "class=\"active\"";
    String grade_active = "";
    if (request.getAttribute("grade_active") != null)
        grade_active = "class=\"active\"";
    String forum_active = "";
    if (request.getAttribute("forum_active") != null)
        forum_active = "class=\"active\"";
%>
<ul>
    <li><a href="/student">主页</a></li>
    <li><a <%= course_home_active %> href="/student/course/<%= select_course_id %>">课程首页</a></li>
    <li><a <%= homework_list_active %> href="/student/homework_list/<%= select_course_id %>">我的作业</a></li>
    <li><a <%= grade_active %> href="/student/grade/<%= select_course_id %>">成绩</a></li>
    <li><a <%= courseware_active %> href="/student/coursewares/<%= select_course_id %>">课件</a></li>
    <li><a <%= forum_active %> href="/student/forum/<%= select_course_id %>">论坛</a></li>
    <li><a href="/login/logout">登出</a> </li>
</ul>
