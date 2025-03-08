import {defineStore} from "pinia";
import {ref, computed} from "vue";

export const useTimeStore = defineStore('timeStore', () => {
    const isStart = ref(false);
    const currentTime = ref(new Date().toLocaleTimeString());

    const updateTime = () => {
        currentTime.value = new Date().toLocaleTimeString();
    };

    // 开始定时刷新
    const startTimer = () => {
        if (isStart.value) return; // 防止重复启动
        // timer = setInterval(updateTime, 1000); // 每秒刷新一次
    };

    // 停止定时刷新
    const stopTimer = () => {
        // if (timer) {
        //     clearInterval(timer); // 清除定时器
        //     timer = null;
        // }
    };
})
