<template>
  <div class="article-list-content">
    <Row>
      <Col :xs="24" :sm="24" :md="24" :lg="17">
        <div class="layout-left">
          <section-title :mainTitle="'排行榜'" :subTitle="'Leaderboard'">
          </section-title>
          <div style="height: 20px"></div>
          <div class="ques-section clearfix">
            <!-- 1 -->
            <div class="ques-section-item">
              <div class="ques-section-card">
                <div class="ques-card-head ques-card-gul">
                  <svg>
                    <defs>
                      <pattern
                        id="water"
                        width=".25"
                        height="1.1"
                        patternContentUnits="objectBoundingBox"
                      >
                        <path
                          fill="#fff"
                          d="M0.25,1H0c0,0,0-0.659,0-0.916c0.083-0.303,0.158,0.334,0.25,0C0.25,0.327,0.25,1,0.25,1z"
                        ></path>
                      </pattern>
                      <g id="eff">
                        <rect
                          fill="url(#water)"
                          x="-119.461"
                          y="0"
                          width="1200"
                          height="120"
                          opacity="0.3"
                        >
                          <animate
                            attributeType="xml"
                            attributeName="x"
                            from="-300"
                            to="0"
                            repeatCount="indefinite"
                            dur="10s"
                          ></animate>
                        </rect>
                        <rect
                          fill="url(#water)"
                          y="5"
                          width="1600"
                          height="125"
                          opacity="0.3"
                          x="-399.447"
                        >
                          <animate
                            attributeType="xml"
                            attributeName="x"
                            from="-400"
                            to="0"
                            repeatCount="indefinite"
                            dur="13s"
                          ></animate>
                        </rect>
                      </g>
                    </defs>
                    <use
                      xlink:href="#eff"
                      opacity="1"
                      style="mix-blend-mode: normal"
                    ></use>
                  </svg>
                </div>
                <div class="ques-card-title ques-card-title-top">
                  金币数排行榜
                </div>
                <ul class="ques-card-list">
                  <a
                    href="${ctxWeb }/user/${user1.id}.html"
                    target="_blank"
                    v-for="(item, index) in goldList"
                    :key="index"
                  >
                    <li
                      :class="
                        index == '0'
                          ? 'ques-card-list-1'
                          : index == '1'
                          ? 'ques-card-list-2'
                          : 'ques-card-list-3'
                      "
                    >
                      <div class="ques-list-box clearfix">
                        <div class="ques-list-head">
                          <div class="ques-list-image">
                            <img :src="item.photo" alt="" />
                          </div>
                        </div>
                        <div class="ques-list-name">
                          <div class="ques-list-name-head">
                            {{ item.name }}
                            <img
                              v-if="item.isMember == '1'"
                              style="margin-left: 5px"
                              src="../../assets/images/vip3.svg"
                            />
                          </div>
                          <div class="ques-list-name-text">
                            金币数: {{ item.count }}
                          </div>
                          <span class="font">{{ index + 1 }}</span>
                          <span
                            :id="
                              index == '0'
                                ? 'iconfontColor1'
                                : index == '1'
                                ? 'iconfontColor2'
                                : index == '2'
                                ? 'iconfontColor3'
                                : 'iconfontColor4'
                            "
                            class="iconfont icon-paihangbang2"
                          ></span>
                        </div>
                      </div>
                    </li>
                  </a>
                </ul>
              </div>
            </div>
            <!-- 2 -->
            <div class="ques-section-item">
              <div class="ques-section-card">
                <div class="ques-card-head ques-card-org">
                  <svg>
                    <use
                      xlink:href="#eff"
                      opacity="1"
                      style="mix-blend-mode: normal"
                    ></use>
                  </svg>
                </div>
                <div class="ques-card-title ques-card-title-top">
                  上传数排行榜
                </div>
                <ul class="ques-card-list">
                  <a
                    href="${ctxWeb }/user/${user1.id}.html"
                    v-for="(item, index) in uploadList"
                    :key="index"
                    target="_blank"
                  >
                    <li
                      :class="
                        index == '0'
                          ? 'ques-card-list-1'
                          : index == '1'
                          ? 'ques-card-list-2'
                          : 'ques-card-list-3'
                      "
                    >
                      <div class="ques-list-box clearfix">
                        <div class="ques-list-head">
                          <div class="ques-list-image">
                            <img :src="item.photo" alt="" />
                          </div>
                        </div>
                        <div class="ques-list-name">
                          <div class="ques-list-name-head">
                            {{ item.name }}
                            <img
                              v-if="item.isMember == '1'"
                              style="margin-left: 5px"
                              src="../../assets/images/vip3.svg"
                            />
                          </div>
                          <div class="ques-list-name-text">
                            上传数: {{ item.count }}
                          </div>
                          <span class="font">{{ index + 1 }}</span>
                          <span
                            :id="
                              index == '0'
                                ? 'iconfontColor1'
                                : index == '1'
                                ? 'iconfontColor2'
                                : index == '2'
                                ? 'iconfontColor3'
                                : 'iconfontColor4'
                            "
                            class="iconfont icon-paihangbang2"
                          ></span>
                        </div>
                      </div>
                    </li>
                  </a>
                </ul>
              </div>
            </div>
            <!-- 3 -->
            <div class="ques-section-item">
              <div class="ques-section-card">
                <div class="ques-card-head ques-card-bul">
                  <svg>
                    <use
                      xlink:href="#eff"
                      opacity="1"
                      style="mix-blend-mode: normal"
                    ></use>
                  </svg>
                </div>
                <div class="ques-card-title ques-card-title-top">
                  下载数排行榜
                </div>
                <ul class="ques-card-list">
                  <a
                    href="${ctxWeb }/user/${user1.id}.html"
                    v-for="(item, index) in downloadList"
                    target="_blank"
                  >
                    <li
                      :class="
                        index == '0'
                          ? 'ques-card-list-1'
                          : index == '1'
                          ? 'ques-card-list-2'
                          : 'ques-card-list-3'
                      "
                    >
                      <div class="ques-list-box clearfix">
                        <div class="ques-list-head">
                          <div class="ques-list-image">
                            <img :src="item.photo" alt="" />
                          </div>
                        </div>
                        <div class="ques-list-name">
                          <div class="ques-list-name-head">
                            {{ item.name }}
                            <img
                              v-if="item.isMember == '1'"
                              style="margin-left: 5px"
                              src="../../assets/images/vip3.svg"
                            />
                          </div>
                          <div class="ques-list-name-text">
                            下载数: {{ item.count }}
                          </div>
                          <span class="font">{{ index + 1 }}</span>
                          <span
                            :id="
                              index == '0'
                                ? 'iconfontColor1'
                                : index == '1'
                                ? 'iconfontColor2'
                                : index == '2'
                                ? 'iconfontColor3'
                                : 'iconfontColor4'
                            "
                            class="iconfont icon-paihangbang2"
                          ></span>
                        </div>
                      </div>
                    </li>
                  </a>
                </ul>
              </div>
            </div>

            <!-- 4 -->
            <div class="ques-section-item">
              <div class="ques-section-card">
                <div class="ques-card-head ques-card-green">
                  <svg>
                    <use
                      xlink:href="#eff"
                      opacity="1"
                      style="mix-blend-mode: normal"
                    ></use>
                  </svg>
                </div>
                <div class="ques-card-title ques-card-title-top">
                  收藏数排行榜
                </div>
                <ul class="ques-card-list">
                  <a
                    href="${ctxWeb }/user/${user1.id}.html"
                    v-for="(item, index) in favoritesList"
                    target="_blank"
                  >
                    <li
                      :class="
                        index == '0'
                          ? 'ques-card-list-1'
                          : index == '1'
                          ? 'ques-card-list-2'
                          : 'ques-card-list-3'
                      "
                    >
                      <div class="ques-list-box clearfix">
                        <div class="ques-list-head">
                          <div class="ques-list-image">
                            <img :src="item.photo" alt="" />
                          </div>
                        </div>
                        <div class="ques-list-name">
                          <div class="ques-list-name-head">
                            {{ item.name }}
                            <img
                              v-if="item.isMember == '1'"
                              style="margin-left: 5px"
                              src="../../assets/images/vip3.svg"
                            />
                          </div>
                          <div class="ques-list-name-text">
                            收藏数: {{ item.count }}
                          </div>
                          <span class="font">{{ index + 1 }}</span>
                          <span
                            :id="
                              index == '0'
                                ? 'iconfontColor1'
                                : index == '1'
                                ? 'iconfontColor2'
                                : index == '2'
                                ? 'iconfontColor3'
                                : 'iconfontColor4'
                            "
                            class="iconfont icon-paihangbang2"
                          ></span>
                        </div>
                      </div>
                    </li>
                  </a>
                </ul>
              </div>
            </div>
          </div>
        </div>
      </Col>
    </Row>
  </div>
</template>

<script>
import SectionTitle from "@/components/views/SectionTitle/SectionTitle";
import { gold, upload, favorites, download } from "@/api/leaderboard";
import { getAes } from "@/utils/auth";
export default {
  data() {
    return {
      goldList: [],
      uploadList: [],
      downloadList: [],
      favoritesList: [],
    };
  },
  created() {
    this.getList();
  },
  mounted() {
    //开启粒子特效
    document.getElementById("particles-js").style.display = "block";
  },
  methods: {
    getList() {
      //获取保存在cookie的AES密钥
      let aesKey = getAes();
      //为了处理aes还没有写入cookie就调接口了
      const timer = setInterval(() => {
        if (aesKey != undefined) {
          //金币排行榜
          gold().then((res) => {
            this.goldList = res.voList;
          });
          //上传排行榜
          upload().then((res) => {
            this.uploadList = res.voList;
          });
          //下载排行榜
          download().then((res) => {
            this.downloadList = res.voList;
          });
          //收藏排行榜
          favorites().then((res) => {
            this.favoritesList = res.voList;
          });
          clearInterval(timer);
          return;
        }
        aesKey = getAes();
      }, 50);
    },
  },
  components: {
    "section-title": SectionTitle,
  },
};
</script>

<style lang="stylus" scoped rel="stylesheet/stylus">
li {
  list-style: none;
}

/* 必要布局样式css */
.ques-section {
  width: 1300px;
}

.ques-section-item {
  width: 293px;
  position: relative;
  float: left;
}

.ques-section-card {
  position: relative;
  height: 860px;
  text-align: center;
  border: 1px solid #f2f2f2;
  border-radius: 2px;
}

.ques-card-head {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 30px;
  padding-top: 48px;
  border-radius: 2px 2px 0 0;
}

.ques-card-gul {
  background-color: #f55727;
  background-image: -webkit-radial-gradient(50% 3%, circle, #f07a56, #f55727);
  background-image: radial-gradient(circle at 50% 3%, #f07a56, #f55727);
  height: 78px;
}

.ques-card-org {
  background-color: #f59b00;
  background-image: -webkit-radial-gradient(50% -6%, circle, #ffb83d 2%, #f59b00);
  background-image: radial-gradient(circle at 50% -6%, #ffb83d 2%, #f59b00);
  height: 78px;
}

.ques-card-bul {
  background-color: #397bee;
  background-image: -webkit-radial-gradient(50% -45%, circle, #53a7f5, #397bee);
  background-image: radial-gradient(circle at 50% -45%, #53a7f5, #397bee);
  height: 78px;
}

.ques-card-green {
  background-color: green;
  height: 78px;
}

.ques-card-title {
  font-size: 18px;
  font-weight: 500;
  line-height: 24px;
  position: relative;
  padding-top: 15px;
  color: #fff;
}

.ques-section-item+.ques-section-item {
  margin-left: 20px;
}

.ques-card-title-top {
  padding: 27px 0;
}

.ques-card-list-1:before {
  position: absolute;
  z-index: 2;
  top: 6px;
  left: 37px;
  display: block;
  content: '';
  background-position: -115px -44px;
  width: 20px;
  height: 20px;
  background-image: url('../../assets/images/icon-body.png');
}

.ques-card-list-2:before {
  background-position: -115px -66px;
  width: 20px;
  height: 20px;
  position: absolute;
  z-index: 2;
  top: 6px;
  left: 37px;
  display: block;
  content: '';
  background-image: url('../../assets/images/icon-body.png');
}

.ques-card-list-3:before {
  position: absolute;
  z-index: 2;
  top: 6px;
  left: 37px;
  display: block;
  content: '';
  background-position: -115px -88px;
  width: 20px;
  height: 20px;
  background-image: url('../../assets/images/icon-body.png');
}

.ques-card-list {
  text-align: left;
}

.ques-card-list li {
  position: relative;
}

.ques-card-list li:after {
  position: absolute;
  bottom: 0;
  left: 10px;
  display: block;
  width: 280px;
  height: 1px;
  content: '';
  background-color: #ededed;
}

.ques-list-box {
  position: relative;
  display: block;
  height: 69px;
  padding: 16px 15px 17px;
}

.ques-list-head {
  position: relative;
  float: left;
  padding-right: 8px;
}

.ques-list-image {
  position: relative;
  display: block;
  width: 36px;
  height: 36px;
  z-index: 2;
  margin: 0 auto;
}

.ques-list-image:before {
  position: absolute;
  top: 0;
  left: 0;
  width: 36px;
  height: 36px;
  content: '';
  border: 2px solid #f87851;
  border-radius: 100%;
}

.ques-list-image img {
  border-radius: 100%;
  width: 100%;
  height: auto;
  display: block;
  border: none;
}

.ques-list-name {
  overflow: hidden;
  margin-right: 33px;
}

.ques-list-name-head {
  line-height: 18px;
  color: #333;
  display: flex;
  justify-content: flex-start;
  align-items: center;
}

.ques-list-name-text {
  font-size: 12px;
  line-height: 16px;
  margin-top: 2px;
  color: #a3a3a3;
}

.iconfont {
  font-size: 30px;
  position: absolute;
  top: 12px;
  right: 20px;
}

#iconfontColor1 {
  color: #FF6314;
}

#iconfontColor2 {
  color: #FFBD24;
}

#iconfontColor3 {
  color: #4C8DFC;
}

#iconfontColor4 {
  color: #CED2D7;
}

.font {
  position: absolute;
  top: 25px;
  right: 32px;
  color: #fff;
  z-index: 2;
}

.ques-list-name-icon {
  font-size: 12px;
  line-height: 24px;
  position: absolute;
  top: 50%;
  right: 14px;
  display: block;
  margin-top: -13px;
  text-align: center;
  color: #fff;
  background-position: 0 -60px;
  width: 25px;
  height: 25px;
  background-image: url('../../assets/images/icon-body.png');
}

.ques-list-box:hover {
  cursor: pointer;
  background-color: #f5f5f5;
}

.article-list-content {
  width: auto;
  min-height: calc(100vh - 308px);

  @media only screen and (max-width: 768px) {
    margin: 5px 5px 0 5px;

    .ques-section {
      width: 100%;
    }

    .ques-section-item, svg {
      margin: 0 auto;
      float: none;
      width: 90%;
    }

    svg {
      width: 100%;
    }
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
