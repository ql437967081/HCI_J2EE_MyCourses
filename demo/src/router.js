import Vue from 'vue'
import Router from 'vue-router'
import Home from './views/Home.vue'
import DemoPage from './components/DemoPage'
import LoginRegister from './components/LoginRegister'
import RegisterValidate from './components/RegisterValidate'
import StudentMain from './components/student/StudentMain'
import StudentInfo from './components/student/StudentInfo'
import Logout from './components/Logout'
import StudentCourse from './components/student/StudentCourse'
import StudentCourseDetail from "./components/student/StudentCourseDetail";
import StudentHomework from "./components/student/StudentHomework";
import AdminMain from "./components/admin/AdminMain";
import AdminStatistic from "./components/admin/AdminStatistic";

Vue.use(Router)

let router = new Router({
  routes: [
    {
      path: '/',
      name: 'home',
      component: Home
    },
    {
      path: '/about',
      name: 'about',
      // route level code-splitting
      // this generates a separate chunk (about.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import(/* webpackChunkName: "about" */ './views/About.vue')
    },
    {
      path: '/demo_page',
      name: 'DemoPage',
      component: DemoPage
    },
    {
      path: '/login_register',
      name: 'login_register',
      component: LoginRegister,
      meta: {
        title: 'MyCourses'
      }
    },
    {
      path: '/register/validate',
      name: 'register_validate',
      component: RegisterValidate,
      meta: {
        title: 'MyCourses邮箱验证'
      }
    },
    {
      path: '/student_main',
      name: 'student_main',
      component: StudentMain,
      meta: {
        title: 'MyCourses：我的主页'
      }
    },
    {
      path: '/student_info',
      name: 'student_info',
      component: StudentInfo,
      meta: {
        title: 'MyCourses：个人信息'
      }
    },
    {
      path: '/logout',
      name: 'logout',
      component: Logout,
      meta: {
        title: 'MyCourses：退出登录'
      }
    },
    {
      path: '/student_course',
      name: 'student_course',
      component: StudentCourse,
      meta: {
        title: 'MyCourses：我的课程'
      }
    },
    {
      path: '/student_course/:select_course_id/',
      name: 'student_course_select_course_id',
      component: StudentCourseDetail,
      meta: {
        title: '课程详情'
      }
    },
    {
      path: '/student_course/:select_course_id/homework/:homework_request_id',
      name: 'student_course_select_course_id_homework_homework_request_id',
      component: StudentHomework,
      meta: {
        title: '作业详情'
      }
    },
    {
      path: '/admin_main',
      name: 'admin_main',
      component: AdminMain,
      meta: {
        title: '我的主页'
      }
    },
    {
      path: '/admin_statistic',
      name: 'admin_statistic',
      component: AdminStatistic,
      meta: {
        title: '数据统计'
      }
    }
  ]
})

router.beforeEach((to, from, next) => {
  /* 路由发生变化修改页面title */
  if (to.meta.title) {
    document.title = to.meta.title
  }
  next()
})

export default router
