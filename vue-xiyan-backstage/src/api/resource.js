import request from '@/utils/request'
//用户所属资源列表
export function queryResourceByUserId() {
    return request({
        url: 'system/resource/by/list',
        method: 'get',
    })
}

//所有资源权限列表
export function list() {
    return request({
        url: 'system/resource/list',
        method: 'get',
    })
}

//添加
export function add(data) {
    return request({
        url: 'system/resource/add',
        method: 'post',
        data
    })
}

//修改
export function update(id, data) {
    return request({
        url: 'system/resource/update/' + id,
        method: 'put',
        data
    })
}

//删除
export function del(id) {
    return request({
        url: 'system/resource/delete/' + id,
        method: 'delete',
    })
}

//根据id查询
export function queryResourceById(id) {
    return request({
        url: 'system/resource/by/' + id,
        method: 'get',
    })
}