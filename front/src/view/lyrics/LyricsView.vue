<script setup lang="ts">
import {useSongStore} from "@/store/SongStore";
import {baseURL} from "@/api/request";

const songStore = useSongStore();


</script>

<template>
  <div class="lyrics-bg">
    <div class="aside-bg">
      <div class="album-area">
        <el-image :src="baseURL + songStore.getCurrentSong.picture" class="album-pic"/>
      </div>
      <div class="song-info-area">
        <div class="album">{{ songStore.getCurrentSong.album }}</div>
        <div class="singer">{{ songStore.getCurrentSong.title }}</div>
      </div>
    </div>
    <div class="main-bg"></div>
  </div>
</template>

<style scoped lang="scss">

.lyrics-bg {
  width: 70%;
  height: 100%;
  margin: 0 auto;
  display: flex;
  flex-wrap: nowrap;

  .aside-bg {
    flex: 1;
    height: 100%;

    .album-area {
      height: 60%;

      .album-pic {
        //width: 50%;
        //height: 50%;
      }
    }

    .song-info-area {
      height: 40%;
    }
  }

  .main-bg {
    flex: 2;
    height: 100%;
  }
}

</style>