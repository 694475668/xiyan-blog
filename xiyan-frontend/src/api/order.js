import request from '@/utils/request'
export function create(data) {
    return request({
        url: '/cms/order/create',
        method: 'post',
        data
    })
}