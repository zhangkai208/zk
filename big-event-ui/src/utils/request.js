//定制请求的实例
import axios from 'axios';
import { ElMessage } from 'element-plus'
import { useTokenStore } from '../stores/token.js'
import router from '@/router/index.js';

//定义一个变量,记录公共的前缀  ,  baseURL
const baseURL = '/api';
const instance = axios.create({baseURL})

// 是否正在刷新token
let isRefreshing = false;
// 等待token刷新的请求队列
let requests = [];

instance.interceptors.request.use(
    config => {
        const tokenStore = useTokenStore();
        const token = tokenStore.token;
        if(token){
            config.headers.Authorization = token;
        }
        return config;
    },
    err => Promise.reject(err)
)

//添加响应拦截器
instance.interceptors.response.use(
    result => {
        if(result.data.code === 0){
            return result.data;
        }
        // 不再全局提示错误，让调用方自己处理
        return Promise.reject(result.data);
    },
    err => {
        if(err.response?.status === 401){
            // 如果在登录页面，不提示错误
            if(router.currentRoute.value.path !== '/login') {
                ElMessage.error('请先登录');
                router.push('/login');
            }
            return Promise.reject(err);
        }
        
        // 其他错误只在非登录页面提示
        if(router.currentRoute.value.path !== '/login') {
            ElMessage.error(err.response?.data?.msg || '服务器错误');
        }
        return Promise.reject(err);
    }
)

export default instance;