import request from '@/utils/request'
export function list() {
    return request({
        url: '/cms/link/list',
        method: 'get',
    })
}