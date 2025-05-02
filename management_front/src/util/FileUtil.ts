import {ElMessage, type UploadRawFile} from "element-plus";
import type Result from "@/util/Result.ts";


/**
 *
 * @param rawFile file
 * @param type file type
 * @param size file size in MB
 */
export const beforeFileUpload = (rawFile: UploadRawFile, type: Set<string>, size = 5) => {
    console.log(rawFile);
    if (!type.has(rawFile.type)) {
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