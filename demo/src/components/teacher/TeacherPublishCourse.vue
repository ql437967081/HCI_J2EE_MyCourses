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
        <h2>发布课程</h2>
        <el-col :span="6"><br/></el-col>
        <el-col :span="15">
          <el-card class="box-card" style="width: 80%">
            <el-form ref="publishCourse" :model="publishCourse" label-width="80px">
              <el-form-item label="学期">
                <el-date-picker v-model="year" type="year" placeholder="选择年份"></el-date-picker>
                <span>年</span>
                <span>&nbsp;&nbsp;&nbsp;</span>
                <el-dropdown split-button="">
                  -
                  <el-dropdown-menu slot="dropdown">
                    <el-dropdown-item>春季</el-dropdown-item>
                    <el-dropdown-item>夏季</el-dropdown-item>
                    <el-dropdown-item>秋季</el-dropdown-item>
                    <el-dropdown-item>冬季</el-dropdown-item>
                  </el-dropdown-menu>
                </el-dropdown>
                <span>&nbsp;&nbsp;&nbsp;</span>
                <span>学期</span>
              </el-form-item>
              <el-form-item label="课程">
                <el-dropdown>
                  <el-dropdown-menu slot="dropdown">{{course}}

                  </el-dropdown-menu>
                </el-dropdown>
              </el-form-item>
              <el-form-item label="班次">

              </el-form-item>
              <el-button type="primary" @click="publishCourse">发布课程</el-button>
            </el-form>
          </el-card>
        </el-col>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
  export default {
    name: "TeacherPublishCourse",
    mounted: function () {
      this.getInfo()
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
      chooseSeason(season) {
        this.season = season
      },
      publishCourse() {

      }
    },
    data() {
      return {
        fit: 'cover',
        url: 'http://localhost:8080/img/portrait/default portrait.png',
        name: '',
        year: '',
        season: '',
        course: '',
        publishCourse: {},
        courseClass: {}
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
