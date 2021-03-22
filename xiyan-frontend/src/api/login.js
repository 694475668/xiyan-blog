import request from '@/utils/request'

export function login(data) {
    return request({
        url: '/system/user/login',
        method: 'post',
        data
    })
}

export function qqLogin() {
    return request({
        url: '/system/qq/login',
        method: 'get',
    })
}