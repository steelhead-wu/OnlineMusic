import {ref, computed} from 'vue'
import {defineStore} from 'pinia'
import type SongList from "@/pojo/SongList.ts";


export const useSongListStore = defineStore('songListStore', () => {
        const songList = ref<SongList>({});
        const getSongList = computed(() => songList.value);

        function setSongList(rawSongList: SongList) {
            songList.value = rawSongList;
        }

        return {songList, getSongList, setSongList}
    },
    {
        persist: false
    }
)