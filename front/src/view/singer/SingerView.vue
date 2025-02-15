<script setup lang="ts">
import Navigation from "@/layouts/Navigation.vue";
import {onMounted, ref, watch} from "vue";
import {Singer, singer_style_list} from "@/enum/Singer";
import {getAllSinger, getSingerBySex} from "@/api/singer/SingerApi";
import PlayBody from "@/layouts/PlayBody.vue";


const nameOpt = ref('全部歌手');
const playList = ref<Array<Singer>>();

const doClick = (item) => {
  nameOpt.value = item.name;
  if (item.id === Singer.ALL_SINGER) {
    getAllSinger().then(value => {
      playList.value = value.data.data;
    })
  } else {
    getSingerBySex(item.id).then(value => {
      playList.value = value.data.data;
    })
  }
}


onMounted(
    () => {
      getAllSinger().then(value => {
        playList.value = value.data.data
        console.log(playList.value.length)
      });
    }
)


</script>


<template>
  <Navigation style="position: absolute;top: 96px;left: 0" :name="nameOpt" :style-list="singer_style_list"
              @click="doClick"/>
  <PlayBody style="position: absolute;top: 146px;left: 0" :play-list="playList"/>
</template>


<style scoped>

</style>