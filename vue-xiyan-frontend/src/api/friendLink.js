import request from '@/utils/request'
export function list() {
    return request({
        url: '/web/link/list',
        method: 'get',
    })
}