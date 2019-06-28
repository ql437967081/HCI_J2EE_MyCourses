<template>
  <el-container style="height: 590px; border: 1px solid #eee">
    <el-aside width="200px" class="el-aside">
      <el-menu :default-openeds="['/teacher_course']" default-active="/teacher_courses/courseId" style="height: 588px">
        <el-link href="/#/teacher_main">
          <el-menu-item index="/teacher_main">
            <template slot="title">
              <i class="el-icon-s-home" style="color: grey"></i>
              <i class="course" style="font-weight: bold; font-style: normal; color: grey; font-size: 18px">主页</i>
            </template>
          </el-menu-item>
        </el-link>

        <el-submenu index="/teacher_course">
          <template slot="title">
            <i class="el-icon-menu"></i>
            <i class="course" style="font-weight: bold; font-style: normal; color: grey; font-size: 18px">我的课程</i>
          </template>
          <el-menu-item-group v-loading="loading">
            <el-menu-item index="/teacher_courses/courseId" v-for="course in createdCourses">
              <el-link :href="'/#/teacher_course/' + course.id">
                <i v-if="course.id == createdCourseId" style="color: #409EFF; font-size: 15px; font-style: normal;">
                  {{course.courseName}}
                </i>
                <i v-else style="font-size: 15px; font-style: normal;">{{course.courseName}}</i>
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
        <el-col :span="2"><br></el-col>
        <el-col :span="10">
          <el-card class="box-card" style="width: 500px">
            <h2>{{ currentCourse.name }}</h2>
            <span>创建者：{{currentCourse.creator}}</span><br><br>
            <el-form align="center" label-width="80px">
              <el-form-item style="text-align: left" label="课件">
                <el-upload class="upload-demo" drag action :limit="20" :show-file-list="false" :before-upload="beforeUpload" :file-list="coursewares">
                  <i class="el-icon-upload" style="margin-top: 20px"></i>
                  <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
                  <div class="el-upload__tip" slot="tip" style="margin-top: -20px">
                    &nbsp;&nbsp;&nbsp;文件类型限制：ppt/pdf，且大小不超过 10 MB
                  </div>
                </el-upload>
                <el-table :data="coursewares" border style="width: 80%" align="center" header-algin="center">
                  <el-table-column prop="name" label="已上传课件">
                    <template slot-scope="scope">
                      <i v-if="scope.row.status == 'success'">
                      <el-link :download="scope.row.name" target="_blank" :href="'http://localhost:8080' + scope.row.location">
                        {{scope.row.name}}
                      </el-link>
                      </i>
                      <i v-else> {{scope.row.name}}</i>
                      <el-button type="text" v-on:click="handleRemove(scope.row)">&nbsp;&nbsp;删除</el-button>&nbsp;&nbsp;&nbsp;&nbsp;
                    </template>
                  </el-table-column>
                </el-table>
              </el-form-item>
              <el-button type="primary" @click="uploadCourseware">提交</el-button>
            </el-form>
          </el-card>
        </el-col>
        <el-col :span="6">
          <el-card class="box-card" style="width: 100%">
            <el-table :data="publishedCourses" border style="width: 80%" align="center" header-algin="center">
              <el-table-column prop="term" label="学期" width="218">
                <template slot-scope="scope">
                  <el-link :href="'/#/teacher_course/' + createdCourseId + '/term_course/' + scope.row.id">
                    {{scope.row.term}}
                  </el-link>
                </template>
              </el-table-column>
            </el-table>
          </el-card>
        </el-col>
        <el-col :span="6"><br></el-col>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
  import {getLoading} from '../../loading'
    export default {
      name: "TeacherCourse",
      mounted: function () {
        this.init()
      },
      methods: {
        init() {
          this.createdCourseId = this.$route.params.course_id
          this.createdCourses = []
          this.coursewares = []
          this.currentCourse = {}
          this.publishedCourses = []
          this.getInfo()
          this.getMyCreatedCourses()
          this.getCourse()
        },
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
        getMyCreatedCourses () {
          this.$axios({
            method: 'get',
            url: 'http://localhost:8080/vue/teacher/course'
          }).then(function (res) {
            this.loading = false
            const info = res.data
            console.log(info)
            for(let key in info){
              this.createdCourses.push({
                id: key,
                courseName: info[key]
              })
            }
          }.bind(this)).catch(function (err) {
            console.log(err)
            if (err.response.status === 401) {
              this.$router.push('/login_register')
            }
          }.bind(this))
        },
        getCourse() {
          this.$axios({
            method: 'get',
            url: 'http://localhost:8080/vue/teacher/course/'+this.createdCourseId
          }).then(function (res) {
             this.loading = false
             this.currentCourse = res.data
             console.log(this.currentCourse)
             if(this.currentCourse.status === 'SUBMIT') {
               this.$message('课程' + this.currentCourse.name + '的创建请求已提交MyCourses主管审批，请耐心等待！')
               this.$router.push('/teacher_main')
             }
             else if(this.currentCourse.status === 'REJECTED') {
               this.$message.error('课程' + this.currentCourse.name + '未通过MyCourses主管审批！')
               this.$router.push('/teacher_main')
             }
             else if(this.currentCourse.status === 'REJECTED_READ') {
               this.$message.error('课程不存在或未通过MyCourses主管审批！')
               this.$router.push('/teacher_main')
             }
             this.coursewares = this.currentCourse.coursewares
             console.log(this.coursewares)
             this.publishedCourses = this.currentCourse.terms
             console.log(this.publishedCourses)
          }.bind(this)).catch(function (err) {
            console.log(err)
            if (err.response.status === 401) {
              this.$router.push('/login_register')
            }
          }.bind(this))
        },
        uploadCourseware() {
          let formData = new FormData()
          formData.append('id', this.createdCourseId)
          for(let file of this.fileList) {
            formData.append('files', file)
          }
          console.log(formData.getAll('files'))
          this.$axios({
            method: 'post',
            url: 'http://localhost:8080/vue/teacher/course/' + this.createdCourseId + '/add_wares',
            headers: {
              'Content-Type': 'multipart/form-data'
            },
            data: formData
          }).then(function (res) {
            this.$router.push('/teacher_course/'+this.createdCourseId)
          }.bind(this)).catch(function (err) {
            console.log(err)
            if (err.response.status === 401) {
              this.$router.push('/login_register')
            } else if (err.response.status === 402) {
              this.$message.error('您未选择此课件')
              this.$router.go(-1)
            } else if (err.response.status === 403) {
              this.$message.error('课件上传有误')
              this.$router.go(-1)
            }
          }.bind(this))
        },
        handleRemove(courseware) {
          this.$confirm('确定删除所选课件吗？', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            if(courseware.status === 'success') {
              let wareId = courseware.id
              this.$axios({
                method: 'post',
                url: 'http://localhost:8080/vue/teacher/course/' + this.createdCourseId + '/remove/' + wareId,
                params: {
                  id: wareId
                }
              }).then(function (res) {
                this.$router.push('/teacher_course/'+this.createdCourseId)
              }.bind(this)).catch(function (err) {
                console.log(err)
                if (err.response.status === 401) {
                  this.$router.push('/login_register')
                } else if (err.response.status === 402) {
                  this.$message.error('您未选择此课件')
                  this.$router.go(-1)
                } else if (err.response.status === 403) {
                  this.$message.error('课件移除有误')
                  this.$router.go(-1)
                }
              }.bind(this))
            }
            else {
              this.coursewares.remove(courseware)
            }
          }).catch(() => {
            this.$message('已取消删除')
          })
        },
        beforeUpload (file) {
          this.fileList.push(file)
          this.coursewares.push({
            name: file.name,
            status: 'fail'
          })
          console.log(file)
          console.log(this.coursewares)
        }
      },
      watch: {
        '$route'() {
          // 此处写router变化时，想要初始化或者是执行的方法......
          this.init()
        },
      },
      data() {
        return {
          fit: 'cover',
          url: 'http://localhost:8080/img/portrait/default portrait.png',
          name: '',
          loading: true,
          createdCourseId: '',
          coursewares: [],
          currentCourse: {},
          createdCourses: [],
          publishedCourses: [],
          fileList: []
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
    width: 245px !important;
    margin-top: 5px;
    margin-left: 10px;
  }
</style>
