<template>
  <div class="article-list-header">
    <section-title :mainTitle="mainTitle" :subTitle="subTitle">
      <title-menu-filter
        @filterByMenu="filterByMenu"
        slot="menu"
        :menu-filter-list="defaultFilterList"
      ></title-menu-filter>
    </section-title>
  </div>
</template>

<script type="text/ecmascript-6">
import SectionTitle from "@/components/views/SectionTitle/SectionTitle";
import TitleMenuFilter from "@/components/views/SectionTitle/TitleMenuFilter";
import { DefaultFilterList } from "@/common/js/const";
export default {
  data() {
    return {
      defaultFilterList: DefaultFilterList,
      categoryParam: {},
    };
  },
  components: {
    "section-title": SectionTitle,
    "title-menu-filter": TitleMenuFilter,
  },
  props: {
    categorys: Array,
    defaultCategory: "",
    mainTitle: "",
    subTitle: "",
  },
  methods: {
    filterByMenu(params) {
      this.$emit("filterByMenu", params);
    },
    filterByCategory(params) {
      this.$emit("filterByCategory", params);
    },
  },
};
</script>

<style lang="stylus" scoped rel="stylesheet/stylus"></style>
