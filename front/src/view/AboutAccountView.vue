<script setup lang="ts">

import {useRoute, useRouter} from "vue-router";

const router = useRouter();
const route = useRoute();
import {onMounted, ref} from "vue";
import {Behavior} from "@/enum/Behavior";
import {Cookie} from "@/api/utils/Cookie";


const store_key = (route.params.id as string);
const url = window.location.origin + Behavior.ABOUT_ACCOUNT;
const tableData = ref([])
const isValid = ref(true);
const forwardTime = 5000;
const timeToForward = ref(forwardTime);


const updateTime = () => {
  timeToForward.value -= 1000;
}

const startTimer = () => {
  setInterval(updateTime, 1000);
}


const hs = {
  backgroundColor: '#e9eef3',
  fontSize: 'bold',
  color: 'black',
};


onMounted(() => {
  const v = Cookie.get(store_key);
  if (v) {
    isValid.value = true;
    tableData.value.push({account: store_key});
  } else {
    // forward to homepage
    isValid.value = false;
    setTimeout(() => router.push(Behavior.HOME), forwardTime);
    startTimer();

  }
});

</script>

<template>
  <div class="bg">
    <div class="header">
      <h1 v-if="isValid">恭喜你成功注册账号，本网页时效仅{{ 5 }}分钟</h1>
      <h1 v-else>网页已经失效，在{{ timeToForward / 1000 }}秒后跳转至主页</h1>
    </div>

    <div class="info">
      <el-table class="table-data" :data="tableData" size="large" highlight-current-row table-layout="fixed" flexible
                :header-row-style="hs"
      >
        <el-table-column prop="account" label="账号" width="700px" align="center"/>
      </el-table>
    </div>
  </div>
</template>


<style lang="scss">
.bg {
  height: 100%;
  width: auto;
  margin: 0 auto;
  background-image: linear-gradient(15deg, #03a9f4 20%, #ff82aa 60%, #c5c4c4 100%);
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;

  .header {
    //margin-bottom: 100px;
    height: 100px;
  }

  .info {
    .table-data {
      //background-color: red; /* 设置表格背景颜色 */
      width: 100%;

      //margin: 0 auto;
      //width: 100%;
    }
  }
}
</style>