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
            <i class="el-icon-menu" style="color: #409EFF"></i>
            <i class="course" style="font-weight: bold; font-style: normal; color: #409EFF; font-size: 18px">创建课程</i>
          </template>
        </el-menu-item>
        <el-menu-item index="/teacher_main">
          <template slot="title">
            <i class="el-icon-menu" style="color: #409EFF"></i>
            <i class="course" style="font-weight: bold; font-style: normal; color: #409EFF; font-size: 18px">发布课程</i>
          </template>
        </el-menu-item>
        <el-menu-item index="/teacher_main">
          <template slot="title">
            <i class="el-icon-menu" style="color: #409EFF"></i>
            <i class="course" style="font-weight: bold; font-style: normal; color: #409EFF; font-size: 18px">我的课程</i>
          </template>
        </el-menu-item>
      </el-menu>
    </el-aside>


  </el-container>
</template>

<script>
    export default {
        name: "TeacherInfo",
        mounted: function () {
          this.getInfo()
          this.getMyCourses()
        },
        data () {
          return {
            loading: true,
            courses: [],
            fit: 'cover',
            url: 'http://localhost:8080/img/portrait/default portrait.png',
            imageUrl: '',
            labelPosition: 'right',
            formLabelAlign: {
              name: '',
              studentID: '',
              email: ''
            },
            name: '',
            portrait: null
          }
        },
        methods: {
          handleAvatarChange (file) {
            this.imageUrl = URL.createObjectURL(file.raw)
          },
          beforeAvatarUpload (file) {
            this.portrait = file
            return false
          },
          getInfo () {

          },
          modify () {
            let formData = new FormData()
            formData.append('portrait', this.portrait)
            formData.append('name', this.formLabelAlign.name)
            console.log(formData.get('portrait'))
            this.$axios({
              method: 'post',
              url: 'http://localhost:8080/vue/student/info',
              headers: {
                'Content-Type': 'multipart/form-data'
              },
              data: formData
            }).then(function (res) {
              console.log(res)
              this.$message.success('修改成功')
              this.getInfo()
            }.bind(this)).catch(function (err) {
              console.log(err)
              this.$message.error('修改失败：服务器繁忙，请稍后重试！')
              this.getInfo()
            }.bind(this))
          }
        }
    }
</script>

<style scoped>
  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
  .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }
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
