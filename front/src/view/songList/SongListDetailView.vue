<script setup lang="ts">
import {useRoute} from "vue-router";
import {useSingersStore} from "@/store/SingersStore";
import {baseURL} from "@/api/request";

import {onMounted, ref} from "vue";
import SongList from "@/components/song/SongList.vue";
import {getAllSongBySingerId, getSongBySongListID} from "@/api/song/SongApi";

const route = useRoute();
const singersStore = useSingersStore();
const current_song_list = ref<SongList>(singersStore.getSongList[route.params.idx]);


const tableData = ref<Array<unknown>>([]);

onMounted(() => {
  getSongBySongListID(current_song_list.value.id).then(value => {
    for (const song: Song of value.data.data) {
      const split = song.title?.split('-');
      song['song'] = split[1];
      song['singer'] = split[0];
      tableData.value.push(song);
    }
  })
})

</script>

<template>
  <el-container class="container">
    <el-aside class="aside">
      <div class="song-list-img-box">
        <el-image class="cover-img" :src="baseURL + current_song_list.pic"></el-image>
      </div>
      <h1>{{ current_song_list.title }}</h1>
    </el-aside>
    <el-main class="main">
      <div class="introduction">
        <h1>简介</h1>
        <p>{{ current_song_list.introduction }}</p>
      </div>
      <div class="songList">
        <SongList :table-data="tableData"/>
      </div>
    </el-main>
  </el-container>
</template>

<style scoped lang="scss">


.aside {
  margin-top: 50px;
  margin-left: 140px;

  h1 {
    margin-top: 10px;
    font-weight: bold;
  }

  .song-list-img-box {
    .cover-img {
      height: 250px;
      width: 250px;
      border-radius: 10%;
    }
  }
}

.main {
  margin-top: 20px;
  margin-left: 50px;


  .songList {
    max-width: 1000px;
  }

  .introduction {
    max-width: 800px;
    padding: 0;
    margin: 0;

    h1 {
      font-weight: bold;
      font-size: 30px;
    }

    p {
      margin: 0;
      text-align: left;
      line-height: 1.5;
      min-width: 500px;
    }
  }
}
</style>