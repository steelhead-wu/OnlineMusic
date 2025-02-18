<script setup lang="ts">
import {useRoute} from "vue-router";
import {useSingersStore} from "@/store/SingersStore";
import {baseURL} from "@/api/request";

import {onMounted, ref} from "vue";
import SongList from "@/components/song/SongList.vue";
import {getAllSongBySingerId} from "@/api/song/SongApi";

const route = useRoute();
const singersStore = useSingersStore();
const current_singer = ref<Singer>(singersStore.getSingers[route.params.idx]);


const tableData = ref<Array<unknown>>([]);

onMounted(() => {
  getAllSongBySingerId(current_singer.value.id).then(value => {
    console.log(value.data.data);
    for (const song: Song of value.data.data) {
      song['song'] = song.title?.slice(song.title?.indexOf('-') + 1);
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
          <li>出生: {{ current_singer.birth }}</li>
          <li>年龄: {{
              new Date().getFullYear() - current_singer.birth.slice(0, current_singer.birth.indexOf('-'))
            }}
          </li>
          <li>性别: {{ current_singer.sex ? '男' : '女' }}</li>
          <li>家乡: {{ current_singer.location }}</li>
        </ul>
      </div>
    </el-aside>
    <el-main class="album-main">
      <h1 class="singer-name">{{ current_singer.name }}</h1>
      <el-text class="singer-introduction">
        {{ current_singer.introduction }}
      </el-text>
      <SongList class="songList" :table-data="tableData"/>
    </el-main>
  </el-container>
</template>

<style scoped lang="scss">
.album-slide {
  //display: flex;
  flex-direction: column;
  align-items: center;
  padding-top: 20px;


  .singer-img {
    position: fixed;
    top: 100px;
    left: 100px;
    height: 250px;
    width: 250px;
    border-radius: 10%;
  }

  .album-info {
    width: 60%;
    padding-top: 2rem;
    position: fixed;
    top: 325px;
    left: 110px;

    ul {
      position: fixed;
      left: 70px;

      li {
        display: inline-block;
        width: 100%;
        height: 30px;
        line-height: 30px;
      }
    }
  }
}

.album-main {
  position: fixed;
  //top: 0;
  .singer-name {
    position: fixed;
    top: 175px;
    left: 375px;
    font-weight: bold;
  }

  .singer-introduction {
    width: 50%;
    position: fixed;
    top: 200px;
    left: 375px;
    color: black;
    margin: 10px 0 20px 0;
  }

  .songList {
    position: relative;
    //left: 200px;
    //top: 0;
    //right:  200px;

  }
}

@media screen and (min-width: 668px) {
  .album-slide {
    position: fixed;
    width: 400px;
  }
  .album-main {
    min-width: 600px;
    padding-right: 10vw;
    margin-left: 400px;
  }
}

@media screen and (max-width: 668px) {
  .album-slide {
    display: none;
  }
}
</style>