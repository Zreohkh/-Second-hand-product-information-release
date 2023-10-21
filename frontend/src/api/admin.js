import request from '@/utils/request'

export function adminLogin(data) {
  return request({
    url: '/adminLogin',
    method: 'post',
    data
  })
}
export function customerList(data) {
  return request({
    url: '/admin/customerList',
    method: 'post',
    data
  })
}

export function review(data) {
  return request({
    url: '/admin/review',
    method: 'post',
    data
  })
}


export function productList(data) {
  return request({
    url: '/admin/productList',
    method: 'post',
    data
  })
}



export function Delete(data){
  return request({
    url: '/admin/delete',
    method: 'post',
    data
  })
}



