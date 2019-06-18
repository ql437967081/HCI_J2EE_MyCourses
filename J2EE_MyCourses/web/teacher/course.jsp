<%--
  Created by IntelliJ IDEA.
  User: 43796
  Date: 2019/2/11
  Time: 11:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" import="edu.nju.wsql.beans.CoursewareBean, edu.nju.wsql.beans.CourseTermBean" %>
<html>
<jsp:useBean id="info" type="edu.nju.wsql.beans.CourseBean" scope="request" />
<head>
    <title>课程：<%= info.getName() %></title>
    <%@include file="/navbar/style.jsp"%>
    <%@include file="/info_page/confirm.jsp"%>
    <%@include file="add_delete_courseware.jsp"%>
    <script type="text/javascript">
        function removeCourseware(wareId, wareName) {
            var coursewares = document.getElementById("coursewares");
            if (coursewares.childElementCount > 0) {
                alert('请先提交或删除添加的课件');
                return;
            }
            sure('确定删除课件：'+ wareName +'吗？', 'ware' + wareId);
        }
    </script>
</head>
<body>
<%@include file="/navbar/teacher_course_navbar.jsp"%>
<div style="margin-left:25%;padding:1px 16px;">
    <h1 align=center><%= info.getName() %></h1>
    <span style="width: 70%;padding:1px 2.5%;float: left;">
    <table align="center">
        <tr>
            <td style='padding:5px'>创建者：</td>
            <td style='padding:5px'><%= info.getCreator() %></td>
        </tr>
        <tr>
            <td style='padding:5px'>课件：</td>
            <td style='padding:5px'>
                <%
                    for (CoursewareBean ware: info.getCoursewares()) {
                        long wareId = ware.getId();
                        String wareName = ware.getName();
                %>
                <p>
                    <a id="<%= wareId %>" href="<%= ware.getLocation() %>" target="_blank" download="<%= wareName %>"><%= wareName %></a>
                    <button onclick="removeCourseware('<%= wareId %>', '<%= wareName %>')">x</button>
                    <form id="ware<%= wareId %>" method="post" action="/teacher/course/<%= info.getId() %>/remove/<%= wareId %>"></form>
                </p>
                <%
                    }
                %>
                <form method="post" action="/teacher/course/<%= info.getId() %>/add_wares" enctype="multipart/form-data">
                    <div id="coursewares"></div>
                    <input type="button" value="添加.." onclick="addCourseware()">
                    <input type="submit" value="提交">
                </form>
            </td>
        </tr>
    </table>
    </span>
    <span style="width: 20%;padding:1px 2.5%;float: right;">
        <table border="1" align="center">
        <tr>
            <th style='padding:5px'>
                学期
            </th>
        </tr>
        <%
            for (CourseTermBean bean: info.getTerms()) {
        %>
        <tr>
            <td style='padding:5px'>
                <a href="/teacher/course/<%= info.getId() %>/term_course/<%= bean.getId() %>"><%= bean.getTerm() %></a>
            </td>
        </tr>
        <%
            }
        %>
    </table>
    </span>
</div>
</body>
</html>
