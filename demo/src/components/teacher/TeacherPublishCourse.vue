<template>
  <el-container style="height: 590px; border: 1px solid #eee">
    <el-aside width="200px" class="el-aside">
      <el-menu :default-openeds="['1']" default-active="/teacher_main" style="height: 588px">
        <el-link href="/#/teacher_main">
          <el-menu-item index="/teacher_main">
            <template slot="title">
              <i class="el-icon-s-home" style="color: #409EFF"></i>
              <i class="course" style="font-weight: bold; font-style: normal; color: #409EFF; font-size: 18px">主页</i>
            </template>
          </el-menu-item>
        </el-link>
        <el-menu-item index="/teacher_create_course">
          <template slot="title">
            <el-link href="/#/teacher_create_course">
              <i class="el-icon-menu" style="color: #409EFF"></i>
              <i class="course" style="font-weight: bold; font-style: normal; color: #409EFF; font-size: 18px">创建课程</i>
            </el-link>
          </template>
        </el-menu-item>
        <el-menu-item index="/teacher_publish_course">
          <template slot="title">
            <el-link href="/#/teacher_publish_course">
              <i class="el-icon-menu" style="color: #409EFF"></i>
              <i class="course" style="font-weight: bold; font-style: normal; color: #409EFF; font-size: 18px">发布课程</i>
            </el-link>
          </template>
        </el-menu-item>

        <el-submenu index="/teacher_course">
          <template slot="title">
            <el-link href="/#/teacher_course">
              <i class="el-icon-menu"></i>
              <i class="course" style="font-weight: bold; font-style: normal; color: grey; font-size: 18px">我的课程</i>
            </el-link>
          </template>
          <el-menu-item-group v-loading="loading">
            <el-menu-item v-for="course in courses" >
              <el-link :href="'/#/teacher_course/' + course.link">
                {{course.course}}
              </el-link>
            </el-menu-item>
          </el-menu-item-group>
        </el-submenu>
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
              <el-form-item style="text-align: left" label="学期">
                <el-date-picker v-model="year" type="year" placeholder="选择年份"></el-date-picker>
                <span>年</span>
                <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
                <el-dropdown split-button="" @command="chooseSeason">{{chosenSeason}}
                  <el-dropdown-menu slot="dropdown">
                    <el-dropdown-item v-for="season in seasons" :command="season">{{season}}</el-dropdown-item>
                  </el-dropdown-menu>
                </el-dropdown>
                <span>学期</span>
              </el-form-item>
              <el-form-item style="text-align: left" label="课程">
                <el-dropdown style="margin-left: auto" split-button="" @command="chooseCourse">{{chosenCourse}}
                  <el-dropdown-menu slot="dropdown">
                    <el-dropdown-item v-for="course in courses" :command="course">{{course}}</el-dropdown-item>
                  </el-dropdown-menu>
                </el-dropdown>
              </el-form-item>
              <el-form-item style="text-align: left" label="班次">
                <el-button @click="addClass">添加班级</el-button>
              </el-form-item>
              <el-form-item
                style="text-align: left"
                v-for="(domain, index) in domains"
                :label="'班号' + (index+1)"
                :key="domain.key"
                :prop="'domains.' + index + '.value'">
                <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;人数&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
                <el-input-number v-model="domain.value"></el-input-number><el-button @click.prevent="removeClass($index)">删除</el-button>
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
      getCourse() {

      },
      addClass() {
        this.domains.push({
          value: '',
          key: Date.now()
        });
      },
      removeClass(index) {
        this.domains.splice(index, 1)
      },
      publishCourse() {

      },
      chooseSeason(command) {
        this.chosenSeason = command
      },
      chooseCourse(command) {
        this.chosenCourse = command
      }
    },
    data() {
      return {
        fit: 'cover',
        url: 'http://localhost:8080/img/portrait/default portrait.png',
        name: '',
        year: '',
        season: '',
        seasons: ['春季', '夏季', '秋季', '冬季'],
        chosenSeason: '选择季度',
        course: '',
        chosenCourse: '选择课程',
        courses: ['线性代数'],
        publishCourse: {},
        courseClass: {},
        classes:[{value:0,}],
        domains:[],
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
