import request from '@/utils/request'

export function list(data) {
    return request({
        url: '/system/black/list',
        method: 'post',
        data
    })
}

//根据id查询信息接口
export function getBlackListById(id) {
    return request({
        url: 'system/black/by/' + id,
        method: 'get',
    })
}

//添加接口
export function add(data) {
    return request({
        url: 'system/black/add',
        method: 'post',
        data
    })
}

//修改接口
export function update(id, data) {
    return request({
        url: 'system/black/update/' + id,
        method: 'put',
        data
    })
}

////删除接口
export function del(id) {
    return request({
        url: 'system/black/delete/' + id,
        method: 'delete',
    })
}