<script lang="ts" setup>
import {computed, ref} from 'vue'
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";
import {baseURL} from "@/api/request";
import {download} from "@/api/song/SongApi";
import {useSongStore} from "@/store/SongStore";
import MusicAudio from "@/components/play/MusicAudio.vue";

const songStore = useSongStore()
const value4 = ref(0)
const music = ref(null);
const cover = ref();

// current song play
const song = computed(() => songStore.getCurrentSong.value);
// music link
const music_src = computed(() => baseURL + song.value.url);

const music_pic_src = computed(() => baseURL + song.value.picture);

const formatTooltip = (val: number) => {
  return val / 100
}


const doDownloadMusic = () => {
  if (song.value.title === '') return;
  try {
    download(song.value).then(value => {
      console.log("开始下载...");
      const eleLink = document.createElement("a");
      eleLink.download = song.value.url.slice(song.value.url.lastIndexOf('/') + 1);
      eleLink.style.display = "none";
      // // 字符内容转变成 blob 地址
      const blob = new Blob([value.data]);
      eleLink.href = URL.createObjectURL(blob);
      document.body.appendChild(eleLink); // 触发点击
      eleLink.click();
      document.body.removeChild(eleLink); // 移除

    })
  } catch (error) {
    console.error('下载失败:', error);
    if (error.response && error.response.status === 404) {
      alert('文件不存在！');
    } else {
      alert('文件下载失败，请稍后重试！');
    }
  }
}
const toggle_music_status = () => {
  songStore.setIsPlay();
  console.log("current song is")
  console.log(songStore.getCurrentSong.value);
  console.log('----------------')
  if (songStore.getIsPlay.value) {
    cover.value.style['animationPlayState'] = 'running';
    music.value.play();
  } else {
    cover.value.style['animationPlayState'] = 'paused';
    music.value.pause();
  }

}


const playMusic = () => {
  music.value.load(); // 强制加载新资源
  music.value.addEventListener('canplay', () => {
    if (!songStore.getIsPlay.value) {
      songStore.setIsPlay();
    }
    cover.value.style['animationPlayState'] = 'running';
    music.value.play();
  }, {once: true});
}
const randomIdx = (): number => {
  let number;
  do {
    number = Math.floor(Math.random() * songStore.getSongList.value.length);
  } while (number === songStore.getCurrentSongIdx.value);
  return number;
}

const next = () => {
  console.log("cur song:")
  console.log(song.value);
  console.log('--------------')
  console.log(songStore.getCurrentSongIdx.value);
  if (isLoop.value) {
    songStore.incSongIdx();
  } else {
    songStore.setCurrentSongIdx(randomIdx());
  }
  console.log(songStore.getCurrentSongIdx.value);
  console.log("next song:")
  console.log(song.value);
  console.log('--------------')
  // music.value.play();
  playMusic();
}


const previous = () => {
  console.log("cur song:")
  console.log(song.value);
  console.log('--------------')
  console.log(songStore.getCurrentSongIdx.value);
  songStore.decSongIdx();
  console.log(songStore.getCurrentSongIdx.value);
  console.log("next song:")
  console.log(song.value);
  console.log('--------------')
  // music.value.play();
  playMusic();
}

const playThisMusic = (song: Song, song_idx: number) => {
  songStore.setCurrentSongIdx(song_idx);
  songStore.setCurrentSong(song);
  playMusic();
}


const ended = () => {
  console.log("come ended");
  music.value.addEventListener('ended', next);
}

const showPlayList = ref(true);

// loop playback
const isLoop = ref(true);

</script>

<template>
  <div id="playBar">
    <div class="song-process">
      <el-slider v-model="value4" :format-tooltip="formatTooltip"/>
    </div>

    <div class="control-box">
      <div class="cover" ref="cover">
        <img id="cover_img" :src="music_pic_src" alt="" srcset="">
      </div>

      <div class="control-btn-area">

        <!--        收藏-->
        <FontAwesomeIcon icon="fa fa-heart" size="2x" style="color: red;"/>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <!--      循环播放  -->
        <FontAwesomeIcon v-if="isLoop" class="control-btn-each" icon="fa-refresh" size="2x" @click="isLoop = !isLoop"/>

        <FontAwesomeIcon v-else class="control-btn-each" icon="fa-random" size="2x" @click="isLoop = !isLoop"/>


        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <!--        上一首-->
        <FontAwesomeIcon class="control-btn-each" icon="fa-step-backward" size="2x" @click="previous"/>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <!--        暂停-->
        <FontAwesomeIcon class="control-btn-each" v-if="songStore.getIsPlay.value" icon="fa-pause" size="2x"
                         @click="toggle_music_status"/>

        <!--        播放-->
        <FontAwesomeIcon v-else icon="fa-play" size="2x" @click="toggle_music_status"/>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <!--        下一首-->
        <FontAwesomeIcon class="control-btn-each" icon="fa-step-forward" size="2x" @click="next"/>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <!--        下载-->
        <FontAwesomeIcon icon="fa-cloud-download" size="2x" @click="doDownloadMusic"/>

        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <!--        歌曲列表-->
        <FontAwesomeIcon @click="showPlayList=!showPlayList" icon="fa-navicon" size="2x"/>
        <transition name="slide-fade">
          <div v-if="showPlayList" id="showPlayList">
            <h2 class="title">当前播放</h2>
            <div class="control">共 {{ songStore.getSongList.value.length }} 首</div>
            <ul id="playlist">
              <li v-for="(asong,song_idx) in songStore.getSongList.value"
                  :key="asong.id"
                  @click="playThisMusic(asong,song_idx)"
                  :class="{'current-play' : song.id === asong.id}"
              >
                {{ asong.title.split('-')[1] + '-  ' + asong.title.split('-')[0] }}
              </li>
              <!--              <li v-for="(song,song_idx) in songStore.getSongList.value"-->
              <!--                  :key="song.id"-->
              <!--                  @click="playThisMusic(song,song_idx)"-->
              <!--                  :class="{'current-play' : song.id === $data.song.id}"-->
              <!--              >-->
              <!--                {{$options.song}}-->
              <!--                {{ song.title.split('-')[1] + '-  ' + song.title.split('-')[0] }}-->
              <!--              </li>-->
            </ul>
          </div>
        </transition>

        <audio controls ref="music" id="myAudio" :src="music_src" type="audio/mpeg" @ended="ended"></audio>

      </div>
    </div>
  </div>
</template>


<style scoped>

.current-play {
  color: black;
  font-weight: bold;
}

.title, .control {
  padding-left: 20px;
  box-sizing: border-box;
}


#playlist {
  width: 100%;
  /*height: calc(100% - 10px);*/
  height: 100%;
  cursor: pointer;
  z-index: 100;
  overflow: scroll;
  white-space: nowrap;
}


#playlist li {
  display: block;
  width: 100%;
  height: 40px;
  line-height: 40px;
}


#playlist li:hover {
  background-color: #EFEFEF;
}

.control {
  margin: 3px 0;
  color: grey;
}

#playBar {
  /*width: 100%;*/
  /*position: fixed;*/
  /*bottom: 0;*/

}


.control-box .control-btn-area {
  position: relative;
  bottom: 70px;
  left: 245px;
  z-index: 1;
}

.control-btn-each:hover {
  color: red;
}

@keyframes spin {
  from
    /*0% */
  {
    transform: rotate(0deg);
  }
  to
    /*100%*/
  {
    transform: rotate(360deg);
  }
}

.control-box .cover {
  width: 100px;
  height: 100px;
  border-radius: 50%;
  /*background-color: #fff;*/
  background-color: snow;
  /*margin-top: -60px;*/
  padding: 6px;
  position: relative;
  left: 20px;
  bottom: 0;
  animation: spin 5s infinite linear;
  animation-play-state: paused;
  /*animation-play-state: running;*/

}

.cover img {
  width: 100%;
  height: 100%;
  border-radius: 50%;
  position: relative;
  /*left: 20px;*/
}


#playBar .control-box {
  width: 100%;
  height: 100px;
  position: fixed;
  left: 0;
  bottom: 50px;
  z-index: 1;
  background-color: snow;
}

#playBar .song-process {
  position: fixed;
  z-index: 100;
  bottom: 150px;
  width: 100%;
  transition: all 0.5s;
  left: 0;
}

.slider-demo-block {
  max-width: 600px;
  display: flex;
  align-items: center;

}

.slider-demo-block .el-slider {
  margin-top: 0;
  margin-left: 12px;
}

.slider-demo-block .demonstration {
  font-size: 14px;
  color: var(--el-text-color-secondary);
  line-height: 44px;
  flex: 1;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  margin-bottom: 0;
}

.slider-demo-block .demonstration + .el-slider {
  flex: 0 0 70%;
}


#showPlayList {
  font-size: 14px;
  width: 350px;
  position: fixed;
  right: 0;
  top: 80px;
  bottom: 180px;
  padding-bottom: 100px;
  z-index: 99;
  background-color: white;
  box-shadow: 1px 1px 10px rgba(0, 0, 0, 0.4);
  overflow: hidden;
}

.slide-fade-enter-active {
  transition: all 0.3s ease-out;
}

.slide-fade-leave-active {
  transition: all 0.1s cubic-bezier(1, 0.5, 0.8, 1);
}

.slide-fade-enter-from,
.slide-fade-leave-to {
  transform: translateX(20px);
  opacity: 0;
}


</style>