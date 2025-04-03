<script setup lang="ts">
import Navigation from "@/layouts/Navigation.vue";
import {onMounted, ref} from "vue";
import {SongListEnum, song_list_style} from "@/enum/SongListEnum";
import PlayBody from "@/layouts/PlayBody.vue";
import {Behavior} from "@/enum/Behavior";
import {useRouter} from "vue-router";
import {useSingersStore} from "@/store/SingersStore";
import {getAllSongList, getSongListByStyle} from "@/api/songList/SongListApi";


// interface

const playList = ref<Array<SongList>>();
const router = useRouter();

const singersStores = useSingersStore();

const doStyleClick = (item) => {
  console.log('item', item);
  singersStores.setCurrentSongListNameOptIdx(item.id);
  if (item.id === SongListEnum.ALL_SONG_LIST) {
    getAllSongList().then(value => {
      playList.value = value.data.data;
      singersStores.setSongList(playList.value);
    })
  } else {
    getSongListByStyle(item.id).then(value => {
      playList.value = value.data.data;
      singersStores.setSongList(playList.value);
    })
  }
}

onMounted(
    () => {
      if (singersStores.getCurrentSongListNameOptIdx === SongListEnum.ALL_SONG_LIST) {
        getAllSongList().then(value => {
          playList.value = value.data.data
          singersStores.setSongList(playList.value);
          console.log('total singers:', playList.value.length);
        });
        // } else if (singersStores.getCurrentNameOpt === '女歌手') {
      } else {

        getSongListByStyle(song_list_style[singersStores.getCurrentSongListNameOptIdx].id).then(value => {
          playList.value = value.data.data
          singersStores.setSongList(playList.value);
          console.log('total singers:', playList.value.length);
        });
      }


    }
)

const doSingerDetail = (idx: number) => {
  console.log('singer_idx', idx);

  router.push(Behavior.SONG_LIST_DETAIL + '/' + idx);
}

</script>


<template>
  <Navigation style="position: absolute;top: 96px;left: 0"
              :name="song_list_style[singersStores.getCurrentSongListNameOptIdx].name"
              :style-list="song_list_style"
              @click="doStyleClick"/>
  <PlayBody style="position: absolute;top: 146px;left: 0" :play-list="playList"
            @click="doSingerDetail"/>
</template>


<style scoped>

</style>