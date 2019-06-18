<%--
  Created by IntelliJ IDEA.
  User: 43796
  Date: 2019/3/9
  Time: 0:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" import="edu.nju.wsql.beans.StudentGradeBean, edu.nju.wsql.beans.CourseGradeBean" %>
<html>
<jsp:useBean id="info" type="edu.nju.wsql.beans.StudentGradeListBean" scope="request" />
<head>
    <title>课程成绩</title>
    <%@include file="/navbar/style.jsp"%>
</head>
<body>
<%@include file="/navbar/student_course_navbar.jsp"%>
<div style="margin-left:25%;padding:1px 16px;">
    <h1 align=center>成绩</h1>
    <table border="1" align="center">
        <tr>
            <th style='padding:5px'>项目</th>
            <th style='padding:5px'>备注</th>
            <th style='padding:5px'>分数</th>
        </tr>
        <%
            for (StudentGradeBean bean: info.getGrades()) {
                CourseGradeBean courseGradeBean = bean.getBean();
                String name = courseGradeBean.getName();
                String location = courseGradeBean.getLocation();
                String remark = courseGradeBean.getRemark();
                String td1 = location == null ? name :
                        "<a href='" + location +
                                "' target='_blank' download='" + name +
                                "'>" + name +
                                "</a>";
        %>
        <tr>
            <td style='padding:5px'><%= td1 %></td>
            <td style='padding:5px'><%= remark %></td>
            <td style='padding:5px'><%= bean.getScore() %></td>
        </tr>
        <%
            }
        %>
    </table>
</div>
</body>
</html>
