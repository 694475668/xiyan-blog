<template>
  <div class="article-list-content">
    <div class="box">
      <div class="channel">
        <Menu>
          <MenuItem :name="index" v-for="(item, index) in tvList" :key="index"
            ><span @click="cutTable(item)">{{ item.name }}</span></MenuItem
          >
        </Menu>
      </div>
      <div class="video" style="width: 100%">
        <video-player
          class="vjs-custom-skin"
          ref="videoPlayer"
          :options="playerOptions"
          :playsinline="true"
          customEventName="customstatechangedeventname"
        >
        </video-player>
        <div style="text-align: center; margin-top: 5px">
          <Tag
            type="border"
            style="font-size: 18px"
            v-if="remindFlag"
            closable
            color="warning"
            @on-close="remind"
          >
            温馨提醒：首次播放有点卡顿，放到20秒之后拉流正常就不会出现卡顿了</Tag
          >
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { list } from "@/api/tv";
import { videoPlayer } from "vue-video-player";
import "video.js/dist/video-js.css";
import "vue-video-player/src/custom-theme.css";
import { getAes } from "@/utils/auth";
import { AESEncrypt } from "@/api/aes";
import "videojs-flash";
export default {
  data() {
    return {
      tvList: [],
      remindFlag: true,
      playerOptions: {
        sources: [
          {
            type: "rtmp/mp4",
            src: "rtmp://58.200.131.2:1935/livetv/hunanhd",
          },
        ],
        techOrder: ["flash", "html5"],
        autoplay: true, // 自动播放
        // controls: true, // 控制条
        fluid: true, // 按比例缩放适应容器
        // preload: 'auto', // 预加载
        // muted: true, // 消除所有音频
        // loop: false, // 循环播放
        aspectRatio: "16:9",
        // poster: "http://qiniu-picture.xiyanyuanma.com/sfsdfdf.png", //首屏图片
      },
    };
  },
  created() {
    this.getList();
  },
  mounted() {
    //此页面不需要粒子特效
    document.getElementById("particles-js").style.display = "none";
    //设置左边的节目栏跟播放器一样的高度
    document.querySelector(".channel").style.height =
      document.querySelector(".video").offsetHeight + "px";
  },
  methods: {
    remind() {
      this.remindFlag = false;
    },
    getList() {
      //获取保存在cookie的AES密钥
      let aesKey = getAes();
      //为了处理aes还没有写入cookie就调接口了
      const timer = setInterval(() => {
        if (aesKey != undefined) {
          list().then((res) => {
            this.tvList = res.voList;
          });
          clearInterval(timer);
          return;
        }
        aesKey = getAes();
      }, 50);
    },
    //切台
    cutTable(item) {
      console.log(item);
      this.playerOptions.sources[0].src = item.url;
      this.playerOptions.sources[0].type = item.type;
    },
  },
  components: { videoPlayer },
};
</script>

<style lang="stylus" scoped rel="stylesheet/stylus">
.box {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  margin-top: 100px;
}

.channel {
  overflow-x: hidden;
  overflow-y: auto;
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
    width: 1300px;
    margin-top: 50px;
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