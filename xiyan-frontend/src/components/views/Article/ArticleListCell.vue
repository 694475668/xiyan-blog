<template>
  <div>
    <a @click="getContent(article)">
      <Card class="ivu">
        <div class="ivu-row">
          <div class="ivu-col ivu-col-span-7">
            <img
              class="live-cover"
              :src="article.pic"
              style="width: 100%; height: 100%"
            />
          </div>
          <div class="ivu-col ivu-col-span-17">
            <div class="live-info">
              <div class="live-title">
                <div>{{ article.title }}</div>
                <div>
                  <Tag v-if="article.official == '1'" checkable color="success"
                    >官方</Tag
                  >
                  <Tag v-if="article.boutique == '1'" checkable color="error"
                    >精品</Tag
                  >
                  <Tag v-if="article.good == '1'" checkable color="error"
                    >好文</Tag
                  >
                  <Tag v-if="article.recommend == '1'" checkable color="warning"
                    >推荐</Tag
                  >
                  <Tag v-if="article.top == '1'" checkable color="primary"
                    >置顶</Tag
                  >
                </div>
              </div>
              <div class="live-desc">{{ article.remark }}</div>
            </div>
            <div class="live-down">
              <div class="live-down-left">
                <div
                  class="live-for"
                  v-for="(item, index) in article.tagList"
                  :key="item"
                  :name="item"
                >
                  <Icon type="ios-pricetag" :color="index | mapTagColor" />
                  {{ item }}
                </div>

                <div class="live-for like">
                  <span class="iconfont icon-liulan" style="color: #d4237a"
                    ><b style="margin-left: 5px">{{
                      article.readCount
                    }}</b></span
                  >
                  <span
                    class="iconfont icon-dianzan"
                    style="margin-left: 5px; color: #d81e06"
                    ><b style="margin-left: 5px">{{
                      article.starCount
                    }}</b></span
                  >
                  <span
                    style="margin-left: 5px; color: #3cc2a8"
                    class="iconfont icon-pinglun_huabanfuben"
                    ><b style="margin-left: 5px">{{
                      article.conCount
                    }}</b></span
                  >
                  <span
                    v-if="article.downloadCount != null"
                    style="margin-left: 5px; color: #1296db"
                    class="iconfont icon-download"
                    ><b style="margin-left: 5px">{{
                      article.downloadCount
                    }}</b></span
                  >
                </div>
              </div>
              <div class="live-down-right">
                <div class="live-name">
                  <Icon
                    type="ios-contact"
                    class="icon"
                    style="color: #1296db"
                  />{{ article.name }}
                </div>
                <div class="live-time">
                  <Icon
                    type="ios-timer-outline"
                    style="color: #d81e06"
                    class="icon"
                  />{{ article.createTime | formatDate }}
                </div>
              </div>
            </div>
          </div>
        </div>
      </Card>
    </a>
  </div>
  <!-- <router-link
            :to="{ name: 'article', params: { articleId: article.id } }"
          > -->
</template>

<script >
import { mixin } from "@/utils";

export default {
  props: {
    article: {
      Type: Object,
    },
    type: "",
  },
  filters: {
    formatDate(value) {
      if (value != null) {
        let date = new Date(value);
        let y = date.getFullYear();
        let MM = date.getMonth() + 1;
        MM = MM < 10 ? "0" + MM : MM;
        let d = date.getDate();
        d = d < 10 ? "0" + d : d;
        let h = date.getHours();
        h = h < 10 ? "0" + h : h;
        let m = date.getMinutes();
        m = m < 10 ? "0" + m : m;
        let s = date.getSeconds();
        s = s < 10 ? "0" + s : s;
        return y + "-" + MM + "-" + d + " " + h + ":" + m + ":" + s;
      }
    },
  },
  mixins: [mixin],
  // computed: {
  //   themeClass: function () {
  //     if (this.article.coverType === 1) {
  //       return "big-image";
  //     } else {
  //       return "";
  //     }
  //   },
  // },
  methods: {
    getContent(article) {
      //判断是博客内容还是源码内容
      if (article.good == null) {
        this.$router.push({
          name: "code",
          query: { id: article.id },
        });
      } else {
        this.$router.push({
          name: "article",
          query: { id: article.id },
        });
      }
    },
  },
};
</script>
<style lang="stylus" rel="stylesheet/stylus" scoped>
@import '../../../common/stylus/index.styl';

.ivu-tag:nth-child(1) {
  margin-left: 10px;
}

.like {
  position: absolute;
  bottom: 0px;
  left: 0px;
}

.iconfont {
  font-size: 14px !important;
}

> a {
  display: block;
  cursor: default;
  border: 1px solid $color-border;

  &:hover {
    border: 1px solid $color-border-hover;
    box-shadow: 2px 2px 3px $color-border;
  }

  .text-wrapper {
    padding: 20px 20px 0 20px;
    text-align: left;

    @media only screen and (max-width: 768px) {
      padding: 15px 15px 0 15px;
    }

    .title {
      font-size: 23px;
      font-weight: 100;
      line-height: 27px;

      span.special {
        border-radius: $border-radius;
        font-size: 15px;
        font-weight: 100;
        padding: 3px 5px;
        margin-left: 1px;
        vertical-align: top;
        color: $default-background-color;
        background: $iview-secondary-warning-color;
        cursor: pointer;
      }

      a {
        color: $color-typegraphy-title;
        cursor: pointer;

        &:hover {
          color: $color-typegraphy-title-hover;
          text-decoration: underline;
        }
      }
    }

    .info {
      margin-top: 10px;
      font-size: 14px;
      line-height: 18px;
      font-weight: 200;

      a {
        color: #777;
        cursor: pointer;

        &:hover {
          color: $color-main-primary;
          text-decoration: underline;
        }
      }

      .publish-time {
        margin-left: 20px;
      }
    }

    .line {
      width: 50px;
      margin-top: 30px;
      border-1px(rgba(7, 17, 27, 0.4));

      &::after {
        margin-bottom: 15px;
      }
    }

    .tags {
      /* cursor: pointer; */
      margin: 8px 0;
    }

    .desc {
      color: #666;
      font-size: 14px;
      line-height: 20px;
      font-weight: 200;

      a {
        color: $color-main-primary;
        font-weight: 500;
        cursor: pointer;

        &:hover {
          text-decoration: underline;
        }
      }
    }

    .operate_info {
      font-size: 14px;
      margin: 15px 0 20px;

      span {
        margin-right: 8px;

        + span {
          margin-left: 8px;
        }

        a {
          cursor: default;

          &:hover {
            color: $color-main-primary;
          }
        }

        img {
          width: 100%;
        }
      }
    }
  }
}

.before {
  box-sizing: border-box;
}

.ivu {
  background-color: #fff;
  margin-bottom: 10px;
  cursor: pointer;
}

.live-cover {
  height: 150px !important;
  background-repeat: no-repeat;
  background-size: cover;
  background-position: 50%;
  border-radius: 4px;
}

.live-info {
  height: 110px;
  padding: 10px;
  overflow: hidden;
}

.live-title {
  font-size: 16px;
  font-weight: 700;
  display: inline-flex;
  justify-content: center;
  align-items: center;
}

.live-desc {
  color: #9ea7b4;
  margin-top: 20px;
  line-height: 20px;
}

.live-down-left {
  float: left;
  width: 200px;
}

.live-down {
  margin-top: -35px;
}

.live-for {
  float: left;
  margin-left: 10px;
}

.live-down-right {
  width: 50px;
  margin-left: 400px;
  margin-top: 20px;
}

.live-name {
  width: 200px;
  font-size: 15px;
}

.live-time {
  color: #9ea7b4;
  width: 200px;
  margin-top: 5px;
  font-size: 10px;
  line-height: 20px;
}

.ivu-col:hover {
  color: #2d8cf0;
}

.ivu-col {
  color: #798788;
}

.icon {
  margin-right: 10px;
}

@media (max-width: 768px) {
  .live-cover {
    height: 100px;
  }

  .live-info {
    height: 100px;
  }

  .live-down {
    display: none;
  }

  .live-title {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: flex-start;
  }

  .ivu-tag:nth-child(1) {
    margin-left: 0px;
  }

  .ivu-col-span-7 {
    width: 45%;
  }

  .ivu-col-span-17 {
    width: 55%;
  }

  /deep/ .ivu-card-body {
    padding: 5px;
  }

  .ivu-tag {
    margin: 0px;
  }

  .live-info {
    height: 100%;
    padding: 10px 0px 10px 10px;
  }
}
</style>
