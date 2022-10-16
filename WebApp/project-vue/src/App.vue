<template>
  <div id="app" style="background-color: rgba(235, 235, 235, 0.08)">
    <router-view/>
  </div>
</template>

<script>
export default {
  name: 'app',
  mounted: function(){
    if(localStorage.getItem('token')===null){
      //如果用户并未登录 直接跳转到登录界面
      this.$router.push('/login')
    }
  }

}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
