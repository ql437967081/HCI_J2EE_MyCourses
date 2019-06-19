<template>
  <el-container style="height: 590px; border: 1px solid #eee">
    <el-aside width="200px" class="el-aside">
      <el-menu :default-openeds="['1', 'student_course']" default-active="/student_course" style="height: 588px">
        <el-link href="/#/student_main">
          <el-menu-item index="/student_main">
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

      <el-main>
        <el-col :span="4"><br/></el-col>
        <el-col :span="16">
          <el-card class="box-card">
            <el-table
              :data="tableData"
              height="450px"
              border
              style="width: 1041px;">
              <el-table-column
                prop="course"
                label="课程"
                width="230">
              </el-table-column>
              <el-table-column
                prop="semester"
                label="学期"
                width="300">
              </el-table-column>
              <el-table-column
                prop="teacher"
                label="教师"
                width="180">
              </el-table-column>
              <el-table-column
                prop="condition"
                label="状态"
                width="180" >
              </el-table-column>
              <el-table-column
                prop="link"
                label= "操作"
                width="150">
              </el-table-column>
            </el-table>
          </el-card>
        </el-col>
        <el-col :span="4"><br/></el-col>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>

export default {
  name: 'StudentMain',
  mounted: function () {
    this.getInfo()
  },
  methods: {
  },
  data () {
    return {
      fit: 'cover',
      url: 'http://localhost:8080/img/portrait/default portrait.png',
      name: '',
      tableData: [ {
        course: '111',
        semester: '123',
        teacher: 'wsql',
        condition: 'jiaru',
        link: 'hhh'
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
  .box-card {
    height: 485px;
    width: 1085px;
  }
</style>
