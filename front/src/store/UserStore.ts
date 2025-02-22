import {defineStore} from "pinia";
import {ref, computed} from "vue";


export const useUserStore = defineStore('userStore', () => {
        // 是否登录状态
        let isOnline = ref<boolean>(false);
        let loginUser = ref<User>({
            id: '',
            account: '',
            password: '',
            nickname: '',
            avatar: 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png'
        });

        const getLoginUser = computed(() => loginUser.value);

        const setLoginUser = (user: User) => {
            loginUser.value = user;
            // loginUser.id = user.id;
            // loginUser.account = user.account;
            // loginUser.password = user.password;
            // loginUser.nickname = user.nickname;
            // loginUser.avatar = user.avatar;
        }


        function $reset() {
            isOnline.value = false;
            // loginUser.value
            loginUser.value.id = '';
            loginUser.value.account = '';
            loginUser.value.password = '';
            loginUser.value.nickname = '';
            loginUser.value.avatar = 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png';
        }

        return {loginUser, isOnline, setLoginUser, getLoginUser, $reset};
    },
    {
        persist: true,
    },
);