<template>
  <div class="article-list-content">
    <Row>
      <Col :xs="24" :sm="24" :md="24" :lg="17">
        <div class="layout-left">
          <section-title :mainTitle="'金币充值'" :subTitle="'Recharge'">
          </section-title>
          <div class="pay">
            <!--主内容开始编辑-->
            <div class="tr_recharge">
              <div class="tr_rechtext">
                <p class="te_retit">
                  <img src="../../assets/images/金币.svg" alt="" />充值中心
                </p>
                <p>（1）充值的比例为：1元=10金币</p>
                <p>（2）充值越多，赠送的金币越多。</p>
                <p>（3）支持支付宝，微信等充值方式。</p>
                <p>（4）自定义充值请联系客服。</p>
                <p>
                  （5）充值所得金币仅限在夕颜源码使用，最终解释权归夕颜源码所有。
                </p>
                <p>
                  （6）充值若失败请联系客服QQ694475668（PS：目前充值成功率100%）
                </p>
                <p>
                  （7）充值成功后若发现金币数量不变，请刷新页面或者重新登录。
                </p>
              </div>
              <form action="" class="am-form" id="doc-vld-msg">
                <div class="tr_rechbox">
                  <div class="tr_rechhead" v-if="userInfo.name != undefined">
                    <img :src="userInfo.photo" />
                    <p>
                      充值帐号：
                      <a>{{ userInfo.name }}</a>
                    </p>
                    <div class="tr_rechheadcion">
                      <img src="../../assets/images/金币.svg" alt="" />
                      <span
                        >当前余额：<span>{{ userInfo.point }}</span></span
                      >
                    </div>
                  </div>
                  <div class="tr_rechli am-form-group">
                    <ul class="ui-choose am-form-group" id="uc_01">
                      <li class="price1" @click="select('price1')">
                        <label class="am-radio-inline">
                          <input type="radio" />
                          VIP会员
                        </label>
                      </li>
                      <li class="price2" @click="select('price2')">
                        <label class="am-radio-inline">
                          <input type="radio" />
                          10￥/100金币
                        </label>
                      </li>
                      <li class="price3" @click="select('price3')">
                        <label class="am-radio-inline">
                          <input type="radio" />
                          30/350金币
                        </label>
                      </li>

                      <li class="price4" @click="select('price4')">
                        <label class="am-radio-inline">
                          <input type="radio" />
                          50￥/1000金币
                        </label>
                      </li>
                      <li class="price5" @click="select('price5')">
                        <label class="am-radio-inline">
                          <input type="radio" />
                          100￥/3000金币
                        </label>
                      </li>
                    </ul>
                    <!--<span>1招兵币=1元 10元起充</span>-->
                  </div>
                  <div>
                    <span>充值方式：</span>
                    <RadioGroup
                      v-model="payParam.type"
                      style="margin: 15px 0px 15px 0px"
                    >
                      <Radio label="1">
                        <img
                          class="box"
                          src="../../assets/images/wechatpay.png"
                        />
                      </Radio>
                      <Radio label="2" style="margin-left: 20px">
                        <img class="box" src="../../assets/images/zfbpay.png" />
                      </Radio>
                    </RadioGroup>
                  </div>
                  <div class="tr_rechnum">
                    <span>应付金额：</span>
                    <p class="rechnum">{{ payParam.price }}</p>
                  </div>
                </div>
                <div class="tr_paybox">
                  <input
                    @click="pay"
                    type="button"
                    style="cursor: pointer"
                    value="立即充值"
                    class="tr_pay am-btn"
                  />
                </div>
              </form>
            </div>
          </div>
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
    <form
      style="display: none"
      id="form"
      method="post"
      action="http://pay.xiyanyuanma.com/createOrder"
    >
      <input name="payId" id="payId" type="text" value="" />
      <input name="type" id="type" type="text" value="" />
      <input name="price" id="price" type="text" value="" />
      <input name="sign" id="sign" type="text" value="" />
      <input name="param" id="param" type="text" value="" />
      <input name="isHtml" id="isHtml" type="text" value="" />
      <input type="submit" id="submit" />
    </form>
  </div>
</template>

<script>
import Recommend from "@/components/views/Recommend";
import TagWall from "@/components/views/TagWall";
import SectionTitle from "@/components/views/SectionTitle/SectionTitle";
import FriendLinks from "@/components/views/FriendLinks";
import { list } from "@/api/code";
import { create } from "@/api/order";
import { getUserInfo, getAes } from "@/utils/auth";
import { AESEncrypt } from "@/api/aes";
export default {
  data() {
    return {
      userInfo: {},
      res: {
        requestData: "",
      },
      payParam: {
        price: 199,
        type: "1",
      },
    };
  },
  created() {
    if (getUserInfo() != undefined) {
      this.userInfo = JSON.parse(getUserInfo());
    }
  },
  mounted() {
    $(".price1").addClass("selected");
  },
  methods: {
    pay() {
      //获取保存在cookie的AES密钥
      let aesKey = getAes();
      //进行参数加密,必须把对象转换json字符串，不然加密不了
      let dataJson = JSON.stringify(this.payParam);
      //数据进行加密
      this.res.requestData = AESEncrypt(dataJson, aesKey);
      create(this.res).then((res) => {
        if (res.success) {
          $("#payId").val(res.payId);
          $("#type").val(res.type);
          $("#sign").val(res.sign);
          $("#param").val(res.param);
          $("#price").val(res.price);
          $("#isHtml").val(res.isHtml);
          $("#submit").click();
          $(".tr_pay").attr("disabled", true);
        }
      });
    },
    select(item) {
      if (item == "price1") {
        this.payParam.price = 199;
        $(".price1").addClass("selected");
        $(".price2").removeClass("selected");
        $(".price3").removeClass("selected");
        $(".price4").removeClass("selected");
        $(".price5").removeClass("selected");
      } else if (item == "price2") {
        this.payParam.price = 10;
        $(".price1").removeClass("selected");
        $(".price2").addClass("selected");
        $(".price3").removeClass("selected");
        $(".price4").removeClass("selected");
        $(".price5").removeClass("selected");
      } else if (item == "price3") {
        this.payParam.price = 30;
        $(".price1").removeClass("selected");
        $(".price2").removeClass("selected");
        $(".price3").addClass("selected");
        $(".price4").removeClass("selected");
        $(".price5").removeClass("selected");
      } else if (item == "price4") {
        this.payParam.price = 50;
        $(".price1").removeClass("selected");
        $(".price2").removeClass("selected");
        $(".price3").removeClass("selected");
        $(".price4").addClass("selected");
        $(".price5").removeClass("selected");
      } else if (item == "price5") {
        this.payParam.price = 100;
        $(".price1").removeClass("selected");
        $(".price2").removeClass("selected");
        $(".price3").removeClass("selected");
        $(".price4").removeClass("selected");
        $(".price5").addClass("selected");
      }
    },
  },
  components: {
    recommend: Recommend,
    "tag-wall": TagWall,
    "section-title": SectionTitle,
    "friend-links": FriendLinks,
  },
};
</script>
<style>
</style>
<style lang="stylus" scoped rel="stylesheet/stylus">
@import '../../common/stylus/main.css';

.ivu-radio-wrapper {
  display: inline-flex;
  justify-content: center;
  align-items: center;
}

.box {
  height: 50px;
  padding: 10px;
  border: 1px solid #e5e5e5;
  width: 130px;
  height: 50px;
}

.article-list-content {
  width: auto;
  min-height: calc(100vh - 308px);

  @media only screen and (max-width: 768px) {
    .tr_rechheadcion {
      float: left;
    }

    .ui-choose li {
      margin-top: 30px;
    }

    .ivu-radio-group {
      width: 100%;
    }

    .box {
      width: 120px;
      height: 50px;
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
