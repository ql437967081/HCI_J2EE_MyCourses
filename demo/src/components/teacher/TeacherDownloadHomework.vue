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

      <el-main>
        <el-card class="box-card">
          <el-table
            :data="tableData"
            border
            style="width: 90%"
          >
            <el-table-column
              prop="title"
              label="标题"
              width="150">
            </el-table-column>
            <el-table-column
              prop="content"
              label="内容"
              width="120">
            </el-table-column>
            <el-table-column
              prop="date"
              label="截止时间"
              width="120">
            </el-table-column>
            <el-table-column
              prop="size"
              label="大小限制"
              width="120">
            </el-table-column>
            <el-table-column
              prop="type"
              label="文件类型"
              width="300">
            </el-table-column>
            <el-table-column
              prop="zip"
              label="提交人数"
              width="120">
            </el-table-column>
            <el-table-column
              label="下载作业"
              style="width: 100%"
             >
              <template slot-scope="scope">
                <el-button @click="handleClick(scope.row)" type="text" size="small">下载</el-button>
                <el-button type="text" size="small">编辑</el-button>
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
  name: 'TeacherDownloadHomework',
  methods: {
    handleClick (row) {
      alert(row.zip)
    }
  },
  data () {
    return {
      tableData: [{
        title: '作业一',
        content: '你叫什么名字',
        date: '2019-06-23',
        size: '2MB',
        type: 'pdf',
        zip: 200333
      }, {
        title: '作业二',
        content: '你叫什么名字',
        date: '2019-06-23',
        size: '2MB',
        type: 'pdf',
        zip: 200333
      }, {
        title: '作业三',
        content: '你叫什么名字',
        date: '2019-06-23',
        size: '2MB',
        type: 'pdf',
        zip: 200333
      }, {
        title: '作业四',
        content: '你叫什么名字',
        date: '2019-06-23',
        size: '2MB',
        type: 'pdf',
        zip: 200333
      }]
    }
  }
}

</script>

<style scoped>

</style>
