<%--
  Created by IntelliJ IDEA.
  User: 43796
  Date: 2019/3/5
  Time: 20:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" import="edu.nju.wsql.beans.HomeworkBean" %>
<html>
<jsp:useBean id="info" type="edu.nju.wsql.beans.CourseHomeworkListBean" scope="request" />
<head>
    <title>下载学生课程作业</title>
    <%@include file="/navbar/style.jsp"%>
</head>
<body>
<%@include file="/navbar/teacher_term_course_navbar.jsp"%>
<%@include file="/info_page/alert.jsp"%>
<div style="margin-left:25%;padding:1px 16px;">
    <h1 align="center">下载作业</h1>
    <table border="1" align="center">
        <tr>
            <th style='padding:5px'>标题</th>
            <th style='padding:5px'>内容</th>
            <th style='padding:5px'>截止时间</th>
            <th style='padding:5px'>大小限制</th>
            <th style='padding:5px'>文件类型</th>
            <th style='padding:5px'>提交人数</th>
            <th style='padding:5px'>下载作业</th>
        </tr>
        <%
            for (HomeworkBean bean: info.getHomeworkBeans()) {
        %>
        <tr>
            <td style='padding:5px'><%= bean.getTitle() %></td>
            <td style='padding:5px'><%= bean.getContent() %></td>
            <td style='padding:5px'><%= bean.getDdl() %></td>
            <td style='padding:5px'><%= bean.getFileMaxSize() %>MB</td>
            <td style='padding:5px'><%= bean.getFileType() %></td>
            <td style='padding:5px'><%= bean.getSubmitNum() %>/<%= bean.getSelectCourseNum() %></td>
            <td style='padding:5px'>
                <%
                    long requestId = bean.getId();
                %>
                <form id="<%= requestId %>" method="get" target="_blank" action="/teacher/course/<%= course_id %>/download_homework/<%= id %>/<%= requestId %>"></form>
                <button onclick="document.getElementById('<%= requestId %>').submit()">下载</button>
            </td>
        </tr>
        <%
            }
        %>
    </table>
</div>
</body>
</html>
