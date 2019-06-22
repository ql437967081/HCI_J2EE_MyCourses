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
              <i class="el-icon-menu" style="color: grey"></i>
              <i class="course" style="font-weight: bold; font-style: normal; color: grey; font-size: 18px">课程</i>
            </el-link>
          </template>
          <el-menu-item-group v-loading="loading">
            <el-menu-item v-for="course in tableData" v-if="course.condition" >
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

      <el-main>
        <el-card class="box-card">
          <el-page-header @back="goBack" content="作业详情"></el-page-header>
          <div><br></div>
          <el-col :span="5"><br></el-col>
          <el-col :span="13">
            <el-form ref="form" :label-position="position" label-width="120px" :model="homeworkInfo" size="mini">
              <el-form-item label="作业名称" style="font-weight: bold">
                <label class="text-wrapper">{{homeworkInfo.title}}</label>
              </el-form-item>
              <el-form-item label="作业内容" style="font-weight: bold">
                <el-input
                  type="textarea"
                  v-model="homeworkInfo.content"
                  readonly
                  autosize>
                </el-input>
              </el-form-item>
              <el-form-item label="作业截止时间" style="font-weight: bold">
                <label class="text-wrapper">{{homeworkInfo.ddl}}</label>
              </el-form-item>
              <el-form-item label="最新提交作业" style="font-weight: bold">
                <label class="text-wrapper">
                  <el-link
                    v-if="homeworkInfo.submitName"
                    :download="homeworkInfo.submitName"
                    target="_blank"
                    :href="'http://localhost:8080' + homeworkInfo.location"
                    style="color: #409EFF">
                    {{homeworkInfo.submitName}}
                  </el-link>
                  <i v-else style="color: #B3C0D1">无</i>
                </label>
              </el-form-item>
              <el-form-item label="上传作业" style="font-weight: bold">
                <div v-if="file" class="el-icon-document">
                  &nbsp;&nbsp;{{file.name}}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                  <el-button type="primary" @click="submit">提交</el-button>
                </div>
                <div v-else style="color: #B3C0D1">未选择文件&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
                <el-upload
                  class="upload-demo"
                  drag
                  action
                  :limit="1"
                  :show-file-list="false"
                  :before-upload="beforeUpload">
                  <i class="el-icon-upload" style="margin-top: 20px"></i>
                  <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
                  <div class="el-upload__tip" slot="tip" style="margin-top: -10px">
                    文件类型限制：{{homeworkInfo.fileType}}，且大小不超过{{homeworkInfo.fileMaxSize}}MB
                  </div>
                </el-upload>
              </el-form-item>
            </el-form>
          </el-col>
          <el-col :span="6"><br></el-col>
        </el-card>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import { getLoading } from '../../loading'

export default {
  name: "StudentHomework",
  mounted: function () {
    this.selectCourseId = this.$route.params.select_course_id
    this.homeworkRequestId = this.$route.params.homework_request_id
    this.getInfo()
    this.getMyCourses()
    this.getHomework()
  },
  methods: {
    goBack() {
      this.$router.push('/student_course/' + this.selectCourseId + '?activeName=second')
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
    getHomework () {
      const loading = getLoading(this)
      this.$axios({
        method: 'get',
        url: 'http://localhost:8080/vue/student/course_homework',
        params: {
          selectCourseId: this.selectCourseId,
          homeworkRequestId: this.homeworkRequestId
        }
      }).then(function (res) {
        loading.close()
        const info = res.data
        this.homeworkInfo = info
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
      }.bind(this))
    },
    beforeUpload (file) {
      const fileSize = (file.size / 1024).toFixed(0)
      if (fileSize > this.homeworkInfo.fileMaxSize * 1024) {
        this.$message.error('文件大小限制：' + this.homeworkInfo.fileMaxSize + 'MB，你的文件大小：' + (fileSize / 1024).toFixed(2) + "MB");
        return;
      }
      this.file = file
      console.log(file)
    },
    submit () {
      this.$confirm('确定保存并提交更改的作业吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const loading = getLoading(this)
        this.$axios({
          method: 'post',
          url: 'http://localhost:8080/vue/student/course_homework',
          params: {
            selectCourseId: this.selectCourseId,
            homeworkRequestId: this.homeworkRequestId
          }
        }).then(function (res) {
          loading.close()
          const info = res.data
          console.log(info)
          if (info) {
            this.$message.success('作业提交成功！')
            this.getHomework()
          } else {
            this.$message.error('您已错过截止的提交时间！')
          }
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
        }.bind(this))
      }).catch(() => {
        this.$message('已取消提交')
      })
    }
  },
  data () {
    return {
      selectCourseId: 0,
      homeworkRequestId: 0,
      fit: 'cover',
      url: 'http://localhost:8080/img/portrait/default portrait.png',
      name: '',
      loading: true,
      tableData: [],
      position: 'right',
      homeworkInfo: {
        title: '',
        content: '',
        ddl: '',
        fileMaxSize: 0,
        fileType: '',
        id: 0,
        location: null,
        selectCourseNum: 0,
        submitId: 0,
        submitName: null,
        submitNum: 0
      },
      file: null
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
    width: 1020px;
  }
  .text-wrapper {
    word-break: break-all;
    word-wrap: break-word;
    float: left;
    font-weight: normal;
  }
  .upload-demo {
    float: left;
    font-weight: normal;
  }
</style>
<style>
  /*修改拖动上传的默认样式*/
  .el-upload-dragger {
    height: 135px !important;
    margin-top: 5px;
    margin-left: 10px;
  }
</style>
