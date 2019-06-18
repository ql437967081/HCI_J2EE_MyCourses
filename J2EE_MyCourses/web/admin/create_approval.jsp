<%--
  Created by IntelliJ IDEA.
  User: 43796
  Date: 2019/2/28
  Time: 10:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" import="edu.nju.wsql.beans.CourseBean" %>
<html>
<jsp:useBean id="info" type="edu.nju.wsql.beans.CreateApprovalListBean" scope="request" />
<head>
    <title>审批课程创建信息</title>
    <%@include file="/navbar/style.jsp"%>
</head>
<body>
<%@include file="/navbar/admin_navbar.jsp"%>
<%@include file="/info_page/alert.jsp"%>
<div style="margin-left:25%;padding:1px 16px;">
    <h1 align=center>课程创建审批</h1>
    <table border="1" align="center">
        <tr>
            <th style='padding:5px'>课程名称</th>
            <th style='padding:5px'>创建申请教师</th>
            <th style='padding:5px'>操作</th>
        </tr>
        <%
            for (CourseBean bean: info.getCourseBeans()) {
        %>
        <tr>
            <td style='padding:5px'>
                <%= bean.getName() %>
            </td>
            <td style='padding:5px'>
                <%= bean.getCreator() %>
            </td>
            <td style='padding:5px'>
                <a href="/admin/approve_creation/<%= bean.getId() %>">审批</a>
            </td>
        </tr>
        <%
            }
        %>
    </table>
</div>
</body>
</html>
