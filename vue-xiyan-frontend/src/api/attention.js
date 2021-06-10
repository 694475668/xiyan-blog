import request from '@/utils/request'
export function add(data) {
    return request({
        url: '/web/attention/add',
        method: 'post',
        data
    })
}

export function del(data) {
    return request({
        url: '/web/attention/delete',
        method: 'post',
        data
    })
}

export function query(data) {
    return request({
        url: '/web/attention/query',
        method: 'post',
        data
    })
}