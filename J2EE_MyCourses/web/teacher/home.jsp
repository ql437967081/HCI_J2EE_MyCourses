<%--
  Created by IntelliJ IDEA.
  User: 43796
  Date: 2019/2/7
  Time: 18:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" import="edu.nju.wsql.beans.TermCourseInfoBean" %>
<html>
<jsp:useBean id="info" type="edu.nju.wsql.beans.PublishApprovalListBean" scope="request" />
<head>
    <title>我的主页</title>
    <%@include file="/navbar/style.jsp"%>
</head>
<body>
<%@include file="/navbar/teacher_navbar.jsp"%>
<div style="margin-left:25%;padding:1px 16px;">
    <h1 align=center>主页</h1>
    <div style="padding: 2% 10%">
        <form  id="terms" method="get" action="/teacher/select_term">
            <select name="term" onchange="document.getElementById('terms').submit()">
                <option value="all">全部</option>
                <%
                    for (String term: info.getTerms()) {
                        String selected = "";
                        if (term.equals(info.getSelection()))
                            selected = " selected";
                %>
                <option value="<%= term %>"<%= selected %>><%= term %></option>
                <%
                    }
                %>
            </select>
        </form>
    </div>
    <table align="center">
        <tr>
            <th style='padding:5px'>我发布的课程</th>
            <th style='padding:5px'>本科生人数</th>
            <th style='padding:5px'>研究生人数</th>
            <th style='padding:5px'>博士生人数</th>
            <th style='padding:5px'>总人数</th>
            <th style='padding:5px'>作业数</th>
        </tr>
        <%
            int uNum = 0, mNum = 0, dNum = 0, aNum = 0, lNum = 0, hNum = 0;
            for (TermCourseInfoBean termCourseInfo: info.getTermCourses()) {
                String name = termCourseInfo.getName() + " " + termCourseInfo.getTerm();
                String href = "/teacher/course/" + termCourseInfo.getCourseId() + "/term_course/" + termCourseInfo.getId();
                int u = termCourseInfo.getNumOfUndergraduate(); uNum += u;
                int m = termCourseInfo.getNumOfGraduate(); mNum += m;
                int d = termCourseInfo.getNumOfDoctor(); dNum += d;
                int l = termCourseInfo.getLimit(); lNum += l;
                int a = u + m + d; aNum += a;
                int h = termCourseInfo.getNumOfHomework(); hNum += h;
                String h_href = "/teacher/course/" + termCourseInfo.getCourseId() + "/download_homework/" + termCourseInfo.getId();
        %>
        <tr>
            <td style='padding:5px'>
                <a href="<%= href %>"><%= name %></a>
            </td>
            <td style='padding:5px'><%= u %></td>
            <td style='padding:5px'><%= m %></td>
            <td style='padding:5px'><%= d %></td>
            <td style='padding:5px'><%= a %>/<%= l %></td>
            <td style='padding:5px'>
                <a href="<%= h_href %>"><%= h %></a>
            </td>
        </tr>
        <%
            }
        %>
        <tr>
            <td style='padding:5px'>合计</td>
            <td style='padding:5px'><%= uNum %></td>
            <td style='padding:5px'><%= mNum %></td>
            <td style='padding:5px'><%= dNum %></td>
            <td style='padding:5px'><%= aNum %>/<%= lNum %></td>
            <td style='padding:5px'><%= hNum %></td>
        </tr>
    </table>
</div>
</body>
</html>
