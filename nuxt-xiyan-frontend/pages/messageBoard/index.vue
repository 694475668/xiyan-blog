<template>
  <div class="article-list-content">
    <Row>
      <Col :xs="24" :sm="24" :md="24" :lg="17">
        <div class="layout-left">
          <section-title :mainTitle="'留言板'" :subTitle="'Message'">
          </section-title>
          <div id="container" style="height: 600px; width: 100%">
            <div
              :id="'show' + index"
              :class="
                index % 11 == 0
                  ? 'item itemColor1'
                  : index % 11 == 1
                  ? 'item itemColor2'
                  : index % 11 == 2
                  ? 'item itemColor3'
                  : index % 11 == 3
                  ? 'item itemColor4'
                  : index % 11 == 4
                  ? 'item itemColor5'
                  : index % 11 == 5
                  ? 'item itemColor6'
                  : index % 11 == 6
                  ? 'item itemColor7'
                  : index % 11 == 7
                  ? 'item itemColor8'
                  : index % 11 == 8
                  ? 'item itemColor9'
                  : index % 11 == 9
                  ? 'item itemColor10'
                  : 'item itemColor11'
              "
              v-for="(item, index) in messageList"
            >
              <p>{{ item.content }}</p>
              <a href="#" @click="close(index)">关闭</a>
            </div>
          </div>
          <input
            id="input"
            type="text"
            v-model="param.content"
            @keydown.enter="keydown()"
            placeholder="留一个脚印呗...按回车发布"
          />
        </div>
      </Col>
    </Row>
  </div>
</template>

<script>
import SectionTitle from "@/pages/components/views/SectionTitle/SectionTitle";
import { AESEncrypt } from "@/assets/js/aes";
export default {
  head() {
    return {
      title: "留言板",
      meta: [
        {
          name: "keywords",
          content: "在线留言,留言,私密留言,评论",
        },
        {
          name: "description",
          content:
            "提供在线留言,留言,私密留言,评论,为广大网友的开发工作提供便利。",
        },
      ],
    };
  },
  async asyncData({ $axios }) {
    //需要里面有多个接口需要统一接收参数的话let [第一个接口返回结果，第二个接口返回结果]
    let [data1] = await Promise.all([
      $axios.get("/web/message/list").then((res) => {
        return res;
      }),
    ]);
    //需要在外面进行返回
    return {
      messageList: data1.voList,
    };
  },
  data() {
    return {
      //加密后请求服务器的参数
      res: {
        requestData: "",
      },
      messageList: [],
      param: { content: "" },
    };
  },
  mounted() {
    //初始页一打开就定位到顶部
    document.documentElement.scrollTop = 0;
    //开启粒子特效
    if (document.getElementById("particles-js") != null) {
      document.getElementById("particles-js").style.display = "block";
    }
  },
  methods: {
    async getList() {
      let res = await this.$axios.get("/web/message/list", {
        headers: { "Content-Type": "application/x-www-form-urlencoded" },
      });
      if (res.success) {
        this.messageList = res.voList;
        //初始页一打开就定位到顶部
        document.documentElement.scrollTop = 0;
      }
    },
    async keydown() {
      //获取保存在cookie的AES密钥
      let aesKey = this.$cookies.get("key");
      //进行参数加密,必须把对象转换json字符串，不然加密不了
      let dataJson = JSON.stringify(this.param);
      //数据进行加密
      this.res.requestData = AESEncrypt(dataJson, aesKey);
      let res = await this.$axios.post("/web/message/add", this.res, {
        headers: { "Content-Type": "application/x-www-form-urlencoded" },
      });
      if (res.success) {
        this.getList();
        this.param.content = "";
      }
    },
    close(index) {
      $("#show" + index).remove();
    },
  },
  components: {
    "section-title": SectionTitle,
  },
};
</script>

<style lang="stylus" scoped rel="stylesheet/stylus">
.ivu-col {
  width: auto;
}

#container {
  overflow-y: auto;
}

.item {
  width: 200px;
  height: 200px;
  line-height: 30px;
  -webkit-border-bottom-left-radius: 20px 500px;
  -webkit-border-bottom-right-radius: 500px 30px;
  -webkit-border-top-right-radius: 5px 100px;
  -moz-border-bottom-left-radius: 20px 500px;
  -moz-border-bottom-right-radius: 500px 30px;
  -moz-border-top-right-radius: 5px 100px;
  box-shadow: 0 2px 10px 1px rgba(0, 0, 0, 0.2);
  -webkit-box-shadow: 0 2px 10px 1px rgba(0, 0, 0, 0.2);
  -moz-box-shadow: 0 2px 10px 1px rgba(0, 0, 0, 0.2);
  float: left;
  margin: 5px;
}

#container p {
  height: 80px;
  margin: 30px 10px;
  overflow: hidden;
  word-wrap: break-word;
  line-height: 22px !important;
  color: #fff;
}

#container a {
  text-decoration: none;
  color: white;
  position: relative;
  left: 150px;
  bottom: 0px;
  font-size: 15px;
}

.itemColor1 {
  background: #D52BB3;
}

.itemColor2 {
  background: #3CC48D;
}

.itemColor3 {
  background: #d81e06;
}

.itemColor4 {
  background: #1296db;
}

.itemColor5 {
  background: #13227a;
}

.itemColor6 {
  background: #3cc2a8;
}

.itemColor7 {
  background: #1cedf2;
}

.itemColor8 {
  background: #6b1cf2;
}

.itemColor9 {
  background: #e71cf2;
}

.itemColor10 {
  background: #f28a1c;
}

.itemColor11 {
  background: #f4ea2a;
}

.ivu-col-span-lg-17 {
  max-width: 100%;
  flex: 0 0 100%;
}

#input {
  border: 0;
  border-radius: 5px;
  display: block;
  height: 30px;
  padding: 0 1em;
  line-height: 30px;
  width: 300px;
  margin: 85px auto;
  margin-top: 60px;
  margin-bottom: -85px;
  font-size: 20px;
  background: #f28a1c;
}

.article-list-content {
  width: auto;
  min-height: calc(100vh - 308px);

  @media only screen and (max-width: 768px) {
    margin: 5px 5px 0 5px;

    #input {
      margin-bottom: 50px;
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
