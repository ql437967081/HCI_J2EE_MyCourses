<template>
  <el-container style="height: 590px; border: 1px solid #eee">
    <el-aside width="200px" class="el-aside">
      <el-menu :default-openeds="['1']" default-active="/admin_main" style="height: 588px">
        <el-link href="/#/admin_main">
          <el-menu-item index="/admin_main">
            <template slot="title">
              <i class="el-icon-s-home"></i>
              <i class="course" style="font-weight: bold; font-style: normal; color: grey; font-size: 18px">审批</i>
            </template>
          </el-menu-item>
        </el-link>
        <el-link href="/#/admin_statistic">
          <el-menu-item index="/admin_statistic">
            <template slot="title">
              <i class="el-icon-menu" style="color: #409EFF"></i>
              <i class="course" style="font-weight: bold; font-style: normal; color: #409EFF; font-size: 18px">统计</i>
            </template>
          </el-menu-item>
        </el-link>
      </el-menu>
    </el-aside>

    <el-container>
      <el-header class="el-header">
        <el-dropdown>
          <span class="el-dropdown-link">
            <span style="position:relative;">
              <el-image  :src="url" :fit="fit" style="width: 30px; height: 30px;"></el-image>
              <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
              <span style="font-size: 15px; color: aliceblue; position: absolute;top:0; left: 40px">
                {{name}}
                <i class="el-icon-arrow-down el-icon--right"></i>
              </span>
            </span>
          </span>
          <el-dropdown-menu slot="dropdown" style="width: 100px">
            <el-link href="/#/logout">
              <el-dropdown-item style="font-size: 15px">退出登录</el-dropdown-item>
            </el-link>
          </el-dropdown-menu>
        </el-dropdown>
      </el-header>

      <el-main>
        <el-card class="box-card">
          <el-col :span="2"><br></el-col>
          <el-col :span="20">
            <el-tabs v-model="activeName" class="tab">
              <el-tab-pane name="first">
                <span slot="label">
                  &nbsp;<i class="el-icon-s-order"></i> 使用情况&nbsp;&nbsp;
                </span>
                <div id="mainStatics" style="width: 475px; height: 1000px; margin-left: 150px"></div>
              </el-tab-pane>
              <el-tab-pane name="second">
                <span slot="label">
                    &nbsp;<i class="el-icon-s-order"></i> 教师信息&nbsp;&nbsp;
                </span>
                <div id="teacherStatics" style="width: 800%; height: 450px;"></div>
              </el-tab-pane>
              <el-tab-pane name="third">
                <span slot="label">
                    &nbsp;<i class="el-icon-s-order"></i> 学生信息&nbsp;&nbsp;
                </span>
                <div id="studentStatics" style="width: 600%; height: 450px; margin-left: 100px"></div>
              </el-tab-pane>
            </el-tabs>
          </el-col>
          <el-col :span="2"><br></el-col>
        </el-card>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import { getLoading } from '../../loading'
import echarts from 'echarts'

export default {
  name: "AdminStatistic",
  mounted: function () {
    this.init()
  },
  methods: {
    init () {
      const loading = getLoading(this)
      this.$axios({
        method: 'get',
        url: 'http://localhost:8080/vue/admin/statics'
      }).then(function (res) {
        loading.close()
        const info = res.data
        this.getMainStatic(info.userStatics)
        this.getTeacherStatic(info.teacherStatics.list)
        this.getStudentStatic(info.studentStatics)
      }.bind(this)).catch(function (err) {
        console.log(err)
        if (err.response.status === 401) {
          this.$router.push('/login_register')
        }
      }.bind(this))
    },
    getMainStatic (userStatics) {
      //使用制定的配置项和数据显示图表
      let series = this.option.series[0]
      series.data[0].value = userStatics.teacherNum
      series.data[1].value = userStatics.undergraduateNum
      series.data[2].value = userStatics.graduateNum
      series.data[3].value = userStatics.doctorNum
      let myChart = echarts.init(document.getElementById('mainStatics'));
      myChart.setOption(this.option);
    },
    getTeacherStatic (teacherStatics) {
      //使用制定的配置项和数据显示图表
      this.teacherOption.xAxis[0].data = []
      this.teacherOption.series[0].data = []
      this.teacherOption.series[1].data = []
      this.teacherOption.series[2].data = []
      this.teacherOption.series[3].data = []
      for (let teacher of teacherStatics) {
        this.teacherOption.xAxis[0].data.push(teacher.name)
        this.teacherOption.series[0].data.push(teacher.createNum)
        this.teacherOption.series[1].data.push(teacher.publishNum)
        this.teacherOption.series[2].data.push(teacher.homeworkNum)
        this.teacherOption.series[3].data.push(teacher.selectNum)
      }
      let myChart = echarts.init(document.getElementById('teacherStatics'));
      myChart.setOption(this.teacherOption);
    },
    getStudentStatic (studentStatics) {
      //使用制定的配置项和数据显示图表
      this.studentOption.series[0].data = [
        studentStatics.undergraduateSelectSum,
        studentStatics.graduateSelectSum,
        studentStatics.doctorSelectSum
      ]
      this.studentOption.series[1].data = [
        studentStatics.undergraduateSelectNum,
        studentStatics.graduateSelectNum,
        studentStatics.doctorSelectNum
      ]
      let myChart = echarts.init(document.getElementById('studentStatics'));
      myChart.setOption(this.studentOption);
    }
  },
  data () {
    return {
      fit: 'cover',
      url: 'http://localhost:8080/img/portrait/default portrait.png',
      name: 'MyCourses主管',
      activeName: 'first',
      option : {
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
            radius : '60%',
            center: ['60%', '22%'],
            data:[
              {value:222, name:'教师人数'},
              {value:2222, name:'本科生人数'},
              {value:333, name:'研究生人数'},
              {value:444, name:'博士生人数'}
            ]
          }
        ]
      },
      teacherOption : {
        title : {
          text: '教师信息统计'
        },
        tooltip : {
          trigger: 'axis'
        },
        legend: {
          data:['课程数量','开课总数','作业总数','选课总人数']
        },
        calculable : true,
        xAxis : [
          {
            type : 'category',
            data : ['王浩然','送团','111','222']
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
            data:[2.0, 4.9, 7.0, 23.2],
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
            data:[2.6, 5.9, 9.0, 26.4],
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
            data:[2.6, 5.9, 9.0, 26.4],
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
            name:'选课总人数',
            type:'bar',
            data:[2.6, 5.9, 9.0, 26.4],
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
      },
      studentOption : {
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
            data:[2.0, 4.9, 7.0],
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
            data:[2.6, 5.9, 9.0],
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
      }
    }
  }
}
</script>

<style scoped>
  .el-header {
    background-color: #B3C0D1;
    color: #333;
    line-height: 60px;
    text-align: right;
  }
  .el-dropdown-link {
    cursor: pointer;
    height: 50px;
    width: 200px;
    font-size: 50px;
  }
  .el-aside {
    color: #333;
    background-color: rgb(238, 241, 246);
  }
  .box-card {
    height: 485px;
    width: 1020px;
  }
  .tab {
    height: 475px;
  }
</style>
