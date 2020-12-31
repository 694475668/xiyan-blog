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
    </div>
  </div>
</template>

<script>
//引入星星点赞特性组件
import VueStarPlus from "vue-star-plus";
import "vue-star-plus/lib/vue-star-plus.css";
import { getUserInfo, setUserInfo } from "@/utils/auth";
//评论
import comment from "hbl-comment";
import { getAes } from "@/utils/auth";
import { AESEncrypt } from "@/api/aes";
import { list, add } from "@/api/comment";
import { like } from "@/api/blog";
import { codeLike, download } from "@/api/code";
//分享插件
import Share from "vue-social-share";
import "vue-social-share/dist/client.css";

import { MessageBox } from "element-ui";
export default {
  components: {
    VueStarPlus,
    comment,
    Share,
  },
  props: {
    type: String,
    gold: Number,
  },
  inject: ["reload"],
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
  mounted() {
    if (getUserInfo() != undefined) {
      this.userInfo = JSON.parse(getUserInfo());
    }
    let id = this.$route.query.id;
    this.commentParam.id = id;
    this.commentParam.type = this.type;
    this.codeDownload.id = id;
    this.getList();
  },
  methods: {
    //下载
    download() {
      MessageBox.confirm("您确定要花" + this.gold + "金币下载么？", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          //获取保存在cookie的AES密钥
          let aesKey = getAes();
          //进行参数加密,必须把对象转换json字符串，不然加密不了
          let dataJson = JSON.stringify(this.codeDownload);
          //数据进行加密
          this.res.requestData = AESEncrypt(dataJson, aesKey);
          download(this.res).then((res) => {
            if (res.success) {
              this.userInfo.point = res.point;
              setUserInfo(this.userInfo);
              location.href = res.url;
              this.reload();
            }
          });
        })
        .catch(() => {});
    },
    doChidSend(content, bid, pid) {
      if (content == undefined || content == "") {
        this.$Message.warning("评论内容不能为空");
        return;
      }
      this.addParam.infoId = this.commentParam.id;
      this.addParam.type = this.commentParam.type;
      this.addParam.content = content;
      this.addParam.targetUserId = bid;
      //获取保存在cookie的AES密钥
      let aesKey = getAes();
      //进行参数加密,必须把对象转换json字符串，不然加密不了
      let dataJson = JSON.stringify(this.addParam);
      //数据进行加密
      this.res.requestData = AESEncrypt(dataJson, aesKey);
      add(this.res).then((res) => {
        if (res.success) {
          this.getList();
        }
      });
    },
    doSend(content) {
      if (content == undefined || content == "") {
        this.$Message.warning("评论内容不能为空");
        return;
      }
      this.addParam.infoId = this.commentParam.id;
      this.addParam.type = this.commentParam.type;
      this.addParam.content = content;
      //获取保存在cookie的AES密钥
      let aesKey = getAes();
      //进行参数加密,必须把对象转换json字符串，不然加密不了
      let dataJson = JSON.stringify(this.addParam);
      //数据进行加密
      this.res.requestData = AESEncrypt(dataJson, aesKey);
      add(this.res).then((res) => {
        if (res.success) {
          this.getList();
        }
      });
    },
    //点赞
    thumbs() {
      if (this.type == "1") {
        like(this.commentParam.id).then((res) => {});
      } else if (this.type == "2") {
        codeLike(this.commentParam.id).then((res) => {});
      }
      this.active = false;
      $(".likes").css("pointer-events", "none");
    },
    getList() {
      //获取保存在cookie的AES密钥
      let aesKey = getAes();
      //为了处理aes还没有写入cookie就调接口了
      const timer = setInterval(() => {
        if (aesKey != undefined) {
          //进行参数加密,必须把对象转换json字符串，不然加密不了
          let dataJson = JSON.stringify(this.commentParam);
          //数据进行加密
          this.res.requestData = AESEncrypt(dataJson, aesKey);
          list(this.res).then((res) => {
            this.commentList = res.voList;
            //日期格式化
            this.commentList.forEach((item) => {
              let date = new Date(item.createDate);
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
              item.createDate =
                y + "-" + MM + "-" + d + " " + h + ":" + m + ":" + s;
            });
            this.commentNum = res.total;
          });
          clearInterval(timer);
          return;
        }
        aesKey = getAes();
      }, 50);
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
