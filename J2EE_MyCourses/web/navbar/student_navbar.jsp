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
    String courses_active = "";
    if (request.getAttribute("courses_active") != null)
        courses_active = "class=\"active\"";
%>
<ul>
    <li><a <%= home_active %> href="/student">主页</a></li>
    <li><a <%= info_active %> href="/student/info">个人信息</a></li>
    <li><a <%= courses_active %> href="/student/courses">课程</a></li>
    <li><a href="/login/logout">登出</a> </li>
</ul>
