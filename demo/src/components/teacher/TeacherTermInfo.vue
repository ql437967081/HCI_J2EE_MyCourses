<template>
  <el-container style="height: 590px; border: 1px solid #eee">
    <el-aside width="200px" class="el-aside">
      <el-menu :default-openeds="['/student_course']" default-active="/student_course" style="height: 588px">
        <el-link href="/#/student_main">
          <el-menu-item index="/student_main">
            <template slot="title">
              <i class="el-icon-s-home"></i>
              <i class="course" style="font-weight: bold; font-style: normal; color: grey; font-size: 18px">主页</i>
            </template>
          </el-menu-item>
        </el-link>
        <el-menu-item index="/student_course">
          <template slot="title">
            <el-link href="/#/student_course">
              <i class="el-icon-menu" style="color: #409EFF"></i>
              <i class="course" style="font-weight: bold; font-style: normal; color: #409EFF; font-size: 18px">学期信息</i>
            </el-link>
          </template>
        </el-menu-item>
        <el-menu-item index="/lauchhomework">
          <template slot="title">
            <el-link href="/#/student_course">
              <i class="el-icon-menu" style="color: #409EFF"></i>
              <i class="course" style="font-weight: bold; font-style: normal; color: #409EFF; font-size: 18px">发布作业</i>
            </el-link>
          </template>
        </el-menu-item>
        <el-menu-item index="/student_course">
          <template slot="title">
            <el-link href="/#/student_course">
              <i class="el-icon-menu" style="color: #409EFF"></i>
              <i class="course" style="font-weight: bold; font-style: normal; color: #409EFF; font-size: 18px">群发邮件</i>
            </el-link>
          </template>
        </el-menu-item>
        <el-menu-item index="/student_course">
          <template slot="title">
            <el-link href="/#/student_course">
              <i class="el-icon-menu" style="color: #409EFF"></i>
              <i class="course" style="font-weight: bold; font-style: normal; color: #409EFF; font-size: 18px">下载作业</i>
            </el-link>
          </template>
        </el-menu-item>
        <el-menu-item index="/student_course">
          <template slot="title">
            <el-link href="/#/student_course">
              <i class="el-icon-menu" style="color: #409EFF"></i>
              <i class="course" style="font-weight: bold; font-style: normal; color: #409EFF; font-size: 18px">发布成绩</i>
            </el-link>
          </template>
        </el-menu-item>
      </el-menu>
    </el-aside>

    <el-container>
      <el-header class="el-header">
        <el-dropdown>
          <span class="el-dropdown-link">
            <span style="position:relative;">
              <el-image  :src="url" :fit="fit" style="width: 30px; height: 30px;"></el-image>
              <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
              <span style="font-size: 15px; color: aliceblue; position: absolute;top:0; left: 40px">
                {{name}}
                <i class="el-icon-arrow-down el-icon--right"></i>
              </span>
            </span>
          </span>
          <el-dropdown-menu slot="dropdown" style="width: 100px">
            <el-link href="/#/student_info">
              <el-dropdown-item style="font-size: 15px">个人信息</el-dropdown-item>
            </el-link>
            <el-link href="/#/logout">
              <el-dropdown-item style="font-size: 15px">退出登录</el-dropdown-item>
            </el-link>
          </el-dropdown-menu>
        </el-dropdown>
      </el-header>

      <el-main style="position: relative">
        <div style="width: 45%; height: 100%;position: absolute; left:20px; ">
          <el-card class="box-card">
            <span style="margin-bottom: 20px">线性代数2019年春季学期</span>
            <el-table
              :data="tableData"
              border
              style="width: 90%"
            >
              <el-table-column
                prop="class"
                label="班级"
                style="width: 30%"
              >
              </el-table-column>
              <el-table-column
                prop="count"
                label="已选人数"
                style="width: 40%"
              >
              </el-table-column>
              <el-table-column
                prop="number"
                label="限选人数"
                style="width: 30%"
              >
              </el-table-column>
            </el-table>
          </el-card>
        </div>
        <div style="width: 45%; height: 100%;  position:absolute;left:50%;" id="main">
        </div>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import echarts from 'echarts'
export default {
  data () {
    return {
      tableData: [{
        class: '1',
        count: '3',
        number: '100'
      }, {
        class: '1',
        count: '3',
        number: '100'
      }, {
        class: '1',
        count: '3',
        number: '100'
      }, {
        class: '1',
        count: '3',
        number: '100'
      }],
      charts: '',
      opinion: ['男', '女', '中性'],
      opinionData: [
        { value: 335, name: '男' },
        { value: 310, name: '女' },
        { value: 15, name: '中性' }
      ]
    }
  },
  methods: {
    drawPie (id) {
      this.charts = echarts.init(document.getElementById(id))
      this.charts.setOption({
        title: {
          text: '成绩统计',
          subtext: '纯属虚构',
          x: 'center'
        },
        tooltip: {
          trigger: 'item'

        },
        legend: {
          orient: 'vertical',
          x: 'left',
          data: this.opinion
        },
        series: [
          {
            name: '性别',
            type: 'pie',
            radius: ['50%', '70%'],
            avoidLabelOverlap: false,
            label: {
              normal: {
                show: false,
                position: 'center'
              },
              emphasis: {
                show: true,
                textStyle: {
                  fontSize: '30',
                  fontWeight: 'blod'
                }
              }
            },
            labelLine: {
              normal: {
                show: false
              }
            },
            data: this.opinionData
          }
        ]
      })
    }
  },
  // 调用
  mounted () {
    this.$nextTick(function () {
      this.drawPie('main')
    })
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
</style>
