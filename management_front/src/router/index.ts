import {createRouter, createWebHistory} from 'vue-router'
import BasicLayout from "@/layout/BasicLayout.vue";
import {Behavior} from "@/enums/Behavior.ts";
import LoginView from "@/views/LoginView.vue";
import RegistryView from "@/views/RegistryView.vue";
import SingerManagementView from "@/views/SingerManagementView.vue";
import SongManagementView from "@/views/SongManagementView.vue";
import SongListManagementView from "@/views/SongListManagement/SongListManagementView.vue";
import SongListDetailsView from "@/views/SongListManagement/SongListDetailsView.vue";
import SettingsView from "@/views/admin/SettingsView.vue";
import {Cookie} from "@/util/Cookie.ts";
import {CookiesName} from "@/enums/CookiesName.ts";


const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: Behavior.HOME,
            component: BasicLayout,
            children: [
                {
                    path: Behavior.SINGER,
                    component: SingerManagementView,
                },
                {
                    path: Behavior.SONG,
                    component: SongManagementView,
                },
                {
                    path: Behavior.SONG_LIST,
                    component: SongListManagementView,
                },
                {
                    path: Behavior.SONG_LIST_DETAIL + '/:id(\\d+)',
                    component: SongListDetailsView,
                },
                {
                    path: Behavior.SETTINGS,
                    component: SettingsView,
                },
            ]
        },
        {
            path: Behavior.SIGN_IN,
            component: LoginView,
        },
        {
            path: Behavior.SIGN_UP,
            component: RegistryView,
        },


    ],
})


router.beforeEach((to, from, next) => {
    const cookie = Cookie.get(CookiesName.AD_AU);
    // admin is not login.
    if (cookie == '') {
        if (to.path == Behavior.SINGER || to.path == Behavior.SONG || to.path == Behavior.SONG_LIST || to.path.startsWith(Behavior.SONG_LIST_DETAIL)) {
            next(Behavior.SIGN_IN);
            return;
        }
    }
    next();
})

export default router
