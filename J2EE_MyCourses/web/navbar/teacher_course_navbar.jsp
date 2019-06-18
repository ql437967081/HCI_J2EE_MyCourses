<%--
  Created by IntelliJ IDEA.
  User: 43796
  Date: 2019/2/27
  Time: 14:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%
    long id = (long) request.getAttribute("course_id");
    String course_home_active = "";
    if (request.getAttribute("course_home_active") != null)
        course_home_active = "class=\"active\"";
    String forum_active = "";
    if (request.getAttribute("forum_active") != null)
        forum_active = "class=\"active\"";
%>
<ul>
    <li><a href="/teacher">主页</a></li>
    <li><a <%= course_home_active %> href="/teacher/course/<%= id %>">课程首页</a></li>
    <li><a <%= forum_active %> href="/teacher/course/<%= id %>/forum">论坛</a></li>
    <li><a href="/teacher/my_courses">我的课程</a></li>
    <li><a href="/login/logout">登出</a></li>
</ul>