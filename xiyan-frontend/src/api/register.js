import request from '@/utils/request'

export function register(data) {
    return request({
        url: '/system/user/register',
        method: 'post',
        data
    })
}