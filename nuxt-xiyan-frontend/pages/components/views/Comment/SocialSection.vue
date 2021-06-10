<template>
  <div class="social-section">
    <div class="share" style="text-align: center; margin-top: 80px">
      <share></share>
    </div>
    <div v-if="type == '2'" style="text-align: center; margin-top: 40px">
      <Button
        icon="md-arrow-round-down"
        @click="download"
        type="success"
        size="large"
        >下载源码</Button
      >
      <Button
        @click="$router.push('/recharge')"
        icon="logo-usd"
        type="warning"
        size="large"
        style="margin-left: 80px"
        >金币充值</Button
      >
    </div>
    <div class="likes">
      <vue-star-plus v-model="active" class="i-star__component">
        <a class="i-star__picture thumbs" slot="icon" @click="thumbs">
          <img
            :src="
              require(active
                ? '@/assets/images/likes-wirte.svg'
                : '@/assets/images/likes.svg')
            "
          />
        </a>
      </vue-star-plus>
    </div>
    <div class="comment">
      <!-- client-only只允许客户端使用  nuxt-->
      <client-only>
        <comment
          avatar="https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3987769337,1868985049&fm=26&gp=0.jpg"
          commentWidth="100%"
          placeholder="想说点什么呢"
          :commentNum="commentNum"
          :commentList="commentList"
          label="评论者"
          @doSend="doSend"
          @doChidSend="doChidSend"
        ></comment>
      </client-only>
    </div>
  </div>
</template>

<script>
import Vue from "vue";
//引入星星点赞特性组件
import VueStarPlus from "vue-star-plus";
import "vue-star-plus/lib/vue-star-plus.css";
//评论
import comment from "hbl-comment";
import { AESEncrypt } from "@/assets/js/aes";

import { MessageBox } from "element-ui";
export default {
  components: {
    VueStarPlus,
    comment,
  },
  props: {
    type: String,
    gold: Number,
  },
  data() {
    return {
      active: false,
      userInfo: {},
      commentNum: 0,
      commentList: [],
      commentParam: {
        id: "",
        type: "",
      },
      res: {
        requestData: "",
      },
      codeDownload: {
        id: "",
      },
      addParam: {
        infoId: "",
        targetUserId: "",
        content: "",
        type: "",
      },
    };
  },
  inject: ["reload"],
  mounted() {
    if (this.$cookies.get("user") != undefined) {
      this.userInfo = this.$cookies.get("user");
    }
    let id = this.$route.query.id;
    this.commentParam.id = id;
    this.commentParam.type = this.type;
    this.codeDownload.id = id;
    this.getList();
  },
  methods: {
    async down(reqParam) {
      let res = await this.$axios.post("/web/code/download", reqParam, {
        headers: { "Content-Type": "application/x-www-form-urlencoded" },
      });
      return res;
    },
    //下载
    download() {
      MessageBox.confirm("您确定要花" + this.gold + "金币下载么？", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          //获取保存在cookie的AES密钥
          let aesKey = this.$cookies.get("key");
          //进行参数加密,必须把对象转换json字符串，不然加密不了
          let dataJson = JSON.stringify(this.codeDownload);
          //数据进行加密
          this.res.requestData = AESEncrypt(dataJson, aesKey);
          //由于这个方法不能使用await 所以调用外部
          this.down(this.res).then((res) => {
            if (res.success) {
              this.userInfo.point = res.point;
              this.$cookies.set("user", this.userInfo);
              window.location.href = res.url;
              this.reload();
            } else {
              this.$Message.error({
                background: true,
                content: res.errorMsg,
              });
            }
          });
        })
        .catch(() => {});
    },
    async doChidSend(content, bid, pid) {
      if (content == undefined || content == "") {
        this.$Message.warning("评论内容不能为空");
        return;
      }
      this.addParam.infoId = this.commentParam.id;
      this.addParam.type = this.commentParam.type;
      this.addParam.content = content;
      this.addParam.targetUserId = bid;
      //获取保存在cookie的AES密钥
      let aesKey = this.$cookies.get("key");
      //进行参数加密,必须把对象转换json字符串，不然加密不了
      let dataJson = JSON.stringify(this.addParam);
      //数据进行加密
      this.res.requestData = AESEncrypt(dataJson, aesKey);
      let res = await this.$axios.post("/web/comment/add", this.res, {
        headers: { "Content-Type": "application/x-www-form-urlencoded" },
      });
      if (res.success) {
        this.getList();
      }
    },
    async doSend(content) {
      if (content == undefined || content == "") {
        this.$Message.warning("评论内容不能为空");
        return;
      }
      this.addParam.infoId = this.commentParam.id;
      this.addParam.type = this.commentParam.type;
      this.addParam.content = content;
      //获取保存在cookie的AES密钥
      let aesKey = this.$cookies.get("key");
      //进行参数加密,必须把对象转换json字符串，不然加密不了
      let dataJson = JSON.stringify(this.addParam);
      //数据进行加密
      this.res.requestData = AESEncrypt(dataJson, aesKey);
      let res = await this.$axios.post("/web/comment/add", this.res, {
        headers: { "Content-Type": "application/x-www-form-urlencoded" },
      });
      if (res.success) {
        this.getList();
      }
    },
    //点赞
    async thumbs() {
      if (this.type == "1") {
        await this.$axios.get("/web/article/like/" + this.commentParam.id, {
          headers: { "Content-Type": "application/x-www-form-urlencoded" },
        });
      } else if (this.type == "2") {
        await this.$axios.get("/web/code/like/" + this.commentParam.id, {
          headers: { "Content-Type": "application/x-www-form-urlencoded" },
        });
      }
      this.active = true;
      $(".likes").css("pointer-events", "none");
    },
    async getList() {
      //获取保存在cookie的AES密钥
      let aesKey = this.$cookies.get("key");
      //进行参数加密,必须把对象转换json字符串，不然加密不了
      let dataJson = JSON.stringify(this.commentParam);
      //数据进行加密
      this.res.requestData = AESEncrypt(dataJson, aesKey);
      let res = await this.$axios.post("/web/comment/list", this.res, {
        headers: { "Content-Type": "application/x-www-form-urlencoded" },
      });
      if (res.success) {
        this.commentList = res.voList;
        //日期格式化
        this.commentList.forEach((item) => {
          let date = new Date(item.mountedate);
          let y = date.getFullYear();
          let MM = date.getMonth() + 1;
          MM = MM < 10 ? "0" + MM : MM;
          let d = date.getDate();
          d = d < 10 ? "0" + d : d;
          let h = date.getHours();
          h = h < 10 ? "0" + h : h;
          let m = date.getMinutes();
          m = m < 10 ? "0" + m : m;
          let s = date.getSeconds();
          s = s < 10 ? "0" + s : s;
          item.mountedate =
            y + "-" + MM + "-" + d + " " + h + ":" + m + ":" + s;
        });
        this.commentNum = res.total;
      }
    },
  },
};
</script>
<style lang="stylus" rel="stylesheet/stylus" scoped>
.likes {
  margin-top: 80px !important;
  display: flex !important;
  align-items: center !important;
  justify-content: center !important;
}

@media only screen and (max-width: 768px) {
  /deep/.hbl-fa .hbl-comm {
    padding: 40px 0px 20px 0px !important;
  }
}
</style>
