<template>
  <div>
    <el-card class="login-form-layout" id="mobile">
      <el-form
        autocomplete="on"
        :model="loginForm"
        :rules="loginRules"
        ref="loginForm"
        label-position="left"
      >
        <div style="text-align: left">
          <img
            src="@/assets/images/xiyan-logo.png"
            width="130px"
            height="65px"
          />
        </div>
        <el-form-item prop="username" class="font-style" label="用户名">
          <el-input
            name="email"
            type="text"
            v-model="loginForm.username"
            autocomplete="off"
            placeholder="请输入您的用户名"
          ></el-input>
        </el-form-item>
        <el-form-item class="font-style" label="密码" prop="password">
          <el-input
            name="password"
            :type="pwdType"
            @keyup.enter.native="handleLogin"
            v-model="loginForm.password"
            autocomplete="on"
            maxlength="16"
            placeholder="请输入您的密码"
          >
            <span slot="suffix" @click="showPwd">
              <svg-icon icon-class="eye" class="color-main"></svg-icon>
            </span>
          </el-input>
        </el-form-item>
        <el-form-item
          style="margin-top: 30px; margin-bottom: 60px; text-align: center"
        >
          <el-button
            style="
              width: 100%;
              font-size: 18px;
              font-family: PingFangSC-Semibold;
              background-color: #e60023;
              border-radius: 4px;
              color: #ffffff;
            "
            :loading="loading"
            @click.native.prevent="handleLogin"
            >登录</el-button
          >
        </el-form-item>
      </el-form>
    </el-card>
    <img :src="login_center_bg" class="login-center-layout" />
  </div>
</template>
<script>
import { setAesKey, getAes } from "@/utils/auth";
import { AESEncrypt } from "@/api/aes";
import { getAesKey } from "@/api/getAesKey";
import login_center_bg from "@/assets/images/login_center_bg.png";
export default {
  name: "login",
  //暴露reload方法
  inject: ["reload"],
  data() {
    const validateUsername = (rule, value, callback) => {
      if (value == "") {
        callback(new Error("用户名不能为空"));
      } else {
        callback();
      }
    };
    const validatePass = (rule, value, callback) => {
      if (value.length < 8 || value.length > 16) {
        callback(new Error("密码为8到16位"));
      } else {
        callback();
      }
    };
    return {
      loginForm: {
        username: "",
        password: "",
      },
      //加密后请求服务器的参数
      res: {
        requestData: "",
      },
      loginRules: {
        username: [
          { required: true, trigger: "blur", validator: validateUsername },
        ],
        password: [
          { required: true, trigger: "blur", validator: validatePass },
        ],
      },
      loading: false,
      pwdType: "password",
      login_center_bg,
    };
  },
  created() {
    //获取aes密钥
    getAesKey().then((res) => {
      //设置到cookie中
      setAesKey(res.key);
    });
  },
  methods: {
    showPwd() {
      if (this.pwdType === "password") {
        this.pwdType = "";
      } else {
        this.pwdType = "password";
      }
    },
    handleLogin() {
      this.$refs.loginForm.validate((valid) => {
        if (valid) {
          this.loading = true;
          //获取保存在cookie的AES密钥
          let aesKey = getAes();
          //进行参数加密,必须把对象转换json字符串，不然加密不了
          let dataJson = JSON.stringify(this.loginForm);
          //数据进行加密
          this.res.requestData = AESEncrypt(dataJson, aesKey);
          this.$store
            .dispatch("Login", this.res)
            .then(() => {
              this.loading = false;
              this.$router.push({ path: "/" });
            })
            .catch((error) => {
              this.loading = false;
            });
        } else {
          return false;
        }
      });
    },
  },
};
</script>
<style scoped lang="less">
/* 响应式布局 */
@media screen and (max-width: 768px) {
  .language {
    width: 160px !important;
    margin: 0 auto !important;
    margin-top: -45px !important;
  }
  .el-select-dropdown__item {
    margin-top: 16px !important;
    font-size: 21px !important;
  }
  /deep/.el-select .el-input .el-input__inner {
    font-size: 21px !important;
  }
  .login-form-layout {
    width: 500px !important;
  }
}
/deep/.el-select .el-input.is-focus .el-input__inner {
  border-color: #e60023 !important;
}
/deep/.el-select .el-input__inner:focus {
  border-color: #e60023 !important;
}
.el-select-dropdown__item.selected {
  color: #e60023 !important;
}
.el-form-item__label {
  font-size: 16px;
  color: #020202 !important;
  font-family: Arial;
}

.color-creat-account {
  color: #005588;
  font-size: 15px;
  font-weight: normal;
}

.link-presents-center {
  width: 100%;
  text-align: center;
  position: fixed;
  bottom: 30px;
  margin: 0 auto;
}

.login-form-layout {
  position: absolute;
  left: 0;
  right: 0;
  width: 440px;
  margin: 140px auto;
  background: #ffffff;
  border-radius: 6px;
  box-shadow: 0 20px 30px 0 rgba(0, 0, 0, 0.06);
}

.login-title {
  text-align: center;
}

.login-center-layout {
  background: #ffffff;
  width: auto;
  height: auto;
  max-width: 100%;
  max-height: 100%;
  margin-top: 80px;
}

.qr-layout {
  width: 3rem;
  height: 3rem;
  margin-top: 10px;
}

.copyright {
  width: 100%;
  text-align: center;
  color: #303133;
  font-weight: normal;
  position: fixed;
  bottom: 10px;
  margin: 0 auto;
}
.font-style {
  font-family: PingFangSC-Regular;
  font-size: 14px;
  color: #000000;
  line-height: 25px;
}
.language {
  width: 150px;
  margin: 0 auto;
  margin-top: -30px;
}
.el-select-dropdown__item {
  margin-top: 5px;
}
</style>

