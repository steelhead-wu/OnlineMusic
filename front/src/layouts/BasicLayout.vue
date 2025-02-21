<script setup lang="ts">

import BasicFooter from "@/layouts/BasicFooter.vue";
import BasicHeader from "@/layouts/BasicHeader.vue";
import PlayBar from "@/components/play/PlayBar.vue";


</script>

<template>
  <div id="basicLayout">
    <el-container>
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
  .header {
    position: fixed;
    width: 100%;
    height: 76px;
    left: 0;
    top: 0;
    padding: 0;
    max-width: 100%;
  }


  .main {
    width: 100%;
    height: 100%;
    //max-height: calc(100% - 76px - 100px);
    padding: 0;
  }

  .footer {
    padding: 0;
    margin: 0;
    position: fixed;
    bottom: 0;
    left: 0;
    width: 100%;
    height: 100px;
  }
}
</style>