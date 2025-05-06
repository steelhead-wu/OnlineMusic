import {Behavior} from "@/enums/Behavior.ts";


export enum UserDropDown {
    PERSONAL = '个人中心',
    SETTINGS = '设置',
    SIGN_OUT = '退出登录',
}


export const dropDownList = [
    {
        name: UserDropDown.PERSONAL,
        path: Behavior.PERSONAL,
    },
    {
        name: UserDropDown.SETTINGS,
        path: Behavior.SETTINGS,
    },
    {
        name: UserDropDown.SIGN_OUT,
        path: Behavior.SIGN_OUT,
    },
]