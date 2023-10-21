import request from '@/utils/request'
//申请
export function applyFor(data) {
    return request({
      url: '/trade/apply',
      method: 'post',
      data
    })
  }

export function tradeList(data) {
    return request({
      url: '/trade/appliedlist',
      method: 'post',
      data
    })
  }

export function tradeDelete(id) {
    return request({
      url: '/trade/deleteApplied',
      method: 'post',
      params: {
        id
      }
    })
  }

export function tradeBatchDelete(data) {
    return request({
      url: '/trade/deleteApplied/batch',
      method: 'post',
      data
    })
  }
  

  



  export function applyList(data) {
    return request({
      url: '/trade/applylist',
      method: 'post',
      data
    })
 }
    export function applyDelete(id) {
      return request({
        url: '/trade/deleteApply',
        method: 'post',
        params: {
          id
        }
      })
    }
  
  export function applyBatchDelete(data) {
      return request({
        url: '/trade/deleteApply/batch',
        method: 'post',
        data
      })
    }
  
  

  
 
