<script setup lang="ts">
import {nextTick, onMounted, reactive, ref, watch} from "vue";
import {
  addSongList,
  conditionalQuerySongList,
  deleteSongListByID,
  updateSongListByID,
  uploadSongListImg
} from "@/api/admin/AdminApi.ts";
import {HttpStatusCode} from "@/enums/HttpStatusCode.ts";
import type SongList from "@/pojo/SongList.ts";
import {baseURL} from "@/api/request.ts";
import {
  ElMessage,
  ElMessageBox,
  type UploadFile,
  type UploadFiles,
  type UploadRawFile,
  type UploadRequestOptions
} from "element-plus";
import {useRouter} from "vue-router";
import {PictureRepoType} from "@/enums/PictureRepoType.ts";
import {beforeFileUpload} from "@/util/FileUtil.ts";
import {HttpHeaders} from "@/enums/HttpHeaders.ts";
import type Result from "@/util/Result.ts";
import {Plus} from "@element-plus/icons-vue";
import {useSearchStore} from "@/stores/SearchStore.ts";
import {Behavior} from "@/enums/Behavior.ts";

const songListData = ref<Array<SongList>>([]);
const editing = ref({
  row: null,
  col: null
})
const router = useRouter();
const styles = ref<Array<string>>([]);

const searchStore = useSearchStore();
watch(() => searchStore.getContext, () => {
  songListData.value = searchStore.getContext;
})


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

const resetEditing = () => {
  editing.value.row = null;
  editing.value.col = null;
}

const handleEditing = (songList: SongList, rawSongList: SongList) => {
  if (songList.title != rawSongList.title || songList.rating != rawSongList.rating
      || songList.introduction != rawSongList.introduction || songList.style != rawSongList.style) {
    handleBlur(songList);
  }
  resetEditing();
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

const dialogVisible = ref(false);
const currentSongList = ref<SongList>({});
const add = () => {
  dialogVisible.value = true;
  currentSongList.value = {};
}

const inputError = ref(true);
const errorMessage = ref('');

const validateInput = (v: string) => {
  let val = Number(v);
  if (val < 0 || val > 5) {
    errorMessage.value = '只能在0~5之间的整数';
    return;
  }

  if (!Number.isInteger(val)) {
    errorMessage.value = '只能是整数';
    return;
  }
  errorMessage.value = '';
}


const handleAddSongList = async (options: UploadRequestOptions) => {
  const {filename, file, data, onSuccess} = options;
  data[filename] = file;
  data['songList'] = new Blob([JSON.stringify(currentSongList.value)], {
    type: 'application/json'
  });

  const res = await addSongList(data);

  if (res.data.code == HttpStatusCode.OK) {
    onSuccess(res);
  } else {
    ElMessage.error(res.data.message);
  }

}


const handleAddSongListPictureSuccess = (response: any, uploadFile: UploadFile, uploadFiles: UploadFiles) => {
  sessionStorage.setItem('showSuccessMessage', '新增成功！')
  router.go(0);
}

const uploadRef = ref();
const updateEdit = () => {
  uploadRef.value.submit();
}
</script>

<template>
  <el-dialog v-model="dialogVisible" title="Tips" width="500" draggable>
    <template #header>
      <span>歌单信息</span>
    </template>
    <template #default>
      <el-form :model="currentSongList" label-width="120px">
        <el-form-item label="标题">
          <el-input v-model="currentSongList.title" placeholder="不可为空"/>
        </el-form-item>

        <el-form-item label="照片">

          <el-upload
              :auto-upload="false"
              ref="uploadRef"
              name="blob"
              :data=
                  "{
            'Picture-Repo-Type': PictureRepoType.SONG_LIST,
                    }"
              :http-request="handleAddSongList"
              :show-file-list="true"
              :on-success="handleAddSongListPictureSuccess"
              :before-upload="(file:UploadRawFile)=>beforeFileUpload(file,new Set<string>([HttpHeaders.IMAGE_JPEG,HttpHeaders.IMAGE_PNG]))"
              with-credentials
          >
            <!--            <el-image v-if="currentSongList.picture" style="width: 100px;height: 100px;top:5px"-->
            <!--                      :src="baseURL + currentSong.picture"/>-->
            <el-button style="margin-left: 5px" type="info">上传照片</el-button>

          </el-upload>

        </el-form-item>

        <el-form-item label="简介">
          <el-input v-model="currentSongList.introduction" type="textarea" placeholder="最多50词"/>
        </el-form-item>
        <!--        选在音乐风格-->
        <el-form-item label="风格">
          <el-select v-model="currentSongList.style" placeholder="选择此音乐风格">
            <el-option
                :value="style"
                v-for="style in styles" :key="style"
            >
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="评分">
          <el-input ref="inputRef" type="number" max="5" min="0" v-model="currentSongList.rating"
                    placeholder="输入0-5的整数"
                    @input="validateInput"
          ></el-input>
          <span v-if="inputError" style="color: red">{{ errorMessage }}</span>
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
  <div class="add-context">
    <!--    add singer-->
    <el-icon class="animated-icon" size="32" color="red" @click="add" title="新增歌单">
      <template #default>

        <Plus/>

      </template>

    </el-icon>
  </div>

  <el-table :data="songListData" style="width: 100%" border height="731px" highlight-current-row
            @cell-dblclick="handleDBLClick"
  >

    <el-table-column prop="title" label="标题" width="auto" align="center">
      <template #default="s">
        <div
            v-if="editing.row!=null && editing.col!=null && editing.row.id==s.row.id && editing.col.property==s.column.property">
          <el-input ref="inputRef" v-model="editing.row.title" @blur="handleEditing(editing.row,s.row)"
                    @keyup.enter="handleEditing(editing.row,s.row)"></el-input>
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
                   :before-upload="(file:UploadRawFile)=>beforeFileUpload(file, new Set<string>([ HttpHeaders.IMAGE_JPEG,HttpHeaders.IMAGE_PNG]),5)"
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
          <el-input ref="inputRef" v-model="editing.row.introduction" @blur="handleEditing(editing.row,s.row)"
                    @keyup.enter="handleEditing(editing.row,s.row)"></el-input>
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
          <el-select v-model="editing.row.style" @blur="handleEditing(editing.row,s.row)">
            <el-option
                :value="style"
                v-for="style in styles" :key="style"
            >
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
          <el-input ref="inputRef" type="number" max="5" min="0" v-model="editing.row.rating"
                    placeholder="输入0-5的整数"
                    @input="validateInput"
                    @blur="handleEditing(editing.row,s.row) "
                    @keyup.enter="handleEditing(editing.row,s.row)"></el-input>
          <span v-if="inputError" style="color: red">{{ errorMessage }}</span>
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
        <div style="display: flex;flex-direction: row; justify-content: center">
          <el-button type="primary" @click="router.push(Behavior.SONG_LIST_DETAIL.concat('/',s.row.id))">查看</el-button>
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
</style>