<template>
  <div class="article-page-header">
    <p class="title">{{ article.title }}</p>
    <Row
      style="display: flex; justify-content: center; align-items: flex-start"
    >
      <Col
        :xs="24"
        :sm="10"
        :md="10"
        :lg="10"
        style="padding-left: 0; padding-right: 0"
      >
        <p class="info">
          <span class="author"
            ><Icon type="ios-contact" class="icon" /><a href="">{{
              article.name
            }}</a></span
          >
          <span class="publish-time">
            <Icon type="ios-timer-outline" class="icon" />{{
              article.createTime | formatDate
            }}</span
          >
        </p>
      </Col>
      <Col
        :xs="24"
        :sm="14"
        :md="14"
        :lg="14"
        style="padding-left: 0; padding-right: 0"
      >
        <p class="operate_info">
          <span class="iconfont icon-liulan" style="color: #d4237a"
            ><b style="margin-left: 5px">{{ article.readCount }}</b></span
          >
          <span
            class="iconfont icon-dianzan"
            style="margin-left: 5px; color: #d81e06"
            ><b style="margin-left: 5px">{{ article.starCount }}</b></span
          >
          <Tag
            v-if="userInfo.username != article.username"
            style="cursor: pointer"
            :color="favorites == '收藏' ? 'success' : 'warning'"
            @click.native="addFavorites(favorites)"
            >{{ favorites }}</Tag
          >
          <span
            @click="update(article.id)"
            v-if="userInfo.username === article.username"
            ><a>编辑</a></span
          >
        </p>
      </Col>
    </Row>
  </div>
</template>

<script>
import { mixin } from "@/utils";
import { getUserInfo } from "@/utils/auth";
import { del, query, insert } from "@/api/favorites";
import { getAes } from "@/utils/auth";
import { AESEncrypt } from "@/api/aes";
export default {
  data() {
    return {
      userInfo: {},
      favorites: "收藏",
      favoritesParam: {
        infoId: "",
        type: "1",
      },
      //加密后请求服务器的参数
      res: {
        requestData: "",
      },
    };
  },
  props: {
    article: {},
  },
  mixins: [mixin],
  methods: {
    addFavorites(item) {
      //获取保存在cookie的AES密钥
      let aesKey = getAes();
      //进行参数加密,必须把对象转换json字符串，不然加密不了
      let dataJson = JSON.stringify(this.favoritesParam);
      //数据进行加密
      this.res.requestData = AESEncrypt(dataJson, aesKey);
      if (item == "收藏") {
        insert(this.res).then((res) => {
          if (res.success) {
            this.getList();
          }
        });
      } else if (item == "取消收藏") {
        del(this.res).then((res) => {
          if (res.success) {
            this.getList();
          }
        });
      }
    },
    getList() {
      if (getUserInfo() != undefined) {
        //获取保存在cookie的AES密钥
        let aesKey = getAes();
        //为了处理aes还没有写入cookie就调接口了
        const timer = setInterval(() => {
          if (aesKey != undefined) {
            //进行参数加密,必须把对象转换json字符串，不然加密不了
            let dataJson = JSON.stringify(this.favoritesParam);
            //数据进行加密
            this.res.requestData = AESEncrypt(dataJson, aesKey);
            query(this.res).then((res) => {
              if (res.errorCode == "E0708") {
                this.favorites = "取消收藏";
              } else {
                this.favorites = "收藏";
              }
            });
            clearInterval(timer);
            return;
          }
          aesKey = getAes();
        }, 50);
      }
    },
    update(id) {
      this.$router.push({ name: "postArticle", query: { id: id } });
    },
  },
  mounted() {
    if (getUserInfo() != undefined) {
      let id = this.$route.query.id;
      this.favoritesParam.infoId = id;
      this.userInfo = JSON.parse(getUserInfo());
      this.getList();
    }
  },
  filters: {
    formatDate: function (value) {
      if (value != null) {
        let date = new Date(value);
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
        return y + "-" + MM + "-" + d + " " + h + ":" + m + ":" + s;
      }
    },
  },
};
</script>

<style lang="stylus" scoped rel="stylesheet/stylus">
@import '../../../common/stylus/index.styl';

.article-page-header {
  text-align: left;
  padding: 25px 5px 10px 5px;

  @media only screen and (max-width: 768px) {
    padding-top: 10px;

    .ivu-row {
      flex-direction: column;
    }
  }

  @media screen and (min-width: 768px) {
    padding-top: 10px;
  }

  @media screen and (min-width: 992px) {
    padding-top: 25px;
  }

  .title {
    font-size: 27px;
    line-height: 33px;
    font-weight: 500;
    color: $color-typegraphy-title;
    margin-bottom: 23px;
    text-align: center;
  }

  .info {
    margin-top: 10px;
    font-size: 14px;
    line-height: 18px;
    font-weight: 200;

    a {
      color: #777;

      &:hover {
        color: $color-main-primary;
        text-decoration: underline;
      }
    }

    .publish-time {
      margin-left: 20px;
    }
  }

  .operate_info {
    text-align: right;
    font-size: 14px;
    margin: 15px 0;

    @media only screen and (max-width: 768px) {
      text-align: left;
    }

    span {
      margin-right: 10px;

      + span {
        margin-left: 10px;
      }
    }
  }
}

.icon {
  margin-right: 5px;
}
</style>
