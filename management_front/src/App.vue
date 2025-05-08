<script setup lang="ts">
import {ElMessage} from "element-plus";
import {onMounted} from "vue";
import {Cookie} from "@/util/Cookie.ts";
import {CookiesName} from "@/enums/CookiesName.ts";
import {useRouter} from "vue-router";

const router = useRouter();


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
  <router-view/>
</template>


<style scoped lang="scss"></style>