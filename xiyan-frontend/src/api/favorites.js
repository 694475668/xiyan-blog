import request from '@/utils/request'
export function insert(data) {
    return request({
        url: '/web/favorites/add',
        method: 'post',
        data
    })
}

export function del(data) {
    return request({
        url: '/web/favorites/delete',
        method: 'post',
        data
    })
}

export function query(data) {
    return request({
        url: '/web/favorites/query',
        method: 'post',
        data
    })
}