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
            <el-link href="/#/TeacherTermInfo">
              <i class="el-icon-menu" style="color: #409EFF"></i>
              <i class="course" style="font-weight: bold; font-style: normal; color: #409EFF; font-size: 18px">学期信息</i>
            </el-link>
          </template>
        </el-menu-item>
        <el-menu-item index="/lauchhomework">
          <template slot="title">
            <el-link href="/#/TeacherLauchHomework">
              <i class="el-icon-menu" style="color: grey"></i>
              <i class="course" style="font-weight: bold; font-style: normal; color: grey; font-size: 18px">发布作业</i>
            </el-link>
          </template>
        </el-menu-item>
        <el-menu-item index="/student_course">
          <template slot="title">
            <el-link href="/#/TeacherSendMails">
              <i class="el-icon-menu" style="color: grey"></i>
              <i class="course" style="font-weight: bold; font-style: normal; color: grey; font-size: 18px">群发邮件</i>
            </el-link>
          </template>
        </el-menu-item>
        <el-menu-item index="/student_course">
          <template slot="title">
            <el-link href="/#/TeacherDownloadHomework">
              <i class="el-icon-menu" style="color: grey"></i>
              <i class="course" style="font-weight: bold; font-style: normal; color: grey; font-size: 18px">下载作业</i>
            </el-link>
          </template>
        </el-menu-item>
        <el-menu-item index="/student_course">
          <template slot="title">
            <el-link href="/#/TeacherLauchHomework">
              <i class="el-icon-menu" style="color: grey"></i>
              <i class="course" style="font-weight: bold; font-style: normal; color: grey; font-size: 18px">发布成绩</i>
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
            <div style="margin-bottom: 20px" v-model="title">{{title}}</div>
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
import { getLoading } from '../../loading'
import echarts from 'echarts'
export default {
  // 调用
  mounted: function () {
    this.coursetermid = this.$route.params.couseId
    // 方便测试，之后要删除
    this.coursetermid = 5
    this.termInfo()
  },
  data () {
    return {
      tableData: [],
      charts: '',
      opinion: [],
      opinionData: [],
      coursetermid: '',
      title: ''
    }
  },
  methods: {
    termInfo () {
      const loading = getLoading(this)
      this.$axios({
        method: 'get',
        url: 'http://localhost:8080/vue/teacher/terminfo',
        params: {
          id: this.coursetermid
        }
      }).then(function (res) {
        loading.close()
        const info = res.data
        let list1 = info.classes
        for (let courseclass of list1) {
          console.log(courseclass)
          this.tableData.push({
            class: courseclass.classId,
            count: courseclass.selectedNum,
            number: courseclass.limitNum
          })
        }
        this.title = info.name + info.year + '年' + info.season + '学期'
        this.opinion.push('本科生')
        this.opinion.push('研究生')
        this.opinion.push('博士生')
        this.opinionData.push({
          value: info.numOfUndergraduate,
          name: '本科生'
        })
        this.opinionData.push({
          value: info.numOfGraduate,
          name: '研究生'
        })
        this.opinionData.push({
          value: info.numOfDoctor,
          name: '博士生'
        })
        this.drawPie('main')
      }.bind(this)).catch(function (err) {
        console.log(err)
        loading.close()
        if (err.response.status === 401) {
          this.$router.push('/login_register')
        } else if (err.response.status === 402) {
          this.$message.error('您未选择此课程')
          this.$router.go(-1)
        } else if (err.response.status === 403) {
          this.$message.error('课程作业有误')
          this.$router.go(-1)
        }
      })
    },
    drawPie (id) {
      this.charts = echarts.init(document.getElementById(id))
      this.charts.setOption({
        title: {
          text: this.title + '成绩统计',
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
