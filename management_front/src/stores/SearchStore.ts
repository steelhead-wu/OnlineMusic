import {ref, computed} from 'vue'
import {defineStore} from 'pinia'


export const useSearchStore = defineStore('searchStore', () => {
        const context = ref<object>();
        const getContext = computed(() => context.value);

        const setContext = (rawcontext: object) => {
            context.value = rawcontext;
        }
        return {context, getContext, setContext};
    },
    {
        persist: false
    }
)