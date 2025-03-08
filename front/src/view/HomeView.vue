<script setup lang="ts">
import PlayBody from "@/layouts/PlayBody.vue";
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";
import {onMounted, onUnmounted, ref} from "vue";
import {getRandomSongList} from "@/api/songList/SongListApi";
import {getRandomSinger} from "@/api/singer/SingerApi";
import {Behavior} from "@/enum/Behavior.js";
import {useRouter} from "vue-router";
import {useSingersStore} from "@/store/SingersStore";
import {Timer} from "@/api/utils/interface/Timer";

const router = useRouter();

const songList = ref([]);
const singerList = ref([]);

const singersStore = useSingersStore();

// unit: second
const TOTAL_TIME = 20;
const remainingTime = ref(TOTAL_TIME * 1000);
let timer = null;
// const STORE_KEY = 'remainingTime';
const SONG_LIST_KEY = 'SONG_LIST_KEY';
const SINGERS_KEY = 'SINGERS_KEY';
let songList_timer: Timer;
let singers_timer: Timer;


onUnmounted(() => {


  console.log("卸载")
  songList_timer.deconstructor(singersStore.getSongList);
  singers_timer.deconstructor(singersStore.getSingers);

  // localStorage.setItem(SONG_LIST_KEY, JSON.stringify(
  //     {
  //       remainingTime: remainingTime.value,
  //       savedTime: new Date().valueOf(),
  //     }));
  // clearInterval(timer);

  // localStorage.setItem(SONG_LIST_KEY, JSON.stringify(
  //     {
  //       remainingTime: songList_timer.remainingTime,
  //       savedTime: new Date().valueOf(),
  //     }));
  //
  // localStorage.setItem(SINGERS_KEY, JSON.stringify(
  //     {
  //       remainingTime: singers_timer.remainingTime,
  //       savedTime: new Date().valueOf(),
  //     }));
  //
  // clearInterval(songList_timer.timer);
  // clearInterval(singers_timer.timer);
});

onMounted(() => {
  // refreshSongList();
  // refreshSinger();
  // initializeTimer()
  console.log("挂在")
  songList_timer = new Timer(SONG_LIST_KEY, TOTAL_TIME, refreshSongList);
  singers_timer = new Timer(SINGERS_KEY, TOTAL_TIME, refreshSinger);
  console.log(songList_timer.savedData);
  if (songList_timer.savedData != null) {
    songList.value = songList_timer.savedData;
  }

  if (singers_timer.savedData != null) {
    singerList.value = singers_timer.savedData;
  }
  // 用户刷新的时候的操作
  window.addEventListener('beforeunload', handleBeforeUnload);
})

const handleBeforeUnload = (event) => {
  songList_timer.deconstructor(singersStore.getSongList);
  singers_timer.deconstructor(singersStore.getSingers);
};

// window.addEventListener('beforeunload', handleBeforeUnload);

// remaining time format : second
// const getRemainingTime = (): number => {
//   // {millisecond,millisecond}
//   let {
//     remainingTime,
//     savedTime
//   }: { remainingTime: number, savedTime: number }
//       = JSON.parse(localStorage.getItem(SONG_LIST_KEY));
//
//   return remainingTime + savedTime - Date.now();
// }


// const updateTimer = (doStuff: Function) => {
//   console.log('remaining time', Math.floor(remainingTime.value / 1000));
//   remainingTime.value -= 1000;
//   if (remainingTime.value <= 0) {
//     doStuff();
//     resetRemainingTime();
//   }
// }


// const startTimer = () => {
//   // console.log("start");
//   timer = setInterval(updateTimer, 1000, refreshSongList);
//   // console.log('timer', timer);
// }

// const resetRemainingTime = () => {
//   remainingTime.value = TOTAL_TIME * 1000;
// }

// const initializeTimer = () => {
//   const item = localStorage.getItem(SONG_LIST_KEY);
//   // console.log("remainingTime", item);
//   if (item) {
//     remainingTime.value = getRemainingTime();
//     if (remainingTime.value <= 0) {
//       resetRemainingTime();
//     }
//   } else {
//     resetRemainingTime();
//   }
//   startTimer();
// }


const refreshSongList = () => {
  getRandomSongList(10).then(value => {
    if (value.data.data) {
      songList.value = value.data.data;
      singersStore.setSongList(songList.value);
    }
  })
}

const refreshSinger = () => {
  getRandomSinger(10).then(value => {
    if (value.data.data) {
      singerList.value = value.data.data;
      singersStore.setSingers(singerList.value);
    }
  })
}

const doSingerDetail = (idx: number) => {
  router.push(Behavior.SINGER_DETAIL + '/' + idx);
}

const doSongListDetail = (idx: number) => {
  router.push(Behavior.SONG_LIST_DETAIL + '/' + idx);
}


const userDoSongListReFresh = () => {
  refreshSongList();
  // resetRemainingTime();
  songList_timer.resetRemainingTime();
}

const userDoSingersReFresh = () => {
  refreshSinger();
  // resetRemainingTime();
  singers_timer.resetRemainingTime();
}

</script>

<template>
  <div id="homeView">
    <div class="songList">
      <div class="area-header">
        <h2>动态随机歌单</h2>

        <ul class="refresh">
          <li>
            <FontAwesomeIcon style="cursor: pointer" icon="fa-refresh" size="2x" @click="userDoSongListReFresh"/>
          </li>
        </ul>
      </div>

      <div class="random-song-list">
        <PlayBody :play-list="songList" @click="doSongListDetail"/>
      </div>

    </div>
    <div class="singer">
      <div class="area-header">
        <h2>动态随机歌手</h2>

        <ul class="refresh">
          <li>
            <FontAwesomeIcon style="cursor: pointer" icon="fa-refresh" size="2x"
                             @click="userDoSingersReFresh"
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