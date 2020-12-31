<template>
  <div class="title-menu-timeline">
    <div class="date-picker">
      <DatePicker
        type="daterange"
        confirm
        placement="bottom-end"
        placeholder="选择存档日期区间"
      ></DatePicker>
    </div>
  </div>
</template>

<script type="text/ecmascript-6">
</script>

<style lang="stylus" scoped rel="stylesheet/stylus" scoped>
@import '../../../common/stylus/theme.styl';

.title-menu-timeline {
  .date-picker {
    width: 175px;
    margin-left: 15px;
    font-size: 13px;
  }
}
</style>
