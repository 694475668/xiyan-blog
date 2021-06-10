<template>
  <div class="article-list-content">
    <Row>
      <Col :xs="24" :sm="24" :md="24" :lg="17">
        <div class="layout-left">
          <section-title
            :mainTitle="'毕设源码'"
            :btnFlag="true"
            :tipText="'源码赚取金币'"
            :tipHref="'/publishProject'"
          >
            <title-menu-filter
              @filterByMenu="getList"
              slot="menu"
              :menu-filter-list="defaultFilterList"
              :title="'源码'"
              :show="true"
            ></title-menu-filter>
          </section-title>
          <article-list-cell
            v-for="code in codeList"
            :article="code"
            :key="code.id"
          ></article-list-cell>
          <Page
            class="mt-10 text-right"
            :total="total"
            :current="listQuery.pageNo"
            :page-size="listQuery.pageSize"
            @on-change="changePage"
            @on-page-size-change="changeSize"
            :show-elevator="isShow"
            :show-total="isShow"
            :show-sizer="isShow"
            :page-size-opts="[5, 10, 15, 20]"
          />
        </div>
      </Col>
      <Col :xs="0" :sm="0" :md="0" :lg="7">
        <div class="layout-right">
          <recommend></recommend>
          <tag-wall style="margin-top: 15px"></tag-wall>
          <friend-links style="margin-top: 15px"></friend-links>
        </div>
      </Col>
    </Row>
  </div>
</template>

<script>
import ArticleListCell from "@/pages/components/views/Article/ArticleListCell";
import Recommend from "@/pages/components/views/Recommend";
import TagWall from "@/pages/components/views/TagWall";
import SectionTitle from "@/pages/components/views/SectionTitle/SectionTitle";
import TitleMenuFilter from "@/pages/components/views/SectionTitle/TitleMenuFilter";
import { DefaultFilterList } from "~/assets/js/const";
import FriendLinks from "@/pages/components/views/FriendLinks";
import { AESEncrypt } from "@/assets/js/aes";
export default {
  head() {
    return {
      title: "毕设源码",
      meta: [
        {
          name: "keywords",
          content:
            "Java毕设,Java项目,Java源码,Vue博客,代码,教程,web编程,前端开发,后端开发",
        },
        {
          name: "description",
          content:
            "Java毕设,Java项目,Java源码,Vue博客,代码,教程,web编程,前端开发,后端开发",
        },
      ],
    };
  },
  async asyncData({ $axios, route }) {
    //获取路径的参数
    var type = route.fullPath.substring(
      route.fullPath.indexOf("=") + 1,
      route.fullPath.length
    );
    //获取博客数据
    let param = {
      pageNo: 1,
      pageSize: 5,
      //默认是以创建时间倒叙排序
      sortField: "create_time",
      type: "",
    };
    //如果没有参数默认是路由地址，所以这里要排除掉
    if (type != "/codes") {
      param.type = type;
    }
    //需要里面有多个接口需要统一接收参数的话let [第一个接口返回结果，第二个接口返回结果]
    let [data] = await Promise.all([
      $axios.post("/web/code/list", param).then((res) => {
        return res;
      }),
    ]);
    //需要在外面进行返回
    return {
      codeList: data.voList,
      total: data.total,
    };
  },
  data() {
    return {
      //加密后请求服务器的参数
      res: {
        requestData: "",
      },
      isShow: true,
      codeList: [],
      total: 0,
      listQuery: {
        pageNo: 1,
        pageSize: 5,
        //默认是以创建时间倒叙排序
        sortField: "create_time",
        type: "",
      },
      defaultFilterList: DefaultFilterList,
    };
  },
  watch: {
    //监听路由变化
    $route(to, from) {
      // 对路由变化作出响应...
      let type = this.$route.query.type;
      this.listQuery.type = type;
      if (type == undefined) {
        this.listQuery.type = "";
      }
      this.getList();
    },
  },
  mounted() {
    //初始页一打开就定位到顶部
    document.documentElement.scrollTop = 0;
    this.$nextTick(function () {
      if (document.body.offsetWidth <= 678) {
        this.isShow = false;
      }
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
    async getList(param) {
      if (param != undefined) {
        this.listQuery.sortField = param.sortField;
      }
      //获取保存在cookie的AES密钥
      let aesKey = this.$cookies.get("key");
      //进行参数加密,必须把对象转换json字符串，不然加密不了
      let dataJson = JSON.stringify(this.listQuery);
      //数据进行加密
      this.res.requestData = AESEncrypt(dataJson, aesKey);
      let res = await this.$axios.post("/web/code/list", this.res, {
        headers: { "Content-Type": "application/x-www-form-urlencoded" },
      });
      if (res.success) {
        this.codeList = res.voList;
        this.total = res.total;
        //初始页一打开就定位到顶部
        document.documentElement.scrollTop = 0;
      }
    },
  },
  components: {
    "article-list-cell": ArticleListCell,
    recommend: Recommend,
    "tag-wall": TagWall,
    "section-title": SectionTitle,
    "title-menu-filter": TitleMenuFilter,
    "friend-links": FriendLinks,
  },
};
</script>

<style lang="stylus" scoped rel="stylesheet/stylus">
.ivu-modal-footer {
  display: none !important;
}

.article-list-content {
  width: auto;
  min-height: calc(100vh - 308px);

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
</style>
