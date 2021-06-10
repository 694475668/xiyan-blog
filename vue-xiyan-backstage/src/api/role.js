import request from '@/utils/request'
export function list() {
    return request({
        url: '/system/role/list',
        method: 'get',
    })
}
//根据id查询
export function queryRolesById(id) {
    return request({
        url: '/system/role/by/' + id,
        method: 'get',
    })
}

//根据用户id查询角色
export function queryRolesByUserId(id) {
    return request({
        url: '/system/role/by/user/' + id,
        method: 'get',
    })
}

export function roleList(data) {
    return request({
        url: '/system/role/list',
        method: 'post',
        data
    })
}

//添加
export function add(data) {
    return request({
        url: 'system/role/add',
        method: 'post',
        data
    })
}
//删除
export function del(id) {
    return request({
        url: 'system/role/delete/' + id,
        method: 'delete',
    })
}
//修改
export function update(id, data) {
    return request({
        url: 'system/role/update/' + id,
        method: 'put',
        data
    })
}