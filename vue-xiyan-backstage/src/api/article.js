import request from '@/utils/request'

export function list(data) {
    return request({
        url: '/backstage/article/list',
        method: 'post',
        data
    })
}
//根据id查询
export function getArticleById(id) {
    return request({
        url: '/web/article/by/' + id,
        method: 'get',
    })
}
//添加
export function add(data) {
    return request({
        url: '/web/article/add',
        method: 'post',
        data
    })
}
//修改
export function update(data, id) {
    return request({
        url: '/web/article/update/' + id,
        method: 'put',
        data
    })
}
//删除
export function del(id) {
    return request({
        url: '/backstage/article/delete/' + id,
        method: 'delete',
    })
}

export function popular(data, id) {
    return request({
        url: '/backstage/article/update/' + id,
        method: 'put',
        data
    })
}
//审核
export function review(data, id) {
    return request({
        url: '/backstage/article/review/' + id,
        method: 'put',
        data
    })
}