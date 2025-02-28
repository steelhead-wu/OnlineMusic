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
import {ElMessage, ElMessageBox} from "element-plus";

const userStore = useUserStore();
const formSize = ref<ComponentSize>('default')
const ruleFormRef = ref<FormInstance>()

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

</script>

<template>
  <!--  <el-container>-->
  <!--    <el-main class="main">-->
  <div class="setting-box">
    <div>
      <h1>设置</h1>
    </div>

    <div>
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
          <h2>个人资料</h2>
          <div class="nickname-box">
            <el-form-item label="昵称" prop="nickname">
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

        <!--          <el-form-item>-->
        <!--            <el-button type="primary" @click="submitForm(ruleFormRef)">-->
        <!--              Create-->
        <!--            </el-button>-->
        <!--            <el-button @click="resetForm(ruleFormRef)">Reset</el-button>-->
        <!--          </el-form-item>-->

        <div class="pwd-area">
          <h2>更改密码</h2>
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


    <div>
      <h2>注销</h2>
      <el-button type="danger" round @click="handleLogOff" style="margin-left: 10px">注销</el-button>
    </div>

  </div>
  <!--    </el-main>-->
  <!--  </el-container>-->
</template>

<style scoped lang="scss">

//.main {
//  padding: 0;
.setting-box {
  display: flex;
  align-items: center;
  align-content: space-around;
  flex-wrap: wrap;
  flex-direction: column;

  margin: 0 auto;

  h1 {
    font-weight: bold;
    font-size: 35px;
    text-align: left;
  }

  .personal-area {

    .nickname-box {

    }

    .sex-box {

    }

    .birth-box {

    }

    .whatsUp-box {

    }
  }

  .pwd-area {
    .oldPwd-box {

    }

    .newPassword {

    }

    .reNewPassword {

    }
  }

}

//}

</style>