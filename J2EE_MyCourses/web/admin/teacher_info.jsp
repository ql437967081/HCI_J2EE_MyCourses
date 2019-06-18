<%--
  Created by IntelliJ IDEA.
  User: 43796
  Date: 2019/3/13
  Time: 21:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" import="edu.nju.wsql.beans.TeacherStaticsBean" %>
<html>
<jsp:useBean id="info" type="edu.nju.wsql.beans.TeacherStaticsListBean" scope="request" />
<head>
    <title>查看教师统计信息</title>
    <%@include file="/navbar/style.jsp"%>
    <script type="text/javascript" src="/js/echarts.js"></script>
</head>
<body>
<%@include file="/navbar/admin_navbar.jsp"%>
<div style="margin-left:25%;padding:1px 16px;">
    <br>
    <div align="center">
        <div id="chart_main" style="width: 80%; height: 80%;"></div>
    </div>
    <script type="text/javascript">
        //指定图标的配置和数据
        var option = {
            title : {
                text: '教师统计信息'
            },
            tooltip : {
                trigger: 'axis'
            },
            legend: {
                data:['课程数量','开课总数','作业总数','选课总人次']
            },
            calculable : true,
            xAxis : [
                {
                    type : 'category',
                    data : [
                        <%
                            String tmp = "";
                            for (TeacherStaticsBean bean: info.getList()) {
                        %>
                        <%= tmp %>'<%= bean.getName() %>'
                        <%
                                tmp = ",";
                            }
                        %>
                    ]
                }
            ],
            yAxis : [
                {
                    type : 'value'
                }
            ],
            series : [
                {
                    name:'课程数量',
                    type:'bar',
                    data:[
                        <%
                            tmp = "";
                            for (TeacherStaticsBean bean: info.getList()) {
                        %>
                        <%= tmp %>'<%= bean.getCreateNum() %>'
                        <%
                                tmp = ",";
                            }
                        %>
                    ],
                    markPoint : {
                        data : [
                            {type : 'max', name: '最大值'},
                            {type : 'min', name: '最小值'}
                        ]
                    },
                    markLine : {
                        data : [
                            {type : 'average', name: '平均值'}
                        ]
                    }
                },
                {
                    name:'开课总数',
                    type:'bar',
                    data:[
                        <%
                            tmp = "";
                            for (TeacherStaticsBean bean: info.getList()) {
                        %>
                        <%= tmp %>'<%= bean.getPublishNum() %>'
                        <%
                                tmp = ",";
                            }
                        %>
                    ],
                    markPoint : {
                        data : [
                            {type : 'max', name: '最大值'},
                            {type : 'min', name: '最小值'}
                        ]
                    },
                    markLine : {
                        data : [
                            {type : 'average', name : '平均值'}
                        ]
                    }
                },
                {
                    name:'作业总数',
                    type:'bar',
                    data:[
                        <%
                            tmp = "";
                            for (TeacherStaticsBean bean: info.getList()) {
                        %>
                        <%= tmp %>'<%= bean.getHomeworkNum() %>'
                        <%
                                tmp = ",";
                            }
                        %>
                    ],
                    markPoint : {
                        data : [
                            {type : 'max', name: '最大值'},
                            {type : 'min', name: '最小值'}
                        ]
                    },
                    markLine : {
                        data : [
                            {type : 'average', name : '平均值'}
                        ]
                    }
                },
                {
                    name:'选课总人次',
                    type:'bar',
                    data:[
                        <%
                            tmp = "";
                            for (TeacherStaticsBean bean: info.getList()) {
                        %>
                        <%= tmp %>'<%= bean.getSelectNum() %>'
                        <%
                                tmp = ",";
                            }
                        %>
                    ],
                    markPoint : {
                        data : [
                            {type : 'max', name: '最大值'},
                            {type : 'min', name: '最小值'}
                        ]
                    },
                    markLine : {
                        data : [
                            {type : 'average', name : '平均值'}
                        ]
                    }
                }
            ]
        };

        //初始化echarts实例
        var myChart = echarts.init(document.getElementById('chart_main'));

        //使用制定的配置项和数据显示图表
        myChart.setOption(option);
    </script>
</div>
</body>
</html>
