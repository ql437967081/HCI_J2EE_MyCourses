<%--
  Created by IntelliJ IDEA.
  User: 43796
  Date: 2019/3/3
  Time: 21:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" import="edu.nju.wsql.beans.CourseClassBean" %>
<html>
<jsp:useBean id="info" type="edu.nju.wsql.beans.TermCourseInfoBean" scope="request" />
<head>
    <title>选课：<%= info.getName() %>&nbsp;&nbsp;<%= info.getTerm() %></title>
    <%@include file="/navbar/style.jsp"%>
    <%@include file="/info_page/confirm.jsp"%>
</head>
<body>
<%@include file="/navbar/student_select_course_navbar.jsp"%>
<%@include file="/info_page/alert.jsp"%>
<div style="margin-left:25%;padding:1px 16px;">
    <h1 align="center">选课：<%= info.getName() %></h1>
    <h2 align="center"><%= info.getTerm() %></h2>
    <h3 align="center">教师：<%= info.getPublisher() %></h3>
    <table border="1" align="center">
        <tr>
            <th style='padding:5px'>班级</th>
            <th style='padding:5px'>已选人数</th>
            <th style='padding:5px'>限选人数</th>
        </tr>
        <%
            int selectNum = 0;
            int limitNum = 0;
            for (CourseClassBean classBean: info.getClasses()) {
                int sn = classBean.getSelectedNum();
                int ln = classBean.getLimitNum();
                selectNum += sn;
                limitNum += ln;
        %>
        <tr>
            <td style='padding:5px'><%= classBean.getClassId() %></td>
            <td style='padding:5px'><%= sn %></td>
            <td style='padding:5px'><%= ln %></td>
        </tr>
        <%
            }
            String td3;
            if (selectNum == limitNum)
                td3 = "选课人数已满";
            else
                td3 = "<button onclick=\"sure('确定加入此课程吗？', 'select')\">加入课程</button>";
        %>
        <tr>
            <td style='padding:5px' colspan='3' align='center'>
                <%= td3 %>
            </td>
        </tr>
    </table>
    <form id="select" method="post" action="/student/select_course/<%=info.getId()%>"></form>
</div>
</body>
</html>
