<template>
  <div class="photowall">
    <el-carousel
      :interval="4000"
      indicator-position="outside"
      :height="bannerHeight + 'px'"
      ref="carousel"
    >
      <el-carousel-item v-for="(item, index) in banners" :key="item.id">
        <v-touch
          :swipe-options="{ direction: 'horizontal' }"
          v-on:swipeleft="swiperleft(index)"
          v-on:swiperight="swiperright(index)"
          class="wrapper"
        >
          <div class="menu-container" ref="menuContainer">
            <img ref="bannerHeight" :src="item.url" @load="imgLoad" />
          </div>
        </v-touch>
      </el-carousel-item>
    </el-carousel>
  </div>
</template>

<script>
export default {
  data() {
    return {
      banners: [
        {
          id: 1,
          url: require("../../assets/xiyan2.png"),
        },
        {
          id: 2,
          url: require("../../assets/xiyan2.png"),
        },
        {
          id: 3,
          url: require("../../assets/xiyan2.png"),
        },
      ],
      bannerHeight: "",
    };
  },
  mounted() {
    this.imgLoad();
    window.addEventListener(
      "resize",
      () => {
        //防止在ie浏览器出现报错导致后面的代码不执行,所以这里直接捕获异常
        try {
          this.bannerHeight = this.$refs.bannerHeight[0].height;
          this.imgLoad();
        } catch (exception) {
          this.imgLoad();
        }
      },
      false
    );
  },
  methods: {
    //设置滑动切换轮播图
    swiperleft: function (index) {
      //上一页
      this.$refs.carousel.prev();
      //设置幻灯片的索引
      this.$refs.carousel.setActiveItem(index - 1);
    },
    swiperright: function (index) {
      //下一页
      this.$refs.carousel.next();
      this.$refs.carousel.setActiveItem(index + 1);
    },
    //第一次加载获取图片在视口中的高度然后渲染页面
    imgLoad() {
      this.$nextTick(() => {
        //防止在ie浏览器出现报错导致后面的代码不执行,所以这里直接捕获异常
        try {
          this.bannerHeight = this.$refs.bannerHeight[0].height;
        } catch (exception) {}
      });
    },
  },
};
</script>
<style lang="stylus" scoped>
img {
  max-width: 100% !important;
  height: auto !important;
}
</style>