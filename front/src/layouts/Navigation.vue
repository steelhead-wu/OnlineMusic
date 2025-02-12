<script setup lang="ts">

const props = defineProps<{
  styleList: Array<string>,
  name: string
}>();


const emits = defineEmits<{
  click: [item: string]
}>();

const handleChangeView = (val) => {
  emits('click', val);
}
</script>


<template>
  <div id="navigation">
    <ul>
      <li v-for="(item, index) in styleList" :key="index" :class="{ active: item.name === name }"
          @click="handleChangeView(item.name)">
        {{ item.name }}
      </li>
    </ul>
  </div>
</template>


<style scoped>
#navigation {
  width: 100%;
}


#navigation ul li {
  /*display: inline;*/
  line-height: 2rem;
  font-size: 1rem;
  color: grey;
  border-bottom: none;
  cursor: pointer;
}

#navigation ul li.active {
  color: black;
  font-weight: 600;
}
</style>