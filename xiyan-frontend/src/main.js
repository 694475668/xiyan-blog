import Vue from 'vue'
import router from '@/router'
import store from '@/store'
import '@/common/stylus/index.styl'
import App from './App.vue'
import '@/icon/iconfont.js'
import '@/icon/iconfont.css'
//mavon-editor 富文本编辑器
import mavonEditor from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'
import VueParticles from 'vue-particles'
import APlayer from '@moefe/vue-aplayer'
import '@/permission'
//移动端手指滑动监控插件
import VueTouch from 'vue-touch'
//由于iview的走马灯不好设置移动端的滑动，所以我这里走马灯用的是elementui
import { Carousel, CarouselItem, Button, Input, Popover } from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css'
// iView UI 组件引入
import ViewUI from 'view-design';
import 'view-design/dist/styles/iview.css';

//引入jquery的爱心js
import "@/api/love";
//兼容ie
import 'babel-polyfill'
import hls from 'videojs-contrib-hls'

Vue.use(hls)

Vue.use(VueParticles)
Vue.use(ViewUI)
Vue.use(mavonEditor)
Vue.use(APlayer, {
    defaultCover: 'https://github.com/u3u.png',
    productionTip: true,
});
Vue.use(VueTouch, { name: 'v-touch' })
    //elementui的走马灯挂载组件
Vue.use(Carousel)
Vue.use(CarouselItem)
Vue.use(Button)
Vue.use(Input)
Vue.use(Popover)

new Vue({
    el: '#app',
    router,
    store,
    template: '<App/>',
    components: {
        App
    }
})