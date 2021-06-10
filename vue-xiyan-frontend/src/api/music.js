import request from '@/utils/request'

export function list() {
    return request({
        url: '/web/music/list',
        method: 'get'
    })
}