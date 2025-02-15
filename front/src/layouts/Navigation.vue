<script setup lang="ts">

const props = defineProps<{
  styleList: Array<string>,
  name: string
}>();


const emits = defineEmits<{
  click: [item: string]
}>();


</script>


<template>
  <div id="navigation">
    <ul>
      <li v-for="(item, index) in props.styleList" :key="index" :class="{ active: item.name === props.name }"
          @click="emits('click', item)">
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
  display: inline;
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

@media screen and (min-width: 668px) {
  #navigation ul li {
    margin: 0.5rem 1rem;
  }
}

@media screen and (max-width: 668px) {
  #navigation ul li {
    margin: 0.3rem 0.4rem;
  }
}
</style>