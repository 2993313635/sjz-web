import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import axios from 'axios'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'

// 设置axios基础URL为空，因为我们使用Vite代理
axios.defaults.baseURL = ''

// 创建Vue应用实例
const app = createApp(App)

// 全局配置axios
app.config.globalProperties.$http = axios

// 使用Element Plus
app.use(ElementPlus)

// 使用路由
app.use(router)

// 挂载应用
app.mount('#app')