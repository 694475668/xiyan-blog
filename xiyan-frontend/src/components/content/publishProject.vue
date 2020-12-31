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
          <FormItem label="描述：">
            <Input
              type="textarea"
              placeholder="请填写描述..."
              v-model="param.remark"
              :maxlength="100"
              style="width: 600px"
            ></Input>
          </FormItem>
          <FormItem label="选择封面：">
            <div class="demo-upload-list" v-if="imgUrl != ''">
              <template>
                <img :src="imgUrl" />
                <div class="demo-upload-list-cover">
                  <Icon
                    type="ios-trash-outline"
                    @click.native="handleRemove"
                  ></Icon>
                </div>
              </template>
            </div>
            <Upload
              v-if="imgUrl == ''"
              ref="upload"
              :show-upload-list="false"
              :before-upload="handleBeforeUpload"
              paste
              type="drag"
              action=""
              style="display: inline-block; width: 120px; height: 120px"
            >
              <div style="width: 120px; height: 120px; line-height: 140px">
                <Icon type="md-cloud-upload" size="50" />
              </div>
            </Upload>
          </FormItem>
          <FormItem label="上传压缩包：">
            <div class="demo-upload-list" v-if="imgUrl1 != ''">
              <template>
                <img :src="imgUrl1" />
                <div
                  v-if="param.id == undefined"
                  class="demo-upload-list-cover"
                >
                  <Icon
                    type="ios-trash-outline"
                    @click.native="handleRemove1"
                  ></Icon>
                </div>
              </template>
            </div>
            <Upload
              :disabled="uploadDisabled"
              v-if="imgUrl1 == ''"
              ref="upload"
              :show-upload-list="false"
              :before-upload="handleBeforeUpload1"
              paste
              type="drag"
              action=""
              style="display: inline-block; width: 120px; height: 120px"
            >
              <div style="width: 120px; height: 120px; line-height: 140px">
                <Icon type="md-cloud-upload" size="50" />
              </div>
            </Upload>
          </FormItem>
          <FormItem label="下载金币：">
            <Input
              :maxlength="30"
              v-model="param.downloadPoint"
              style="width: 300px"
              size="large"
            ></Input>
          </FormItem>
          <FormItem label="所属语言：">
            <Select v-model="param.type" style="width: 180px">
              <Avatar
                src="https://dev-file.iviewui.com/userinfoPDvn9gKWYihR24SpgC319vXY8qniCqj4/avatar"
                slot="prefix"
                size="small"
              />
              <Option
                v-for="item in languageList"
                :value="item.value"
                :key="item.value"
                >{{ item.label }}</Option
              >
            </Select>
          </FormItem>
          <FormItem label="源码标签：">
            <Tag
              type="border"
              v-for="(item, index) in tagList"
              :color="index | mapTagColor"
              :key="item"
              :name="item"
              closable
              @on-close="handleClose(index)"
              >{{ item }}</Tag
            >
            <Input
              class="input-new-tag"
              v-if="inputVisible"
              v-model="inputValue"
              placeholder="请输入标签"
              ref="saveTagInput"
              style="width: 200px"
              @keyup.enter.native="handleInputConfirm"
              @on-blur="handleInputConfirm"
            >
            </Input>
            <Button
              v-else
              size="small"
              type="dashed"
              icon="ios-add"
              @click="showInput"
              >添加标签</Button
            >
          </FormItem>
          <FormItem label="编辑器选择：">
            <Select
              :disabled="markdownDisabled"
              v-model="param.markdownType"
              @on-change="editorChange"
              style="width: 180px"
              placeholder="请选择内容编辑器"
            >
              <Avatar
                src="https://dev-file.iviewui.com/userinfoPDvn9gKWYihR24SpgC319vXY8qniCqj4/avatar"
                slot="prefix"
                size="small"
              />
              <Option
                v-for="item in editorList"
                :value="item.value"
                :key="item.value"
                >{{ item.label }}</Option
              >
            </Select>
          </FormItem>
          <FormItem
            label="文章内容："
            v-if="param.markdownType != '' && param.markdownType != null"
          >
            <mavon-editor
              :value="mavonEditorDefault"
              v-if="param.markdownType == '0'"
              ref="md"
              @imgAdd="handleEditorImgAdd"
              @imgDel="handleEditorImgDel"
              :toolbars="toolbars"
              @change="mavonChangeHandle"
              :ishljs="true"
              fontSize="18px"
              placeholder="客官，请输入内容哦！！！！！！"
              codeStyle="atelier-lakeside-dark"
            />
            <editor-wang
              :value="wangEditorDefault"
              v-if="param.markdownType == '1'"
              @change="changeWang"
            ></editor-wang>
          </FormItem>
          <FormItem>
            <Button v-if="param.id == undefined" type="error" @click="save"
              >发表源码</Button
            >
            <Button v-if="param.id != undefined" type="error" @click="update"
              >修改源码</Button
            >
          </FormItem>
        </Form>
      </Col>
    </Row>
  </div>
</template>

<script>
import { imageDelete, imageUpload, fileUpload, fileDelete } from "@/api/upload";
import { add, getCodeById, update } from "@/api/code";
import { mixin } from "@/utils";
import { getAes } from "@/utils/auth";
import { AESEncrypt } from "@/api/aes";
import editorWang from "@/components/wangEditor";
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
      wangEditorDefault:
        "<h5 style='line-height: 25px;margin: 15px 0;'><strong style='font-weight: 600;color: #ffa500;font-size:18px'>项目描述</strong style='font-weight: 600;color: #ffa500;font-size:18px'></h5><p><br></p><h5 style='line-height: 25px;margin: 15px 0;'><strong style='font-weight: 600;color: #ffa500;font-size:18px'>运行环境</strong></h5><p>jdk8+tomcat8+mysql+IntelliJ IDEA或者Eclipse+maven</p><p><br></p><h5 style='line-height: 25px;margin: 15px 0;'><strong style='font-weight: 600;color: #ffa500;font-size:18px'>项目技术(必填)</strong></h5><p>spring+spring mvc+mybatis+bootstrap+jquery</p><p><br></p><h5 style='line-height: 25px;margin: 15px 0;'><strong style='font-weight: 600;color: #ffa500;font-size:18px'>数据库文件(可选)</strong></h5><p>压缩包超20M请把数据库文件拆出上传到百度网盘,提供百度网盘分享地址</p><p><br></p><h5 style='line-height: 25px;margin: 15px 0;'><strong style='font-weight: 600;color: #ffa500;font-size:18px'>依赖包文件(可选)</strong></h5><p>压缩包超20M请把依赖包文件拆出上传到百度网盘,提供百度网盘分享地址(比如java的jar包)</p><p><br></p><h5 style='line-height: 25px;margin: 15px 0;'><strong style='font-weight: 600;color: #ffa500;font-size:18px'>运行视频(可选)</strong></h5><p>mp4视频文件请上传到百度网盘,提供百度网盘分享地址,加快官方审核速度</p><p><br></p><h5 style='line-height: 25px;margin: 15px 0;'><strong style='font-weight: 600;color: #ffa500;font-size:18px'>项目截图(必填)</strong></h5><p><br></p><h5 style='line-height: 25px;margin: 15px 0;'><strong style='font-weight: 600;color: #ffa500;font-size:18px'>运行截图(必填)</strong></h5><p><br></p><h5  style='line-height: 25px;margin: 15px 0;'><strong style='font-weight: 600;color: #ffa500;font-size:18px'>注意事项(可选)</strong></h5>",
      mavonEditorDefault: "",
      uploadDisabled: false,
      markdownDisabled: false,
      tagList: ["Java", "Vue"],
      inputVisible: false,
      inputValue: "",
      uploadList: [],
      imgUrl: "",
      imgUrl1: "",
      languageList: [
        {
          value: "0",
          label: "JAVA",
        },
        {
          value: "1",
          label: "Python",
        },
        {
          value: "2",
          label: "GO",
        },
        {
          value: "3",
          label: "PHP",
        },
        {
          value: "4",
          label: "VUE",
        },
        {
          value: "5",
          label: "JavaScript",
        },
        {
          value: "6",
          label: "C",
        },
        {
          value: "7",
          label: "C++",
        },
        {
          value: "8",
          label: "其它",
        },
      ],
      editorList: [
        {
          value: "0",
          label: "mavon编辑器",
        },
        {
          value: "1",
          label: "wangEditor编辑器",
        },
      ],
      param: {
        id: "",
        content: "",
        tag: "",
        pic: "",
        title: "",
        remark: "",
        type: "",
        downloadUrl: "",
        downloadPoint: "",
        markdownContent: "",
        markdownType: "",
      },
      //富文本编辑器
      toolbars: {
        bold: true, // 粗体
        italic: true, // 斜体
        header: true, // 标题
        underline: true, // 下划线
        strikethrough: true, // 中划线
        mark: true, // 标记
        superscript: true, // 上角标
        subscript: true, // 下角标
        quote: true, // 引用
        ol: true, // 有序列表
        ul: true, // 无序列表
        link: true, // 链接
        imagelink: true, // 图片链接
        code: true, // code
        table: true, // 表格
        fullscreen: true, // 全屏编辑
        readmodel: true, // 沉浸式阅读
        htmlcode: true, // 展示html源码
        help: true, // 帮助
        /* 1.3.5 */
        undo: true, // 上一步
        redo: true, // 下一步
        trash: true, // 清空
        save: true, // 保存（触发events中的save事件）
        /* 1.4.2 */
        navigation: true, // 导航目录
        /* 2.1.8 */
        alignleft: true, // 左对齐
        aligncenter: true, // 居中
        alignright: true, // 右对齐
        /* 2.2.1 */
        subfield: true, // 单双栏模式
        preview: true, // 预览
        boxShadow: true,
      },
    };
  },
  mounted() {
    this.uploadList = this.$refs.upload.fileList;
  },
  created() {
    if (document.body.offsetWidth <= 678) {
      this.labelWidth = 100;
    }
    let id = this.$route.query.id;
    this.param.id = id;
    if (id != undefined) {
      this.getBlog();
    }
  },
  methods: {
    update() {
      let tag = "";
      for (let i = 0; i < this.tagList.length; i++) {
        tag += this.tagList[i] + ",";
      }
      this.param.tag = tag;
      this.param.pic = this.imgUrl;
      if (this.param.title === null || this.param.title === "") {
        this.$Message.warning("文章标题不能为空");
        return;
      } else if (this.param.remark === null || this.param.remark === "") {
        this.$Message.warning("文章描述不能为空");
        return;
      } else if (this.param.pic === null || this.param.pic === "") {
        this.$Message.warning("封面不能为空");
        return;
      } else if (
        this.param.downloadUrl === null ||
        this.param.tydownloadUrlpe === ""
      ) {
        this.$Message.warning("压缩包不能为空");
        return;
      } else if (
        this.param.downloadPoint === null ||
        this.param.downloadPoint === ""
      ) {
        this.$Message.warning("金额不能为空");
        return;
      } else if (this.param.type === null || this.param.type === "") {
        this.$Message.warning("所属语言不能为空");
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
          this.$router.push("/codes");
        }
      });
    },
    getBlog(id) {
      getCodeById(this.param.id).then((res) => {
        this.param.title = res.title;
        this.param.remark = res.remark;
        this.param.type = res.type;
        this.param.pic = res.pic;
        this.imgUrl = res.pic;
        this.imgUrl1 = require("../../assets/images/jar.jpg");
        this.param.downloadPoint = res.downloadPoint;
        this.param.markdownType = res.markdownType;
        this.param.content = res.content;
        this.param.markdownContent = res.markdownContent;
        this.wangEditorDefault = res.markdownContent;
        this.mavonEditorDefault = res.markdownContent;
        this.tagList = res.tagList;
        this.uploadDisabled = true;
        this.markdownDisabled = true;
      });
    },
    //wangeditor 值改变的时候
    changeWang(html) {
      this.param.content = html;
      this.param.markdownContent = html;
      this.wangEditorDefault = html;
    },
    //选择完富文本清除内容信息
    editorChange() {
      if (this.param.id == undefined) {
        // this.param.content = "";
      }
    },
    //标签删除
    handleClose(index) {
      if (this.tagList.length == 1) {
        this.$Message.warning("至少保留一个标签");
        return;
      }
      this.tagList.splice(index, 1);
    },

    showInput() {
      this.inputVisible = true;
      this.$nextTick((_) => {
        this.$refs.saveTagInput.$refs.input.focus();
      });
    },

    handleInputConfirm() {
      let inputValue = this.inputValue;
      if (inputValue) {
        this.tagList.push(inputValue);
      }
      this.inputVisible = false;
      this.inputValue = "";
    },
    //富文布删除图片
    handleEditorImgDel(pos) {
      var key = pos[0].substring(pos[0].lastIndexOf("/") + 1);
      imageDelete(key).then((res) => {});
    },
    // 上传图片
    handleEditorImgAdd(pos, file) {
      let formData = new FormData();
      formData.append("file", file);
      imageUpload(formData).then((res) => {
        // console.log(JSON.stringify(url))
        // 第二步.将返回的url替换到文本原位置![...](./0) -> ![...](url)
        /**
         * $vm 指为mavonEditor实例，可以通过如下两种方式获取
         * 1.  通过引入对象获取: `import {mavonEditor} from ...` 等方式引入后，`$vm`为`mavonEditor`
         * 2. 通过$refs获取: html声明ref : `<mavon-editor ref=md ></mavon-editor>，`$vm`为 `this.$refs.md`
         */
        this.$refs.md.$img2Url(pos, res.downloadUrl);
      });
    },

    //封面图片上传
    handleBeforeUpload(file) {
      if (
        !(
          file.type === "image/png" ||
          file.type === "image/gif" ||
          file.type === "image/jpg" ||
          file.type === "image/jpeg"
        )
      ) {
        this.$Message.warning(
          "文件 " + file.name + " 格式不正确，请上传正确格式的图片。"
        );
        return false;
      }
      if (file.size > 2048000) {
        this.$Message.warning("文件 " + file.name + " 太大，文件不能超过 2M。");
        return false;
      }
      let formData = new FormData();
      formData.append("file", file);
      imageUpload(formData).then((res) => {
        this.imgUrl = res.downloadUrl;
      });
    },
    //文件上传
    handleBeforeUpload1(file) {
      if (
        !(
          file.type === "application/zip" ||
          file.type === "application/x-zip-compressed"
        )
      ) {
        this.$Message.warning("文件 " + file.name + " 格式不正确。");
        return false;
      }
      if (file.size > 2048000) {
        this.$Message.warning("文件 " + file.name + " 太大，文件不能超过 2M。");
        return false;
      }
      let formData = new FormData();
      formData.append("file", file);
      fileUpload(formData).then((res) => {
        if (res.success) {
          this.imgUrl1 = require("../../assets/images/jar.jpg");
          this.param.downloadUrl = res.downloadUrl;
        }
      });
    },
    //删除图片
    handleRemove() {
      var key = this.imgUrl.substring(this.imgUrl.lastIndexOf("/") + 1);
      imageDelete(key).then((res) => {
        if (res.success) {
          this.imgUrl = "";
        }
      });
    },
    //删除文件
    handleRemove1() {
      var key = this.param.downloadUrl.substring(
        this.param.downloadUrl.lastIndexOf("/") + 1
      );
      fileDelete(key).then((res) => {
        if (res.success) {
          this.imgUrl1 = "";
        }
      });
    },
    // 保存文章
    save() {
      let tag = "";
      for (let i = 0; i < this.tagList.length; i++) {
        tag += this.tagList[i] + ",";
      }
      this.param.tag = tag;
      this.param.pic = this.imgUrl;
      if (this.param.title === null || this.param.title === "") {
        this.$Message.warning("文章标题不能为空");
        return;
      } else if (this.param.remark === null || this.param.remark === "") {
        this.$Message.warning("文章描述不能为空");
        return;
      } else if (this.param.pic === null || this.param.pic === "") {
        this.$Message.warning("封面不能为空");
        return;
      } else if (
        this.param.downloadUrl === null ||
        this.param.tydownloadUrlpe === ""
      ) {
        this.$Message.warning("压缩包不能为空");
        return;
      } else if (
        this.param.downloadPoint === null ||
        this.param.downloadPoint === ""
      ) {
        this.$Message.warning("金额不能为空");
        return;
      } else if (this.param.type === null || this.param.type === "") {
        this.$Message.warning("所属语言不能为空");
        return;
      } else if (
        this.param.markdownType === null ||
        this.param.markdownType === ""
      ) {
        this.$Message.warning("请选择编辑器");
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
            content: "发表成功,审核通过后将于显示",
            duration: 5,
          });
          this.$router.push("/codes");
        }
      });
    },
    //mavon编辑器富文本内容发送改变的时候
    mavonChangeHandle(context, render) {
      this.param.content = render;
      this.param.markdownContent = context;
      this.mavonEditorDefault = context;
    },
  },
};
</script>

<style lang="stylus" scoped rel="stylesheet/stylus">
.v-note-wrapper {
  width: 99% !important;
  height: 900px !important;
  z-index: 1;
}

.editor {
  width: 99%;
  margin: auto;
  position: relative;
  z-index: 1;
}

.demo-upload-list {
  display: inline-block;
  width: 100px;
  height: 100px;
  text-align: center;
  line-height: 100px;
  border: 1px solid transparent;
  border-radius: 4px;
  overflow: hidden;
  background: #fff;
  position: relative;
  box-shadow: 0 1px 1px rgba(0, 0, 0, 0.2);
  margin-right: 4px;
}

.ivu-col-span-lg-17 {
  width: 100% !important;
  margin-top: 50px !important;
}

.demo-upload-list img {
  width: 100%;
  height: 100%;
}

.demo-upload-list-cover {
  display: none;
  position: absolute;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
  background: rgba(0, 0, 0, 0.6);
}

.demo-upload-list:hover .demo-upload-list-cover {
  display: block;
}

.demo-upload-list-cover i {
  color: #fff;
  font-size: 20px;
  cursor: pointer;
  margin: 0 2px;
}

.ivu-modal-footer {
  display: none !important;
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
