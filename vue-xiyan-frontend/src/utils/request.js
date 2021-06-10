import axios from 'axios'
import {
    Message
} from 'view-design'
import store from '../store'
import {
    getToken,
    getAes,
} from '@/utils/auth'
import {
    AESDecrypt
} from '@/api/aes'
// 创建axios实例
const service = axios.create({
        baseURL: process.env.BASE_API, // api的base_url
        timeout: 600000 // 请求超时时间
    })
    // request拦截器
service.interceptors.request.use(config => {
    //获取登录成功后store中的token
    if (store.getters.token) {
        //如果有toekn才携带请求头中的token到后端
        config.headers['x-access-token'] = getToken() // 让每个请求携带自定义token 请根据实际情况自行修改
    }
    //兼容ie和苹果手机的浏览器，默认是不支持appliction/json方式
    config.headers['Content-type'] = "application/x-www-form-urlencoded"
    return config
}, error => {
    // Do something with request error
    console.log(error) // for debug
    Promise.reject(error)
})

// respone拦截器
service.interceptors.response.use(
    response => {
        /**
         * code为非200是抛错 可结合自己业务进行修改
         */
        var res;
        //判断服务器返回是否是加密数据
        if (response.data.responseData != null && response.data.responseData != "") {
            //进行解密数据
            let aesDecrypt = AESDecrypt(response.data.responseData, getAes());
            //解密后转换成json
            res = JSON.parse(aesDecrypt);
        } else {
            //不是加密的数据正常返回
            res = response.data
        }
        if (!res.success) {
            Message.error({
                content: res.errorMsg,
                duration: 3,
                top: 50,
                backgroun: true
            })
            return Promise.reject('error')
        } else {
            return res
        }
    },
    error => {
        //如果客户端密钥已经失效或者token失效提示用户重新登录
        if (error.response.status === 678) {
            store.dispatch('LogOut').then(() => {})
            return Promise.reject(error);
        }
        if (error.response.status === 401) {
            location.href = "/login"
            return Promise.reject(error);
        }
        console.log('err' + error) // for debug
        Message.error({
            content: error.message,
            duration: 3,
            top: 50,
            backgroun: true
        })
        return Promise.reject(error)
    }
)

export default service