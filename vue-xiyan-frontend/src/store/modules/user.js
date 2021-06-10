import { login } from '@/api/login'
import { register } from '@/api/register'
import { getKey } from "@/api/key";
import { getToken, setToken, removeToken, setUserInfo, getUserInfo, removeUserInfo, setAesKey } from '@/utils/auth'


const user = {
    state: {
        token: getToken(),
        userInfo: getUserInfo(),
    },

    mutations: {
        SET_TOKEN: (state, token) => {
            state.token = token
        },
        SET_USERINFO: (state, userInfo) => {
            state.userInfo = userInfo
        },
    },

    actions: {
        // 登录
        Login({ commit }, requestData) {
            return new Promise((resolve, reject) => {
                login(requestData).then(response => {
                    const token = response.token
                    setToken(token)
                    setUserInfo(response.userVO)
                    commit('SET_TOKEN', token)
                    commit('SET_USERINFO', response.userVO)
                    resolve()
                }).catch(error => {
                    reject(error)
                })
            })
        },
        //盒子端登录
        webLogin({ commit }, data) {
            return new Promise((resolve, reject) => {
                setToken(data.token)
                setUserInfo(data.userVO)
                commit('SET_TOKEN', data.token)
                commit('SET_USERINFO', data.userVO)
                resolve()
            })
        },

        Register({ commit }, requestData) {
            return new Promise((resolve, reject) => {
                register(requestData).then(response => {
                    const token = response.token
                    setToken(token)
                    setUserInfo(response.userVO)
                    commit('SET_TOKEN', token)
                    commit('SET_USERINFO', response.userVO)
                    resolve()
                }).catch(error => {
                    reject(error)
                })
            })
        },

        // 登出
        LogOut({ commit, state }) {
            return new Promise((resolve, reject) => {
                commit('SET_TOKEN', '')
                commit('SET_USERINFO', '')
                removeToken()
                    //登出后重新拉取最新aes
                getKey().then((res) => {
                    setAesKey(res.key);
                });
                removeUserInfo()
                resolve()
            })
        },
    }
}

export default user