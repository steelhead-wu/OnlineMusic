import {ref, computed} from 'vue'
import {defineStore} from 'pinia'
import type Admin from "@/pojo/Admin.ts";


export const useAdminStore = defineStore('admin', () => {
        const isLogin = ref<boolean>(false);
        const getIsLogin = computed(() => isLogin.value);

        function setIsLogin(login: boolean) {
            isLogin.value = login;
        }


        const admin = ref<Admin>();
        const getAdmin = computed(() => admin.value);

        function setAdmin(loginAdmin: Admin) {
            admin.value = loginAdmin;
        }

        return {isLogin, getIsLogin, setIsLogin, admin, getAdmin, setAdmin}
    },
    {
        persist: true
    }
)