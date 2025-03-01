<script setup lang="ts">
import {reactive, ref} from 'vue'
import type {ComponentSize, FormInstance, FormRules} from 'element-plus'
import {useUserStore} from "@/store/UserStore";
import {
  MAX_LEN_NK, MAX_LEN_PWD,
  MIN_LEN_NK, MIN_LEN_PWD,
  validateBirth,
  validateNickname, validateOldPwd, validatePassword,
  validateRepass,
  validateWhatsUp
} from "@/enum/UserPropsRule";
import {Plus} from '@element-plus/icons-vue'
import {ElMessage, ElMessageBox, UploadProps} from "element-plus";
import {AvatarSize} from "@/enum/AvatarSize";
import {updateUserById} from "@/api/user/UserApi";
import {PictureRepoType} from "@/enum/PictureRepoType";
import {baseURL} from "@/api/request";


const userStore = useUserStore();
const formSize = ref<ComponentSize>('default')
const ruleFormRef = ref<FormInstance>()
const cur_idx = ref(0);

const disableChangePWD = ref(true);

interface NewUser extends User {
  newPassword: string,
  reNewPassword: string,
}

const userInfo = reactive<NewUser>({
  id: userStore.getLoginUser.id,
  account: userStore.getLoginUser.account,
  nickname: userStore.getLoginUser.nickname,
  avatar: userStore.getLoginUser.avatar,
  birth: userStore.getLoginUser.birth,
  whatsUp: userStore.getLoginUser.whatsUp,
  sex: userStore.getLoginUser.sex,
  password: '', // old pwd, for validating user input
  newPassword: '',
  reNewPassword: '',
});


const rules = reactive<FormRules<User>>({
  nickname: [
    {
      required: true,
      message: `昵称不可以为空,且长度应该在${MIN_LEN_NK}~${MAX_LEN_NK}`,
      trigger: ['blur', 'change'],
      validator: validateNickname
    },
  ],
  birth: [
    {
      type: 'date',
      required: false,
      message: '请选择一个合适的日期',
      trigger: 'change',
      validator: validateBirth
    },
  ],
  whatsUp: [
    {
      required: false, trigger: ['blur', 'change'],
      message: '长度不允许超过40个字符',
      validator: validateWhatsUp
    },
  ],
  password: [
    {
      required: false, trigger: ['blur', 'change'],
      message: '密码验证不通过',
      validator: (rule: object, value: string, callback: Function,) => {
        if (validateOldPwd(rule, value, callback, userStore.loginUser.password)) {
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
      validator: (rule: object, value: string, callback: Function) => validateRepass(rule, value, callback, userInfo.newPassword),
      trigger: ['blur', 'change']
    },
  ]
})


const submitForm = async (formEl: FormInstance | undefined) => {
  if (!formEl) return
  await formEl.validate((valid, fields) => {
    if (valid) {
      console.log('submit!')
    } else {
      console.log('error submit!', fields)
    }
  })
}

const resetForm = (formEl: FormInstance | undefined) => {
  if (!formEl) return
  formEl.resetFields()
}

const handleLogOff = () => {
  ElMessageBox.confirm(
      '会永久地删除该用户，你确定要怎么做吗？',
      '警告',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }
  ).then(() => {
    ElMessage({
      type: 'success',
      message: 'Delete completed',
    })
  }).catch(() => {
    ElMessage({
      type: 'info',
      message: 'Delete canceled',
    })
  })
}

const handleAvatarSuccess: UploadProps['onSuccess'] = (
    response,
    uploadFile
) => {
  console.log('uploadFile', uploadFile);
  console.log('response', response);
  userStore.getLoginUser.avatar = response.link;
  console.log(userStore.getLoginUser);
  updateUserById(userStore.getLoginUser).then(value => {
    console.log(value);
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
  <!--  <el-container>-->
  <!--    <el-main class="main">-->
  <!--  <div class="setting-box">-->
  <!--    <h1>设置</h1>-->
  <!--    <div class="kl">-->
  <!--      &lt;!&ndash;      personal info and change PWD&ndash;&gt;-->
  <!--      <div>-->
  <!--        <el-form-->
  <!--            ref="ruleFormRef"-->
  <!--            style="max-width: 600px"-->
  <!--            :model="userInfo"-->
  <!--            :rules="rules"-->
  <!--            label-width="auto"-->
  <!--            class="demo-ruleForm"-->
  <!--            :size="formSize"-->
  <!--            status-icon-->
  <!--        >-->
  <!--          <div class="personal-area">-->
  <!--            <h2>个人资料</h2>-->
  <!--            <div class="nickname-box">-->
  <!--              <el-form-item label="昵称" prop="nickname">-->
  <!--                <el-input v-model="userInfo.nickname"/>-->
  <!--              </el-form-item>-->
  <!--            </div>-->
  <!--            <div class="sex-box">-->
  <!--              <el-form-item label="性别">-->
  <!--                <el-radio-group v-model="userInfo.sex">-->
  <!--                  <el-radio-button :value="0" label="保密"/>-->
  <!--                  <el-radio-button :value="1" label="男生"/>-->
  <!--                  <el-radio-button :value="2" label="女生"/>-->
  <!--                </el-radio-group>-->
  <!--              </el-form-item>-->
  <!--            </div>-->

  <!--            <div class="birth-box">-->
  <!--              <el-form-item label="生日">-->
  <!--                <el-form-item prop="birth">-->
  <!--                  <el-date-picker-->
  <!--                      v-model="userInfo.birth"-->
  <!--                      type="date"-->
  <!--                      aria-label="Pick a date"-->
  <!--                      placeholder="生日"-->
  <!--                      style="width: 100%"-->
  <!--                  />-->
  <!--                </el-form-item>-->
  <!--              </el-form-item>-->
  <!--            </div>-->

  <!--            <div class="whatsUp-box">-->
  <!--              <el-form-item label="个性签名" prop="whatsUp">-->
  <!--                <el-input v-model="userInfo.whatsUp" type="textarea" placeholder="说点什么吧~"/>-->
  <!--              </el-form-item>-->
  <!--            </div>-->
  <!--          </div>-->

  <!--          &lt;!&ndash;          <el-form-item>&ndash;&gt;-->
  <!--          &lt;!&ndash;            <el-button type="primary" @click="submitForm(ruleFormRef)">&ndash;&gt;-->
  <!--          &lt;!&ndash;              Create&ndash;&gt;-->
  <!--          &lt;!&ndash;            </el-button>&ndash;&gt;-->
  <!--          &lt;!&ndash;            <el-button @click="resetForm(ruleFormRef)">Reset</el-button>&ndash;&gt;-->
  <!--          &lt;!&ndash;          </el-form-item>&ndash;&gt;-->

  <!--          <div class="pwd-area">-->
  <!--            <h2>更改密码</h2>-->
  <!--            <div class="oldPwd-box">-->
  <!--              <el-form-item label="旧密码" prop="password">-->
  <!--                <el-input type="password"-->
  <!--                          placeholder="请输入旧密码"-->
  <!--                          style="width: 240px"-->
  <!--                          v-model="userInfo.password"-->
  <!--                          clearable-->
  <!--                ></el-input>-->
  <!--              </el-form-item>-->
  <!--            </div>-->

  <!--            <div class="newPassword">-->
  <!--              <el-form-item label="新密码" prop="newPassword">-->
  <!--                <el-input-->
  <!--                    :disabled="disableChangePWD"-->
  <!--                    v-model="userInfo.newPassword"-->
  <!--                    style="width: 240px"-->
  <!--                    type="password"-->
  <!--                    placeholder="请输入你的新密码"-->
  <!--                    show-password-->
  <!--                    clearable-->
  <!--                />-->
  <!--              </el-form-item>-->
  <!--            </div>-->

  <!--            <div class="reNewPassword">-->
  <!--              <el-form-item label="再次" prop="reNewPassword">-->
  <!--                <el-input-->
  <!--                    :disabled="disableChangePWD"-->
  <!--                    v-model="userInfo.reNewPassword"-->
  <!--                    style="width: 240px"-->
  <!--                    type="password"-->
  <!--                    placeholder="再次输入你的新密码"-->
  <!--                    show-password-->
  <!--                    clearable-->
  <!--                />-->
  <!--              </el-form-item>-->
  <!--            </div>-->
  <!--          </div>-->
  <!--        </el-form>-->
  <!--      </div>-->

  <!--      <div>-->
  <!--        <h2>注销</h2>-->
  <!--        <el-button type="danger" round @click="handleLogOff" style="margin-left: 10px">注销</el-button>-->
  <!--      </div>-->
  <!--    </div>-->
  <!--  </div>-->
  <!--    </el-main>-->
  <!--  </el-container>-->

  <div class="bodyArea">
    <div class="area-outset">
      <div class="area">
        <div class="personal-left">
          <div class="avatar">
            <el-avatar style="height: 160px;width: 160px;margin-top: 20px" :src="userStore.getLoginUser.avatar"/>
          </div>

          <div class="nickname-box">
            <div class="nickname">
              {{ userStore.getLoginUser.nickname }}
            </div>
          </div>

          <div class="account-box">
            账号:{{ userStore.getLoginUser.account }}
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
                        <span :class="{cur:cur_idx===1}" @click="cur_idx = 1;">编辑资料</span>
                      </li>
                      <li>
                        <span :class="{cur:cur_idx===2}" @click="cur_idx=2">修改密码</span>
                      </li>

                    </ul>

                  </div>
                  <div class="module02_bd">
                    <div class="module02-content">
                      <div class="change-avatar" v-if="cur_idx===0">
                        <div>
                          <span class="avatar-info">设置一个你喜爱的头像:</span>
                        </div>

                        <p>最佳尺寸400x400，支持JPG、JPEG、GIF、PNG </p>

                        <div class="user-avatar">
                          <el-upload
                              name="blob"
                              :action="baseURL + '/api/files/upload'"
                              :data="{
                          'Picture-Repo-Type': PictureRepoType.USER_AVATAR,
                          'User-ID': userStore.getLoginUser.id,
                                }"
                              :show-file-list="false"
                              :on-success="handleAvatarSuccess"
                              :before-upload="beforeAvatarUpload"
                              limit="1"
                          >
                            <!--                                  <img v-if="imageUrl" :src="imageUrl" class="avatar"/>-->
                            <el-avatar v-if="userStore.getLoginUser.avatar" style="width: 100px;height: 100px"
                                       alt="default avatar" :size="AvatarSize.LARGE"
                                       :src="userStore.getLoginUser.avatar"/>
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
                            :model="userInfo"
                            :rules="rules"
                            label-width="auto"
                            class="demo-ruleForm"
                            :size="formSize"
                            status-icon
                        >
                          <div class="personal-area">
                            <div class="nickname-box">
                              <el-form-item label="昵称" prop="nickname" style="width: 250px">
                                <el-input v-model="userInfo.nickname"/>
                              </el-form-item>
                            </div>
                            <div class="sex-box">
                              <el-form-item label="性别">
                                <el-radio-group v-model="userInfo.sex">
                                  <el-radio-button :value="0" label="保密"/>
                                  <el-radio-button :value="1" label="男生"/>
                                  <el-radio-button :value="2" label="女生"/>
                                </el-radio-group>
                              </el-form-item>
                            </div>

                            <div class="birth-box">
                              <el-form-item label="生日">
                                <el-form-item prop="birth">
                                  <el-date-picker
                                      v-model="userInfo.birth"
                                      type="date"
                                      aria-label="Pick a date"
                                      placeholder="生日"
                                      style="width: 100%"
                                  />
                                </el-form-item>
                              </el-form-item>
                            </div>

                            <div class="whatsUp-box">
                              <el-form-item label="个性签名" prop="whatsUp">
                                <el-input v-model="userInfo.whatsUp" type="textarea" placeholder="说点什么吧~"/>
                              </el-form-item>
                            </div>
                          </div>
                        </el-form>

                      </div>

                      <div class="change-PWD" v-else>
                        <el-form
                            ref="ruleFormRef"
                            style="max-width: 600px"
                            :model="userInfo"
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
                                          v-model="userInfo.password"
                                          clearable
                                ></el-input>
                              </el-form-item>
                            </div>

                            <div class="newPassword">
                              <el-form-item label="新密码" prop="newPassword">
                                <el-input
                                    :disabled="disableChangePWD"
                                    v-model="userInfo.newPassword"
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
                                    v-model="userInfo.reNewPassword"
                                    style="width: 240px"
                                    type="password"
                                    placeholder="再次输入你的新密码"
                                    show-password
                                    clearable
                                />
                              </el-form-item>
                            </div>
                          </div>
                        </el-form>

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
                        & .whatsUp-box {
                          margin-top: 10px; // 设置相同的外边距值
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
}


//.main {
//  padding: 0;
.setting-box {
  display: flex;
  align-items: center;
  align-content: space-between;
  flex-direction: column;

  background-color: #8c939d;
  margin: 0 auto;
  width: 100%;
  height: 100%;

  h1 {
    font-weight: bold;
    font-size: 35px;
    //align-self: flex-start;
    //margin-left: 634px;
    text-align: left;
    width: 20%;
  }

  //.personal-area {
  //  width: 100%;
  //  margin-top: 10px;
  //  //border-radius: 5%;
  //  background-color: white;
  //
  //
  //  & .nickname-box,
  //  & .sex-box,
  //  & .birth-box,
  //  & .whatsUp-box {
  //    margin-top: 10px; // 设置相同的外边距值
  //  }
  //
  //  h2 {
  //    margin-bottom: 10px;
  //  }
  //
  //  .sex-box {
  //
  //  }
  //
  //  .birth-box {
  //
  //  }
  //
  //  .whatsUp-box {
  //
  //  }
  //}

  //.pwd-area {
  //  margin-top: 10px;
  //
  //  .oldPwd-box {
  //
  //  }
  //
  //  .newPassword {
  //
  //  }
  //
  //  .reNewPassword {
  //
  //  }
  //}

}

//}

</style>