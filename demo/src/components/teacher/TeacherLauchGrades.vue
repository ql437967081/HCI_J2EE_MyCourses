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
            <el-link href="/#/student_course">
              <i class="el-icon-menu" style="color: #409EFF"></i>
              <i class="course" style="font-weight: bold; font-style: normal; color: #409EFF; font-size: 18px">学期信息</i>
            </el-link>
          </template>
        </el-menu-item>
        <el-menu-item index="/lauchhomework">
          <template slot="title">
            <el-link href="/#/student_course">
              <i class="el-icon-menu" style="color: #409EFF"></i>
              <i class="course" style="font-weight: bold; font-style: normal; color: #409EFF; font-size: 18px">发布作业</i>
            </el-link>
          </template>
        </el-menu-item>
        <el-menu-item index="/student_course">
          <template slot="title">
            <el-link href="/#/student_course">
              <i class="el-icon-menu" style="color: #409EFF"></i>
              <i class="course" style="font-weight: bold; font-style: normal; color: #409EFF; font-size: 18px">群发邮件</i>
            </el-link>
          </template>
        </el-menu-item>
        <el-menu-item index="/student_course">
          <template slot="title">
            <el-link href="/#/student_course">
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

      <el-main style="position: relative">
        <div style="width: 45%; height: 100%;position: absolute; left:20px; ">
          <el-card class="box-card">
            <el-form :model="form" ref="form" label-width="100px">
              <el-form-item label="类型" prop="type">
                <el-select v-model="form.value" placeholder="请选择" style="float: left">
                  <el-option
                    v-for="item in option1s"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="备注" prop="remark">
                <el-input type="textarea" v-model="form.remark"></el-input>
              </el-form-item>
              <el-form-item label="是否公开" prop="public">
                <el-select v-model="form.public" placeholder="请选择" style="float: left">
                  <el-option
                    v-for="item in option2s"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="成绩表" size="small" prop="size">
                <el-upload
                  class="upload-demo"
                  drag
                  action="https://jsonplaceholder.typicode.com/posts/"
                  multiple
                  style="float: left ;"
                  :before-upload="beforeUpload"
                  :show-file-list="false"
                >
                  <i class="el-icon-upload"></i>
                  <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
                  <div class="el-upload__tip" slot="tip" style="margin-top: 0.1px">只能上传jpg/png文件，且不超过500kb</div>
                </el-upload>
              </el-form-item>
              <el-form-item label="文件类型" prop="value">
                <el-select v-model="form.value" placeholder="请选择" style="float: left">
                  <el-option
                    v-for="item in option2s"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" @click="onSubmit" style="float: left">立即发布</el-button>
                <el-button @click="resetForm('form')" style="float: left">重置</el-button>
              </el-form-item>
            </el-form>
          </el-card>
        </div>
        <div style="width: 45%; height: 100%;  position:absolute;left:50%;">
          <el-card class="box-card">
          <span>以往发布的成绩</span>
          <el-table
            :data="tableData"
            border
            style="width: 90%"
          >
            <el-table-column
              prop="file"
              label="成绩表"
              style="width: 30%"
              >
            </el-table-column>
            <el-table-column
              prop="remarks"
              label="备注"
              style="width: 40%"
             >
            </el-table-column>
            <el-table-column
              prop="ispublic"
              label="是否公开"
              style="width: 30%"
              >
            </el-table-column>
          </el-table>
          </el-card>
        </div>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import { getLoading } from '../../loading'
export default {
  name: 'TeacherLauchGrades',
  mounted: function () {
    this.courseId = this.$route.params.couseId
    // 方便测试，之后要删除
    this.courseId = 5
    this.getHomeworkList()
  },
  data () {
    return {
      tableData: [{
        file: '作业一',
        remarks: '作业一部分成绩',
        ispublic: '否'
      }, {
        file: '作业一',
        remarks: '作业一部分成绩',
        ispublic: '否'
      }, {
        file: '作业一',
        remarks: '作业一部分成绩',
        ispublic: '否'
      }, {
        file: '作业一',
        remarks: '作业一部分成绩',
        ispublic: '否'
      }],
      form: {
        type: '',
        remark: '',
        public: '',
        size: '',
        value: ''
      },
      option1s: [{
        value: '选项1',
        label: '考试',
        id: '100000'
      }, {
        value: '选项2',
        label: '作业',
        id: '10000000'
      }],
      option2s: [{
        value: '选项1',
        label: '是'
      }, {
        value: '选项2',
        label: '否'
      }],
      sheet: null,
      courseId: ''
    }
  },
  methods: {
    getHomeworkList () {
      alert(this.courseId)
      const loading = getLoading(this)
      this.$axios({
        method: 'get',
        url: 'http://localhost:8080/vue/teacher/coursehomework',
        params: {
          id: this.courseId
        }
      }).then(function (res) {
        loading.close()
        alert('成功')
        const info = res.data
        let index = 3
        let list = info.homeworkBeans
        alert(info.homeworkBeans.length)
        for (let homework of list) {
          console.log(homework)
          this.option1s.push({
            value: '选项' + index,
            label: homework.title,
            id: homework.id
          })
          index++
        }
        if (info) {
          this.$message.success('作业获取成功！')
        } else {
          this.$message.error('作业未发布')
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
      })
    },
    beforeUpload (file) {
      this.sheet = file
    },

    onSubmit () {
      this.$confirm('确定发布这次成绩吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const loading = getLoading(this)
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
