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
export default {
  name: 'TeacherLauchGrades',
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
        label: '考试'
      }, {
        value: '选项2',
        label: '作业'
      }],
      option2s: [{
        value: '选项1',
        label: '是'
      }, {
        value: '选项2',
        label: '否'
      }]
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
