import {defineStore} from "pinia";
import {computed, ref} from "vue";

export const useSongStore = defineStore('songStore', () => {

        const currentTime = ref(0);

        const getCurrentTime = computed(() => currentTime.value);

        const setCurrentTime = (curTime: number) => {
            currentTime.value = curTime;

        }


        const currentLyricIndex = ref(-1); //当前高亮歌词的索引

        const getCurrentLyricIndex = computed(() => getCurrentLyricIndex.value);

        const setCurrentLyricIndex = (lyricsIndex: number) => {
            currentLyricIndex.value = lyricsIndex;
        }

        const isPlay = ref(false);
        const currentSongIdx = ref(0);
        const songList = ref<Array<Song>>([]);

        let currentSong = ref<Song>(songList.value[currentSongIdx.value]);


        const getIsPlay = computed(() => isPlay.value);
        const getCurrentSongIdx = computed(() => currentSongIdx.value);
        const getCurrentSong = computed(() => {
            console.log('currentSongIdx:', currentSongIdx.value);
            // if (getCurrentSongIdx.value > -1) {
            if (currentSongIdx.value > -1 && currentSongIdx.value < songList.value.length) {
                console.log('come currentSongIdx.value > -1')
                setCurrentSong(songList.value[currentSongIdx.value]);
                return songList.value[currentSongIdx.value];
            }
            return {
                id: '-1',
                singerId: '',
                title: '',
                album: '',
                picture: '/asset/img/songPic/tubiao.jpg',
                lyric: '暂无歌词',
                url: ''
            };
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


        const parseLyrics = () => {
            const lyricsList = [];

            getCurrentSong.value.lyric.matchAll(/^\[(?<min>\d{2}):(?<sec>\d{2}\.\d{2,})](?<lyric>.+)/gm).forEach((each, idx) => {
                lyricsList.push({time: parseInt(each.groups.min) * 60 + parseFloat(each.groups.sec), lyric: each.groups.lyric});
                if (idx == 10) {
                    console.log('each.groups.min', each.groups.min);
                    console.log('each.groups.sec', each.groups.sec);
                    console.log('time',lyricsList[10].time);
                }


            })
            return lyricsList;
        }

        /**
         * find maximum value which is not larger than
         * <code>songStore.getCurrentTime </code>
         * @param lyrics
         */
        const findCurrentLyricPos = (lyrics: Array<object>) => {
            let l = 0, r = lyrics.length;
            while (l < r) {
                let m = l + r >> 1;
                if (lyrics[m].time >= currentTime.value) {
                    r = m;
                } else {
                    l = m + 1;
                }
            }
            currentLyricIndex.value = l;
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
            currentTime,
            getCurrentTime,
            setCurrentTime,
            parseLyrics,
            currentLyricIndex,
            getCurrentLyricIndex,
            setCurrentLyricIndex,
        };
    },
    {
        persist: true,
    },
);