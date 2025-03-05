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
  <!--  <div class="lyrics-bg">-->
  <!--    <div class="aside-bg">-->
  <!--      <div class="album-area">-->
  <!--        <el-image :src="baseURL + songStore.getCurrentSong.picture" class="album-pic"/>-->
  <!--      </div>-->

  <!--    </div>-->
  <!--    <div class="main-bg">-->
  <!--      <div class="song-info-area">-->
  <!--        <div class="song-name">{{ songStore.getCurrentSong.title }}</div>-->
  <!--        <div class="songDetail">-->
  <!--          <span class="album">{{ songStore.getCurrentSong.album }}</span>-->
  <!--          <span class="singer">{{ songStore.getCurrentSong.album }}</span>-->
  <!--        </div>-->

  <!--        <div class="lyrics-container">-->
  <!--          <div class="lyrics">-->
  <!--            <p>{{songStore.getCurrentSong.lyric}}</p>-->
  <!--          </div>-->
  <!--        </div>-->
  <!--      </div>-->

  <!--    </div>-->
  <!--  </div>-->

  <div class="song-container">
    <el-image class="song-pic" fit="contain" :src="baseURL + songStore.getCurrentSong.picture"/>
    <ul class="song-info">
      <li>歌手：{{ songStore.getCurrentSong.album }}</li>
      <li>歌曲：{{ songStore.getCurrentSong.title }}</li>
    </ul>
  </div>
  <div class="container">
    <div class="lyric-container">
      <div class="song-lyric">
        <transition-group name="lyric-fade" tag="ul">
          <!--          {{ songStore.getCurrentSong.lyric }}-->
          <!--有歌词-->
          <!--          <ul :style="{ top: lrcTop }" class="has-lyric" v-if="lyricArr.length" key="has-lyric">-->
          <!--            <li v-for="(item, index) in lyricArr" :key="index">-->
          <!--              {{ item[1] }}-->
          <!--            </li>-->
          <!--          </ul>-->
          <!--没歌词-->
          <!--          <div v-else class="no-lyric" key="no-lyric">-->
          <!--            <span>暂无歌词</span>-->
          <!--          </div>-->
          <li v-for="(item,idx) in lyricArr"
              :key="idx" :class="{'cur-lyric':currentLyricIndex===idx}">
            {{ item.lyric }}
          </li>

        </transition-group>
      </div>
      <!--            <comment :playId="songId" :type="0"></comment>-->
    </div>
  </div>

</template>

<style scoped lang="scss">
.song-container {
  position: fixed;
  top: 120px;
  left: 50px;
  display: flex;
  flex-direction: column;

  .song-pic {
    height: 300px;
    width: 300px;
    border: 4px solid white;
    border-radius: 12px;
  }

  .song-info {
    width: 300px;

    li {
      width: 100%;
      line-height: 40px;
      font-size: 18px;
      padding-left: 10%;
    }
  }
}

.lyric-container {
  font-family: Lato, Helvetica Neue For Number, -apple-system, BlinkMacSystemFont, Segoe UI, Roboto, PingFang SC, Hiragino Sans GB, Microsoft YaHei,
  Helvetica Neue, Helvetica, Arial, sans-serif;

  .song-lyric {
    position: relative;
    min-height: 300px;
    padding: 30px 0;
    overflow: auto;
    border-radius: 12px;
    background-color: #EFEFEF;

    .has-lyric {
      position: absolute;
      transition: all 1s;

      li {
        width: 100%;
        height: 40px;
        text-align: center;
        font-size: 14px;
        line-height: 40px;

        overflow: hidden;
        text-overflow: ellipsis;

        //list-style: none;
      }
    }

    .no-lyric {
      position: absolute;
      margin: 100px 0;

      span {
        font-size: 18px;
        text-align: center;
      }
    }

    .cur-lyric {
      //color: white;
      transition: allease-in-out 0.3s;
      font-size: 18px;
      letter-spacing: 1px;
      font-weight: bold;
      filter: drop-shadow(0px 1px 2px rgba(0, 0, 0, 0.2));
      background: linear-gradient(to right, #ffffff 25%, #37d8ff 40%, #ffdd5b 50%, #ff5151 80%);
      -webkit-background-clip: text;
      //background-clip: text;
      color: transparent;
    }
  }
}

.lyric-fade-enter,
.lyric-fade-leave-to {
  transform: translateX(30px);
  opacity: 0;
}

.lyric-fade-enter-active,
.lyric-fade-leave-active {
  transition: all 0.3s ease;
}

@media screen and (min-width: 667px) {
  .container {
    padding-top: 30px;
  }
  .lyric-container {
    margin: 0 150px 0px 400px;
  }
}

@media screen and (max-width: 667px) {
  .container {
    padding: 20px;
  }
  .song-container {
    display: none;
  }
}


//.lyrics-bg {
//  width: 70%;
//  height: 100%;
//  margin: 0 auto;
//  display: flex;
//  flex-wrap: nowrap;
//
//  .aside-bg {
//    flex: 1;
//    height: 100%;
//
//    .album-area {
//      height: 60%;
//
//      .album-pic {
//        //width: 50%;
//        //height: 50%;
//      }
//    }
//
//
//  }
//
//  .main-bg {
//    flex: 2;
//    height: 100%;
//
//    .song-info-area {
//      height: 40%;
//
//      .song-name {
//
//      }
//
//      .songDetail {
//        .album {
//          margin-right: 10px;
//          float: left;
//        }
//
//        .singer {
//          //display: block;
//          //width: 225px;
//          //height: 24px;
//          //line-height: 24px;
//          //white-space: nowrap;
//          //overflow: hidden;
//          //text-overflow: ellipsis;
//          //font-size: 15px;
//          //color: #fff;
//        }
//      }
//
//      .lyrics-container {
//        .lyrics {
//
//        }
//      }
//
//    }
//  }
//}

</style>