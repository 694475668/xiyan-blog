// 不重定向白名单 [路由守卫]
const whiteList = ['/login', '/', '/regist', '/articleList', '/articles/search', '/recover', '/codes', '/talk', '/tool', "/article", "/messageBoard", "/leaderboard", "/code", "/classroom", "/tv", "/recharge"]
export default ({ app, $cookies, store }) => {
    app.router.beforeEach((to, from, next) => {
        //服务端直接放行，只做客户端处理
        let isClient = process.client
        if (isClient) {
            if ($cookies.get("token")) {
                if (to.path === '/login') {
                    next({ path: '/' })
                } else {
                    next()
                }
            } else {
                if (whiteList.indexOf(to.path) !== -1) {
                    next()
                } else {
                    next('/login')
                }
            }
        }
        next()
    })
}