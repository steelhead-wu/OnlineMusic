import axios from "axios";
import {Cookie} from "@/api/utils/Cookie.ts";
import {CookiesName} from "@/enum/CookiesName.ts";


export const baseURL = 'http://localhost';

const myAxios = axios.create({
    baseURL,
    timeout: 10000,
    withCredentials: true,

})

myAxios.interceptors.request.use(function (req) {
    console.log('进入请求拦截器');
    let us_au = Cookie.get(CookiesName.US_AU);
    if (us_au) {
        req.headers.Authorization = us_au;
    }
    return req;
}, function (error) {
    // 对请求错误做些什么
    return Promise.reject(error);
});


// 添加请求拦截器
// axios.interceptors.request.use(function (req) {
//     // document.cookie
//     Cookie.get(CookiesName.US_AU);
//     console.log("进入请求拦截器");
//     return req;
// }, function (error) {
//     // 对请求错误做些什么
//     return Promise.reject(error);
// });

// 添加响应拦截器
// axios.interceptors.response.use(function (response) {
//     console.log("进入响应拦截器");
//     // 2xx 范围内的状态码都会触发该函数。
//     // 对响应数据做点什么
//     return response;
// }, function (error) {
//     // 超出 2xx 范围的状态码都会触发该函数。
//     // 对响应错误做点什么
//     return Promise.reject(error);
// });

export default myAxios;

