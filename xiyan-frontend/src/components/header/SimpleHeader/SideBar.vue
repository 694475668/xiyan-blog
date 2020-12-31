<template>
  <div class="side-bar" @touchmove.stop.prevent :class="{ open: show }">
    <div class="main-area">
      <div class="top-wrapper" @touchmove.stop>
        <div class="top-area">
          <img src="../../../assets/background.jpg" alt="" />
          <div class="site-info">
            <h1 class="site-name">夕颜源码</h1>
            <h1 class="site-desc">夕颜源码, Change the World</h1>
          </div>
        </div>
        <div class="sidebar-menus">
          <div class="site-nav">
            <p>
              <router-link to="/articleList" class="nav-link"
                ><i @click="clone" class="arrow iconfont icon-fenxiang3"
                  ><span>技术交流</span></i
                >
              </router-link>
            </p>
          </div>
        </div>
        <div class="sidebar-menus">
          <div class="site-nav">
            <p>
              <router-link to="/codes" class="nav-link"
                ><i @click="clone" class="arrow iconfont icon-ziyuan"
                  ><span>毕设源码</span></i
                >
              </router-link>
            </p>
          </div>
        </div>
        <div class="sidebar-menus">
          <div class="site-nav">
            <p>
              <router-link to="/talk" class="nav-link"
                ><i
                  @click="clone"
                  class="arrow iconfont icon-amscloudappxiaochengxuyunyingyong"
                  ><span>高谈阔论</span></i
                >
              </router-link>
            </p>
          </div>
        </div>
        <div class="sidebar-menus">
          <div class="site-nav">
            <p>
              <router-link to="/tool" class="nav-link"
                ><i @click="clone" class="arrow iconfont icon-gongju"
                  ><span>开发工具</span></i
                >
              </router-link>
            </p>
          </div>
        </div>
        <div class="sidebar-menus">
          <div class="site-nav">
            <p>
              <router-link to="/recharge" class="nav-link">
                <i @click="clone" class="arrow iconfont icon-chongzhi2"
                  ><span>金币充值</span></i
                >
              </router-link>
            </p>
          </div>
        </div>
        <div class="sidebar-menus">
          <div class="site-nav">
            <p>
              <Dropdown trigger="click">
                <a href="javascript:void(0)">
                  <i class="arrow iconfont icon-gengduo"
                    ><span>更多功能</span></i
                  ><Icon type="ios-arrow-down"></Icon>
                </a>
                <DropdownMenu slot="list">
                  <DropdownItem>
                    <router-link to="/leaderboard" class="nav-link">
                      <i @click="clone" class="arrow iconfont icon-bangdan"
                        ><span>排行榜</span></i
                      >
                    </router-link>
                  </DropdownItem>
                  <DropdownItem>
                    <router-link to="/messageBoard" class="nav-link">
                      <i @click="clone" class="arrow iconfont icon-liuyanban"
                        ><span>留言板</span></i
                      >
                    </router-link>
                  </DropdownItem>
                  <DropdownItem>
                    <router-link to="/classroom" class="nav-link">
                      <i @click="clone" class="arrow iconfont icon-ketang"
                        ><span>夕颜课堂</span></i
                      >
                    </router-link>
                  </DropdownItem>
                  <DropdownItem>
                    <i @click="clone('tv')" class="arrow iconfont icon-dianshi1"
                      ><span>电视直播</span></i
                    >
                  </DropdownItem>
                </DropdownMenu>
              </Dropdown>
            </p>
          </div>
        </div>
      </div>
    </div>
    <div class="mask" @click.prevent="toggleSideBar"></div>
  </div>
</template>

<script>
import { mixin } from "@/utils";

export default {
  name: "side-bar",
  data() {
    return {
      show: false,
      showNav: false,
    };
  },
  mixins: [mixin],
  beforeRouteUpdate(to, from, next) {
    next();
  },
  methods: {
    clone(item) {
      if (item == "tv") {
        this.$Message.warning({
          content: "手机端不支持播放,请前往pc端播放",
          duration: 5,
        });
        return;
      }
      this.show = false;
    },
    rootRouterLink(category) {
      let router = {};
      router.name = category.category_type;
      return router;
    },
    routerLink(category) {
      let router = {};
      router.name = category.category_type;
      router.params = {};
      router.params["id"] = category.id;
      return router;
    },
    toggleSideBar() {
      this.show = !this.show;
      this.showNav = !(
        this.$route.name === "articleList" ||
        this.$route.name === "codes" ||
        this.$route.name === "book/note" ||
        this.$route.name === "tool" ||
        this.$route.name === "album"
      );
    },
  },
};
</script>
<style lang="stylus" scoped  type="text/stylus" rel="stylesheet/stylus">
@import './stylus/sidebar.styl';

.ivu-dropdown {
  margin-left: 16px;
}

.icon-fenxiang3 {
  color: #F71D1F;
}

.icon-ziyuan {
  color: #00DBDE;
}

.icon-bangdan {
  color: #FECE0A;
}

.icon-ketang {
  color: #FF80C0;
}

.icon-liuyanban {
  color: #FF8000;
}

.icon-dianshi1 {
  color: #FF00FF;
}

.icon-gengduo, .ivu-icon-ios-arrow-down {
  color: #19d521;
}

.icon-amscloudappxiaochengxuyunyingyong {
  color: #FF6A00;
}

.icon-gongju {
  color: #409EFF;
}

.icon-chongzhi2 {
  color: #FF7C99;
}

.arrow span {
  padding-left: 10px;
}
</style>
