// 密码长度
export const MIN_LEN_PWD = 1, MAX_LEN_PWD = 16;
// 昵称长度
export const MIN_LEN_NK = 1, MAX_LEN_NK = 20;
// 账号长度
export const MIN_LEN_ACC = 3, MAX_LEN_ACC = 11;


export const validateNickname = (rule: object, value: string, callback: Function) => {
    if (value.length < MIN_LEN_NK || value.length > MAX_LEN_NK) {
        callback(new Error('昵称长度不规范'));
        return;
    }

    value = value.trim();
    if (value == '') {
        callback(new Error('不能含有空格'));
        return;
    }
    callback();
}


export const validateAccount = (rule: object, value: string, callback: Function) => {
    if (value.length < MIN_LEN_ACC || value.length > MAX_LEN_ACC) {
        callback(new Error('密码长度不规范'));
        return;
    }
    for (const item of value) {
        if (item === ' ') {
            callback(new Error('不能含有空格'));
            return;
        } else if (item < '0' || item > '9') {
            callback(new Error('只能为纯数字'));
            return;
        }
    }
    callback();
}


export const validatePassword = (rule: object, value: string, callback: Function) => {
    if (value.length < MIN_LEN_PWD || value.length > MAX_LEN_PWD) {
        callback(new Error('密码长度不规范'));
        return;
    }
    if (value.includes(' ')) {
        callback(new Error('不能含有空格'));
        return;
    }
    callback();
}

export const validateRepass = (rule: object, value: string, callback: Function, password: string) => {
    if (value === '') {
        callback(new Error('再次输入密码'))
    } else if (value !== password) {
        callback(new Error("两次输入不匹配"))
    } else {
        callback()
    }
}




export const validateOldPwd = (rule: object, password: string, callback: Function, rawPassword: string) => {
    if (password != rawPassword) {
        callback(new Error());
        return false;
    }
    callback();
    return true;
}