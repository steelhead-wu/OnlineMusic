import {ElMessage, type UploadRawFile} from "element-plus";
import type Result from "@/util/Result.ts";


/**
 *
 * @param rawFile file
 * @param type file type
 * @param size file size in MB
 */
export const beforeFileUpload = (rawFile: UploadRawFile, type = 'image/jpeg', size = 5) => {
    if (type == '') {
        ElMessage.error(`文件必须为lrc类型`)
        return false;
    }
    if (rawFile.type !== type) {
        ElMessage.error(`file must be ${type} format!`)
        return false
    }
    if (rawFile.size / 1024 / 1024 > size) {
        ElMessage.error(`Avatar picture size can not exceed ${size}MB!`)
        return false
    }
    return true
}


export const afterSuccessFileUpload = (response: Result, uploadFile: UploadRawFile) => {

}