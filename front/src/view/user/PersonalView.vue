<script setup lang="ts">
import {useUserStore} from "@/store/UserStore";
import {AvatarSize} from "@/enum/AvatarSize";
import SongList from "@/components/song/SongList.vue";
import Update from "@/components/user/Update.vue";

const userStore = useUserStore();


const tableData = [
  {
    song: '2016-05-03',
    singer: 'Tom',
    album: 'No. 189, Grove St, Los Angeles',
  },
  {
    song: '2016-05-03',
    singer: 'Tom',
    album: 'No. 189, Grove St, Los Angeles',
  },
]
</script>

<template>
  <div>
    <div class="avatar-area">
      <Update/>
      <span class="personal-nickname">
        <h1 style="font-size: 6rem;white-space: nowrap;">
          {{ userStore.getLoginUser.nickname === '' ? 'orzzro' : userStore.getLoginUser.nickname }}
        </h1>
      </span>
    </div>

    <div class="main-body">
      <div class="like-song">
        <SongList :table-data="tableData"/>
      </div>
    </div>
  </div>
</template>


<style scoped>

.avatar-area .personal-nickname {
  position: fixed;
  top: 131px;
  left: 309px;
}

.avatar-area {
  height: 207px;
  width: 100%;
  position: fixed;
  left: 0;
  top: 76px;
}


.main-body {
  height: 353px;
  width: 100%;
  position: fixed;
  bottom: 100px;
  left: 0;
}

.avatar-area .personal-avatar {
  width: 200px;
  height: 200px;
  position: fixed;
  left: 80px;
  top: 106px;
}

.main-body .like-song {

}
</style>