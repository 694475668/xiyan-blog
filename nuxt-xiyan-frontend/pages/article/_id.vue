<template>
  <div class="home-content" v-cloak>
    <Row>
      <Col :xs="24" :sm="24" :md="24" :lg="17">
        <div
          class="layout-left"
          style="background-color: #fff; padding: 30px 20px 10px 20px"
        >
          <article-page-header :article="article"></article-page-header>
          <article-page-content>
            <article
              id="article-main-page"
              class="typo container"
              slot="content"
              ref="article"
              v-html="article.content"
              v-viewer
            ></article>
          </article-page-content>
          <article-page-footer
            :tags="article.tagList"
            :postId="article.id"
          ></article-page-footer>
        </div>
      </Col>
      <Col :xs="0" :sm="0" :md="0" :lg="7">
        <div class="layout-right">
          <recommend></recommend>
          <personal
            v-if="Object.keys(article).length > 0"
            :type="'1'"
            :userInfo="article.user"
          ></personal>
          <Affix :offset-top="60">
            <side-toc style="margin-top: 15px"></side-toc>
          </Affix>
        </div>
      </Col>
    </Row>
  </div>
</template>
<script>
import ArticlePageHeader from "@/pages/components/views/Article/ArticlePageHeader";
import ArticlePageContent from "@/pages/components/views/Article/ArticlePageContent";
import ArticlePageFooter from "@/pages/components/views/Article/ArticlePageFooter";
import TagWall from "@/pages/components/views/TagWall";
import Recommend from "@/pages/components/views/Recommend";
import Personal from "@/pages/components/views/Personal";
import TOC from "~/assets/js/MarkdownToc";
import SideToc from "@/pages/components/views/SideToc";
// 样式文件
import "highlight.js/styles/solarized-light.css";
// TOC滚动监听
import TocScrollSpy from "~/assets/js/TocScrollSpy";

export default {
  head() {
    return {
      title: this.article.title,
      meta: [
        {
          name: "keywords",
          content: this.article.remark,
        },
        {
          name: "description",
          content: this.article.remark,
        },
      ],
    };
  },
  data() {
    return {
      article: {
        title: "夕颜源码 - 专注于技术|源码分享的IT技术平台",
        remark:
          "夕颜博客,夕颜源码,夕颜社区,夕颜技术社区,,夕颜IT社区,IT社区,技术社区,Java技术分享,Spring教程,开发者社区,Java毕设,Java博客,Java项目,Java源码,Vue博客,代码,教程,web编程,前端开发,后端开发",
      },
      arrnum: 5,
    };
  },
  components: {
    "article-page-header": ArticlePageHeader,
    "article-page-content": ArticlePageContent,
    "article-page-footer": ArticlePageFooter,
    "tag-wall": TagWall,
    recommend: Recommend,
    personal: Personal,
    "side-toc": SideToc,
  },
  async asyncData({ $axios, query }) {
    let [data1, data2] = await Promise.all([
      $axios.get("/web/article/by/" + query.id).then((res) => {
        return res;
      }),
      $axios.get("/web/article/browse/" + query.id).then((res) => {
        return res;
      }),
    ]);
    return {
      article: data1,
    };
  },
  mounted() {
    //初始页一打开就定位到顶部
    document.documentElement.scrollTop = 0;
    this.refreshDiectory();
  },
  methods: {
    refreshDiectory() {
      /* eslint-disable 目录列表*/
      new TOC("article-main-page", {
        level: 8,
        top: 0,
        class: "list",
        targetId: "side-toc",
      });
      /* eslint-disable 滚动监听 */
      new TocScrollSpy("article-main-page", "side-toc", {
        spayLevel: 8,
        articleMarginTop: 0,
      });
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
    .layout-left {
      padding: 30px 15px 10px 15px !important;
    }
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
