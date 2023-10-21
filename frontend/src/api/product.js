import request from '@/utils/request'

//获取物品列表
export function productList(data) {
  return request({
    url: '/product/list',
    method: 'post',
    data
  })
}

//添加物品
export function productAdd(data) {
  return request({
    url: '/product/add',
    method: 'post',
    data
  })
}

//修改物品
export function productUpdate(data) {
  return request({
    url: '/product/update',
    method: 'post',
    data
  })
}

//删除物品
export function productDelete(id) {
  return request({
    url: '/product/delete',
    method: 'post',
    params: {
      id
    }
  })
}

//批量删除物品
export function productBatchDelete(data) {
  return request({
    url: '/product/delete/batch',
    method: 'post',
    data
  })
}





