<%--
  Created by IntelliJ IDEA.
  User: 43796
  Date: 2019/2/7
  Time: 18:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" import="edu.nju.wsql.beans.StudentTermCourseBean, edu.nju.wsql.beans.TermCourseInfoBean" %>
<%@ page import="java.util.Map" %>
<%@ page import="edu.nju.wsql.beans.StudentQuitCourseBean" %>
<html>
<jsp:useBean id="info" type="edu.nju.wsql.beans.StudentPublishCourseListBean" scope="request" />
<head>
    <title>我的主页</title>
    <%@include file="/navbar/style.jsp"%>
</head>
<body>
<%@include file="/navbar/student_navbar.jsp"%>
<div style="margin-left:25%;padding:1px 16px;">
    <h1 align=center>主页</h1>
    <form id="select" method="get" action="/student/select">
        <div align="center">
            <span style="padding: 2% 5%">
            学期：
            <select name="term" onchange="document.getElementById('select').submit()">
                <option value="all">全部</option>
                <%
                    for (String term: info.getTerms()) {
                        String selected = "";
                        if (term.equals(info.getTermSelection()))
                            selected = " selected";
                %>
                <option value="<%= term %>"<%= selected %>><%= term %></option>
                <%
                    }
                %>
            </select>
        </span>
            <span style="padding: 2% 5%">
            课程：
            <select name="course" onchange="document.getElementById('select').submit()">
                <option value="-1">全部</option>
                <%
                    for (Map.Entry<Long, String> entry: info.getCourses().entrySet()) {
                        String selected = "";
                        long id = entry.getKey();
                        String name = entry.getValue();
                        if (id == info.getCourseSelection())
                            selected = " selected";
                %>
                <option value="<%= id %>"<%= selected %>><%= name %></option>
                <%
                    }
                %>
            </select>
        </span>
            <span style="padding: 2% 5%">
            教师：
            <select name="teacher" onchange="document.getElementById('select').submit()">
                <option value="all">全部</option>
                <%
                    for (Map.Entry<String, String> entry: info.getTeachers().entrySet()) {
                        String selected = "";
                        String id = entry.getKey();
                        String name = entry.getValue();
                        if (id.equals(info.getTeacherSelection()))
                            selected = " selected";
                %>
                <option value="<%= id %>"<%= selected %>><%= name %></option>
                <%
                    }
                %>
            </select>
        </span>
        </div>
    </form>
    <table align="center">
        <tr>
            <th style='padding:5px'>我的课程</th>
            <th style='padding:5px'>教师</th>
            <th style='padding:5px'>选课时间</th>
            <th style='padding:5px'>未提交的作业</th>
            <th style='padding:5px'>成绩</th>
        </tr>
        <%
            for (StudentTermCourseBean studentTermCourseBean: info.getTermCourseList()) {
                TermCourseInfoBean termCourseInfo = studentTermCourseBean.getTermCourseInfo();
                String name = termCourseInfo.getName() + " " + termCourseInfo.getTerm();
                String href = "/student/course/" + studentTermCourseBean.getSelectCourseId();
                String teacher = termCourseInfo.getPublisher();
                String time = termCourseInfo.getSelectTime();
                int notSubmit = termCourseInfo.getNotSubmitHomeworkNum();
                String homeworkHref = "/student/homework_list/" + studentTermCourseBean.getSelectCourseId();
                String gradeHref = "/student/grade/" + studentTermCourseBean.getSelectCourseId();
        %>
        <tr>
            <td style='padding:5px'>
                <a href="<%= href %>"><%= name %></a>
            </td>
            <td style='padding:5px'><%= teacher %></td>
            <td style='padding:5px'><%= time %></td>
            <td style='padding:5px'>
                <a href="<%= homeworkHref %>"><%= notSubmit %></a>
            </td>
            <td style='padding:5px'>
                <a href="<%= gradeHref %>">查看</a>
            </td>
        </tr>
        <%
            }
        %>
    </table>
    <table align="center">
        <tr>
            <th style='padding:5px'>退课记录</th>
            <th style='padding:5px'>教师</th>
            <th style='padding:5px'>选课时间</th>
            <th style='padding:5px'>退课时间</th>
        </tr>
        <%
            for (StudentQuitCourseBean studentQuitCourseBean: info.getQuitCourseList()) {
                String name = studentQuitCourseBean.getName() + " " + studentQuitCourseBean.getTerm();
                String href = "/student/select_course/" + studentQuitCourseBean.getId();
                String teacher = studentQuitCourseBean.getTeacher();
                String selectTime = studentQuitCourseBean.getSelectTime();
                String quitTime = studentQuitCourseBean.getQuitTime();
        %>
        <tr>
            <td style='padding:5px'>
                <a href="<%= href %>"><%= name %></a>
            </td>
            <td style='padding:5px'><%= teacher %></td>
            <td style='padding:5px'><%= selectTime %></td>
            <td style='padding:5px'><%= quitTime %></td>
        </tr>
        <%
            }
        %>
    </table>
</div>
</body>
</html>

