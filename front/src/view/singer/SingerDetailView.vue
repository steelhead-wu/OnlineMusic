<script setup lang="ts">
import {useRoute} from "vue-router";
import {useSingersStore} from "@/store/SingersStore";
import {baseURL} from "@/api/request";

import {onMounted, ref} from "vue";
import SongList from "@/components/song/SongList.vue";
import {getAllSongBySingerId} from "@/api/song/SongApi";
import type {Singer} from "@/pojo/Singer.ts";
import type Song from "@/pojo/Song.ts";

const route = useRoute();
const singersStore = useSingersStore();
const current_singer = ref<Singer>(singersStore.getSingers[route.params.idx]);


const tableData = ref<Array<unknown>>([]);

onMounted(() => {
  getAllSongBySingerId(current_singer.value.id).then(value => {
    for (let song: Song of value.data.data) {
      song['title'] = song.title;
      song['singerName'] = current_singer.value.name;
      tableData.value.push(song);
    }
  })
})

</script>

<template>
  <el-container>
    <el-aside class="album-slide">
      <el-image class="singer-img" :src="baseURL+current_singer.pic"></el-image>
      <div class="album-info">
        <h2>基本信息</h2>
        <ul>
          <li>出生: {{ `${current_singer.birth[0]}-${current_singer.birth[1]}-${current_singer.birth[2]}` }}</li>
          <li>年龄: {{
              `${new Date().getFullYear() - current_singer.birth[0]}岁`
            }}
          </li>
          <li>性别: {{ current_singer.sex ? '男' : '女' }}</li>
          <li>家乡: {{ current_singer.location }}</li>
        </ul>
      </div>
    </el-aside>
    <el-main class="album-main">
      <h1 class="singer-name">{{ current_singer.name }}</h1>
      <p class="singer-introduction">
        {{ current_singer.introduction }}
      </p>
      <SongList class="songList" :table-data="tableData"/>
    </el-main>
  </el-container>
</template>

<style scoped lang="scss">
.album-slide {
  display: flex;
  flex-direction: column;
  padding: 0;
  margin-left: 100px;
  margin-top: 50px;

  .singer-img {
    border-radius: 10%;
    width: 250px;
    height: 250px;
  }

  .album-info {
    h2 {
      margin-top: 10px;
      margin-left: 20px;
    }

    ul {
      list-style: none;

      li {
        margin-top: 5px;
        margin-left: -20px;
      }
    }
  }
}

.album-main {
  display: flex;
  flex-direction: column;
  padding: 0;
  margin-top: 50px;
  margin-left: 50px;

  .singer-name {
    font-weight: bold;
    font-size: 50px;
  }

  .singer-introduction {
    //width: 1100px;
    width: 900px;
    font-weight: bold;

  }

  .songList {
    margin-top: 20px;
  }
}

@media screen and (min-width: 668px) {
  .album-slide {
    //position: fixed;
    //width: 400px;
  }
  .album-main {
    //min-width: 600px;
    //padding-right: 10vw;
    //margin-left: 400px;
  }
}

@media screen and (max-width: 668px) {
  .album-slide {
    //  display: none;
  }
}
</style>