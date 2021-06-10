import request from '@/utils/request'

export function login(data) {
    return request({
        url: '/auth/user/login',
        method: 'post',
        data
    })
}

export function qqLogin() {
    return request({
        url: '/auth/qq/login',
        method: 'get',
    })
}