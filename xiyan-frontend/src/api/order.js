import request from '@/utils/request'
export function create(data) {
    return request({
        url: '/web/order/create',
        method: 'post',
        data
    })
}