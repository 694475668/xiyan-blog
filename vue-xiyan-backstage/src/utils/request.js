import axios from 'axios'
import Cookies from "js-cookie";
import {
    Message,
} from 'element-ui'
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
    //由于在没有登录之前需要对后端提示做国际化,所以就去掉了token的验证,让每个请求携带语言标识
    config.headers['x-access-language'] = Cookies.get('locale')
        //兼容ie和苹果手机的浏览器，默认是不支持appliction/json方式
    config.headers['Content-type'] = "application/x-www-form-urlencoded"
        //用来标记是否需要授权校验
    config.headers['x-access-resource'] = "auth"
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
            //这里的意思除了就是状态码为E0707不需要它帮我打印出错误信息，我自己定义，其它都进行打印
            if (res.errorCode === "E0707" || res.errorCode === "E0706") {
                return res
            }
            //如果客户端密钥已经失效提示用户重新登录或者最新的密钥
            if (res.errorCode === "E0708" || res.errorCode === 'E0701') {
                store.dispatch('FedLogOut').then(() => {
                    location.reload() // 为了重新实例化vue-router对象 避免bug
                })
                return;
            }

            Message({
                message: res.errorMsg,
                type: 'error',
                duration: 3 * 1000
            })
            return Promise.reject('error')
        } else {
            return res
        }
    },
    error => {
        //如果客户端密钥已经失效或者token失效提示用户重新登录
        if (error.response.status === 678) {
            store.dispatch('FedLogOut').then(() => {
                location.reload() // 为了重新实例化vue-router对象 避免bug
            })
            return Promise.reject(error);
        }
        if (error.response.status == 401) {
            Message({
                message: "您没有该操作权限！！！",
                type: 'error',
                duration: 3 * 1000
            })
            return Promise.reject(error);
        }
        Message({
            message: error.message,
            type: 'error',
            duration: 3 * 1000
        })
        return Promise.reject(error)
    }
)

export default service