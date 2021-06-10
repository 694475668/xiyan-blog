import request from '@/utils/request'

//文件上传
export function fileUpload(data) {
    return request({
        url: '/oss/file/upload',
        method: 'post',
        data
    })
}
//图片上传
export function imageUpload(data) {
    return request({
        url: '/oss/image/upload',
        method: 'post',
        data
    })
}
//文件删除
export function fileDelete(key) {
    return request({
        url: '/oss/file/delete/' + key,
        method: 'delete',
    })
}
//图片删除
export function imageDelete(key) {
    return request({
        url: '/oss/image/delete/' + key,
        method: 'delete',
    })
}