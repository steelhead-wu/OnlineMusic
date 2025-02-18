import {defineStore} from "pinia";
import {computed, ref} from "vue";

export const useSingersStore = defineStore('singersStore', () => {

        const singers = ref<Array<Singer>>();
        const currentNameOptIdx = ref<number>(0);


        const getSingers = computed((): Array<Singer> => singers.value);
        const getCurrentNameOptIdx = computed((): number => currentNameOptIdx.value);

        const setCurrentNameOptIdx = (nameOptIdx: number) => {
            currentNameOptIdx.value = nameOptIdx;
        }
        const setSingers = (singer_list: Array<Singer>) => {
            singers.value = singer_list;
        }

        return {singers, getSingers, setSingers, currentNameOptIdx, getCurrentNameOptIdx, setCurrentNameOptIdx};
    },
    {
        persist: true
    }
);