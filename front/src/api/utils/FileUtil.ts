import {ElMessage, type UploadRawFile} from "element-plus";


/**
 *
 * @param rawFile file
 * @param type file type
 * @param size file size in MB
 */
export const beforeFileUpload = (rawFile: UploadRawFile, type = 'image/jpeg', size = 5) => {
    if (rawFile.type !== type) {
        ElMessage.error(`Avatar picture must be ${type} format!`)
        return false
    }
    if (rawFile.size / 1024 / 1024 > size) {
        ElMessage.error(`Avatar picture size can not exceed ${size}MB!`)
        return false
    }
    return true
}