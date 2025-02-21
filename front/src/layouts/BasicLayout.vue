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
        <keep-alive include="SingerView">
          <RouterView/>
        </keep-alive>
        <PlayBar/>
        <!--        <Test/>-->
      </el-main>

      <el-footer>
        <BasicFooter></BasicFooter>
      </el-footer>
    </el-container>
  </div>
</template>

<style scoped>

</style>