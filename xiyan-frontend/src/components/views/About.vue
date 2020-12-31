<template>
  <div class="about">
    <img class="background" src="../../assets/350.jpg" alt="" />
    <img class="avatar" src="../../assets/tx.gif" alt="" />
    <p class="name">夕颜源码</p>
    <p class="desc">热爱自己</p>
    <div class="line"></div>
    <ul class="social">
      <li>
        <div style="float: left; margin-top: 0px" class="desc">社交:</div>
        <a
          href="http://wpa.qq.com/msgrd?v=3&uin=694475668&site=qq&menu=yes"
          target="_blank"
          ><img src="../../assets/icon/QQ.png" alt=""
        /></a>
        <el-popover placement="top-start" trigger="click">
          <div>
            <img
              src="../../assets/icon/bright.jpg"
              width="250px"
              height="280px"
            />
          </div>
          <div slot="reference">
            <a
              ><img
                src="../../assets/icon/vx.png"
                width="39.59px"
                height="39.59px"
            /></a>
          </div>
        </el-popover>
        <a
          href="https://www.npmjs.com/settings/bright-boy/packages"
          target="_blank"
          ><img src="../../assets/icon/npm.png" alt=""
        /></a>
        <a href="https://blog.csdn.net/qq_40942490" target="_blank"
          ><img src="../../assets/icon/csdn.png" alt=""
        /></a>
      </li>
    </ul>
    <div class="line"></div>
    <ul class="social">
      <li>
        <div style="float: left; margin-top: 0px" class="desc">加入我们:</div>
        <a
          href="https://qm.qq.com/cgi-bin/qm/qr?k=MR8TChVi_FtBishIZO7FvkUNaxb5L7ei&jump_from=webapi"
          target="_blank"
          alt="夕颜源码"
          title="夕颜源码"
          style="margin-left: -30px"
          ><img src="../../assets/icon/QQqun.png" alt=""
        /></a>
      </li>
    </ul>
    <h4>技能值</h4>
    <div class="progresses">
      <Row :gutter="20">
        <Col :span="6">
          <p class="title">Java</p>
        </Col>
        <Col :span="18">
          <Progress
            status="active"
            :hide-info="true"
            :stroke-width="10"
            :percent="60"
            class="bar"
          ></Progress>
        </Col>
      </Row>
      <Row :gutter="20">
        <Col :span="6">
          <p class="title">Vue</p>
        </Col>
        <Col :span="18">
          <Progress
            status="wrong"
            :hide-info="true"
            :stroke-width="10"
            :percent="40"
            class="bar"
          ></Progress>
        </Col>
      </Row>
      <Row :gutter="20">
        <Col :span="6">
          <p class="title">Go</p>
        </Col>
        <Col :span="18">
          <Progress
            status="success"
            :hide-info="true"
            :stroke-width="10"
            :percent="10"
            class="bar"
          ></Progress>
        </Col>
      </Row>
      <Row :gutter="20">
        <Col :span="6">
          <p class="title">Cloud</p>
        </Col>
        <Col :span="18">
          <Progress
            status="normal"
            :hide-info="true"
            :stroke-width="10"
            :percent="30"
            class="bar"
            stroke-color="yellow"
          ></Progress>
        </Col>
      </Row>
    </div>
  </div>
</template>

<script type="text/ecmascript-6">
export default {};
</script>

<style lang="stylus" scoped rel="stylesheet/stylus" scoped>
@import '../../common/stylus/theme.styl';

.about {
  position: relative;
  text-align: center;
  border: 1px solid $color-border;
  padding-bottom: 20px;
  background-color: #fff;

  img.background {
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    width: 100%;
    height: 125px;
    z-index: 1;
  }

  img.avatar {
    position: relative;
    margin: 75px auto 15px;
    width: 100px;
    height: 100px;
    border: 5px solid $color-gradually-gray-91;
    border-radius: 50%;
    z-index: 9;
  }

  .name {
    font-size: 22px;
    color: $color-typegraphy-title;
    line-height: 30px;
    font-weight: 700;
  }

  .desc {
    font-size: 15px;
    color: $color-secondary-info;
    line-height: 30px;
    font-weight: 100;
  }

  .social {
    text-align: center;
    padding: 0 20px;
    margin-top: 15px;
    list-style: none;

    > li {
      padding: 8px;

      a, .el-popover__reference {
        display: inline-block;
        width: 44px;
        height: 44px;
        margin: auto;

        img {
          width: 90%;
        }
      }
    }
  }

  .line {
    height: 1px;
    background-color: $color-gradually-gray-91;
    margin: 10px 20px;
  }

  h4 {
    font-size: 19px;
    margin: 30px 0 20px;
    font-weight: 600;
  }

  .progresses {
    padding: 0 20px;

    p.title {
      height: 38px;
      line-height: 38px;
      text-align: right;
    }

    .bar {
      margin: 10px 0;
    }
  }
}
</style>
