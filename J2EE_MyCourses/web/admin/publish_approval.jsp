<%--
  Created by IntelliJ IDEA.
  User: 43796
  Date: 2019/3/1
  Time: 16:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" import="edu.nju.wsql.beans.TermCourseInfoBean" %>
<html>
<jsp:useBean id="info" type="edu.nju.wsql.beans.PublishApprovalListBean" scope="request" />
<head>
    <title>审批课程发布信息</title>
    <%@include file="/navbar/style.jsp"%>
</head>
<body>
<%@include file="/navbar/admin_navbar.jsp"%>
<%@include file="/info_page/alert.jsp"%>
<div style="margin-left:25%;padding:1px 16px;">
    <h1 align=center>课程发布审批</h1>
    <table border="1" align="center">
        <tr>
            <th style='padding:5px'>课程名称</th>
            <th style='padding:5px'>发布学期</th>
            <th style='padding:5px'>申请教师</th>
            <th style='padding:5px'>操作</th>
        </tr>
        <%
            for (TermCourseInfoBean bean: info.getTermCourses()) {
        %>
        <tr>
            <td style='padding:5px'>
                <%= bean.getName() %>
            </td>
            <td style='padding:5px'>
                <%= bean.getTerm() %>
            </td>
            <td style='padding:5px'>
                <%= bean.getPublisher() %>
            </td>
            <td style='padding:5px'>
                <a href="/admin/approve_publish/<%= bean.getId() %>">审批</a>
            </td>
        </tr>
        <%
            }
        %>
    </table>
</div>
</body>
</html>
