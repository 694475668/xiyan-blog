<template>
  <div class="dictionaries">
    <div class="box">
      <div class="dic-head">
        <el-card class="box-card" style="margin: 50px 0px 50px 0px">
          <div>
            <el-form :inline="true" :model="form" class="demo-form-inline">
              <el-form-item label="数据名称">
                <el-input v-model="form.name"></el-input>
              </el-form-item>
              <el-form-item label="含义">
                <el-input v-model="form.meaning"></el-input>
              </el-form-item>
              <el-form-item>
                <el-button type="info" @click="getList">查询</el-button>
              </el-form-item>
              <el-form-item>
                <el-button @click="empty">清空</el-button>
              </el-form-item>
              <el-form-item>
                <el-button type="warning" @click="add">新增</el-button>
              </el-form-item>
              <el-form-item>
                <el-button type="success" @click="amend">修改</el-button>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" @click="del">删除</el-button>
              </el-form-item>
            </el-form>
          </div>
        </el-card>
      </div>
      <el-card class="box-card">
        <div slot="header" class="clearfix" style="margin-left: 1.5%">
          <span class="title-style">字典</span>
        </div>
        <div class="text-item" style="margin-left: 1.5%">
          <div class="tab">
            <el-table
              :data="tableData"
              style="width: 100%"
              v-loading="loading"
              :header-cell-style="{ 'text-align': 'center' }"
              :cell-style="{ 'text-align': 'center' }"
              highlight-current-row
              @row-click="getRow"
            >
              <el-table-column
                type="index"
                :index="indexMethod"
              ></el-table-column>
              <el-table-column
                prop="name"
                label="数据名称"
                width="230"
              ></el-table-column>
              <el-table-column
                prop="value"
                label="数据值"
                width="200"
              ></el-table-column>
              <el-table-column prop="meaning" label="含义"></el-table-column>
              <el-table-column
                prop="createUser"
                label="操作员"
              ></el-table-column>
              <el-table-column
                prop="createTime"
                label="操作时间"
                :formatter="formatDate"
                width="200"
              ></el-table-column>
            </el-table>
          </div>
        </div>
      </el-card>
      <div class="page">
        <el-pagination
          background
          layout="total, prev, pager, next"
          :page-size="form.pageSize"
          :total="total"
          :current-page="form.pageNo"
          @current-change="changepage"
        ></el-pagination>
      </div>
      <el-dialog
        title="添加"
        :visible.sync="dialogFormVisible"
        @close="close"
        :close-on-click-modal="false"
      >
        <el-form
          :model="param"
          :rules="addForm"
          ref="form"
          :status-icon="true"
          :show-message="false"
          hide-required-asterisk
        >
          <el-form-item
            label="数据名称"
            :label-width="formLabelWidth"
            prop="name"
          >
            <el-input
              v-model="param.name"
              autocomplete="off"
              maxlength="10"
            ></el-input>
          </el-form-item>
          <el-form-item
            label="数据值"
            :label-width="formLabelWidth"
            prop="value"
          >
            <el-input
              v-model="param.value"
              autocomplete="off"
              maxlength="50"
            ></el-input>
          </el-form-item>
          <el-form-item
            label="含义"
            :label-width="formLabelWidth"
            prop="meaning"
          >
            <el-input
              v-model="param.meaning"
              autocomplete="off"
              maxlength="100"
            ></el-input>
          </el-form-item>
          <el-form-item
            label="备注"
            :label-width="formLabelWidth"
            prop="remark"
          >
            <el-input
              v-model="param.remark"
              autocomplete="off"
              maxlength="100"
            ></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="close">取 消</el-button>
          <el-button type="primary" @click="sure" :disabled="isDisable"
            >保存</el-button
          >
        </div>
      </el-dialog>
      <el-dialog
        title="修改"
        :visible.sync="dialogVisible"
        @close="close"
        :close-on-click-modal="false"
      >
        <el-form
          :model="param"
          :rules="addForm"
          ref="form"
          :status-icon="true"
          :show-message="false"
          hide-required-asterisk
        >
          <el-form-item
            label="数据名称"
            :label-width="formLabelWidth"
            prop="name"
          >
            <el-input
              v-model="param.name"
              autocomplete="off"
              disabled
              maxlength="50"
            ></el-input>
          </el-form-item>
          <el-form-item
            label="数据值"
            :label-width="formLabelWidth"
            prop="value"
          >
            <el-input
              v-model="param.value"
              autocomplete="off"
              disabled
              maxlength="10"
            ></el-input>
          </el-form-item>
          <el-form-item
            label="含义"
            :label-width="formLabelWidth"
            prop="meaning"
          >
            <el-input
              v-model="param.meaning"
              autocomplete="off"
              maxlength="100"
            ></el-input>
          </el-form-item>
          <el-form-item
            label="备注"
            :label-width="formLabelWidth"
            prop="remark"
          >
            <el-input
              v-model="param.remark"
              autocomplete="off"
              maxlength="100"
            ></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="close">取 消</el-button>
          <el-button type="primary" @click="update" :disabled="isDisable"
            >修改</el-button
          >
        </div>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import { list, add, del, update, getDictionaryById } from "@/api/dictionary";
import { AESEncrypt } from "@/api/aes";
import { getAes } from "@/utils/auth";
export default {
  data() {
    return {
      isDisable: false,
      //加密后请求服务器的参数
      res: {
        requestData: "",
      },
      param: {
        id: "",
        name: "",
        remark: "",
        value: "",
        meaning: "",
      },
      form: {
        name: "",
        pageNo: 1,
        pageSize: 10,
        sortField: "",
        sortRules: "",
        meaning: "",
      },
      dialogFormVisible: false,
      dialogVisible: false,
      tableData: [],
      formLabelWidth: "120px",
      total: 0,
      loading: false,
      isRepeat: false,
      currentrow: "",
      //添加
      addForm: {
        value: [{ required: true, trigger: "blur" }],
        name: [{ required: true, trigger: "blur" }],
        remark: [{ required: true, trigger: "blur" }],
        meaning: [{ required: true, trigger: "blur" }],
      },
    };
  },
  created() {
    this.getList();
  },
  methods: {
    formatDate(item) {
      if (item.updateTime != null) {
        let date = new Date(item.updateTime);
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
    getList() {
      this.loading = true;
      setTimeout(() => {
        //获取保存在cookie的AES密钥
        let aesKey = getAes();
        //进行参数加密,必须把对象转换json字符串，不然加密不了
        let dataJson = JSON.stringify(this.form);
        //数据进行加密
        this.res.requestData = AESEncrypt(dataJson, aesKey);
        list(this.res).then((res) => {
          this.currentrow = null;
          this.tableData = res.voList;
          this.total = res.total;
          this.loading = false;
          this.isRepeat = false;
        });
      }, 500);
    },
    //分页
    changepage(currentPage) {
      this.form.pageNo = currentPage;
      this.getList();
    },
    //清空
    empty() {
      this.form.name = "";
      this.form.meaning = "";
      this.getList();
    },

    //添加
    add() {
      this.dialogFormVisible = true;
      this.isDisable = false;
    },
    sure() {
      this.$refs.form.validate((valid) => {
        if (valid) {
          // 表单验证通过之后的操作
          this.isRepeat = true;
          this.isDisable = true;
          //获取保存在cookie的AES密钥
          let aesKey = getAes();
          //进行参数加密,必须把对象转换json字符串，不然加密不了
          let dataJson = JSON.stringify(this.param);
          //数据进行加密
          this.res.requestData = AESEncrypt(dataJson, aesKey);
          add(this.res).then((res) => {
            if (res.success) {
              this.$message({
                type: "success",
                message: "添加成功",
                center: true,
              });
              this.dialogFormVisible = false;
              this.empty();
            } else {
              this.$message({
                type: "warning",
                message: res.errorMsg,
                center: true,
              });
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

    //弹框关闭
    close() {
      this.dialogFormVisible = false;
      this.dialogVisible = false;
      this.$refs.form.resetFields();
    },
    //点击获取这行数据
    getRow(row) {
      this.currentrow = row;
    },
    msgTip(msgType, msgInfo) {
      this.$message({
        type: msgType,
        message: msgInfo,
        center: true,
      });
    },
    //修改
    amend() {
      if (this.currentrow == null) {
        this.msgTip("error", "请选择需要修改的数据");
      } else {
        this.dialogVisible = true;
        this.isDisable = false;
        getDictionaryById(this.currentrow.id).then((res) => {
          console.log(res);
          this.param.value = res.value;
          this.param.name = res.name;
          this.param.remark = res.remark;
          this.param.meaning = res.meaning;
        });
      }
    },
    update() {
      this.$refs.form.validate((valid) => {
        if (valid) {
          // 表单验证通过之后的操作
          this.isRepeat = true;
          this.isDisable = true;
          //获取保存在cookie的AES密钥
          let aesKey = getAes();
          //进行参数加密,必须把对象转换json字符串，不然加密不了
          let dataJson = JSON.stringify(this.param);
          //数据进行加密
          this.res.requestData = AESEncrypt(dataJson, aesKey);
          update(this.currentrow.id, this.res).then((res) => {
            if (res.success) {
              this.$message({
                type: "success",
                message: "修改成功",
                center: true,
              });
              this.dialogVisible = false;
              this.empty();
            } else {
              this.$message({
                type: "success",
                message: res.errorMsg,
                center: true,
              });
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
    //删除
    del() {
      if (this.currentrow == null) {
        this.msgTip("error", "请选择需要删除的数据！");
      } else {
        this.$confirm("您是否要删除当前选中的记录?", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }).then(() => {
          this.isRepeat = true;
          del(this.currentrow.id).then((res) => {
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
      }
    },
    indexMethod(index) {
      return index + 1;
    },
  },
};
</script>

<style lang="scss" scoped>
.dictionaries {
  width: 100%;
  min-height: 100vh;
  background-color: #f0f4f7;
  .box {
    width: 95%;
    margin: 0 auto;
    margin-top: 2%;
  }
  .dic-head {
    padding-top: 25px;
  }
  .function-icon {
    img {
      cursor: pointer;
      width: 14px;
    }
  }
  .page {
    text-align: center;
    margin-top: 20px;
  }
  .title-style {
    font-size: 22px;
    color: #000000;
    font-weight: bold;
  }
}
</style>

