<%--
  Created by IntelliJ IDEA.
  User: 43796
  Date: 2019/2/7
  Time: 18:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<jsp:useBean id="info" type="edu.nju.wsql.beans.UserStaticsBean" scope="request" />
<head>
    <title>我的主页</title>
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
                text: 'MyCourses使用情况',
                x:'center'
            },
            tooltip : {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            legend: {
                orient : 'vertical',
                x : 'left',
                data:['教师人数','本科生人数','研究生人数','博士生人数']
            },
            calculable : true,
            series : [
                {
                    name:'用户人数统计',
                    type:'pie',
                    radius : '55%',
                    center: ['50%', '60%'],
                    data:[
                        {value:<%= info.getTeacherNum() %>, name:'教师人数'},
                        {value:<%= info.getUndergraduateNum() %>, name:'本科生人数'},
                        {value:<%= info.getGraduateNum() %>, name:'研究生人数'},
                        {value:<%= info.getDoctorNum() %>, name:'博士生人数'}
                    ]
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
