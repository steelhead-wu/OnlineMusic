<script lang="ts" setup>
import {ref} from 'vue'
import {Headset, Message, VideoPlay} from '@element-plus/icons-vue'
import {useRouter} from "vue-router";
import {Behavior} from "@/enums/Behavior.ts";

const router = useRouter();

const goSingerManagement = () => {
  router.push(Behavior.SINGER);
}

const goSongManagement = () => {
  router.push(Behavior.SONG);
}


const goSongListManagement = () => {
  router.push(Behavior.SONG_LIST);
}
</script>


<template>
  <el-scrollbar height="731px">
    <el-menu background-color="#545c64" active-text-color="#ffd04b" class="menu">
      <el-menu-item index="1" @click="goSingerManagement">
        <template #title>
          <el-icon>
            <Headset/>
          </el-icon>
          管理歌手
        </template>
      </el-menu-item>

      <el-menu-item index="2" @click="goSongManagement">
        <template #title>
          <el-icon>
            <message/>
          </el-icon>
          管理歌曲
        </template>
      </el-menu-item>

      <el-menu-item index="3" @click="goSongListManagement">
        <template #title>
          <el-icon>
            <VideoPlay/>
          </el-icon>
          管理歌单
        </template>
      </el-menu-item>
    </el-menu>
  </el-scrollbar>
</template>

<style scoped lang="scss">
.menu {
  height: 100vh;
  --el-menu-text-color: #ffffff; // 修改默认文字颜色
  //--el-menu-hover-text-color: #ffd04b; // 修改悬停时文字颜色

}
</style>