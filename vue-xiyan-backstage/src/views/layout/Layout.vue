<template>
  <div class="app-wrapper" :class="classObj">
    <sidebar class="sidebar-container"></sidebar>
    <div
      class="bgcolor"
      v-show="sidebar.opened == true && this.bgcolor"
      @click="yxbg"
    ></div>
    <div class="main-container">
      <navbar></navbar>
      <app-main></app-main>
    </div>
  </div>
</template>

<script>
import { Navbar, Sidebar, AppMain } from "./components";
import ResizeMixin from "./mixin/ResizeHandler";
import variables from "@/styles/variables.scss";
export default {
  name: "layout",
  components: {
    Navbar,
    Sidebar,
    AppMain,
  },
  data() {
    return {
      bgcolor: true,
    };
  },
  created() {
    var windowWidth = $(window).width();
    if (windowWidth >= 768) {
      this.bgcolor = false;
    }
  },
  mixins: [ResizeMixin],
  computed: {
    sidebar() {
      return this.$store.state.app.sidebar;
    },
    device() {
      return this.$store.state.app.device;
    },
    classObj() {
      return {
        hideSidebar: !this.sidebar.opened,
        withoutAnimation: this.sidebar.withoutAnimation,
        mobile: this.device === "mobile",
      };
    },
  },
  methods: {
    yxbg() {
      this.$store.dispatch("ToggleSideBar");
    },
  },
};
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
@import "src/styles/mixin.scss";
.app-wrapper {
  @include clearfix;
  position: relative;
  height: 100%;
  width: 100%;
}
.bgcolor {
  position: fixed;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
  margin: auto;
  background: rgba(0, 0, 0, 0.5);
  z-index: 10;
}
</style>

