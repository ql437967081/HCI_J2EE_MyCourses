<template>
  <div></div>
</template>

<script>
export default {
  name: 'Logout',
  mounted: function () {
    this.$axios({
      method: 'get',
      url: 'http://localhost:8080/vue/login/logout'
    }).then(function (res) {
      console.log(res)
      this.$router.push('/login_register')
    }.bind(this)).catch(function (err) {
      console.log(err)
    })
  }
}
</script>

<style scoped>

</style>
