<template>
  <div class="license-tag">
    <Row>
      <Col :xs="24" :sm="14" :md="14" :lg="14">
        <span
          class="live-for"
          v-for="(item, index) in tags"
          :key="item"
          :name="item"
        >
          <Tag :checked="false"
            ><Icon
              type="ios-pricetag"
              :style="{ color: getTagsColor(index) }"
            />{{ item }}</Tag
          >
        </span>
      </Col>
    </Row>
  </div>
</template>

<script>
export default {
  props: {
    tags: Array,
  },
  data() {
    return {
      tagsColor: ["#D52BB3", "#3CC48D", "#E6E61A", "#1296db", "#13227a"],
    };
  },
  methods: {
    getTagsColor(index) {
      return this.tagsColor[index];
    },
  },
};
</script>

<style lang="stylus" scoped rel="stylesheet/stylus">
@import '../../common/stylus/theme.styl';

.license-tag {
  margin: 10px 0;
}

.ivu-tag {
  font-size: 14px;
}
</style>
