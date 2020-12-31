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
import ArticlePageHeader from "@/components/views/Article/ArticlePageHeader";
import ArticlePageContent from "@/components/views/Article/ArticlePageContent";
import ArticlePageFooter from "@/components/views/Article/ArticlePageFooter";
import TagWall from "@/components/views/TagWall";
import Recommend from "@/components/views/Recommend";
import Personal from "@/components/views/Personal";
import TOC from "@/common/js/MarkdownToc";
import SideToc from "@/components/views/SideToc";
// 样式文件
import "highlight.js/styles/solarized-light.css";
// TOC滚动监听
import TocScrollSpy from "@/common/js/TocScrollSpy";
import { getArticleById, browse } from "@/api/blog";
import { getAes } from "@/utils/auth";

export default {
  data() {
    return {
      article: {},
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
  mounted() {
    //获取id和内容标识
    let id = this.$route.query.id;
    this.getArticle(id);
  },
  methods: {
    getArticle(id) {
      //获取保存在cookie的AES密钥
      let aesKey = getAes();
      //为了处理aes还没有写入cookie就调接口了
      const timer = setInterval(() => {
        if (aesKey != undefined) {
          getArticleById(id).then((res) => {
            if (res.success) {
              this.article = res;
              // 更新目录、高亮代码
              this.$nextTick(() => {
                this.refreshDiectory(); //这个就是更新渲染目录
              });
            }
          });
          browse(id).then((res) => {});
          clearInterval(timer);
          return;
        }
        aesKey = getAes();
      }, 50);
    },
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
