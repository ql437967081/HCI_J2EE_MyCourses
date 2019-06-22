<template>
  <el-container style="height: 590px; border: 1px solid #eee">
    <el-aside width="200px" class="el-aside">
      <el-menu :default-openeds="['/student_course']" default-active="/student_course/course_id" style="height: 588px">
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
              <i class="el-icon-menu"></i>
              <i class="course" style="font-weight: bold; font-style: normal; color: grey; font-size: 18px">课程</i>
            </el-link>
          </template>
          <el-menu-item-group v-loading="loading">
            <el-menu-item v-for="course in tableData" v-if="course.condition" index="/student_course/course_id">
              <el-link :href="'/#/student_course/' + course.link">
                <i v-if="course.link == selectCourseId" style="color: #409EFF; font-size: 12px; font-style: normal;">
                  {{course.course}}
                </i>
                <i v-else style="font-size: 12px; font-style: normal;">{{course.course}}</i>
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
          <el-col :span="2"><br></el-col>
          <el-col :span="20">
            <div style="font-size: xx-large; font-weight: bolder">课程：{{this.course.name}}</div>
            <div><br></div>
            <div style="font-size: medium;">
              {{this.course.term}}
              &nbsp;&nbsp;
              班级：{{this.course.classId}}
              &nbsp;&nbsp;
              教师：{{this.course.teacher}}
            </div>
            <el-tabs v-model="activeName">
              <el-tab-pane name="first">
                <span slot="label">&nbsp;<i class="el-icon-s-order"></i> 课件&nbsp;</span>
                <el-card class="coursewares">
                  <el-table
                    v-loading="loading"
                    :data="coursewares"
                    height="270px"
                    border>
                    <el-table-column
                      prop="name"
                      label="课件"
                      width="713">
                      <template slot-scope="scope">
                        <el-link :download="scope.row.name" target="_blank" :href="'http://localhost:8080' + scope.row.location">
                          {{scope.row.name}}
                        </el-link>
                      </template>
                    </el-table-column>
                    <el-table-column
                      label="下载"
                      width="60">
                      <template slot-scope="scope">
                        <el-link :download="scope.row.name" target="_blank" :href="'http://localhost:8080' + scope.row.location" class="el-icon-download"></el-link>
                      </template>
                    </el-table-column>
                  </el-table>
                </el-card>
              </el-tab-pane>
              <el-tab-pane name="second">
                <span slot="label">&nbsp;<i class="el-icon-message-solid"></i> 作业&nbsp;</span>
                <el-card class="homework">
                  <el-table
                    v-loading="loading"
                    :data="homework"
                    height="270px"
                    border>
                    <el-table-column
                      prop="title"
                      label="作业"
                      width="413">
                      <template slot-scope="scope">
                        <el-link :href="'/#/student_course/' + selectCourseId + '/homework/' + scope.row.id">
                          {{scope.row.title}}
                        </el-link>
                      </template>
                    </el-table-column>
                    <el-table-column
                      prop="ddl"
                      label="截止时间"
                      width="220">
                    </el-table-column>
                    <el-table-column
                      label="状态"
                      width="80">
                      <template slot-scope="scope">
                        <i v-if="scope.row.submitId != -1" style="font-style: normal; color: green">
                          已提交
                        </i>
                        <i v-else style="font-style: normal; color: red">
                          未提交
                        </i>
                      </template>
                    </el-table-column>
                    <el-table-column
                      label="详情"
                      width="60">
                      <template slot-scope="scope">
                        <el-link :href="'/#/student_course/' + selectCourseId + '/homework/' + scope.row.id" class="el-icon-view"></el-link>
                      </template>
                    </el-table-column>
                  </el-table>
                </el-card>
              </el-tab-pane>
              <el-tab-pane name="third">
                <span slot="label">&nbsp;<i class="el-icon-s-flag"></i> 成绩&nbsp;</span>
                <el-card class="grades">
                  <el-table
                    v-loading="loading"
                    :data="grades"
                    height="270px"
                    border>
                    <el-table-column
                      label="成绩文件"
                      width="413">
                      <template slot-scope="scope">
                        <i v-if="scope.row.bean.location" style="font-style: normal">
                          <el-link :download="scope.row.bean.name" target="_blank" :href="'http://localhost:8080' + scope.row.bean.location">
                            {{scope.row.bean.name}}
                          </el-link>
                        </i>
                        <i v-else style="font-style: normal">
                          {{scope.row.bean.name}}
                        </i>
                      </template>
                    </el-table-column>
                    <el-table-column
                      prop="bean.remark"
                      label="备注"
                      width="300">
                    </el-table-column>
                    <el-table-column
                      prop="score"
                      label="分数"
                      width="60">
                    </el-table-column>
                  </el-table>
                </el-card>
              </el-tab-pane>
            </el-tabs>
          </el-col>
          <el-col :span="2">
            <el-button type="danger" @click="open" >退课</el-button>
          </el-col>
        </el-card>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import { getLoading } from '../../loading'
export default {
  name: "StudentCourseDetail",
  mounted: function () {
    this.init()
  },
  methods: {
    init() {
      this.tableData = []
      this.coursewares = []
      this.homework = []
      this.grades = []
      const an = this.$route.query.activeName
      if (an) {
        this.activeName = an
      }
      this.selectCourseId = this.$route.params.select_course_id
      this.getInfo()
      this.getMyCourses()
      this.getCourseDetail()
    },
    open() {
      this.$confirm('是否退选该课程?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$axios({
          method: 'post',
          url: 'http://localhost:8080/vue/student/select_course/quit',
          params: {selectCourseId: this.selectCourseId}
        }).then(function (res) {
          const info = res.data
          console.log(info)
          this.$message.success('从 ' + info.name + ' ' + info.term + ' 退课成功')
          this.$router.push('/student_course')
        }.bind(this)).catch(function (err) {
          if (err.response.status === 401) {
            this.$router.push('/login_register')
          } else if (err.response.status === 402) {
            this.$message.error('您未选择此课程')
            this.$router.go(-1)
          }
        }.bind(this))
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消操作'
        })
      })
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
    },
    getCourseDetail () {
      const loading = getLoading(this)
      this.$axios({
        method: 'get',
        url: 'http://localhost:8080/vue/student/course_detail',
        params: {selectCourseId: this.selectCourseId}
      }).then(function (res) {
        const info = res.data
        loading.close()
        console.log(info)
        this.course.name = info.course.name
        this.course.term = info.course.term
        this.course.classId = info.course.classId
        this.course.teacher = info.course.teacher
        this.coursewares = info.coursewares.coursewares
        this.grades = info.grade.grades
        this.homework = info.homework_list.homeworkBeans
      }.bind(this)).catch(function (err) {
        loading.close()
        if (err.response.status === 401) {
          this.$router.push('/login_register')
        } else if (err.response.status === 402) {
          this.$message.error('您未选择此课程')
          console.log(this.$route)
          this.$router.go(-1)
        }
      }.bind(this))
    }
  },
  watch: {
    '$route' () {
      // 此处写router变化时，想要初始化或者是执行的方法......
      this.init()
    }
  },
  data () {
    return {
      fit: 'cover',
      url: 'http://localhost:8080/img/portrait/default portrait.png',
      name: '',
      tableData: [],
      activeName: 'first',
      loading: true,
      selectCourseId: 0,
      course: {
        name: '',
        term: '',
        classId: 0,
        teacher: ''
      },
      coursewares: [],
      grades: [],
      homework: []
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
  .el-icon-download {
    font-size: 22px;
  }
  .el-icon-view {
    font-size: 22px;
  }
</style>
