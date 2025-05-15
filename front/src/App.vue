<script setup lang="ts">

import {onMounted} from "vue";
import {ElMessage} from "element-plus";

onMounted(() => {
// after statement router.go(0) ran, display 修改成功
  const showMessage = sessionStorage.getItem('showSuccessMessage')
  if (showMessage) {
    ElMessage.success(showMessage)
    sessionStorage.removeItem('showSuccessMessage') // 清除状态
  }

  // const cookie = Cookie.get(CookiesName.AD_AU);

  // if (cookie == '') {
  //   ElMessage.info('请先去注册或登录');
  // router.push(Behavior.SIGN_IN);
  // }
})

</script>

<template>
  <!--  <div id="app">-->

  <!--  </div>-->
  <router-view></router-view>

  <!--  <audio preload="metadata" ref="music" id="myAudio" :src="music_src" type="audio/mpeg"-->
  <!--         @ended="ended"></audio>-->
</template>



