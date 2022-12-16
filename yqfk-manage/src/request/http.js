// 在http.js中引入axios
import axios from 'axios'; // 引入axios
import config from '../../config'
import store from '../store'
import router from '@/router'

axios.defaults.timeout = 10000;







const baseUrl = process.env.NODE_ENV === 'development' ? config.baseUrl.dev : config.baseUrl.pro

const API_OK = 200;
const API_ERROR = 1;
// 响应拦截器
axios.defaults.timeout = 600000;
//请求不能超过20秒
axios.interceptors.request.use(function (config) {
  return config;
}, function (error) {
  return Promise.reject(error);
});
axios.interceptors.response.use(function (response) {
  return response;
}, function (error) {
  return Promise.reject(error);
});
export function baseUrlPlace() {
  return baseUrl
}


/**
 * get方法，对应get请求
 * @param {String} url [请求的url地址]
 * @param {Object} params [请求时携带的参数]
 *
 */
export function get(url, params) {
  store.commit('getToken')
  const token = store.state.user.token
  axios.defaults.headers.get['Content-Type'] = 'application/json';
  axios.defaults.headers.get['token'] = token;

  return new Promise((resolve, reject) => {
    axios.get(baseUrl + url, {
      params: params
    }).then(res => {
      if(res.data.code === 401){
        localStorage.clear();
        sessionStorage.clear();
        window.location.href = "/login"
      }
      if (res.status === API_OK) {
        resolve(res.data);
      }
      if (res.status === API_ERROR) {
        resolve(res.data);
      }
    }).catch(err => {
      reject(err.data)
    })
  });
}


/**
 * post方法，对应post请求
 * @param {String} url [请求的url地址]
 * @param {Object} params [请求时携带的参数]
 */
export function post(url, params) {

  store.commit('getToken')
  const token = store.state.user.token
  axios.defaults.headers.post['Content-Type'] = 'application/json';
  axios.defaults.headers.post['token'] = token;

  return new Promise((resolve, reject) => {
    axios.post(baseUrl + url, JSON.stringify(params))
      .then(res => {
        if(res.headers['system_error'] === 'token_invalid'){
          console.info("system_error"+res.headers['system_error']);
          store.commit('clearToken')
          store.commit('clearMenu')
          router.push("/login")
          alert("token失效，请重新登录");
          return;
        }
        if (res.status === API_OK) {
          resolve(res.data);
        }
        if (res.status === API_ERROR) {
          resolve(res.data);
        }
      })
      .catch(err => {
        reject(err.data)
      })
  });
}



