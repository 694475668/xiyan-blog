import request from '@/utils/request'

export function register(data) {
    return request({
        url: '/cms/user/register',
        method: 'post',
        data
    })
}