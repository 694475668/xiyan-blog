<template>
  <div id="article-page-content">
    <slot name="content"></slot>
  </div>
</template>

<script>
export default {};
</script>

<style lang="stylus" scoped rel="stylesheet/stylus">
@import '../../../common/stylus/article.styl';

#article-page-content {
  padding: 5px;
}
</style>
