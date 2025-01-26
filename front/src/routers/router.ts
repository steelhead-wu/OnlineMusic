import {createRouter, createWebHistory} from 'vue-router'

import HomeView from '../view/HomeView.vue'
import Login from '../view/LoginView.vue'
import {Behavior} from "@/enum/Behavior";
import {useGlobalStatusStore} from "@/store/GlobalStatusStore";

const routes = [
    {
        path: Behavior.HOME,
        // path: '/',
        component: HomeView,
        alias: '/home',
    },
    {
        path: Behavior.SIGN_IN,
        // path: '/sign-in',
        component: Login,
        alias: Behavior.SIGN_UP,
        // alias: '/sign-up',
    },
]

// const routes = [{ path: '/home', redirect: '/' }]

const router = createRouter({
    /*
  创建一个基于内存的历史。该历史的主要目的是为了处理服务端渲染。它从一个不存在的特殊位置开始。
  用户可以通过调用 router.push 或 router.replace 将该位置替换成起始位置。

  名称	  类型	   默认值	描述
  base	string	''  	所有 URL 的基础位置，默认为 '/'
   */
    history: createWebHistory(),
    routes,
});


router.beforeEach((to, from, next) => {
    console.log(to);
    console.log(from);
    console.log(next);
    const globalStatusStore = useGlobalStatusStore();
    if (to.path === Behavior.SIGN_IN) {
        globalStatusStore.isLogin = true;
        globalStatusStore.isLoginOrSignup = true;
    } else if (to.path === Behavior.SIGN_UP) {
        globalStatusStore.isLogin = false;
        globalStatusStore.isLoginOrSignup = true;
    }
    return true;
})


export default router