<%--
  Created by IntelliJ IDEA.
  User: 43796
  Date: 2019/3/5
  Time: 14:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" import="java.time.LocalDate" %>
<html>
<head>
    <title>发布课程作业</title>
    <%@include file="/navbar/style.jsp"%>
</head>
<body>
<%@include file="/navbar/teacher_term_course_navbar.jsp"%>
<div style="margin-left:25%;padding:1px 16px;">
    <h1 align="center">发布作业</h1>
    <form method="post" action="/teacher/course/<%= course_id %>/publish_homework/<%= id %>">
        <table align="center">
            <tr>
                <td style='padding:5px'>标题：</td>
                <td style='padding:5px'>
                    <input type='text' name='title' value=''>
                </td>
            </tr>
            <tr>
                <td style='padding:5px'>内容：</td>
                <td style='padding:5px'>
                    <textarea name="content"></textarea>
                </td>
            </tr>
            <tr>
                <td style='padding:5px'>截止时间：</td>
                <td style='padding:5px'>
                    <%
                        LocalDate tomorrow = LocalDate.now().plusDays(1);
                        String minTime = tomorrow.toString() + "T00:00";
                    %>
                    <input type="datetime-local" min="<%=minTime%>" name="ddl">
                </td>
            </tr>
            <tr>
                <td style='padding:5px'>提交作业大小限制：</td>
                <td style='padding:5px'>
                    <input type='number' min="1" max="100" name='file_max_size'>MB
                </td>
            </tr>
            <tr>
                <td style='padding:5px'>提交作业文件类型：</td>
                <td style='padding:5px'>
                    <input type='text' name='file_type' value=''>
                </td>
            </tr>
            <tr>
                <td style='padding:5px' colspan='2' align='center'>
                    <input type='submit' value='发布作业'>
                </td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>
