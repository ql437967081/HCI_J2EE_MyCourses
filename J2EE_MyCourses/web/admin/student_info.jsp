<%--
  Created by IntelliJ IDEA.
  User: 43796
  Date: 2019/3/14
  Time: 16:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<jsp:useBean id="info" type="edu.nju.wsql.beans.StudentStaticsBean" scope="request" />
<head>
    <title>查看学生统计信息</title>
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
        var option = {
            title : {
                text: '学生统计信息'
            },
            tooltip : {
                trigger: 'axis'
            },
            legend: {
                data:['选课总人次','人均选课次数']
            },
            calculable : true,
            xAxis : [
                {
                    type : 'category',
                    data : ['本科生','研究生','博士生']
                }
            ],
            yAxis : [
                {
                    type : 'value'
                }
            ],
            series : [
                {
                    name:'选课总人次',
                    type:'bar',
                    data:[<%= info.getUndergraduateSelectSum() %>,<%= info.getGraduateSelectSum() %>,<%= info.getDoctorSelectSum() %>],
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
                    name:'人均选课次数',
                    type:'bar',
                    data:[<%= info.getUndergraduateSelectNum() %>,<%= info.getGraduateSelectNum() %>,<%= info.getDoctorSelectNum() %>],
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
