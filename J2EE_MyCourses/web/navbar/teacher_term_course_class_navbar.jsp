<%--
  Created by IntelliJ IDEA.
  User: 43796
  Date: 2019/3/12
  Time: 11:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%
    long course_id = (long) request.getAttribute("course_id");
    long id = (long) request.getAttribute("term_course_id");
    long course_class_id = (long) request.getAttribute("course_class_id");
%>
<ul>
    <li><a href="/teacher">主页</a></li>
    <li><a href="/teacher/course/<%= course_id %>">课程首页</a></li>
    <li><a href="/teacher/course/<%= course_id %>/term_course/<%= id %>">学期信息</a></li>
    <li><a class="active" href="/teacher/course/<%= course_id %>/term_course/<%= id %>/<%= course_class_id %>">班级选课详情</a></li>
    <li><a href="/login/logout">登出</a></li>
</ul>
