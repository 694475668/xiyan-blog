<template>
  <div class="dashboard-container">
    <div class="welcome">WELCOME</div>
    <div class="title">欢迎进入夕颜后台管理系统</div>
  </div>
</template>

<script>
export default {
  name: "Dashboard",
  computed: {},
};
</script>

<style lang="scss" scoped>
.dashboard-container {
  width: 100%;
  min-height: calc(100vh - 50px);
  max-height: 100%;
  background: url("../../assets/images/banner2.png") no-repeat;
  background-size: 100%;
  .welcome {
    width: 100%;
    text-align: center;
    padding-top: 50px;
    font-size: 60px;
    color: rgb(90, 143, 220);
  }
  .title {
    width: 100%;
    text-align: center;
    font-size: 18px;
    color: rgb(135, 178, 226);
    margin-top: 20px;
  }
}
</style>
