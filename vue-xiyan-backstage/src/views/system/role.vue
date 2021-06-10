<template>
  <div class="app-container">
    <div class="box">
      <el-card class="box-card" style="margin: 55px 0px 50px 0px">
        <h5>角色名称</h5>
        <div class="head">
          <el-input class="name" v-model="param.name"></el-input>
          <el-button
            type="primary"
            style="margin-left: 20px"
            @click="search"
            @keyup.enter.native="search"
            >查询</el-button
          >
          <el-button @click="empty">清空</el-button>
          <el-button type="warning" @click="handleAddRole">新增角色</el-button>
        </div>
      </el-card>
      <el-card class="box-card">
        <div slot="header" class="clearfix" style="margin-left: 1.5%">
          <span class="title-style">角色</span>
        </div>
        <el-table
          :data="rolesLists"
          style="width: 100%; margin-top: 30px"
          :header-cell-style="{ 'text-align': 'center' }"
          :cell-style="{ 'text-align': 'center' }"
          border
          v-loading="loading"
        >
          <el-table-column
            align="center"
            label="角色名称"
            prop="name"
          ></el-table-column>
          <el-table-column
            align="center"
            show-overflow-tooltip
            label="拥有资源"
          >
            <template slot-scope="scope">
              <span
                class="resource"
                v-for="(item, index) in scope.row.resourceList"
                :key="index"
                >{{ item.name }},</span
              >
            </template>
          </el-table-column>
          <el-table-column
            align="center"
            label="备注"
            prop="remark"
          ></el-table-column>
          <el-table-column align="center" label="操作" width="200px">
            <template slot-scope="scope">
              <div class="function-icon">
                <el-tooltip
                  class="item"
                  effect="dark"
                  content="修改"
                  placement="bottom"
                >
                  <img
                    @click="change(scope.row, false)"
                    src="../../assets/iconfont/amend.png"
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
                    @click="accredit(scope.row, true)"
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
              </div>
            </template>
          </el-table-column>
        </el-table>
      </el-card>
      <div class="page">
        <el-pagination
          background
          layout="total, prev, pager, next"
          :page-size="param.pageSize"
          :total="total"
          :current-page="param.pageNo"
          @current-change="changepage"
        ></el-pagination>
      </div>
      <el-dialog
        :visible.sync="dialogVisible"
        :title="dialogType === 'edit' ? 'Edit Role' : '新增角色'"
        @close="close"
        :close-on-click-modal="false"
      >
        <el-form
          :model="role"
          label-width="80px"
          ref="role"
          label-position="left"
        >
          <el-form-item label="角色名称">
            <el-input maxlength="25" v-model="role.name" type="text" />
          </el-form-item>
          <el-form-item label="备注">
            <el-input
              maxlength="100"
              v-model="role.remark"
              :autosize="{ minRows: 2, maxRows: 4 }"
              type="textarea"
            />
          </el-form-item>
          <div style="overflow-y: auto; height: 450px">
            <el-form-item label="权限">
              <el-tree
                ref="tree"
                :data="routesData"
                :props="defaultProps"
                show-checkbox
                node-key="id"
                class="permission-tree"
                @check-change="handleCheckChange"
              ></el-tree>
            </el-form-item>
          </div>
        </el-form>
        <div style="text-align: right">
          <el-button @click="close">取消</el-button>
          <el-button type="primary" @click="confirmRole" :disabled="isDisable"
            >保存</el-button
          >
        </div>
      </el-dialog>

      <el-dialog
        title="修改角色"
        :visible.sync="dialogFormVisible"
        @close="close"
        :close-on-click-modal="false"
      >
        <el-form :model="form">
          <el-form-item label="角色名称" :label-width="formLabelWidth">
            <el-input
              maxlength="25"
              v-model="form.name"
              autocomplete="off"
            ></el-input>
          </el-form-item>
          <el-form-item label="备注" :label-width="formLabelWidth">
            <el-input
              maxlength="100"
              v-model="form.remark"
              autocomplete="off"
            ></el-input>
          </el-form-item>
          <el-form-item label="所属角色" :label-width="formLabelWidth">
            <div style="overflow-y: auto; height: 440px">
              <el-tree
                ref="tree"
                :data="routesData"
                :props="defaultProps"
                show-checkbox
                node-key="id"
                class="permission-tree"
                :default-expanded-keys="idList"
                :default-checked-keys="idList"
                @check-change="getChecked"
              ></el-tree>
            </div>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="close">取 消</el-button>
          <el-button type="primary" @click="sure" :disabled="isDisable"
            >确 定</el-button
          >
        </div>
      </el-dialog>

      <el-dialog
        title="授权"
        :visible.sync="dialogFormsVisible"
        @close="close"
        :close-on-click-modal="false"
      >
        <div style="overflow-y: auto; height: 500px">
          <el-tree
            ref="accreditTree"
            :data="routesData"
            :props="defaultProps"
            show-checkbox
            node-key="id"
            class="permission-tree"
            :default-expanded-keys="idList"
            :default-checked-keys="idList"
            @check-change="accreditCheck"
          ></el-tree>
        </div>
        <div slot="footer" class="dialog-footer">
          <el-button @click="close">取 消</el-button>
          <el-button type="primary" @click="keep" :disabled="isDisable"
            >保存</el-button
          >
        </div>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import { roleList, add, del, queryRolesById, update } from "@/api/role";
import { queryResourceByUserId } from "@/api/resource";

import { AESEncrypt } from "@/api/aes";
import { getAes } from "@/utils/auth";
export default {
  data() {
    return {
      //加密后请求服务器的参数
      res: {
        requestData: "",
      },
      isDisable: false,
      role: {},
      dialogVisible: false,
      dialogFormVisible: false,
      dialogType: "new",
      loading: false,
      isRepeat: false,
      defaultProps: {
        id: "id",
        children: "children",
        label: "label",
      },
      roleId: "",
      rolesLists: [],
      param: {
        pageNo: 1,
        pageSize: 10,
        name: "",
        sortField: "",
        sortRules: "",
      },
      form: {
        name: "",
        remark: "",
        idList: [],
      },
      routesData: [],
      formLabelWidth: "100px",
      arr: [],
      arr1: [],
      arr2: [],
      total: 0,
      //授权
      dialogFormsVisible: false,
      idList: [],
    };
  },
  created() {
    this.getList();
  },
  methods: {
    getList() {
      this.loading = true;
      setTimeout(() => {
        //获取保存在cookie的AES密钥
        let aesKey = getAes();
        //进行参数加密,必须把对象转换json字符串，不然加密不了
        let dataJson = JSON.stringify(this.param);
        //数据进行加密
        this.res.requestData = AESEncrypt(dataJson, aesKey);
        roleList(this.res).then((res) => {
          this.rolesLists = res.voList;
          this.total = res.total;
          this.loading = false;
          this.isRepeat = false;
        });
      }, 500);
    },

    //分页
    changepage(currentPage) {
      this.param.pageNo = currentPage;
      this.getList();
    },
    //查询角色
    search() {
      this.getList();
    },

    //清空
    empty() {
      this.param.name = "";
      this.getList();
    },

    //关闭事件
    close() {
      this.role = {};
      this.dialogVisible = false;
      this.dialogFormVisible = false;
      this.dialogFormsVisible = false;
      this.form.name = "";
      this.form.remark = "";
      this.idList = [];
    },
    getChecked(val) {
      this.arr1 = this.$refs.tree.getCheckedKeys();
      this.arr2 = this.$refs.tree.getHalfCheckedKeys();
      this.arr = this.arr1.concat(this.arr2);
    },
    //修改
    change(v, flag) {
      this.isDisable = false;
      queryResourceByUserId().then((res) => {
        this.routesData = res.voList.map((item) => {
          item.label = item.label;
          return item;
        });
      });
      v.resourceList.map((res) => {
        this.arr.push(res.id);
      });
      queryRolesById(v.id).then((res) => {
        res.resourceList.map((res) => {
          this.idList.push(res.id);
        });
        this.form.name = res.name;
        this.form.remark = res.remark;
      });
      setTimeout(() => {
        //false修改 ，true授权
        if (flag) {
          this.dialogFormsVisible = true;
        } else {
          this.dialogFormVisible = true;
        }
      }, 500);
      this.roleId = v.id;
    },
    sure() {
      let getCheck = this.$refs.tree.getCheckedKeys();
      if (getCheck == "" || getCheck == null) {
        this.$message({
          type: "error",
          message: "角色至少拥有一条资源权限",
          center: true,
        });
        return;
      }
      this.isRepeat = true;
      this.isDisable = true;
      this.form.idList = this.arr;
      //获取保存在cookie的AES密钥
      let aesKey = getAes();
      //进行参数加密,必须把对象转换json字符串，不然加密不了
      let dataJson = JSON.stringify(this.form);
      //数据进行加密
      this.res.requestData = AESEncrypt(dataJson, aesKey);
      update(this.roleId, this.res).then((res) => {
        if (res.success) {
          this.$message({
            type: "success",
            message: "操作成功",
            center: true,
          });
          this.dialogFormVisible = false;
          this.dialogFormsVisible = false;
          this.getList();
          this.idList = [];
          this.arr = [];
        } else {
          this.$message({
            type: "error",
            message: res.errorMsg,
            center: true,
          });
          this.isRepeat = false;
          this.isDisable = false;
        }
      });
    },
    accreditCheck() {
      this.arr1 = this.$refs.accreditTree.getCheckedKeys();
      this.arr2 = this.$refs.accreditTree.getHalfCheckedKeys();
      this.arr = this.arr1.concat(this.arr2);
    },
    //授权
    accredit(v, flag) {
      this.change(v, flag);
    },
    keep() {
      this.sure();
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

    //新增角色
    handleAddRole() {
      this.isDisable = false;
      this.dialogVisible = true;
      queryResourceByUserId().then((res) => {
        this.routesData = res.voList.map((item) => {
          item.label = item.label;
          return item;
        });
      });
    },
    handleCheckChange(data) {
      this.arr1 = this.$refs.tree.getCheckedKeys();
      this.arr2 = this.$refs.tree.getHalfCheckedKeys();
      this.arr = this.arr1.concat(this.arr2);
      console.log(this.arr, "");
    },
    async confirmRole() {
      if (this.role.name === undefined) {
        this.$message({
          type: "warning",
          message: "请填写角色名称",
          center: true,
        });
      } else if (this.arr.length == 0) {
        this.$message({
          type: "warning",
          message: "请选择权限",
          center: true,
        });
      } else {
        this.isDisable = true;
        this.isRepeat = true;
        this.role.idList = this.arr;
        //获取保存在cookie的AES密钥
        let aesKey = getAes();
        //进行参数加密,必须把对象转换json字符串，不然加密不了
        let dataJson = JSON.stringify(this.role);
        //数据进行加密
        this.res.requestData = AESEncrypt(dataJson, aesKey);
        add(this.res).then((res) => {
          if (res.success) {
            this.$message({
              type: "success",
              message: "添加成功",
              center: true,
            });
            this.param.name = "";
            this.role.name = "";
            this.role.remark = "";
            this.dialogVisible = false;
            this.getList();
          } else {
            this.$message({
              type: "error",
              message: res.errorMsg,
              center: true,
            });
            this.isRepeat = false;
            this.isDisable = false;
          }
        });
      }
    },
  },
};
</script>

<style lang="scss" scoped>
.app-container {
  width: 100%;
  min-height: 100vh;
  background-color: #f0f4f7;
  .box {
    width: 97%;
    margin: 0 auto;
    .title-style {
      font-size: 22px;
      color: #000000;
      font-weight: bold;
    }
  }
  .head {
    display: flex;
    .name {
      width: 250px;
    }
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
    margin-left: 35px;
  }
}
</style>
