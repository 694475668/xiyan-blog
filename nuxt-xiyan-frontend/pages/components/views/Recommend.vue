<template>
  <div class="recommend">
    <panel :title="'推荐阅读'">
      <div slot="content" class="content">
        <div class="top" v-for="recommend in recommendList" :key="recommend.id">
          <nuxt-link
            :to="{ name: 'article', params: { articleId: recommend.id } }"
          >
            <p class="title">{{ recommend.title }}</p>
            <div class="tags">
              <Tag
                v-for="(item, index) in recommend.tagList"
                :key="index"
                :color="index | mapTagColor"
                >{{ item }}</Tag
              >
            </div>
            <p class="info">
              <span class="time">{{ recommend.createTime | socialDate }}</span>
              <span
                class="iconfont icon-dianzan"
                style="margin-left: 5px; color: #d81e06"
                ><b style="margin-left: 5px">{{ recommend.starCount }}</b></span
              >
              <span
                style="margin-left: 5px; color: #3cc2a8"
                class="iconfont icon-pinglun_huabanfuben"
                ><b style="margin-left: 5px">{{ recommend.conCount }}</b></span
              >
              <span class="iconfont icon-liulan" style="color: #d4237a"
                ><b style="margin-left: 5px">{{ recommend.readCount }}</b></span
              >
            </p>
          </nuxt-link>
        </div>
      </div>
    </panel>
  </div>
</template>

<script>
import Panel from "@/pages/components/utils/Panel";
import { mixin } from "@/utils/index";
import { AESEncrypt } from "@/assets/js/aes";
export default {
  mixins: [mixin],
  data() {
    return {
      //加密后请求服务器的参数
      res: {
        requestData: "",
      },
      recommendList: [],
      param: {
        pageNo: 1,
        pageSize: 4,
        //默认是以创建时间倒叙排序
        sortField: "create_time",
        type: "",
      },
    };
  },
  filters: {
    socialDate(value) {
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
  mounted() {
    this.getList();
    //开启粒子特效
    if (document.getElementById("particles-js") != null) {
      document.getElementById("particles-js").style.display = "block";
    }
  },
  methods: {
    async getList() {
      //获取保存在cookie的AES密钥
      let aesKey = this.$cookies.get("key");
      //进行参数加密,必须把对象转换json字符串，不然加密不了
      let dataJson = JSON.stringify(this.param);
      //数据进行加密
      this.res.requestData = AESEncrypt(dataJson, aesKey);
      let res = await this.$axios.post("/web/article/list", this.res, {
        headers: { "Content-Type": "application/x-www-form-urlencoded" },
      });
      this.recommendList = res.voList;
    },
  },
  components: {
    panel: Panel,
  },
};
</script>
<style lang="stylus" scoped rel="stylesheet/stylus">
@import '~/assets/stylus/index.styl';

b {
  font-weight: inherit !important;
}

.recommend {
  .content {
    padding: 5px 20px;
  }

  .top, .others {
    a {
      display: block;
      overflow: hidden;

      .tags {
        margin-bottom: 10px;
      }

      .title {
        text-align: justify;
        color: $color-gradually-gray-41;
        font-size: 16px;
        line-height: 23px;
        margin-bottom: 5px;
      }

      .info {
        margin: 5px 0 0px;

        span {
          font-size: 13px;
          line-height: 18px;
          font-weight: 100;
          color: $color-secondary-info;

          + span {
            float: right;
            margin-left: 10px;
          }
        }

        a {
          display: inline-block;
          color: #777;
          cursor: pointer;

          &:hover {
            color: $color-main-primary;
            text-decoration: underline;
          }
        }
      }

      .img {
        padding-bottom: 40%;
        width: 100%;
        height: 0;
        margin: 5px 0;
        overflow: hidden;

        img {
          width: 100%;
          transition: All 0.4s ease-in-out;
          transform: scale(1);
          zoom: 1;
        }
      }

      .desc {
        text-align: justify;
        color: $color-secondary-info;
        font-size: 13px;
        line-height: 20px;
        margin: 5px 0 0;
      }

      &:hover {
        .title {
          color: $color-main-primary;
        }

        img {
          transition: All 0.4s ease-in-out;
          transform: scale(1.05);
          zoom: 1.05;
        }
      }
    }
  }

  .others {
    li {
      list-style-type: none;
      margin-top: 10px;
      padding-top: 10px;
      border-top: 1px solid $color-border;
    }
  }
}
</style>
