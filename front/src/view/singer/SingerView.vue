<script setup lang="ts">
import Navigation from "@/layouts/Navigation.vue";
import Test from "@/layouts/Test.vue";
import {ref} from "vue";
import {Singer, singer_style_list} from "@/enum/Singer";


</script>


<template>
  <Navigation :style-list="singer_style_list"/>
  <h1>singer</h1>
</template>


<style scoped>

</style>