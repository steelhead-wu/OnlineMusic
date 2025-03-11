<template>
  <div>
    <p>倒计时剩余时间: {{ formattedTime }}</p>
    <button @click="resetTimer">重置倒计时</button>
  </div>
</template>

<script lang="ts">
import {ref, onMounted, onUnmounted, onBeforeUnmount} from 'vue';

export default {
  setup() {
    const TOTAL_TIME = 20 * 60; // 总时长：20分钟（单位：秒）
    const STORAGE_KEY = 'timerData';

    // 响应式数据
    const formattedTime = ref('20:00');
    let timer = null;

    // 格式化时间为 MM:SS
    const formatTime = (seconds) => {
      const minutes = Math.floor(seconds / 60);
      const secs = seconds % 60;
      return `${String(minutes).padStart(2, '0')}:${String(secs).padStart(2, '0')}`;
    };

    // 计算剩余时间
    const getRemainingTime = () => {
      const savedData = localStorage.getItem(STORAGE_KEY);
      if (!savedData) return TOTAL_TIME;

      const {startTime, totalTime} = JSON.parse(savedData);
      const elapsed = Math.floor((Date.now() - startTime) / 1000);
      const remaining = totalTime - elapsed;
      return remaining > 0 ? remaining : 0;
    };

    // 更新倒计时显示
    const updateTimerDisplay = () => {
      const remaining = getRemainingTime();
      formattedTime.value = formatTime(remaining);

      if (remaining <= 0) {
        clearInterval(timer);
        localStorage.removeItem(STORAGE_KEY);
        alert('倒计时结束！');
      }
    };

    // 启动定时器（仅在前台运行）
    const startTimer = () => {
      if (timer) clearInterval(timer);
      timer = setInterval(updateTimerDisplay, 1000);
    };

    // 重置倒计时
    const resetTimer = () => {
      localStorage.setItem(STORAGE_KEY, JSON.stringify({
        startTime: Date.now(),
        totalTime: TOTAL_TIME,
      }));
      startTimer();
      updateTimerDisplay();
    };

    // 初始化：恢复或新建倒计时
    const initializeTimer = () => {
      const savedData = localStorage.getItem(STORAGE_KEY);
      if (!savedData) {
        resetTimer(); // 首次启动
      } else {
        const remaining = getRemainingTime();
        if (remaining <= 0) {
          localStorage.removeItem(STORAGE_KEY);
          alert('倒计时已结束！');
        } else {
          startTimer();
          updateTimerDisplay();
        }
      }
    };

    // 监听页面可见性变化（切换标签页/窗口）
    const handleVisibilityChange = () => {

      if (document.visibilityState === 'visible') {
        console.log("visibility event has been fired -- visible");
        updateTimerDisplay(); // 返回页面时立即更新
        startTimer();
      } else {
        console.log("visibility event has been fired -- hidden");
        clearInterval(timer); // 离开页面时停止定时器（节省资源）
      }
    };

    // 组件挂载
    onMounted(() => {
      console.log("组件加载")
      initializeTimer();
      document.addEventListener('visibilitychange', handleVisibilityChange);
    });

    // 组件卸载
    onUnmounted(() => {
      console.log("组件卸载");
      clearInterval(timer);
      document.removeEventListener('visibilitychange', handleVisibilityChange);
    });

    onBeforeUnmount(() => {
      console.log("组件卸载前");
    });

    return {formattedTime, resetTimer};
  },
};
</script>