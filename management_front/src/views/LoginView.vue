<script lang="ts" setup>
import {onMounted, onUnmounted, reactive, ref} from 'vue'
import {ElMessage, type FormInstance, type FormRules} from 'element-plus'
import type Admin from "@/pojo/Admin.ts";
import {isExistWhiteSpace} from "@/util/StringUtils.ts";


import {login} from "@/api/admin/AdminApi.ts";
import {HttpStatusCode} from "@/enums/HttpStatusCode.ts";
import {Behavior} from "@/enums/Behavior.ts";
import {useRouter} from "vue-router";
import {useAdminStore} from "@/stores/AdminStore.ts";

const loginFormRef = ref<FormInstance>()
const adminStore = useAdminStore();
const router = useRouter();
const loginAdmin = reactive<Admin>({
  username: '',
  password: '',
})

const onKeyUp = (e: KeyboardEvent) => {
  if (e.key === 'Enter') {
    signIn()
  }
}

onMounted(() => {
  window.addEventListener('keyup', onKeyUp);
})

onUnmounted(() => {
  window.removeEventListener('keyup', onKeyUp)
})

const checkUsername = (rule: object, value: string, callback: Function) => {
  if (isExistWhiteSpace(value)) {
    rule.message = '不能有空白字符';
    callback(new Error());
    return;
  }

  if (value.length > 10 || value.length < 3) {
    rule.message = '用户名必须在3~10字符之间';
    callback(new Error());
    return;
  }

  callback();
}

const checkPassword = (rule: object, value: string, callback: Function) => {
  if (isExistWhiteSpace(value)) {

    rule.message = '不能有空白字符';
    callback(new Error());
    return;
  }

  if (value.length > 16 || value.length < 3) {
    rule.message = '用户名必须在3~16字符之间';
    callback(new Error());
    return;
  }
  callback();
}

const rules = reactive<FormRules<typeof loginAdmin>>({
  username: [{
    trigger: ['change', 'blur'],
    validator: checkUsername
  }
  ],
  password: [{
    validator: checkPassword,
    trigger: ['change', 'blur']
  }],
})


const signIn = () => {
  loginFormRef.value.validate(pass => {
    if (pass) {
      login(loginAdmin).then(value => {
        if (value.data.code == HttpStatusCode.OK) {
          // store to pinia
          adminStore.setIsLogin(true);
          adminStore.setAdmin(value.data.data);

          router.push(Behavior.HOME);
          ElMessage.success(value.data.message);
        } else if (value.data.code == HttpStatusCode.CLIENT_ERROR) {
          ElMessage.info(value.data.message);
        } else {
          ElMessage.info("未知错误");
        }
      })
    } else {

    }
  })
}

</script>

<template>
  <div class="bg-area">
    <div class="title-area">在线音乐后台管理系统</div>
    <div class="login-area">
      <el-form
          ref="loginFormRef"
          style="max-width: 600px"
          :model="loginAdmin"
          status-icon
          :rules="rules"
          label-width="auto"
          class="demo-ruleForm"

      >

        <el-form-item prop="username">
          <el-input v-model="loginAdmin.username" type="text" autocomplete="off" placeholder="用户名"
          />
        </el-form-item>

        <el-form-item prop="password">
          <el-input v-model="loginAdmin.password" type="password" autocomplete="off" placeholder="密码"
          />
        </el-form-item>

        <el-form-item class="go-registry">
          <a href="/sign-up">去注册？</a>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="signIn" style="margin-left: 30px">登录</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>


<style scoped lang="scss">
.bg-area {
  height: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  //justify-content: space-around;

  .title-area {
    font-size: 40px;
    margin-top: 200px;
  }

  .login-area {
    margin-top: 20px;
    width: 400px;

    .go-registry {
      float: right;

      a {
        text-decoration: none;
        color: #565555;
      }
    }
  }
}
</style>


