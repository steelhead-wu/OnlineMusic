<script setup lang="ts">
import {computed, onMounted, ref, watch} from "vue";
import {onBeforeRouteLeave, useRoute, useRouter} from "vue-router";
import {searchSong} from "@/api/song/SongApi";
import {baseURL} from "@/api/request";


const route = useRoute();

const data = ref(null);

const isShow = ref(false);

watch(() => route.query.kw, fetchData, {immediate: true})


async function fetchData(kw: string) {
  searchSong(kw).then(value => {
    data.value = value.data.data;
    isShow.value = data.value.length != 0 && data.value[0].name != null && data.value[0].pic != null;
    console.log('isShow.value', isShow.value);
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
          <span class="singer_name">{{ data[0].name }}</span>
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          <span>单曲 {{ data[0].song_number }}</span>
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          <span>专辑 {{ data[0].album_number }}</span>
        </el-main>
      </el-container>
    </el-header>
    <el-main class="main">

    </el-main>
  </el-container>
</template>

<style scoped lang="scss">
.header {
  padding: 0;
  width: 70%;

  height: auto;
  margin: 30px auto 0;


  .el-aside {
    width: auto;
    height: auto;

    .avatar {
      width: 150px;
      height: 150px;
    }
  }

  .el-main {
    font-size: 50px;
    .singer_name {
      margin-left: 20px;

    }
  }


}


.main {
  padding: 0;
  width: 70%;
  height: auto;
  margin: 30px auto 0;
}
</style>