import {createMemoryHistory, createRouter, createWebHistory} from 'vue-router'

import HomeView from '../view/HomeView.vue'
import AboutView from '../view/AboutView.vue'
import BasicLayout from '../layouts/BasicLayout.vue'
import Login from '../view/Login.vue'

const routes = [
    {
        path: '/',
        component: HomeView
    },
    {
        path: '/login',
        component: Login
    },
]

const router = createRouter({
    /*
  创建一个基于内存的历史。该历史的主要目的是为了处理服务端渲染。它从一个不存在的特殊位置开始。
  用户可以通过调用 router.push 或 router.replace 将该位置替换成起始位置。

  名称	  类型	   默认值	描述
  base	string	''  	所有 URL 的基础位置，默认为 '/'
   */
    history: createWebHistory(),
    routes,
})

export default router