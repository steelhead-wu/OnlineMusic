<script setup lang="ts">
import {computed, onMounted, ref, watch} from "vue";
import {onBeforeRouteLeave, useRoute, useRouter} from "vue-router";
import {getAllSongBySingerId, searchSong} from "@/api/song/SongApi";
import {baseURL} from "@/api/request";
import {Behavior} from "@/enum/Behavior";
import {useSingersStore} from "@/store/SingersStore";
import SongList from "@/components/song/SongList.vue";


const route = useRoute();

const data = ref(null);

const isShow = ref(false);
const loading_toggle = ref(true);

watch(() => route.query.kw, fetchData, {immediate: true})

const singersStore = useSingersStore();

const tableData = ref(null);

async function fetchData(kw: string) {
  searchSong(kw).then(value => {
    data.value = value.data.data;
    isShow.value = data.value.length != 0;
    console.log('isShow.value', isShow.value);
    console.log(data.value);
    tableData.value = [];
    if (isShow.value) {
      setTimeout(() => {
        loading_toggle.value = false;
      }, 250);

      for (const song of data.value) {
        const split = song.title?.split('-');
        song['song'] = split[1];
        song['singer'] = split[0];
        tableData.value.push(song);
      }
    } else {

    }
  })
}


</script>

<template>
  <el-container>
    <el-header class="header" v-if="isShow">
      <el-container>
        <el-aside>
          <el-avatar class="avatar" :src="baseURL + data[0].pic"/>
        </el-aside>
        <el-main>
          <span class="singer_name" @click="">{{ data[0].name }}</span>
          <span class="song-number">单曲 {{ data[0].song_number }}</span>
          <span class="album-number">专辑 {{ data[0].album_number }}</span>
        </el-main>
      </el-container>
    </el-header>
    <el-main class="main">
      <el-container>
        <el-header></el-header>
        <el-main>
          <SongList v-if="isShow" class="song-list" :table-data="tableData" :loading="loading_toggle"/>
          <h1 v-else style="text-align: center">NO DATA</h1>
        </el-main>
      </el-container>
    </el-main>
  </el-container>
</template>

<style scoped lang="scss">
.header {
  padding: 0;
  width: 900px;

  height: auto;
  margin: 30px auto 0;


  .el-aside {
    width: auto;
    height: auto;

    .avatar {
      width: 96px;
      height: 96px;
    }
  }

  .el-main {
    font-size: 25px;

    .singer_name {
      margin-left: 20px;
    }

    .song-number {
      margin-left: 20px;
    }

    .album-number {
      margin-left: 20px;
    }

  }


}


.main {
  padding: 0;
  width: auto;
  height: auto;
  margin: 30px auto 0;

  .el-header {
    height: auto;
  }

  .el-main {
    padding: 0;
    //margin: 0 auto;

    //.song-list {
    //  margin: 0 auto;
    //}
  }


}
</style>