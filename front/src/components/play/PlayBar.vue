<script lang="ts" setup>
import {ref} from 'vue'
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";
import {baseURL} from "@/api/request";
import {download} from "@/api/song/SongApi";
import {useSongStore} from "@/store/SongStore";
import MusicAudio from "@/components/play/MusicAudio.vue";

const songStore = useSongStore()
const value4 = ref(0)
const music = ref();
const cover = ref();
// music link
const music_src = ref(baseURL + songStore.getCurrentSong.value.url);

const music_pic_src = ref(baseURL + songStore.getCurrentSong.value.picture);

const formatTooltip = (val: number) => {
      return val / 100
    }

;

const song = songStore.getCurrentSong.value;

const doDownloadMusic = () => {
  if (song.title === '') return;
  try {
    download(song).then(value => {
      console.log("开始下载...");
      const eleLink = document.createElement("a");
      eleLink.download = song.url.slice(song.url.lastIndexOf('/') + 1);
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
  songStore.isPlay = !songStore.isPlay;

  if (songStore.isPlay) {
    cover.value.style['animationPlayState'] = 'running';
    music.value.play();
  } else {
    cover.value.style['animationPlayState'] = 'paused';
    music.value.pause();
  }

}


function play() {
  console.log("comi")
  console.log(music.value);
  songStore.isPlay = !songStore.isPlay;
  music.value.play();
  // const audio = document.getElementById("myAudio");
  // console.log(audio);
  // audio.play();
}


const showPlayList = ref(true);

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
        <FontAwesomeIcon class="control-btn-each" icon="fa-refresh" size="2x"/>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <!--        上一首-->
        <FontAwesomeIcon class="control-btn-each" icon="fa-step-backward" size="2x"/>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <!--        暂停-->
        <FontAwesomeIcon class="control-btn-each" v-if="songStore.isPlay" icon="fa-pause" size="2x"
                         @click="toggle_music_status"/>

        <!--        播放-->
        <FontAwesomeIcon v-else icon="fa-play" size="2x" @click="toggle_music_status"/>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <!--        下一首-->
        <FontAwesomeIcon class="control-btn-each" icon="fa-step-forward" size="2x"/>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <!--        下载-->
        <FontAwesomeIcon icon="fa-cloud-download" size="2x" @click="doDownloadMusic"/>

        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <!--        歌曲列表-->
        <FontAwesomeIcon @click="showPlayList=!showPlayList" icon="fa-navicon" size="2x"/>
        <transition name="slide-fade">
          <div v-if="showPlayList" id="showPlayList">
            <h2>当前播放</h2>
            <div class="control">共 xxx 首</div>
            <ul id="playlist">
              <li>
                sds
              </li>
            </ul>
          </div>
        </transition>

        <audio ref="music" id="myAudio">
          <source :src="music_src" type="audio/mpeg">
        </audio>

      </div>
    </div>
  </div>
</template>


<style scoped>
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

}
</style>