<template>
  <div class="resource">
    <div class="box">
      <div class="head">
        <el-button type="primary" @click="add">添加</el-button>
        <el-button type="warning" id="fold" @click="fold">折叠</el-button>
        <el-button @click="reload">刷新</el-button>
      </div>
      <div class="content" v-loading="loading">
        <div class="table-title">
          <div>资源名称</div>
          <div>资源路径</div>
          <div>资源类型</div>
          <div>上级资源</div>
          <div>操作</div>
          <div>备注</div>
        </div>
        <div class="table-info">
          <div
            class="table-item"
            v-for="(item, index) in roleDate"
            :key="index"
          >
            <div class="item-title">
              <div class="item" @click="pickUp(item)">
                <i v-if="item.pick_up" class="el-icon-caret-bottom"></i>
                <i v-else class="el-icon-caret-right"></i>
                {{ item.label }}
              </div>
              <div class="item">{{ item.attributes.url }}</div>
              <div class="item">{{ item.attributes.resourceType }}</div>
              <div class="item">{{ item.attributes.superior }}</div>
              <div class="item">
                <el-tooltip
                  class="item"
                  effect="dark"
                  content="修改"
                  placement="bottom"
                >
                  <img
                    @click="exchange(item.id)"
                    src="../../assets/iconfont/amend.png"
                    alt
                  />
                </el-tooltip>
                <el-tooltip
                  class="item"
                  effect="dark"
                  content="删除"
                  placement="bottom"
                >
                  <img
                    @click="dele(item.id)"
                    src="../../assets/iconfont/delete.png"
                    alt
                  />
                </el-tooltip>
              </div>
              <div class="item">{{ item.attributes.remark }}</div>
            </div>
            <div
              class="item-detail"
              v-for="(childItem, childIndex) in item.children"
              :key="childIndex"
            >
              <div class="detail-content" v-if="item.pick_up">
                <div class="detail-info">
                  <div
                    style="padding-left: 20px"
                    class="item"
                    @click="pickUp(childItem)"
                  >
                    <i
                      v-if="childItem.pick_up"
                      class="el-icon-caret-bottom"
                    ></i>
                    <i v-else class="el-icon-caret-right"></i>
                    {{ childItem.label }}
                  </div>
                  <div class="item">{{ childItem.attributes.url }}</div>
                  <div class="item">
                    {{ childItem.attributes.resourceType }}
                  </div>
                  <div class="item">{{ childItem.attributes.superior }}</div>
                  <div class="item">
                    <el-tooltip
                      class="item"
                      effect="dark"
                      content="修改"
                      placement="bottom"
                    >
                      <img
                        @click="exchange(childItem.id)"
                        src="../../assets/iconfont/amend.png"
                        alt
                      />
                    </el-tooltip>
                    <el-tooltip
                      class="item"
                      effect="dark"
                      content="删除"
                      placement="bottom"
                    >
                      <img
                        @click="dele(childItem.id)"
                        src="../../assets/iconfont/delete.png"
                        alt
                      />
                    </el-tooltip>
                  </div>
                  <div class="item">{{ childItem.attributes.remark }}</div>
                </div>
                <div
                  class="item-list"
                  v-for="(childs, indexs) in childItem.children"
                  :key="indexs"
                >
                  <div v-if="childItem.pick_up" class="item-content">
                    <div class="item">{{ childs.label }}</div>
                    <div class="item">{{ childs.attributes.url }}</div>
                    <div class="item">{{ childs.attributes.resourceType }}</div>
                    <div class="item">{{ childs.attributes.superior }}</div>
                    <div class="item">
                      <el-tooltip
                        class="item"
                        effect="dark"
                        content="修改"
                        placement="bottom"
                      >
                        <img
                          @click="exchange(childs.id)"
                          src="../../assets/iconfont/amend.png"
                          alt
                        />
                      </el-tooltip>
                      <el-tooltip
                        class="item"
                        effect="dark"
                        content="删除"
                        placement="bottom"
                      >
                        <img
                          @click="dele(childs.id)"
                          src="../../assets/iconfont/delete.png"
                          alt
                        />
                      </el-tooltip>
                    </div>
                    <div class="item">{{ childs.attributes.remark }}</div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="table-nodata" v-if="roleDate.length == 0">暂无数据</div>
      </div>

      <el-dialog
        title="添加资源"
        :visible.sync="dialogVisibles"
        @close="close"
        :close-on-click-modal="false"
      >
        <el-form
          :model="form"
          :rules="addRules"
          ref="form"
          :status-icon="true"
          :show-message="false"
          hide-required-asterisk
        >
          <el-form-item
            label="资源名称"
            :label-width="formLabelWidth"
            prop="name"
          >
            <el-input v-model="form.name"></el-input>
          </el-form-item>
          <el-form-item label="资源路径" :label-width="formLabelWidth">
            <el-input v-model="form.url"></el-input>
          </el-form-item>
          <div class="three">
            <el-form-item label="上级资源" :label-width="formLabelWidth">
              <el-select v-model="form.pid" style="width: 100%">
                <el-option :value="form.pid" style="padding: 0; height: auto">
                  <el-tree
                    ref="tree"
                    :data="routesData"
                    :props="defaultProps"
                    node-key="id"
                    class="permission-tree"
                    @node-click="handleNodeClick"
                    :highlight-current="true"
                  ></el-tree>
                </el-option>
              </el-select>
            </el-form-item>
          </div>
          <div class="three">
            <el-form-item
              label="资源类型"
              :label-width="formLabelWidth"
              prop="type"
            >
              <el-select v-model="form.type" style="width: 100%">
                <el-option
                  v-for="item in type"
                  :key="item.id"
                  :label="item.meaning"
                  :value="item.value"
                ></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="排序" :label-width="formLabelWidth">
              <el-input-number
                v-model="form.seq"
                controls-position="right"
                :min="100"
                style="width: 100%"
              ></el-input-number>
            </el-form-item>
          </div>
          <el-form-item label="备注" :label-width="formLabelWidth">
            <el-input type="textarea" v-model="form.remark"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogVisibles = false">取 消</el-button>
          <el-button type="primary" @click="save" :disabled="isDisable"
            >保存</el-button
          >
        </div>
      </el-dialog>

      <el-dialog title="修改" :visible.sync="dialogVisible">
        <el-form
          :model="form"
          :rules="addRules"
          ref="form"
          :status-icon="true"
          :show-message="false"
          hide-required-asterisk
          :close-on-click-modal="false"
        >
          <el-form-item
            label="资源名称"
            :label-width="formLabelWidth"
            prop="name"
          >
            <el-input v-model="form.name" :disabled="true"></el-input>
          </el-form-item>
          <el-form-item label="资源路径" :label-width="formLabelWidth">
            <el-input v-model="form.url"></el-input>
          </el-form-item>
          <div class="three">
            <el-form-item label="上级资源" :label-width="formLabelWidth">
              <el-select v-model="form.pid" style="width: 100%">
                <el-option :value="form.pid" style="padding: 0; height: auto">
                  <el-tree
                    ref="tree"
                    :data="routesData"
                    :props="defaultProps"
                    node-key="id"
                    class="permission-tree"
                    @node-click="handleNodeClick"
                    :highlight-current="true"
                  ></el-tree>
                </el-option>
              </el-select>
            </el-form-item>
          </div>
          <div class="three">
            <el-form-item
              label="资源类型"
              :label-width="formLabelWidth"
              prop="type"
            >
              <el-select v-model="form.type" style="width: 100%">
                <el-option
                  v-for="item in type"
                  :key="item.id"
                  :label="item.meaning"
                  :value="item.value"
                ></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="排序" :label-width="formLabelWidth">
              <el-input-number
                v-model="form.seq"
                controls-position="right"
                :min="100"
                style="width: 100%"
              ></el-input-number>
            </el-form-item>
          </div>
          <el-form-item label="备注" :label-width="formLabelWidth">
            <el-input type="textarea" v-model="form.remark"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="keep" :disabled="isDisable"
            >保存</el-button
          >
        </div>
      </el-dialog>
    </div>
  </div>
</template>
<script>
import {
  queryDictionaryByName,
} from "../../api/dictionary.js";
import {
  list,
  queryResourceByUserId,
  add,
  del,
  update,
  queryResourceById,
} from "@/api/resource";
import { AESEncrypt } from "@/api/aes";
import { getAes } from "@/utils/auth";
export default {
  data() {
    return {
      //加密后请求服务器的参数
      res: {
        requestData: "",
      },
      systemType: [],
      resourcesTypeParam: {
        name: "",
      },
      isDisable: false,
      roleDate: [], //列表数据
      form: {
        id: "",
        name: "",
        pid: "",
        remark: "",
        type: "",
        seq: "",
        url: "",
      },
      formLabelWidth: "100px",
      dialogVisibles: false,
      loading: false,
      isRepeat: false,
      type: [],
      routesData: [],
      defaultProps: {
        children: "children",
        label: "label",
      },
      addRules: {
        name: [{ required: true, trigger: "blur" }],
        type: [{ required: true, trigger: "blur" }],
      },
      foldItem: "",
      //修改
      dialogVisible: false,
      rowId: "",
      myid: "",
      flag: true,
    };
  },
  created() {
    this.getList();
  },
  methods: {
    getList() {
      this.loading = true;
      setTimeout(() => {
        list().then((res) => {
          res.voList.map((item) => {
            if (this.flag) {
              item.pick_up = true;
            } else {
              item.pick_up = false;
            }
            item.children.map((childItem) => {
              childItem.pick_up = true;
              this.loading = false;
              this.isRepeat = false;
              return childItem;
            });
            this.loading = false;
            this.isRepeat = false;
            return item;
          });
          this.roleDate = res.voList;
        });
      }, 500);
    },
    // icon的点击child的展开及收起情况
    pickUp(item) {
      item.pick_up = !item.pick_up;
      this.foldItem = item.pick_up;
    },

    msgTip(msgType, msgInfo) {
      this.$message({
        type: msgType,
        message: msgInfo,
        center: true,
      });
    },

    //添加
    add() {
      this.isDisable = false;
      this.dialogVisibles = true;
      this.form.name = "";
      this.form.url = "";
      this.form.pid = "";
      this.form.type = "";
      this.form.seq = "";
      this.form.remark = "";
      //获取保存在cookie的AES密钥
      this.resourcesTypeParam.name = "RESOURCETYPE_ID";
      let aesKey = getAes();
      //进行参数加密,必须把对象转换json字符串，不然加密不了
      let dataJson = JSON.stringify(this.resourcesTypeParam);
      //数据进行加密
      this.res.requestData = AESEncrypt(dataJson, aesKey);
      queryDictionaryByName(this.res).then((res) => {
        this.type = res.voList;
      });
      queryResourceByUserId().then((res) => {
        this.routesData = res.voList.map((item) => {
          item.children.map((res) => {
            if (res.children) {
              delete res.children;
            }
          });
          item.label = item.label;
          return item;
        });
      });
    },
    handleNodeClick(data) {
      this.form.pid = data.label;
      this.myid = data.id;
    },
    save() {
      this.$refs.form.validate((valid) => {
        if (valid) {
          // 表单验证通过之后的操作
          this.isRepeat = true;
          this.isDisable = true;
          this.form.pid = this.myid;
          let aesKey = getAes();
          //进行参数加密,必须把对象转换json字符串，不然加密不了
          let dataJson = JSON.stringify(this.form);
          //数据进行加密
          this.res.requestData = AESEncrypt(dataJson, aesKey);
          add(this.res).then((res) => {
            if (res.success) {
              this.msgTip("success", "添加成功");
              this.dialogVisibles = false;
              this.getList();
            } else {
              this.msgTip("error", res.errorMsg);
              this.isRepeat = false;
              this.isDisable = false;
            }
          });
        } else {
          this.msgTip("warning", "请填写完整");
          return false;
        }
      });
    },
    close() {
      this.form.name = "";
      this.form.url = "";
      this.form.type = "";
      this.form.pid = "";
      this.form.remark = "";
      this.dialogVisibles = false;
      this.dialogVisible = false;
      this.$refs.form.resetFields();
    },
    //修改
    exchange(val) {
      this.isDisable = false;
      this.dialogVisible = true;
      queryResourceById(val).then((res) => {
        this.form.id = res.id;
        this.form.name = res.name;
        this.form.pid = res.pname;
        this.form.pname = res.pid;
        console.log(this.form.pname);
        this.form.remark = res.remark;
        this.form.seq = res.seq;
        this.form.url = res.url;
        this.form.type = res.type;
        if (res.type == "0") {
          this.form.type = "菜单";
        } else if (res.type == "1") {
          this.form.type = "功能";
        }
      });
      //获取保存在cookie的AES密钥
      this.resourcesTypeParam.name = "RESOURCETYPE_ID";
      let aesKey = getAes();
      //进行参数加密,必须把对象转换json字符串，不然加密不了
      let dataJson = JSON.stringify(this.resourcesTypeParam);
      //数据进行加密
      this.res.requestData = AESEncrypt(dataJson, aesKey);
      queryDictionaryByName(this.res).then((res) => {
        this.type = res.voList;
      });
      queryResourceByUserId().then((res) => {
        this.routesData = res.voList.map((item) => {
          item.children.map((res) => {
            if (res.children) {
              delete res.children;
            }
          });
          item.label = item.label;
          return item;
        });
      });
      this.rowId = val;
    },
    keep() {
      this.isDisable = true;
      let myid = null;
      console.log(this.form.pname);
      if (this.myid == null || this.myid == "") {
        myid = this.form.pname;
      } else {
        myid = this.myid;
      }
      if (this.form.type == "菜单") {
        this.form.type = "0";
      } else if (this.form.type == "功能") {
        this.form.type = "1";
      }
      this.isRepeat = true;
      this.form.pid = this.myid;
      //获取保存在cookie的AES密钥
      this.resourcesTypeParam.name = "RESOURCETYPE_ID";
      let aesKey = getAes();
      //进行参数加密,必须把对象转换json字符串，不然加密不了
      let dataJson = JSON.stringify(this.form);
      //数据进行加密
      this.res.requestData = AESEncrypt(dataJson, aesKey);
      update(this.rowId, this.res).then((res) => {
        if (res.success) {
          this.msgTip("success", "修改成功！");
          this.dialogVisible = false;
          this.getList();
        } else {
          this.msgTip("error", res.errorMsg);
          this.isRepeat = false;
          this.isDisable = false;
        }
      });
    },

    dele(val) {
      this.$confirm("您是否要删除当前选中的记录？", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        this.isRepeat = true;
        del(val).then((res) => {
          if (res.success) {
            this.$message({
              type: "success",
              message: "删除成功",
              center: true,
            });
            this.getList();
          } else {
            this.$message({
              type: "warning",
              message: res.errorMsg,
              center: true,
            });
            this.isRepeat = false;
          }
        });
      });
    },
    //折叠
    fold() {
      var fold = document.getElementById("fold");
      if (fold.innerText == "折叠") {
        this.flag = false;
        document.getElementById("fold").innerText = "展开";
      } else if (fold.innerText == "展开") {
        this.flag = true;
        document.getElementById("fold").innerText = "折叠";
      }
      this.getList();
    },
    //刷新
    reload() {
      document.getElementById("fold").innerText = "折叠";
      this.flag = true;
      this.getList();
    },
  },
};
</script>

<style lang='scss' scoped>
.resource {
  width: 100%;
  min-height: 100vh;
  background-color: #f0f4f7;
  .box {
    width: 96%;
    margin: 0 auto;
  }
  .head {
    padding: 20px 11px;
    padding-top: 110px;
  }
  .content {
    padding: 10px;
    .table-title {
      display: flex;
      line-height: 36px;
      background: #f5f7fa;
      border: 1px solid #e4e9ee;
      div {
        flex: 1;
        padding: 8px 10px;
      }
    }
    .table-info {
      border-right: 1px solid #e4e9ee;
      border-left: 1px solid #e4e9ee;
      .table-item {
        .item-title,
        .detail-info {
          border-bottom: 1px solid #e4e9ee;
          display: flex;
          line-height: 36px;
          div {
            flex: 1;
            padding: 8px 10px;
          }
        }
        .item-list {
          .item-content {
            border-bottom: 1px solid #e4e9ee;
            display: flex;
            line-height: 36px;
            div {
              flex: 1;
              padding: 8px 46px;
            }
          }
        }
      }
      img {
        width: 15px;
        cursor: pointer;
      }
    }
    .table-nodata {
      display: flex;
      align-items: center;
      justify-content: center;
      font-size: 24px;
      color: #c0c4cc;
      width: 100%;
      height: 450px;
      border-left: 1px solid #e4e9ee;
      border-right: 1px solid #e4e9ee;
      border-bottom: 1px solid #e4e9ee;
    }
  }
  .three {
    display: flex;
    justify-content: space-between;
    .el-form-item {
      width: 50%;
    }
  }
}
</style>