import request from '@/utils/request'

//获取aes密钥
export function getKey() {
    return request({
        url: '/cms/aes/key',
        method: 'post'
    })
}