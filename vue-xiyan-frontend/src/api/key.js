import request from '@/utils/request'

//获取aes密钥
export function getKey() {
    return request({
        url: '/web/aes/key',
        method: 'post'
    })
}