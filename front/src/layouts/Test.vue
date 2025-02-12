<template>
  <div>
    <button @click="handleIncrement">Increment</button>
    <button @click="handleDecrement">Decrement</button>
  </div>
</template>

<script setup lang="ts">
const emit = defineEmits<{
  'increment': [id: number],
  'decrement': [id: number]
}>();

const handleIncrement = () => {
  emit('increment', 10);
};

const handleDecrement = () => {
  emit('decrement', -10);
};
</script>
