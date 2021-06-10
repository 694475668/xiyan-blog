import request from '@/utils/request'

//排行榜
export function gold() {
    return request({
        url: '/auth/leaderboard/gold',
        method: 'get',
    })
}
export function upload() {
    return request({
        url: '/auth/leaderboard/upload',
        method: 'get',
    })
}
export function download() {
    return request({
        url: '/auth/leaderboard/download',
        method: 'get',
    })
}
export function favorites() {
    return request({
        url: '/auth/leaderboard/favorites',
        method: 'get',
    })
}