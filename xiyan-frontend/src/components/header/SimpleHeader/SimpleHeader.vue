<template>
  <div class="simple-header">
    <transition name="slide-fade">
      <div id="mobile-bar" v-show="show">
        <a class="menu-button" ref="menubutton"></a>
        <router-link class="logo" to="/home"></router-link>
        <div class="avatar">
          <p class="flex">
            <Input v-if="showFlag" search enter-button @on-search="submit(keywords)" v-model="keywords" placeholder="想搜点什么呢？"/>
            <a @click="isShow" style="margin-left: 5px"><img class="search" :src="searchUrl" /></a>
            <Dropdown trigger="click" style="margin-left: 5px">
              <a href="javascript:void(0)">
                <Avatar
                  v-if="userInfo.name != undefined"
                  :src="userInfo.photo"
                />
                <router-link v-if="userInfo.name == undefined" to="/login">
                  <Avatar icon="ios-person" />
                </router-link>
                <Icon
                  v-if="userInfo.name != undefined"
                  type="ios-arrow-down"
                ></Icon>
              </a>
              <DropdownMenu slot="list" v-if="userInfo.name != undefined">
                <DropdownItem
                  ><router-link class="inlineBlock" to="/home"
                    >个人中心</router-link
                  ></DropdownItem
                >
                <DropdownItem><span @click="logout">退出</span></DropdownItem>
              </DropdownMenu>
              </DropdownMenu>
            </Dropdown>
          </p>
        </div>
      </div>
    </transition>
    <transition name="slide-fade">
      <div id="header" v-show="show">
        <router-link id="logo" to="/home">
          <img src="../../../assets/logo.png" />
          <span class="title">夕颜源码</span>
          <span class="motto">go！！！</span>
        </router-link>
        <ul id="nav">
          <li>
            <router-link to="/articleList" class="nav-link contribute"
              ><span
                ><svg class="icon" aria-hidden="true">
                  <use
                    xlink:href="#icon-fenxiang3
"
                  ></use></svg
                >技术交流</span
              ></router-link
            >
          </li>
          <li>
            <router-link to="/codes" class="nav-link contribute"
              ><span
                ><svg class="icon" aria-hidden="true">
                  <use
                    xlink:href="#icon-ziyuan
"
                  ></use></svg
                >毕设源码</span
              ></router-link
            >
          </li>
          <li>
            <router-link to="/talk" class="nav-link contribute"
              ><span
                ><svg class="icon" aria-hidden="true">
                  <use
                    xlink:href="#icon-amscloudappxiaochengxuyunyingyong
"
                  ></use></svg
                >高谈阔论</span
              ></router-link
            >
          </li>
          <li>
            <router-link to="/tool" class="nav-link contribute"
              ><span
                ><svg class="icon" aria-hidden="true">
                  <use
                    xlink:href="#icon-gongju

"
                  ></use></svg
                >开发工具</span
              ></router-link
            >
          </li>
          <li>
            <router-link to="/recharge" class="nav-link contribute"
              ><span
                ><svg class="icon" aria-hidden="true">
                  <use
                    xlink:href="#icon-chongzhi2


"
                  ></use></svg
                >金币充值</span
              ></router-link
            >
          </li>
          <li>
            <Dropdown>
              <a href="javascript:void(0)">
                <span style="font-size: 17px"
                  ><svg class="icon" aria-hidden="true">
                    <use
                      xlink:href="#icon-gengduo
"
                    ></use></svg
                  >更多功能<Icon type="ios-arrow-down"></Icon
                ></span>
              </a>
              <DropdownMenu slot="list">
                <DropdownItem
                  ><span @click="$router.push('/leaderboard')"
                    ><svg class="icon" aria-hidden="true">
                      <use
                        xlink:href="#icon-bianpinghuatubiaosheji-"
                      ></use></svg
                    >排行榜</span
                  ></DropdownItem
                >
                <DropdownItem
                  ><span @click="$router.push('/messageBoard')"
                    ><svg class="icon" aria-hidden="true">
                      <use xlink:href="#icon-zhubangdan"></use></svg
                    >留言板</span
                  ></DropdownItem
                >
                <DropdownItem
                  ><span @click="$router.push('/classroom')"
                    ><svg class="icon" aria-hidden="true">
                      <use xlink:href="#icon-jinrongketang"></use></svg
                    >夕颜课堂</span
                  ></DropdownItem
                >
                <DropdownItem
                  ><span @click="$router.push('/tv')"
                    ><svg class="icon" aria-hidden="true">
                      <use xlink:href="#icon-dianshi2"></use></svg
                    >电视直播</span
                  ></DropdownItem
                >
              </DropdownMenu>
            </Dropdown>
          </li>
          <li>
            <span
              class="algolia-autocomplete"
              style="position: relative; display: inline-block; direction: ltr"
            >
              <input
                type="text"
                id="search-query-nav"
                class="search-query st-default-search-input aa-input"
                name="keywords"
                v-model="keywords"
                @keyup.enter="submit(keywords)"
                autocomplete="off"
                spellcheck="false"
                role="combobox"
                aria-autocomplete="list"
                aria-expanded="false"
                aria-owns="algolia-autocomplete-listbox-0"
                dir="auto"
                style="position: relative; vertical-align: top"
                placeholder="在这里可劲搜吧...." />
              <pre
                aria-hidden="true"
                style="
                  position: absolute;
                  visibility: hidden;
                  white-space: pre;
                  font-family: system-ui;
                  font-size: 15px;
                  font-style: normal;
                  font-variant-ligatures: normal;
                  font-variant-caps: normal;
                  font-weight: normal;
                  word-spacing: 0px;
                  letter-spacing: normal;
                  text-indent: 0px;
                  text-rendering: auto;
                  text-transform: none;
                "
              ></pre>
              <span
                class="aa-dropdown-menu"
                role="listbox"
                id="algolia-autocomplete-listbox-0"
                style="
                  position: absolute;
                  top: 100%;
                  z-index: 100;
                  display: none;
                  left: 0px;
                  right: auto;
                "
                ><div class="aa-dataset-1"></div></span
            ></span>
          </li>
          <li v-show="userInfo.name == undefined">
            <router-link
              to="/login"
              class="nav-link contribute"
              style="margin-right: 0px; color: #e6e61a"
              >登录</router-link
            >
          </li>
          <li v-show="userInfo.name != undefined">
            <Dropdown trigger="click">
              <a class="assets" href="javascript:void(0)">
                <Icon size="20" color="#2db7f5" type="ios-contact" />
                {{ userInfo.name }}
                <Icon type="ios-arrow-down"></Icon>
              </a>
              <DropdownMenu slot="list">
                <DropdownItem
                  ><router-link class="inlineBlock" to="/home"
                    >个人中心</router-link
                  ></DropdownItem
                >
                <DropdownItem><span @click="logout">退出</span></DropdownItem>
              </DropdownMenu>
            </Dropdown>
          </li>
        </ul>
      </div>
    </transition>
    <sidebar ref="sidebar"></sidebar>
  </div>
</template>

<script>
import SideBar from "@/components/header/SimpleHeader/SideBar";
import Cookie from "js-cookie";
export default {
  inject: ["reload"],
  components: {
    sidebar: SideBar,
  },
  data() {
    return {
      show: true,
      keywords: "",
      userInfo: {},
      showFlag: false,
      searchUrl: require("../../../assets/icon/search.png"),
    };
  },
  created() {
    this.keywords = this.$route.query.keywords;
  },
  mounted: function () {
    if (Cookie.get("userInfo") != undefined) {
      this.userInfo = JSON.parse(Cookie.get("userInfo"));
    }
    this.$nextTick(function () {
      this.initMobileMenu();
    });
    // 给页面绑定滑轮滚动事件
    if (document.addEventListener) {
      // firefox
      document.addEventListener("DOMMouseScroll", this.watchScroll, false);
    }
    // 滚动滑轮触发scrollFunc方法  //ie 谷歌
    window.onmousewheel = document.onmousewheel = this.watchScroll;
  },
  methods: {
    isShow() {
      this.keywords = "";
      this.showFlag = !this.showFlag;
      if (this.showFlag) {
        this.searchUrl = require("../../../assets/icon/close.png");
      } else {
        this.searchUrl = require("../../../assets/icon/search.png");
      }
    },
    logout() {
      this.$store.dispatch("LogOut", this.form).then(() => {
        this.$router.push({ name: "login" });
        this.reload();
      });
    },
    submit(keywords) {
      this.$router.push({ path: "/articles/search", query: { keywords } });
    },
    initMobileMenu() {
      // 显示手机端的菜单
      var sidebar = this.$refs.sidebar;
      this.$refs.menubutton.addEventListener("click", function () {
        sidebar.toggleSideBar();
      });
    },
    watchScroll(e) {
      e = e || window.event;
      if (e.wheelDelta) {
        if (e.wheelDelta > 0 && this.show === false) {
          // 当滑轮向上滚动
          this.show = true;
        }
        if (e.wheelDelta < 0 && this.show === true) {
          // 当滑轮向下滚动
          this.show = false;
        }
      } else if (e.detail) {
        if (e.detail < 0 && this.show === false) {
          // 当滑轮向上滚动
          this.show = true;
        }
        if (e.detail > 0 && this.show === true) {
          // 当滑轮向下滚动
          this.show = false;
        }
      }
    },
  },
};
</script>
<style scoped lang="stylus"  rel="stylesheet/stylus">
@import 'stylus/header.styl';

@media only screen and (max-width: 768px) {
  .ivu-avatar {
    background: #FF84C6;
  }

  /deep/.ivu-input-wrapper {
    width: auto !important;

    .ivu-input {
      height: 36px;
      width: 210px !important;
    }
  }

  .avatar {
    position: absolute;
    right: 10px;
    top: 0px;
  }

  .flex {
    display: flex;
    justify-content: center;
    align-items: center;

    .search {
      margin-top: 8px;
      width: 28px;
      height: 28px;
    }
  }
}

.assets {
  font-size: 16px;
}

.slide-fade-enter-active, .slide-fade-leave-active {
  transition: all 0.8s ease;
}

.slide-fade-leave-to, .slide-fade-enter {
  /* .slide-fade-leave-active for below version 2.1.8 */
  transform: translateY(-70px);
  opacity: 0;
}

@media screen and (min-width: 1025px) and (max-width: 1400px) {
  body.docs #header {
    background-color: #fff !important;
    padding: 30px 60px !important;
    z-index: 11 !important;
    position: fixed !important;
    width: 100% !important;
    top: 0 !important;
  }
}

.icon {
  width: 1em;
  height: 1em;
  vertical-align: -0.15em;
  fill: currentColor;
  overflow: hidden;
  margin-right: 5px;
}
</style>
