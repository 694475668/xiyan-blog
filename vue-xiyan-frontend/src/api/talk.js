import request from '@/utils/request'
export function list(data) {
    return request({
        url: '/web/talk/list',
        method: 'post',
        data
    })
}
export function update(data, id) {
    return request({
        url: '/web/talk/update/' + id,
        method: 'put',
        data
    })
}

export function add(data) {
    return request({
        url: '/web/talk/add',
        method: 'post',
        data
    })
}
export function getTalkById(id) {
    return request({
        url: '/web/talk/by/' + id,
        method: 'get',
    })
}