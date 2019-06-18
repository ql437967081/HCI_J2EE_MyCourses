<%--
  Created by IntelliJ IDEA.
  User: 43796
  Date: 2019/2/23
  Time: 12:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" import="edu.nju.wsql.beans.CourseBean" %>
<%@ page import="edu.nju.wsql.model.enums.Season" %>
<html>
<jsp:useBean id="info" type="edu.nju.wsql.beans.PublishCourseBean" scope="request" />
<head>
    <title>发布新学期课程</title>
    <%@include file="/navbar/style.jsp"%>
    <script type="text/javascript">
        function addCourseClasses() {
            var courseClasses = document.getElementById('course_classes');
            var tmp = document.createElement('p');
            var classId = courseClasses.childElementCount + 1;
            tmp.id = '' + classId;
            tmp.innerHTML = '班号：' + classId + '&nbsp;&nbsp;限选人数：<input type="number" min="1" name="' + classId + '" value="50" size="3">';
            courseClasses.appendChild(tmp);
        }
        function deleteCourseClasses() {
            document.getElementById('course_classes').lastElementChild.remove();
        }
    </script>
</head>
<body>
<%@include file="/navbar/teacher_navbar.jsp"%>
<%@include file="/info_page/alert.jsp"%>
<div style="margin-left:25%;padding:1px 16px;">
    <h1 align=center>发布课程</h1>
    <form method="post" action="/teacher/publish_course">
        <table align="center">
            <tr>
                <td style='padding:5px'>学期：</td>
                <td style='padding:5px'>
                    <%
                        int year = info.getYear();
                    %>
                    <input type="number" name="year" min="<%= year %>" value="<%= year %>" size="2" style="text-align: center;">年
                    <select name="season" required>
                        <%
                            for (Season season: Season.values()) {
                                String chi = season.toChinese();
                                String selected = chi.equals(info.getSeason()) ? " selected" : "";
                        %>
                        <option value="<%= chi %>"<%= selected %>><%= chi %></option>
                        <%
                            }
                        %>
                    </select>
                    学期
                </td>
            </tr>
            <tr>
                <td style='padding:5px'>课程：</td>
                <td style='padding:5px'>
                    <select name="course" required>
                        <%
                            for (CourseBean course: info.getCourseList()) {
                        %>
                        <option value="<%= course.getId() %>"><%= course.getName() %></option>
                        <%
                            }
                        %>
                    </select>
                </td>
            </tr>
            <tr>
                <td style='padding:5px'>班次：</td>
                <td style='padding:5px'>
                    <div id="course_classes"></div>
                    <input type="button" value="添加班级" onclick="addCourseClasses()">
                    <input type="button" value="移除班级" onclick="deleteCourseClasses()">
                </td>
            </tr>
            <tr>
                <td style='padding:5px' colspan='2' align='center'>
                    <input type='submit' value='发布课程'>
                </td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>
