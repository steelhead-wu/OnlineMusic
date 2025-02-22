<script setup lang="ts">
import {useRoute} from "vue-router";
import {useSingersStore} from "@/store/SingersStore";
import {baseURL} from "@/api/request";

import {onMounted, ref} from "vue";
import SongList from "@/components/song/SongList.vue";
import {getSongBySongListID} from "@/api/song/SongApi";
import {getEvenRatingOfSongList, getRatingBy, updateOrSaveRating, updateSongList} from "@/api/songList/SongListApi";
import {useUserStore} from "@/store/UserStore";

const route = useRoute();
const userStore = useUserStore();
const singersStore = useSingersStore();
const current_song_list = ref<SongList>(singersStore.getSongList[route.params.idx]);


const rating = ref(0);
const colors = ref(['#99A9BF', '#F7BA2A', '#FF9900']); // same as { 2: '#99A9BF', 4: { value: '#F7BA2A', excluded: true }, 5: '#FF9900' }
const tableData = ref<Array<unknown>>([]);

onMounted(() => {
  getSongBySongListID(current_song_list.value.id).then(value => {
    for (const song: Song of value.data.data) {
      const split = song.title?.split('-');
      song['song'] = split[1];
      song['singer'] = split[0];
      tableData.value.push(song);
    }
  });

  getRatingBy(userStore.getLoginUser.id, current_song_list.value.id,).then(value => {
    console.log(value.data);
    rating.value = value.data.data.rating;
  })

})


const changeRating = () => {
  updateOrSaveRating(userStore.getLoginUser.id, current_song_list.value.id, rating.value).then(
      value => {
        if (value.data.data) {
          getEvenRatingOfSongList(current_song_list.value.id).then(value1 => {
            if (value1.data.data != current_song_list.value.rating) {
              updateSongList({
                id: current_song_list.value.id,
                rating: value1.data.data
              }).then(value2 => {
                if (value2.data.data) {
                  current_song_list.value.rating = value1.data.data;
                  console.log("更新评分成功");
                } else {
                  console.log("更新评分失败");
                }

              })
            }

          });

        } else {
          console.log("更新评分失败")
        }
      }
  );
}


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


      <div class="rate-block">
        <div class="rating-of-song">
          <h2 class="demonstration">歌单评分</h2>
          <el-rate ref="rater" v-model="singersStore.getSongList[route.params.idx].rating" :colors="colors" show-score
                   disabled/>
        </div>

        <div class="my-rating">
          <h2 class="demonstration">我的评分</h2>
          <el-rate v-model="rating" :colors="colors" show-score @change="changeRating"/>
        </div>

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
    margin-top: 20px;
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

  .rate-block {
    display: flex;
    padding: 0;
    margin-top: 10px;


    .el-rate {
      //margin-left: 10px;
    }

    .rating-of-song {
      margin-top: 10px;

      .demonstration {
        text-align: center;
        font-weight: bold;
      }

    }


    .my-rating {
      margin-top: 10px;
      margin-left: 120px;

      .demonstration {
        text-align: center;
        font-weight: bold;
      }
    }
  }
}
</style>