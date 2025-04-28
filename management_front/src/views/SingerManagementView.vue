<script setup lang="ts">
import {nextTick, onBeforeUnmount, onMounted, onUnmounted, ref, watch} from "vue";
import {addSinger, querySingerByID, deleteSingerById, updateSinger} from "@/api/admin/AdminApi.ts";
import type Singer from "@/pojo/Singer.ts";
import {baseURL} from "@/api/request.ts";
import {ElMessage, ElMessageBox, type UploadRawFile} from "element-plus";
import {useRouter} from "vue-router";
import {formatDate} from "@/util/MyUtils.ts";
import {PictureRepoType} from "@/enums/PictureRepoType.ts";
import {beforeFileUpload} from "@/util/FileUtil.ts";
import type Result from "@/util/Result.ts";
import {HttpStatusCode} from "@/enums/HttpStatusCode.ts";
import {nonEmpty} from "@/util/StringUtils.ts";
import {Plus} from "@element-plus/icons-vue";
import {useSearchStore} from "@/stores/SearchStore.ts";


const dialogVisible = ref<boolean>(false);
const router = useRouter();
const singerData = ref<Array<Singer>>([]);

const currentSinger = ref<Singer>({});

const searchStore = useSearchStore();
watch(() => searchStore.getContext, () => {
  singerData.value = searchStore.getContext;
})

onMounted(() => {
  const singer: Singer = {
    // sex: 1,
  }
  querySingerByID(singer).then(value => {
    singerData.value = value.data.data;
  })
})


// delete singer
const confirm_delete = (id: number) => {
  ElMessageBox.confirm(
      '确认要删除这个歌手信息吗？',
      '确认删除',
      {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
        type: 'warning',
      }
  ).then(value => {
    if (value === 'confirm') {
      deleteSingerById(id).then(value => {
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

// 0:修改；1：添加
const opt = ref<boolean>(false);

// display edit box
const edit = (singer: Singer) => {
  currentSinger.value = {...singer};
  dialogVisible.value = true;
  opt.value = false;
}

// display add box
const add = () => {
  currentSinger.value = {id: '0'};
  dialogVisible.value = true;
  opt.value = true;
}

// save modified info
const updateEdit = () => {
  if (nonEmpty(currentSinger.value.birth as string)) {
    currentSinger.value.birth = formatDate(new Date(currentSinger.value.birth as string));
  }
  dialogVisible.value = false;

  if (opt.value) {
    addSinger(currentSinger.value).then(value => {
      if (value.data.code == HttpStatusCode.OK) {
        sessionStorage.setItem('showSingerAddSuccessMessage', '添加成功！')


        router.go(0);
      } else {
        ElMessage.error('修改失败')
      }
    });

  } else {
    updateSinger(currentSinger.value).then(value => {
      if (value.data.code == HttpStatusCode.OK) {
        sessionStorage.setItem('showSingerModifiedSuccessMessage', '修改成功！')
        router.go(0);
      } else {
        ElMessage.error('修改失败')
      }
    })
  }
}


const handleAvatarSuccess = (response: Result, uploadFile: UploadRawFile) => {

  // updating the position of singer avatar
  currentSinger.value.pic = response.link.substring(response.link.indexOf("\\asset"));
  ElMessage.success('歌手头像上传成功！');


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
  <el-table :data="singerData" style="width: 100%" border height="731px">
    <el-table-column prop="name" label="姓名" width="180" align="center"/>
    <el-table-column prop="sex" label="性别" width="180" align="center">
      <template #default="s">
        {{ s.row.sex === 1 ? '女' : s.row.sex === 2 ? '男' : s.row.sex === 3 ? '团体' : '未知' }}
      </template>
    </el-table-column>
    <el-table-column prop="pic" label="照片" width="180" align="center">
      <template #default="s">
        <img :src="baseURL+s.row.pic" style="border-radius: 10%;width: 150px;height: 150px">
      </template>
    </el-table-column>
    <el-table-column prop="birth" label="出生" width="180" align="center"/>
    <el-table-column prop="location" label="家乡" width="180" align="center"/>
    <el-table-column prop="introduction" label="简介" align="left" header-align="center"/>
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
      <span>更改歌手信息</span>
    </template>
    <template #default>
      <el-form :model="currentSinger" label-width="120px">
        <el-form-item label="歌手姓名">
          <el-input v-model="currentSinger.name" placeholder="不可为空"/>
        </el-form-item>

        <el-form-item label="图片">
          <el-upload
              name="blob"
              :action="baseURL + '/api/files/upload'"
              :data="{
                'Picture-Repo-Type': PictureRepoType.SINGER_AVATAR,
                'ID': currentSinger.id,
                }"
              :show-file-list="false"
              :on-success="handleAvatarSuccess"
              :before-upload="(file:UploadRawFile)=>beforeFileUpload(file)"
              with-credentials
          >
            <el-image v-if="opt && currentSinger.pic" style="width: 100px;height: 100px"
                      :src="baseURL + currentSinger.pic"/>
            <el-button style="margin-left: 5px" type="info">上传头像</el-button>

          </el-upload>

        </el-form-item>

        <el-form-item label="性别">
          <el-radio-group v-model="currentSinger.sex">
            <el-radio :label="1">女</el-radio>
            <el-radio :label="2">男</el-radio>
            <el-radio :label="0">团体</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="出生日期">
          <el-date-picker v-model="currentSinger.birth" type="date"/>
        </el-form-item>
        <el-form-item label="家乡">
          <el-input v-model="currentSinger.location"/>
        </el-form-item>
        <el-form-item label="简介">
          <el-input v-model="currentSinger.introduction" type="textarea" :rows="4"/>
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
</style>