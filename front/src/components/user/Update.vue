<script lang="ts" setup>
import {ref} from 'vue'
import {ElMessage} from 'element-plus'
import {Plus} from '@element-plus/icons-vue'

import type {UploadProps} from 'element-plus'
import {AvatarSize} from "@/enum/AvatarSize";
import {useUserStore} from "@/store/UserStore";
import {baseURL} from "@/api/request";
import {updateUserById} from "@/api/user/UserApi";
import {PictureRepoType} from "@/enum/PictureRepoType";


const imageUrl = ref('https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png')
const act = ref(baseURL + '/api/files/upload');
const middle_path = '/asset/img/avatarImages';
const userStore = useUserStore();
const handleAvatarSuccess: UploadProps['onSuccess'] = (
    response,
    uploadFile
) => {
  // console.log('uploadFile', uploadFile);
  // console.log('response',response);
  userStore.getLoginUser.avatar = response.link;
  // console.log(userStore.getLoginUser);
  updateUserById(userStore.getLoginUser).then(value => {
    // console.log(value);
  })
}

const beforeAvatarUpload: UploadProps['beforeUpload'] = (rawFile) => {
  if (rawFile.type !== 'image/jpeg') {
    ElMessage.error('Avatar picture must be JPG format!')
    return false
  } else if (rawFile.size / 1024 / 1024 > 5) {
    ElMessage.error('Avatar picture size can not exceed 5MB!')
    return false
  }
  return true
}
</script>

<template>
  <el-upload
      class="avatar-uploader"
      name="blob"
      :action="act"
      :data="{
        'Picture-Repo-Type': PictureRepoType.USER_AVATAR,
        'User-ID': userStore.getLoginUser.id,
      }"
      :show-file-list="false"
      :on-success="handleAvatarSuccess"
      :before-upload="beforeAvatarUpload"
      limit="1"
  >
    <!--        <img v-if="imageUrl" :src="imageUrl" class="avatar"/>-->
    <el-avatar v-if="userStore.getLoginUser.avatar" class="avatar" alt="default avatar" :size="AvatarSize.LARGE"
               :src="userStore.getLoginUser.avatar"/>
    <el-icon v-else class="avatar-uploader-icon">
      <Plus/>
    </el-icon>
  </el-upload>
</template>


<style scoped>
.avatar-uploader .avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>

<style>
.avatar-uploader .el-upload {
  border: 1px var(--el-border-color);
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: var(--el-transition-duration-fast);
  left: 100px;
  top: 46px;
}

.avatar-uploader .el-upload:hover {
  border-color: var(--el-color-primary);
}

.el-icon.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  text-align: center;
}
</style>