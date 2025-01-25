<template>
  <!--  登录-->
  <div id="login">
    <el-form
        ref="form"
        style="max-width: 600px"
        :model="newUser"
        :rules="rules"
        label-width="auto"
        class="demo-ruleForm"
        :size="formSize"
        status-icon
        v-if="isLogin"

    >
      <el-form-item label="账号" prop="account">
        <el-input
            v-model="newUser.account"
            placeholder="请输入你的账号"
            clearable

        />
      </el-form-item>


      <el-form-item label="密码" prop="password">
        <el-input
            v-model="newUser.password"
            style="width: 240px"
            type="password"
            placeholder="请输入你的密码"
            show-password
            clearable
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="singIn" :disabled="unable">登录</el-button>
        <el-button @click="toggle">返回注册</el-button>
      </el-form-item>
    </el-form>

    <!--注册-->
    <el-form
        style="max-width: 600px"
        :model="newUser"
        :rules="rules"
        label-width="auto"
        class="demo-ruleForm"
        :size="formSize"
        status-icon
        v-if="!isLogin"
    >
      <el-form-item label="昵称" prop="nickname">
        <el-input
            v-model="newUser.nickname"
            style="width: 240px"
            type="text"
            placeholder="昵称"
            clearable
        />
      </el-form-item>

      <el-form-item label="密码" prop="password">
        <el-input
            v-model="newUser.password"
            style="width: 240px"
            type="password"
            placeholder="请输入你的密码"
            show-password
            clearable
        />
      </el-form-item>

      <el-form-item label="再次输入密码" prop="rePassword">
        <el-input
            v-model="newUser.rePassword"
            style="width: 240px"
            type="password"
            placeholder="再次输入你的密码"
            show-password
            clearable
        />
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="signUp">注册</el-button>
        <el-button @click="toggle">返回登录</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script lang="ts" setup>
import {reactive, ref, useTemplateRef} from 'vue'
import type {ComponentSize, FormInstance, FormRules} from 'element-plus'
import {login, register} from "@/api/user/UserApi";
import {ElMessage} from "element-plus";

interface NewUser extends User {
  rePassword: string
}

const formSize = ref<ComponentSize>('default');
const form = ref<FormInstance>()

const unable = ref(false);

const newUser = reactive<NewUser>({
  account: '',
  password: '',
  rePassword: '',
  nickname: '',
});

const isLogin = ref(true); // 登入=1 注册=0

const validateUser = () => {
  if (isLogin) {
    console.log(newUser);
    unable.value = newUser.account === '' || newUser.password === '';
  } else {
    unable.value = newUser.nickname === '' || newUser.password === '' || newUser.rePassword === '' || newUser.password !== newUser.rePassword
  }
}

const toggle = () => {
  isLogin.value = !isLogin.value;
  newUser.password = '';
}
// 密码长度
const min_len_pwd = 3, max_len_pwd = 16;
// 昵称长度
const min_len_nk = 1, max_len_nk = 10;
// 账号长度
const min_len_acc = 10, max_len_acc = 11;


const validateRepass = (rule: object, value: string, callback: Function) => {
  if (value === '') {
    callback(new Error('再次输入密码'))
  } else if (value !== newUser.password) {
    callback(new Error("两次输入不匹配"))
  } else {
    callback()
  }
}


const validateAccount = (rule: object, value: string, callback: Function) => {
  if (value.length < min_len_acc || value.length > max_len_acc) {
    callback(new Error('密码长度不规范'));
    return;
  }
  for (const item of value) {
    if (item === ' ') {
      callback(new Error('不能含有空格'));
      return;
    } else if (item < '0' || item > '9') {
      callback(new Error('只能为纯数字'));
      return;
    }
  }
  callback();
}

const validateNickname = (rule: object, value: string, callback: Function) => {
  if (value.length < min_len_nk || value.length > max_len_nk) {
    callback(new Error('昵称长度不规范'));
    return;
  }
  if (value.includes(' ')) {
    callback(new Error('不能含有空格'));
    return;
  }
  callback();
}
const validatePassword = (rule: object, value: string, callback: Function) => {
  if (value.length < min_len_pwd || value.length > max_len_pwd) {
    callback(new Error('密码长度不规范'));
    return;
  }
  if (value.includes(' ')) {
    callback(new Error('不能含有空格'));
    return;
  }
  callback();
}


// 表单校验
const rules = reactive<FormRules<NewUser>>({
  account: [
    {required: true, message: '请输入账号', trigger: 'blur',},
    {
      message: `长度应该在${min_len_acc}~${max_len_acc}位且为数字，不能含有空格`,
      trigger: ['blur', 'change'],
      validator: validateAccount
    },
  ],
  password: [
    {required: true, message: '请输入密码', trigger: 'blur'},
    {
      message: `长度应该在${min_len_pwd}~${max_len_pwd}位且不能含有空格`,
      trigger: ['blur', 'change'],
      validator: validatePassword,
    },
  ],
  rePassword: [
    {required: true, message: '请再次输入密码', trigger: ['blur'],},
    {validator: validateRepass, trigger: ['blur', 'change']},

  ],
  nickname: [
    {
      required: true,
      message: `昵称不可以为空,且长度应该在${min_len_nk}~${max_len_nk}`,
      trigger: ['blur', 'change'],
      validator: validateNickname
    },
  ],
});


const singIn = () => {
  // const form = useTemplateRef('form');
  form.value.validate(pass => {
    if (!pass) {
      ElMessage({
        type: 'info',
        message: `提示: ${'请认真填写信息'}`,
      })
    } else {
      login(newUser).then(value => {
        ElMessage({
          type: 'success',
          message: `提示: ${value.data.message}`,
        })
      }, reason => {
        ElMessage({
          type: 'info',
          message: `提示: ${reason.data.message}`,
        })
      })
    }
  });
}

const signUp = () => {
  register(newUser);
}
</script>
