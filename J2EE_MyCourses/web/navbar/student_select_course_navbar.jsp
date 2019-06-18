<%--
  Created by IntelliJ IDEA.
  User: 43796
  Date: 2019/3/3
  Time: 22:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<ul>
    <li><a href="/student">主页</a></li>
    <li><a href="/student/courses">课程</a></li>
    <li><a class="active" href="/student/select_course/<%= request.getAttribute("id") %>">选课中...</a></li>
    <li><a href="/login/logout">登出</a> </li>
</ul>
