import request from '@/utils/request'
export function list() {
    return request({
        url: '/web/message/list',
        method: 'get',
    })
}

export function add(data) {
    return request({
        url: '/web/message/add',
        method: 'post',
        data
    })
}