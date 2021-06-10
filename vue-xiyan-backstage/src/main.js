import Vue from 'vue'

// 移动端适配
import 'lib-flexible/flexible'

import 'normalize.css/normalize.css' // A modern alternative to CSS resets
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import 'element-ui/lib/theme-chalk/display.css';
import '@/styles/element-variables.scss'

//mavon-editor 富文本编辑器
import mavonEditor from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'

//自定义权限指令，通过v-permit="'/system/role/grant'"进行按钮鉴权
import { directive } from './utils/directive'
directive()

//引入jquery的爱心js
// import love from "@/api/love";

import '@/styles/index.scss' // global css

import './assets/icon/iconfont.css'
import App from './App'
import router from './router'
import store from './store'

import '@/icons' // icon
//登录拦截器
import '@/permission' // permission control


//挂载elementui语言包
Vue.use(ElementUI)
Vue.use(mavonEditor)
Vue.config.productionTip = false
new Vue({
    el: '#app',
    router,
    store,
    template: '<App/>',
    components: {
        App
    }
})