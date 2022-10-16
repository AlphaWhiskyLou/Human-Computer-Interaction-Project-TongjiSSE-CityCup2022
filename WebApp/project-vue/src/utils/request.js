import axios from 'axios'
import router from "../router";

//创建axios实例
const instance=axios.create({
  baseURL: 'http://localhost:8080',
  timeout:4000 //请求超时时间
})

// http request 请求拦截器
axios.interceptors.request.use(config => {
  // 在发送请求之前做的事
  let pathname = location.pathname;
  if(localStorage.getItem('token')){
    if(pathname != '/' &&  pathname != '/register'){
      config.headers.common['token'] = localStorage.getItem('token');
    }
  }
  return config;
}, error => {
  // 对请求错误做些什么
  return Promise.reject(error);
});
// http response 响应拦截器
axios.interceptors.response.use(response => {
  return response;
},error => {
  if (error.response) {
    switch (error.response.data.code) {
      // 返回503，清除token信息并跳转到登录页面
      case 503:
        localStorage.removeItem('token');
        router.replace({
          path: '/'
          //登录成功后跳入浏览的当前页面
          // query: {redirect: router.currentRoute.fullPath}
        })
    }
    // 返回接口返回的错误信息
    return Promise.reject(error.response.data);
  }
});

export default instance
