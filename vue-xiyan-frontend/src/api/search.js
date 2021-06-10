import request from '@/utils/request'

export function list(data) {
    return request({
        url: '/search/technology/list',
        method: 'post',
        data,
    })
}