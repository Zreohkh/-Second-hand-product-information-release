import request from '@/utils/request'

//获取查询物品列表
export function productList(data) {
  return request({
    url: '/search/list',
    method: 'post',
    data
  })
}