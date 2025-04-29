<script setup lang="ts">
import {ElMessage} from "element-plus";


// after statement router.go(0) ran, display 修改成功
const showMessage = sessionStorage.getItem('showSuccessMessage')
if (showMessage) {
  ElMessage.success(showMessage)
  sessionStorage.removeItem('showSuccessMessage') // 清除状态
}

</script>

<template>
  <router-view/>
</template>


<style scoped lang="scss"></style>