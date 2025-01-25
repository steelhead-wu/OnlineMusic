import {createMemoryHistory, createRouter} from 'vue-router'

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
    history: createMemoryHistory(),
    routes,
})

export default router