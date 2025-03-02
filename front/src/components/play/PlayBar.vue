<script lang="ts" setup>
import {Picture as IconPicture} from '@element-plus/icons-vue'
import {computed, onMounted, ref} from 'vue'
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";
import {baseURL} from "@/api/request";
import {download} from "@/api/song/SongApi";
import {useSongStore} from "@/store/SongStore";

const songStore = useSongStore()

const music = ref(null);
const cover = ref();

const progress = ref(0);

// current song play
// const song = computed(() => songStore.getCurrentSong);
// console.log('song.value:',song.value);
// music link
const music_src = computed(() => {
  if (songStore.getCurrentSong === null) {
    songStore.$reset();
    return '';
  }
  return baseURL + songStore.getCurrentSong.url;
});

const music_pic_src = computed(() => {
  if (songStore.getCurrentSong === null) {
    songStore.$reset();
    return '';
  }
  console.log('songStore.getCurrentSong', songStore.getCurrentSong);
  return baseURL + songStore.getCurrentSong.picture;
});


const doDownloadMusic = () => {
  if (songStore.getCurrentSong == null || songStore.getCurrentSong.title === '') return;
  try {
    download(songStore.getCurrentSong).then(value => {
      console.log("开始下载...");
      const eleLink = document.createElement("a");
      eleLink.download = songStore.getCurrentSong.url.slice(songStore.getCurrentSong.url.lastIndexOf('/') + 1);
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
  songStore.flipIsPlay();
  console.log("current song is")
  console.log(songStore.getCurrentSong);
  console.log('----------------')
  if (songStore.getIsPlay) {
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
    if (!songStore.getIsPlay) {
      songStore.flipIsPlay();
    }
    cover.value.style['animationPlayState'] = 'running';
    music.value.play();
  }, {once: true});
}
const randomIdx = (): number => {
  let number;
  do {
    number = Math.floor(Math.random() * songStore.getSongList.length);
  } while (number === songStore.getCurrentSongIdx);
  return number;
}

const next = () => {
  console.log("cur song:")
  console.log(songStore.getCurrentSong);
  console.log('--------------')
  console.log(songStore.getCurrentSongIdx);
  if (isLoop.value) {
    songStore.incSongIdx();
  } else {
    songStore.setCurrentSongIdx(randomIdx());
  }
  console.log(songStore.getCurrentSongIdx);
  console.log("next song:")
  console.log(songStore.getCurrentSong);
  console.log('--------------')
  // music.value.play();
  playMusic();
}


const previous = () => {
  console.log("cur song:")
  console.log(songStore.getCurrentSong);
  console.log('--------------')
  console.log(songStore.getCurrentSongIdx);
  if (isLoop.value) {
    songStore.decSongIdx();
  } else {
    songStore.setCurrentSongIdx(randomIdx());
  }
  console.log(songStore.getCurrentSongIdx);
  console.log("previous song:")
  console.log(songStore.getCurrentSong);
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
  progress.value = 0;
  music.value.addEventListener('ended', next);
}
// song play bar in right aside
const showPlayList = ref(false);

// loop playback
const isLoop = ref(true);

const changeTime = () => {
  console.log("come changeTime");
  music.value.currentTime = progress.value;
  songStore.setIsPlay(true);
  cover.value.style['animationPlayState'] = 'running';
  music.value.play();
}


const songTime = ref<number>();

const isUserChanging = ref<boolean>(false);

onMounted(() => {
  console.log('mounted');
  if (music.value) {
    music.value.addEventListener('loadedmetadata', () => {
      songTime.value = music.value.duration;
      console.log(`music duration:${music.value.duration} sec`);
    });

    music.value.addEventListener('timeupdate', () => {
      if (!isUserChanging.value) {
        progress.value = music.value.currentTime;
        console.log('Current Time:', music.value.currentTime);
      }
    });
  }
});

// handle progress bar when progress changed
const onProgressChange = (value: number) => {
  console.log('onProgressChange');
  //  when user manually changed the progress bar
  isUserChanging.value = true;
  progress.value = value;
  if (music.value) {
    music.value.currentTime = value;
  }
  // user's OP has finished
  isUserChanging.value = false;
};

const formatTooltip = (progress: number) => {
  progress = Math.round(progress);
  console.log('progress:' + progress);
  let min = Math.floor(progress / 60), sec = progress % 60;
  console.log('min:' + min);
  console.log('sec:' + sec);
  if (min) {
    return `0${min}:` + (sec < 10 ? `0${sec}` : sec);
  } else {
    return `00:` + (sec < 10 ? `0${sec}` : sec);
  }
}


const doRemoveSong = (song_idx: number) => {
  if (song_idx < songStore.getCurrentSongIdx) {
    songStore.setCurrentSongIdx(songStore.getCurrentSongIdx - 1);
  } else if (song_idx === songStore.getCurrentSongIdx) {
    if (songStore.getCurrentSongIdx + 1 === songStore.getSongList.length) {
      songStore.setCurrentSongIdx(songStore.getCurrentSongIdx - 1);
    }
  }
  songStore.getSongList.splice(song_idx, 1);
  console.log('removed song_idx:', song_idx)
  console.log('removed song:', songStore.getCurrentSong);
  console.log(songStore.getSongList);
}
</script>

<template>
  <!--  <div id="playBar">-->
  <!--    &lt;!&ndash;    progress bar&ndash;&gt;-->
  <!--    <div class="song-progress">-->
  <!--      <el-slider :max="songTime" id="progress" v-model="progress" @change="changeTime"-->
  <!--                 @input="onProgressChange" :format-tooltip="formatTooltip"-->
  <!--      />-->
  <!--    </div>-->

  <!--    <div class="control-box">-->
  <!--      <div class="cover" ref="cover">-->
  <!--        <el-image id="cover_img" :src="music_pic_src" alt="" srcset="">-->
  <!--          <template #error>-->
  <!--            <div class="image-slot">-->
  <!--              <el-icon>-->
  <!--                <icon-picture/>-->
  <!--              </el-icon>-->
  <!--            </div>-->
  <!--          </template>-->
  <!--        </el-image>-->
  <!--      </div>-->

  <!--      <div class="control-btn-area">-->

  <!--        &lt;!&ndash;        收藏&ndash;&gt;-->
  <!--        <FontAwesomeIcon icon="fa fa-heart" :class="{'fa-disabled':songStore.getCurrentSong==null}" size="2x"-->
  <!--                         style="color: red;"/>-->
  <!--        &lt;!&ndash;        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&ndash;&gt;-->
  <!--        &lt;!&ndash;      循环播放  &ndash;&gt;-->
  <!--        <FontAwesomeIcon v-if="isLoop" class="control-btn-each" icon="fa-refresh" size="2x" @click="isLoop = !isLoop"/>-->

  <!--        <FontAwesomeIcon v-else class="control-btn-each" icon="fa-random" size="2x" @click="isLoop = !isLoop"/>-->


  <!--        &lt;!&ndash;        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&ndash;&gt;-->
  <!--        &lt;!&ndash;        上一首&ndash;&gt;-->
  <!--        <FontAwesomeIcon class="control-btn-each" :class="{'fa-disabled':songStore.getCurrentSong==null}"-->
  <!--                         icon="fa-step-backward" size="2x" @click="previous"/>-->
  <!--        &lt;!&ndash;        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&ndash;&gt;-->
  <!--        &lt;!&ndash;        暂停&ndash;&gt;-->
  <!--        <FontAwesomeIcon class="control-btn-each" v-if="songStore.getIsPlay" icon="fa-pause" size="2x"-->
  <!--                         @click="toggle_music_status"/>-->

  <!--        &lt;!&ndash;        播放&ndash;&gt;-->
  <!--        <FontAwesomeIcon v-else icon="fa-play" :class="{'fa-disabled':songStore.getCurrentSong==null}" size="2x"-->
  <!--                         @click="toggle_music_status"/>-->
  <!--        &lt;!&ndash;        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&ndash;&gt;-->
  <!--        &lt;!&ndash;        下一首&ndash;&gt;-->
  <!--        <FontAwesomeIcon class="control-btn-each" :class="{'fa-disabled':songStore.getCurrentSong==null}"-->
  <!--                         icon="fa-step-forward" size="2x" @click="next"-->
  <!--        />-->
  <!--        &lt;!&ndash;        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&ndash;&gt;-->
  <!--        &lt;!&ndash;        下载&ndash;&gt;-->
  <!--        <FontAwesomeIcon icon="fa-cloud-download" :class="{'fa-disabled':songStore.getCurrentSong==null}" size="2x"-->
  <!--                         @click="doDownloadMusic"/>-->

  <!--        &lt;!&ndash;        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&ndash;&gt;-->
  <!--        &lt;!&ndash;        歌曲列表&ndash;&gt;-->
  <!--        <FontAwesomeIcon title="queue" @click="showPlayList=!showPlayList" icon="fa-navicon" size="2x"/>-->
  <!--        <transition name="slide-fade">-->
  <!--          <div v-if="showPlayList" id="showPlayList">-->
  <!--            <h2 class="title">当前播放</h2>-->
  <!--            <div class="control">共 {{ songStore.getSongList.length }} 首</div>-->
  <!--            <ul id="playlist">-->
  <!--              <li v-for="(asong,song_idx) in songStore.getSongList"-->
  <!--                  :key="asong.id"-->
  <!--                  @click="playThisMusic(asong,song_idx)"-->
  <!--                  :class="{'current-play' : songStore.getCurrentSong.id === asong.id && song_idx === songStore.getCurrentSongIdx}"-->
  <!--              >-->
  <!--                {{ asong.title.split('-')[1] + '-  ' + asong.title.split('-')[0] }}-->
  <!--                <FontAwesomeIcon icon="fa-remove" size="ls" class="remove" @click.stop="doRemoveSong(song_idx)"/>-->
  <!--              </li>-->
  <!--            </ul>-->
  <!--          </div>-->
  <!--        </transition>-->

  <!--        <audio preload="metadata" ref="music" id="myAudio" :src="music_src" type="audio/mpeg"-->
  <!--               @ended="ended"></audio>-->

  <!--      </div>-->
  <!--    </div>-->
  <!--  </div>-->

  <!--  <div class="playBar-area">-->
  <!--    <div class="song-progress">-->
  <!--      <el-slider :max="songTime" id="progress" v-model="progress" @change="changeTime"-->
  <!--                 @input="onProgressChange" :format-tooltip="formatTooltip"-->
  <!--      />-->
  <!--    </div>-->

  <!--    <div class="playBar">-->
  <!--      &lt;!&ndash;      <div class="cover" ref="cover">&ndash;&gt;-->
  <!--      <el-image id="cover_img" :src="music_pic_src" alt="" srcset="">-->
  <!--        <template #error>-->
  <!--          <div class="image-slot">-->
  <!--            <el-icon>-->
  <!--              <icon-picture/>-->
  <!--            </el-icon>-->
  <!--          </div>-->
  <!--        </template>-->
  <!--      </el-image>-->
  <!--      &lt;!&ndash;      </div>&ndash;&gt;-->

  <!--      &lt;!&ndash;      <div class="control-btn-area">&ndash;&gt;-->

  <!--      &lt;!&ndash;        收藏&ndash;&gt;-->
  <!--      <FontAwesomeIcon icon="fa fa-heart" :class="{'fa-disabled':songStore.getCurrentSong==null}" size="2x"-->
  <!--                       style="color: red;"/>-->
  <!--      &lt;!&ndash;        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&ndash;&gt;-->
  <!--      &lt;!&ndash;      循环播放  &ndash;&gt;-->
  <!--      <FontAwesomeIcon v-if="isLoop" class="control-btn-each" icon="fa-refresh" size="2x" @click="isLoop = !isLoop"/>-->

  <!--      <FontAwesomeIcon v-else class="control-btn-each" icon="fa-random" size="2x" @click="isLoop = !isLoop"/>-->


  <!--      &lt;!&ndash;        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&ndash;&gt;-->
  <!--      &lt;!&ndash;        上一首&ndash;&gt;-->
  <!--      <FontAwesomeIcon class="control-btn-each" :class="{'fa-disabled':songStore.getCurrentSong==null}"-->
  <!--                       icon="fa-step-backward" size="2x" @click="previous"/>-->
  <!--      &lt;!&ndash;        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&ndash;&gt;-->
  <!--      &lt;!&ndash;        暂停&ndash;&gt;-->
  <!--      <FontAwesomeIcon class="control-btn-each" v-if="songStore.getIsPlay" icon="fa-pause" size="2x"-->
  <!--                       @click="toggle_music_status"/>-->

  <!--      &lt;!&ndash;        播放&ndash;&gt;-->
  <!--      <FontAwesomeIcon v-else icon="fa-play" :class="{'fa-disabled':songStore.getCurrentSong==null}" size="2x"-->
  <!--                       @click="toggle_music_status"/>-->
  <!--      &lt;!&ndash;        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&ndash;&gt;-->
  <!--      &lt;!&ndash;        下一首&ndash;&gt;-->
  <!--      <FontAwesomeIcon class="control-btn-each" :class="{'fa-disabled':songStore.getCurrentSong==null}"-->
  <!--                       icon="fa-step-forward" size="2x" @click="next"-->
  <!--      />-->
  <!--      &lt;!&ndash;        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&ndash;&gt;-->
  <!--      &lt;!&ndash;        下载&ndash;&gt;-->
  <!--      <FontAwesomeIcon icon="fa-cloud-download" :class="{'fa-disabled':songStore.getCurrentSong==null}" size="2x"-->
  <!--                       @click="doDownloadMusic"/>-->

  <!--      &lt;!&ndash;        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&ndash;&gt;-->
  <!--      &lt;!&ndash;        歌曲列表&ndash;&gt;-->
  <!--      <FontAwesomeIcon title="queue" @click="showPlayList=!showPlayList" icon="fa-navicon" size="2x"/>-->
  <!--      <transition name="slide-fade">-->
  <!--        <div v-if="showPlayList" id="showPlayList">-->
  <!--          <h2 class="title">当前播放</h2>-->
  <!--          <div class="control">共 {{ songStore.getSongList.length }} 首</div>-->
  <!--          <ul id="playlist">-->
  <!--            <li v-for="(asong,song_idx) in songStore.getSongList"-->
  <!--                :key="asong.id"-->
  <!--                @click="playThisMusic(asong,song_idx)"-->
  <!--                :class="{'current-play' : songStore.getCurrentSong.id === asong.id && song_idx === songStore.getCurrentSongIdx}"-->
  <!--            >-->
  <!--              {{ asong.title.split('-')[1] + '-  ' + asong.title.split('-')[0] }}-->
  <!--              <FontAwesomeIcon icon="fa-remove" size="ls" class="remove" @click.stop="doRemoveSong(song_idx)"/>-->
  <!--            </li>-->
  <!--          </ul>-->
  <!--        </div>-->
  <!--      </transition>-->

  <!--      <audio preload="metadata" ref="music" id="myAudio" :src="music_src" type="audio/mpeg"-->
  <!--             @ended="ended"></audio>-->

  <!--      &lt;!&ndash;      </div>&ndash;&gt;-->

  <!--    </div>-->
  <!--  </div>-->
  <div class="audio-module">
    <div class="player-module">
      <div class="left-part">
        <!--        上一首-->
        <FontAwesomeIcon class="control-btn-each" :class="{'fa-disabled':songStore.getCurrentSong==null}"
                         icon="fa-step-backward" size="2x" @click="previous"/>

        <!--        暂停-->
        <FontAwesomeIcon class="control-btn-each" v-if="songStore.getIsPlay" icon="fa-pause" size="2x"
                         @click="toggle_music_status"/>

        <!--        播放-->
        <FontAwesomeIcon v-else icon="fa-play" :class="{'fa-disabled':songStore.getCurrentSong==null}" size="2x"
                         @click="toggle_music_status"/>

        <!--        下一首-->
        <FontAwesomeIcon class="control-btn-each" :class="{'fa-disabled':songStore.getCurrentSong==null}"
                         icon="fa-step-forward" size="2x" @click="next"/>
      </div>

      <div class="main-part">
        <div class="cover-part" ref="cover">
          <el-image class="cover-img" :src="music_pic_src" alt="" srcset="">
            <template #error>
              <div class="image-slot">
                <el-icon>
                  <icon-picture/>
                </el-icon>
              </div>
            </template>
          </el-image>
        </div>
        <div class="song-part">
          <div class="song-name">{{ songStore.getCurrentSong.title }}</div>
          <div class="song-progress">
            <el-slider :max="songTime" id="progress" v-model="progress" @change="changeTime"
                       @input="onProgressChange"
                       :format-tooltip="formatTooltip"/>
          </div>
          <audio preload="metadata" ref="music" id="myAudio" :src="music_src" type="audio/mpeg" @ended="ended">
          </audio>
        </div>
      </div>

      <div class="right-part">
        <!--      循环播放  -->
        <FontAwesomeIcon v-if="isLoop" class="control-btn-each" icon="fa-refresh" size="2x" @click="isLoop = !isLoop"/>

        <FontAwesomeIcon v-else class="control-btn-each" icon="fa-random" size="2x" @click="isLoop = !isLoop"/>


        <!--        下载-->
        <FontAwesomeIcon icon="fa-cloud-download" :class="{'fa-disabled':songStore.getCurrentSong==null}" size="2x"
                         @click="doDownloadMusic"/>
        <!--        volume-->
        <FontAwesomeIcon icon="fa-volume-up" :class="{'fa-disabled':songStore.getCurrentSong==null}" size="2x"
                         @click="doDownloadMusic"/>

        <!--        <FontAwesomeIcon icon="fa-volume-off" :class="{'fa-disabled':songStore.getCurrentSong==null}" size="2x"-->
        <!--                         @click="doDownloadMusic"/>-->

        <!--        歌曲列表-->
        <FontAwesomeIcon title="queue" @click="showPlayList=!showPlayList" icon="fa-navicon" size="2x"/>
        <transition name="slide-fade">
          <div v-if="showPlayList" class="showPlayList">
            <h2 class="title">当前播放</h2>
            <div class="control">共 {{ songStore.getSongList.length }} 首</div>
            <ul class="playlist">
              <li v-for="(asong,song_idx) in songStore.getSongList"
                  :key="asong.id"
                  @click="playThisMusic(asong,song_idx)"
                  :class="{'current-play' : songStore.getCurrentSong.id === asong.id && song_idx === songStore.getCurrentSongIdx}"
              >
                {{ asong.title.split('-')[1] + '-  ' + asong.title.split('-')[0] }}
                <FontAwesomeIcon icon="fa-remove" size="ls" class="remove" @click.stop="doRemoveSong(song_idx)"/>
              </li>
            </ul>
          </div>
        </transition>
      </div>
    </div>

    <div class="showHide-playbar"></div>
  </div>

</template>


<style scoped lang="scss">

.audio-module {
  position: fixed;
  min-width: 940px;
  width: 100%;
  height: 100px;
  bottom: 0;
  z-index: 1000;
  background: snow;

  .player-module {
    position: relative;
    width: 960px;
    height: 100px;
    margin: 0 auto;

    .left-part {
      position: absolute;
      width: 160px;
      height: 100px;

      display: flex;
      justify-content: space-between;
      align-items: center;

      .control-btn-each {

      }

      .fa-disabled {
        opacity: 0.5;
        pointer-events: none;
      }
    }

    .main-part {
      position: absolute;
      height: 100px;
      width: 540px;
      cursor: pointer;
      margin-left: 160px;
      //margin-top: 10px;
      //background: url(//www.kugou.com/yy/static/images/play/default.jpg) no-repeat left top;
      //background-position: 100% 100%;
      .cover-part {
        width: 90px;
        height: 90px;
        margin-left: 30px;
        margin-top: 5px;
        animation: spin 5s infinite linear;
        animation-play-state: paused;
        /*animation-play-state: running;*/
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

        .cover-img {
          border-radius: 50%;
        }
      }

      .song-part {
        margin-left: 120px;
        height: 100px;
        width: 420px;

        .song-name {
          position: absolute;
          left: 150px;
          bottom: 50px;
        }

        .song-progress {
          position: absolute;
          z-index: 1;
          width: 70%;
          transition: all 0.5s;
          bottom: 0px;
          left: 150px;
        }

      }
    }

    .right-part {
      position: absolute;
      height: 100px;
      width: 260px;
      cursor: pointer;
      margin-left: 710px;

      display: flex;
      justify-content: space-between;
      align-items: center;

      .control-btn-each {

      }

      .fa-disabled {
        opacity: 0.5;
        pointer-events: none;
      }

      .showPlayList {
        font-size: 14px;
        width: 369px;
        position: fixed;
        right: 0;
        top: 80px;
        bottom: 180px;
        padding-bottom: 100px;
        background-color: white;
        box-shadow: 1px 1px 10px rgba(0, 0, 0, 0.4);
        overflow: hidden;


        .title {

        }

        .control {
          margin: 3px 0;
          color: grey;
        }

        .title, .control {
          padding-left: 20px;
          box-sizing: border-box;

        }

        .playlist {
          width: 100%;
          /*height: calc(100% - 10px);*/
          height: 100%;
          padding-left: 20px;
          overflow: scroll;
          white-space: nowrap;

          li {
            display: flex;
            justify-content: space-between; /* 左右内容分列两端 */
            align-items: center; /* 垂直居中 */
            width: 100%;
            height: 40px;
            line-height: 40px;
            position: relative; /* 为子元素定位提供基准 */

            .remove {
              opacity: 0.3; /* 轻微透明度效果 */
              height: 15px;
              width: 15px;
              margin-left: auto; /* 将图标推到最右侧 */
              transition: opacity 0.3s; /* 添加悬停效果 */
              display: none;
              &:hover {
                opacity: 1;
              }

            }

            &:hover {
              background-color: #EFEFEF;
              .remove {
                pointer-events: auto; /* 确保图标可以点击 */
                display: block;
              }
            }
          }

          .current-play {
            color: black;
            font-weight: bold;
          }
        }
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
    }
  }

  .showHide-playbar {

  }

}
</style>