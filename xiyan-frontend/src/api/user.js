import request from '@/utils/request'

//绑定
export function bind(data) {
    return request({
        url: '/system/user/bind',
        method: 'put',
        data
    })
}
//找回密码
export function retrievePassword(data) {
    return request({
        url: '/system/user/retrieve',
        method: 'put',
        data
    })
}

//发送验证码
export function send(data) {
    return request({
        url: '/system/user/send',
        method: 'post',
        data
    })
}