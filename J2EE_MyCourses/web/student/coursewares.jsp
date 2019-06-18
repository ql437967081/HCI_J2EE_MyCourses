<%--
  Created by IntelliJ IDEA.
  User: 43796
  Date: 2019/3/5
  Time: 10:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" import="edu.nju.wsql.beans.CoursewareBean" %>
<html>
<jsp:useBean id="info" type="edu.nju.wsql.beans.CourseBean" scope="request" />
<head>
    <title>课程课件</title>
    <%@include file="/navbar/style.jsp"%>
    <%@include file="/info_page/confirm.jsp"%>
</head>
<body>
<%@include file="/navbar/student_course_navbar.jsp"%>
<div style="margin-left:25%;padding:1px 16px;">
    <h1 align=center>课件</h1>
    <table align="center">
        <tr>
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
    </table>
</div>
</body>
</html>
