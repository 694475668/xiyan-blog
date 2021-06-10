import request from '@/utils/request'
export function list(data) {
    return request({
        url: '/web/comment/list',
        method: 'post',
        data
    })
}


export function add(data) {
    return request({
        url: '/web/comment/add',
        method: 'post',
        data
    })
}