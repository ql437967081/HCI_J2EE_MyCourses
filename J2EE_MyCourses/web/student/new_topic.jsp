<%--
  Created by IntelliJ IDEA.
  User: 43796
  Date: 2019/3/10
  Time: 16:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>课程论坛：添加一个新话题</title>
    <%@include file="/navbar/style.jsp"%>
    <%@include file="/info_page/confirm.jsp"%>
    <script type="text/javascript">
        function newTopic() {
            if (document.getElementById('title').value.length == 0) {
                alert('请输入主题');
                return;
            }
            console.log(document.getElementById('content').value);
            if (document.getElementById('content').value.length == 0) {
                alert('请输入内容');
                return;
            }
            sure('确定添加这个新话题到课程论坛上吗', 'send');
        }
    </script>
</head>
<body>
<%@include file="/navbar/student_new_topic_navbar.jsp"%>
<div style="margin-left:25%;padding:1px 16px;">
    <h1 align=center>添加一个新话题</h1>
    <form id="send" method="post" action="/student/forum/<%= select_course_id %>/new_topic">
        <table align="center">
            <tr>
                <td style='padding:5px'>主题：</td>
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
        <button onclick="newTopic()">发布</button>
    </div>
</div>
</body>
</html>
