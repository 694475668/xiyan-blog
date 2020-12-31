<template>
  <div class="friend-links">
    <panel :title="'友情链接'">
      <div class="link-list" slot="content">
        <div v-for="(link, index) in linkList" :key="link.id">
          <a :href="link.address" target="_blank">
            <div class="right">
              <Tag
                style="
                  display: flex;
                  align-items: center;
                  justify-content: center;
                "
                :color="index | mapTagColor"
                >{{ link.name }}</Tag
              >
            </div>
          </a>
        </div>
      </div>
    </panel>
  </div>
</template>

<script>
import Panel from "@/components/utils/Panel";
import { list } from "@/api/friendLink";
import { mixin } from "@/utils";
import { getAes } from "@/utils/auth";
export default {
  mixins: [mixin],
  data() {
    return {
      linkList: [],
    };
  },
  created() {
    this.getList();
  },
  components: {
    panel: Panel,
  },
  methods: {
    getList() {
      //获取保存在cookie的AES密钥
      let aesKey = getAes();
      //为了处理aes还没有写入cookie就调接口了
      const timer = setInterval(() => {
        if (aesKey != undefined) {
          list().then((res) => {
            this.linkList = res.voList;
          });
          clearInterval(timer);
          return;
        }
        aesKey = getAes();
      }, 50);
    },
  },
};
</script>

<style lang="stylus" scoped rel="stylesheet/stylus">
@import '../../common/stylus/theme.styl';

.friend-links {
  position: relative;

  div.link-list {
    div {
      display: inline-block;
      height: 45px;
      margin-right: 10px;

      a {
        float: left;
        display: flex;
        transform: translate(0, 0);
        transition: all 0.2s ease-in;

        &:hover {
          transform: translate(10px, 0);
          transition: all 0.2s ease-in-out;

          .link {
            color: $dark;
          }
        }

        .right {
          display: flex;
          flex-direction: column;
          padding-left: 12px;
          height: 30px;
          line-height: 60px;

          div {
            flex: 1;
            text-align: left;

            &.title {
              height: 35px;
              line-height: 45px;
              font-size: 17px;
              color: $color-main-primary;
            }

            &.link {
              height: 15px;
              line-height: 15px;
              font-weight: 100;
            }
          }
        }
      }
    }
  }
}
</style>
