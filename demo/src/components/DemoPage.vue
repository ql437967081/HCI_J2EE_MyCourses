<template>
  <div>
    <!-- 这里是html区，在这里编写的内容会在界面上直接进行展示 -->
    <!-- 如果想要在界面上展示本页面的属性，使用双大括号 {{value}} -->
    <!-- 比如，下面这条语句会在界面上显示"demo" -->
    <p> {{name}} </p>
    <!-- 下面这条语句会显示'level2' -->
    <p> {{user.level}} </p>
    <!-- 请注意一点，Vue在一个templates里仅支持一个元素，不可以并排放置多个元素 -->
    <!-- 因此，建议使用<div></div>包裹-->
    <!-- 下面演示了如何调用script内的方法，用到了Element-UI的元素，这些会在下一节详细讲解-->
    <el-button type='primary' @click='consoleShow'>我是一个按钮</el-button>
  </div>
</template>

<script>
// script区，即JavaScript编写的地方
// 这里主要包括在本页面中用到的JS方法和属性
// 想要使用外部方法可以在这里import包，例如：
export default {
  name: 'demoPage',
  // mounted指在页面加载时，一并运行的方法
  // 比如说你想在页面加载时从后端获取数据，那么就把这些工作都放在mounted的function内执行
  mounted: function () {
    this.init()
  },
  // methods用于写本界面可能用到的方法
  methods: {
    init () {
      // console.log指在控制台输出内容，在浏览器界面按F12打开，选择console即可看到内容了
      console.log('page named demoPage init')
      this.hello()
    },
    consoleShow () {
      console.log('the butoon is clicked.')
    },
    hello () {
      this.$axios({
        method: 'get',
        url: 'http://localhost:8080/hello_world',
        params: {
        }
      }).then(function (res) {
        console.log(res.data)
        // this.username = res.data // Now it works correctly
      }).catch(function (err) {
        console.log(err)
      })
    }
  },
  // data区存放了本界面需要用到的数据，在HTML/JS中均可用this.value访问到相应属性的值
  data () {
    return {
      name: 'demo',
      user: {
        userId: '10000',
        level: 'level2'
      }
    }
  }
}
</script>

<style scoped>
  /* 这里存放css */
</style>
