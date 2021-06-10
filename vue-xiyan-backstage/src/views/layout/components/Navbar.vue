<template>
  <el-menu class="navbar" mode="horizontal">
    <hamburger
      class="hamburger-container"
      :toggleClick="toggleSideBar"
      :isActive="sidebar.opened"
    ></hamburger>
    <breadcrumb></breadcrumb>
    <el-dropdown class="avatar-container" trigger="click">
      <div class="avatar-wrapper el-dropdown-link">
        <!--
        <img class="user-avatar" :src="avatar">
        -->
        <img class="user-avatar" src="@/assets/user/nav_account.png" />
        <span class="info">{{ userInfo.name | formatName }}</span>
        <i class="el-icon-caret-bottom"></i>
      </div>
      <el-dropdown-menu class="user-dropdown" slot="dropdown">
        <router-link class="inlineBlock" to="/order">
          <el-dropdown-item
            style="
              font-family: PingFangSC-Regular;
              color: #333333;
              line-height: 22px;
            "
            >主页</el-dropdown-item
          >
        </router-link>
        <el-dropdown-item divided>
          <span
            @click="logout"
            style="
              display: block;
              font-family: PingFangSC-Regular;
              color: #333333;
              line-height: 22px;
            "
            >登出</span
          >
        </el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
  </el-menu>
</template>

<script>
import { mapGetters } from "vuex";
import Breadcrumb from "@/components/Breadcrumb";
import Hamburger from "@/components/Hamburger";
import { getUserInfo } from "@/utils/auth";
import Cookies from "js-cookie";

export default {
  components: {
    Breadcrumb,
    Hamburger,
  },
  //暴露reload方法
  inject: ["reload"],
  data() {
    return {
      userInfo: {
        name: "",
        realname: "",
        email: "",
        address: "",
        mobile: "",
        phone: "",
        createTime: "",
        activeTime: "",
      },
      language: "",
    };
  },
  created() {
    this.userInfo = JSON.parse(getUserInfo());
  },
  filters: {
    formatName(value) {
      var index = value.indexOf("@");
      if (index != -1) {
        return value.substring(0, index);
      } else {
        return value;
      }
    },
  },
  computed: {
    ...mapGetters(["sidebar", "avatar"]),
  },
  methods: {
    toggleSideBar() {
      this.$store.dispatch("ToggleSideBar");
    },
    logout() {
      this.$store.dispatch("LogOut").then(() => {
        location.reload(); // 为了重新实例化vue-router对象 避免bug
        try {
          QuickWebHolder.finish();
        } catch (exception) {}
      });
    },
  },
};
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
/* 响应式布局 */
@media screen and (max-width: 768px) {
  .navbar {
    height: 60px !important;
    background: #f0f4f7;
    border: none;
    z-index: 0.5 !important;
  }
  .navbar .avatar-container .avatar-wrapper {
    display: none;
  }
  .navbar .language-container .avatar-wrapper {
    display: none;
  }
  #app .mobile .hamburger-container {
    margin-left: 0rem !important;
  }
}
.el-dropdown-menu,
.el-menu--collapse .el-submenu .el-menu {
  width: 125px;
}
.info,
.info1 {
  float: right;
  font-family: PingFangSC-Regular;
  font-size: 14px;
  color: #666666;
  margin-left: 10px;
  margin-top: -3px;
}
.el-icon-caret-bottom {
  margin-top: -1px;
}
.info1 {
  width: 55px;
}
.el-dropdown-menu__item:hover {
  color: #e60023;
  background-color: #fef0f2;
}
#spanSty {
  color: #e60023 !important;
}
.navbar {
  height: 57px;
  line-height: 50px;
  border-radius: 0px !important;
  position: fixed;
  width: 100%;
  top: 0;
  right: 0;
  z-index: 1;
  .hamburger-container {
    line-height: 58px;
    height: 50px;
    float: left;
    padding: 0 35px;
  }
  .screenfull {
    position: absolute;
    right: 90px;
    top: 16px;
    color: red;
  }
  .avatar-container {
    height: 50px;
    display: inline-block;
    position: absolute;
    right: 30px;
    .avatar-wrapper {
      cursor: pointer;
      margin: 7px 20px 0 0;
      position: relative;
      .user-avatar,
      .language-avatar {
        background-color: #f3f4f9;
        border-radius: 20px;
        padding: 12px;
      }
      .el-icon-caret-bottom {
        position: absolute;
        right: -20px;
        top: 17px;
        font-size: 12px;
      }
    }
  }
}
</style>
