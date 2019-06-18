<%--
  Created by IntelliJ IDEA.
  User: 43796
  Date: 2019/2/28
  Time: 11:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" import="edu.nju.wsql.beans.CoursewareBean" %>
<html>
<jsp:useBean id="info" type="edu.nju.wsql.beans.CourseBean" scope="request" />
<head>
    <title>审批课程创建信息：<%= info.getName() %></title>
    <%@include file="/navbar/style.jsp"%>
    <%@include file="/info_page/confirm.jsp"%>
</head>
<body>
<%@include file="/navbar/admin_approve_creation_navbar.jsp"%>
<div style="margin-left:25%;padding:1px 16px;">
    <h1 align=center>课程创建审批：<%= info.getName() %></h1>
    <form id="approve" method="post" action="/admin/approve_creation/<%= info.getId() %>"></form>
    <form id="reject" method="post" action="/admin/reject_creation/<%= info.getId() %>"></form>
    <table align="center">
        <tr>
            <td style='padding:5px'>创建者：</td>
            <td style='padding:5px'><%= info.getCreator() %></td>
        </tr>
        <tr>
            <td style='padding:5px'>课件：</td>
            <td style='padding:5px'>
                <%
                    for (CoursewareBean ware: info.getCoursewares()) {
                        long wareId = ware.getId();
                        String wareName = ware.getName();
                %>
                <p>
                    <a id="<%= wareId %>" href="<%= ware.getLocation() %>" target="_blank" download="<%= wareName %>"><%= wareName %></a>
                </p>
                <%
                    }
                %>
            </td>
        </tr>
        <tr>
            <td style='padding:5px' colspan='2' align='center'>
                <button onclick="sure('确定审批通过此课程的创建吗？', 'approve')">审批通过</button>
                &nbsp;&nbsp;&nbsp;&nbsp;
                <button onclick="sure('确定拒绝通过此课程的创建吗？', 'reject')">拒绝通过</button>
            </td>
        </tr>
    </table>
</div>
</body>
</html>
