<template>
  <div class="title-menu-timeline">
    <ul class="list clearfix">
      <li v-for="menuFilter in filterList" :key="menuFilter.name">
        <a
          v-if="downloadType != menuFilter.type"
          :class="menuFilter.active ? 'active' : ''"
          @click="filterByMenu(menuFilter.type)"
          >{{ menuFilter.name }}</a
        >
      </li>
      <li v-if="show">
        <Dropdown trigger="click" @on-click="queryByType">
          <a href="javascript:void(0)" style="font-size: 15px">
            {{ title }}类型
            <Icon type="ios-arrow-down"></Icon>
          </a>
          <DropdownMenu slot="list">
            <DropdownItem
              ><i
                class="arrow iconfont icon-quanbu-"
                style="color: #f4ea2a; font-weight: 400"
                ><span>全部</span></i
              ></DropdownItem
            >
            <DropdownItem name="0"
              ><i
                class="arrow iconfont icon-java"
                style="color: #d52bb3; font-weight: 400"
                ><span>JAVA</span></i
              ></DropdownItem
            >
            <DropdownItem name="1"
              ><i
                class="arrow iconfont icon-python"
                style="color: #3cc48d; font-weight: 400"
                ><span>Python</span></i
              ></DropdownItem
            >
            <DropdownItem name="2"
              ><i
                class="arrow iconfont icon-go"
                style="color: #d81e06; font-weight: 400"
                ><span>GO</span></i
              ></DropdownItem
            >
            <DropdownItem name="3"
              ><i
                class="arrow iconfont icon-php1"
                style="color: #f28a1c; font-weight: 400"
                ><span>PHP</span></i
              ></DropdownItem
            >
            <DropdownItem name="4"
              ><i
                class="arrow iconfont icon-Vue"
                style="color: #1296db; font-weight: 400"
                ><span>VUE</span></i
              ></DropdownItem
            >
            <DropdownItem name="5"
              ><i
                class="arrow iconfont icon-java-script"
                style="color: #13227a; font-weight: 400"
                ><span>JavaScript</span></i
              ></DropdownItem
            >
            <DropdownItem name="6"
              ><i
                class="arrow iconfont icon-c"
                style="color: #3cc2a8; font-weight: 400"
                ><span>C</span></i
              ></DropdownItem
            >
            <DropdownItem name="7"
              ><i
                class="arrow iconfont icon-c1"
                style="color: #1cedf2; font-weight: 400"
                ><span>C++</span></i
              ></DropdownItem
            >
            <DropdownItem name="8"
              ><i
                class="arrow iconfont icon-linux"
                style="color: #6b1cf2; font-weight: 400"
                ><span>Linux</span></i
              ></DropdownItem
            >
            <DropdownItem name="9"
              ><i
                class="arrow iconfont icon-app"
                style="color: #ed4014; font-weight: 400"
                ><span>APP</span></i
              ></DropdownItem
            >
            <DropdownItem name="10"
              ><i
                class="arrow iconfont icon-other"
                style="color: #e71cf2; font-weight: 400"
                ><span>其它</span></i
              ></DropdownItem
            >
          </DropdownMenu>
        </Dropdown>
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  props: {
    menuFilterList: Array,
    //是技术分享页面不显示下载最多的查询按钮
    downloadType: String,
    title: String,
    show: Boolean,
  },
  computed: {
    filterList() {
      return this.menuFilterList;
    },
  },
  methods: {
    queryByType(item) {
      if (this.title == "文章") {
        this.$router.push({
          name: "articleList",
          query: { type: item },
        });
      } else {
        this.$router.push({
          name: "codes",
          query: { type: item },
        });
      }
    },
    filterByMenu(type) {
      this.replaceActive(type);
      let param = {};
      param["sortField"] = type;
      this.$emit("filterByMenu", param);
    },
    replaceActive(type) {
      this.menuFilterList.map((menuFilter) => {
        if (menuFilter.type === type) {
          menuFilter.active = true;
        } else {
          menuFilter.active = false;
        }
      });
    },
  },
};
</script>
<style lang="stylus" scoped rel="stylesheet/stylus" scoped>
@import '~/assets/stylus/theme.styl';

span {
  margin-left: 5px;
}

.title-menu-timeline {
  display: flex;

  ul {
    li {
      float: left;
      list-style: none;
      font-size: 15px;
      margin: 0 6px;

      a {
        padding: 6px 0;
        color: #2d8cf0;

        &:hover, &.active {
          color: $color-main-primary;
          font-weight: bold;
          border-bottom: 2px solid $color-main-primary;
        }
      }
    }
  }
}
</style>
