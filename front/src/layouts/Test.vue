<template>
  <div>
    <button @click="add">add</button>
    <ul>
      <li v-for="(i,j) in testStore.getArr" @click="packup(j)" :class="{fw:j===testStore.getCurIdx}">
        {{ i }}
        <button @click.stop="remove(j)">del</button>
      </li>
    </ul>
  </div>
</template>

<script setup lang="ts">
import {ref} from "vue";
import {useTestStore} from "@/store/TestStore";

const cnt = ref(0);
const testStore = useTestStore();

const packup = (j) => {
  testStore.setCurIdx(j);
}
const add = () => {
  testStore.getArr.push(cnt.value++);
  testStore.setCurIdx(testStore.getCurIdx + 1);
}

const remove = (song_idx) => {
  if (song_idx < testStore.getCurIdx) {
    testStore.setCurIdx(testStore.getCurIdx - 1);
  } else if (song_idx === testStore.getCurIdx) {
    if (testStore.getCurIdx + 1 === testStore.getArr.length) {
      testStore.setCurIdx(testStore.getCurIdx - 1);
    }
  }
  console.log(testStore.getCurIdx);
  testStore.getArr.splice(song_idx, 1);
}
</script>


<style scoped>
.fw {
  font-weight: bold;
}
</style>
