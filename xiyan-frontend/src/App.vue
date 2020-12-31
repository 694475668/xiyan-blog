<template>
  <div id="app">
    <vue-particles
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
    </vue-particles>
    <router-view v-if="isRouterALive"></router-view>
  </div>
</template>

<script>
export default {
  name: "app",
  metaInfo: {
    title: "夕颜源码", // set a title
    meta: [
      {
        // set meta
        name: "夕颜源码",
        content: "夕颜源码",
      },
    ],
    link: [
      {
        // set link
        rel: "asstes",
        href: "http://www.xiyanyuanma.com",
      },
    ],
  },
  created() {
    try {
      setTimeout(function () {
        document.body.removeChild(document.getElementById("apploading"));
        document.getElementById("app").style.display = "block";
      }, 2000);
    } catch (e) {
      console.log(e);
    }
  },
  provide() {
    return {
      reload: this.reload,
    };
  },
  data() {
    return {
      isRouterALive: true,
    };
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
    window.WIDGET = {
      CONFIG: {
        modules: "01234",
        background: 1,
        tmpColor: "FFFFFF",
        tmpSize: 16,
        cityColor: "FFFFFF",
        citySize: 16,
        aqiSize: 16,
        weatherIconSize: 24,
        alertIconSize: 18,
        padding: "10px 10px 10px 10px",
        shadow: "1",
        language: "auto",
        borderRadius: 5,
        fixed: "true",
        vertical: "middle",
        horizontal: "center",
        right: "0",
        bottom: "0",
        key: "92LNwqg5VQ",
      },
    };
    var script = document.createElement("script");
    script.type = "text/javascript";
    script.src =
      "https://apip.weatherdt.com/simple/static/js/weather-simple-common.js?v=2.0";
    document.getElementsByTagName("head")[0].appendChild(script);
  },
};
</script>

<style>
#app {
  position: relative;
}
#particles-js {
  position: absolute;
  width: 100%;
  height: 100vh;
}
@import "assets/css/reset.css";
#weather-v2-plugin-simple {
  position: fixed !important;
}
</style>
