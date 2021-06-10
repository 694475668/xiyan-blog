<template>
  <scroll-bar>
    <!-- pc端不需要加事件 -->
    <el-row class="hidden-xs-only">
      <el-menu
        mode="vertical"
        :show-timeout="200"
        :default-active="$route.path"
        :collapse="isCollapse"
        background-color="#ffffff"
        text-color="#6D6D6E"
        active-text-color="#E60023"
      >
        <sidebar-item :routes="routes"></sidebar-item>
      </el-menu>
    </el-row>
    <!-- 移动端加事件 -->
    <el-row class="hidden-sm-and-up">
      <el-col>
        <el-menu
          mode="vertical"
          :show-timeout="200"
          :default-active="$route.path"
          :collapse="isCollapse"
          background-color="#ffffff"
          text-color="#6D6D6E"
          active-text-color="#E60023"
          @select="select"
        >
          <img class="logo" width src="@/assets/images/xiyan-logo.png" />
          <sidebar-item :routes="routes"></sidebar-item>
        </el-menu>
      </el-col>
    </el-row>
  </scroll-bar>
</template>

<script>
import { mapGetters } from "vuex";
import SidebarItem from "./SidebarItem";
import ScrollBar from "@/components/ScrollBar";

export default {
  components: { SidebarItem, ScrollBar },
  computed: {
    ...mapGetters(["sidebar", "permission_routes"]),
    routes() {
      // return this.$router.options.routes
      return this.permission_routes;
    },
    isCollapse() {
      return !this.sidebar.opened;
    },
  },
  methods: {
    select(index, indexPath) {
      this.$router.push({ name: indexPath });
    },
  },
};
</script>
<style  scoped>
img {
  padding-top: 50px;
  padding-left: 50px;
  background: #ffffff;
}
</style>