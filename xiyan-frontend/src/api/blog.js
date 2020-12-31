import request from '@/utils/request'
export function list(data) {
    return request({
        url: '/cms/article/list',
        method: 'post',
        data
    })
}

export function getArticleById(id) {
    return request({
        url: '/cms/article/byId/' + id,
        method: 'get',
    })
}
export function add(data) {
    return request({
        url: '/cms/article/add',
        method: 'post',
        data
    })
}
export function browse(id) {
    return request({
        url: '/cms/article/browse/' + id,
        method: 'get',
    })
}

export function like(id) {
    return request({
        url: '/cms/article/like/' + id,
        method: 'get',
    })
}

export function update(data, id) {
    return request({
        url: '/cms/article/update/' + id,
        method: 'put',
        data
    })
}