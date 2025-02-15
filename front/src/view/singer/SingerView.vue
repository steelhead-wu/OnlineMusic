<script setup lang="ts">
import Navigation from "@/layouts/Navigation.vue";
import Test from "@/layouts/Test.vue";
import {ref} from "vue";
import {Singer, singer_style_list} from "@/enum/Singer";


const nameOpt = ref('全部歌手');

const doClick = (item) => {
  console.log(item);
  nameOpt.value = item.name;
}
</script>


<template>
  <Navigation :name="nameOpt" :style-list="singer_style_list" @click="doClick"/>
  <h1>singer</h1>
</template>


<style scoped>

</style>