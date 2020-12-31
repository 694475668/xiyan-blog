import request from '@/utils/request'

export function list(data) {
    return request({
        url: '/cms/code/list',
        method: 'post',
        data,
    })
}


export function tagList() {
    return request({
        url: '/cms/code/tag/list',
        method: 'get',
    })
}
export function getCodeById(id) {
    return request({
        url: '/cms/code/byId/' + id,
        method: 'get',
    })
}

export function browse(id) {
    return request({
        url: '/cms/code/browse/' + id,
        method: 'get',
    })
}

export function codeLike(id) {
    return request({
        url: '/cms/code/like/' + id,
        method: 'get',
    })
}
export function update(data, id) {
    return request({
        url: '/cms/code/update/' + id,
        method: 'put',
        data
    })
}

export function add(data) {
    return request({
        url: '/cms/code/add',
        method: 'post',
        data
    })
}
export function download(data) {
    return request({
        url: '/cms/code/download',
        method: 'post',
        data
    })
}