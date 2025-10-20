import axios from 'axios'

// 资讯相关API
export default {
  // 获取资讯详情
  getNews(id) {
    return axios.get(`/news/${id}`)
  },

  // 获取新闻列表
  getNewsList(params) {
    return axios.get('/api/news', { params })
  },

  // 创建资讯
  createNews(data) {
    return axios.post('/news', data)
  },

  // 更新资讯
  updateNews(id, data) {
    return axios.put(`/news/${id}`, data)
  },

  // 删除资讯
  deleteNews(id) {
    return axios.delete(`/news/${id}`)
  },

  // 获取最新资讯
  getLatestNews(limit = 5) {
    return axios.get('/api/news/latest', { params: { limit } })
  },

  // 搜索资讯
  searchNews(keyword, page = 1, size = 10) {
    return axios.get('/api/news/search', { params: { keyword, page, size } })
  }
}