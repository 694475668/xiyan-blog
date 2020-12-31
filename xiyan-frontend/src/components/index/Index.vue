<template>
  <div class="main-wrapper">
    <router-view name="header"></router-view>
    <router-view name="content"></router-view>
    <router-view name="footer"></router-view>
    <div class="backTop" v-show="btnFlag" @click="backTop"></div>
    <div>
      <aplayer autoplay :audio="audio" :lrcType="1" fixed />
      <div id="weather-v2-plugin-simple"></div>
    </div>
    <Modal
      v-model="modal"
      title="为了以后安全的找回密码,请绑定邮箱，下次也可以通过账号进行登陆"
    >
      <Form
        autocomplete="off"
        class="ivu-form ivu-form-label-top"
        ref="bindingFrom"
        :model="form"
        :rules="rules"
        @keydown.enter.native="handleSubmit"
      >
        <div class="ivu-form-item ivu-form-item-required ivu-form-item-error">
          <label class="ivu-form-item-label">电子邮箱</label>
          <FormItem prop="username">
            <Input
              type="text"
              placeholder="请填写你的电子邮箱"
              v-model="form.username"
            >
            </Input>
          </FormItem>
        </div>
        <div class="ivu-form-item ivu-form-item-required ivu-form-item-error">
          <label class="ivu-form-item-label">密码</label>
          <FormItem prop="password">
            <Input
              autocomplete="off"
              type="password"
              v-model="form.password"
              placeholder="请输入密码"
              password
            />
          </FormItem>
        </div>
        <div class="footer">
          <FormItem>
            <Button type="primary" @click="handleSubmit('bindingFrom')"
              >绑定</Button
            >
            <Button @click="modal = false">取消</Button>
          </FormItem>
        </div>
      </Form>
    </Modal>
  </div>
</template>

<script>
import Cookie from "js-cookie";
import { list } from "@/api/music";
import { bind } from "@/api/user";
import { getKey } from "@/api/key";
import { setAesKey, getAes } from "@/utils/auth";
import { AESEncrypt } from "@/api/aes";
export default {
  data() {
    return {
      //加密后请求服务器的参数
      res: {
        requestData: "",
      },
      btnFlag: false,
      audio: [],
      form: {
        username: "",
        password: "",
      },
      modal: false,
      rules: {
        username: [
          {
            required: true,
            message: "用户名不能为空",
            trigger: "blur",
          },
        ],
        password: [
          {
            required: true,
            message: "密码不能为空",
            trigger: "blur",
          },
        ],
      },
    };
  },
  beforeCreate() {
    if (getAes() == undefined) {
      //获取aes密钥
      getKey().then((res) => {
        //设置到cookie中
        setAesKey(res.key);
      });
    }
  },
  created() {
    if (Cookie.get("loginToken") != undefined) {
      let data = JSON.parse(Cookie.get("userInfo"));
      if (data.username == undefined || data.username == "") {
        this.modal = true;
      }
    }
    // vue的两个生命钩子，这里不多解释。
    // window对象，所有浏览器都支持window对象。它表示浏览器窗口，监听滚动事件
    window.addEventListener("scroll", this.scrollToTop);
    this.musicList();
  },

  destroyed() {
    window.removeEventListener("scroll", this.scrollToTop);
  },

  methods: {
    // 点击图片回到顶部方法，加计时器是为了过渡顺滑
    backTop() {
      const that = this;
      let timer = setInterval(() => {
        let ispeed = Math.floor(-that.scrollTop / 5);
        document.documentElement.scrollTop = document.body.scrollTop =
          that.scrollTop + ispeed;
        if (that.scrollTop === 0) {
          clearInterval(timer);
        }
      }, 16);
    },

    // 为了计算距离顶部的高度，当高度大于60显示回顶部图标，小于60则隐藏
    scrollToTop() {
      const that = this;
      let scrollTop =
        window.pageYOffset ||
        document.documentElement.scrollTop ||
        document.body.scrollTop;
      that.scrollTop = scrollTop;
      if (that.scrollTop > 0) {
        that.btnFlag = true;
      } else {
        that.btnFlag = false;
      }
    },

    //音乐列表
    musicList() {
      //获取保存在cookie的AES密钥
      let aesKey = getAes();
      //为了处理aes还没有写入cookie就调接口了
      const timer = setInterval(() => {
        if (aesKey != undefined) {
          list().then((res) => {
            this.audio = res.voList;
          });
          clearInterval(timer);
          return;
        }
        aesKey = getAes();
      }, 50);
    },
    handleSubmit(name) {
      this.$refs[name].validate((valid) => {
        if (valid) {
          var re = /[^`~!@#$%\^&\var*\(\)\+=\|\{\}\':;\',\\\[\]<>\/\?~！@#￥%……&\*（）——+\|\{\}【】‘；：”“’。，、？\s]{1,}@[^`~!@#$%\^&\*\(\)\+=\|\{\}\':;\',\\\[\]\.<>\/\?~！@#￥%……&\*（）——+\|\{\}【】‘；：”“’。，、？\s]{1,}\.[^`~!@#$%\^&\*\(\)\+=\|\{\}\':;\',\\\[\]<>\/\?~！@#￥%……&\*（）——+\|\{\}【】‘；：”“’。，、？\s]{1,}/i;
          if (!re.test(this.form.username)) {
            this.$Message.warning({
              content: "请填写正确邮箱哦！",
              duration: 3,
              top: 50,
              backgroun: true,
            });
          } else {
            //获取保存在cookie的AES密钥
            let aesKey = getAes();
            //进行参数加密,必须把对象转换json字符串，不然加密不了
            let dataJson = JSON.stringify(this.form);
            //数据进行加密
            this.res.requestData = AESEncrypt(dataJson, aesKey);
            //执行绑定接口
            bind(this.res).then((res) => {
              if (res.success) {
                this.$Message.success({
                  content: "绑定成功！",
                  duration: 3,
                  top: 50,
                  backgroun: true,
                });
                this.modal = false;
              }
            });
          }
        } else {
          this.$Message.error({
            content: "信息还没有填写完整哦!",
            duration: 3,
            top: 50,
            backgroun: true,
          });
        }
      });
    },
  },
};
</script>
<style scoped>
.ivu-modal-footer {
  display: none !important;
}
#snowbox {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100vh;
  z-index: -1;
}
.backTop {
  position: fixed;
  bottom: 20px;
  right: 60px;
  width: 23px;
  height: 23px;
  background: url("../../assets/toTop.png");
  cursor: pointer;
}
</style>
<style lang="stylus" rel="stylesheet/stylus" >
html, body {
  height: 100%;
}

.main-wrapper {
  width: 100%;
  margin: 61px auto;
}
</style>
