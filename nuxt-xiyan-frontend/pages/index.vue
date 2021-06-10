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
              :show="false"
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
import PhotoWall from "@/pages/components/views/PhotoWall";
import ArticleListCell from "@/pages/components/views/Article/ArticleListCell";
import SectionTitle from "@/pages/components/views/SectionTitle/SectionTitle";
import TitleMenuFilter from "@/pages/components/views/SectionTitle/TitleMenuFilter";
import About from "@/pages/components/views/About";
import FriendLinks from "@/pages/components/views/FriendLinks";
import TagWall from "@/pages/components/views/TagWall";
import Recommend from "@/pages/components/views/Recommend";
import { DefaultFilterList } from "~/assets/js/const";
import { AESEncrypt, AESDecrypt } from "@/assets/js/aes";
import { getUrlKey } from "@/utils/intercept";
import { mapMutations } from "vuex";

export default {
  head() {
    return {
      title: "夕颜源码 - 专注于技术|源码分享的IT技术平台",
      meta: [
        {
          hid: "keywords",
          name: "keywords",
          content:
            "夕颜博客,夕颜源码,夕颜社区,夕颜技术社区,,夕颜IT社区,IT社区,技术社区,Java技术分享,Spring教程,开发者社区,Java毕设,Java博客,Java项目,Java源码,Vue博客,代码,教程,web编程,前端开发,后端开发",
        },
        {
          hid: "description",
          name: "description",
          content:
            "一个专注于技术|源码分享的IT技术平台，大家以共同学习，乐于分享，拥抱开源的价值观进行学习交流",
        },
      ],
    };
  },
  data() {
    return {
      //加密后请求服务器的参数
      res: {
        requestData: "",
      },
      codeList: [],
      defaultFilterList: DefaultFilterList,
      listQuery: {
        pageNo: 1,
        pageSize: 6,
        //默认是以创建时间倒叙排序
        sortField: "create_time",
        type: "",
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
  async asyncData({ $axios }) {
    //获取博客数据
    let param = {
      pageNo: 1,
      pageSize: 6,
      //默认是以创建时间倒叙排序
      sortField: "create_time",
      type: "",
    };
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
  mounted() {
    //初始页一打开就定位到顶部
    document.documentElement.scrollTop = 0;
    var param = getUrlKey("param", window.location.href);
    if (param != null) {
      //处理特殊字符的转义
      var repData = param.replace(/ /g, "+");
      let aesKey = this.$cookies.get("key");
      let res = JSON.parse(AESDecrypt(repData, aesKey));
      this.tripartiteLogin(res);
    }
  },
  methods: {
    //引入方法并使用
    ...mapMutations(["setUserInfo"]),
    tripartiteLogin(res) {
      // 登录成功，向Vuex中设置用户信息
      this.setUserInfo({
        token: res.token,
        user: res.userVO,
      });
      const inFifteenMinutes = new Date(
        new Date().getTime() + 24 * 60 * 60 * 1000
      );
      // Cookie的过期时间与Token的过期时间一致，为24小时
      this.$cookies.set("token", res.token, { expires: inFifteenMinutes });
      this.$cookies.set("user", res.userVO, { expires: inFifteenMinutes });
      this.$router.push({ name: "index" });
    },
    async getList(param) {
      this.listQuery.sortField = param.sortField;
      //获取保存在cookie的AES密钥
      let aesKey = this.$cookies.get("key");
      //进行参数加密,必须把对象转换json字符串，不然加密不了
      let dataJson = JSON.stringify(this.listQuery);
      //数据进行加密
      this.res.requestData = AESEncrypt(dataJson, aesKey);
      //设置Content-Type 为application/x-www-form-urlencoded 为了兼容苹果浏览器和ie浏览器
      let res = await this.$axios.post("/web/code/list", this.res, {
        headers: { "Content-Type": "application/x-www-form-urlencoded" },
      });
      this.codeList = res.voList;
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
