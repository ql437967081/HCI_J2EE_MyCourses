<%--
  Created by IntelliJ IDEA.
  User: 43796
  Date: 2019/2/26
  Time: 21:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" import="edu.nju.wsql.beans.CourseClassBean" %>
<html>
<jsp:useBean id="info" type="edu.nju.wsql.beans.TermCourseInfoBean" scope="request" />
<head>
    <title>课程：<%= info.getName() %>&nbsp;&nbsp;<%= info.getTerm() %></title>
    <%@include file="/navbar/style.jsp"%>
    <script type="text/javascript" src="/js/echarts.js"></script>
</head>
<body>
<%@include file="/navbar/teacher_term_course_navbar.jsp"%>
<div style="margin-left:25%;padding:1px 16px;">
    <h1 align="center"><%= info.getName() %></h1>
    <h2 align="center"><%= info.getTerm() %></h2>
    <table border="1" align="center">
        <tr>
            <th style='padding:5px'>班级</th>
            <th style='padding:5px'>已选人数</th>
            <th style='padding:5px'>限选人数</th>
        </tr>
        <%
            for (CourseClassBean classBean: info.getClasses()) {
        %>
        <tr>
            <td style='padding:5px'><%= classBean.getClassId() %></td>
            <td style='padding:5px'>
                <a href="/teacher/course/<%= course_id %>/term_course/<%= id %>/<%= classBean.getId() %>">
                    <%= classBean.getSelectedNum() %>
                </a>
            </td>
            <td style='padding:5px'><%= classBean.getLimitNum() %></td>
        </tr>
        <%
            }
        %>
    </table>
    <br>
    <div align="center">
        <div id="chart_main" style="width: 50%; height: 50%;"></div>
    </div>
    <script type="text/javascript">
        //指定图标的配置和数据
        var option = {
            title:{
                text:'选课学生类型分布'
            },
            tooltip : {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            series:[{
                name:'选课人数',
                type:'pie',
                radius:'60%',
                data:[
                    {value:<%= info.getNumOfUndergraduate() %>,name:'本科生'},
                    {value:<%= info.getNumOfGraduate() %>,name:'研究生'},
                    {value:<%= info.getNumOfDoctor() %>,name:'博士生'}
                ]
            }]
        };
        //初始化echarts实例
        var myChart = echarts.init(document.getElementById('chart_main'));

        //使用制定的配置项和数据显示图表
        myChart.setOption(option);
    </script>
</div>
</body>
</html>
