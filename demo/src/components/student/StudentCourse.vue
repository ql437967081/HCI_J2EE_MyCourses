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
        <el-submenu index="/student_course">
          <template slot="title">
            <el-link href="/#/student_course">
              <i class="el-icon-menu" style="color: #409EFF"></i>
              <i class="course" style="font-weight: bold; font-style: normal; color: #409EFF; font-size: 18px">课程</i>
            </el-link>
          </template>
          <el-menu-item-group v-loading="loading">
            <el-menu-item v-for="course in tableData" v-if="course.condition" >
              <el-link :href="'/#/student_course/' + course.link">
                <i style="font-size: 12px; font-style: normal">
                  {{course.course}}
                </i>
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
              <el-image  :src="url" :fit="fit" style="width: 30px; height: 30px;"></el-image>
              <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
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

      <el-main>
        <el-card class="box-card">
            <el-table
              v-loading="loading"
              :data="tableData"
              height="450px"
              border
              style="width: 1000px;">
              <el-table-column
                prop="course"
                label="课程"
                width="320">
              </el-table-column>
              <el-table-column
                prop="semester"
                label="学期"
                width="220">
              </el-table-column>
              <el-table-column
                prop="teacher"
                label="教师"
                width="159">
              </el-table-column>
              <el-table-column
                prop="condition"
                label="状态"
                width="150">
                <template slot-scope="scope">
                  <i v-if="!scope.row.condition" style="color: red; font-style: normal">未加入</i>
                  <i v-else style="color: green; font-style: normal">已加入</i>
                </template>
              </el-table-column>
              <el-table-column
                prop="link"
                label= "操作"
                width="130">
                <template slot-scope="scope">
                  <i v-if="!scope.row.condition" style="color: black; font-style: normal;">
                    <el-button type="text" @click="open(scope.row.link)" class="el-icon-circle-plus">
                    </el-button>
                    <el-dialog :title="selectedData.name" width="30%" :visible.sync="dialogTableVisible">
                      <div style="font-size: small; margin-top: -30px">
                        {{selectedData.term}}
                        &nbsp;&nbsp;
                        教师：{{selectedData.publisher}}
                      </div>
                      <el-table :data="selectedData.classes" border>
                        <el-table-column property="classId" label="班级"></el-table-column>
                        <el-table-column property="selectedNum" label="已选人数"></el-table-column>
                        <el-table-column property="limitNum" label="限选人数"></el-table-column>
                      </el-table>
                      <i v-if="isFull()" style="color: red;">
                        该课程人数已满
                      </i>
                      <div slot="footer" class="dialog-footer" style="margin-top: -30px">
                        <el-button @click="cancel">取 消</el-button>
                        <el-button v-if="!isFull()" type="primary" @click="join">加 入</el-button>
                      </div>
                    </el-dialog>
                  </i>
                  <i v-else style="color: black; font-style: normal;">
                    <el-link :href="'/#/student_course/' + scope.row.link" class="el-icon-view">
                    </el-link>
                  </i>
                </template>
              </el-table-column>
            </el-table>
          </el-card>
        </el-main>
    </el-container>
  </el-container>
</template>

<script>
export default {
  name: 'StudentMain',
  mounted: function () {
    this.getInfo()
    this.getMyCourses()
  },
  methods: {
    isFull () {
      let selectNum = 0;
      let limitNum = 0;
      for (let c of this.selectedData.classes) {
        selectNum += c.selectedNum
        limitNum += c.limitNum
      }
      return selectNum == limitNum
    },
    open (link) {
      console.log(link)
      this.$axios({
        method: 'get',
        url: 'http://localhost:8080/vue/student/select_course',
        params: {publishCourseId: link}
      }).then(function (res) {
        const info = res.data
        console.log(info)
        this.selectedData = info
        this.dialogTableVisible = true
      }.bind(this)).catch(function (err) {
        console.log(err)
        if (err.response.status === 401) {
          this.$router.push('/login_register')
        }
      }.bind(this))
    },
    join () {
      const link = this.selectedData.id
      this.$axios({
        method: 'post',
        url: 'http://localhost:8080/vue/student/select_course',
        params: {publishCourseId: link}
      }).then(function (res) {
        const selectCourseId = res.data
        console.log(selectCourseId)
        if (selectCourseId > 0) {
          this.$message.success('选课成功')
          this.$router.push('/student_course/' + selectCourseId)
        } else if (selectCourseId == -1) {
          this.$message.error('您选的课程人数已满')
          this.cancel()
          this.getMyCourses()
        } else {
          this.$message.error('您已经加入该课程')
          this.cancel()
          this.getMyCourses()
        }
      }.bind(this)).catch(function (err) {
        console.log(err)
        if (err.response.status === 401) {
          this.$router.push('/login_register')
        }
      }.bind(this))
    },
    cancel () {
      this.dialogTableVisible = false
    },
    getInfo () {
      this.$axios({
        method: 'get',
        url: 'http://localhost:8080/vue/student/info'
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
    getMyCourses () {
      this.$axios({
        method: 'get',
        url: 'http://localhost:8080/vue/student/courses'
      }).then(function (res) {
        this.loading = false
        const info = res.data
        for (let course of info) {
          console.log(course)
          let link = course.selected ? course.selectCourseId : course.termCourseInfo.id
          this.tableData.push({
            course: course.termCourseInfo.name,
            semester: course.termCourseInfo.term,
            teacher: course.termCourseInfo.publisher,
            condition: course.selected,
            link: link
          })
        }
      }.bind(this)).catch(function (err) {
        console.log(err)
        if (err.response.status === 401) {
          this.$router.push('/login_register')
        }
      }.bind(this))
    }
  },
  data () {
    return {
      fit: 'cover',
      url: 'http://localhost:8080/img/portrait/default portrait.png',
      name: '',
      tableData: [],
      loading: true,
      selectedData: {
        classes: [],
        courseId: 14,
        id: 12,
        limit: 5,
        name: "软件工程与计算Ⅱ",
        notSubmitHomeworkNum: 0,
        numOfDoctor: 1,
        numOfGraduate: 2,
        numOfHomework: 2,
        numOfUndergraduate: 2,
        publisher: "刘钦",
        season: "秋季",
        selectTime: null,
        status: "APPROVED",
        term: "2019年秋季学期",
        year: 2019
      },
      dialogTableVisible: false,
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
  .el-icon-view {
    font-size: 22px;
  }
  .el-icon-circle-plus {
    font-size: 22px;
  }
</style>
