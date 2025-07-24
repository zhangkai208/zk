import { createRouter, createWebHistory } from 'vue-router'
import { useTokenStore } from '@/stores/token'

import Login from '@/view/Login.vue'
import Layout from '@/view/Layout.vue'
import ArticleCategory from '@/article/ArticleCategory.vue'
import ArticleManage from '@/article/ArticleManage.vue'
import UserAvatar from '@/user/UserAvatar.vue'
import UserInfo from '@/user/UserInfo.vue'
import UserResetPassword from '@/user/UserResetPassword.vue'

const routes =[
     { path:'/login',component:Login },
     { path:'/',component:Layout, redirect:'/article/manage',
      children: [
        { path:'/article/category',component:ArticleCategory },
        { path:'/article/manage',component:ArticleManage },
        { path:'/user/avatar',component:UserAvatar },
        { path:'/user/info',component:UserInfo },
        { path:'/user/resetPassword',component:UserResetPassword }
      ]
     }
]

const router = createRouter({
  history: createWebHistory(),
  routes: routes,
})

// 添加路由守卫
router.beforeEach((to) => {
  const tokenStore = useTokenStore()
  if (!tokenStore.token && to.path !== '/login') {
    return '/login'
  }
})

export default router