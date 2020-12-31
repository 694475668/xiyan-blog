import request from '@/utils/request'
export function list(data) {
    return request({
        url: '/cms/comment/list',
        method: 'post',
        data
    })
}


export function add(data) {
    return request({
        url: '/cms/comment/add',
        method: 'post',
        data
    })
}