<template>
  <el-container style="height: 590px; border: 1px solid #eee">
    <el-aside width="200px" class="el-aside">
      <el-menu :default-openeds="['1', 'student_course']" style="height: 588px">
        <el-link href="/#/student_main">
          <el-menu-item index="student_main">
            <template slot="title">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
              <i class="el-icon-s-home"></i>主页&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            </template>
          </el-menu-item>
        </el-link>
        <el-submenu index="student_course">
          <template slot="title">
            <el-link href="/#/student_course">
              <i class="el-icon-menu"></i>课程
            </el-link>
          </template>
          <el-menu-item-group>
            <el-menu-item index="2-1">选项1</el-menu-item>
            <el-menu-item index="2-2">选项2</el-menu-item>
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

      <el-main class="info" style="margin-top: 10px">
        <el-col :span="5"><br/></el-col>
        <el-col :span="16">
          <el-card class="box-card" style="width: 80%">
            <div style="font-size: 10px; color: #B3C0D1; ">
              <i class="el-icon-warning"></i>点击头像可进行更换
            </div>
              <el-upload
                accept="image/*"
                class="avatar-uploader"
                action="https://jsonplaceholder.typicode.com/posts/"
                :show-file-list="false"
                :on-change="handleAvatarChange"
                :before-upload="beforeAvatarUpload">
                <img v-if="imageUrl" :src="imageUrl" class="avatar">
                <i v-else class="el-icon-plus avatar-uploader-icon"></i>
              </el-upload>
              <el-form :label-position="labelPosition" label-width="80px" :model="formLabelAlign">
                <el-form-item label="姓名">
                  <el-input v-model="formLabelAlign.name" style="width: 90%"></el-input>
                </el-form-item>
              </el-form>
              <el-form :label-position="labelPosition" label-width="80px" :model="formLabelAlign" >
                <el-form-item label="学号">
                  <el-input v-model="formLabelAlign.studentID" style="width: 90%" :disabled="true"></el-input>
                </el-form-item>
                <el-form-item label="邮箱">
                  <el-input v-model="formLabelAlign.email" style="width: 90%" :disabled="true"></el-input>
                </el-form-item>
                <el-button type="primary" @click="modify">保存修改</el-button>
                <el-button @click="getInfo" style="margin-left: 30px">重置</el-button>
              </el-form>
          </el-card>
        </el-col>
        <el-col :span="5"><br/></el-col>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import { getLoading } from '../loading'

export default {
  name: 'StudentInfo',
  mounted: function () {
    this.getInfo()
  },
  data () {
    return {
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
      const loading = getLoading(this)
      this.$axios({
        method: 'get',
        url: 'http://localhost:8080/vue/student/info'
      }).then(function (res) {
        const info = res.data
        loading.close()
        this.formLabelAlign.studentID = info.id
        this.formLabelAlign.email = info.email
        this.name = this.formLabelAlign.name = info.name
        this.url = this.imageUrl = 'http://localhost:8080' + info.portrait
      }.bind(this)).catch(function (err) {
        if (err.response.status === 401) {
          this.$router.push('/login_register')
        }
      }.bind(this))
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
