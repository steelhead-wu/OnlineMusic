<script setup lang="ts">

import {baseURL} from "@/api/request.ts";
import {onMounted, ref, watch} from "vue";
import {
  addSongToSongList,
  deleteSongFromSongList,
  download,
  downloadMusic, getSongBySongListID,
  searchSong,
  updateSongByID
} from "@/api/admin/AdminApi.ts";
import {HttpStatusCode} from "@/enums/HttpStatusCode.ts";
import {ElMessage, ElMessageBox, type UploadRawFile} from "element-plus";
import type SongDO from "@/pojo/DO/SongDO.ts";
import type Result from "@/util/Result.ts";
import {useRoute, useRouter} from "vue-router";
import type Transfer from "@/pojo/Transfer.ts";
import {HttpHeaders} from "@/enums/HttpHeaders.ts";
import {Plus, Search} from "@element-plus/icons-vue";
import {useSearchStore} from "@/stores/SearchStore.ts";
import {PictureRepoType} from "@/enums/PictureRepoType.ts";
import {isEmpty, nonEmpty} from "@/util/StringUtils.ts";

const isUploadLyric = ref(false);
const songData = ref<Array<SongDO>>([])
const currentSong = ref<SongDO>({});
const dialogVisible = ref(false);
// 判断是添加歌曲还是编辑歌曲
const isAddSong = ref(false);
const router = useRouter();
const route = useRoute();
const search_res = ref<Array<SongDO>>([]);

const searchStore = useSearchStore();
watch(() => searchStore.getContext, () => {
  songData.value = searchStore.getContext;
})

const search = async (kw: string) => {
  const res = await searchSong(kw);
  if (res.data.code == HttpStatusCode.OK) {
    search_res.value = res.data.data;
  }
}

onMounted(async () => {


  // query all songs
  getSongBySongListID(route.params.id as string).then(value => {
    if (value.data.code == HttpStatusCode.OK) {
      songData.value = value.data.data;
    }
  })
})


const confirm_delete = (songID: string, songListID: string) => {
  ElMessageBox.confirm(
      '确认要这个歌单中删除这个歌曲信息吗？',
      '确认删除',
      {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
        type: 'warning',
      }
  ).then(value => {
    if (value === 'confirm') {
      deleteSongFromSongList(songID, songListID).then(value => {
            if (value.data.code === 200) {
              sessionStorage.setItem('showSuccessMessage', '删除成功！');
              router.go(0);
            } else {
              ElMessage({
                type: 'info',
                message: '未知原因，删除失败',
              })
            }
          }
      )
    }
  }).catch(() => {
    ElMessage({
      type: 'info',
      message: '已取消',
    })
  })
}

// 处理专辑照片上传成功后处理的函数
const handleAlbumPictureSuccess = (response: Result, uploadFile: UploadRawFile) => {
  currentSong.value.picture = response.link.substring(response.link.indexOf("\\asset"));
  ElMessage.success('专辑照片上传成功！');
}

// 上传歌词文件成功后的处理函数
const handleLyricFileSuccess = (response: Result, uploadFile: UploadRawFile) => {
  currentSong.value.lyricUrl = response.link.substring(response.link.indexOf("\\asset"));
  isUploadLyric.value = true;
  // console.log('上传的文件', uploadFile);
  ElMessage.success('歌词文件上传成功！');
}
// 上传歌曲文件成功后的处理函数
const handleSongFileSuccess = (response: Result, uploadFile: UploadRawFile) => {
  currentSong.value.url = response.link.substring(response.link.indexOf("\\asset"));

  // console.log('上传的文件', uploadFile);
  ElMessage.success('歌曲文件上传成功！');
}

// 预览歌词
const previewFile = async (song: SongDO) => {
  if (song.lyricUrl == undefined) return;
  const transfer: Transfer = {
    path: song.lyricUrl,
    contentDisposition: HttpHeaders.ATTACHMENT,
    mediaType: HttpHeaders.TEXT_PLAIN,
  }
  try {
    const res = await download(transfer);
    const newWindow = window.open('', '_blank', 'width=600,height=800');
    if (newWindow == null) return;
    newWindow.document.write(`
    <!DOCTYPE html>
    <html>
      <head>
        <title>歌词预览</title>
        <style>
          body { font-family: Arial, sans-serif; padding: 20px; }
          pre { white-space: pre-wrap; background: #f5f5f5; padding: 15px; border-radius: 5px; }
          .lrc-header { margin-bottom: 20px; }
          .close-btn {
            padding: 5px 10px;
            background: #f44336;
            color: white;
            border: none;
            border-radius: 3px;
            cursor: pointer;
          }
        </style>
      </head>
      <body>
        <div class="lrc-header">
          <h2>${song.title}-${song.singerName}</h2>
          <button class="close-btn" onclick="window.close()">关闭窗口</button>
        </div>
        <pre>${res.data}</pre>
      </body>
    </html>
  `);
    newWindow.document.close();
  } catch (e) {
    console.log("没有找到");
    return;
  }


}

const downloadLyricFile = async (song: SongDO) => {
  if (song.lyricUrl == undefined) return;
  const transfer: Transfer = {
    path: song.lyricUrl,
    contentDisposition: HttpHeaders.ATTACHMENT,
    mediaType: HttpHeaders.TEXT_PLAIN,
  }

  const res = await download(transfer);
  const eleLink = document.createElement("a");
  // 下载的文件名
  eleLink.download = transfer.path.slice(transfer.path.lastIndexOf('\\') + 1);
  eleLink.style.display = "none";
  // // 字符内容转变成 blob 地址
  const blob = new Blob([res.data]);
  eleLink.href = URL.createObjectURL(blob);
  document.body.appendChild(eleLink); // 触发点击
  eleLink.click();
  document.body.removeChild(eleLink); // 移除
}

const updateEdit = async () => {
  if (isAddSong.value) {
    const res = await addSongToSongList(currentSong.value.id, route.params.id);
    if (res.data.code == HttpStatusCode.OK) {
      sessionStorage.setItem('showSuccessMessage', '添加成功！')
      dialogVisible.value = false;
      router.go(0);
    } else {
      ElMessage.error("添加失败");
    }
  } else {
    let res = null;
    if (isUploadLyric.value) {
      res = await updateSongByID(currentSong.value, true);
      isUploadLyric.value = false;
    } else {
      res = await updateSongByID(currentSong.value, false);
    }
    if (res.data.code == HttpStatusCode.OK) {
      sessionStorage.setItem('showSuccessMessage', '修改成功！')
      dialogVisible.value = false;
      router.go(0);
    } else {
      ElMessage.error("更新失败");
    }
  }
}


// display add box
const add = () => {
  currentSong.value = {id: '0'};
  dialogVisible.value = true;
  isAddSong.value = true;
}
// 下载歌曲
const downloadSong = async (song: SongDO) => {
  console.log(song.url);
  if (song.url == undefined) return;
  const transfer: Transfer = {
    path: song.url,
    contentDisposition: HttpHeaders.ATTACHMENT, // attachment
    mediaType: HttpHeaders.APPLICATION_OCTET_STREAM, // application/octet-stream
  }

  const res = await downloadMusic(transfer);


  console.log(res)
  const eleLink = document.createElement("a");
  // 下载的文件名
  eleLink.download = transfer.path.slice(transfer.path.lastIndexOf('\\') + 1);
  eleLink.style.display = "none";
  // 字符内容转变成 blob 地址
  const blob = new Blob([res.data]);
  eleLink.href = URL.createObjectURL(blob);
  document.body.appendChild(eleLink); // 触发点击
  eleLink.click();
  document.body.removeChild(eleLink); // 移除
}

const choose = (song: SongDO) => {
  currentSong.value = song;
  search_res.value = [];
}


const handleSearchBoxChange = (kw: string) => {
  kw = kw.trim();
  if (isEmpty(kw)) {
    search_res.value = [];
    return;
  }
  search(kw);
}

</script>


<template>
  <div class="add-context">
    <!--    add singer-->
    <el-icon class="animated-icon" size="32" color="red" @click="add" title="新增歌手">
      <template #default>

        <Plus/>

      </template>

    </el-icon>
  </div>

  <!--  singer data-->
  <el-table :data="songData" style="width: 100%" border height="731px">
    <el-table-column prop="title" label="名字" width="180" align="center"/>
    <el-table-column prop="singerName" label="歌手" width="180" align="center"/>
    <el-table-column prop="album" label="专辑" width="180" align="center"/>

    <el-table-column prop="picture" label="图片" width="180" align="center">
      <template #default="s">
        <img :src="baseURL+s.row.picture" style="border-radius: 10%;width: 150px;height: 150px">
      </template>
    </el-table-column>

    <el-table-column prop="lyric" label="歌曲源文件" width="180" align="center">
      <template #default="s">
        <!--        <el-button @click="previewFile(s.row)" style="margin-bottom: 5px">播放</el-button>-->
        <el-button @click="downloadSong(s.row)">下载歌曲</el-button>
      </template>
    </el-table-column>


    <el-table-column prop="lyric" label="歌词" width="180" align="center">
      <template #default="s">
        <el-button @click="previewFile(s.row)" style="margin-bottom: 5px">预览歌词</el-button>
        <el-button @click="downloadLyricFile(s.row)">下载歌词</el-button>
      </template>
    </el-table-column>

    <el-table-column label="操作" align="left" header-align="center">
      <template #default="s">
        <el-button type="danger" @click="confirm_delete(s.row.id,route.params.id)">删除</el-button>
      </template>
    </el-table-column>
  </el-table>

  <!--edit box -->
  <el-dialog v-model="dialogVisible" title="Tips" width="500" draggable>
    <template #header>
      <span>歌曲信息</span>
    </template>
    <template #default>
      <el-form :model="currentSong" label-width="120px">
        <el-form-item label="歌名">
          <el-input
              v-model="currentSong.title"
              placeholder="输入歌手、歌名或者专辑进行搜索"
              @keyup.enter="search"
              @input="handleSearchBoxChange"
          >
            <template #append>
              <el-button :icon="Search" @click="search"/>
            </template>
          </el-input>
          <div v-if="search_res.length>0" class="autocomplete-items">
            <ul>
              <li v-for="(song) in search_res" :key="song.id">
                <strong style="display: flex;justify-content: space-between;" @click="choose(song)">
                  <span>{{ song.title }} </span>
                  <span>{{ song.singerName }}</span>
                </strong>
              </li>
            </ul>
          </div>
        </el-form-item>

        <el-form-item label="歌手">
          <el-input type="text" disabled :placeholder="currentSong.singerName"/>
        </el-form-item>

        <el-form-item label="专辑">
          <el-input :placeholder="currentSong.album" disabled/>
        </el-form-item>
      </el-form>
    </template>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="updateEdit">
          确认
        </el-button>
      </div>
    </template>
  </el-dialog>
</template>

<style scoped lang="scss">

.add-context {
  width: 50px;
  height: 50px;
  position: fixed;
  right: -25px;
  bottom: 120px;
  z-index: 2100;
  display: flex;
  justify-content: center;
  align-items: center;

  .animated-icon {
    transition: transform 0.3s ease;

    &:hover {
      transform: translateX(-20px);
    }
  }
}

.autocomplete-items {
  position: absolute;
  border: 1px solid #d4d4d4;
  border-bottom: none;
  border-top: none;
  z-index: 99;
  top: 100%;
  left: 0;
  right: 46px;

  ul {
    list-style: none;
    padding-left: 0;
    max-height: 300px; /* 设置固定高度 */
    //height: 300px; /* 设置固定高度 */
    overflow-y: scroll;

    li {
      padding: 10px;
      cursor: pointer;
      background-color: #fff;
      border-bottom: 1px solid #d4d4d4;

      &:hover {
        /*鼠标移动到填充项设置的背景颜色*/
        background-color: #e9e9e9;
      }
    }
  }
}
</style>