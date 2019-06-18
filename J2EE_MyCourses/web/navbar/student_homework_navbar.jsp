<%--
  Created by IntelliJ IDEA.
  User: 43796
  Date: 2019/3/6
  Time: 14:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%
    long select_course_id = (long) request.getAttribute("select_course_id");
    long id = (long) request.getAttribute("id");
%>
<ul>
    <li><a href="/student">主页</a></li>
    <li><a href="/student/course/<%= select_course_id %>">课程首页</a></li>
    <li><a href="/student/homework_list/<%= select_course_id %>">我的作业</a></li>
    <li><a class="active" href="/student/homework/<%= select_course_id %>/<%= id %>">当前作业</a></li>
    <li><a href="/login/logout">登出</a> </li>
</ul>
