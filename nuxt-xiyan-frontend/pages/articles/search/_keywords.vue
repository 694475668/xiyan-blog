<template>
  <div class="article-list-content">
    <Row>
      <Col :xs="24" :sm="24" :md="24" :lg="17">
        <section-title
          :mainTitle="'搜索结果'"
          :subTitle="this.$route.query.keywords"
        >
        </section-title>
        <Card
          class="ivu-card ivu-card-bordered"
          style="
            margin-bottom: 10px;
            cursor: pointer;
            background-color: #f5f5f5;
            width: 830px;
          "
          v-for="item in list"
          :key="item.id"
        >
          <div class="ivu-row">
            <a @click="getContent(item)">
              <div class="ivu-col ivu-col-span-7">
                <div
                  class="live-cover"
                  :style="{
                    background: 'url(' + item.pic + ')',
                    backgroundPosition: 'center center ',
                    backgroundSize: 'cover',
                  }"
                ></div>
              </div>
              <div class="ivu-col ivu-col-span-15">
                <div class="live-info">
                  <div class="live-title" v-html="item.title">
                    {{ item.title }}
                  </div>
                  <div class="live-desc" v-html="item.remark">
                    {{ item.remark }}
                  </div>
                </div>
                <div class="live-down">
                  <div class="live-down-left">
                    <div
                      class="live-for"
                      v-for="(item, index) in item.tagList"
                      :key="item"
                      :name="item"
                    >
                      <Icon type="ios-pricetag" :color="index | mapTagColor" />
                      {{ item }}
                    </div>
                  </div>
                  <div class="live-down-right">
                    <div class="live-name">
                      <Icon type="ios-contact" class="icon" />{{ item.name }}
                    </div>
                    <div class="live-time">
                      <Icon type="ios-timer-outline" class="icon" />{{
                        item.createTime | formatDate
                      }}
                    </div>
                  </div>
                </div>
              </div>
            </a>
          </div>
        </Card>
        <Page
          class="mt-10 text-right"
          :total="total"
          :current="searchParam.pageNo"
          :page-size="searchParam.pageSize"
          @on-change="changePage"
          @on-page-size-change="changeSize"
          show-elevator
          show-total
        />
      </Col>
      <Col :xs="0" :sm="0" :md="0" :lg="7">
        <div class="layout-right">
          <recommend></recommend>
          <tag-wall style="margin-top: 15px"></tag-wall>
        </div>
      </Col>
    </Row>
  </div>
</template>

<script>
// mixin
import { mixin } from "@/utils";
import { AESEncrypt } from "@/assets/js/aes";
import Recommend from "@/pages/components/views/Recommend";
import TagWall from "@/pages/components/views/TagWall";
import SectionTitle from "@/pages/components/views/SectionTitle/SectionTitle";
export default {
  data() {
    return {
      //加密后请求服务器的参数
      res: {
        requestData: "",
      },
      searchParam: {
        pageNo: 1,
        pageSize: 5,
        keywords: "",
      },
      list: [],
      total: 0,
    };
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
  mounted() {
    this.getList();
  },
  watch: {
    "$route.query.keywords": function (val, old) {
      if (val !== old) {
        this.getList();
      }
    },
  },
  methods: {
    getContent(item) {
      //判断是博客内容还是源码内容
      if (item.good == null) {
        this.$router.push({
          name: "code-id",
          query: { id: item.id },
        });
      } else {
        this.$router.push({
          name: "article-id",
          query: { id: item.id },
        });
      }
    },
    async getList() {
      this.searchParam.keywords = this.$route.query.keywords;
      //获取保存在cookie的AES密钥
      let aesKey = this.$cookies.get("key");
      //进行参数加密,必须把对象转换json字符串，不然加密不了
      let dataJson = JSON.stringify(this.searchParam);
      //数据进行加密
      this.res.requestData = AESEncrypt(dataJson, aesKey);
      let res = await this.$axios.post("/search/technology/list", this.res, {
        headers: { "Content-Type": "application/x-www-form-urlencoded" },
      });
      if (res.success) {
        this.list = res.voList;
        this.total = res.total;
      }
    },

    changePage(page) {
      this.searchParam.pageNo = page;
      this.getList();
    },
    changeSize(size) {
      this.searchParam.pageSize = size;
      this.searchParam.pageNo = 1;
      this.getList();
    },
  },
  components: {
    recommend: Recommend,
    "tag-wall": TagWall,
    "section-title": SectionTitle,
  },
};
</script>
<style lang="stylus" type="text/stylus" scoped rel="stylesheet/stylus">
@import '~/assets/stylus/theme.styl';

.article-list-content {
  width: auto;

  a {
    width: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
  }

  @media only screen and (max-width: 768px) {
    margin: 5px 5px 0 5px;
  }

  @media screen and (min-width: 768px) {
    margin: 10px 10px 0 10px;
  }

  @media screen and (min-width: 992px) {
    margin: 15px 35px 0 35px;
  }

  @media screen and (min-width: 1200px) {
    width: 1200px;
    margin: 15px auto 0;
    margin-bottom: 200px;
  }

  .layout-left, .layout-right {
    padding: 0;

    @media only screen and (max-width: 768px) {
      padding: 0;
    }

    @media screen and (min-width: 768px) {
      padding: 0;
    }

    @media screen and (min-width: 992px) {
      padding: 0 10px;
    }

    @media screen and (min-width: 1200px) {
      padding: 0 10px;
    }
  }
}

.ivu-card, .ivu-card-bordered {
  border: 1px solid #dcdee2;
  border-color: #e8eaec;
}

.before {
  box-sizing: border-box;
}

.live-cover {
  height: 150px;
  background-repeat: no-repeat;
  background-size: cover;
  background-position: 50%;
  border-radius: 4px;
}

.live-info {
  height: 130px;
  padding: 10px;
}

.live-title {
  font-size: 16px;
  font-weight: 700;
}

.live-desc {
  color: #9ea7b4;
  margin-top: 20px;
  line-height: 20px;
}

.live-down {
  margin-top: -10px;
}

.live-down-left {
  margin-top: 20px;
  float: left;
  width: 340px;
}

.live-for {
  float: left;
  margin-left: 15px;
}

.live-down-right {
  width: 50px;
  margin-left: 400px;
}

.live-name {
  width: 200px;
  font-size: 15px;
}

.live-time {
  color: #9ea7b4;
  width: 200px;
  margin-top: 11px;
  font-size: 10px;
  line-height: 20px;
}

.icon {
  margin-right: 10px;
}
</style>
