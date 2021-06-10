<template>
  <div id="app">
    <!-- <vue-particles
      color="#00FFFF"
      :particleOpacity="1"
      :particlesNumber="80"
      shapeType="circle"
      :particleSize="5"
      linesColor="#00FF00"
      :linesWidth="1"
      :lineLinked="true"
      :lineOpacity="1"
      :linesDistance="150"
      :moveSpeed="3"
      :hoverEffect="true"
      hoverMode="grab"
      :clickEffect="true"
      clickMode="push"
    >
    </vue-particles> -->
    <simpleHeader></simpleHeader>
    <!-- nuxt中使用局部刷新reload必须这样写，不然没有效果 -->
    <router-view v-if="isRouterALive"> <Nuxt /></router-view>
    <commonFooter></commonFooter>
  </div>
</template>
<script>
import SimpleHeader from "@/pages/components/header/SimpleHeader/SimpleHeader";
import CommonFooter from "@/pages/components/footer/CommonFooter";
export default {
  name: "app",
  data() {
    return { isRouterALive: true };
  },
  provide() {
    return {
      reload: this.reload,
    };
  },
  components: {
    simpleHeader: SimpleHeader,
    commonFooter: CommonFooter,
  },
  methods: {
    reload() {
      this.isRouterALive = false;
      this.$nextTick(function () {
        this.isRouterALive = true;
      });
    },
  },
  mounted() {
    //初始页一打开就定位到顶部
    document.documentElement.scrollTop = 0;
    this.$nextTick(() => {
      this.$Spin.show({
        render: (h) => {
          return h("div", [
            h("Icon", {
              class: "demo-spin-icon-load",
              props: {
                type: "ios-loading",
                size: 50,
              },
            }),
            h("div", { class: "textSty" }, "Loading"),
          ]);
        },
      });
      setTimeout(() => {
        this.$Spin.hide();
      }, 1000);
    });
  },
};
</script>


<style scope>
#app {
  position: relative;
  width: 100%;
  margin: 61px auto;
}

#particles-js {
  position: absolute;
  width: 100%;
  height: 100vh;
}

#weather-v2-plugin-simple {
  position: fixed !important;
}
</style>
<style>
.demo-spin-icon-load {
  color: #3bc48d;
  animation: ani-demo-spin 1s linear infinite;
}
.textSty {
  color: #3bc48d;
}
</style>
