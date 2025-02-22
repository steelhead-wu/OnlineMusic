<script setup lang="ts">

import {computed, defineOptions, ref} from "vue";
import {baseURL} from "@/api/request";
import {useSongStore} from "@/store/SongStore";
import {getSingerBySex} from "@/api/singer/SingerApi";
import {getAllSongList, getSongListByStyle, updateOrSaveRating} from "@/api/songList/SongListApi";
import {SongListEnum} from "@/enum/SongListEnum";
import {getSongBySongListID} from "@/api/song/SongApi";


// const songStore = useSongStore()
// const music = ref();
// current song play
// const song = computed(() => songStore.getCurrentSong.value);
// const music_src = computed(() => baseURL + song.value.url);


// const ended = () => {
//   console.log("come ended");
//   progress.value = 0;
//   music.value.addEventListener('ended', next);
// }


// getSingerBySex(2).then(value => {
//   console.log(value);
// })


// getAllSongList().then(value => {
//   console.log(value.data);
// })

// getSongListByStyle('华语').then(value => {
//   console.log(value.data);
// })

// getSongBySongListID(7).then(value => {
//   console.log(value.data);
// })

// updateOrSaveRating(1, 2, 3).then(value => {
//   console.log("value.data", value.data);
// })
</script>

<template>

  <router-view></router-view>

  <!--  <audio preload="metadata" ref="music" id="myAudio" :src="music_src" type="audio/mpeg"-->
  <!--         @ended="ended"></audio>-->
</template>



