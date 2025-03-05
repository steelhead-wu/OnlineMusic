<script setup lang="ts">

import {computed, defineOptions, ref} from "vue";
import {baseURL} from "@/api/request";
import {useSongStore} from "@/store/SongStore";
import {getSingerBySex} from "@/api/singer/SingerApi";
import {getAllSongList, getSongListByStyle, updateOrSaveRating} from "@/api/songList/SongListApi";
import {SongListEnum} from "@/enum/SongListEnum";
import {getLikedSongByUserId, getSongBySongListID} from "@/api/song/SongApi";
import {conditionalDelete, conditionalQuery, conditionalUpdate} from "@/api/likes/LikesApi";

const like1: Likes = {
  userId: '1',
  songId: '2',
}

const like2: Likes = {
  // userId: '1',
  // songId: '2',
  id: '-2'
}
// conditionalQuery(like).then(value => {
//   console.log(value.data);
// })
// conditionalDelete(like).then(value => {
//   console.log(value.data);
// })

conditionalUpdate(like1, like2).then(value => {

})

</script>

<template>
  <!--  <div id="app">-->

  <!--  </div>-->
  <router-view></router-view>

  <!--  <audio preload="metadata" ref="music" id="myAudio" :src="music_src" type="audio/mpeg"-->
  <!--         @ended="ended"></audio>-->
</template>



