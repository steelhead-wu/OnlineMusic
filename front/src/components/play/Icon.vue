<template>
  <svg class="icon" aria-hidden="true">
    <use :href="icon"></use>
  </svg>
</template>

<script lang="ts" setup>
// import {defineComponent} from "vue";

interface Props {
  icon: string
}

defineProps<Props>();

// export default defineComponent({
//   props: {
//     icon: String,
//   },
// });
</script>

<style scoped>
.icon {

}
</style>
