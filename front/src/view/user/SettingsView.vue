<script setup lang="ts">
import {reactive, ref} from 'vue'
import type {ComponentSize, FormInstance, FormRules} from 'element-plus'
import {useUserStore} from "@/store/UserStore";

const userStore = useUserStore();
const formSize = ref<ComponentSize>('default')
const ruleFormRef = ref<FormInstance>()
const userInfo = reactive<User>({
  id: userStore.getLoginUser.id,
  account: userStore.getLoginUser.account,
  password: userStore.getLoginUser.password,
  nickname: userStore.getLoginUser.nickname,
  avatar: userStore.getLoginUser.avatar,
  birth: userStore.getLoginUser.birth,
  whatsUp: userStore.getLoginUser.whatsUp,
  sex: userStore.getLoginUser.sex,
});


const rules = reactive<FormRules<User>>({
  name: [
    {required: true, message: 'Please input Activity name', trigger: 'blur'},
    {min: 3, max: 5, message: 'Length should be 3 to 5', trigger: 'blur'},
  ],
  date1: [
    {
      type: 'date',
      required: true,
      message: 'Please pick a date',
      trigger: 'change',
    },
  ],
  desc: [
    {required: true, message: 'Please input activity form', trigger: 'blur'},
  ],
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

</script>

<template>
  <!--  <el-container>-->
  <!--    <el-main class="main">-->
  <div class="setting-box">
    <div>
      <h1>设置</h1>
    </div>

    <div>
      <h2>个人资料</h2>
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
          <el-form-item label="昵称" prop="nickname">
            <el-input v-model="userInfo.nickname"/>
          </el-form-item>

          <el-form-item label="性别">
            <el-radio-group v-model="userInfo.sex">
              <el-radio-button :value="0" label="保密"/>
              <el-radio-button :value="1" label="男生"/>
              <el-radio-button :value="2" label="女生"/>
            </el-radio-group>
          </el-form-item>


          <el-form-item label="生日" required>
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

          <el-form-item label="个性签名" prop="whatsUp">
            <el-input v-model="userInfo.whatsUp" type="textarea"/>
          </el-form-item>

          <el-form-item>
            <el-button type="primary" @click="submitForm(ruleFormRef)">
              Create
            </el-button>
            <el-button @click="resetForm(ruleFormRef)">Reset</el-button>
          </el-form-item>

        </el-form>
      </div>
    </div>

    <div>
      <h2>更改密码</h2>
    </div>

    <div>
      <h2>安全</h2>
    </div>

  </div>
  <!--    </el-main>-->
  <!--  </el-container>-->
</template>

<style scoped lang="scss">

//.main {
//  padding: 0;
.setting-box {
  margin: 0 auto;

  h1 {
    font-weight: bold;
    font-size: 35px;
  }
}

//}

</style>