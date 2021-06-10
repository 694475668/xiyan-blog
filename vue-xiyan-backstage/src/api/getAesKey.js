import request from '@/utils/request'

//获取aes密钥
export function getAesKey() {
    return request({
        url: '/web/aes/key',
        method: 'post'
    })
}
//前端密钥失效验证
export function againAes(data) {
    return request({
        url: '/web/aes/again',
        method: 'post',
        data
    })
}