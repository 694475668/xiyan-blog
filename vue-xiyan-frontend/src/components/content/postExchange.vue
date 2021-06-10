<template>
  <div class="bookNote-list-content">
    <Row>
      <Col :xs="24" :sm="24" :md="24" :lg="17">
        <Form ref="param" :model="param" :label-width="labelWidth">
          <input v-model="param.id" name="id" type="hidden" />
          <FormItem label="标题：">
            <Input
              placeholder="请填写标题..."
              :maxlength="30"
              v-model="param.title"
              style="width: 300px"
              size="large"
            ></Input>
          </FormItem>
          <FormItem label="类型：">
            <RadioGroup v-model="param.type">
              <Radio
                :label="item.value"
                v-for="(item, index) in dictionaryList"
                :key="index"
              >
                <Icon type="logo-octocat" v-if="index == 0"></Icon>
                <Icon type="ios-megaphone" v-if="index == 1"></Icon>
                <Icon type="ios-leaf" v-if="index == 2"></Icon>
                <span>{{ item.meaning }}</span>
              </Radio>
            </RadioGroup>
          </FormItem>
          <FormItem label="文章内容：">
            <editor-wang
              :value="wangEditorDefault"
              @change="changeWang"
            ></editor-wang>
          </FormItem>
          <FormItem>
            <Button v-if="param.id == undefined" type="error" @click="save"
              >发表说说</Button
            >
            <Button v-if="param.id != undefined" type="error" @click="update"
              >修改说说</Button
            >
          </FormItem>
        </Form>
      </Col>
    </Row>
  </div>
</template>

<script>
import { add, getTalkById, update } from "@/api/talk";
import { mixin } from "@/utils";
import { getAes } from "@/utils/auth";
import { AESEncrypt } from "@/api/aes";
import editorWang from "@/components/wangEditor";
import { queryDictionaryByName } from "@/api/dictionary";
export default {
  components: { editorWang },
  mixins: [mixin],
  data: function () {
    return {
      labelWidth: 200,
      //加密后请求服务器的参数
      res: {
        requestData: "",
      },
      dictionaryList: [],
      wangEditorDefault: "",
      param: {
        id: "",
        content: "",
        type: "1",
      },
      dictionaryParam: {
        name: "TALK-TYPE",
      },
    };
  },
  created() {
    if (document.body.offsetWidth <= 678) {
      this.labelWidth = 100;
    }
    let id = this.$route.query.id;
    this.param.id = id;
    if (id != undefined) {
      this.getTalkById();
    }
    this.queryDictionaryByName();
  },
  methods: {
    queryDictionaryByName() {
      //获取保存在cookie的AES密钥
      let aesKey = getAes();
      //进行参数加密,必须把对象转换json字符串，不然加密不了
      let dataJson = JSON.stringify(this.dictionaryParam);
      //数据进行加密
      this.res.requestData = AESEncrypt(dataJson, aesKey);
      queryDictionaryByName(this.res).then((res) => {
        this.dictionaryList = res.voList;
        console.log(this.dictionaryList);
        this.getList();
      });
    },
    update() {
      if (this.param.title === null || this.param.title === "") {
        this.$Message.warning("文章标题不能为空");
        return;
      } else if (this.param.content === null || this.param.content === "") {
        this.$Message.error("文章内容不能为空");
        return;
      }
      //获取保存在cookie的AES密钥
      let aesKey = getAes();
      //进行参数加密,必须把对象转换json字符串，不然加密不了
      let dataJson = JSON.stringify(this.param);
      //数据进行加密
      this.res.requestData = AESEncrypt(dataJson, aesKey);
      update(this.res, this.param.id).then((res) => {
        if (res.success) {
          this.$Message.success({
            content: "修改成功",
            duration: 5,
          });
          this.$router.push("/talk");
        }
      });
    },
    getTalkById(id) {
      getTalkById(this.param.id).then((res) => {
        this.param.title = res.title;
        this.param.type = res.type;
        this.param.content = res.content;
      });
    },
    //wangeditor 值改变的时候
    changeWang(html) {
      this.param.content = html;
    },

    // 保存
    save() {
      if (this.param.title === null || this.param.title === "") {
        this.$Message.warning("文章标题不能为空");
        return;
      } else if (this.param.content === null || this.param.content === "") {
        this.$Message.error("文章内容不能为空");
        return;
      }
      //获取保存在cookie的AES密钥
      let aesKey = getAes();
      //进行参数加密,必须把对象转换json字符串，不然加密不了
      let dataJson = JSON.stringify(this.param);
      //数据进行加密
      this.res.requestData = AESEncrypt(dataJson, aesKey);
      add(this.res).then((res) => {
        if (res.success) {
          this.$Message.success({
            content: "发表成功",
            duration: 5,
          });
          this.$router.push("/talk");
        }
      });
    },
  },
};
</script>

<style lang="stylus" scoped rel="stylesheet/stylus">
.ivu-col-span-lg-17 {
  width: 100% !important;
  margin-top: 50px !important;
}

.bookNote-list-content {
  min-height: calc(100vh - 108px);
  width: 100%;

  @media only screen and (max-width: 768px) {
    .ivu-col-span-lg-17 {
      margin-top: 20px !important;
    }

    .ivu-input-wrapper {
      width: 100% !important;
    }

    .v-note-wrapper {
      min-width: 0px;
    }
  }

  @media screen and (min-width: 768px) {
    margin: 10px 10px 0 10px;
  }

  @media screen and (min-width: 992px) {
    margin: 15px 35px 0 35px;
  }

  @media screen and (min-width: 1200px) {
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
