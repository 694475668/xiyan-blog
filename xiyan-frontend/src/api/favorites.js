import request from '@/utils/request'
export function insert(data) {
    return request({
        url: '/cms/favorites/add',
        method: 'post',
        data
    })
}

export function del(data) {
    return request({
        url: '/cms/favorites/delete',
        method: 'post',
        data
    })
}

export function query(data) {
    return request({
        url: '/cms/favorites/query',
        method: 'post',
        data
    })
}