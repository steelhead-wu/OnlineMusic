<script setup lang="ts">

import BasicFooter from "@/layouts/BasicFooter.vue";
import BasicHeader from "@/layouts/BasicHeader.vue";
import PlayBar from "@/components/play/PlayBar.vue";


</script>

<template>
  <div id="basicLayout">
    <el-container>

      <el-header>
        <BasicHeader/>
      </el-header>

      <el-main>
        <RouterView/>
        <PlayBar/>
      </el-main>

      <el-footer>
        <BasicFooter></BasicFooter>
      </el-footer>
    </el-container>
  </div>
</template>

<style scoped>

</style>