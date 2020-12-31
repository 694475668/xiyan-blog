import request from '@/utils/request'

export function list() {
    return request({
        url: '/cms/music/list',
        method: 'get'
    })
}