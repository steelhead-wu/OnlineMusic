import {defineStore} from "pinia";
import {computed, ref} from "vue";

export const useSingersStore = defineStore('singersStore', () => {

        const singers = ref<Array<Singer>>();
        const getSingers = computed((): Array<Singer> => singers.value);
        const setSingers = (singer_list: Array<Singer>) => {
            singers.value = singer_list;
        }

        const currentNameOptIdx = ref<number>(0);
        const getCurrentNameOptIdx = computed((): number => currentNameOptIdx.value);
        const setCurrentNameOptIdx = (nameOptIdx: number) => {
            currentNameOptIdx.value = nameOptIdx;
        }

        const songList = ref<Array<SongList>>();
        const getSongList = computed(() => songList.value);
        const setSongList = (song_list: Array<SongList>) => {
            songList.value = song_list;
        }

        const currentSongListNameOptIdx = ref<number>(0);
        const getCurrentSongListNameOptIdx = computed((): number => currentSongListNameOptIdx.value);
        const setCurrentSongListNameOptIdx = (songListNameOptIdx: number) => {
            currentSongListNameOptIdx.value = songListNameOptIdx;
        }



        return {
            singers, getSingers, setSingers,
            currentNameOptIdx, getCurrentNameOptIdx, setCurrentNameOptIdx,
            songList, getSongList, setSongList,
            currentSongListNameOptIdx, getCurrentSongListNameOptIdx, setCurrentSongListNameOptIdx,
        };
    },
    {
        persist: true
    }
);