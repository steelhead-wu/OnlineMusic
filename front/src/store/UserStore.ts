import {defineStore} from "pinia";
import {ref, computed} from "vue";


export const useUserStore = defineStore('userStore', () => {
        // 是否登录状态
        let isOnline = ref<boolean>(false);

        const getIsOnline = computed(() => isOnline.value);

        const setIsOnline = (online: boolean) => {
            isOnline.value = online;
        }

        let loginUser = ref<User>({
            id: '',
            account: '',
            password: '',
            nickname: '',
            sex: 0,
            whatsUp: '',
            avatar: 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png',
            birth: '',
        });

        const getLoginUser = computed(() => loginUser.value);

        const setLoginUser = (user: User) => {
            if (user.id)
                loginUser.value.id = user.id;
            if (user.account)
                loginUser.value.account = user.account;
            if (user.password)
                loginUser.value.password = user.password;
            if (user.nickname)
                loginUser.value.nickname = user.nickname;
            if (user.sex != undefined)
                loginUser.value.sex = user.sex;
            if (user.whatsUp)
                loginUser.value.whatsUp = user.whatsUp;
            if (user.birth)
                loginUser.value.birth = user.birth;
            if (user.avatar)
                loginUser.value.avatar = user.avatar;

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

        return {loginUser, isOnline, getIsOnline, setIsOnline, setLoginUser, getLoginUser, $reset};
    },
    {
        persist: true,
    },
);