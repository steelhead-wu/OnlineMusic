<script setup lang="ts">
import {reactive, ref} from 'vue'
import {
  type ComponentSize, ElMessage,
  ElMessageBox,
  type FormInstance,
  type FormRules,
  type UploadProps,
  type UploadRawFile
} from 'element-plus'

import {
  MAX_LEN_NK, MAX_LEN_PWD,
  MIN_LEN_NK, MIN_LEN_PWD,
  validateNickname, validateOldPwd, validatePassword,
  validateRepass,
} from "@/enums/AdminPropsRule";
import {Plus, Check} from '@element-plus/icons-vue'

import {baseURL} from "@/api/request.ts";

import {useRouter} from "vue-router";
import {HttpStatusCode} from "axios";
import {useAdminStore} from "@/stores/AdminStore.ts";
import type Admin from "@/pojo/Admin.ts";
import {PictureRepoType} from "@/enums/PictureRepoType.ts";
import {beforeFileUpload} from "@/util/FileUtil.ts";
import {Behavior} from "@/enums/Behavior.ts";
import {HttpHeaders} from "@/enums/HttpHeaders.ts";
import {deleteAdminByID, updateAdminByID} from "@/api/admin/AdminApi.ts";
import type Result from "@/util/Result.ts";
import {Cookie} from "@/util/Cookie.ts";
import {CookiesName} from "@/enums/CookiesName.ts";


const adminStore = useAdminStore();
const formSize = ref<ComponentSize>('default')
const ruleFormRef = ref<FormInstance>();
const cur_idx = ref(0);

const disableChangePWD = ref(true);
const router = useRouter();

interface NewAdmin {
  id?: string,
  username?: string,
  password?: string,
  avatar?: string,
  newPassword: string,
  reNewPassword: string,
}

const adminInfo = reactive<NewAdmin>({
  id: adminStore.getAdmin?.id,
  avatar: adminStore.getAdmin?.avatar,
  password: '', // old pwd, for validating admin input
  newPassword: '',
  reNewPassword: '',
});


const rules = reactive<FormRules<NewAdmin>>({
  username: [
    {
      required: true,
      message: `用户名不可以为空,且长度应该在${MIN_LEN_NK}~${MAX_LEN_NK}`,
      trigger: ['blur', 'change'],
      validator: validateNickname
    },
  ],

  password: [
    {
      required: false, trigger: ['blur', 'change'],
      message: '请输入旧密码',
      validator: (rule: object, value: string, callback: Function,) => {
        if (validateOldPwd(rule, value, callback, adminStore.getAdmin?.password)) {
          disableChangePWD.value = false;
        } else {
          disableChangePWD.value = true;
        }
      },
    },
  ],

  newPassword: [
    {required: true, message: '请输入新密码', trigger: 'blur'},
    {
      message: `长度应该在${MIN_LEN_PWD}~${MAX_LEN_PWD}位且不能含有空格`,
      trigger: ['blur', 'change'],
      validator: validatePassword,
    },
  ],
  reNewPassword: [
    {required: true, message: '请再次输入新密码', trigger: ['blur'],},
    {
      validator: (rule: object, value: string, callback: Function) => validateRepass(rule, value, callback, adminInfo.newPassword),
      trigger: ['blur', 'change']
    },
  ]
})

// const saveUserInfoUpdate = () => {
//   const updatedUser = {
//     id: userInfo.id,
//     nickname: userInfo.nickname,
//     sex: userInfo.sex,
//     birth: formatDate(userInfo.birth),
//     whatsUp: userInfo.whatsUp,
//   };
//   console.log(updatedUser);
//   updateUserById(updatedUser).then(value => {
//     console.log(value);
//     if (value.data.data) {
//       console.log('更新用户资料成功');
//       userStore.setLoginUser(updatedUser);
//
//     } else {
//       console.log('更新用户资料失败');
//     }
//   })
// }

const submitForm = async (formEl: FormInstance | undefined) => {
  if (!formEl) return
  await formEl.validate((valid, fields) => {
    if (valid) {
      // saveUserInfoUpdate();
    } else {
      console.log('error submit!', fields)
    }
  })
}

const resetForm = (formEl: FormInstance | undefined) => {
  if (!formEl) return
  formEl.resetFields()
}

const handleLogOff = (admin: Admin) => {
  ElMessageBox.confirm(
      `会永久地删除用户${admin.username}，你确定要怎么做吗？`,
      '警告',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }
  ).then(async () => {
    if (admin.id != null) {
      const res = await deleteAdminByID(admin.id)
      if (res.data.code == HttpStatusCode.Ok) {
        localStorage.removeItem('admin');
        router.push(Behavior.SIGN_IN);
        ElMessage({
          type: 'success',
          message: '删除成功',
        });
      }
    }
  }).catch(() => {
    ElMessage({
      type: 'info',
      message: '已取消',
    })
  })
}

const handleAvatarSuccess: UploadProps['onSuccess'] = async (
    response: Result,
    uploadFile
) => {
  console.log('uploadFile', uploadFile);
  console.log('response', response);
  if (adminStore.getAdmin != null)
    adminStore.getAdmin.avatar = response.link.substring(response.link.indexOf("\\asset"));

  // 更新用户头像地址
  await updateAdminByID(adminStore.getAdmin);
}
//
// const beforeAvatarUpload: UploadProps['beforeUpload'] = (rawFile) => {
//   if (rawFile.type !== 'image/jpeg') {
//     ElMessage.error('Avatar picture must be JPG format!')
//     return false
//   } else if (rawFile.size / 1024 / 1024 > 5) {
//     ElMessage.error('Avatar picture size can not exceed 5MB!')
//     return false
//   }
//   return true
// }
//
const validateChangePWD = () => {
  ruleFormRef.value?.validate(async (pass) => {
    if (pass) {
      const admin: Admin = {
        id: adminInfo.id,
        password: adminInfo.newPassword,
      }
      const res = await updateAdminByID(admin);
      if (res.data.data) {
        adminStore.getAdmin.password = adminInfo.newPassword;
        ElMessage.success("修改成功！");
      } else {
        ElMessage.info("更新密码失败");
      }


      //   updateUserById(updatedUser).then(value => {
      //     if (value.data.data) {
      //       console.log("更新密码成功");
      //       userStore.setLoginUser(updatedUser);
      //     } else {
      //       console.log("更新密码失败");
      //     }
      //   })
      // } else {
      //   ElMessage({
      //     type: 'info',
      //     message: `提示: 请重新填写`,
      //   })
      // }

    }
  })
}

</script>

<template>
  <div class="bodyArea">
    <div class="area-outset">
      <div class="area">
        <div class="personal-left">
          <div class="avatar">
            <el-avatar style="height: 160px;width: 160px;margin-top: 20px" :src="baseURL+adminStore.getAdmin.avatar"/>
          </div>

          <div class="nickname-box">
            <div class="nickname">
              用户名：{{ adminStore.getAdmin.username }}
            </div>
          </div>
        </div>
        <div class="personal-main">
          <div class="personal-main-content">
            <div class="module1">
              <div class="module-bd">
                <div class="module02">
                  <div class="module02_hd">
                    <ul class="tag-list-ct">
                      <li>
                        <span :class="{cur:cur_idx===0}" @click="cur_idx=0">修改头像</span>
                      </li>
                      <li>
                        <span :class="{cur:cur_idx===2}" @click="cur_idx=2">修改密码</span>
                      </li>
                      <li>
                        <span :class="{cur:cur_idx===3}" @click="cur_idx=3">注销</span>
                      </li>
                    </ul>

                  </div>
                  <div class="module02_bd">
                    <div class="module02-content">
                      <div class="change-avatar" v-if="cur_idx===0">
                        <div>
                          <span class="avatar-info">设置一个你喜爱的头像:</span>
                        </div>

                        <p>最佳尺寸400x400，支持JPG、JPEG、PNG </p>

                        <div class="user-avatar">
                          <el-upload
                              name="blob"
                              :action="baseURL + '/api/files/upload'"
                              :data="{
                          'Picture-Repo-Type': PictureRepoType.ADMIN_AVATAR,
                          'ID': adminStore.getAdmin.id,
                                }"
                              :headers="{
                                'authorization':Cookie.get(CookiesName.AD_AU)
                              }"
                              :show-file-list="false"
                              :on-success="handleAvatarSuccess"
                              :before-upload="(file:UploadRawFile)=>beforeFileUpload(file,new Set([HttpHeaders.IMAGE_JPEG,HttpHeaders.IMAGE_PNG]),5)"
                              limit="1"
                              with-credentials
                          >

                            <el-avatar v-if="adminStore.getAdmin.avatar" style="width: 100px;height: 100px"
                                       alt="default avatar" :size="'large'"
                                       :src="baseURL+adminStore.getAdmin.avatar"/>
                            <el-icon v-else class="avatar-uploader-icon">
                              <Plus/>
                            </el-icon>
                          </el-upload>
                        </div>

                      </div>

                      <div class="edit-info" v-else-if="cur_idx===1">
                        <el-form
                            ref="ruleFormRef"
                            style="max-width: 600px"
                            :model="adminInfo"
                            :rules="rules"
                            label-width="auto"
                            class="demo-ruleForm"
                            :size="formSize"
                            status-icon
                        >
                          <div class="personal-area">
                            <div class="nickname-box">
                              <el-form-item label="用户名" prop="username" style="width: 250px">
                                <el-input v-model="adminInfo.username" :placeholder="adminStore.getAdmin.username"/>
                              </el-form-item>
                            </div>

                            <div class="option-box">
                              <el-form-item>
                                <el-button type="primary" round @click="submitForm(ruleFormRef)">保存</el-button>
                              </el-form-item>
                            </div>
                          </div>
                        </el-form>

                      </div>

                      <div class="change-PWD" v-else-if="cur_idx===2">
                        <el-form
                            ref="ruleFormRef"
                            style="max-width: 600px"
                            :model="adminInfo"
                            :rules="rules"
                            label-width="auto"
                            class="demo-ruleForm"
                            :size="formSize"
                            status-icon
                        >
                          <div class="pwd-area">
                            <div class="oldPwd-box">
                              <el-form-item label="旧密码" prop="password">
                                <el-input type="password"
                                          placeholder="请输入旧密码"
                                          style="width: 240px"
                                          v-model="adminInfo.password"
                                          clearable
                                ></el-input>
                              </el-form-item>
                            </div>

                            <div class="newPassword">
                              <el-form-item label="新密码" prop="newPassword">
                                <el-input
                                    :disabled="disableChangePWD"
                                    v-model="adminInfo.newPassword"
                                    style="width: 240px"
                                    type="password"
                                    placeholder="请输入你的新密码"
                                    show-password
                                    clearable
                                />
                              </el-form-item>
                            </div>

                            <div class="reNewPassword">
                              <el-form-item label="再次" prop="reNewPassword">
                                <el-input
                                    :disabled="disableChangePWD"
                                    v-model="adminInfo.reNewPassword"
                                    style="width: 240px"
                                    type="password"
                                    placeholder="再次输入你的新密码"
                                    show-password
                                    clearable
                                />
                              </el-form-item>
                            </div>

                            <div class="option-box">
                              <el-form-item>
                                <el-button type="primary" round @click="validateChangePWD">保存更改</el-button>
                              </el-form-item>
                            </div>

                          </div>
                        </el-form>

                      </div>

                      <div class="logoff" v-else>
                        <el-button type="primary" round @click="handleLogOff(adminStore.getAdmin)">注销</el-button>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>

        </div>
      </div>
    </div>
  </div>


</template>

<style scoped lang="scss">

.bodyArea {
  .area-outset {
    padding: 60px 0 70px;
    background: rgb(251, 251, 251);

    .area {
      display: flex;
      width: 1000px;
      margin: 0 auto;
      border-radius: 8px;
      overflow: hidden;
      box-shadow: 18px 18px 40px 0px rgba(164, 186, 197, 0.04);
      min-height: 368px;
      //height: 500px;


      .personal-left {
        width: 240px;
        display: flex;
        flex-direction: column;
        align-items: center;
        background: rgba(0, 169, 255, 0.1);

        .nickname-box {
          display: flex;
          margin-top: 24px;
          align-items: center;

          .nickname {
            font-size: 14px;
            color: #000;
            font-weight: 500;
          }
        }

        .account-box {
          font-size: 12px;
          margin-top: 8px;
        }

      }

      .personal-main {
        background: #fff;
        width: 760px;

        .personal-main-content {
          .module1 {
            .module-bd {
              padding-bottom: 40px;

              .module02 {
                .module02_hd {
                  .tag-list-ct {
                    display: flex;
                    padding: 40px 0 15px;
                    border-bottom: 2px solid rgba(177, 179, 185, 0.16);
                    list-style: none;

                    li {
                      margin-left: 60px;

                      span {
                        cursor: pointer;
                      }

                      .cur {
                        color: #30bfd5;
                      }
                    }
                  }
                }

                .module02_bd {
                  margin-left: 60px;

                  .module02-content {
                    .change-avatar {
                      .avatar-info {
                        display: block;
                        font-size: 14px;
                        color: #333;
                        margin-top: 24px;
                      }

                      p {
                        color: #B1B3B9;
                        font-size: 12px;
                        margin-top: 8px;
                        padding-left: 18px;
                        background: url(https://www.kugou.com/newuc/static/images/icon_disc.png) no-repeat;
                        background-size: 16px 16px;
                        background-position: 0 1px;
                      }

                      .user-avatar {
                        margin-top: 25px;
                      }
                    }

                    .edit-info {
                      .personal-area {
                        width: 100%;
                        margin-top: 10px;
                        //border-radius: 5%;
                        background-color: white;


                        & .nickname-box,
                        & .sex-box,
                        & .birth-box,
                        & .whatsUp-box,
                        &.option-box {
                          margin-top: 50px; // 设置相同的外边距值
                        }

                        h2 {
                          margin-bottom: 10px;
                        }

                        .sex-box {

                        }

                        .birth-box {

                        }

                        .whatsUp-box {

                        }

                        .option-box {
                          margin-top: 30px;
                          margin-left: 70px;
                        }
                      }
                    }

                    .change-PWD {
                      .pwd-area {
                        margin-top: 10px;

                        .oldPwd-box {

                        }

                        .newPassword {

                        }

                        .reNewPassword {

                        }

                        .option-box {
                          margin-top: 30px;
                          margin-left: 70px;
                        }
                      }
                    }

                    .logoff {
                      margin-top: 20px;
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
  }
}
</style>