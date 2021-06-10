<template>
  <div>
    <div class="app-container">
      <el-col :span="12">
        <img :src="img_404" alt="404" class="img-style">
      </el-col>
      <el-col :span="12">
        <div style="margin-left: 100px;margin-top: 60px">
          <h1 class="color-main">OOPS!</h1>
          <h2 style="color: #606266">Sorry, the page got lost accidentally！</h2>
          <div style="color:#909399;font-size: 14px">
            Please check the URL entered is correct, please click the button below to return to the homepage or send an error report, thanks.</div>
          <el-button style="margin-top: 20px" type="primary" round @click="handleGoMain">Back to Home</el-button>
        </div>
      </el-col>
    </div>
  </div>
</template>

<script>
  import img_404 from '@/assets/images/gif_404.gif';

  export default {
    name: 'wrongPage',
    data() {
      return {
        img_404
      }
    },
    methods: {
      handleGoMain() {
        this.$router.push({path: '/'})
      }
    },
  }
</script>

<style scoped>
  .app-container {
    width: 80%;
    margin: 120px auto;
  }

  .img-style {
    width: auto;
    height: auto;
    max-width: 100%;
    max-height: 100%;
  }
</style>
