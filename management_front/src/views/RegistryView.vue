<script lang="ts" setup>
import {reactive, ref} from 'vue'
import {ElMessage, type FormInstance, type FormRules} from 'element-plus'
import type Admin from "@/pojo/Admin.ts";
import {isExistWhiteSpace} from "@/util/StringUtils.ts";


import {registry} from "@/api/admin/AdminApi.ts";
import {HttpStatusCode} from "@/enums/HttpStatusCode.ts";
import {Behavior} from "@/enums/Behavior.ts";
import {useRouter} from "vue-router";

const router = useRouter();
const registryFormRef = ref<FormInstance>()

interface registryAdmin extends Admin {
  confirm: string
}

const admin = reactive<registryAdmin>({
  username: '',
  password: '',
  confirm: '',
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

const checkConfirm = (rule: object, value: string, callback: Function, password: string) => {
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

  if (password != value) {
    rule.message = '密码不匹配';
    callback(new Error());
    return;
  }
  callback();
}

const rules = reactive<FormRules<typeof admin>>({
  username: [{
    trigger: ['change', 'blur'],
    validator: checkUsername
  }
  ],
  password: [{
    validator: checkPassword,
    trigger: ['change', 'blur']
  }],
  confirm: [{
    validator: (rule: object, value: string, callback: Function) => checkConfirm(rule, value, callback, admin.password),
    trigger: ['change', 'blur']
  }],
})



const signUp = () => {
  registryFormRef.value.validate(pass => {
    if (pass) {
      registry(admin).then(value => {
        if (value.data.code == HttpStatusCode.OK) {
          router.push(Behavior.SIGN_IN);
          ElMessage.success(value.data.message);

        } else if (value.data.code == HttpStatusCode.CLIENT_ERROR) {
          ElMessage.info(value.data.message);
        } else {
          ElMessage.info("未知错误");
        }
      })
    }
  })
}

</script>

<template>
  <div class="bg-area">
    <div class="title-area">在线音乐后台管理系统</div>
    <div class="registry-area">
      <el-form
          ref="registryFormRef"
          style="max-width: 600px"
          :model="admin"
          status-icon
          :rules="rules"
          label-width="auto"
          class="demo-ruleForm"

      >

        <el-form-item prop="username">
          <el-input v-model="admin.username" type="text" autocomplete="off" placeholder="用户名"/>
        </el-form-item>

        <el-form-item prop="password">
          <el-input v-model="admin.password" type="password" autocomplete="off" placeholder="密码"
          />
        </el-form-item>

        <el-form-item prop="confirm">
          <el-input v-model="admin.confirm" type="password" autocomplete="off" placeholder="确认密码"/>

        </el-form-item>

        <el-form-item class="go-registry">
          <a href="/sign-in">去登录？</a>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="signUp" style="margin-left: 30px">注册</el-button>
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

  .registry-area {
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


