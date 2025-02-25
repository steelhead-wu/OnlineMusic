<template>
  <div id="basicFooter">
    <a href="https://y.qq.com/" target="_blank" style="padding: 0">QQ音乐</a>
    &nbsp;
    <a href="https://y.qq.com/" target="_blank" style="padding: 0">QQ音乐</a>
    &nbsp;
    <a href="https://y.qq.com/" target="_blank" style="padding: 0">QQ音乐</a>
    &nbsp;
    <a href="https://y.qq.com/" target="_blank" style="padding: 0">QQ音乐</a>
    &nbsp;
    <a href="https://y.qq.com/" target="_blank" style="padding: 0;display: block">QQ音乐</a>

    <p class="copyright" v-for="(item,idx) in copyright">
      {{ item }}
    </p>
  </div>
</template>

<script setup>
import {readonly} from "vue";

const copyright = readonly([
  "关于 | 帮助 | 条款 | 反馈",
  "Copyright © 2025 WLL",
])
</script>

<style scoped>

#basicFooter .copyright {

}

a {
  display: inline;
}

#basicFooter {
  text-align: center;
  background: #333333;
  bottom: 0;
  right: 0;
  left: 0;
  position: fixed;
  font-size: medium;
  color: rgba(153, 153, 153, 0.6);
  height: 100px;
}

</style>
