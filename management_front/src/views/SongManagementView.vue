<script setup lang="ts">

import {baseURL} from "@/api/request.ts";
import {onMounted, ref} from "vue";
import type Song from "@/pojo/Song.ts";
import {
  deleteSongById, download,
  querySingerByID,
  querySongByID,
  updateSongByID
} from "@/api/admin/AdminApi.ts";
import {HttpStatusCode} from "@/enums/HttpStatusCode.ts";
import {PictureRepoType} from "@/enums/PictureRepoType.ts";
import {ElMessage, ElMessageBox, type UploadRawFile} from "element-plus";
import {beforeFileUpload} from "@/util/FileUtil.ts";
import type Singer from "@/pojo/Singer.ts";
import type SongDO from "@/pojo/DO/SongDO.ts";
import type Result from "@/util/Result.ts";
import {useRouter} from "vue-router";
import type Transfer from "@/pojo/Transfer.ts";
import {HttpHeaders} from "@/enums/HttpHeaders.ts";
import {isEmpty, nonEmpty} from "@/util/StringUtils.ts";


const songData = ref<Array<SongDO>>([])
const currentSong = ref<SongDO>({});
const dialogVisible = ref(false);
const opt = ref(false);
const router = useRouter();

let singers: Array<Singer> = [];

onMounted(async () => {
  // query all singers
  const singer: Singer = {};
  const res = await querySingerByID(singer);
  singers = res.data.data;

  // query all songs
  const song: Song = {}
  querySongByID(song).then(value => {
    if (value.data.code == HttpStatusCode.OK) {
      songData.value = value.data.data;
    }
  })
})


const edit = (song: SongDO) => {
  currentSong.value = {...song};
  dialogVisible.value = true;
}

const confirm_delete = (id: string) => {
  ElMessageBox.confirm(
      '确认要删除这个歌曲信息吗？',
      '确认删除',
      {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
        type: 'warning',
      }
  ).then(value => {
    if (value === 'confirm') {
      deleteSongById(id).then(value => {
            if (value.data.code === 200) {
              ElMessage({
                type: 'success',
                message: '删除成功',
              })
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



  ElMessage.success('歌词文件上传成功！');
}

const updateEdit = async () => {
  const res = await updateSongByID(currentSong.value);
  if (res.data.code == HttpStatusCode.OK) {
    sessionStorage.setItem('showSuccessMessage', '修改成功！')
    dialogVisible.value = false;
    router.go(0);
  } else {
    ElMessage.error("更新失败");
  }
}


const previewFile = (song: SongDO) => {
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
        <pre>${song.lyric}</pre>
      </body>
    </html>
  `);
  newWindow.document.close();
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


</script>

<template>
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

    <el-table-column prop="lyric" label="歌词" width="180" align="center">
      <template #default="s">
        <el-button @click="previewFile(s.row)" style="margin-bottom: 5px">预览歌词</el-button>
        <el-button @click="downloadLyricFile(s.row)">下载歌词</el-button>
      </template>
    </el-table-column>

    <el-table-column label="操作" align="left" header-align="center">
      <template #default="s">
        <el-button type="primary" @click="edit(s.row)">编辑</el-button>
        <el-button type="danger" @click="confirm_delete(s.row.id )">删除</el-button>
      </template>
    </el-table-column>
  </el-table>

  <!--edit box -->
  <el-dialog v-model="dialogVisible" title="Tips" width="500" draggable>
    <template #header>
      <span>更改歌曲信息</span>
    </template>
    <template #default>
      <el-form :model="currentSong" label-width="120px">
        <el-form-item label="歌名">
          <el-input v-model="currentSong.title" placeholder="不可为空"/>
        </el-form-item>

        <el-form-item label="歌手">
          <el-select v-model="currentSong.singerId" filterable placeholder="请选择">
            <el-option
                v-for="singer in singers"
                :key="singer.id"
                :label="singer.name"
                :value="singer.id"
            />
          </el-select>
        </el-form-item>

        <el-form-item label="专辑">
          <!--          上传专辑-->
          <el-input v-model="currentSong.album" placeholder="不可为空"/>
          <el-upload
              name="blob"
              :action="baseURL + '/api/files/upload'"
              :data="{
                        'Picture-Repo-Type': PictureRepoType.ALBUM_AVATAR,
                        'ID': currentSong.id,
                        }"
              :show-file-list="false"
              :on-success="handleAlbumPictureSuccess"
              :before-upload="(file:UploadRawFile)=>beforeFileUpload(file)"
              with-credentials
          >
            <el-image v-if="currentSong.picture" style="width: 100px;height: 100px;top:5px"
                      :src="baseURL + currentSong.picture"/>
            <el-button style="margin-left: 5px" type="info">上传专辑照片</el-button>

          </el-upload>

        </el-form-item>

        <el-form-item label="歌词">
          <!--          上传歌词文件-->
          <el-upload
              name="blob"
              :action="baseURL + '/api/files/upload'"
              :data="{
                                'Picture-Repo-Type': PictureRepoType.LYRIC,
                                'ID': currentSong.id,
                                }"
              :show-file-list="true"
              :on-success="handleLyricFileSuccess"
              :before-upload="(file:UploadRawFile)=>beforeFileUpload(file,'')"
              with-credentials
          >
            <template #default>
              <el-button type="primary">点击上传</el-button>
            </template>
            <template #tip>
              <div>
                仅支持lrc，小于5MB
              </div>
            </template>

          </el-upload>
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
.lyric-area {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}
</style>