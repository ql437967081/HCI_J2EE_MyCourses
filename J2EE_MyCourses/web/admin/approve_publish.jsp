<%--
  Created by IntelliJ IDEA.
  User: 43796
  Date: 2019/3/2
  Time: 20:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" import="edu.nju.wsql.beans.CourseClassBean" %>
<html>
<jsp:useBean id="info" type="edu.nju.wsql.beans.TermCourseInfoBean" scope="request" />
<head>
    <title>审批课程发布信息：<%= info.getName() %></title>
    <%@include file="/navbar/style.jsp"%>
    <%@include file="/info_page/confirm.jsp"%>
</head>
<body>
<%@include file="/navbar/admin_approve_publish_navbar.jsp"%>
<div style="margin-left:25%;padding:1px 16px;">
    <h1 align=center>课程发布审批：<%= info.getName() %></h1>
    <h2 align="center"><%= info.getTerm() %></h2>
    <h3 align="center">发布者：<%= info.getPublisher() %></h3>
    <form id="approve" method="post" action="/admin/approve_publish/<%= info.getId() %>"></form>
    <form id="reject" method="post" action="/admin/reject_publish/<%= info.getId() %>"></form>
    <table border="1" align="center">
        <tr>
            <th style='padding:5px'>班级</th>
            <th style='padding:5px'>限选人数</th>
        </tr>
        <%
            for (CourseClassBean classBean: info.getClasses()) {
        %>
        <tr>
            <td style='padding:5px'><%= classBean.getClassId() %></td>
            <td style='padding:5px'><%= classBean.getLimitNum() %></td>
        </tr>
        <%
            }
        %>
        <tr>
            <td style='padding:5px' colspan='2' align='center'>
                <button onclick="sure('确定审批通过此课程的发布吗？', 'approve')">审批通过</button>
                &nbsp;&nbsp;&nbsp;&nbsp;
                <button onclick="sure('确定拒绝通过此课程的发布吗？', 'reject')">拒绝通过</button>
            </td>
        </tr>
    </table>
</div>
</body>
</html>
