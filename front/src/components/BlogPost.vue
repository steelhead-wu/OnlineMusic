<script setup>
import {ref, watch, computed} from 'vue';

const [model, modifiers] = defineModel({
  set(value) {
    if (modifiers.capitalize) {
      return value.charAt(0).toUpperCase() + value.slice(1)
    }
    return value
  }
});
const cnt = ref(0);


</script>

<template>
  <div class="el-footer">
    <input type="button" value="nih" class="ds">
    <div>
      <slot name="sd"></slot>
    </div>
    <div>
      <slot></slot>
    </div>
  </div>
</template>


<style>
.ds {
  background-color: yellow;
}
</style>