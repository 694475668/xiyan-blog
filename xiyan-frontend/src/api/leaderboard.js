import request from '@/utils/request'

//排行榜
export function gold() {
    return request({
        url: '/system/leaderboard/gold',
        method: 'get',
    })
}
export function upload() {
    return request({
        url: '/system/leaderboard/upload',
        method: 'get',
    })
}
export function download() {
    return request({
        url: '/system/leaderboard/download',
        method: 'get',
    })
}
export function favorites() {
    return request({
        url: '/system/leaderboard/favorites',
        method: 'get',
    })
}