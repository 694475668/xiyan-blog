import request from '@/utils/request'
export function list() {
    return request({
        url: '/cms/tv/list',
        method: 'get',
    })
}