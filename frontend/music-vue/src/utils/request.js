import axios from 'axios'
import router from "@/router";
import qs from "qs"

axios.defaults.withCredentials = true //让ajax请求携带cookie

// 创建请求对象
const request = axios.create({
    baseURL: 'http://localhost:8085', // 
    timeout: 5000
})

// response 拦截器
request.interceptors.response.use(
    response => {
        let res = response.data;

        if (response.config.responseType === 'blob') {
            return res
        }

        if (typeof res === 'string') {
            res = res ? JSON.parse(res) : res
        }
        return res;
    },
    error => {
        console.log('err' + error)
        return Promise.reject(error)
    }
)
console.log(request.defaults.baseURL)

export default request