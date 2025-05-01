<script setup lang="ts">
import {nextTick, onMounted, reactive, ref} from "vue";
import {
  conditionalQuerySongList,
  deleteSongListByID,
  updateSongListByID,
  uploadSongListImg
} from "@/api/admin/AdminApi.ts";
import {HttpStatusCode} from "@/enums/HttpStatusCode.ts";
import type SongList from "@/pojo/SongList.ts";
import {baseURL} from "@/api/request.ts";
import {ElMessage, ElMessageBox, type UploadRawFile, type UploadRequestOptions} from "element-plus";
import {useRouter} from "vue-router";
import {PictureRepoType} from "@/enums/PictureRepoType.ts";
import {beforeFileUpload} from "@/util/FileUtil.ts";
import {HttpHeaders} from "@/enums/HttpHeaders.ts";
import type Result from "@/util/Result.ts";

const songListData = ref<Array<SongList>>([]);
const editing = ref({
  row: null,
  col: null
})
const router = useRouter();
const styles = ref<Array<string>>([]);
onMounted(async () => {
  const res = await conditionalQuerySongList({});
  if (res.data.code == HttpStatusCode.OK) {
    songListData.value = res.data.data;
    let set = new Set<string>();

    for (let songList of songListData.value) {
      if (songList.style == undefined) continue;
      if (!set.has(songList.style)) {
        set.add(songList.style);
        styles.value.push(songList.style);
      }
    }
  }


});
const inputRef = ref();

const handleDBLClick = (row: any, column: any, cell: HTMLTableCellElement, event: Event): void => {
  editing.value.row = {...row}
  editing.value.col = {...column}

  // 解决无法聚焦
  nextTick(() => {
    inputRef.value.focus();
  });
}


// 单元格编辑后处理函数
const handleBlur = (songList: SongList) => {
  ElMessageBox.confirm(
      '确认要更新此单元格吗？',
      '再次确定',
      {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
        type: 'warning',
      }
  ).then(async () => {
    const res = await updateSongListByID(songList);
    if (res.data.code == HttpStatusCode.OK) {
      sessionStorage.setItem('showSuccessMessage', '修改成功！')
      router.go(0);
    } else {
      ElMessage.error("修改失败！");
    }
  })


  editing.value.row = null;
  editing.value.col = null;


  // 这里可以添加保存数据的逻辑
}


const confirm_delete = async (id: string) => {
  const res = await deleteSongListByID(id);
  if (res.data.code === HttpStatusCode.OK) {
    sessionStorage.setItem('showSuccessMessage', '删除成功！');
    router.go(0);
  } else {
    ElMessage.error("删除失败！");
  }

}

const uploadImg = async (option: UploadRequestOptions) => {
  const {data, onSuccess, file, filename,} = option;
  data[filename] = file;
  const res = await uploadSongListImg(data);
  if (res.data.code == HttpStatusCode.OK) {
    onSuccess(res);
  } else {
    ElMessage.error(res.data.message);
  }
}

const handleImgFileSuccess = (response: Result, uploadFile: UploadRawFile) => {
  sessionStorage.setItem('showSuccessMessage', '上传成功！');
  router.go(0);
}
</script>

<template>
  <el-table :data="songListData" style="width: 100%" border height="731px" highlight-current-row
            @cell-dblclick="handleDBLClick"
  >

    <el-table-column prop="title" label="标题" width="auto" align="center">
      <template #default="s">
        <div
            v-if="editing.row!=null && editing.col!=null && editing.row.id==s.row.id && editing.col.property==s.column.property">
          <el-input ref="inputRef" v-model="editing.row.title" @blur="handleBlur(editing.row)"
                    @keyup.enter="handleBlur(editing.row)"></el-input>
        </div>
        <div v-else>{{ s.row.title }}</div>
      </template>
    </el-table-column>

    <el-table-column prop="pic" label="照片" width="auto" align="center">

      <template #default="s">
        <!--        :action="baseURL + '/api/files/upload'"-->
        <el-upload :auto-upload="true"
                   name="blob"
                   :accept="['.jpg','.png']"
                   :http-request="uploadImg"
                   :data="{
                                'Picture-Repo-Type': PictureRepoType.SONG_LIST,
                                'ID': s.row.id,
                                }"
                   :show-file-list="false"
                   :before-upload="(file:UploadRawFile)=>beforeFileUpload(file,HttpHeaders.IMAGE_JPEG,5)"
                   :on-success="handleImgFileSuccess"
                   with-credentials
        >
          <template #default>
            <img :src="baseURL+s.row.pic" style="border-radius: 10%;width: 150px;height: 150px">
          </template>
          <template #tip>
            <p style="color: #565555;font-size: 12px">
              支持JPG,PNG，小于5MB
            </p>
          </template>
        </el-upload>
      </template>
    </el-table-column>

    <el-table-column prop="introduction" label="简介" width="auto" align="center">
      <template #default="s">
        <div
            v-if="editing.row!=null && editing.col!=null  && editing.row.id==s.row.id && editing.col.property==s.column.property">
          <el-input ref="inputRef" v-model="editing.row.introduction" @blur="handleBlur(editing.row)"
                    @keyup.enter="handleBlur(editing.row)"></el-input>
        </div>

        <div v-else>
          <p class="ellipsis-text" :title="s.row.introduction">
            {{ s.row.introduction }}
          </p>
        </div>

      </template>
    </el-table-column>
    <el-table-column prop="style" label="风格" width="auto" align="center">
      <template #default="s">
        <div
            v-if="editing.row!=null && editing.col!=null  && editing.row.id==s.row.id && editing.col.property==s.column.property">
          <el-select v-model="s.row.style">
            <el-option v-for="style in styles" :key="style">
            </el-option>
          </el-select>
        </div>
        <div v-else>
          {{ s.row.style }}
        </div>
      </template>
    </el-table-column>
    <el-table-column prop="rating" label="评分" width="auto" align="center">
      <template #default="s">
        <div
            v-if="editing.row!=null && editing.col!=null  && editing.row.id==s.row.id && editing.col.property==s.column.property">
          <el-input ref="inputRef" v-model="editing.row.rating" @blur="handleBlur(editing.row)"
                    @keyup.enter="handleBlur(editing.row)"></el-input>
        </div>

        <div v-else>
          <p class="ellipsis-text" :title="s.row.introduction">
            {{ s.row.rating }}
          </p>
        </div>
      </template>
    </el-table-column>

    <el-table-column label="操作" align="left" header-align="center" width="auto">
      <template #default="s">
        <!--        <el-button type="primary" @click="edit(s.row)">编辑</el-button>-->
        <div style="display: flex;justify-content: center">
          <el-button type="danger" @click="confirm_delete(s.row.id)">删除</el-button>
        </div>
      </template>
    </el-table-column>
  </el-table>
</template>

<style scoped lang="scss">
.ellipsis-text {
  overflow: hidden; /* 隐藏溢出内容 */
  text-overflow: ellipsis; /* 显示省略号 */
  white-space: nowrap; /* 不换行 */
  display: block; /* 确保宽度生效 */
  margin: 0; /* 去除默认外边距 */
}
</style>