import request from '@/utils/request'

export function login(data) {
    return request({
        url: '/cms/user/login',
        method: 'post',
        data
    })
}

export function qqLogin() {
    return request({
        url: '/cms/user/login',
        method: 'get',
    })
}