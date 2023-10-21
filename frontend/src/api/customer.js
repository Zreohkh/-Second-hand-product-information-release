import request from '@/utils/request'

export function userLogin(data) {
  return request({
    url: '/login',
    method: 'post',
    data
  })
}
export function customerRegister(data) {
    return request({
      url: '/register',
      method: 'post',
      data
    })
  }
export function customerModify(data) {
    return request({
      url: '/update_customer',
      method: 'post',
      data
    })
  }
export function customerMes(data) {
    return request({
      url: '/customer',
      method: 'post',
      data
    })
  }