<script lang="ts" setup>

import {useSongStore} from "@/store/SongStore";
import {ref} from "vue";
import {baseURL} from "@/api/request";

const songStore = useSongStore();

const music = ref();
const music_src = ref(baseURL + songStore.getCurrentSong.value.url);


function play() {
  console.log("comi")
  console.log(music.value);
  music.value.play();
  // const audio = document.getElementById("myAudio");
  // console.log(audio);
  // audio.play();
}

function pause() {
  const audio = document.getElementById("myAudio");
  audio.pause();
}

function setVolume(vol) {
  const audio = document.getElementById("myAudio");
  audio.volume = vol; // 0.0 ~ 1.0
}

// 监听播放结束事件
// audio.addEventListener('ended', () => {
//   console.log('播放结束');
// });
</script>


<template>
  <button @click="play">播放</button>
  <audio ref="music" controls id="myAudio">
    <source :src="music_src" type="audio/mpeg">
    <!--    <source src="../../assets/孝琳 - 안녕 (再见).mp3" type="audio/mpeg">-->
  </audio>

  <button @click="pause()">暂停</button>
  <button @click="setVolume(0.5)">半音量</button>
</template>


<style scoped>

</style>