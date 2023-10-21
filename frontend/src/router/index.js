import Vue from 'vue'
import Router from 'vue-router'
Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Default',
      redirect: '/home',
      component: () => import('@/views/User/home'),
    },
    {
       // home页面并不需要被访问，只是作为其它组件的父组件
      path: '/home',
      name: 'Home',
      component: () => import('@/views/User/home'),
      meta: {
        requireAuth: true
      },
      redirect: '/index',
      children:[
        {
          path:'/index',
          name:'Index',
          component:() => import('@/views/User/home/index'),
          meta:{
            requireAuth:true
          }
        },
        {
          path:'/product',
          name:'product',
          component:()=>import('@/views/User/product/index'),
          meta:{
            requireAuth:true
          }
        },
        {
          path:'/homepage',
          name:'homepage',
          component:()=>import('@/views/User/person/homepage'),
          meta:{
            requireAuth:true
          }
        },
        {
            path:'/apply',
            name:'apply',
            component:() => import('@/views/User/manage/apply'),
            meta:{
              requireAuth:true
            }
        },
        {
            path:'/applied',
            name:'applied',
            component:() => import('@/views/User/manage/applied'),
            meta:{
              requireAuth:true
            }
        }
        
      ]
    },
    //添加登录页面路由
    {
      path:'/login',
      name: 'Login',
      component: () =>import('@/views/login')
    },
    {
      path:'/register',
      name: 'register',
      component:() =>import('@/views/User/register')
    },
    {
      path:'/adminLogin',
      name: 'adminLogin',
      component:() =>import('@/views/Admin/adminLogin')
    },
    





    {
      path:'/adminHome',
      name: 'adminHome',
      component:() =>import('@/views/Admin/home'),
      meta: {
        requireAuth: true
      },
      children:[
        {
          path:'/customer0',
          name: 'customer0',
          component:() =>import('@/views/Admin/page/customer/customer0'),
          meta:{
            requireAuth:true
          }
        },
        {
          path:'/customer1',
          name: 'customer1',
          component:() =>import('@/views/Admin/page/customer/customer1'),
          meta:{
            requireAuth:true
          }
        },
        {
          path:'/customer2',
          name: 'customer2',
          component:() =>import('@/views/Admin/page/customer/customer2'),
          meta:{
            requireAuth:true
          }
        },

        {
          path:'/product0',
          name: 'product0',
          component:() =>import('@/views/Admin/page/product/product0'),
          meta:{
            requireAuth:true
          }
        },
        {
          path:'/product1',
          name: 'product1',
          component:() =>import('@/views/Admin/page/product/product1'),
          meta:{
            requireAuth:true
          }
        },
        {
          path:'/product2',
          name: 'product2',
          component:() =>import('@/views/Admin/page/product/product2'),
          meta:{
            requireAuth:true
          }
        },
      ]
    }
  ]
})
