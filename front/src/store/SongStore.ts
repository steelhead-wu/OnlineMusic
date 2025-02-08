import {defineStore} from "pinia";
import {computed, ref} from "vue";

export const useSongStore = defineStore('songStore', () => {
        const isPlay = ref(false);


        let currentSongIdx = ref(0);
        const songList = ref<Array<Song>>([
            {
                id: '1',
                singerId: '',
                title: '孝琳 - 안녕 (再见)',
                album: '',
                picture: '/asset/img/songPic/109951169493800260.jpg',
                lyric: '',
                url: '/asset/song/孝琳 - 안녕 (再见).mp3'
            },
            {
                id: '2',
                singerId: '',
                title: '林俊杰-一千年以后',
                album: '一千年以后',
                picture: '/asset/img/songPic/109951163187405670.jpg',
                lyric: '',
                url: '/asset/song/林俊杰-一千年以后.mp3'
            },
        ]);


        let currentSong = ref<Song>(songList.value[currentSongIdx.value]);
        // let currentSong = ref<Song>({
        //     id: '',
        //     singerId: '',
        //     title: '孝琳 - 안녕 (再见)',
        //     album: '',
        //     picture: '/asset/img/songPic/109951169493800260.jpg',
        //     lyric: '',
        //     url: '/asset/song/孝琳 - 안녕 (再见).mp3'
        // })
        // const getCurrentSongIdx = computed(() => currentSongIdx);
        const getIsPlay = computed(() => isPlay);
        const getCurrentSongIdx = computed(() => currentSongIdx);
        const getCurrentSong = computed(() => {
            setCurrentSong(songList.value[currentSongIdx.value]);
            return currentSong;
        });
        const getSongList = computed(() => songList);
        // const getCurrentSong = computed(() => songList.value[currentSongIdx.value]);

        const setIsPlay = () => {
            isPlay.value = !isPlay.value;
        }

        const setCurrentSongIdx = (idx: number) => {
            currentSongIdx.value = idx;
        }

        const incSongIdx = () => {
            if (currentSongIdx.value + 1 < songList.value.length) {
                ++currentSongIdx.value;
            } else {
                currentSongIdx.value = 0;
            }
        }

        const decSongIdx = () => {
            if (currentSongIdx.value > 0) {
                --currentSongIdx.value;
            } else {
                currentSongIdx.value = songList.value.length - 1;
            }
        }

        const setCurrentSong = (song: Song) => {
            currentSong.value = song;
        }

        const setSongList = (songs: Array<Song>) => {
            songList.value = songs;
        }

        function $reset() {
            isPlay.value = false;
            currentSong.value.id = '';
            currentSong.value.singerId = '';
            currentSong.value.title = '';
            currentSong.value.album = '';
            currentSong.value.picture = '';
            currentSong.value.lyric = '';
            currentSong.value.url = '';
        }

        return {
            $reset,
            isPlay,
            getIsPlay,
            setIsPlay,
            currentSong,
            getCurrentSong,
            setCurrentSong,
            currentSongIdx,
            getCurrentSongIdx,
            setCurrentSongIdx,
            decSongIdx,
            incSongIdx,
            songList,
            getSongList,
            setSongList,
        };
    },
    {
        persist: false,
    },
);