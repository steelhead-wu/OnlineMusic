<script setup lang="ts">
import {useSongStore} from "@/store/SongStore";
import {baseURL} from "@/api/request";
import {onMounted, reactive, ref, watch} from "vue";

const songStore = useSongStore();

const lrcTop = ref("80px"); // 歌词滑动
const lyricArr = ref([]); // 当前歌曲的歌词
const currentLyricIndex = ref(-1);


// const parseLyrics = () => {
//   const lyricsList = [];
//
//   songStore.getCurrentSong?.lyric.matchAll(/^\[(?<min>\d{2}):(?<sec>\d{2}\.\d{2,})](?<lyric>.+)/gm).forEach(each => {
//     lyricsList.push({time: each.groups.min * 60 + each.groups.sec, lyric: each.groups.lyric});
//   })
//   return lyricsList;
// }

/**
 * find maximum value which is not larger than
 * <code>songStore.getCurrentTime </code>
 * @param lyrics
 */
const findCurrentLyricPos = (lyrics: Array<object>): number => {
  let l = 0, r = lyrics.length - 1;
  while (l < r) {
    let m = l + r + 1 >> 1;
    if (lyrics[m].time <= songStore.getCurrentTime) {
      l = m;
    } else {
      r = m - 1;
    }
  }
  return l;
}

watch(() => songStore.getCurrentSong, () => {
  lyricArr.value = songStore.parseLyrics();
});

watch(() => songStore.getCurrentTime, () => {
  currentLyricIndex.value = findCurrentLyricPos(lyricArr.value);
  console.log('currentLyricIndex', currentLyricIndex.value);
  console.log('10th', lyricArr.value[10]);
})

onMounted(() => {
  lyricArr.value = songStore.parseLyrics();
  currentLyricIndex.value = findCurrentLyricPos(lyricArr.value);
  console.log('www', lyricArr.value);
})

</script>

<template>


  <!--  <div class="song-container">-->
  <!--    <el-image class="song-pic" fit="contain" :src="baseURL + songStore.getCurrentSong.picture"/>-->
  <!--    <ul class="song-info">-->
  <!--      <li>歌手：{{ songStore.getCurrentSong.album }}</li>-->
  <!--      <li>歌曲：{{ songStore.getCurrentSong.title }}</li>-->
  <!--    </ul>-->
  <!--  </div>-->
  <!--  <div class="container">-->
  <!--    <div class="lyric-container">-->
  <!--      <div class="song-lyric">-->
  <!--        <ul class="lyrics">-->
  <!--          <li v-for="(item,idx) in lyricArr"-->
  <!--              :key="idx" :class="{'cur-lyric':currentLyricIndex===idx}">-->
  <!--            {{ item.lyric }}-->
  <!--          </li>-->
  <!--        </ul>-->
  <!--      </div>-->
  <!--            <comment :playId="songId" :type="0"></comment>-->
  <!--    </div>-->
  <!--  </div>-->


  <div class="lyrics-bg">
    <div class="aside-bg">
      <div class=" album-area">
        <el-image class="album-img" :src="baseURL+songStore.getCurrentSong.picture"/>
      </div>

      <div class="singer">
        <h1>歌手：{{ songStore.getCurrentSong.title.split('-')[0] }}</h1>
      </div>

      <div class="album">
        <h1> 专辑：{{ songStore.getCurrentSong.album }}</h1>
      </div>

    </div>
    <div class="main-bg">
      <div class="lyrics-area">
        <div class="song-name">
          {{ songStore.getCurrentSong.title }}
        </div>

        <div class="lyrics-container">
          <ul class="lyrics">
            <li v-for="(item,idx) in lyricArr"
                :key="idx" :class="{'cur-lyric':currentLyricIndex===idx}">
              {{ item.lyric }}
            </li>
          </ul>
        </div>
      </div>
    </div>
  </div>

</template>

<style scoped lang="scss">
.lyrics-bg {
  height: 100%;
  display: flex;
  background-image: linear-gradient(45deg, #03a9f4 20%, #ff82aa 80%);
  cursor: url("../../assets/cursor/default.cur") auto;

  .aside-bg {
    flex: 41;
    position: relative;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    line-height: 50px;
    .album-area {
      .album-img {
        width: 160px;
        height: 160px;
        border-radius: 10%;
      }
    }

    .singer {

    }

    .album {

    }
  }

  .main-bg {
    flex: 59;
    position: relative;

    .lyrics-area {
      height: 100%;
      transition: allease-in-out 0.3s;
      border-radius: 10px;
      background: linear-gradient(to top, rgb(255 255 255 / 0%) 0, rgb(255 255 255 / 2%) 50%, rgb(255 255 255 / 0%) 100%);

      .song-name {
        position: absolute;
        width: 100%;
        margin-top: 20px;
        color: rgba(255, 255, 255, 1);
        font-size: 18px;
        font-weight: bold;
        text-align: center;
        filter: drop-shadow(0px 1px 1px rgba(0, 0, 0, 0.2));
        transition: allease-in-out .5s;

      }

      .lyrics-container {
        position: absolute;
        top: 65px;
        left: 10px;
        right: 50px;
        bottom: 10px;
        font-size: 14px;
        overflow: hidden;
        text-align: center;
        color: #bdbdbe;
        color: rgba(225, 225, 225, .8);
        line-height: 26px;

        .lyrics {
          position: absolute;
          width: 100%;
          top: 0;
          bottom: 0;
          overflow: hidden;
          overflow-y: auto;
          -ms-overflow-style: none;
          scrollbar-width: none;

          .cur-lyric {
            transition: allease-in-out 0.3s;
            font-size: 18px;
            letter-spacing: 1px;
            font-weight: bold;
            filter: drop-shadow(0px 1px 2px rgba(0, 0, 0, 0.2));
            background: linear-gradient(to right, #ffffff 25%, #37d8ff 40%, #ffdd5b 50%, #ff5151 80%);
            -webkit-background-clip: text;
            color: transparent;
          }
        }


        ul {
          list-style: none;
        }
      }
    }
  }
}
</style>