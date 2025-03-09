import {createRouter, createWebHistory} from 'vue-router'

import HomeView from '../view/HomeView.vue'
import LoginView from '../view/LoginView.vue'
import PersonalView from '../view/user/PersonalView.vue'
import SettingsView from '../view/user/SettingsView.vue'
import SingerView from '../view/singer/SingerView.vue'
import SingerDetailView from '../view/singer/SingerDetailView.vue'
import SongListDetailView from '../view/songList/SongListDetailView.vue'
import SongListView from '../view/songList/SongListView.vue'
import SearchView from '@/view/search/SearchView.vue'
import LyricsView from '@/view/lyrics/LyricsView.vue'
import AboutAccountView from '@/view/AboutAccountView.vue'
import BasicLayout from '@/layouts/BasicLayout.vue'
import {Behavior} from "@/enum/Behavior";
import {useGlobalStatusStore} from "@/store/GlobalStatusStore";

const routes = [
    {
        path: Behavior.HOME,
        component: BasicLayout,
        alias: Behavior.HOMEPAGE,
        children: [
            {
                // 主页
                path: Behavior.HOME,
                component: HomeView,
                alias: Behavior.HOMEPAGE,
            },
            {// 用户中心
                path: Behavior.PERSONAL,
                component: PersonalView,
            },
            {// 设置
                path: Behavior.SETTINGS,
                component: SettingsView,
            },
            {// 歌手
                path: Behavior.SINGER,
                component: SingerView,
            },
            {// 歌手细节
                path: Behavior.SINGER_DETAIL + '/:idx(\\d+)',
                component: SingerDetailView,
            },
            {// 歌单
                path: Behavior.SONG_LIST,
                component: SongListView,
            },
            {// 歌单细节
                path: Behavior.SONG_LIST_DETAIL + '/:idx(\\d+)',
                component: SongListDetailView,
            },
            {// search
                path: Behavior.SEARCH,
                component: SearchView,
            },
            {// lyrics
                // path: Behavior.LYRICS + '/:id(\\d+)',
                path: Behavior.LYRICS,
                component: LyricsView,
            },
        ]
    },
    {
        path: Behavior.SIGN_IN,
        component: LoginView,
        alias: Behavior.SIGN_UP,
    },
    {// 设置
        path: Behavior.ABOUT_ACCOUNT + '/:id(\\d+)',
        // path: Behavior.ABOUT_ACCOUNT,
        component: AboutAccountView,
    },
]

// const routes = [{ path: '/singer', redirect: '/' }]

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

/*
 Add a navigation guard that executes before any navigation. Returns a function that removes the registered guard.
 */
router.beforeEach((to, from, next) => {
    // console.log(to);
    // console.log(from);
    // console.log(next);
    const globalStatusStore = useGlobalStatusStore();
    if (to.path === Behavior.SIGN_IN) {
        globalStatusStore.isLogin = true;
        globalStatusStore.isLoginOrSignup = true;
    } else if (to.path === Behavior.SIGN_UP) {
        globalStatusStore.isLogin = false;
        globalStatusStore.isLoginOrSignup = true;
    }
    return next();
})


export default router