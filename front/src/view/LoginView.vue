<script lang="ts" setup>
import {computed, reactive, ref, watch, watchEffect} from 'vue'
import type {ComponentSize, FormInstance, FormRules} from 'element-plus'
import {login, register} from "@/api/user/UserApi";
import {ElMessage} from "element-plus";
import {House} from '@element-plus/icons-vue'
import router from "@/routers/router";
import {useGlobalStatusStore} from "@/store/GlobalStatusStore";
import {Behavior} from "@/enum/Behavior";
import {useUserStore} from "@/store/UserStore";

interface NewUser extends User {
  rePassword: string
}

const formSize = ref<ComponentSize>('default');
const form = ref<FormInstance>()

const globalStatusStore = useGlobalStatusStore();
const userStore = useUserStore();

const newUser = reactive<NewUser>({
  account: '',
  password: '',
  rePassword: '',
  nickname: '',
});

const isLogin = ref(true); // 登入=1 注册=0
// const isLogin = globalStatusStore.isLogin // 登入=1 注册=0


const toggle = () => {
  // isLogin.value = !isLogin.value;
  globalStatusStore.isLogin = !globalStatusStore.isLogin;
  newUser.password = '';
}
// 密码长度
const min_len_pwd = 1, max_len_pwd = 16;
// 昵称长度
const min_len_nk = 1, max_len_nk = 10;
// 账号长度
const min_len_acc = 3, max_len_acc = 11;


watch(() => globalStatusStore.isLogin, () => {
  console.log("s")
// ad_word_h3.bottom = isLogin.value ? '500px' : '550px';
// ad_word_h1.bottom = isLogin.value ? '550px' : '600px';
  ad_word_h3.bottom = globalStatusStore.isLogin ? '500px' : '550px';
  ad_word_h1.bottom = globalStatusStore.isLogin ? '550px' : '600px';
});

const ad_word_h3 = reactive({
  position: 'fixed',
  bottom: globalStatusStore.isLogin ? '500px' : '550px',
});

const ad_word_h1 = reactive({
  position: 'fixed',
  bottom: globalStatusStore.isLogin ? '550px' : '600px',
})


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
  form.value.validate(pass => {
    console.log(userStore.getLoginUser);
    if (pass) {
      console.log("进入signIn");
      login(newUser).then(value => {
        ElMessage({
          type: 'success',
          message: `提示: ${value.data.message}`,
        });
        console.log(value.data.data);
        userStore.setLoginUser(value.data.data);
        userStore.isOnline = true;
        console.log(userStore.getLoginUser)
        // globalStatusStore.isLoginOrSignup = false;
        // console.log("globalStatusStore:" + JSON.stringify(globalStatusStore));
        // console.log("userStore:" + JSON.stringify(userStore));

        router.push(Behavior.HOME);
        console.log("退出signIn");

      }, reason => {
        ElMessage({
          type: 'info',
          message: `提示: ${reason.data.message}`,
        })
      })
    } else {
      ElMessage({
        type: 'info',
        message: `提示: 请重新填写`,
      })
    }
  });
}

const signUp = () => {
  form.value.validate(pass => {
    if (pass) {
      register(newUser).then(value => {
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
    } else {
      ElMessage({
        type: 'info',
        message: `提示: 请重新填写`,
      })
    }
  });
}
</script>

<template>
  <!--  登录-->
  <div id="userForm">
    <div class="Ad-word">
      <h1 :style="ad_word_h1">
        欢迎{{ globalStatusStore.isLogin ? '登录' : '注册' }}XX音乐
      </h1>
      <h3 :style="/*{position:'fixed',bottom:bot}*/ad_word_h3">烦恼时，放松自己。</h3>

    </div>
    <el-form
        ref="form"
        style="max-width: 600px"
        :model="newUser"
        :rules="rules"
        label-width="auto"
        class="login"
        :size="formSize"
        status-icon
        v-if="globalStatusStore.isLogin"
    >

      <el-form-item prop="account">
        <el-input
            v-model="newUser.account"
            placeholder="请输入你的账号"
            clearable

        />
      </el-form-item>


      <el-form-item prop="password">
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
        <el-button type="primary" @click="singIn">登录</el-button>
        <el-button @click="toggle">返回注册</el-button>

        <el-button>
          <a href="/" title="去主页" class="goToHomePage">去主页</a>
        </el-button>
      </el-form-item>
    </el-form>

    <!--注册-->
    <el-form
        ref="form"
        style="max-width: 600px"
        :model="newUser"
        :rules="rules"
        label-width="auto"
        class="registry"
        :size="formSize"
        status-icon
        v-if="!globalStatusStore.isLogin"
    >
      <el-form-item prop="nickname">
        <el-input
            v-model="newUser.nickname"
            style="width: 240px"
            type="text"
            placeholder="昵称"
            clearable
        />
      </el-form-item>

      <el-form-item prop="password">
        <el-input
            v-model="newUser.password"
            style="width: 240px"
            type="password"
            placeholder="请输入你的密码"
            show-password
            clearable
        />
      </el-form-item>

      <el-form-item prop="rePassword">
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
        <el-button>
          <a href="/" title="去主页" class="goToHomePage">去主页</a>
        </el-button>
      </el-form-item>


    </el-form>
  </div>
</template>


<style scoped>

#userForm {
  position: fixed;
  bottom: 38%;
  left: 40%;
}

#userForm .login {

}

#userForm .registry {


}

.Ad-word {

}

.Ad-word h3 {
  position: fixed;
  bottom: 550px;
  /*bottom: 500px;*/

}


.Ad-word h1 {
  position: fixed;
  bottom: 600px;
  /*bottom: 550px;*/
}
</style>


