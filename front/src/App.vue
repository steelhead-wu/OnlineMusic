<script setup lang="ts">

import {computed, ref} from "vue";
import {baseURL} from "@/api/request";
import {useSongStore} from "@/store/SongStore";
import {getSingerBySex} from "@/api/singer/SingerApi";


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
</script>

<template>

  <router-view></router-view>

  <!--  <audio preload="metadata" ref="music" id="myAudio" :src="music_src" type="audio/mpeg"-->
  <!--         @ended="ended"></audio>-->
</template>



