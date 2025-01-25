<template>
  <!--  登录-->
  <el-form
      ref="ruleFormRef"
      style="max-width: 600px"
      :model="ruleForm"
      :rules="rules"
      label-width="auto"
      class="demo-ruleForm"
      :size="formSize"
      status-icon
  >
    <el-form-item label="账号" prop="account" v-if="toggle">
      <el-input
          v-model="ruleForm.account"
          placeholder="请输入你的账号"
          clearable

      />
    </el-form-item>

    <el-form-item label="密码" prop="password">
      <el-input
          v-model="ruleForm.password"
          style="width: 240px"
          type="password"
          placeholder="请输入你的密码"
          show-password
          clearable
      />
    </el-form-item>

    <el-form-item label="再次输入密码" prop="rePassword" v-if="!toggle">
      <el-input
          v-model="ruleForm.rePassword"
          style="width: 240px"
          type="password"
          placeholder="再次输入你的密码"
          show-password
          clearable
      />
    </el-form-item>

    <el-form-item>
      <el-button type="primary" @click="onSubmit">{{ toggle ? '登入' : '注册' }}</el-button>
      <el-button @click="toggle=!toggle">{{ toggle ? '返回注册' : '返回登录' }}</el-button>
    </el-form-item>
  </el-form>

</template>

<script lang="ts" setup>
import {reactive, ref} from 'vue'
import type {ComponentSize, FormInstance, FormRules} from 'element-plus'

interface RuleForm {
  account: string
  password: string
  rePassword: string

}

const formSize = ref<ComponentSize>('default')
const ruleFormRef = ref<FormInstance>()
const ruleForm = reactive<RuleForm>({
  account: '',
  password: '',
  rePassword: '',
})


const toggle = ref(true); // 登入=1 注册=0

const validatePass2 = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('再次输入密码'))
  } else if (value !== ruleForm.password) {
    callback(new Error("两次输入不匹配"))
  } else {
    callback()
  }
}


// 表单校验
const rules = reactive<FormRules<RuleForm>>({
  account: [
    {required: true, message: '请输入用户名', trigger: 'blur'},
    {min: 3, max: 10, message: '长度应该在3~10位', trigger: 'blur'},
  ],
  password: [
    {required: true, message: '请输入密码', trigger: 'blur'},
    {min: 3, max: 10, message: '长度应该在3~10位', trigger: 'blur'},
  ],
  rePassword: [
    {validator: validatePass2, trigger: 'blur'},
    {required: true, message: '请再次输入密码', trigger: 'blur'},
  ],
})

const onSubmit = () => {
  if (toggle.value) {
    console.log('form.account:' + ruleForm.account);
    console.log('form.password:' + ruleForm.password);
  } else {

    console.log('form.password:' + ruleForm.password);
    console.log('form.repassword:' + ruleForm.rePassword);
  }
}
</script>
