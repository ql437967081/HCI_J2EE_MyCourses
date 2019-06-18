<%--
  Created by IntelliJ IDEA.
  User: 43796
  Date: 2019/3/11
  Time: 11:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%
    long id = (long) request.getAttribute("course_id");
%>
<ul>
    <li><a href="/student">主页</a></li>
    <li><a href="/teacher/course/<%= id %>">课程首页</a></li>
    <li><a href="/teacher/course/<%= id %>/forum">返回论坛</a></li>
    <li><a class="active" href="/teacher/course/<%= id %>/forum/new_topic">添加一个新话题</a></li>
    <li><a href="/login/logout">登出</a></li>
</ul>
