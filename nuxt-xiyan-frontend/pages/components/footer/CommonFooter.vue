<template>
  <div class="foot">
    <a href="http://beian.miit.gov.cn/">
      Copyright©2021 All Rights Reserved. 湘ICP备2021001381号</a
    >
    <client-only>
      <aplayer autoplay :audio="audio" :lrcType="1" fixed />
    </client-only>
  </div>
</template>
<script>
export default {
  data() {
    return {
      audio: [],
    };
  },
  mounted() {
    //初始页一打开就定位到顶部
    document.documentElement.scrollTop = 0;
    this.list();
  },
  methods: {
    async list() {
      let res = await this.$axios.get("/web/music/list", {
        headers: { "Content-Type": "application/x-www-form-urlencoded" },
      });
      if (res.success) {
        this.audio = res.voList;
      }
    },
  },
};
</script>
<style lang="stylus" scoped rel="stylesheet/stylus">
.foot {
  color: #2db7f5;
  font-size: 15px;
  text-align: center;
}

a {
  color: #2d8cf0;
}

@media only screen and (max-width: 768px) {
  .foot {
    margin-top: 50px;
  }
}
</style>
