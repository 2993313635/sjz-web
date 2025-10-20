import axios from 'axios'

// 攻略相关API
export default {
  // 获取攻略详情
  getGuide(id) {
    return axios.get(`/guides/${id}`)
  },

  // 分页查询攻略列表
  getGuides(params) {
    return axios.get('/guides', { params })
  },

  // 创建攻略
  createGuide(data) {
    return axios.post('/guides', data)
  },

  // 更新攻略
  updateGuide(id, data) {
    return axios.put(`/guides/${id}`, data)
  },

  // 删除攻略
  deleteGuide(id) {
    return axios.delete(`/guides/${id}`)
  },

  // 点赞攻略
  likeGuide(id) {
    return axios.post(`/guides/${id}/like`)
  },

  // 获取热门攻略
  getHotGuides(limit = 8) {
    return axios.get('/guides/hot', { params: { limit } })
  },

  // 获取最新攻略
  getLatestGuides(limit = 6) {
    return axios.get('/guides/latest', { params: { limit } })
  },

  // 根据分类获取攻略
  getGuidesByCategory(categoryId, page = 1, size = 10) {
    return axios.get(`/api/guides/category/${categoryId}`, { params: { page, size } })
  },

  // 搜索攻略
  searchGuides(keyword, page = 1, size = 10) {
    return axios.get('/api/guides/search', { params: { keyword, page, size } })
  }
}