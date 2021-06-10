<template>
  <div class="bookNote-list-content">
    <el-row>
      <el-col :xs="24" :sm="24" :md="24" :lg="17">
        <el-form ref="article" :model="article" :label-width="labelWidth">
          <input v-model="article.id" name="id" type="hidden" />
          <el-form-item label="文章标题：">
            <el-input
              placeholder="请填写文章标题..."
              :maxlength="30"
              v-model="article.title"
              style="width: 300px"
              size="large"
            ></el-input>
          </el-form-item>
          <el-form-item label="文章描述：">
            <el-input
              type="textarea"
              placeholder="请填写文章描述..."
              v-model="article.remark"
              :maxlength="100"
              style="width: 600px"
            ></el-input>
          </el-form-item>
          <el-form-item label="选择封面：">
            <el-upload
              class="avatar-uploader"
              action="https://jsonplaceholder.typicode.com/posts/"
              :show-file-list="false"
              :before-upload="beforeUploadPicture"
            >
              <img v-if="imgUrl" :src="imgUrl" class="avatar" />
              <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
          </el-form-item>
          <el-form-item label="所属语言：">
            <el-select v-model="article.type" style="width: 180px">
              <el-avatar
                style="margin: 5px -5px"
                src="https://dev-file.iviewui.com/userinfoPDvn9gKWYihR24SpgC319vXY8qniCqj4/avatar"
                slot="prefix"
                size="small"
              />
              <el-option
                v-for="item in languageList"
                :value="item.value"
                :key="item.value"
                :label="item.label"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="文章标签：">
            <el-tag
              type="border"
              v-for="(item, index) in tagList"
              :color="index | mapTagColor"
              :key="item"
              :name="item"
              closable
              @close="handleClose(index)"
              >{{ item }}</el-tag
            >
            <el-input
              class="input-new-tag"
              v-if="inputVisible"
              v-model="inputValue"
              placeholder="请输入标签"
              ref="saveTagInput"
              style="width: 200px"
              @keyup.enter.native="handleInputConfirm"
              @blur="handleInputConfirm"
            >
            </el-input>
            <el-button
              v-else
              size="small"
              type="dashed"
              icon="ios-add"
              @click="showInput"
              >添加标签</el-button
            >
          </el-form-item>
          <el-form-item label="编辑器选择：">
            <el-select
              v-model="article.markdownType"
              :disabled="markdownDisabled"
              @on-change="editorChange"
              style="width: 180px"
              placeholder="请选择内容编辑器"
            >
              <el-avatar
                style="margin: 5px -5px"
                src="https://dev-file.iviewui.com/userinfoPDvn9gKWYihR24SpgC319vXY8qniCqj4/avatar"
                slot="prefix"
                size="small"
              />
              <el-option
                v-for="item in editorList"
                :value="item.value"
                :key="item.value"
                :label="item.label"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item
            label="文章内容："
            v-if="article.markdownType != '' && article.markdownType != null"
          >
            <mavon-editor
              :value="mavonEditorDefault"
              v-if="article.markdownType == '0'"
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
              v-if="article.markdownType == '1'"
              @change="changeWang"
            ></editor-wang>
          </el-form-item>
          <el-form-item>
            <el-button
              v-if="article.id == undefined"
              type="primary"
              @click="saveArticle"
              >发表文章</el-button
            >
            <el-button
              v-if="article.id != undefined"
              type="primary"
              @click="updateArticle"
              >修改文章</el-button
            >
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { imageDelete, imageUpload } from "@/api/upload";
import { add, getArticleById, update } from "@/api/article";
import { mixin } from "@/utils";
import { getAes } from "@/utils/auth";
import { AESEncrypt } from "@/api/aes";
import editorWang from "@/views/wangEditor";
export default {
  components: { editorWang },
  mixins: [mixin],
  data: function () {
    return {
      labelWidth: "200px",
      //加密后请求服务器的参数
      res: {
        requestData: "",
      },
      wangEditorDefault:
        "<h5 style='line-height: 25px;margin: 15px 0;'><strong style='font-weight: 600;color: #ffa500;font-size:18px'>项目描述</strong style='font-weight: 600;color: #ffa500;font-size:18px'></h5><p><br></p><h5 style='line-height: 25px;margin: 15px 0;'><strong style='font-weight: 600;color: #ffa500;font-size:18px'>运行环境</strong></h5><p>jdk8+tomcat8+mysql+IntelliJ IDEA或者Eclipse+maven</p><p><br></p><h5 style='line-height: 25px;margin: 15px 0;'><strong style='font-weight: 600;color: #ffa500;font-size:18px'>项目技术(必填)</strong></h5><p>spring+spring mvc+mybatis+bootstrap+jquery</p><p><br></p><h5 style='line-height: 25px;margin: 15px 0;'><strong style='font-weight: 600;color: #ffa500;font-size:18px'>数据库文件(可选)</strong></h5><p>压缩包超20M请把数据库文件拆出上传到百度网盘,提供百度网盘分享地址</p><p><br></p><h5 style='line-height: 25px;margin: 15px 0;'><strong style='font-weight: 600;color: #ffa500;font-size:18px'>依赖包文件(可选)</strong></h5><p>压缩包超20M请把依赖包文件拆出上传到百度网盘,提供百度网盘分享地址(比如java的jar包)</p><p><br></p><h5 style='line-height: 25px;margin: 15px 0;'><strong style='font-weight: 600;color: #ffa500;font-size:18px'>运行视频(可选)</strong></h5><p>mp4视频文件请上传到百度网盘,提供百度网盘分享地址,加快官方审核速度</p><p><br></p><h5 style='line-height: 25px;margin: 15px 0;'><strong style='font-weight: 600;color: #ffa500;font-size:18px'>项目截图(必填)</strong></h5><p><br></p><h5 style='line-height: 25px;margin: 15px 0;'><strong style='font-weight: 600;color: #ffa500;font-size:18px'>运行截图(必填)</strong></h5><p><br></p><h5  style='line-height: 25px;margin: 15px 0;'><strong style='font-weight: 600;color: #ffa500;font-size:18px'>注意事项(可选)</strong></h5>",
      mavonEditorDefault: "",
      markdownDisabled: false,
      tagList: ["Java", "Vue"],
      inputVisible: false,
      inputValue: "",
      imgUrl: "",
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
          label: "Linux",
        },
        {
          value: "9",
          label: "APP",
        },
        {
          value: "10",
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
      article: {
        id: "",
        content: "",
        tag: "",
        pic: "",
        title: "",
        remark: "",
        type: "",
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
  created() {
    if (document.body.offsetWidth <= 678) {
      this.labelWidth = "100px";
    }
    let id = this.$route.query.id;
    this.article.id = id;
    if (id != undefined) {
      this.getArticle();
    }
  },
  methods: {
    updateArticle() {
      let tag = "";
      for (let i = 0; i < this.tagList.length; i++) {
        tag += this.tagList[i] + ",";
      }
      this.article.tag = tag;
      this.article.pic = this.imgUrl;
      if (this.article.title === null || this.article.title === "") {
        this.$message.warning("文章标题不能为空");
        return;
      } else if (this.article.remark === null || this.article.remark === "") {
        this.$message.warning("文章描述不能为空");
        return;
      } else if (this.article.pic === null || this.article.pic === "") {
        this.$message.warning("封面不能为空");
        return;
      } else if (this.article.type === null || this.article.type === "") {
        this.$message.warning("所属语言不能为空");
        return;
      } else if (this.article.content === null || this.article.content === "") {
        this.$message.error("文章内容不能为空");
        return;
      }
      //获取保存在cookie的AES密钥
      let aesKey = getAes();
      //进行参数加密,必须把对象转换json字符串，不然加密不了
      let dataJson = JSON.stringify(this.article);
      //数据进行加密
      this.res.requestData = AESEncrypt(dataJson, aesKey);
      update(this.res, this.article.id).then((res) => {
        if (res.success) {
          this.$message({
            type: "success",
            message: "修改成功",
            center: true,
          });
          this.$router.push("/article/list");
        }
      });
    },
    getArticle(id) {
      getArticleById(this.article.id).then((res) => {
        this.article.title = res.title;
        this.article.remark = res.remark;
        this.article.type = res.type;
        this.article.pic = res.pic;
        this.imgUrl = res.pic;
        this.article.markdownType = res.markdownType;
        this.article.markdownContent = res.markdownContent;
        this.article.content = res.content;
        this.wangEditorDefault = res.markdownContent;
        this.mavonEditorDefault = res.markdownContent;
        this.tagList = res.tagList;
        this.markdownDisabled = true;
      });
    },
    //wangeditor 值改变的时候
    changeWang(html) {
      this.article.content = html;
      this.article.markdownContent = html;
      this.wangEditorDefault = html;
    },
    //选择完富文本清除内容信息
    editorChange() {
      if (this.article.id == undefined) {
        // this.article.content = "";
      }
    },
    //标签删除
    handleClose(index) {
      if (this.tagList.length == 1) {
        this.$message.warning("至少保留一个标签");
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
    beforeUploadPicture(file) {
      if (
        !(
          file.type === "image/png" ||
          file.type === "image/gif" ||
          file.type === "image/jpg" ||
          file.type === "image/jpeg"
        )
      ) {
        this.$message.warning(
          "文件 " + file.name + " 格式不正确，请上传正确格式的图片。"
        );
        return false;
      }
      if (file.size > 2048000) {
        this.$message.warning("文件 " + file.name + " 太大，文件不能超过 2M。");
        return false;
      }
      let formData = new FormData();
      formData.append("file", file);
      imageUpload(formData).then((res) => {
        this.imgUrl = res.downloadUrl;
      });
    },
    // 保存文章
    saveArticle() {
      let tag = "";
      for (let i = 0; i < this.tagList.length; i++) {
        tag += this.tagList[i] + ",";
      }
      this.article.tag = tag;
      this.article.pic = this.imgUrl;
      if (this.article.title === null || this.article.title === "") {
        this.$message.warning("文章标题不能为空");
        return;
      } else if (this.article.remark === null || this.article.remark === "") {
        this.$message.warning("文章描述不能为空");
        return;
      } else if (this.article.pic === null || this.article.pic === "") {
        this.$message.warning("封面不能为空");
        return;
      } else if (this.article.type === null || this.article.type === "") {
        this.$message.warning("所属语言不能为空");
        return;
      } else if (
        this.article.markdownType === null ||
        this.article.markdownType === ""
      ) {
        this.$message.warning("请选择编辑器");
        return;
      } else if (this.article.content === null || this.article.content === "") {
        this.$message.error("文章内容不能为空");
        return;
      }
      //获取保存在cookie的AES密钥
      let aesKey = getAes();
      //进行参数加密,必须把对象转换json字符串，不然加密不了
      let dataJson = JSON.stringify(this.article);
      //数据进行加密
      this.res.requestData = AESEncrypt(dataJson, aesKey);
      add(this.res).then((res) => {
        if (res.success) {
          this.$message({
            type: "success",
            message: "发表成功,审核通过后将于显示",
            center: true,
          });
          this.$router.push("/article/list");
        }
      });
    },
    //mavon编辑器富文本内容发送改变的时候
    mavonChangeHandle(context, render) {
      this.article.content = render;
      this.article.markdownContent = context;
      this.mavonEditorDefault = context;
    },
  },
};
</script>

<style lang="scss" scoped>
/deep/.el-tag .el-tag__close,
.el-tag {
  color: #fff;
}
/deep/.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
/deep/.avatar-uploader .el-upload:hover {
  border-color: #409eff;
}
/deep/.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}
/deep/.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
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
    margin: 80px 35px 0 35px;
  }

  @media screen and (min-width: 1200px) {
  }

  .layout-left,
  .layout-right {
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
