<%--
  Created by IntelliJ IDEA.
  User: 43796
  Date: 2019/3/6
  Time: 14:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<jsp:useBean id="info" type="edu.nju.wsql.beans.HomeworkBean" scope="request" />
<head>
    <title>课程作业</title>
    <%@include file="/navbar/style.jsp"%>
    <%@include file="/info_page/confirm.jsp"%>
    <script type="text/javascript">
        function fileValid(input, maxSize) {
            var submitFile = document.getElementById(input);
            var file = submitFile.files[0];
            if (file == null) {
                alert('请选择文件');
                return;
            }
            var fileSize = (file.size / 1024).toFixed(0);
            if (fileSize > maxSize * 1024) {
                alert('文件大小限制：' + maxSize + 'MB，你的文件大小：' + fileSize / 1024 + "MB");
                return;
            }
            sure('确定保存作业吗？', 'submit_homework');
        }
    </script>
</head>
<body>
<%@include file="/navbar/student_homework_navbar.jsp"%>
<%@include file="/info_page/alert.jsp"%>
<div style="margin-left:25%;padding:1px 16px;">
    <h1 align=center>作业</h1>
    <table align="center">
        <tr>
            <td style='padding:5px'>标题：</td>
            <td style='padding:5px'><%= info.getTitle() %></td>
        </tr>
        <tr>
            <td style='padding:5px'>内容：</td>
            <td style='padding:5px'>
                <textarea readonly><%= info.getContent() %></textarea>
            </td>
        </tr>
        <tr>
            <td style='padding:5px'>截止时间：</td>
            <td style='padding:5px'><%= info.getDdl() %></td>
        </tr>
        <tr>
            <td style='padding:5px'>提交作业大小限制：</td>
            <td style='padding:5px'>
                <%= info.getFileMaxSize() %>MB
            </td>
        </tr>
        <tr>
            <td style='padding:5px'>提交作业文件类型：</td>
            <td style='padding:5px'><%= info.getFileType() %></td>
        </tr>
        <tr>
            <td style='padding:5px'>已交的作业：</td>
            <%
                String submitName = info.getSubmitName();
                String td1;
                if (submitName == null)
                    td1 = "无";
                else
                    td1 = "<a href='" + info.getLocation() + "' target='_blank' download='" + submitName + "'>" + submitName + "</a>";
            %>
            <td style='padding:5px'>
                <%= td1 %>
            </td>
        </tr>
        <tr>
            <td style='padding:5px'>提交更改：</td>
            <td style='padding:5px'>
                <form id="submit_homework" method="post" action="/student/homework/<%= select_course_id %>/<%= id %>" enctype="multipart/form-data">
                    <input id="submit_file" type="file" name="file">
                </form>
            </td>
        </tr>
        <tr>
            <td style='padding:5px' colspan='2' align='center'>
                <button onclick="fileValid('submit_file', <%= info.getFileMaxSize() %>)">提交更改的作业</button>
            </td>
        </tr>
    </table>
</div>
</body>
</html>
