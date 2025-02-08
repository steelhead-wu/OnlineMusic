import {defineStore} from "pinia";
import {computed, ref} from "vue";

export const useSongStore = defineStore('songStore', () => {
    const isPlay = ref(false);


    let currentSong = ref<Song>(
        {
            id: '',
            singerId: '',
            title: '孝琳 - 안녕 (再见)',
            album: '',
            picture: '/asset/img/songPic/109951169493800260.jpg',
            lyric: '',
            url: '/asset/song/孝琳 - 안녕 (再见).mp3'
        }
    );

    const getCurrentSong = computed(() => currentSong);

    const setCurrentSong = (song: Song) => {
        currentSong.value = song;
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

    return {isPlay, currentSong, getCurrentSong, setCurrentSong, $reset};
});