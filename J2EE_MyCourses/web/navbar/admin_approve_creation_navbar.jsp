<%--
  Created by IntelliJ IDEA.
  User: 43796
  Date: 2019/2/28
  Time: 11:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<ul>
    <li><a href="/admin">主页</a></li>
    <li><a href="/admin/course_create_approval">审批其它课程</a></li>
    <li><a class="active" href="/admin/approve_creation/<%= request.getAttribute("id") %>">当前课程审批中...</a></li>
    <li><a href="/login/logout">登出</a></li>
</ul>

