<template>
  <el-container style="height: 590px; border: 1px solid #eee">
    <el-aside width="200px" class="el-aside">
      <el-menu :default-openeds="['1']" default-active="/student_main" style="height: 588px">
        <el-link href="/#/teacher_main">
          <el-menu-item index="/teacher_main">
            <template slot="title">
              <i class="el-icon-s-home" style="color: #409EFF"></i>
              <i class="course" style="font-weight: bold; font-style: normal; color: #409EFF; font-size: 18px">主页</i>
            </template>
          </el-menu-item>
        </el-link>
        <el-menu-item index="/teacher_main">
          <template slot="title">
            <el-link href="/#/teacher_create_course">
              <i class="el-icon-menu" style="color: #409EFF"></i>
              <i class="course" style="font-weight: bold; font-style: normal; color: #409EFF; font-size: 18px">创建课程</i>
            </el-link>
          </template>
        </el-menu-item>
        <el-menu-item index="/teacher_main">
          <template slot="title">
            <el-link href="/#/teacher_publish_course">
              <i class="el-icon-menu" style="color: #409EFF"></i>
              <i class="course" style="font-weight: bold; font-style: normal; color: #409EFF; font-size: 18px">发布课程</i>
            </el-link>
          </template>
        </el-menu-item>
        <el-menu-item index="/teacher_main">
          <template slot="title">
            <el-link href="/#/teacher_course">
              <i class="el-icon-menu" style="color: #409EFF"></i>
              <i class="course" style="font-weight: bold; font-style: normal; color: #409EFF; font-size: 18px">我的课程</i>
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
              <el-image :src="url" :fit="fit" style="width: 30px; height: 30px;"></el-image>
              <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
              <span style="font-size: 15px; color: aliceblue; position: absolute;top:0; left: 40px">
                {{name}}
                <i class="el-icon-arrow-down el-icon--right"></i>
              </span>
            </span>
          </span>
          <el-dropdown-menu slot="dropdown" style="width: 100px">
            <el-link href="/#/teacher_info">
              <el-dropdown-item style="font-size: 15px">个人信息</el-dropdown-item>
            </el-link>
            <el-link href="/#/logout">
              <el-dropdown-item style="font-size: 15px">退出登录</el-dropdown-item>
            </el-link>
          </el-dropdown-menu>
        </el-dropdown>
      </el-header>

      <el-main>
        <h2>主页</h2>
        <div style="width: 10%">
          <el-dropdown split-button="" @command="handleCommand">{{chosenYear}}
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item v-for="year_ in years" :command="year_">{{year_}}</el-dropdown-item>
            </el-dropdown-menu>


          </el-dropdown>
        </div>
        <el-table :data="tableData" stripe="true" align="center" style="width: 100%">
          <el-table-column prop="my_publish_courses" label="我发布的课程" width="300"></el-table-column>
          <el-table-column prop="undergraduate_num" label="本科生人数" width="180"></el-table-column>
          <el-table-column prop="graduate_num" label="研究生人数" width="180"></el-table-column>
          <el-table-column prop="phd_num" label="博士生人数" width="180"></el-table-column>
          <el-table-column prop="total_num" label="总人数" width="180"></el-table-column>
          <el-table-column prop="homework_num" label="作业数" width="180"></el-table-column>
        </el-table>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
  import {getLoading} from '../../loading'

  export default {
    name: "TeacherMain",
    mounted: function () {
      this.getInfo()
      this.getMyCourses()
    },
    methods: {
      getInfo() {
        this.$axios({
          method: 'get',
          url: 'http://localhost:8080/vue/teacher/info'
        }).then(function (res) {
          const info = res.data
          this.name = info.name
          this.url = 'http://localhost:8080' + info.portrait
        }.bind(this)).catch(function (err) {
          if (err.response.status === 401) {
            this.$router.push('/login_register')
          }
        }.bind(this))
      },
      getMyCourses() {

      },
      handleCommand(command) {
        this.chosenYear = command
      }
    },
    data() {
      return {
        fit: 'cover',
        url: 'http://localhost:8080/img/portrait/default portrait.png',
        name: '',
        loading: true,
        courses: [],
        year: '全部',
        years: ['2019', '2018', '2017'],
        chosenYear: '全部年份'
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
