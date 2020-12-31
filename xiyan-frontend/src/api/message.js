import request from '@/utils/request'
export function list() {
    return request({
        url: '/cms/message/list',
        method: 'get',
    })
}

export function add(data) {
    return request({
        url: '/cms/message/add',
        method: 'post',
        data
    })
}