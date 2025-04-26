import {ElMessage} from "element-plus";

export const getFormatTime = (): string => {
    const date = new Date();
    return `${date.getFullYear()}-${date.getMonth() < 9 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1}-${date.getDate() < 10 ? '0' + date.getDate() : date.getDate()} ${date.getHours() < 10 ? '0' + date.getHours() : date.getHours()}:${date.getMinutes() < 10 ? '0' + (date.getMinutes()) : date.getMinutes()}:${date.getSeconds() < 10 ? '0' + (date.getSeconds()) : date.getSeconds()}`;
}

/**
 * transform into yyyy-MM-dd
 * @param date
 */
export const formatDate = (date: Date): string => {
    return `${date.getFullYear()}-${date.getMonth() < 9 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1}-${date.getDate() < 10 ? '0' + date.getDate() : date.getDate()}`;
}


/**
 * 用户未登录的操作
 */
export const tips = () => {
    ElMessage({
        type: 'info',
        message: `提示: 请先登入！`,
    })
}
