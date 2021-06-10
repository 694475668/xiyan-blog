<template>
  <div id="app">
    <router-view v-if="isReloadAlive" />
  </div>
</template>

<script>
export default {
  name: "App",
  data() {
    return {
      isReloadAlive: true,
    };
  },
  provide() {
    return {
      reload: this.reload,
    };
  },
  methods: {
    //声明vue刷新路由方法
    reload() {
      this.isReloadAlive = false;
      this.$nextTick(function () {
        this.isReloadAlive = true;
      });
    },
  },
};
</script>

<style>
</style>

