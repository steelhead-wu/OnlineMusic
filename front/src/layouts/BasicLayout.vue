<script setup lang="ts">

import BasicFooter from "@/layouts/BasicFooter.vue";
import BasicHeader from "@/layouts/BasicHeader.vue";
import PlayBar from "@/components/play/PlayBar.vue";


</script>

<template>
  <div id="basicLayout">
    <el-container class="container">
      <el-header class="header">
        <BasicHeader/>
      </el-header>

      <el-main class="main">
        <keep-alive include="SingerView">
          <RouterView/>
        </keep-alive>
        <PlayBar/>
        <!--        <Test/>-->
      </el-main>

      <el-footer class="footer">
        <BasicFooter></BasicFooter>
      </el-footer>
    </el-container>
  </div>
</template>

<style scoped lang="scss">

#basicLayout {
  height: 100%;
  width: 100%;

  .container {
    height: 100%;
  }

  .header {
    position: fixed;
    width: 100%;
    height: var(--header-height);
    left: 0;
    top: 0;
    padding: 0;
    max-width: 100%;
  }


  .main {
    width: 100%;
    padding: 0;
    margin: var(--margin-top-offset) 0px var(--margin-bottom-offset);
  }

  .footer {
    padding: 0;
    margin: 0;
    position: fixed;
    bottom: 0;
    left: 0;
    width: 100%;
    height: var(--footer-height);
  }
}
</style>