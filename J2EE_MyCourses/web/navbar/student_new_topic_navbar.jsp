<%--
  Created by IntelliJ IDEA.
  User: 43796
  Date: 2019/3/10
  Time: 16:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%
    long select_course_id = (long) request.getAttribute("select_course_id");
%>
<ul>
    <li><a href="/student">主页</a></li>
    <li><a href="/student/course/<%= select_course_id %>">课程首页</a></li>
    <li><a href="/student/forum/<%= select_course_id %>">返回论坛</a></li>
    <li><a class="active" href="/student/forum/<%= select_course_id %>/new_topic">添加一个新话题</a></li>
    <li><a href="/login/logout">登出</a></li>
</ul>
