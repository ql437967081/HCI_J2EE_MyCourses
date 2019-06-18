<%--
  Created by IntelliJ IDEA.
  User: 43796
  Date: 2019/3/11
  Time: 0:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%
    long select_course_id = (long) request.getAttribute("select_course_id");
    long id = (long) request.getAttribute("topic_id");
%>
<ul>
    <li><a href="/student">主页</a></li>
    <li><a href="/student/course/<%= select_course_id %>">课程首页</a></li>
    <li><a href="/student/forum/<%= select_course_id %>">返回论坛</a></li>
    <li><a class="active" href="/student/forum/<%= select_course_id %>/topic/<%= id %>">当前话题</a></li>
    <li><a href="/login/logout">登出</a></li>
</ul>
