<template xmlns="http://www.w3.org/1999/html">
  <el-container style="height: 590px; border: 1px solid #eee">
    <el-aside width="200px" class="el-aside">
      <el-menu :default-openeds="['1']" default-active="/teacher_create_Course" style="height: 588px">
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
              <el-image  :src="url" :fit="fit" style="width: 30px; height: 30px;"></el-image>
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

      <el-main class="createCourse" style="margin-top: 10px">
        <h2>创建课程</h2>
        <el-col :span="6"><br/></el-col>
        <el-col :span="15">
          <el-card class="box-card" style="width: 80%">
            <el-form ref="course" :model="course" label-width="80px">
              <el-form-item label="课程名称">
                <el-input v-model="course.courseName"></el-input>
              </el-form-item>
              <el-form-item style="text-align: left" label="课件">
                <el-upload class="upload-demo" drag action :limit="20" :show-file-list="false" :before-upload="beforeUpload" :file-list="fileList">
                  <i class="el-icon-upload" style="margin-top: 20px"></i>
                  <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
                  <div class="el-upload__tip" slot="tip" style="margin-top: -10px">
                    文件类型限制：ppt/pdf，且大小不超过 10 MB
                  </div>
                </el-upload>
                <br>
                <div v-for="file in fileList" class="el-icon-document">
                  {{file.name}}
                  <el-button type="text" v-on:click="handleRemove($index)">删除</el-button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                  <br>
                </div>
              </el-form-item>
              <el-button type="primary" @click="createCourse">创建课程</el-button>
            </el-form>
          </el-card>
        </el-col>
      </el-main>

    </el-container>
  </el-container>
</template>

<script>
    export default {
      name: "TeacherCreateCourse",
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
        handleRemove(index) {
          this.fileList.splice(index, 1)
        },
        beforeUpload (file) {
          const fileSize = (file.size / 1024).toFixed(0)
          if (fileSize > 10 * 1024) {
            this.$message.error('文件大小限制：' + 10 + 'MB，你的文件大小：' + (fileSize / 1024).toFixed(2) + "MB");
            return;
          }
          this.fileList.push(file);
          console.log(file)
        },
        createCourse() {

        }
      },
      data() {
        return {
          fit: 'cover',
          url: 'http://localhost:8080/img/portrait/default portrait.png',
          name: '',
          course: {
            courseName: '',
            creator: '',

          },
          file: null,
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
    margin-top: 5px;
    margin-left: 10px;
  }
</style>
