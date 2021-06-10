import request from '@/utils/request'

export function queryDictionaryByName(data) {
    return request({
        url: '/system/dictionary/by/name',
        method: 'post',
        data
    })
}

export function list(data) {
    return request({
        url: '/system/dictionary/list',
        method: 'post',
        data
    })
}

//根据id查询数据字典信息接口
export function getDictionaryById(id) {
    return request({
        url: 'system/dictionary/by/' + id,
        method: 'get',
    })
}

//添加数据字典信息接口
export function add(data) {
    return request({
        url: 'system/dictionary/add',
        method: 'post',
        data
    })
}

//修改数据字典信息接口
export function update(id, data) {
    return request({
        url: 'system/dictionary/update/' + id,
        method: 'put',
        data
    })
}

////删除数据字典信息接口
export function del(id) {
    return request({
        url: 'system/dictionary/delete/' + id,
        method: 'delete',
    })
}