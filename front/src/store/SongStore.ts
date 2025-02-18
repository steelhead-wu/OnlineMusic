import {defineStore} from "pinia";
import {computed, ref} from "vue";

export const useSongStore = defineStore('songStore', () => {
        const isPlay = ref(false);
        let currentSongIdx = ref(0);
        const songList = ref<Array<Song>>([
            {
                id: '1',
                singerId: '',
                title: '孝琳-안녕 (再见)',
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
            {
                id: '3',
                singerId: '',
                title: '周杰伦-告白气球',
                album: '床边故事',
                picture: '/asset/img/songPic/gaobaiqiqui.jpg',
                lyric: '',
                url: '/asset/song/周杰伦-告白气球.mp3'
            },
            {
                id: '4',
                singerId: '',
                title: '权志龙-HOME SWEET HOME',
                album: 'HOME SWEET HOME',
                picture: '/asset/img/songPic/48c3318938d448503898feef740373c7.jpg',
                lyric: '',
                url: '/asset/song/权志龙-HOMESWEETHOME.mp3'
            },
        ]);
        let currentSong = ref<Song>(songList.value[currentSongIdx.value]);


        const getIsPlay = computed(() => isPlay.value);
        const getCurrentSongIdx = computed(() => currentSongIdx.value);
        const getCurrentSong = computed(() => {
            if (currentSongIdx.value > -1) {
                setCurrentSong(songList.value[currentSongIdx.value]);
                return songList.value[currentSongIdx.value];
            }
            return null;
        });
        const getSongList = computed(() => songList.value);
        // const getCurrentSong = computed(() => songList.value[currentSongIdx.value]);

        const flipIsPlay = () => {
            isPlay.value = !isPlay.value;
        }

        const setIsPlay = (play: boolean) => {
            isPlay.value = play;
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
            currentSongIdx.value = -1;
            songList.value = [];
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
            flipIsPlay,
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