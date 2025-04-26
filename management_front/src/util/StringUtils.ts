export const isEmpty = (str: string): boolean => {
    return str == null || str == '';
}


export const isExistWhiteSpace = (str: string): boolean => {
    if (isEmpty(str)) return false;
    for (let s of str) {
        if (s == ' ') {
            return true;
        }
    }
    return false;
}