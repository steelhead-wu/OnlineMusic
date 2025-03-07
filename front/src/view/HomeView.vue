<script setup lang="ts">
import PlayBody from "@/layouts/PlayBody.vue";
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";
import {onMounted, ref} from "vue";
import {getRandomSongList} from "@/api/songList/SongListApi";
import {getRandomSinger} from "@/api/singer/SingerApi";
import {Behavior} from "@/enum/Behavior.js";
import {useRouter} from "vue-router";

const router = useRouter();

const songList = ref([]);
const singerList = ref([]);

onMounted(() => {
  refreshSongList();
  refreshSinger();
})

const refreshSongList = () => {
  getRandomSongList(10).then(value => {
    if (value.data.data) {
      songList.value = value.data.data;
    }
  })
}

const refreshSinger = () => {
  getRandomSinger(10).then(value => {
    if (value.data.data) {
      singerList.value = value.data.data;
    }
  })
}

const doSingerDetail = (idx: number) => {
  console.log('singer_idx', idx);

  router.push(Behavior.SINGER_DETAIL + '/' + idx);
}

</script>

<template>
  <div id="homeView">
    <div class="songList">
      <div class="area-header">
        <h2>随机歌单</h2>

        <ul class="refresh">
          <li>
            <FontAwesomeIcon style="cursor: pointer" icon="fa-refresh" size="2x" @click="refreshSongList"/>
          </li>
        </ul>
      </div>

      <div class="random-song-list">
        <PlayBody :play-list="songList" @click="doSingerDetail"/>
      </div>

    </div>
    <div class="singer">
      <div class="area-header">
        <h2>随机歌手</h2>

        <ul class="refresh">
          <li>
            <FontAwesomeIcon style="cursor: pointer" icon="fa-refresh" size="2x"
                             @click="refreshSinger"
            />
          </li>
        </ul>
      </div>

      <div class="random-singer-list">
        <PlayBody :play-list="singerList" @click="doSingerDetail"/>
      </div>
    </div>
  </div>
</template>

<style scoped lang="scss">
#homeView {
  height: 100%;
  width: 100%;

  .songList {
    .area-header {
      display: flex;
      align-items: center;


      .refresh {
        margin-top: 10px;
        margin-left: auto;
        margin-right: 40px;

        list-style: none;

      }

      h2 {
        margin-top: 10px;
        margin-left: 745px;

      }
    }

    .random-song-list {
      margin-top: 10px;
    }
  }

  .singer {
    .area-header {
      display: flex;
      align-items: center;


      .refresh {
        margin-top: 10px;
        margin-left: auto;
        margin-right: 40px;


        list-style: none;
      }

      h2 {
        margin-top: 10px;
        margin-left: 745px;

      }
    }

    .random-singer-list {
      margin-top: 10px;
    }
  }
}
</style>