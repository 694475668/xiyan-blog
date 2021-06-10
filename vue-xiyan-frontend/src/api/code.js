import request from '@/utils/request'

export function list(data) {
    return request({
        url: '/web/code/list',
        method: 'post',
        data,
    })
}


export function tagList() {
    return request({
        url: '/web/code/tag/list',
        method: 'get',
    })
}
export function getCodeById(id) {
    return request({
        url: '/web/code/by/' + id,
        method: 'get',
    })
}

export function browse(id) {
    return request({
        url: '/web/code/browse/' + id,
        method: 'get',
    })
}

export function codeLike(id) {
    return request({
        url: '/web/code/like/' + id,
        method: 'get',
    })
}
export function update(data, id) {
    return request({
        url: '/web/code/update/' + id,
        method: 'put',
        data
    })
}

export function add(data) {
    return request({
        url: '/web/code/add',
        method: 'post',
        data
    })
}
export function download(data) {
    return request({
        url: '/web/code/download',
        method: 'post',
        data
    })
}