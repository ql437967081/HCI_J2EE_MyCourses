<%--
  Created by IntelliJ IDEA.
  User: 43796
  Date: 2019/3/11
  Time: 0:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" import="edu.nju.wsql.beans.PostBean" %>
<jsp:useBean id="info" type="edu.nju.wsql.beans.TopicBean" scope="request" />
<html>
<head>
    <title>课程论坛：<%= info.getTitle() %></title>
    <%@include file="/navbar/style.jsp"%>
    <%@include file="/info_page/confirm.jsp"%>
    <script type="text/javascript">
        function say() {
            console.log(document.getElementById('content').value);
            if (document.getElementById('content').value.length == 0) {
                alert('请输入内容');
                return;
            }
            sure('确定在当前主题上发言吗', 'send');
        }
    </script>
</head>
<body>
<%@include file="/navbar/student_topic_navbar.jsp"%>
<%@include file="/info_page/alert.jsp"%>
<div style="margin-left:25%;padding:1px 16px;">
    <h1 align=center><%= info.getTitle() %></h1>
    <table border="1" align="center">
        <%
            for (PostBean bean: info.getPosts()) {
        %>
        <tr>
            <td style='padding:5px' colspan="2" align="center">
                <span>
                    <img src="<%= bean.getPosterPortrait() %>" width="35" height="35">
                </span>
                <span>
                    由&nbsp;<%= bean.getPoster() %>&nbsp;发表于&nbsp;<%= bean.getTime() %>
                    <br><br>
                    <%= bean.getContent() %>
                </span>
            </td>
        </tr>
        <%
            }
        %>
        <tr>
            <td style='padding:5px'>我也来说两句：</td>
            <td style='padding:5px'>
                <form id="send" method="post" action="/student/forum/<%= select_course_id %>/topic/<%= id %>">
                    <textarea id="content" name="content"></textarea>
                </form>
            </td>
        </tr>
    </table>
    <div align="center">
        <button onclick="say()">发布</button>
    </div>
</div>
</body>
</html>

