import request from '@/utils/request'
export function list() {
    return request({
        url: '/web/tv/list',
        method: 'get',
    })
}