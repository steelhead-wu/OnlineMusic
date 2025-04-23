import {defineStore} from "pinia";
import {computed, ref} from "vue";
import type {Singer} from "@/pojo/Singer.ts";
import type {SongList} from "@/pojo/SongList.ts";

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


        const setRating = (currentSongListIdx: number, rating: number) => {
            if (currentSongListIdx == null || currentSongListIdx < 0 || currentSongListIdx >= songList.value.length) {
                return;
            }
            // console.log("setRating");
            songList.value[currentSongListIdx].rating = rating;
        }

        return {
            singers, getSingers, setSingers,
            currentNameOptIdx, getCurrentNameOptIdx, setCurrentNameOptIdx,
            songList, getSongList, setSongList,
            currentSongListNameOptIdx, getCurrentSongListNameOptIdx, setCurrentSongListNameOptIdx,
            setRating,
        };
    },
    {
        persist: true
    }
);