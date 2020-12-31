import request from '@/utils/request'

//排行榜
export function gold() {
    return request({
        url: '/cms/leaderboard/gold',
        method: 'get',
    })
}
export function upload() {
    return request({
        url: '/cms/leaderboard/upload',
        method: 'get',
    })
}
export function download() {
    return request({
        url: '/cms/leaderboard/download',
        method: 'get',
    })
}
export function favorites() {
    return request({
        url: '/cms/leaderboard/favorites',
        method: 'get',
    })
}