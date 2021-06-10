<template>
  <div class="article-list-content">
    <Row>
      <Col :xs="24" :sm="24" :md="24" :lg="17">
        <div class="layout-left">
          <section-title
            :mainTitle="'说说交流'"
            :btnFlag="true"
            :tipText="'交流'"
            :tipHref="'/postExchange'"
          >
          </section-title>
          <Tabs @on-click="contextmenu" :value="queryParam.type">
            <TabPane
              v-for="(item, index) in dictionaryList"
              :label="item.meaning"
              :key="index"
              :name="item.value"
              :icon="
                index == 0
                  ? 'logo-octocat'
                  : index == 1
                  ? 'ios-megaphone'
                  : 'ios-leaf'
              "
            >
              <a
                v-for="(talkItem, idx) in talkList"
                :key="idx"
                @click="view(talkItem.id)"
              >
                <Card style="margin: 10px 0px 20px 0px">
                  <div class="talk">
                    <p class="phono">
                      <Avatar :src="talkItem.photo" size="large" />
                    </p>
                    <div class="body">
                      <p class="head">
                        <Tag :color="idx | mapTagColor">{{ item.meaning }}</Tag>
                        <strong
                          style="
                            font-family: PingFangSC-Regular;
                            font-size: 16px;
                          "
                        >
                          {{ talkItem.title }}
                        </strong>
                      </p>
                      <div class="user">
                        <p style="color: #999">
                          <span>{{ talkItem.name }}</span>
                          <span style="margin-left: 10px">{{
                            talkItem.createTime | formatDate
                          }}</span>
                        </p>
                        <p class="live-for like">
                          <span
                            class="iconfont icon-liulan"
                            style="color: #d4237a"
                            ><b style="margin-left: 5px">{{
                              talkItem.readCount
                            }}</b></span
                          >
                          <span
                            style="margin-left: 5px; color: #3cc2a8"
                            class="iconfont icon-pinglun_huabanfuben"
                            ><b style="margin-left: 5px">{{
                              talkItem.conCount
                            }}</b></span
                          >
                        </p>
                      </div>
                    </div>
                  </div>
                </Card>
              </a>
            </TabPane>
          </Tabs>
          <Page
            class="mt-10 text-right"
            :total="total"
            :current="queryParam.pageNo"
            :page-size="queryParam.pageSize"
            @on-change="changePage"
            @on-page-size-change="changeSize"
            :show-elevator="isShow"
            :show-total="isShow"
            :show-sizer="isShow"
            :page-size-opts="[5, 10, 15, 20]"
          />
        </div>
      </Col>
      <Col :xs="0" :sm="0" :md="0" :lg="7">
        <div class="layout-right">
          <recommend></recommend>
          <tag-wall style="margin-top: 15px"></tag-wall>
          <friend-links style="margin-top: 15px"></friend-links>
        </div>
      </Col>
    </Row>
  </div>
</template>

<script>
import Recommend from "@/components/views/Recommend";
import TagWall from "@/components/views/TagWall";
import SectionTitle from "@/components/views/SectionTitle/SectionTitle";
import { DefaultFilterList } from "@/assets/js/const";
import FriendLinks from "@/components/views/FriendLinks";
import { queryDictionaryByName } from "@/api/dictionary";
import { list } from "@/api/talk";
import { getAes } from "@/utils/auth";
import { AESEncrypt } from "@/api/aes";
import { mixin } from "@/utils";
export default {
  data() {
    return {
      //加密后请求服务器的参数
      res: {
        requestData: "",
      },
      talkList: [],
      dictionaryList: [],
      total: 1,
      isShow: true,
      defaultFilterList: DefaultFilterList,
      param: {
        name: "TALK-TYPE",
      },
      queryParam: {
        type: "1",
        pageNo: 1,
        pageSize: 10,
      },
    };
  },
  filters: {
    formatDate(value) {
      if (value != null) {
        let date = new Date(value);
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
  },
  mixins: [mixin],
  created() {
    if (document.body.offsetWidth <= 678) {
      this.isShow = false;
    }
    this.queryDictionaryByName();
  },
  methods: {
    changePage(page) {
      this.queryParam.pageNo = page;
      this.getList();
    },
    changeSize(size) {
      this.queryParam.pageSize = size;
      this.queryParam.pageNo = 1;
      this.getList();
    },
    queryDictionaryByName() {
      //获取保存在cookie的AES密钥
      let aesKey = getAes();
      //进行参数加密,必须把对象转换json字符串，不然加密不了
      let dataJson = JSON.stringify(this.param);
      //数据进行加密
      this.res.requestData = AESEncrypt(dataJson, aesKey);
      queryDictionaryByName(this.res).then((res) => {
        this.dictionaryList = res.voList;
        console.log(this.dictionaryList);
        this.getList();
      });
    },
    //点击标签事件
    contextmenu(value) {
      this.queryParam.type = value;
      this.getList();
    },
    view(id) {
      alert("开发中");
    },
    getList() {
      //获取保存在cookie的AES密钥
      let aesKey = getAes();
      //进行参数加密,必须把对象转换json字符串，不然加密不了
      let dataJson = JSON.stringify(this.queryParam);
      console.log(this.queryParam);
      //数据进行加密
      this.res.requestData = AESEncrypt(dataJson, aesKey);
      list(this.res).then((res) => {
        this.talkList = res.voList;
        this.total = res.total;
      });
    },
  },
  components: {
    recommend: Recommend,
    "tag-wall": TagWall,
    "section-title": SectionTitle,
    "friend-links": FriendLinks,
  },
};
</script>

<style lang="stylus" scoped rel="stylesheet/stylus">
.ivu-modal-footer {
  display: none !important;
}

.ivu-avatar-large {
  width: 50px;
  height: 50px;
  line-height: 50px;
}

.body {
  margin-left: 10px;
}

.user {
  margin-top: 5px;
}

.talk {
  display: flex;
  justify-content: flex-start;
  align-items: center;
}

.like {
  position: absolute;
  bottom: 15px;
  right: 20px;
}

.article-list-content {
  width: auto;
  min-height: calc(100vh - 308px);

  @media only screen and (max-width: 768px) {
    margin: 5px 5px 0 5px;
  }

  @media screen and (min-width: 768px) {
    margin: 10px 10px 0 10px;
  }

  @media screen and (min-width: 992px) {
    margin: 15px 35px 0 35px;
  }

  @media screen and (min-width: 1200px) {
    width: 1200px;
    margin: 15px auto 0;
    margin-bottom: 200px;
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
