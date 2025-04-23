import {defineStore} from "pinia";
import {computed, ref} from "vue";

/*
在 Setup Store 中：

ref() 就是 state 属性
computed() 就是 getters
function() 就是 actions

注意，要让 pinia 正确识别 state，你必须在 setup store 中返回 state 的所有属性。这意味着，你不能在 store 中使用私有属性。
不完整返回会影响 SSR ，开发工具和其他插件的正常运行。

 */
export const useTestStore = defineStore('testStore', () => {
    const arr = ref([]);

    const getArr = computed(() => arr.value);
    const setArr = (array) => {
        arr.value = array;
    }

    const curIdx = ref(-1);
    const getCurIdx = computed(() => curIdx.value);
    const setCurIdx = (idx) => {
        curIdx.value = idx;
    }


    return {arr, getArr, setArr, curIdx, getCurIdx, setCurIdx};
});