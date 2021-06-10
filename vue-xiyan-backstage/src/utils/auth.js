import Cookies from 'js-cookie'

const TokenKey = 'loginToken'

const UserInfo = 'userInfo'
const aesKey = 'aesKey'

//获取token
export function getToken() {
    return Cookies.get(TokenKey)
}

//设置token
export function setToken(token) {
    return Cookies.set(TokenKey, token)
}

//删除token
export function removeToken() {
    return Cookies.remove(TokenKey)
}

//获取当前登录用户信息
export function getUserInfo() {
    return Cookies.get(UserInfo)
}
//设置当前登录用户信息
export function setUserInfo(token) {
    return Cookies.set(UserInfo, token)
}

//删除当前登录用户信息
export function removeUserInfo() {
    return Cookies.remove(UserInfo)
}

//设置aeskey
export function setAesKey(value) {
    return Cookies.set(aesKey, value)
}
//获取aeskey
export function getAes() {
    return Cookies.get(aesKey)
}