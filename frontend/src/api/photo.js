import request from '@/utils/request'

//获取图片列表
export function photoList(data) {
    return request({
      url: '/photo/list',
      method: 'post',
      data
    })
}
//删除图片
export function deletePhoto(data) {
    return request({
      url: '/photo/delete',
      method: 'post',
      data
    })
}