import request from '@/utils/request'

export function queryDictionaryByName(data) {
    return request({
        url: '/system/dictionary/by/name',
        method: 'post',
        data
    })
}