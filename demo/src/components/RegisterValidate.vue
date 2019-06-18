<template>
  <div></div>
</template>

<script>
import { createCookie } from '../cookie'
import { getLoading } from '../loading'

export default {
  name: 'RegisterValidate',
  mounted: function () {
    const loading = getLoading(this)
    this.$axios({
      method: 'get',
      url: 'http://localhost:8080/vue/register/validate?token=' + this.$route.query.token
    }).then(function (res) {
      const result = res.data
      loading.close()
      if (result.result === 'ILLEGAL') {
        this.$alert('非法的邮箱验证！', '验证失败', {
          confirmButtonText: '返回登录',
          callback: () => {
            this.$router.push('/login_register')
          }
        })
      } else if (result.result === 'EXPIRED') {
        this.$alert('邮箱验证已过期，请重新注册！', '验证失败', {
          confirmButtonText: '返回登录',
          callback: () => {
            this.$router.push('/login_register')
          }
        })
      } else if (result.result === 'SUCCESS') {
        createCookie('LoginCookie', result.id, 24 * 365)
        if (result.type === 'STUDENT') {
          this.$router.push('/student_main')
        } else if (result.type === 'TEACHER') {
          this.$router.push('/teacher_main')
        } else if (result.type === 'ADMINISTRATOR') {
          this.$router.push('/admin_main')
        }
      }
    }.bind(this)).catch(function (err) {
      console.log(err)
    })
  }
}
</script>

<style scoped>

</style>
