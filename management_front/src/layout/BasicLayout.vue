<script setup lang="ts">

import BasicHeader from "@/layout/BasicHeader.vue";
import BasicAside from "@/layout/BasicAside.vue";
</script>


<template>
  <div class="container">
    <header class="header">
      <basic-header/>
    </header>
    <main class="main">
      <div class="left">
        <basic-aside/>
      </div>
      <div class="right">
        <router-view/>
      </div>
    </main>
    <!--    <footer class="footer">foot</footer>-->
  </div>
</template>


<style scoped lang="scss">
.container {
  height: 100vh;
  display: flex;
  flex-direction: column;

  .header {
    height: 100px;
  }

  .main {
    flex: 1;
    display: flex;

    .left {
      width: 250px;
    }

    .right {
      flex: 1;
    }
  }

  .footer {
    //position: fixed;
    //bottom: 0;
    width: 100%;
    height: 100px;
  }
}
</style>