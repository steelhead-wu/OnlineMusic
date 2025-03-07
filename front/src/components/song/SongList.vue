<script lang="ts" setup>
import {download} from "@/api/song/SongApi";
import {useRoute} from "vue-router";
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";
import {useSongStore} from "@/store/SongStore";
import {ref} from "vue";

const props = defineProps<{
  tableData: Song & {
    singer: string
  },
  loading: true


}>();


const songStore = useSongStore();


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
    url: row.url
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
      const eleLink = document.createElement("a");
      eleLink.download = song.url?.slice(song.url?.lastIndexOf('/') + 1);
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

</script>

<template>
  <div id="song-list">
    <!--    <h2>我的喜欢</h2>-->
    <!--    <br>-->
    <el-table v-loading="props.loading" element-loading-text="请稍等" highlight-current-row class="song-list"
              :data="props.tableData" stripe @row-click="doRowClick"
              scrollbar-always-on>
      <el-table-column prop="song" label="歌曲" width="250" align="center"/>
      <el-table-column prop="singer" label="歌手" width="250" align="center"/>
      <el-table-column prop="album" label="专辑" width="250" align="center"/>
      <el-table-column prop="op" label="操作" width="150" align="center">
        <template #default="table">
          <el-dropdown>
            <FontAwesomeIcon icon="fa-edit" size="2x"/>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item @click="doDownloadMusic(table.row)">
                  <FontAwesomeIcon icon="fa-download" size="lg"/> &nbsp; 下载
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
    //width: 1500px;
  }
}


</style>