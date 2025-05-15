<script lang="ts" setup>
import {download} from "@/api/song/SongApi";
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";
import {useSongStore} from "@/store/SongStore";
import type Song from "@/pojo/Song.ts";
import {useRoute, useRouter} from "vue-router";
import {Behavior} from "@/enum/Behavior.ts";
import {useUserStore} from "@/store/UserStore.ts";
import {addLike, conditionalDelete, conditionalQuery} from "@/api/likes/LikesApi.ts";
import type Likes from "@/pojo/Likes.ts";
import {ElMessage} from "element-plus";
import {tips} from "@/api/utils/MyUtils.ts";

const props = defineProps<{
  tableData: Song & {
    singer: string
  },
  loading: true


}>();

const userStore = useUserStore();
const songStore = useSongStore();
const route = useRoute();
const router = useRouter();
const doRowClick = (row: any, column: any, event: Event) => {
  // console.log(row);
  // console.log(column);
  // console.log(event);
  songStore.getSongList.push({
    album: row.album,
    id: row.id,
    lyric: row.lyric,
    picture: row.picture,
    singerId: row.singerId,
    title: row.title,
    url: row.url,
    singerName: row.singerName
  });
  // songStore.setCurrentSongIdx(songStore.getSongList.length - 1);
  // console.log('songStore.getSongList:', songStore.getSongList);
  // console.log('songStore.getCurrentSongIdx:', songStore.getCurrentSongIdx);
}


const doDownloadMusic = (song: Song) => {
  // console.log('song', song);
  if (song.title === '') return;
  try {
    download(song).then(value => {
      // console.log("开始下载...");
      console.log(value.data);
      const eleLink = document.createElement("a");
      eleLink.download = song.url?.slice(song.url?.lastIndexOf('\\') + 1);
      eleLink.style.display = "none";
      // // 字符内容转变成 blob 地址
      const blob = new Blob([value.data]);
      eleLink.href = URL.createObjectURL(blob);
      document.body.appendChild(eleLink); // 触发点击
      eleLink.click();
      document.body.removeChild(eleLink); // 移除

    })
  } catch (error) {
    // console.error('下载失败:', error);
    if (error.response && error.response.status === 404) {
      alert('文件不存在！');
    } else {
      alert('文件下载失败，请稍后重试！');
    }
  }
}


const removeMyLikeSong = async (songID: string) => {
  const likes: Likes = {
    songId: songID,
    userId: userStore.getLoginUser.id
  }
  const res = await conditionalDelete(likes);
  if (res.data.data) {
    sessionStorage.setItem('showSuccessMessage', '成功删除')
    router.go(0);
  } else {
    ElMessage.info("删除失败！");
  }


}
// add song to my like
const addToMyFavorite = async (songID: string) => {
  if (!userStore.getIsOnline) {
    tips();
  } else {

    const conditionalLike: Likes = {
      userId: userStore.getLoginUser.id,
      songId: songID,
    }

    const fieldLike: Likes = {
      id: '1',
      userId: '1',
      songId: '1'
    }

    const response = await conditionalQuery(fieldLike, conditionalLike);
    if (response.data.data.length == 0) {
      addLike(conditionalLike).then(value => {
        if (value.data.data) {
          ElMessage.success('添加成功！');
          // sessionStorage.setItem('showSuccessMessage', '添加成功！')
          // router.go(0);
        }
      })
    } else {
      ElMessage.success('已在我喜欢中！');
    }
  }
}
</script>

<template>
  <div id="song-list">
    <!--    <h2>我的喜欢</h2>-->
    <!--    <br>-->
    <el-table v-loading="props.loading" element-loading-text="请稍等" height="350px" highlight-current-row
              class="song-list"
              :data="props.tableData" stripe @row-click="doRowClick"
              scrollbar-always-on>
      <el-table-column prop="title" label="歌曲" width="250px" align="center"/>
      <el-table-column prop="singerName" label="歌手" width="250px" align="center"/>
      <el-table-column prop="album" label="专辑" width="250px" align="center"/>
      <el-table-column prop="op" label="操作" width="250px" align="center">
        <template #default="table">
          <el-dropdown>
            <FontAwesomeIcon icon="fa-edit" size="2x"/>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item @click="doDownloadMusic(table.row)">
                  <FontAwesomeIcon icon="fa-download" size="lg"/> &nbsp; 下载
                </el-dropdown-item>

                <el-dropdown-item v-if="route.path==Behavior.PERSONAL" @click="removeMyLikeSong(table.row.id)">
                  <FontAwesomeIcon icon="fa-remove" size="lg"/> &nbsp;&nbsp;&nbsp; 删除
                </el-dropdown-item>

                <el-dropdown-item v-if="route.path!=Behavior.PERSONAL" @click="addToMyFavorite(table.row.id)">
                  <FontAwesomeIcon icon="fa-heart" style="color: red" size="lg"/> &nbsp;添加到我喜欢
                </el-dropdown-item>
              </el-dropdown-menu>

            </template>
          </el-dropdown>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<style scoped lang="scss">
#song-list {
  align-content: center;
  cursor: pointer;

  .song-list {
    z-index: 0;
    width: 100%;
  }
}


</style>