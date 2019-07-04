<template>
  <el-container style="height: 590px; border: 1px solid #eee">
    <el-aside width="18%" class="el-aside">
      <el-menu :default-openeds="['/teacher_course','/teacher_courses/' + selectedcourse]" default-active="/teacher_courses/courseId" style="height: 588px">
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
            <i class="course" style="font-weight: bold; font-style: normal; color: grey; font-size: 15px">我的课程</i>
          </template>
          <el-submenu v-for="course in createdCourses" :index="'/teacher_courses/' + course.id">
            <template slot="title">
              <el-link :href="'/#/teacher_course/' + course.id">
                <i style="font-style: normal; font-size: 12px">{{course.courseName}}</i>
              </el-link>
            </template>
            <el-menu-item-group v-loading="loading">
              <el-menu-item v-for="publishedCourse in course.terms" :index="'/teacher_term_course/' + publishedCourse.id">
                <el-link :href="'/#/teacher_course/' + course.id + '/term_course/' + publishedCourse.id">
                  <i v-if="publishedCourse.id == coursetermid" style="color: #409EFF; font-size: 12px; font-style: normal;">
                    {{publishedCourse.term}}
                  </i>
                  <i v-else style="font-size: 12px; font-style: normal;">{{publishedCourse.term}}</i>
<!--                  <i style="font-style: normal; font-size: 12px; v-if">{{publishedCourse.term}}</i>-->
                </el-link>
              </el-menu-item>
            </el-menu-item-group>
          </el-submenu>
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
        <el-card class="box-card">
          <el-col :span="2"><br></el-col>
          <el-col :span="20">
            <div style="font-size: xx-large; font-weight: bolder">课程： {{title}}</div>
            <div><br></div>
            <div style="font-size: medium;">
              <!--{{this.course.term}}-->
              {{season}}
              <!--{{this.course.classId}}-->
              &nbsp;&nbsp;
              教师：{{teacher}}
              <!--{{this.course.teacher}}-->
            </div>
            <el-tabs v-model="activeName">
              <el-tab-pane name="first">
                <span slot="label">&nbsp;<i class="el-icon-s-order"></i> 学期信息&nbsp;</span>
                  <el-card>
                    <el-col :span = 8>
                      <el-table
                        :data="tableData"
                        border
                        height="285px"
                      >
                        <el-table-column
                          prop="class"
                          label="班级"
                          width="70"
                        >
                        </el-table-column>
                        <el-table-column
                          prop="count"
                          label="已选人数"
                          width="90"
                        >
                        </el-table-column>
                        <el-table-column
                          prop="number"
                          label="限选人数"
                          width="98"
                        >
                        </el-table-column>
                      </el-table>
                    </el-col>
                    <el-col :span="16">
                      <div style="width: 45%; height: 100%;  position:absolute;left:50%;" id="main">
                      </div>
                    </el-col>
                  </el-card>
              </el-tab-pane>

              <el-tab-pane name="second">
                <span slot="label">&nbsp;<i class="el-icon-message-solid"></i> 发布作业&nbsp;</span>
                <el-card >
                  <!--不设置高度就没有边框-->
                  <el-form :model="form" ref="form" label-width="200px" size="mini" style="height: 285px">
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
              </el-tab-pane>

              <el-tab-pane name="third">
                <span slot="label">&nbsp;<i class="el-icon-s-flag"></i> 群发邮件&nbsp;</span>
                <el-card>
                  <el-col :span="4"><br></el-col>
                  <el-col :span="16">
                    <el-form :model="dynamicValidateForm" ref="dynamicValidateForm" label-width="100px" class="demo-dynamic">
                      <el-form-item
                        prop="title"
                        label="标题"
                        :rules="{
      required: true, message: '内容不能为空', trigger: 'blur'
    }"
                      >
                        <el-input v-model="dynamicValidateForm.title"></el-input>
                      </el-form-item>
                      <el-form-item
                        label="内容"
                        prop="content"
                        :rules="{
      required: true, message: '内容不能为空', trigger: 'blur'
    }"
                      >
                        <el-input v-model="dynamicValidateForm.content" type="textarea"  placeholder="请输入内容" maxlength="1000" rows="5"></el-input>
                      </el-form-item>
                      <el-form-item>
                        <el-button type="primary" @click="submitForm('dynamicValidateForm')">发送</el-button>
                        <el-button @click="resetForm('dynamicValidateForm')">重置</el-button>
                      </el-form-item>
                    </el-form>
                  </el-col>
                </el-card>
              </el-tab-pane>
              <el-tab-pane name="fourth">
                <span slot="label"><i class="el-icon-s-flag"></i> 下载作业</span>
                <el-card>
                  <el-table
                    :data="tableData2"
                    border
                    height="285px"
                  >
                    <el-table-column
                      prop="title"
                      label="标题"
                      width="100">
                      <template slot-scope="scope">
                        <i v-if="scope.row.location" style="font-style: normal">
                          <el-link :download="scope.row.title" target="_blank" @click="handleClick(scope.row)">
                            {{scope.row.title}}
                          </el-link>
                        </i>
                        <i v-else style="font-style: normal">
                          {{scope.row.title}}
                        </i>
                      </template>
                    </el-table-column>
                    <el-table-column
                      prop="content"
                      label="内容"
                      width="253">
                    </el-table-column>
                    <el-table-column
                      prop="date"
                      label="截止时间"
                      width="100">
                    </el-table-column>
                    <el-table-column
                      prop="size"
                      label="大小限制"
                      width="80">
                    </el-table-column>
                    <el-table-column
                      prop="type"
                      label="文件类型"
                      width="80">
                    </el-table-column>
                    <el-table-column
                      prop="zip"
                      label="提交人数"
                      width="80">
                    </el-table-column>
                    <el-table-column
                      label="下载"
                      width="80"
                    >
                      <template slot-scope="scope">
                        <form :id="scope.row.requestid" method="get" target="_blank"
                              :action="'http://localhost:8080/vue/teacher/downloadhomework/' + scope.row.requestid"></form>
                        <el-button @click="handleClick(scope.row)" type="text" size="small" ><i class="el-icon-download"> </i></el-button>
                      </template>
                    </el-table-column>
                  </el-table>
                </el-card>
              </el-tab-pane>
              <el-tab-pane name="fifth">
                <span slot="label"><i class="el-icon-s-flag"></i> 发布成绩</span>
                  <el-card>
                      <el-col :span = 16>
                        <el-form :model="form2" ref="form2" label-width="100px" size="mini" style="height: 280px">
                          <el-form-item label="类型" prop="type">
                            <el-select v-model="form2.type" placeholder="请选择" style="float: left">
                              <el-option
                                v-for="item in option1s"
                                :key="item.value"
                                :label="item.label"
                                :value="item.id">
                              </el-option>
                            </el-select>
                          </el-form-item>
                          <el-form-item label="备注" prop="remark">
                            <el-input type="textarea" v-model="form2.remark" style="width: 80% ;float: left"></el-input>
                          </el-form-item>
                          <el-form-item label="是否公开" prop="public">
                            <el-select v-model="form2.public" placeholder="请选择" style="float: left">
                              <el-option
                                v-for="item in option2s"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value">
                              </el-option>
                            </el-select>
                          </el-form-item>
                          <el-form-item label="成绩表" size="mini" prop="size">
                            <el-upload
                              class="upload-demo"
                              action="https://jsonplaceholder.typicode.com/posts/"
                              multiple
                              style="float: left ;"
                              :before-upload="beforeUpload"
                              :on-change="handleAvatarChange"
                              :show-file-list="false"
                            >
                              <!--<img v-if="imageUrl" :src="imageUrl" class="avatar">-->
                              <!--<i class="el-icon-upload"></i>-->
                              <!--<div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>-->
                              <!--<div class="el-upload__tip" slot="tip" style="margin-top: 0.1px">只能上传jpg/png文件，且不超过500kb</div>-->
                              <el-button size="small" type="primary" style="float: left ;">点击上传</el-button>
                              <div v-if="sheet" class="el-icon-document">
                                &nbsp;&nbsp;{{sheet.name}}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                              </div>
                              <!--<div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>-->
                            </el-upload>
                          </el-form-item>
                          <!--<el-form-item label="文件类型" prop="value">-->
                            <!--<el-select v-model="form2.value" placeholder="请选择" style="float: left">-->
                              <!--<el-option-->
                                <!--v-for="item in option2s"-->
                                <!--:key="item.value"-->
                                <!--:label="item.label"-->
                                <!--:value="item.value">-->
                              <!--</el-option>-->
                            <!--</el-select>-->
                          <!--</el-form-item>-->
                          <el-form-item>
                            <el-button type="primary" @click="onSubmit2" style="float: left">立即发布</el-button>
                            <el-button @click="resetForm('form2')" style="float: left">重置</el-button>
                          </el-form-item>
                        </el-form>
                      </el-col>
                    <el-col :span = 8>
                      <div style=" font-weight: bolder; margin-bottom: 20px">{{gradetitle}}</div>
                      <el-table
                        :data="tableData3"
                        border
                        height="260px"
                      >
                        <el-table-column
                          prop="file"
                          label="成绩表"
                          width="98"
                        >
                          <template slot-scope="scope">
                            <el-link :href="'http://localhost:8080' + scope.row.location">
                              {{scope.row.file}}
                            </el-link>
                          </template>
                        </el-table-column>
                        <el-table-column
                          prop="remarks"
                          label="备注"
                          width="80"
                        >
                        </el-table-column>
                        <el-table-column
                          prop="ispublic"
                          label="是否公开"
                          width="80"
                        >
                        </el-table-column>
                      </el-table>
                    </el-col>
                  </el-card>
              </el-tab-pane>
            </el-tabs>
          </el-col>
          <!--<el-col :span="2">-->
            <!--<el-button type="danger" @click="open" >退课</el-button>-->
          <!--</el-col>-->
        </el-card>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import { getLoading } from '../../loading'
import echarts from 'echarts'
export default {
  name: 'TeacherCourseDetail',
  mounted: function () {
    this.init()
  },
  methods: {
    init () {
      this.getInfo()
      this.selectedcourse = this.$route.params.course_id
      this.coursetermid = this.$route.params.publishedCourse_id
      // 方便测试，之后要删除
      // this.coursetermid = 5
      const an = this.$route.query.activeName
      if (an) {
        this.activeName = an
      }

      this.getMyCreatedCourses()
      // terminfo
      this.termInfo()

      // sendmails
      this.courseId = this.$route.params.publishedCourse_id
      this.teacherId = this.$route.params.teacher_id

      // downloadhomework
      this.courseId = this.$route.params.publishedCourse_id
      // 方便测试，之后要删除
      // this.courseId = 5
      this.getHomeworkList()

      this.courseId = this.$route.params.publishedCourse_id
      // 方便测试，之后要删除
      // this.courseId = 5
      this.getHomeworkList2()
      this.getbeforeGrades()
    },

    termInfo () {
      const loading = getLoading(this)
      this.$axios({
        method: 'get',
        url: 'http://localhost:8080/vue/teacher/terminfo',
        params: {
          id: this.coursetermid
        }
      }).then(function (res) {
        loading.close()
        const info = res.data
        if (info.status === 'SUBMIT') {
          this.$message('课程' + info.name + ' ' + info.term + '的发布请求已提交MyCourses主管审批，请耐心等待！')
          this.$router.push('/teacher_main')
          return
        } else if (info.status === 'REJECTED') {
          this.$message.error('课程' + info.name + ' ' + info.term + '的发布未通过MyCourses主管审批！')
          this.$router.push('/teacher_main')
          return
        } else if (info.status === 'REJECTED_READ') {
          this.$message.error('课程未发布或发布未通过MyCourses主管审批！')
          this.$router.push('/teacher_main')
          return
        }
        let list1 = info.classes
        this.tableData = []
        for (let courseclass of list1) {
          console.log(courseclass)
          this.tableData.push({
            class: courseclass.classId,
            count: courseclass.selectedNum,
            number: courseclass.limitNum
          })
        }
        // alert('termInfo' + this.coursetermid)
        // alert(this.tableData.length)
        this.title = info.name + info.year + '年' + info.season + '学期'
        this.season = info.year + '年' + info.season + '学期'
        this.teacher = info.publisher
        // alert(this.title)
        this.opinion = []
        this.opinion.push('本科生')
        this.opinion.push('研究生')
        this.opinion.push('博士生')
        this.opinionData = []
        this.opinionData.push({
          value: info.numOfUndergraduate,
          name: '本科生'
        })
        this.opinionData.push({
          value: info.numOfGraduate,
          name: '研究生'
        })
        this.opinionData.push({
          value: info.numOfDoctor,
          name: '博士生'
        })
        this.drawPie('main')
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
    drawPie (id) {
      this.charts = echarts.init(document.getElementById(id))
      this.charts.setOption({
        title: {
          text: '选课学生类型分布'
        },
        tooltip: {
          trigger: 'item',
          formatter: '{a} <br/>{b} : {c} ({d}%)'
        },
        legend: {
          orient: 'vertical',
          x: 'right',
          data: this.opinion
        },
        series: [{
          name: '选课人数',
          type: 'pie',
          radius: '50%',
          center: ['38%', '50%'],
          data: this.opinionData
        }]
      })
    },
    // 群发邮件
    submitForm (formName) {
      this.$refs[formName].validate((valid) => {
        const loading = getLoading(this)
        if (valid) {
          // alert(this.dynamicValidateForm.title)
          // alert(this.dynamicValidateForm.content)
          this.$axios({
            method: 'get',
            url: 'http://localhost:8080/vue/teacher/email',
            params: {
              id: this.courseId,
              title: this.dynamicValidateForm.title,
              content: this.dynamicValidateForm.content
            } }).then(function (res) {
            loading.close()
            this.$message.success('邮件发送成功！')
            this.resetForm('dynamicValidateForm')
          }.bind(this)).catch(function (err) {
            console.log(err)
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
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    onSubmit () {
      this.$confirm('确定发布这次作业吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const loading = getLoading(this)
        // // 这里为了测试方便
        // this.courseId = 5
        // console.log(this.form.date1)
        // console.log(this.form.date2)
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
            this.getHomeworkList()
            this.getHomeworkList2()
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
    },
    // downloadhomework
    getHomeworkList () {
      const loading = getLoading(this)
      this.$axios({
        method: 'get',
        url: 'http://localhost:8080/vue/teacher/coursehomework',
        params: {
          id: this.courseId
        }
      }).then(function (res) {
        loading.close()
        const info = res.data
        let list = info.homeworkBeans
        this.tableData2 = []
        for (let homework of list) {
          console.log(homework)
          this.tableData2.push({
            title: homework.title,
            content: homework.content,
            date: homework.ddl,
            size: homework.fileMaxSize + 'MB',
            type: homework.fileType,
            zip: homework.submitNum,
            requestid: homework.id
          })
        }
        // if (info) {
        //   this.$message.success('作业获取成功！')
        // } else {
        //   this.$message.error('作业未发布')
        // }
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
    handleClick (row) {
      // alert(row.requestid)
      document.getElementById('' + row.requestid).submit()
    },

    // lauchgrades
    handleAvatarChange (file) {
      this.imageUrl = URL.createObjectURL(file.raw)
    },
    getHomeworkList2 () {
      // alert(this.courseId)
      const loading = getLoading(this)
      this.$axios({
        method: 'get',
        url: 'http://localhost:8080/vue/teacher/coursehomework',
        params: {
          id: this.courseId
        }
      }).then(function (res) {
        loading.close()
        const info = res.data
        let index = 2
        let list = info.homeworkBeans
        this.option1s = []
        this.option1s.push({
          value: '选项' + 1,
          label: '考试',
          id: -1
        })
        for (let homework of list) {
          console.log(homework)
          this.option1s.push({
            value: '选项' + index,
            label: homework.title,
            id: homework.id
          })
          index++
        }
        // let list2 = info.gradeBeans
        // for (let grade of list2) {
        //   console.log(grade)
        //   this.tableData3.push({
        //     file: grade.name,
        //     remarks: grade.remark,
        //     ispublic: grade.open
        //   })
        // }
        // if (info) {
        //   this.$message.success('作业获取成功！')
        // } else {
        //   this.$message.error('作业未发布')
        // }
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
    getbeforeGrades () {
      const loading = getLoading(this)
      this.$axios({
        method: 'get',
        url: 'http://localhost:8080/vue/teacher/coursehomework',
        params: {
          id: this.courseId
        }
      }).then(function (res) {
        loading.close()
        const info = res.data
        let list = info.gradeBeans
        this.tableData3 = []
        for (let grade of list) {
          console.log(grade)
          this.tableData3.push({
            file: grade.name,
            remarks: grade.remark,
            ispublic: grade.open,
            location: grade.location
          })
        }
        // if (info) {
        //   this.$message.success('过往成绩一览！')
        // } else {
        //   this.$message.error('过往成绩失败')
        // }
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
    onSubmit2 () {
      this.$confirm('确定发布这次成绩吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const loading = getLoading(this)
        let formData = new FormData()
        formData.append('sheet', this.sheet)
        formData.append('id', this.courseId)
        formData.append('project', this.form2.type)
        formData.append('remark', this.form2.remark)
        formData.append('open', this.form2.public)
        this.$axios({
          method: 'post',
          url: 'http://localhost:8080/vue/teacher/lauchgrades',
          headers: {
            'Content-Type': 'multipart/form-data'
          },
          data: formData
        }).then(function (res) {
          loading.close()
          const info = res.data
          if (info) {
            this.$message.success('成绩发布成功！')
            this.resetForm('form2')
            this.getbeforeGrades()
            this.sheet = null
            this.imageUrl = null
          } else {
            this.$message.error('成绩发布失败')
            this.resetForm('form2')
            this.imageUrl = null
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

    getMyCreatedCourses () {
      this.$axios({
        method: 'get',
        url: 'http://localhost:8080/vue/teacher/course'
      }).then(function (res) {
        this.loading = false
        const info = res.data
        this.createdCourses = []
        for (let key in info) {
          this.createdCourses.push({
            id: key,
            courseName: info[key],
            terms: []
          })
        }

        this.getCourse()
        console.log(this.createdCourses)
      }.bind(this)).catch(function (err) {
        console.log(err)
        if (err.response.status === 401) {
          this.$router.push('/login_register')
        }
      }.bind(this))
    },
    getCourse () {
      for (let course of this.createdCourses) {
        this.$axios({
          method: 'get',
          url: 'http://localhost:8080/vue/teacher/course/' + course.id
        }).then(function (res) {
          const info = res.data
          course.terms = info.terms
        }).catch(function (err) {
          console.log(err)
          if (err.response.status === 401) {
            this.$router.push('/login_register')
          }
        }.bind(this))
      }
    },

    getInfo () {
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
    }
  },
  watch: {
    '$route' () {
      // 此处写router变化时，想要初始化或者是执行的方法......
      this.init()
    }
  },
  data () {
    return {
      // termInfo
      tableData: [],
      charts: '',
      opinion: [],
      opinionData: [],
      coursetermid: '',
      title: '',
      fit: 'cover',
      url: 'http://localhost:8080/img/portrait/default portrait.png',
      name: '',
      loading: true,
      activeName: 'first',

      // teacherlauchHomework
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
      courseId: '',

      // sendemails
      dynamicValidateForm: {
        content: '',
        title: ''
      },
      teacherId: '',

      // downloadhomework
      tableData2: [{
        title: '作业一',
        content: '你叫什么名字',
        date: '2019-06-23',
        size: '2MB',
        type: 'pdf',
        zip: 200333,
        requestid: '1000000'
      }, {
        title: '作业二',
        content: '你叫什么名字',
        date: '2019-06-23',
        size: '2MB',
        type: 'pdf',
        zip: 200333,
        requestid: '1000000'
      }, {
        title: '作业三',
        content: '你叫什么名字',
        date: '2019-06-23',
        size: '2MB',
        type: 'pdf',
        zip: 200333,
        requestid: '1000000'
      }, {
        title: '作业四',
        content: '你叫什么名字',
        date: '2019-06-23',
        size: '2MB',
        type: 'pdf',
        zip: 200333,
        requestid: '1000000'
      }],

      // lauchGrades
      gradetitle: '以往成绩表',
      tableData3: [],
      form2: {
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
      imageUrl: '',
      season: '',
      teacher: '',

      // 获得侧边栏
      createdCourses: [],
      index: 0,
      year: '',
      seasons: ['春季', '夏季', '秋季', '冬季'],
      chosenSeason: '',
      courseName: '',
      chosenCourse: '',
      publishedCourses: [],
      classes: [],
      fileList: [],
      selectedcourse: ''
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
  .el-icon-download {
    font-size: 22px;
  }
  .el-icon-view {
    font-size: 22px;
  }
</style>
