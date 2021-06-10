import request from '@/utils/request'
//列表
export function query(data) {
    return request({
        url: '/system/user/list',
        method: 'post',
        data
    })
}

//添加用户
export function add(data) {
    return request({
        url: 'system/user/add',
        method: 'post',
        data,
    })
}
//根据id查询 
export function getUserById(id) {
    return request({
        url: 'system/user/by/' + id,
        method: 'get',
    })
}
//修改
export function update(id, data) {
    return request({
        url: 'system/user/update/' + id,
        method: 'put',
        data
    })
}
//解锁和锁定接口
export function unlockAndLock(data) {
    return request({
        url: 'system/user/operating',
        method: 'post',
        data
    })
}

//删除
export function del(id) {
    return request({
        url: 'system/user/delete/' + id,
        method: 'delete',
    })
}
//修改密码
export function changePassword(data) {
    return request({
        url: 'system/user/update/pwd',
        method: 'put',
        data
    })
}

export function authorization(data) {
    return request({
        url: 'system/user/authorization',
        method: 'post',
        data
    })
}