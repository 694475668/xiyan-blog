import request from '@/utils/request'

export function register(data) {
    return request({
        url: '/auth/user/register',
        method: 'post',
        data
    })
}