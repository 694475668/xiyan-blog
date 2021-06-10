<template>
  <div class="systemuser">
    <div class="box">
      <div class="user-head">
        <el-card class="box-card" style="margin: 50px 0px 50px 0px">
          <div>
            <el-form :inline="true" :model="form" class="demo-form-inline">
              <el-form-item label="用户名">
                <el-input v-model="form.username"></el-input>
              </el-form-item>
              <el-form-item label="所属角色">
                <el-select v-model="form.roleId" placeholder="请选择">
                  <el-option
                    v-for="(item, index) in roleList"
                    :key="index"
                    :label="item.name"
                    :value="item.id"
                  ></el-option>
                </el-select>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" @click="search">查询</el-button>
              </el-form-item>
              <el-form-item>
                <el-button @click="empty">清空</el-button>
              </el-form-item>
              <el-form-item>
                <el-button type="warning" @click="add">新 增</el-button>
              </el-form-item>
            </el-form>
          </div>
        </el-card>
      </div>
      <el-card class="box-card">
        <div slot="header" class="clearfix" style="margin-left: 1.5%">
          <span class="title-style">用户</span>
        </div>
        <div class="text-item" style="margin-left: 1.5%">
          <div class="tab">
            <el-table
              :data="tableData"
              style="width: 100%"
              border
              v-loading="loading"
              :header-cell-style="{ 'text-align': 'center' }"
              :cell-style="{ 'text-align': 'center' }"
              @selection-change="handleSelectionChange"
            >
              <el-table-column
                prop="username"
                label="用户名"
                width="120px"
              ></el-table-column>
              <el-table-column
                prop="name"
                label="姓名"
                width="120px"
              ></el-table-column>
              <el-table-column
                prop="address"
                label="所属角色名称"
                show-overflow-tooltip
              >
                <template slot-scope="scope">
                  <span class="resource">{{ scope.row.rnames }}</span>
                </template>
              </el-table-column>
              <el-table-column
                prop="phone"
                label="手机号"
                width="200"
              ></el-table-column>
              <el-table-column
                prop="email"
                label="电子邮箱"
                width="230px"
              ></el-table-column>
              <el-table-column
                prop="updateUser"
                label="修改人"
                width="120px"
              ></el-table-column>
              <el-table-column
                prop="updateTime"
                label="修改时间"
                :formatter="formatDate"
                width="170px"
              ></el-table-column>
              <el-table-column
                prop="status"
                label="状态"
                :formatter="stateFormat"
                width="70px"
              ></el-table-column>
              <el-table-column label="操作" width="200px">
                <template slot-scope="scope">
                  <div class="function-icon">
                    <el-tooltip
                      class="item"
                      effect="dark"
                      content="修改"
                      placement="bottom"
                    >
                      <img
                        @click="amend(scope.row)"
                        src="../../assets/iconfont/amend.png"
                        alt
                      />
                    </el-tooltip>
                    <el-tooltip
                      v-show="scope.row.status == 1"
                      class="item"
                      effect="dark"
                      content="锁定"
                      placement="bottom"
                    >
                      <img
                        @click="lock(scope.row)"
                        src="../../assets/iconfont/lock.png"
                        alt
                      />
                    </el-tooltip>
                    <el-tooltip
                      v-show="scope.row.status == 0"
                      class="item"
                      effect="dark"
                      content="解锁"
                      placement="bottom"
                    >
                      <img
                        @click="unlock(scope.row)"
                        src="../../assets/iconfont/unlock.png"
                        alt
                      />
                    </el-tooltip>
                    <el-tooltip
                      class="item"
                      effect="dark"
                      content="授权"
                      placement="bottom"
                    >
                      <img
                        @click="accredit(scope.row)"
                        src="../../assets/iconfont/accredit.png"
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
                        @click="dele(scope.row)"
                        src="../../assets/iconfont/delete.png"
                        alt
                      />
                    </el-tooltip>
                    <el-tooltip
                      class="item"
                      effect="dark"
                      content="修改密码"
                      placement="bottom"
                    >
                      <img
                        @click="pasd(scope.row)"
                        src="../../assets/iconfont/pasd.png"
                        alt
                      />
                    </el-tooltip>
                  </div>
                </template>
              </el-table-column>
            </el-table>
          </div>
        </div>
      </el-card>
      <el-dialog
        title="修改密码"
        :visible.sync="dialogFormVisible"
        @close="pasdclose"
        :close-on-click-modal="false"
      >
        <el-form
          :model="pasds"
          :rules="pasdRules"
          ref="pasds"
          :status-icon="true"
          hide-required-asterisk
        >
          <el-form-item label="用户名" :label-width="formLabelWidth">
            <el-input
              v-model="pasds.username"
              autocomplete="off"
              :disabled="true"
            ></el-input>
          </el-form-item>
          <el-form-item
            label="密码"
            :label-width="formLabelWidth"
            prop="password"
          >
            <el-input
              v-model="pasds.password"
              autocomplete="off"
              type="password"
            ></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="pasdclose">取 消</el-button>
          <el-button type="primary" @click="sure" :disabled="isDisable"
            >保存</el-button
          >
        </div>
      </el-dialog>
      <el-dialog
        title="授权"
        :visible.sync="dialogFormsVisible"
        :close-on-click-modal="false"
      >
        <el-form :model="form">
          <el-form-item label="用户名" :label-width="formLabelWidth">
            <el-input
              v-model="authname"
              autocomplete="off"
              :disabled="true"
            ></el-input>
          </el-form-item>
          <el-form-item label="所属角色" :label-width="formLabelWidth">
            <el-select
              v-model="authvalue"
              multiple
              filterable
              allow-create
              default-first-option
              style="width: 100%"
              :clearable="false"
              @change="pickid"
              placeholder="选择角色"
            >
              <el-option
                v-for="(item, index) in authors"
                :key="index"
                :label="item.name"
                :value="item.id"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormsVisible = false">取 消</el-button>
          <el-button type="primary" @click="author" :disabled="isDisable"
            >授权</el-button
          >
        </div>
      </el-dialog>
      <el-dialog
        title="添加用户"
        :visible.sync="dialogVisible"
        @close="close"
        :close-on-click-modal="false"
      >
        <el-form
          :model="addform"
          :rules="addRules"
          ref="addform"
          :status-icon="true"
          hide-required-asterisk
        >
          <el-form-item
            label="用户名"
            :label-width="formLabelWidth"
            prop="username"
          >
            <el-input v-model="addform.username" :maxlength="20"></el-input>
          </el-form-item>
          <el-form-item
            label="密码"
            :label-width="formLabelWidth"
            prop="password"
          >
            <el-input v-model="addform.password" type="password"></el-input>
          </el-form-item>
          <el-form-item label="姓名" :label-width="formLabelWidth" prop="name">
            <el-input
              v-model="addform.name"
              autocomplete="off"
              :maxlength="20"
            ></el-input>
          </el-form-item>
          <el-form-item label="手机号" :label-width="formLabelWidth">
            <el-input
              v-model="addform.phone"
              autocomplete="off"
              maxlength="11"
            ></el-input>
          </el-form-item>
          <el-form-item label="电子邮箱" :label-width="formLabelWidth">
            <el-input
              v-model="addform.email"
              autocomplete="off"
              :maxlength="50"
            ></el-input>
          </el-form-item>
          <el-form-item label="所属角色" :label-width="formLabelWidth">
            <el-select
              v-model="authors"
              multiple
              placeholder="请选择"
              style="width: 100%"
              @change="pickid(authvalue)"
            >
              <el-option
                v-for="item in roleList"
                :key="item.id"
                :label="item.name"
                :value="item.id"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="close">取 消</el-button>
          <el-button type="primary" @click="save" :disabled="isDisable"
            >保存</el-button
          >
        </div>
      </el-dialog>

      <el-dialog
        title="修改用户"
        :visible.sync="dialogVisibles"
        @close="exchangeClose"
        :close-on-click-modal="false"
      >
        <el-form
          :model="exchange"
          ref="exchange"
          :rules="exchangeRules"
          :status-icon="true"
          hide-required-asterisk
        >
          <el-form-item label="用户名" :label-width="formLabelWidth">
            <el-input
              v-model="exchange.username"
              :disabled="true"
              autocomplete="off"
            ></el-input>
          </el-form-item>
          <el-form-item label="姓名" :label-width="formLabelWidth" prop="name">
            <el-input
              v-model="exchange.name"
              autocomplete="off"
              maxlength="10"
            ></el-input>
          </el-form-item>
          <el-form-item label="手机号" :label-width="formLabelWidth">
            <el-input
              v-model="exchange.phone"
              autocomplete="off"
              maxlength="11"
            ></el-input>
          </el-form-item>
          <el-form-item label="电子邮箱" :label-width="formLabelWidth">
            <el-input
              v-model="exchange.email"
              autocomplete="off"
              maxlength="40"
            ></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="exchangeClose">取 消</el-button>
          <el-button type="primary" @click="keep" :disabled="isDisable"
            >保存</el-button
          >
        </div>
      </el-dialog>
      <div class="page">
        <el-pagination
          background
          layout="total, prev, pager, next"
          :page-size="form.pageSize"
          :total="total"
          :current-page="form.pageSize"
          @current-change="changepage"
        ></el-pagination>
      </div>
    </div>
  </div>
</template>

<script>
import { getAes } from "@/utils/auth";
import {
  query,
  add,
  getUserById,
  update,
  unlockAndLock,
  del,
  changePassword,
  authorization,
} from "@/api/sysuser";
import { list, queryRolesByUserId } from "@/api/role";
import { AESEncrypt } from "@/api/aes";
export default {
  data() {
    const validatePassword = (rule, value, callback) => {
      if (!/^(?![A-Z]+$)(?![a-z]+$)(?!\d+$)(?![\W_]+$)\S{6,20}$/.test(value)) {
        callback(new Error("请输入密码为6到20位且由数字大小写字母组成"));
      } else {
        callback();
      }
    };
    return {
      //加密后请求服务器的参数
      res: {
        requestData: "",
      },
      isDisable: false,
      isFlag: true,
      form: {
        username: "",
        roleId: "",
        pageNo: 1,
        pageSize: 10,
        sortField: "",
        sortRules: "",
      },
      //修改用户
      exchange: {
        username: "",
        name: "",
        phone: "",
        email: "",
        id: "",
      },
      exchangeRules: {
        name: [{ required: true, trigger: "blur" }],
      },
      //添加用户
      addform: {
        email: "",
        name: "",
        password: "",
        phone: "",
        username: "",
        idList: [],
      },
      addRules: {
        username: [
          { required: true, message: "请输入用户名", trigger: "blur" },
        ],
        password: [
          { required: true, trigger: "blur", validator: validatePassword },
        ],
        name: [{ required: true, message: "请输入姓名", trigger: "blur" }],
      },
      //修改密码
      pasds: {
        id: "",
        username: "",
        password: "",
      },
      pasdRules: {
        password: [
          { required: true, trigger: "blur", validator: validatePassword },
        ],
      },
      unlockAndLock: {
        id: "",
        status: "",
      },
      dialogFormVisible: false,
      dialogVisible: false,
      dialogFormsVisible: false,
      dialogVisibles: false,
      loading: false,
      isRepeat: false,
      roleList: [],
      tableData: [],
      formLabelWidth: "120px",
      multipleSelection: [],
      authname: "", //授权用户名
      authvalue: [], //授权所属角色
      authors: [], //所属角色数组
      grant: {
        userId: "",
        roleldList: [],
      },
      total: 0,
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
        query(this.res).then((res) => {
          this.tableData = res.voList;
          this.total = Number(res.total);
          this.loading = false;
          this.isRepeat = false;
        });
        list().then((res) => {
          this.roleList = res.voList;
        });
      }, 500);
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    //分页
    changepage(currentPage) {
      this.form.pageNo = currentPage;
      this.getList();
    },
    stateFormat(row) {
      if (row.status === "0") {
        return "锁定";
      } else if (row.status === "1") {
        return "正常";
      }
    },
    close() {
      this.$refs.addform.resetFields();
      this.authors = [];
      this.dialogVisible = false;
      this.addform.phone = "";
      this.addform.email = "";

      this.dialogFormsVisible = false;
    },
    pasdclose() {
      this.$refs.pasds.resetFields();
      this.dialogFormVisible = false;
    },
    exchangeClose() {
      this.$refs.exchange.resetFields();
      this.dialogVisibles = false;
      this.exchange.phone = "";
      this.exchange.email = "";
    },
    //信息提示框
    msgTip(msgType, msgInfo) {
      this.$message({
        type: msgType,
        message: msgInfo,
        center: true,
      });
    },
    //查询
    search() {
      this.getList();
    },

    //清空
    empty() {
      this.form.username = "";
      this.form.roleId = "";
      this.form.pageNo = 1;
      this.getList();
    },

    //修改用户
    amend(v) {
      this.isDisable = false;
      this.dialogVisibles = true;
      getUserById(v.id).then((res) => {
        this.exchange.username = res.username;
        this.exchange.name = res.name;
        this.exchange.phone = res.phone;
        this.exchange.email = res.email;
        this.exchange.id = res.id;
      });
    },
    keep() {
      if (this.exchange.phone != "") {
        if (!/^1(3|4|5|6|7|8|9)\d{9}$/.test(this.exchange.phone)) {
          this.msgTip("warning", "请输入正常的手机号");
          return;
        }
      }
      if (this.exchange.email != "") {
        if (
          !/^[A-Za-z\d]+([-_.][A-Za-z\d]+)*@([A-Za-z\d]+[-.])+[A-Za-z\d]{2,4}$/.test(
            this.exchange.email
          )
        ) {
          this.msgTip("warning", "请输入正常的邮箱");
          return;
        }
      }
      this.$refs.exchange.validate((valid) => {
        if (valid) {
          this.isRepeat = true;
          this.isDisable = true;
          //获取保存在cookie的AES密钥
          let aesKey = getAes();
          //进行参数加密,必须把对象转换json字符串，不然加密不了
          let dataJson = JSON.stringify(this.exchange);
          //数据进行加密
          this.res.requestData = AESEncrypt(dataJson, aesKey);
          update(this.exchange.id, this.res).then((res) => {
            if (res.success) {
              this.$message({
                type: "success",
                message: "修改成功",
                center: true,
              });
              this.dialogVisibles = false;
              this.getList();
            } else {
              this.$message({
                type: "warning",
                message: res.errorMsg,
                center: true,
              });
              this.isRepeat = false;
              this.isDisable = true;
            }
          });
        } else {
          this.msgTip("warning", "请填写姓名");
          return false;
        }
      });
    },

    //解锁
    unlock(v) {
      this.unlockAndLock.id = v.id;
      this.unlockAndLock.status = "1";
      //获取保存在cookie的AES密钥
      let aesKey = getAes();
      //进行参数加密,必须把对象转换json字符串，不然加密不了
      let dataJson = JSON.stringify(this.unlockAndLock);
      //数据进行加密
      this.res.requestData = AESEncrypt(dataJson, aesKey);
      unlockAndLock(this.res).then((res) => {
        if (res.success) {
          this.isRepeat = true;
          if (res.success) {
            this.$message({
              type: "success",
              message: "解锁成功",
              center: true,
            });
            this.form.status = "";
            this.getList();
          }
        } else {
          this.$message({
            type: "warning",
            message: res.errorMsg,
            center: true,
          });
          this.isRepeat = false;
        }
      });
    },

    lock(v) {
      this.unlockAndLock.id = v.id;
      this.unlockAndLock.status = "0";
      //获取保存在cookie的AES密钥
      let aesKey = getAes();
      //进行参数加密,必须把对象转换json字符串，不然加密不了
      let dataJson = JSON.stringify(this.unlockAndLock);
      //数据进行加密
      this.res.requestData = AESEncrypt(dataJson, aesKey);
      unlockAndLock(this.res).then((res) => {
        if (res.success) {
          this.isRepeat = true;
          if (res.success) {
            this.$message({
              type: "success",
              message: "锁定成功",
              center: true,
            });
            this.form.status = "";
            this.getList();
          }
        } else {
          this.$message({
            type: "warning",
            message: res.errorMsg,
            center: true,
          });
          this.isRepeat = false;
        }
      });
    },
    //授权
    accredit(v) {
      this.isDisable = false;
      this.grant.userId = "";
      this.dialogFormsVisible = true;
      queryRolesByUserId(v.id).then((res) => {
        this.authname = v.username;
        let obj = [];
        res.voList.map((res) => {
          obj.push(res.id);
        });
        this.authvalue = obj;
        this.grant.roleldList = obj;
      });
      this.authors = this.roleList;
      //获取所有用户权限
      this.grant.userId = v.id;
    },
    //查找id
    pickid(id) {
      this.grant.roleIdList = id;
    },
    //添加用户
    add() {
      this.isDisable = false;
      this.dialogVisible = true;
      this.addform.username = "";
      this.addform.password = "";
      this.authors = [];
    },
    save() {
      if (this.addform.phone != "") {
        if (!/^1(3|4|5|6|7|8|9)\d{9}$/.test(this.addform.phone)) {
          this.msgTip("warning", "请输入正常的手机号");
          return;
        }
      }
      if (this.addform.email != "") {
        if (
          !/^[A-Za-z\d]+([-_.][A-Za-z\d]+)*@([A-Za-z\d]+[-.])+[A-Za-z\d]{2,4}$/.test(
            this.addform.email
          )
        ) {
          this.msgTip("warning", "请输入正常的邮箱");
          return;
        }
      }
      if (this.authors.length == 0) {
        this.msgTip("warning", "请选择角色");
      } else {
        this.$refs.addform.validate((valid) => {
          if (valid) {
            this.isRepeat = true;
            this.isDisable = true;
            this.addform.idList = this.authors;
            //获取保存在cookie的AES密钥
            let aesKey = getAes();
            //进行参数加密,必须把对象转换json字符串，不然加密不了
            let dataJson = JSON.stringify(this.addform);
            //数据进行加密
            this.res.requestData = AESEncrypt(dataJson, aesKey);
            add(this.res).then((res) => {
              if (res.success) {
                this.msgTip("success", "添加成功");
                this.dialogVisible = false;
                this.empty();
              } else {
                this.msgTip("error", res.errorMsg);
                this.isRepeat = false;
                this.isDisable = false;
              }
            });
          } else {
            return false;
          }
        });
      }
    },
    author() {
      if (this.authvalue == null || this.authvalue == "") {
        this.$message({
          type: "warning",
          message: "一个用户至少有一个角色",
          center: true,
        });
        return;
      }
      this.isRepeat = true;
      this.isDisable = true;
      //获取保存在cookie的AES密钥
      let aesKey = getAes();
      //进行参数加密,必须把对象转换json字符串，不然加密不了
      let dataJson = JSON.stringify(this.grant);
      //数据进行加密
      this.res.requestData = AESEncrypt(dataJson, aesKey);
      authorization(this.res).then((res) => {
        if (res.success) {
          this.$message({
            type: "success",
            message: "授权成功",
            center: true,
          });
          this.dialogFormsVisible = false;
          this.getList();
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
    },
    //删除
    dele(v) {
      this.$confirm("您是否要删除当前选中的记录？", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        this.isRepeat = true;
        del(v.id).then((res) => {
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

    //修改密码
    pasd(v) {
      this.isDisable = false;
      this.dialogFormVisible = true;
      getUserById(v.id).then((res) => {
        this.pasds.username = res.username;
        this.pasds.id = res.id;
      });
    },
    sure() {
      this.$refs.pasds.validate((valid) => {
        if (valid) {
          this.isRepeat = true;
          this.isDisable = true;
          //获取保存在cookie的AES密钥
          let aesKey = getAes();
          //进行参数加密,必须把对象转换json字符串，不然加密不了
          let dataJson = JSON.stringify(this.pasds);
          //数据进行加密
          this.res.requestData = AESEncrypt(dataJson, aesKey);
          changePassword(this.res).then((res) => {
            if (res.success) {
              this.msgTip("success", "修改成功");
              this.dialogFormVisible = false;
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
  },
};
</script>

<style lang="scss" scoped>
.systemuser {
  width: 100%;
  min-height: 100vh;
  background-color: #f0f4f7;
  .box {
    width: 95%;
    margin: 0 auto;
  }
  .title-style {
    font-size: 22px;
    color: #000000;
    font-weight: bold;
  }
  .user-head {
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
  .item {
    margin-left: 20px;
  }
}
</style>

