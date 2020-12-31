import request from '@/utils/request'

export function list(data) {
    return request({
        url: '/cms/search/list',
        method: 'post',
        data,
    })
}