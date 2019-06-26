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
        <el-menu-item index="/student_course">
          <template slot="title">
            <el-link href="/#/TeacherTermInfo">
              <i class="el-icon-menu" style="color: #409EFF"></i>
              <i class="course" style="font-weight: bold; font-style: normal; color: #409EFF; font-size: 18px">学期信息</i>
            </el-link>
          </template>
        </el-menu-item>
        <el-menu-item index="/lauchhomework">
          <template slot="title">
            <el-link href="/#/TeacherLauchHomework">
              <i class="el-icon-menu" style="color: #409EFF"></i>
              <i class="course" style="font-weight: bold; font-style: normal; color: #409EFF; font-size: 18px">发布作业</i>
            </el-link>
          </template>
        </el-menu-item>
        <el-menu-item index="/student_course">
          <template slot="title">
            <el-link href="/#/TeacherSendMails">
              <i class="el-icon-menu" style="color: #409EFF"></i>
              <i class="course" style="font-weight: bold; font-style: normal; color: #409EFF; font-size: 18px">群发邮件</i>
            </el-link>
          </template>
        </el-menu-item>
        <el-menu-item index="/student_course">
          <template slot="title">
            <el-link href="/#/TeacherDownloadHomework">
              <i class="el-icon-menu" style="color: #409EFF"></i>
              <i class="course" style="font-weight: bold; font-style: normal; color: #409EFF; font-size: 18px">下载作业</i>
            </el-link>
          </template>
        </el-menu-item>
        <el-menu-item index="/student_course">
          <template slot="title">
            <el-link href="/#/student_course">
              <i class="el-icon-menu" style="color: #409EFF"></i>
              <i class="course" style="font-weight: bold; font-style: normal; color: #409EFF; font-size: 18px">发布成绩</i>
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
            <!--需要在el-formitem上添加prop属性才能重置-->
            <el-form :model="form" ref="form" label-width="200px">
              <el-form-item label="作业标题" prop="name">
                <el-input v-model="form.name"></el-input>
              </el-form-item>
              <el-form-item label="作业内容" prop="content">
                <el-input type="textarea" v-model="form.content"></el-input>
              </el-form-item>
              <el-form-item label="截止时间">
                <el-col :span="11">
                  <el-form-item prop="date1">
                  <el-date-picker type="date" placeholder="选择日期" v-model="form.date1" style="width: 100%;" value-format="yyyy-MM-dd"></el-date-picker>
                  </el-form-item>
                </el-col>
                <el-col class="line" :span="2">-</el-col>
                <el-col :span="11">
                  <el-form-item prop="date2">
                  <el-time-picker placeholder="选择时间" v-model="form.date2" style="width: 100%;" value-format="HH:mm:ss"></el-time-picker>
                  </el-form-item>
                </el-col>
              </el-form-item>
              <el-form-item label="大小限制" size="small" prop="size">
                <el-input v-model="form.size" style="width: 220px ; float: left"></el-input><span style="float: left">MB</span>
              </el-form-item>
              <el-form-item label="文件类型" prop="value">
                <el-select v-model="form.value" placeholder="请选择" style="float: left">
                  <el-option
                    v-for="item in options"
                    :key="item.value"
                    :label="item.label"
                    :value="item.label">
                  </el-option>
                </el-select>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" @click="onSubmit" style="float: left">立即创建</el-button>
                <el-button @click="resetForm('form')" style="float: left">重置</el-button>
              </el-form-item>
            </el-form>
        </el-card>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import { getLoading } from '../../loading'
export default {
  data () {
    return {
      form: {
        name: '',
        content: '',
        date1: '',
        date2: '',
        size: '',
        value: ''
      },
      options: [{
        value: '选项1',
        label: '.txt'
      }, {
        value: '选项2',
        label: '.doc'
      }, {
        value: '选项3',
        label: '.pdf'
      }, {
        value: '选项4',
        label: '.cpp'
      }, {
        value: '选项5',
        label: '.java'
      }],
      courseId: ''
    }
  },
  mounted: function () {
    this.courseId = this.$route.params.couseId
  },
  methods: {
    onSubmit () {
      this.$confirm('确定发布这次作业吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const loading = getLoading(this)
        // 这里为了测试方便
        this.courseId = 5
        console.log(this.form.date1)
        console.log(this.form.date2)
        this.$axios({
          method: 'post',
          url: 'http://localhost:8080/vue/teacher/lauchhomework',
          params: {
            id: this.courseId,
            title: this.form.name,
            content: this.form.content,
            ddl: this.form.date1 + 'T' + this.form.date2,
            file_max_size: this.form.size,
            file_type: this.form.value
          }
        }).then(function (res) {
          loading.close()
          const info = res.data
          if (info) {
            this.$message.success('作业发布成功！')
            this.resetForm('form')
          } else {
            this.$message.error('作业发布失败')
            this.resetForm('form')
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
    },
    resetForm (formName) {
      this.$refs[formName].resetFields()
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
