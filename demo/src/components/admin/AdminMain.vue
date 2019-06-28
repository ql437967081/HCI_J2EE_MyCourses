<template>
  <el-container style="height: 590px; border: 1px solid #eee">
    <el-aside width="200px" class="el-aside">
      <el-menu :default-openeds="['1']" default-active="/admin_main" style="height: 588px">
        <el-link href="/#/admin_main">
          <el-menu-item index="/admin_main">
            <template slot="title">
              <i class="el-icon-s-home" style="color: #409EFF"></i>
              <i class="course" style="font-weight: bold; font-style: normal; color: #409EFF; font-size: 18px">审批</i>
            </template>
          </el-menu-item>
        </el-link>
        <el-link href="/#/admin_statistic">
          <el-menu-item index="/admin_statistic">
            <template slot="title">
              <i class="el-icon-menu"></i>
              <i class="course" style="font-weight: bold; font-style: normal; color: grey; font-size: 18px">统计</i>
            </template>
          </el-menu-item>
        </el-link>
      </el-menu>
    </el-aside>

    <el-container>
      <el-header class="el-header">
        <el-dropdown>
          <span class="el-dropdown-link">
            <span style="position:relative;">
              <el-image  :src="url" :fit="fit" style="width: 30px; height: 30px;"></el-image>
              <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
              <span style="font-size: 15px; color: aliceblue; position: absolute;top:0; left: 40px">
                {{name}}
                <i class="el-icon-arrow-down el-icon--right"></i>
              </span>
            </span>
          </span>
          <el-dropdown-menu slot="dropdown" style="width: 100px">
            <el-link href="/#/logout">
              <el-dropdown-item style="font-size: 15px">退出登录</el-dropdown-item>
            </el-link>
          </el-dropdown-menu>
        </el-dropdown>
      </el-header>

      <el-main>
        <el-card class="box-card">
          <el-col :span="2"><br></el-col>
          <el-col :span="20">
            <el-tabs v-model="activeName" class="tab">
              <el-tab-pane name="first">
                <span slot="label">
                  <el-badge :value="create.length > 0 ? create.length : ''" class="item">
                    &nbsp;<i class="el-icon-s-order"></i> 课程创建审批&nbsp;
                  </el-badge>
                </span>
                <el-table
                  :data="create"
                  height="380px"
                  border>
                  <el-table-column
                    prop="name"
                    label="课程名称"
                    width="433">
                  </el-table-column>
                  <el-table-column
                    prop="creator"
                    label="申请教师"
                    width="260">
                  </el-table-column>
                  <el-table-column
                    label="操作"
                    width="123">
                    <template slot-scope="scope">
                      <el-button type="text" @click="openCreate(scope.row.id)">审批
                      </el-button>
                      <el-dialog :title="selectedCreation.name" width="30%" :visible.sync="createDialogTableVisible">
                        <div style="font-size: small; margin-top: -30px">
                          是否同意教师 {{selectedCreation.creator}} 创建该课程？
                        </div>
                        <div slot="footer" class="dialog-footer" style="margin-top: -30px">
                          <el-button type="danger" @click="rejectCreation">拒绝</el-button>
                          <el-button type="success" @click="approveCreation">同意</el-button>
                        </div>
                      </el-dialog>
                    </template>
                  </el-table-column>
                </el-table>
              </el-tab-pane>
              <el-tab-pane name="second">
                <span slot="label">
                  <el-badge :value="publish.length > 0 ? publish.length : ''" class="item">
                    &nbsp;<i class="el-icon-s-order"></i> 课程发布申请&nbsp;
                  </el-badge>
                </span>
                <el-table
                  :data="publish"
                  height="380px"
                  border>
                  <el-table-column
                    prop="name"
                    label="课程名称"
                    width="283">
                  </el-table-column>
                  <el-table-column
                    prop="term"
                    label="发布学期"
                    width="250">
                  </el-table-column>
                  <el-table-column
                    prop="publisher"
                    label="申请教师"
                    width="160">
                  </el-table-column>
                  <el-table-column
                    prop="operation"
                    label="操作"
                    width="123">
                    <template slot-scope="scope">
                      <el-button type="text" @click="openPublish(scope.row.id)">审批
                      </el-button>
                      <el-dialog :title="selectedPublish.name" width="30%" :visible.sync="publishDialogTableVisible">
                        <div style="font-size: small; margin-top: -30px">
                          教师：{{selectedPublish.publisher}} &nbsp&nbsp
                          学期：{{selectedPublish.term}}
                          <el-table :data="selectedPublish.classes" border>
                            <el-table-column property="classId" label="班级"></el-table-column>
                            <el-table-column property="limitNum" label="限选人数"></el-table-column>
                          </el-table>
                        </div>
                        <div slot="footer" class="dialog-footer" style="margin-top: -30px">
                          <el-button type="danger" @click="rejectPublish">拒绝</el-button>
                          <el-button type="success" @click="approvePublish">同意</el-button>
                        </div>
                      </el-dialog>
                    </template>
                  </el-table-column>
                </el-table>
              </el-tab-pane>
            </el-tabs>
          </el-col>
          <el-col :span="2"><br></el-col>
        </el-card>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import { getLoading } from '../../loading'

export default {
  name: "AdminMain",
  mounted: function () {
    this.getApprovals()
  },
  methods: {
    getApprovals () {
      const loading = getLoading(this)
      this.$axios({
        method: 'get',
        url: 'http://localhost:8080/vue/admin/approval'
      }).then(function (res) {
        loading.close()
        const info = res.data
        this.create = info.createApproval.courseBeans
        this.publish = info.publishApproval.termCourses
        setTimeout(() => {
          this.getApprovals()
        }, 10000);
      }.bind(this)).catch(function (err) {
        console.log(err)
        if (err.response.status === 401) {
          this.$router.push('/login_register')
        }
      }.bind(this))
    },
    openCreate (courseId) {
      this.$axios({
        method: 'get',
        url: 'http://localhost:8080/vue/admin/approval/create_detail',
        params: {courseId: courseId}
      }).then(function (res) {
        const info = res.data
        if (info.status === 'SUBMIT')  {
          this.createDialogTableVisible = true
          this.selectedCreation = info
        } else if (info.status === 'APPROVED') {
          this.$message.error('错误的审批：课程已通过MyCourses主管审批！')
          this.getApprovals()
        } else {
          this.$message.error('错误的课程：课程不存在或未通过MyCourses主管审批！')
          this.getApprovals()
        }
      }.bind(this)).catch(function (err) {
        console.log(err)
        if (err.response.status === 401) {
          this.$router.push('/login_register')
        }
      }.bind(this))
    },
    approveCreation () {
      this.$axios({
        method: 'post',
        url: 'http://localhost:8080/vue/admin/approval/approve_creation',
        params: {courseId: this.selectedCreation.id}
      }).then(function (res) {
        this.createDialogTableVisible = false
        const info = res.data
        this.$message.success(info.creator + '创建的课程' + info.name + '已被审核通过')
        this.getApprovals()
      }.bind(this)).catch(function (err) {
        console.log(err)
        if (err.response.status === 401) {
          this.$router.push('/login_register')
        }
      }.bind(this))
    },
    rejectCreation () {
      this.$axios({
        method: 'post',
        url: 'http://localhost:8080/vue/admin/approval/reject_creation',
        params: {courseId: this.selectedCreation.id}
      }).then(function (res) {
        this.createDialogTableVisible = false
        const info = res.data
        this.$message(info.creator + '创建课程' + info.name + '的申请已被拒绝！')
        this.getApprovals()
      }.bind(this)).catch(function (err) {
        console.log(err)
        if (err.response.status === 401) {
          this.$router.push('/login_register')
        }
      }.bind(this))
    },
    openPublish (publishCourseId) {
      this.$axios({
        method: 'get',
        url: 'http://localhost:8080/vue/admin/approval/publish_detail',
        params: {publishCourseId: publishCourseId}
      }).then(function (res) {
        const info = res.data
        console.log(info)
        if (info.status === 'SUBMIT')  {
          this.publishDialogTableVisible = true
          this.selectedPublish = info
        } else if (info.status === 'APPROVED') {
          this.$message.error('错误的审批：课程发布已通过MyCourses主管审批！')
          this.getApprovals()
        } else {
          this.$message.error('错误的课程发布：课程未发布或发布未通过MyCourses主管审批！')
          this.getApprovals()
        }
      }.bind(this)).catch(function (err) {
        console.log(err)
        if (err.response.status === 401) {
          this.$router.push('/login_register')
        }
      }.bind(this))
    },
    approvePublish () {
      this.$axios({
        method: 'post',
        url: 'http://localhost:8080/vue/admin/approval/approve_publish',
        params: {publishCourseId: this.selectedPublish.id}
      }).then(function (res) {
        this.publishDialogTableVisible = false
        const info = res.data
        this.$message.success(info.publisher + '发布的课程 ' + info.name + ' ' + info.term + ' 已被审核通过')
        this.getApprovals()
      }.bind(this)).catch(function (err) {
        console.log(err)
        if (err.response.status === 401) {
          this.$router.push('/login_register')
        }
      }.bind(this))
    },
    rejectPublish () {
      this.$axios({
        method: 'post',
        url: 'http://localhost:8080/vue/admin/approval/reject_publish',
        params: {publishCourseId: this.selectedPublish.id}
      }).then(function (res) {
        this.publishDialogTableVisible = false
        const info = res.data
        this.$message(info.publisher + '发布课程' + info.name + ' ' + info.term + '的申请已被拒绝！')
        this.getApprovals()
      }.bind(this)).catch(function (err) {
        console.log(err)
        if (err.response.status === 401) {
          this.$router.push('/login_register')
        }
      }.bind(this))
    }
  },
  data () {
    return {
      fit: 'cover',
      url: 'http://localhost:8080/img/portrait/default portrait.png',
      name: 'MyCourses主管',
      activeName: 'first',
      create: [],
      publish: [],
      createDialogTableVisible: false,
      selectedCreation: {
        coursewares: [],
        creator: '',
        id: 0,
        name: '',
        status: '',
        terms: []
      },
      publishDialogTableVisible: false,
      selectedPublish: {
        classes: [],
        courseId: 0,
        id: 0,
        limit: 0,
        name: '',
        notSubmitHomeworkNum: 0,
        numOfDoctor: 0,
        numOfGraduate: 0,
        numOfHomework: 0,
        numOfUndergraduate: 0,
        publisher: '',
        season: '',
        selectTime: null,
        status: '',
        term: '',
        year: 0
      }
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
    width: 1020px;
  }
  .tab {
    height: 475px;

  }
  .item {
    margin-top: 10px;
  }
</style>
