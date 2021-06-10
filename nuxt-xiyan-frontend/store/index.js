export const state = () => ({
    token: '',
    user: "",
})

export const mutations = {
    // 用户登录时，需要设置用户信息
    setUserInfo(state, loginInfo) {
        state.token = loginInfo.token
        state.user = loginInfo.user
    },
    // 用户登出时，需要删除用户信息
    removeUserInfo(state) {
        state.token = ''
        state.user = ''
    }
}

export const actions = {
    //nuxtServerInit方法在每次发送请求且请求未到达页面的时候都会被调用，可以借助这个方法来设置Vuex。[注意此方法只会在store/index.js 固定的，如果是其它文件将不会执行]
    async nuxtServerInit({ commit }, { req, $cookies, $axios }) {
        //设置aes密钥
        await $axios.post("/web/aes/key").then((res) => {
            $cookies.set("key", res.key);
        });



        //持久化store
        if (req.headers.cookie) {
            // 切分Cookie
            const cookie = req.headers.cookie.split(';')
                // 定义字符常量：需要从cookie中取出的值的名称
            const tk = 'token='
            const u = 'user='

            // 需要持久化的值
            let token = ''
            let user = ''

            // 遍历Cookie，取得需要的值
            cookie.forEach((e) => {
                if (e.includes(tk)) {
                    token = e.split(tk)[1]
                } else if (e.includes(u)) {
                    user = e.split(u)[1]
                }
            })

            // 提交mutation
            commit('setUserInfo', {
                token,
                user,
            })
        }
    }
}