// router.js
import {
	RouterMount,
	createRouter
} from 'uni-simple-router';

const router = createRouter({
	platform: process.env.VUE_APP_PLATFORM,
	routes: [...ROUTES]
});
//全局路由前置守卫
//配置白名单
const whiteList = ['/', '/pages/home/home', '/pages/code/code', '/pages/blog/blog', '/pages/user/user',
	'/pages/classroom/classroom', '/pages/details/details', '/pages/user/login', '/pages/user/register',
	'/pages/user/recover', '/pages/user/contact', '/pages/search/search'
]
router.beforeEach((to, from, next) => {
	if (!uni.getStorageSync("token")) {
		if (whiteList.indexOf(to.path) == -1) {
			next('/pages/user/login')
		} else {
			next()
		}
	} else {
		next();
	}
});
// 全局路由后置守卫
router.afterEach((to, from) => {

})

export {
	router,
	RouterMount
}
