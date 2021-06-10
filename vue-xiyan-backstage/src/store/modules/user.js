import { login } from '@/api/login'
import { queryResourceByUserId } from '@/api/resource'
import { getToken, setToken, removeToken, setUserInfo, getUserInfo, removeUserInfo } from '@/utils/auth'


const user = {
    state: {
        token: getToken(),
        userInfo: getUserInfo(),
        name: '',
        avatar: '',
        roles: []
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
                    setUserInfo(response.userAuthVO)
                    commit('SET_TOKEN', token)
                    commit('SET_USERINFO', response.userAuthVO)
                    resolve()
                }).catch(error => {
                    reject(error)
                })
            })
        },

        // 获取用户资源权限
        GetUserResource({ commit, state }) {
            return new Promise((resolve, reject) => {
                queryResourceByUserId().then(response => {
                    resolve(response)
                }).catch(error => {
                    reject(error)
                })
            })
        },

        // 登出
        LogOut({ commit, state }) {
            return new Promise((resolve, reject) => {
                commit('SET_TOKEN', '')
                commit('SET_ROLES', [])
                commit('SET_USERINFO', '')
                removeToken()
                removeUserInfo()
                resolve()
            })
        },

        // 前端 登出
        FedLogOut({ commit }) {
            return new Promise(resolve => {
                commit('SET_TOKEN', '')
                commit('SET_USERINFO', '')
                removeToken()
                removeUserInfo()
                resolve()
            })
        }
    }
}

export default user