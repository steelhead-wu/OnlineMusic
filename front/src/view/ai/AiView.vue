<script setup lang="ts">
import {ref} from 'vue'
import {ElIcon} from 'element-plus'
import {MagicStick} from '@element-plus/icons-vue'

const iconColor = ref('#7e57c2')
const iconSize = ref(32)
const isShowAi = ref(true);


const toggleAi = () => {
  isShowAi.value = !isShowAi.value
}

</script>

<template>
  <div class="ai-container">
    <el-icon :size="iconSize" :color="iconColor" class="animated-icon" @click="toggleAi">
      <magic-stick/>
    </el-icon>
  </div>
</template>

<style scoped lang="scss">
.ai-container {
  width: 50px;
  height: 50px;
  position: fixed;
  right: -25px;
  bottom: 120px;
  z-index: 2100;
  display: flex;
  justify-content: center;
  align-items: center;

  .animated-icon {
    transition: transform 0.3s ease;

    &:hover {
      transform: translateX(-20px);
    }

  }
}
</style>
