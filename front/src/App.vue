<script setup lang="ts">

import {computed, defineOptions, ref} from "vue";
import {baseURL} from "@/api/request";
import {useSongStore} from "@/store/SongStore";
import {getSingerBySex} from "@/api/singer/SingerApi";
import {getAllSongList, getSongListByStyle, updateOrSaveRating} from "@/api/songList/SongListApi";
import {SongListEnum} from "@/enum/SongListEnum";
import {getLikedSongByUserId, getSongBySongListID} from "@/api/song/SongApi";
import {delLikeBy, getLikeBy} from "@/api/likes/LikesApi";

// getLikeBy('1', '2').then(value => {
//   console.log(value.data.data);
// })

// const like: Likes = {
//   userId: '1',
//   songId: '2'
// }

// delLikeBy(like).then(value => {
//   console.log(value.data.data);
// })


// getLikedSongByUserId('1882054264398209025').then(value => {
//   console.log(value.data.data);
// })


</script>

<template>
  <!--  <div id="app">-->

  <!--  </div>-->
  <router-view></router-view>

  <!--  <audio preload="metadata" ref="music" id="myAudio" :src="music_src" type="audio/mpeg"-->
  <!--         @ended="ended"></audio>-->
</template>



