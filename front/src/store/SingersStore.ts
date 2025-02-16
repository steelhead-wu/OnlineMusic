import {defineStore} from "pinia";
import {computed, ref} from "vue";

export const useSingersStore = defineStore('singersStore', () => {


        const singers = ref<Array<Singer>>();


        const getSingers = computed((): Array<Singer> => singers.value);


        const setSingers = (singer_list: Array<Singer>) => {
            singers.value = singer_list;
        }

        return {singers, getSingers, setSingers};
    },
    {
        persist: true
    }
);