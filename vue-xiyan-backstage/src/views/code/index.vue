<template>
  <div class="app-container">
    <div class="box">
      <el-card class="box-card" style="margin: 55px 0px 50px 0px">
        <div>
          <el-form :inline="true" :model="param" class="demo-form-inline">
            <el-form-item label="源码标题">
              <el-input v-model="param.title"></el-input>
            </el-form-item>
            <el-form-item label="审核状态">
              <el-select v-model="param.state" placeholder="请选择">
                <el-option
                  v-for="item in options"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                >
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item>
              <el-button
                type="primary"
                @click="search"
                @keyup.enter.native="search"
                >查询</el-button
              >
            </el-form-item>
            <el-form-item>
              <el-button @click="empty">清空</el-button>
            </el-form-item>
            <el-form-item>
              <el-button type="warning" @click="add">新增</el-button>
            </el-form-item>
          </el-form>
        </div>
      </el-card>
      <el-card class="box-card">
        <div slot="header" class="clearfix" style="margin-left: 1.5%">
          <span class="title-style">源码</span>
        </div>
        <el-table
          :data="list"
          style="width: 100%; margin-top: 30px"
          :header-cell-style="{ 'text-align': 'center' }"
          :cell-style="{ 'text-align': 'center' }"
          border
          v-loading="loading"
        >
          <el-table-column
            align="center"
            label="标题"
            show-overflow-tooltip
            prop="title"
          ></el-table-column>
          <el-table-column
            align="center"
            show-overflow-tooltip
            label="封面"
            prop="pic"
          >
          </el-table-column>
          <el-table-column
            align="center"
            label="标签"
            show-overflow-tooltip
            prop="tag"
          ></el-table-column>
          <el-table-column
            align="center"
            label="简介"
            show-overflow-tooltip
            prop="remark"
          ></el-table-column>
          <el-table-column
            align="center"
            label="内容"
            show-overflow-tooltip
            prop="content"
          ></el-table-column>
          <el-table-column
            align="center"
            label="发布人"
            prop="name"
          ></el-table-column>
          <el-table-column
            align="center"
            label="类型"
            :formatter="formatType"
            prop="type"
          ></el-table-column>
          <el-table-column
            align="center"
            label="下载金币"
            prop="downloadPoint"
          ></el-table-column>
          <el-table-column
            align="center"
            show-overflow-tooltip
            label="下载地址"
            prop="downloadUrl"
          ></el-table-column>
          <el-table-column
            align="center"
            label="阅读量"
            prop="readCount"
          ></el-table-column>
          <el-table-column
            align="center"
            label="点赞数"
            prop="starCount"
          ></el-table-column>
          <el-table-column
            align="center"
            label="下载数"
            prop="downloadCount"
          ></el-table-column>
          <el-table-column
            align="center"
            label="评论数"
            prop="conCount"
          ></el-table-column>
          <el-table-column align="center" label="精品">
            <template slot-scope="scope">
              <el-switch
                :value="scope.row.boutique == 1 ? true : false"
                :active-text="scope.row.boutique == 1 ? '是' : ''"
                :inactive-text="scope.row.boutique == 0 ? '否' : ''"
                active-color="#D52BB3"
                @change="switchChange(scope.row, 1)"
              >
              </el-switch>
            </template>
          </el-table-column>
          <el-table-column align="center" label="置顶">
            <template slot-scope="scope">
              <el-switch
                :value="scope.row.top == 1 ? true : false"
                :active-text="scope.row.top == 1 ? '是' : ''"
                :inactive-text="scope.row.top == 0 ? '否' : ''"
                active-color="#f4ea2a"
                @change="switchChange(scope.row, 2)"
              >
              </el-switch>
            </template>
          </el-table-column>
          <el-table-column align="center" label="推荐">
            <template slot-scope="scope">
              <el-switch
                :value="scope.row.recommend == 1 ? true : false"
                :active-text="scope.row.recommend == 1 ? '是' : ''"
                :inactive-text="scope.row.recommend == 0 ? '否' : ''"
                active-color="#1cedf2"
                @change="switchChange(scope.row, 3)"
              >
              </el-switch> </template
          ></el-table-column>
          <el-table-column align="center" label="官方">
            <template slot-scope="scope">
              <el-switch
                :value="scope.row.official == 1 ? true : false"
                :active-text="scope.row.official == 1 ? '是' : ''"
                :inactive-text="scope.row.official == 0 ? '否' : ''"
                active-color="#f28a1c"
                @change="switchChange(scope.row, 4)"
              >
              </el-switch> </template
          ></el-table-column>
          <el-table-column
            align="center"
            label="审核状态"
            prop="state"
            :formatter="formatState"
          ></el-table-column>
          <el-table-column
            align="center"
            label="创建时间"
            show-overflow-tooltip
            prop="createTime"
            :formatter="formatDate"
          ></el-table-column>
          <el-table-column align="center" label="操作" width="200px">
            <template slot-scope="scope">
              <div class="function-icon">
                <el-button
                  title="审核"
                  type="warning"
                  icon="el-icon-document-checked"
                  circle
                  @click="open(scope.row)"
                ></el-button>
                <el-button
                  title="修改"
                  type="success"
                  icon="el-icon-edit"
                  circle
                  @click="change(scope.row)"
                ></el-button>
                <el-button
                  title="删除"
                  type="primary"
                  icon="el-icon-delete"
                  circle
                  @click="dele(scope.row)"
                ></el-button>
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
      <el-dialog :visible.sync="dialogVisible" width="30%">
        <span>
          <el-select v-model="review.state" placeholder="请选择">
            <el-option
              v-for="item in reviewOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            >
            </el-option> </el-select
        ></span>
        <span slot="footer" class="dialog-footer">
          <el-button @click="dialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="revieState">确 定</el-button>
        </span>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import { list, del, popular, review } from "@/api/code";
import { AESEncrypt } from "@/api/aes";
import { getAes } from "@/utils/auth";
export default {
  data() {
    return {
      //加密后请求服务器的参数
      res: {
        requestData: "",
      },
      dialogVisible: false,
      loading: false,
      total: 0,
      list: [],
      param: {
        state: "",
        pageNo: 1,
        pageSize: 10,
        title: "",
        sortField: "create_time",
        sortRules: "",
      },
      popular: {
        boutique: "",
        top: "",
        recommend: "",
        official: "",
      },
      options: [
        {
          value: "0",
          label: "待审核",
        },
        {
          value: "1",
          label: "通过",
        },
        {
          value: "2",
          label: "不通过",
        },
      ],
      reviewOptions: [
        {
          value: "1",
          label: "通过",
        },
        {
          value: "2",
          label: "不通过",
        },
      ],
      review: {
        state: "",
        id: "",
      },
    };
  },
  created() {
    this.getList();
  },
  methods: {
    open(item) {
      this.review.state = "";
      this.dialogVisible = true;
      this.review.id = item.id;
    },
    revieState() {
      if (this.review.state == "") {
        this.$message({
          type: "warning",
          message: "审核状态不能为空",
          center: true,
        });
        return;
      }
      //获取保存在cookie的AES密钥
      let aesKey = getAes();
      //进行参数加密,必须把对象转换json字符串，不然加密不了
      let dataJson = JSON.stringify(this.review);
      //数据进行加密
      this.res.requestData = AESEncrypt(dataJson, aesKey);
      review(this.res, this.review.id).then((res) => {
        if (res.success) {
          this.$message({
            type: "success",
            message: "审核成功",
            center: true,
          });
          this.getList();
          this.dialogVisible = false;
        }
      });
    },
    formatType(item) {
      if (item.type == "0") {
        return "JAVA";
      } else if (item.type == "1") {
        return "Python";
      } else if (item.type == "2") {
        return "GO";
      } else if (item.type == "3") {
        return "PHP";
      } else if (item.type == "4") {
        return "VUE";
      } else if (item.type == "5") {
        return "JavaScript";
      } else if (item.type == "6") {
        return "C";
      } else if (item.type == "7") {
        return "C++";
      } else if (item.type == "8") {
        return "Linux";
      } else if (item.type == "9") {
        return "APP";
      } else if (item.type == "10") {
        return "其它";
      }
    },
    formatState(item) {
      if (item.state == "0") {
        return "待审核";
      } else if (item.state == "1") {
        return "通过";
      } else if (item.state == "2") {
        return "不通过";
      }
    },
    formatDate(item) {
      if (item.createTime != null) {
        let date = new Date(item.createTime);
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
    switchChange(item, id) {
      this.loading = true;
      //获取保存在cookie的AES密钥
      let aesKey = getAes();

      //根据id判断修改是好文,推荐，还是官方
      if (id == 1) {
        if (item.boutique == 1) {
          this.popular.boutique = "0";
        } else {
          this.popular.boutique = "1";
        }
        this.popular.recommend = item.recommend;
        this.popular.official = item.official;
        this.popular.top = item.top;
      }
      if (id == 2) {
        if (item.top == 1) {
          this.popular.top = "0";
        } else {
          this.popular.top = "1";
        }
        this.popular.recommend = item.recommend;
        this.popular.official = item.official;
        this.popular.boutique = item.boutique;
      }
      if (id == 3) {
        if (item.recommend == 1) {
          this.popular.recommend = "0";
        } else {
          this.popular.recommend = "1";
        }
        this.popular.boutique = item.boutique;
        this.popular.top = item.top;
        this.popular.official = item.official;
      }
      if (id == 4) {
        if (item.official == 1) {
          this.popular.official = "0";
        } else {
          this.popular.official = "1";
        }
        this.popular.recommend = item.recommend;
        this.popular.boutique = item.boutique;
        this.popular.top = item.top;
      }
      //进行参数加密,必须把对象转换json字符串，不然加密不了
      let dataJson = JSON.stringify(this.popular);
      //数据进行加密
      this.res.requestData = AESEncrypt(dataJson, aesKey);
      popular(this.res, item.id).then((res) => {
        if (res.success) {
          this.getList();
        }
      });
    },
    getList() {
      this.loading = true;
      setTimeout(() => {
        //获取保存在cookie的AES密钥
        let aesKey = getAes();
        //进行参数加密,必须把对象转换json字符串，不然加密不了
        let dataJson = JSON.stringify(this.param);
        //数据进行加密
        this.res.requestData = AESEncrypt(dataJson, aesKey);
        list(this.res).then((res) => {
          this.list = res.voList;
          this.total = res.total;
          this.loading = false;
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
      this.param.title = "";
      this.param.state = "";
      this.getList();
    },

    //修改
    change(v) {
      this.$router.push({
        path: "/code/publishProject",
        query: { id: v.id },
      });
    },
    //删除
    dele(v) {
      this.$confirm("您是否要删除当前选中的记录？", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
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
          }
        });
      });
    },

    add() {
      this.$router.push({ path: "/code/publishProject" });
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
