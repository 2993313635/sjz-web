import axios from 'axios'

// 分类相关API
export default {
  // 获取所有分类
  getCategories() {
    return axios.get('/categories')
  },

  // 获取活跃分类
  getActiveCategories() {
    return axios.get('/categories/active')
  },

  // 获取分类详情
  getCategory(id) {
    return axios.get(`/categories/${id}`)
  },

  // 创建分类
  createCategory(data) {
    return axios.post('/categories', data)
  },

  // 更新分类
  updateCategory(id, data) {
    return axios.put(`/categories/${id}`, data)
  },

  // 删除分类
  deleteCategory(id) {
    return axios.delete(`/categories/${id}`)
  },

  // 更新分类状态
  updateCategoryStatus(id, status) {
    return axios.put(`/categories/${id}/status`, null, { params: { status } })
  }
}