import {
    AESDecrypt
} from '@/assets/js/aes'
export default function({ store, redirect, app: { $axios, $cookies } }) {

    // 数据访问前缀,指定配置的环境变量接口地址
    $axios.defaults.baseURL = process.env.baseUrl

    // request拦截器，我这里设置了一个token，当然你可以不要
    $axios.onRequest(config => {
        //请求超时时间 $cookies.get("token")
        config.timeout = 600000
        if ($cookies.get("token")) {
            //如果有toekn才携带请求头中的token到后端
            config.headers.common['x-access-token'] = $cookies.get("token")
        }
        //服务端请求数据不需要参数加密和响应加密
        if (!process.client) {
            config.headers.common['x-aes'] = '0';
        }
    })
    $axios.onError(error => {
            console.log(error.response);
            //如果客户端密钥已经失效或者token失效提示用户重新登录
            if (error.response.status === 678) {
                $cookies.remove("token")
                $cookies.remove("user")
                store.commit('removeUserInfo');
                return Promise.reject(error);
            }
            if (error.response.status === 401) {
                redirect("/login")
                return Promise.reject(error);
            }
            return Promise.reject(error.response)
        })
        // response拦截器，数据返回后，你可以先在这里进行一个简单的判断
    $axios.interceptors.response.use(response => {
        /**
         * code为非200是抛错 可结合自己业务进行修改
         */
        var res;
        //判断服务器返回是否是加密数据
        if (response.data.responseData != null && response.data.responseData != "") {
            //进行解密数据
            let aesDecrypt = AESDecrypt(response.data.responseData, $cookies.get("key"));
            //解密后转换成json
            res = JSON.parse(aesDecrypt);
        } else {
            //不是加密的数据正常返回
            res = response.data
        }
        return res
    })
}