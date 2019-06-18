<%--
  Created by IntelliJ IDEA.
  User: 43796
  Date: 2019/3/7
  Time: 20:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" import="edu.nju.wsql.beans.HomeworkBean, edu.nju.wsql.beans.CourseGradeBean" %>
<html>
<jsp:useBean id="info" type="edu.nju.wsql.beans.CourseHomeworkListBean" scope="request" />
<head>
    <title>发布学生成绩</title>
    <%@include file="/navbar/style.jsp"%>
    <%@include file="/info_page/confirm.jsp"%>
    <script type="text/javascript">
        function fileValid() {
            var submitFile = document.getElementById('sheet');
            var file = submitFile.files[0];
            if (file == null) {
                alert('请选择文件');
                return;
            }
            sure('确定发布成绩吗？', 'publish_grade');
        }
    </script>
</head>
<body>
<%@include file="/navbar/teacher_term_course_navbar.jsp"%>
<%@include file="/info_page/alert.jsp"%>
<div style="margin-left:25%;padding:1px 16px;">
    <span style="width: 45%;padding:1px 2.5%;float: left;">
        <h1 align="center">发布成绩</h1>
    <form id="publish_grade" method="post" action="/teacher/course/<%= course_id %>/publish_grade/<%= id %>" enctype="multipart/form-data">
        <table align="center">
            <tr>
                <td style='padding:5px'>作业或考试：</td>
                <td style='padding:5px'>
                    <select name="project" required>
                        <option value="-1">考试</option>
                        <%
                            for (HomeworkBean bean: info.getHomeworkBeans()) {
                        %>
                        <option value="<%= bean.getId() %>"><%= bean.getTitle() %></option>
                        <%
                            }
                        %>
                    </select>
                </td>
            </tr>
            <tr>
                <td style='padding:5px'>备注：</td>
                <td style='padding:5px'>
                    <input type="text" name="remark">
                </td>
            </tr>
            <tr>
                <td style='padding:5px'>是否公开：</td>
                <td style='padding:5px'>
                    <select name="open" required>
                        <option value="true">是</option>
                        <option value="false">否</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td style='padding:5px'>成绩表：</td>
                <td style='padding:5px'>
                    <input type="file"
                           id="sheet"
                           accept="application/vnd.ms-excel, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
                           name="sheet">
                </td>
            </tr>
            <tr>
                <td style='padding:5px'>
                    请提交后缀名为.xls或.xlsx的excel表格文件，格式参照右图
                </td>
                <td style='padding:5px'>
                    <img src="/img/grade/example_grade_excel.png" style="width: 60%; height: 40%;">
                </td>
            </tr>
        </table>
    </form>
    <div align='center'>
        <button onclick="fileValid()">发布成绩</button>
    </div>
    </span>
    <span style="width: 45%;padding:1px 2.5%;float: right;">
        <h1 align="center">以往发布的成绩</h1>
        <table border="1" align="center">
            <tr>
                <th style='padding:5px'>成绩表</th>
                <th style='padding:5px'>备注</th>
                <th style='padding:5px'>是否公开</th>
            </tr>
            <%
                for (CourseGradeBean bean: info.getGradeBeans()) {
                    String name = bean.getName();
            %>
            <tr>
                <td style='padding:5px'>
                    <a href="<%= bean.getLocation() %>" target="_blank" download="<%= name %>"><%= name %></a>
                </td>
                <td style='padding:5px'><%= bean.getRemark() %></td>
                <td style='padding:5px'><%= bean.getOpen() %></td>
            </tr>
            <%
                }
            %>
        </table>
    </span>
</div>
</body>
</html>
