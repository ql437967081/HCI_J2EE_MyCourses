<%--
  Created by IntelliJ IDEA.
  User: 43796
  Date: 2019/3/7
  Time: 14:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>给学生群发邮件</title>
    <%@include file="/navbar/style.jsp"%>
    <%@include file="/info_page/confirm.jsp"%>
    <script type="text/javascript">
        function sendEmail() {
            if (document.getElementById('title').value.length == 0) {
                alert('请输入邮件标题');
                return;
            }
            console.log(document.getElementById('content').value);
            if (document.getElementById('content').value.length == 0) {
                alert('请输入邮件内容');
                return;
            }
            sure('确定发送邮件吗', 'send');
        }
    </script>
</head>
<body>
<%@include file="/navbar/teacher_term_course_navbar.jsp"%>
<%@include file="/info_page/alert.jsp"%>
<div style="margin-left:25%;padding:1px 16px;">
    <h1 align="center">群发邮件</h1>
    <form id="send" method="post" action="/teacher/course/<%= course_id %>/group_email/<%= id %>">
        <table align="center">
            <tr>
                <td style='padding:5px'>标题：</td>
                <td style='padding:5px'>
                    <input id="title" type="text" name="title">
                </td>
            </tr>
            <tr>
                <td style='padding:5px'>内容：</td>
                <td style='padding:5px'>
                    <textarea id="content" name="content"></textarea>
                </td>
            </tr>
        </table>
    </form>
    <div align="center">
        <button onclick="sendEmail()">发送</button>
    </div>
</div>
</body>
</html>
