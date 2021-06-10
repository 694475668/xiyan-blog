import request from '@/utils/request'

//登录
export function login(data) {
    return request({
        url: '/system/user/login',
        method: 'post',
        data
    })
}