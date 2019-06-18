<%--
  Created by IntelliJ IDEA.
  User: 43796
  Date: 2019/3/3
  Time: 17:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" import="edu.nju.wsql.beans.StudentTermCourseBean, edu.nju.wsql.beans.TermCourseInfoBean" %>
<html>
<jsp:useBean id="info" type="edu.nju.wsql.beans.StudentPublishCourseListBean" scope="request" />
<head>
    <title>MyCourses</title>
    <%@include file="/navbar/style.jsp"%>
</head>
<body>
<%@include file="/navbar/student_navbar.jsp"%>
<%@include file="/info_page/alert.jsp"%>
<div style="margin-left:25%;padding:1px 16px;">
    <h1 align=center>课程</h1>
    <table border="1" align="center">
        <tr>
            <th style='padding:5px'>课程</th>
            <th style='padding:5px'>学期</th>
            <th style='padding:5px'>教师</th>
            <th style='padding:5px'>状态</th>
            <th style='padding:5px'>操作</th>
        </tr>
        <%
            for (StudentTermCourseBean studentTermCourseBean: info.getTermCourseList()) {
                String href;
                TermCourseInfoBean termCourseInfoBean = studentTermCourseBean.getTermCourseInfo();
                if (studentTermCourseBean.isSelected())
                    href = "/student/course/" + studentTermCourseBean.getSelectCourseId();
                else
                    href = "/student/select_course/" + termCourseInfoBean.getId();
        %>
        <tr>
            <td style='padding:5px'><%= termCourseInfoBean.getName() %></td>
            <td style='padding:5px'><%= termCourseInfoBean.getTerm() %></td>
            <td style='padding:5px'><%= termCourseInfoBean.getPublisher() %></td>
            <td style='padding:5px'><%= studentTermCourseBean.isSelected() ? "已加入" : "未加入" %></td>
            <td style='padding:5px'>
                <a href="<%=href%>">进入</a>
            </td>
        </tr>
        <%
            }
        %>
    </table>
</div>
</body>
</html>
