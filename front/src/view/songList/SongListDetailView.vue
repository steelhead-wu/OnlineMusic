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
  <el-container>
    <el-aside>
      <div class="song-list-img">
        <el-image class="cover" :src="baseURL + current_song_list.pic"></el-image>
      </div>
      <h1>{{ current_song_list.title }}</h1>
    </el-aside>
    <el-main>
      <div class="introduction">
        <h1>简介</h1>
        <p>{{ current_song_list.introduction }}</p>
      </div>
      <div>
        <!--        <SongList :table-data="tableData"/>-->
      </div>
    </el-main>
  </el-container>
</template>

<style scoped lang="scss">
.song-list-img {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding-top: 20px;

  .cover {
    height: 250px;
    width: 250px;
    border-radius: 10%;
  }
}


.introduction {
  width: 100%;

  h1 {
    font-weight: bold;
    font-size: 30px;
  }

  p {
    margin: 10px 0 20px 0;
    text-align: left;
    line-height: 1.5;
    max-width: 1009px;
  }
}
</style>