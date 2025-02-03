<script setup lang="ts">
import {computed, ref} from 'vue'
import {Search, House} from '@element-plus/icons-vue'
import {searchSong} from "@/api/song/SongApi";
import {useRoute, useRouter} from "vue-router";
import {useGlobalStatusStore} from "@/store/GlobalStatusStore";
import {Behavior} from "@/enum/Behavior";
import {AvatarSize} from "@/enum/AvatarSize";
import {dropDownList, UserDropDown} from "@/enum/UserDropDown";
import {useUserStore} from "@/store/UserStore";
import {ArrowDown} from '@element-plus/icons-vue'

const keyWord = ref<string>();
const select = ref<string>('1');

const router = useRouter()
const route = useRoute()
const globalStatusStore = useGlobalStatusStore();
const userStore = useUserStore();
const defaultAvatar = ref<string>('https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png');

const search = () => {
  if (keyWord.value) {
    searchSong(keyWord.value).then(value => {
    });
  }
}

const login = () => {
  console.log("进入login");
  // globalStatusStore.isLoginOrSignup = true;
  // globalStatusStore.isLogin = true;
  // console.log(JSON.stringify(globalStatusStore));
  router.push(Behavior.SIGN_IN);
  console.log("退出login");
}

const registry = () => {
  console.log("进入registry")
  globalStatusStore.isLoginOrSignup = true;
  globalStatusStore.isLogin = false;
  // router.push('/sign-up');
  router.push(Behavior.SIGN_UP);

}


const doDropDown = (dropdown) => {
  console.log("进入doDropDown");
  // console.log(JSON.stringify(userStore));
  // console.log(JSON.stringify(globalStatusStore));
  console.log(userStore.getLoginUser.value);
  if (dropdown.name === UserDropDown.SIGN_OUT) {
    // userStore.isOnline = false;
    userStore.$reset();
    router.push(Behavior.HOME);
    return;
  } else if (dropdown.name === UserDropDown.PERSONAL) {

  } else if (dropdown.name === UserDropDown.SETTINGS) {

  } else {

  }
  router.push(dropdown.path);
}

</script>

<template>

  <div id="basicHeader">
    <el-row>

      <!--      图标-->
      <el-col>
        <img src="../assets/img/logo.png" alt="logo" style="height: 76px;left: 40px;position: fixed">
      </el-col>


      <!--      搜索框-->
      <el-col :span="24" class="searchBox">
        <el-input
            v-model="keyWord"
            style="max-width: 600px; top: 20px;"
            placeholder="请输入内容"
            class="input-with-select "

        >
          <template #append>
            <el-button :icon="Search" @click="search"/>
          </template>
          <!--            <template #prepend>-->
          <!--              <el-select v-model="select" placeholder="歌曲" style="width: 115px">-->
          <!--                <el-option label="歌曲" value="1"/>-->
          <!--                <el-option label="歌手" value="2"/>-->
          <!--              </el-select>-->
          <!--            </template>-->
        </el-input>
      </el-col>
      <!--      主页-->
      <el-col>
        <a href="/" title="去主页">
          <el-button :icon="House" circle style="position: fixed;left: 430px;top: 15px" size="large"
                     tag="a"></el-button>
        </a>
      </el-col>


      <!--      登录-->
      <div v-if="!userStore.isOnline">
        <el-col :span="3" class="sign-in">

          <el-button type="primary" color="black" round @click="login">登录</el-button>

        </el-col>
        <!--注册-->
        <el-col :span="3" class="sign-up">
          <!--                <a href="/sign-up" style="color: black">-->
          <!--                <a :href="Behavior.SIGN_UP" style="color: black">-->
          <el-button type="primary" color="#ffffff" round @click="registry">注册</el-button>
          <!--        </a>-->
        </el-col>
      </div>
      <!--右上角头像-->
      <el-col v-else class="avatar">
        <el-dropdown size="large" trigger="hover">
          <el-avatar alt="default avatar" :size="AvatarSize.LARGE"
                     :src="userStore.getLoginUser.value.avatar"/>
          <!--                     src="http://localhost/asset/img/avatorImages/1547476912596tou.jpg"/>-->
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item v-for="(dropdown,idx) in dropDownList" :key="idx" @click="doDropDown(dropdown)">
                {{ dropdown.name }}
              </el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>


      </el-col>

    </el-row>
  </div>
</template>


<style scoped>

.el-input__wrapper {
  background-color: #1f1f1f;
  /*background: ;*/
}

.avatar {
  position: fixed;
  right: 15px;
  top: 10px;
}

.input-with-select {
  left: 30%;
  top: 15px;
}

.sign-in {
  border: none;
  position: fixed;
  right: 95px;
  top: 20px;
  /*background: #1f1f1f;*/
  background: black;
}

.sign-up {
  /*background: #9d9191;*/
  border: none;
  position: fixed;
  right: 20px;
  top: 20px;
  background: black;
}

.searchBox {
  /*border: solid red;*/
  /*height: 20px;*/
  /*box-shadow: 0 10 0 0 #eede15;*/
  width: 100%;
  height: 76px;
  /*left: 590px;*/
  position: fixed;

}

#basicHeader {
  /*background-color: #b20f96;*/
  width: 100%;
  position: fixed;
  top: 0;
  right: 0;
  left: 0;
  /*display: flex;*/
  flex-direction: row;
  height: 76px;
  background: #000001;
}


.el-row {
  margin-bottom: 20px;
}

.el-row:last-child {
  margin-bottom: 0;
}

.el-col {
  border-radius: 4px;
}

.grid-content {
  border-radius: 4px;
  min-height: 36px;
}

.input-with-select .el-input-group__prepend {
  /*background-color: var(--el-fill-color-blank);*/
  background-color: red;
}
</style>

