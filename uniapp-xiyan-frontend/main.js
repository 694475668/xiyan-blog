import Vue from 'vue'
import App from './App'
import {
	request
} from 'util/request.js'
Vue.prototype.$request = request
//挂载生产环境接口地址
Vue.prototype.$baseURL = "https://xiyanit.cn/xiyan"

//挂载开发环境接口地址
//Vue.prototype.$baseURL = "http://127.0.0.1:9000"
//uview-ui
import uView from "uview-ui";
Vue.use(uView);

//路由守卫
import {
	router,
	RouterMount
} from './router.js'
Vue.use(router)

Vue.config.productionTip = false
App.mpType = 'app'

const app = new Vue({
	...App
})
//v1.3.5起 H5端 你应该去除原有的app.$mount();使用路由自带的渲染方式
// #ifdef H5
RouterMount(app, router, '#app')
// #endif

// #ifndef H5
app.$mount(); //为了兼容小程序及app端必须这样写才有效果
// #endif
