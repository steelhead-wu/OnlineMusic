import axios from "axios";
import {CookiesName} from "@/enums/CookiesName.ts";
import {Cookie} from "@/util/Cookie.ts";


export const baseURL = 'http://localhost:80';

const myAxios = axios.create({
    baseURL,
    timeout: 10000,
    withCredentials: true,
})

myAxios.interceptors.request.use(function (req) {
    console.log("进入请求拦截器1");
    let ad_au = Cookie.get(CookiesName.AD_AU);
    if (ad_au) {
        req.headers.Authorization = ad_au;
    }
    return req;
}, function (error) {
    // 对请求错误做些什么
    return Promise.reject(error);
});


myAxios.interceptors.response.use(function (response) {
    console.log("进入响应拦截器1");
    // 2xx 范围内的状态码都会触发该函数。
    // 对响应数据做点什么
    return response;
}, function (error) {
    // 超出 2xx 范围的状态码都会触发该函数。
    // 对响应错误做点什么
    return Promise.reject(error);
});


export default myAxios;

