<template>
  <div class="article-list-content">
    <Row>
      <Col :xs="24" :sm="24" :md="24" :lg="17">
        <div class="layout-left">
          <section-title
            :mainTitle="'高谈阔论'"
            :btnFlag="true"
            :tipText="'交流'"
            :tipHref="'/postExchange'"
          >
          </section-title>
          <article-list-cell
            v-for="article in articleList"
            :article="article"
            :key="article.id"
          ></article-list-cell>
          <Page
            class="mt-10 text-right"
            :total="total"
            :current="currentPage"
            :page-size="pageSize"
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

<script type="text/ecmascript-6">
import Recommend from "@/components/views/Recommend";
import TagWall from "@/components/views/TagWall";
import ArticleListHeader from "@/components/views/Article/ArticleListHeader";
import ArticleListCell from "@/components/views/Article/ArticleListCell";
import SectionTitle from "@/components/views/SectionTitle/SectionTitle";
import TitleMenuFilter from "@/components/views/SectionTitle/TitleMenuFilter";
import merge from "lodash/merge";
import { DefaultFilterList } from "@/common/js/const";
import FriendLinks from "@/components/views/FriendLinks";

export default {
  data() {
    return {
      articleList: [],
      countTags: [],
      tagsColor: ["#D52BB3", "#E6E61A", "#3CC48D"],
      categoryList: ["java", "hahah"],
      selected_category: this.$route.params.id,
      currentPage: 1,
      pageSize: 5,
      total: 1,
      categoryId: this.$route.params.id,
      defaultFilterList: DefaultFilterList,
    };
  },
  created() {
    if (document.body.offsetWidth <= 678) {
      this.isShow = false;
    }
  },
  methods: {
    listArticle(param) {
      let orderby = {
        articleType: 1003,
        pageSize: this.pageSize,
        currentPage: this.currentPage,
      };
      let params = merge(param, orderby);
      this.$http({
        url: this.$http.adornUrl("/article/list"),
        method: "get",
        params: this.$http.adornParams(params),
      }).then(({ data }) => {
        if (data.result.data !== null && data.status === 0) {
          this.articleList = data.result.data.list;
          this.total = data.result.data.total;
        }
      });
    },
    getTagsColor(index) {
      return this.tagsColor[index];
    },
    changePage(page) {
      this.currentPage = page;
      this.listArticle();
    },
    changeSize(size) {
      this.pageSize = size;
      this.currentPage = 1;
      this.listArticle();
    },
  },
  components: {
    recommend: Recommend,
    "tag-wall": TagWall,
    "article-list-header": ArticleListHeader,
    "article-list-cell": ArticleListCell,
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
