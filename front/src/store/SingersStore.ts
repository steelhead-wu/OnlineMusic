import {defineStore} from "pinia";
import {computed, ref} from "vue";

export const useSingersStore = defineStore('singersStore', () => {

        const singers = ref<Array<Singer>>();
        const currentNameOptIdx = ref<number>(0);
        const currentSongListNameOptIdx = ref<number>(0);


        const getSingers = computed((): Array<Singer> => singers.value);
        const getCurrentNameOptIdx = computed((): number => currentNameOptIdx.value);
        const getCurrentSongListNameOptIdx = computed((): number => currentSongListNameOptIdx.value);

        const setCurrentNameOptIdx = (nameOptIdx: number) => {
            currentNameOptIdx.value = nameOptIdx;
        }

        const setCurrentSongListNameOptIdx = (songListNameOptIdx: number) => {
            currentSongListNameOptIdx.value = songListNameOptIdx;
        }

        const setSingers = (singer_list: Array<Singer>) => {
            singers.value = singer_list;
        }

        return {
            singers, getSingers, setSingers, currentNameOptIdx, getCurrentNameOptIdx, setCurrentNameOptIdx,
            currentSongListNameOptIdx, getCurrentSongListNameOptIdx, setCurrentSongListNameOptIdx,
        };
    },
    {
        persist: true
    }
);