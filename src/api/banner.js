import axios from 'axios'

// Banner相关API
export default {
  // 获取所有激活的Banner
  getActiveBanners() {
    return axios.get('/banners')
  },

  // 获取Banner详情
  getBanner(id) {
    return axios.get(`/banners/${id}`)
  },

  // 创建Banner
  createBanner(data) {
    return axios.post('/banners', data)
  },

  // 更新Banner
  updateBanner(id, data) {
    return axios.put(`/banners/${id}`, data)
  },

  // 删除Banner
  deleteBanner(id) {
    return axios.delete(`/banners/${id}`)
  },

  // 更新Banner状态
  updateBannerStatus(id, isActive) {
    return axios.put(`/banners/${id}/status`, null, { params: { isActive } })
  }
}