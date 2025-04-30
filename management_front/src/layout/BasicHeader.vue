<script setup lang="ts">
import {useAdminStore} from "@/stores/AdminStore.ts";
import {baseURL} from "@/api/request.ts";
import {Search} from "@element-plus/icons-vue";
import {ref, watch} from "vue";
import {useRouter, useRoute} from "vue-router";
import {Behavior} from "@/enums/Behavior.ts";
import {searchSinger, searchSong} from "@/api/admin/AdminApi.ts";
import {useSearchStore} from "@/stores/SearchStore.ts";
import {HttpStatusCode} from "@/enums/HttpStatusCode.ts";

const adminStore = useAdminStore();
const keyword = ref<string>('');

const router = useRouter();
const route = useRoute();
const searchStore = useSearchStore();

watch(keyword, () => {
  console.log(keyword.value);
  search();
})

// accora
const search = async () => {
  if (route.path == Behavior.SINGER) {
    const res = await searchSinger(keyword.value);
    if(res.data.code == HttpStatusCode.OK) {
      searchStore.setContext(res.data.data)
    }

  } else if (route.path == Behavior.SONG) {
    const res = await searchSong(keyword.value);
    if(res.data.code == HttpStatusCode.OK) {
      searchStore.setContext(res.data.data)
    }
  }
}
</script>

<template>
  <div class="basicHeader">
    <div class="logo-area">
      <img class="logo" src="../assets/img/logo.png">
    </div>
    <div class="title-area">
      <div class="title">在线音乐后台管理系统</div>
    </div>
    <!--搜索框-->
    <div class="search-box-area">
      <el-input
          placeholder="请输入内容"
          class="search-box"
          v-model.trim="keyword"
          @keyup.enter="search"
      >
        <template #append>
          <el-button :icon="Search" @click="search"/>
        </template>
      </el-input>
    </div>
    <div class="avatar-area">
      <img class="avatar" :src="baseURL+adminStore.getAdmin?.avatar">
    </div>
  </div>
</template>


<style scoped lang="scss">
.basicHeader {
  background-color: black;
  display: flex;
  flex-direction: row;
  height: 100px;

  .logo-area {
    width: 20%;

    .logo {
      margin-left: 20px;
      width: 90px;
      height: 90px;
    }
  }

  .title-area {
    flex: 1;
    display: flex;
    flex-direction: column;
    justify-content: space-around;

    .title {
      color: #f2f2f2;
      font-size: 40px;
      text-align: center;

    }
  }

  .search-box-area {
    display: flex;
    flex-direction: column;
    justify-content: center;

    .search-box {

    }
  }

  .avatar-area {
    width: 20%;

    .avatar {
      margin-right: 20px;
      margin-top: 2px;
      float: right;
      border-radius: 50%;
      width: 90px;
      height: 90px;
    }
  }
}
</style>

