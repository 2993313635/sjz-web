import axios from 'axios'

// 首页相关API
export default {
  // 获取首页数据
  getHomeData() {
    return axios.get('/api/home')
  }
}