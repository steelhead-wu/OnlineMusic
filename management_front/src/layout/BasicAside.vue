<script lang="ts" setup>
import {Headset, Message, VideoPlay} from '@element-plus/icons-vue'
import {useRoute, useRouter} from "vue-router";
import {Behavior} from "@/enums/Behavior.ts";
import {computed} from "vue";

const router = useRouter();
const route = useRoute();
const goSingerManagement = () => {
  router.push(Behavior.SINGER);
}

const goSongManagement = () => {
  router.push(Behavior.SONG);
}


const goSongListManagement = () => {
  router.push(Behavior.SONG_LIST);
}

const activeMenuIndex = computed(() => route.path);
</script>


<template>
  <el-scrollbar height="731px">
    <el-menu background-color="#545c64" active-text-color="#ffd04b" class="menu" router
             :default-active="activeMenuIndex">
      <!--      <el-menu-item index="1" :route="Behavior.SINGER">-->
      <el-menu-item :index="Behavior.SINGER" :route="Behavior.SINGER">
        <template #title>
          <el-icon>
            <Headset/>
          </el-icon>
          管理歌手
        </template>
      </el-menu-item>

      <!--      <el-menu-item index="2" @click="goSongManagement">-->
      <el-menu-item :index="Behavior.SONG" :route="Behavior.SONG">
        <template #title>
          <el-icon>
            <message/>
          </el-icon>
          管理歌曲
        </template>
      </el-menu-item>

      <!--      <el-menu-item index="3" @click="goSongListManagement">-->
      <el-menu-item :index="Behavior.SONG_LIST" :route="Behavior.SONG_LIST">
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