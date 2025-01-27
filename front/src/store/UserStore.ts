import {defineStore} from "pinia";
import {reactive, ref, computed} from "vue";


export const useUserStore = defineStore('userStore', () => {
    // 是否登录状态
    let isOnline= ref<boolean>(false);
    let loginUser = reactive<User>({
        id: '',
        account: '',
        password: '',
        nickname: '',
        avatar: 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png'
    });

    const getLoginUser = computed(() => loginUser);

    const setLoginUser = (user: User) => {
        loginUser = user;
    }

    return {loginUser, isOnline, setLoginUser, getLoginUser};
});