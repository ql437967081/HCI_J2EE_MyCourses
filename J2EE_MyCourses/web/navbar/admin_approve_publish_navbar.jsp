<%--
  Created by IntelliJ IDEA.
  User: 43796
  Date: 2019/3/2
  Time: 20:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<ul>
    <li><a href="/admin">主页</a></li>
    <li><a href="/admin/course_publish_approval">审批其它课程发布</a></li>
    <li><a class="active" href="/admin/approve_publish/<%= request.getAttribute("id") %>">当前课程发布审批中...</a></li>
    <li><a href="/login/logout">登出</a></li>
</ul>

