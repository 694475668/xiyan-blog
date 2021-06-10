<template>
  <div class="timeline-header">
    <section-title :mainTitle="'开发工具'" :subTitle="'Tool'"> </section-title>
  </div>
</template>

<script type="text/ecmascript-6">
import SectionTitle from "@/components/views/SectionTitle/SectionTitle";
import TitleMenuTimeLine from "@/components/views/SectionTitle/TitleMenuTimeLine";

export default {
  components: {
    "section-title": SectionTitle,
    "title-menu-timeline": TitleMenuTimeLine,
  },
};
</script>

<style lang="stylus" scoped rel="stylesheet/stylus"></style>
