<script setup lang="ts">
import {computed, ref, watch} from 'vue'
import {Search, House} from '@element-plus/icons-vue'
import {searchSong} from "@/api/song/SongApi";
import {useRoute, useRouter} from "vue-router";


const keyWord = ref<string>();
const select = ref<string>('1');

const router = useRouter()
const route = useRoute()


const search = () => {
  if (keyWord.value) {
    searchSong(keyWord.value).then(value => {
    });
  }
}

const login = () => {
  router.push('/login');
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
      <el-col :span="3" class="sign-in">
        <!--        <a href="/login" style="color: white ;background-color: black">-->
        <el-button type="primary" color="black" round @click="login">登录</el-button>
        <!--        </a>-->
      </el-col>

      <el-col :span="3" class="sign-up">
        <a href="/registry" style="color: black">
          <el-button type="primary" color="#ffffff" round>注册</el-button>
        </a>
      </el-col>

    </el-row>
  </div>
</template>


<style scoped>

.el-input__wrapper {
  background-color: #1f1f1f;
  /*background: ;*/
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
  background: #1f1f1f;
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

