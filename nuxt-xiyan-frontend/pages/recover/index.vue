<template>
  <div class="home-content">
    <Row>
      <Col :xs="500" :sm="500" :md="500" :lg="500">
        <div class="dev-sign-main ivu-card ivu-card-dis-hover ivu-card-shadow">
          <!---->
          <!---->
          <div class="ivu-card-body">
            <Form
              autocomplete="off"
              class="ivu-form ivu-form-label-top"
              ref="registForm"
              :model="form"
              :rules="rules"
              @keydown.enter.native="handleSubmit"
            >
              <div
                class="ivu-form-item ivu-form-item-required ivu-form-item-error"
              >
                <label class="ivu-form-item-label">电子邮箱</label>
                <FormItem prop="username"
                  ><!---->
                  <!---->
                  <Input
                    type="text"
                    placeholder="请填写你的电子邮箱"
                    v-model="form.username"
                  >
                    <Button
                      style="height: 30px"
                      :disabled="disabled"
                      slot="append"
                      @click="ToEmail"
                    >
                      <div
                        v-if="show"
                        class="iconfont icon-youxiang3"
                        style="margin-top: -10px; color: #d81e06"
                      ></div>
                      <span v-else style="color: #ed4014">{{ send }}</span>
                    </Button>
                  </Input>
                </FormItem>
              </div>
              <div
                class="ivu-form-item ivu-form-item-required ivu-form-item-error"
              >
                <label class="ivu-form-item-label">新密码</label>
                <FormItem prop="password"
                  ><!---->
                  <Input
                    autocomplete="off"
                    password
                    type="password"
                    v-model="form.password"
                    placeholder="请输入密码"
                  />
                </FormItem>
              </div>
              <div
                class="ivu-form-item ivu-form-item-required ivu-form-item-error"
              >
                <label class="ivu-form-item-label">验证码</label>
                <FormItem prop="code">
                  <Input
                    autocomplete="off"
                    v-model="form.code"
                    placeholder="请输入验证码"
                    style="width: 150px"
                    maxlength="6"
                  />
                  <a
                    @click="ToEmail"
                    :disabled="disabled"
                    style="margin-left: 15px"
                  >
                    没有收到验证码？再来一条</a
                  >
                </FormItem>
              </div>
            </Form>
            <div class="dev-sign-main-aside">
              <Button
                class="ivu-btn ivu-btn-success ivu-btn-long ivu-btn-large"
                @click="rePassword"
              >
                <i class="ivu-icon ivu-icon-md-log-in"></i>
                <span>找回密码</span>
              </Button>
              <span class="ivu-input-prefix">
                <i class="ivu-icon ivu-icon-ios-mail-outline"></i
              ></span>
              <div class="dev-sign-main-aside-tip">
                <p style="margin-top: 20px">
                  还没有账户？
                  <nuxt-link to="/regist" class="">注册</nuxt-link>
                </p>
                <p style="margin-top: 5px">
                  已有有账户？
                  <nuxt-link to="/login">登录</nuxt-link>
                </p>
              </div>
              <!-- 拖动验证码 -->
              <Modal footer-hide v-model="modal" width="400"
                ><slide-verify
                  :l="42"
                  :r="10"
                  :w="310"
                  :h="155"
                  slider-text="向右滑动"
                  @success="onSuccess"
                ></slide-verify>
              </Modal>
            </div>
          </div>
        </div>
      </Col>
    </Row>
  </div>
</template>

<script>
import { mixin } from "@/utils";
import { AESEncrypt } from "@/assets/js/aes";

export default {
  head() {
    return {
      title: "密码找回",
      meta: [
        {
          name: "keywords",
          content:
            "Java毕设,Java项目,Java源码,Vue博客,代码,教程,web编程,前端开发,后端开发",
        },
        {
          name: "description",
          content:
            "Java毕设,Java项目,Java源码,Vue博客,代码,教程,web编程,前端开发,后端开发",
        },
      ],
    };
  },
  name: "registForm",
  props: {
    userNameRules: {
      type: Array,
      default: () => {
        return [{ required: true, message: "账号不能为空", trigger: "blur" }];
      },
    },
    passwordRules: {
      type: Array,
      default: () => {
        return [{ required: true, message: "新密码不能为空", trigger: "blur" }];
      },
    },
    codeRules: {
      type: Array,
      default: () => {
        return [{ required: true, message: "验证码不能为空", trigger: "blur" }];
      },
    },
  },
  data() {
    return {
      show: true,
      send: "",
      timer: "",
      modal: false,
      disabled: false,
      //加密后请求服务器的参数
      res: {
        requestData: "",
      },
      form: {
        username: "",
        password: "",
        code: "",
      },
    };
  },
  mounted() {
    //初始页一打开就定位到顶部
    document.documentElement.scrollTop = 0;
  },
  mixins: [mixin],
  computed: {
    rules() {
      return {
        username: this.userNameRules,
        password: this.passwordRules,
        code: this.codeRules,
      };
    },
  },
  methods: {
    async onSuccess() {
      //获取保存在cookie的AES密钥
      let aesKey = this.$cookies.get("key");
      //进行参数加密,必须把对象转换json字符串，不然加密不了
      let dataJson = JSON.stringify(this.form);
      //数据进行加密
      this.res.requestData = AESEncrypt(dataJson, aesKey);
      let res = await this.$axios.post("/auth/user/send", this.res, {
        headers: { "Content-Type": "application/x-www-form-urlencoded" },
      });
      if (res.success) {
        this.$Message.success({
          content: "验证码发送成功，三分钟之内有效",
          duration: 3,
          top: 50,
          backgroun: true,
        });
        this.modal = false;
        //倒计时60秒
        const TIME_COUNT = 60;
        if (!this.timer) {
          this.send = TIME_COUNT;
          this.timer = setInterval(() => {
            if (this.send > 0 && this.send <= TIME_COUNT) {
              this.send--;
              this.show = false;
              this.disabled = true;
            } else {
              this.show = true;
              clearInterval(this.timer);
              this.timer = null;
              this.disabled = false;
            }
          }, 1000);
        }
      }
    },
    ToEmail() {
      // alert("请注意去你的邮箱查收验证码哦");
      if (this.form.username === null || this.form.username === "") {
        this.$Message.warning({
          content: "请填写你的邮箱哦！",
          duration: 3,
          top: 50,
          backgroun: true,
        });
        return;
      }
      var re = /[^`~!@#$%\^&\var*\(\)\+=\|\{\}\':;\',\\\[\]<>\/\?~！@#￥%……&\*（）——+\|\{\}【】‘；：”“’。，、？\s]{1,}@[^`~!@#$%\^&\*\(\)\+=\|\{\}\':;\',\\\[\]\.<>\/\?~！@#￥%……&\*（）——+\|\{\}【】‘；：”“’。，、？\s]{1,}\.[^`~!@#$%\^&\*\(\)\+=\|\{\}\':;\',\\\[\]<>\/\?~！@#￥%……&\*（）——+\|\{\}【】‘；：”“’。，、？\s]{1,}/i;
      if (!re.test(this.form.username)) {
        this.$Message.warning({
          content: "请填写正确邮箱哦！",
          duration: 3,
          top: 50,
          backgroun: true,
        });
        return;
      }
      this.modal = true;
    },
    async rePassword() {
      // alert("请注意去你的邮箱查收验证码哦");
      if (this.form.username === null || this.form.username === "") {
        this.$Message.error("请输入你的邮箱哦！");
        return;
      }
      if (this.form.password === null || this.form.password === "") {
        this.$Message.error("请输入你的密码哦！");
        return;
      }
      if (this.form.code === null || this.form.code === "") {
        this.$Message.error("请输入验证码哦！");
        return;
      }
      var re = /[^`~!@#$%\^&\var*\(\)\+=\|\{\}\':;\',\\\[\]<>\/\?~！@#￥%……&\*（）——+\|\{\}【】‘；：”“’。，、？\s]{1,}@[^`~!@#$%\^&\*\(\)\+=\|\{\}\':;\',\\\[\]\.<>\/\?~！@#￥%……&\*（）——+\|\{\}【】‘；：”“’。，、？\s]{1,}\.[^`~!@#$%\^&\*\(\)\+=\|\{\}\':;\',\\\[\]<>\/\?~！@#￥%……&\*（）——+\|\{\}【】‘；：”“’。，、？\s]{1,}/i;
      if (!re.test(this.form.username)) {
        this.$Message.warning("请填写正确邮箱哦！");
        return;
      }
      //获取保存在cookie的AES密钥
      let aesKey = this.$cookies.get("key");
      //进行参数加密,必须把对象转换json字符串，不然加密不了
      let dataJson = JSON.stringify(this.form);
      //数据进行加密
      this.res.requestData = AESEncrypt(dataJson, aesKey);
      let res = await this.$axios.put("/auth/user/retrieve", this.res, {
        headers: { "Content-Type": "application/x-www-form-urlencoded" },
      });
      if (res.success) {
        this.$Message.warning({
          content: "密码修改成功",
          duration: 3,
          top: 50,
          backgroun: true,
        });
        this.$router.push({ path: "/login" });
      } else {
        this.$Message.error({
          background: true,
          content: res.errorMsg,
        });
      }
    },

    handleSubmit() {
      this.$refs.registForm.validate((valid) => {
        if (valid) {
          this.$emit("on-success-valid", {
            username: this.form.username,
            password: this.form.password,
          });
        }
      });
    },
  },
};
</script>
<style scoped lang="stylus">
.ivu-form .ivu-form-item-label {
  font-size: 14px;
}

.ivu-col {
  width: 100%;
}

a {
  color: #2d8cf0;
}

.ivu-form-item-content a {
  font-size: 13px;
}

>>>.ivu-modal-content {
  display: flex !important;
  justify-content: center !important;
  align-items: center !important;
}
</style>
<style lang="stylus" scope type="text/stylus" rel="stylesheet/stylus">
@import '~/assets/stylus/theme.styl';
@import '~/assets/stylus/article.styl';

.home-content {
  width: auto;
  min-height: calc(100vh - 108px);

  @media only screen and (max-width: 768px) {
    margin: 5px 5px 0 5px;
  }

  @media only screen and (max-width: 768px) {
    margin: 5px 5px 0 5px;

    .dev-sign-main {
      width: 100%;
      margin: 60px auto;
      padding: 0 16px;
    }
  }

  @media screen and (min-width: 992px) {
    margin: 15px 35px 0 35px;
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

.dev-sign-main {
  width: 410px;
  margin: 120px auto;
  padding: 0 16px;
}

.ivu-card-shadow {
  box-shadow: 0 1px 1px 0 rgba(0, 0, 0, 0.1);
}

.ivu-card {
  background: #FAFAFA;
  border-radius: 4px;
  font-size: 14px;
  position: relative;
  transition: all 0.2s ease-in-out;
}

article, aside, blockquote, body, button, dd, details, div, dl, dt, fieldset, figcaption, figure, footer, form, h1, h2, h3, h4, h5, h6, header, hgroup, hr, iv-input, legend, li, menu, nav, ol, p, section, td, textarea, th, ul {
  margin: 0;
  padding: 0;
}

*, :after, :before {
  box-sizing: border-box;
}

* {
  -webkit-tap-highlight-color: transparent;
}

user agent stylesheet, div {
  display: block;
}

* {
  -webkit-tap-highlight-color: transparent;
}

* {
  -webkit-tap-highlight-color: transparent;
}

* {
  -webkit-tap-highlight-color: transparent;
}

* {
  -webkit-tap-highlight-color: transparent;
}

body {
  font-family: Helvetica Neue, Helvetica, PingFang SC, Hiragino Sans GB, Microsoft YaHei, 5FAE 8 F6F 9 6C 5 9 ED1, Arial, sans-serif;
  font-size: 14px;
  line-height: 22px !important;
  color: #515a6e;
  background-color: #fff;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
}

* {
  -webkit-tap-highlight-color: transparent;
}

html {
  font-family: sans-serif;
  line-height: 1.15;
  -ms-text-size-adjust: 100%;
  -webkit-text-size-adjust: 100%;
}

* {
  -webkit-tap-highlight-color: transparent;
}

*, :after, :before {
  box-sizing: border-box;
}

*, :after, :before {
  box-sizing: border-box;
}

.lizi {
  position: absolute;
  top: 0;
  left: 0;
  height: calc(100VH - 66px);
  width: 100VW;
  z-index: -1;
}

html, body {
  width: 100%;
}
</style>
