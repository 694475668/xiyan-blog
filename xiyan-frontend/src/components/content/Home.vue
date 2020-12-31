<template>
  <div class="home-container">
    <div class="circle circle-ani"></div>
    <router-link to="/home">ENTER</router-link>
  </div>
</template>

<script>
export default {
  data() {
    return {};
  },
  mounted() {
    this.$nextTick(() => {
      document.querySelector(".main-wrapper").style.margin = "0px auto";
    });
  },
};
</script>
<style lang="stylus" scoped>
.home-container {
  width: 100%;
  min-height: 100vh;
  position: relative;
  animation: mainEnter 1s;

  a {
    position: absolute;
    display: block;
    width: 100px;
    height: 100px;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    border: 1px solid dodgerblue;
    border-radius: 100px;
    text-align: center;
    line-height: 100px;
    font-size: 18px;
    color: dodgerblue;

    &:hover, &:active {
      background: #1e90ff;
      color: #fff;
      border: 1px solid #fff;
    }
  }

  .circle {
    position: absolute;
    display: block;
    width: 100px;
    height: 100px;
    border-radius: 100px;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    border: 1px solid dodgerblue;
  }

  .circle-ani {
    animation: enter 2s infinite;
  }

  .title {
    color: dodgerblue;
    text-align: center;
    position: fixed;
    bottom: 50px;
    left: 50%;
    transform: translateX(-50%);
    font-weight: lighter;
  }
}

@keyframes enter {
  0% {
    transform: translate(-50%, -50%) scale(1, 1);
    opacity: 1;
  }

  100% {
    transform: translate(-50%, -50%) scale(1.4, 1.4);
    opacity: 0;
  }
}

@keyframes mainEnter {
  0% {
    opacity: 0;
  }

  100% {
    opacity: 1;
  }
}
</style>
