<%--
  Created by IntelliJ IDEA.
  User: 43796
  Date: 2019/3/10
  Time: 15:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" import="edu.nju.wsql.beans.TopicSimpleBean" %>
<html>
<jsp:useBean id="info" type="edu.nju.wsql.beans.TopicListBean" scope="request" />
<head>
    <title>课程论坛</title>
    <%@include file="/navbar/style.jsp"%>
</head>
<body>
<%@include file="/navbar/teacher_course_navbar.jsp"%>
<%@include file="/info_page/alert.jsp"%>
<div style="margin-left:25%;padding:1px 16px;">
    <h1 align=center>论坛</h1>
    <div align="center"><a href="/teacher/course/<%= id %>/forum/new_topic">添加一个新话题</a></div>
    <br><br>
    <table border="1" align="center">
        <tr>
            <th style='padding:5px'>话题</th>
            <th style='padding:5px'>发起人</th>
            <th style='padding:5px'>帖子数</th>
            <th style='padding:5px'>最后回帖</th>
        </tr>
        <%
            for (TopicSimpleBean topic: info.getTopicList()) {
        %>
        <tr>
            <td style='padding:5px'>
                <a href="/teacher/course/<%= id %>/forum/topic/<%= topic.getId() %>"><%= topic.getTitle() %></a>
            </td>
            <td style='padding:5px'>
                <img src="<%= topic.getFirstPosterPortrait() %>" width="35" height="35">
                <%= topic.getFirstPoster() %>
            </td>
            <td style='padding:5px'><%= topic.getNum() %></td>
            <td style='padding:5px'>
                <span>
                    <img src="<%= topic.getLastPosterPortrait() %>" width="35" height="35">
                </span>
                <span>
                    <%= topic.getLastPoster() %>
                    <br>
                    <%= topic.getLastTime() %>
                </span>
            </td>
        </tr>
        <%
            }
        %>
    </table>
</div>
</body>
</html>
