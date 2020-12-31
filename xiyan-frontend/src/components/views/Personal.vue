<template>
  <div class="recommend">
    <Card>
      <div style="text-align: center">
        <img style="width: 120px; height: 120px" :src="userInfo.photo" />
        <h2>
          {{ userInfo.name }}
          <span style="color: #c00; font-size: 14px">（超级码农）</span>
          <div
            style="
              font-size: 14px;
              display: flex;
              justify-content: center;
              align-items: center;
            "
          >
            <img
              src="@/assets/images/金币.svg"
              style="width: 26px; height: 26px"
            /><span style="color: #ffa100"> {{ userInfo.point }}金币 </span>
            <img
              src="@/assets/images/关注.svg"
              style="margin-left: 10px; width: 26px; height: 26px"
            /><span style="color: #999999">
              ({{ userInfo.attentionCount }})粉丝
            </span>
            <img
              src="@/assets/images/发布.svg"
              style="margin-left: 10px; width: 26px; height: 26px"
            /><span style="color: #999999">
              ({{ userInfo.codeCount }})发布
            </span>
          </div>
          <div style="margin-top: 20px">
            <Button
              class="iconfont icon-guanzhu"
              :type="attention == '关注' ? 'info' : 'success'"
              ghost
              @click="addAttention(attention)"
              ><i class="iconfont icon-guanzhu" style="margin-right: 5px"></i
              >{{ attention }}</Button
            >
            <Button type="warning" ghost style="margin-left: 20px"
              ><i class="iconfont icon-sixin1" style="margin-right: 5px"></i
              >私信</Button
            >
          </div>
        </h2>
      </div>
    </Card>
    <Card style="margin-top: 20px">
      <div style="text-align: center">
        <div
          v-if="type == '2'"
          style="display: flex; justify-content: center; align-items: center"
        >
          <img
            src="@/assets/images/金币.svg"
            style="width: 26px; height: 26px"
          />所需金币：<span style="color: #ffa100; font-size: 18px">{{
            gold
          }}</span>
          <img
            v-if="Object.keys(user).length > 0"
            src="@/assets/images/金币.svg"
            style="margin-left: 30px; width: 26px; height: 26px"
          />我的金币：<span style="color: #ffa100; font-size: 18px">{{
            user.point
          }}</span>
        </div>
        <div class="point">
          <p>赚金币方法：</p>
          <p>1. 注册就送5金币</p>
          <p>2. 发布源码，供别人下载赚取金币</p>
          <p>3. 自动充值或联系QQ694475668人工充值</p>
        </div>
      </div>
    </Card>
  </div>
</template>

<script>
import { getAes } from "@/utils/auth";
import { AESEncrypt } from "@/api/aes";
import { getUserInfo } from "@/utils/auth";
import { add, del, query } from "@/api/attention";
export default {
  data() {
    return {
      user: {},
      attention: "关注",
      //加密后请求服务器的参数
      res: {
        requestData: "",
      },
      attentionParam: {
        username: "",
      },
    };
  },
  props: {
    type: String,
    userInfo: Object,
    gold: Number,
  },
  created() {
    if (getUserInfo() != undefined) {
      this.user = JSON.parse(getUserInfo());
    }
    this.attentionParam.username = this.userInfo.username;
    this.getList();
  },
  methods: {
    getList() {
      if (getUserInfo() != undefined) {
        //获取保存在cookie的AES密钥
        let aesKey = getAes();
        //为了处理aes还没有写入cookie就调接口了
        const timer = setInterval(() => {
          if (aesKey != undefined) {
            //进行参数加密,必须把对象转换json字符串，不然加密不了
            let dataJson = JSON.stringify(this.attentionParam);
            //数据进行加密
            this.res.requestData = AESEncrypt(dataJson, aesKey);
            query(this.res).then((res) => {
              if (res.errorCode == "E0710") {
                this.attention = "取消关注";
              } else {
                this.attention = "关注";
              }
            });
            clearInterval(timer);
            return;
          }
          aesKey = getAes();
        }, 50);
      }
    },
    addAttention(attention) {
      //获取保存在cookie的AES密钥
      let aesKey = getAes();
      //进行参数加密,必须把对象转换json字符串，不然加密不了
      let dataJson = JSON.stringify(this.attentionParam);
      //数据进行加密
      this.res.requestData = AESEncrypt(dataJson, aesKey);
      if (attention == "关注") {
        add(this.res).then((res) => {
          if (res.success) {
            this.getList();
          }
        });
      } else if (attention == "取消关注") {
        del(this.res).then((res) => {
          if (res.success) {
            this.getList();
          }
        });
      }
    },
  },
};
</script>
<style lang="stylus" scoped rel="stylesheet/stylus">
@import '../../common/stylus/index.styl';

.ivu-card-bordered {
  border: none;
  border-left: 1px solid #eee;
}

.ivu-card:hover {
  box-shadow: none;
}

.recommend {
  margin: 20px 0px;
}

.point {
  text-align: left;
  margin-top: 40px;
  background: #5fb878;
  color: #fff;

  p {
    padding: 5px 0px 0px 10px;
  }
}
</style>
