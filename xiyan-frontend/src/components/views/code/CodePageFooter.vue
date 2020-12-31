<template>
  <div class="article-page-footer">
    <license-tag :tags="tags"></license-tag>
    <social-section :type="'2'" :gold="gold"></social-section>
  </div>
</template>

<script>
import LicenseTag from "@/components/views/LicenseTag";
import SocialSection from "@/components/views/Comment/SocialSection";

export default {
  data() {
    return {};
  },
  components: {
    "license-tag": LicenseTag,
    "social-section": SocialSection,
  },
  props: {
    postId: Number,
    tags: Array,
    gold: Number,
  },
};
</script>

<style lang="stylus" scoped rel="stylesheet/stylus">
@import '../../../common/stylus/theme.styl';

.article-page-footer {
  text-align: left;

  .operate_menu {
    margin-top: 30px;
  }

  .comment-menu {
    margin-top: 30px;

    .comment-menu-item {
      margin-bottom: 20px;
    }

    p.comment-menu-item {
      a {
        font-size: 14px;

        &:hover {
          color: $color-main-primary;
        }
      }
    }
  }
}

.operate_info {
  text-align: right;
  font-size: 14px;
  margin-left: 15 0 0 0;
}
</style>
