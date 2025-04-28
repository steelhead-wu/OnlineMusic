<script setup lang="ts">

import {baseURL} from "@/api/request.ts";
import {onMounted, ref} from "vue";
import type Song from "@/pojo/Song.ts";
import {querySingerByID, querySongByID} from "@/api/admin/AdminApi.ts";
import {HttpStatusCode} from "@/enums/HttpStatusCode.ts";
import {PictureRepoType} from "@/enums/PictureRepoType.ts";
import type {UploadRawFile} from "element-plus";
import {beforeFileUpload} from "@/util/FileUtil.ts";
import type Singer from "@/pojo/Singer.ts";
import type SongDO from "@/pojo/DO/SongDO.ts";


const songData = ref<Array<SongDO>>([])
const currentSong = ref<SongDO>({});
const dialogVisible = ref(false);


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


const edit = (song: Song) => {
  dialogVisible.value = true;
  currentSong.value = song;
  // currentSong.value.singerId = song.singerId;
  // currentSong.value
}

const confirm_delete = (id: string) => {

}


const updateEdit = () => {

}


const handleAvatarSuccess = () => {

}


</script>

<template>
  <!--  singer data-->
  <el-table :data="songData" style="width: 100%" border height="731px">
    <el-table-column prop="title" label="名字" width="180" align="center"/>
    <el-table-column prop="album" label="专辑" width="180" align="center">

    </el-table-column>
    <el-table-column prop="picture" label="图片" width="180" align="center">
      <template #default="s">
        <img :src="baseURL+s.row.picture" style="border-radius: 10%;width: 150px;height: 150px">
      </template>
    </el-table-column>
    <!--    <el-table-column prop="lyric" label="歌词" width="180" align="center"/>-->

    <el-table-column label="操作" align="left" header-align="center">
      <template #default="s">
        <el-button type="primary" @click="edit(s.row )">编辑</el-button>
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
          <el-input v-model="currentSong.album" placeholder="不可为空"/>
          <el-upload
              name="blob"
              :action="baseURL + '/api/files/upload'"
              :data="{
                        'Picture-Repo-Type': PictureRepoType.ALBUM_AVATAR,
                        'ID': currentSong.id,
                        }"
              :show-file-list="false"
              :on-success="handleAvatarSuccess"
              :before-upload="(file:UploadRawFile)=>beforeFileUpload(file)"
              with-credentials
          >
            <el-image v-if="opt && currentSong.picture" style="width: 100px;height: 100px"
                      :src="baseURL + currentSong.picture"/>
            <el-button style="margin-left: 5px" type="info">上传专辑照片</el-button>

          </el-upload>

        </el-form-item>

        <el-form-item label="歌词">
          <el-input type="textarea" v-model="currentSong.lyric"/>
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

</style>