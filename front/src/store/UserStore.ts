import {defineStore} from "pinia";
import {reactive, ref} from "vue";

export const useUserStore = defineStore('userStore', () => {
    // 是否登录状态
    const isLogin = ref(false);
    const loginUser = reactive<User>({
        id: '',
        account: '',
        password: '',
        nickname: '',
        avatar: 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png'
    });


    return {loginUser, isLogin};
});