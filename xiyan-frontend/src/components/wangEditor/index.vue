<template lang="html">
    <div class="editor">
        <div ref="editor" class="textNeirong">
        </div>
    </div>
</template>

<script>
import E from "wangeditor";
import store from "@/store/";
//代码高亮
import hljs from "highlight.js";
import { imageDelete, imageUpload } from "@/api/upload";
export default {
  name: "editoritem",
  data() {
    return {
      // uploadPath,components
      editor: null,
      info_: null,
      token: "",
    };
  },
  model: {
    prop: "value",
    event: "change",
  },
  props: {
    value: {
      type: String,
      default:
        "<h5 style='line-height: 25px;margin: 15px 0;'><strong style='font-weight: 600;color: #ffa500;font-size:18px'>项目描述</strong style='font-weight: 600;color: #ffa500;font-size:18px'></h5><p><br></p><h5 style='line-height: 25px;margin: 15px 0;'><strong style='font-weight: 600;color: #ffa500;font-size:18px'>运行环境</strong></h5><p>jdk8+tomcat8+mysql+IntelliJ IDEA或者Eclipse+maven</p><p><br></p><h5 style='line-height: 25px;margin: 15px 0;'><strong style='font-weight: 600;color: #ffa500;font-size:18px'>项目技术(必填)</strong></h5><p>spring+spring mvc+mybatis+bootstrap+jquery</p><p><br></p><h5 style='line-height: 25px;margin: 15px 0;'><strong style='font-weight: 600;color: #ffa500;font-size:18px'>数据库文件(可选)</strong></h5><p>压缩包超20M请把数据库文件拆出上传到百度网盘,提供百度网盘分享地址</p><p><br></p><h5 style='line-height: 25px;margin: 15px 0;'><strong style='font-weight: 600;color: #ffa500;font-size:18px'>依赖包文件(可选)</strong></h5><p>压缩包超20M请把依赖包文件拆出上传到百度网盘,提供百度网盘分享地址(比如java的jar包)</p><p><br></p><h5 style='line-height: 25px;margin: 15px 0;'><strong style='font-weight: 600;color: #ffa500;font-size:18px'>运行视频(可选)</strong></h5><p>mp4视频文件请上传到百度网盘,提供百度网盘分享地址,加快官方审核速度</p><p><br></p><h5 style='line-height: 25px;margin: 15px 0;'><strong style='font-weight: 600;color: #ffa500;font-size:18px'>项目截图(必填)</strong></h5><p><br></p><h5 style='line-height: 25px;margin: 15px 0;'><strong style='font-weight: 600;color: #ffa500;font-size:18px'>运行截图(必填)</strong></h5><p><br></p><h5  style='line-height: 25px;margin: 15px 0;'><strong style='font-weight: 600;color: #ffa500;font-size:18px'>注意事项(可选)</strong></h5>",
    },
    isClear: {
      type: Boolean,
      default: false,
    },
  },
  watch: {
    isClear(val) {
      // 触发清除文本域内容
      if (val) {
        this.editor.txt.clear();
        this.info_ = null;
      }
    },
    value: function (value) {
      if (value !== this.editor.txt.html()) {
        this.editor.txt.html(this.value);
      }
    },
    //value为编辑框输入的内容，这里我监听了一下值，当父组件调用得时候，如果给value赋值了，子组件将会显示父组件赋给的值
  },
  created() {
    this.token = store.getters.token;
  },
  mounted() {
    this.seteditor();
    this.editor.txt.html(this.value);
  },
  methods: {
    seteditor() {
      this.editor = new E(this.$refs.editor);
      this.editor.config.uploadImgShowBase64 = false; // base 64 存储图片
      this.editor.config.uploadImgHeaders = { "x-access-token": this.token }; // 自定义 header
      this.editor.config.uploadFileName = "file"; // 后端接受上传文件的参数名
      this.editor.config.uploadImgMaxSize = 2 * 1024 * 1024; // 将图片大小限制为 2M
      this.editor.config.uploadImgTimeout = 3 * 60 * 1000; // 设置超时时间
      this.editor.config.uploadImgAccept = ["jpg", "jpeg", "png", "gif", "bmp"]; //限制类型
      this.editor.config.height = 900; //编辑器的高度
      // 自定义 onchange 触发的延迟时间，默认为 200 ms
      this.editor.config.onchangeTimeout = 1000; // 单位 ms
      // 挂载highlight插件
      this.editor.highlight = hljs;
      this.editor.config.onchange = (html) => {
        this.info_ = html; // 绑定当前逐渐地值
        this.$emit("change", this.info_); // 将内容同步到父组件中
      };
      // 创建富文本编辑器
      this.editor.create();
      this.editor.config.customUploadImg = (resultFiles, insertImgFn) => {
        // resultFiles 是 input 中选中的文件列表
        // insertImgFn 是获取图片 url 后，插入到编辑器的方法

        // 上传图片，返回结果，将图片插入到编辑器中
        for (var i = 0; i < resultFiles.length; i++) {
          let formData = new FormData();
          formData.append("file", resultFiles[i]);
          imageUpload(formData).then((res) => {
            if (res.success) {
              insertImgFn(res.downloadUrl);
            }
          });
        }
        console.log(resultFiles.length);
      };
      this.editor.config.uploadImgHooks = {
        fail: (xhr, editor, result) => {
          // 插入图片失败回调
          this.$Message.warning("上传失败");
        },
        success: (xhr, editor, result) => {
          // 图片上传成功回调
          this.$Message.success("图片上传成功");
        },
        timeout: (xhr, editor) => {
          // 网络超时的回调
          this.$Message.warning("上传超时");
        },
        error: (xhr, editor) => {
          // 图片上传错误的回调
          this.$Message.warning("上传失败");
        },
      };
    },
  },
};
</script>
