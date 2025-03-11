<script setup lang="ts">
import Navigation from "@/layouts/Navigation.vue";
import {onMounted, ref} from "vue";
import {SingerEnum, singer_style_list} from "@/enum/SingerEnum";
import {getAllSinger, getSingerBySex} from "@/api/singer/SingerApi";
import PlayBody from "@/layouts/PlayBody.vue";
import {Behavior} from "@/enum/Behavior";
import {useRouter} from "vue-router";
import {useSingersStore} from "@/store/SingersStore";


// const nameOpt = ref('全部歌手');
const playList = ref<Array<Singer>>();
const router = useRouter();

const singersStores = useSingersStore();

const doClick = (item) => {
  console.log(item);
  singersStores.setCurrentNameOptIdx(item.id);
  if (item.id === SingerEnum.ALL_SINGER) {
    getAllSinger().then(value => {
      playList.value = value.data.data;
      singersStores.setSingers(playList.value);
    })
  } else {
    getSingerBySex(item.id).then(value => {
      playList.value = value.data.data;
      singersStores.setSingers(playList.value);
    })
  }
}
const handlePopState = (event) => {
  console.log(event);
  // console.log('后退按钮被点击');
}

onMounted(
    () => {
      if (singersStores.getCurrentNameOptIdx === SingerEnum.ALL_SINGER) {
        getAllSinger().then(value => {
          playList.value = value.data.data
          singersStores.setSingers(playList.value);
          console.log('total singers:', playList.value.length);
        });
        // } else if (singersStores.getCurrentNameOpt === '女歌手') {
      } else {
        getSingerBySex(singersStores.getCurrentNameOptIdx).then(value => {
          playList.value = value.data.data
          singersStores.setSingers(playList.value);
          console.log('total singers:', playList.value.length);
        });
      }


      window.addEventListener('popstate', handlePopState);
    }
)

const doSingerDetail = (idx: number) => {
  console.log('singer_idx', idx);

  router.push(Behavior.SINGER_DETAIL + '/' + idx);
}

</script>


<template>
  <Navigation style="position: absolute;top: 96px;left: 0"
              :name="singer_style_list[singersStores.getCurrentNameOptIdx].name"
              :style-list="singer_style_list"
              @click="doClick"/>
  <PlayBody style="position: absolute;top: 146px;left: 0" :play-list="playList"
            @click="doSingerDetail"/>
</template>


<style scoped>

</style>