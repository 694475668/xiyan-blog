<template>
  <div class="home-content">
    <Row>
      <Col :xs="24" :sm="24" :md="24" :lg="17">
        <div class="layout-left">
          <photo-wall></photo-wall>
          <section-title :mainTitle="'源码'" :subTitle="'Code'">
            <title-menu-filter
              @filterByMenu="getList"
              slot="menu"
              :menu-filter-list="defaultFilterList"
            ></title-menu-filter>
          </section-title>
          <code-list-cell
            v-for="code in codeList"
            :article="code"
            :key="code.id"
            :type="'code'"
          ></code-list-cell>
        </div>
      </Col>
      <Col :xs="0" :sm="0" :md="0" :lg="7">
        <div class="layout-right">
          <about></about>
          <recommend></recommend>
          <tag-wall style="margin-top: 15px"></tag-wall>
          <friend-links style="margin-top: 15px"></friend-links>
        </div>
        <div class="layout-left"></div>
      </Col>
    </Row>
  </div>
</template>

<script>
import PhotoWall from "@/components/views/PhotoWall";
import ArticleListCell from "@/components/views/Article/ArticleListCell";
import SectionTitle from "@/components/views/SectionTitle/SectionTitle";
import TitleMenuFilter from "@/components/views/SectionTitle/TitleMenuFilter";
import About from "@/components/views/About";
import FriendLinks from "@/components/views/FriendLinks";
import TagWall from "@/components/views/TagWall";
import Recommend from "@/components/views/Recommend";
import { DefaultFilterList } from "@/common/js/const";
import { list } from "@/api/code";
import { getAes } from "@/utils/auth";
import { AESEncrypt } from "@/api/aes";

export default {
  data() {
    return {
      //加密后请求服务器的参数
      res: {
        requestData: "",
      },
      codeList: [],
      defaultFilterList: DefaultFilterList,
      total: null,
      listQuery: {
        pageNo: 1,
        pageSize: 6,
        //默认是以创建时间倒叙排序
        sortField: "createTime",
      },
    };
  },
  components: {
    "photo-wall": PhotoWall,
    "code-list-cell": ArticleListCell,
    "section-title": SectionTitle,
    "title-menu-filter": TitleMenuFilter,
    about: About,
    "friend-links": FriendLinks,
    "tag-wall": TagWall,
    recommend: Recommend,
  },
  created() {
    this.getList();
  },
  mounted() {
    this.$nextTick(() => {
      document.querySelector(".main-wrapper").style.margin = "61px auto";
    });
  },
  methods: {
    changePage(page) {
      this.listQuery.pageNo = page;
      this.getList();
    },
    changeSize(size) {
      this.listQuery.pageSize = size;
      this.listQuery.pageNo = 1;
      this.getList();
    },
    getList(param) {
      if (param != undefined) {
        this.listQuery.sortField = param.sortField;
      }
      //获取保存在cookie的AES密钥
      let aesKey = getAes();
      //为了处理aes还没有写入cookie就调接口了
      const timer = setInterval(() => {
        if (aesKey != undefined) {
          //进行参数加密,必须把对象转换json字符串，不然加密不了
          let dataJson = JSON.stringify(this.listQuery);
          //数据进行加密
          this.res.requestData = AESEncrypt(dataJson, aesKey);
          list(this.res).then((res) => {
            this.codeList = res.voList;
            this.total = res.total;
          });
          clearInterval(timer);
          return;
        }
        aesKey = getAes();
      }, 50);
    },
  },
};
</script>
<style lang="stylus" scoped rel="stylesheet/stylus">
.ivu-modal-footer {
  display: none !important;
}

.home-content {
  width: auto;
  min-height: calc(100vh - 108px);

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

/* .live-bg{
  background-image:url({{this.imgUrl}})
} */
</style>
