<template>
  <div class="guide-list">
    <!-- 头部导航 -->
    <header class="header">
      <div class="container">
        <h1 class="logo">手游攻略站</h1>
        <nav class="nav">
          <router-link to="/" class="nav-item">首页</router-link>
          <router-link to="/guides" class="nav-item active">攻略</router-link>
          <router-link to="/news" class="nav-item">资讯</router-link>
        </nav>
      </div>
    </header>

    <!-- 攻略列表区域 -->
    <section class="section">
      <div class="container">
        <div class="page-header">
          <h1>游戏攻略</h1>
        </div>

        <!-- 搜索和筛选区域 -->
        <div class="filter-bar">
          <div class="search-box">
            <el-input
              v-model="searchKeyword"
              placeholder="搜索攻略..."
              clearable
              @keyup.enter="searchGuides"
            >
              <template #append>
                <el-button @click="searchGuides">
                  <i class="el-icon-search"></i>
                </el-button>
              </template>
            </el-input>
          </div>
          
          <div class="filters">
            <el-select 
              v-model="selectedCategory" 
              placeholder="全部分类" 
              @change="filterByCategory"
            >
              <el-option label="全部分类" :value="null"></el-option>
              <el-option
                v-for="category in categories"
                :key="category.id"
                :label="category.name"
                :value="category.id"
              >
              </el-option>
            </el-select>
          </div>
        </div>

        <!-- 攻略列表 -->
        <div class="guide-grid" v-if="guides.length > 0">
          <div 
            class="guide-card" 
            v-for="guide in guides" 
            :key="guide.id"
            @click="goToDetail(guide.id)"
          >
            <div class="guide-cover">
              <img :src="guide.coverImage" :alt="guide.title" v-if="guide.coverImage">
              <div class="placeholder" v-else>暂无图片</div>
            </div>
            <div class="guide-info">
              <h3 class="guide-title">{{ guide.title }}</h3>
              <p class="guide-summary">{{ guide.content?.substring(0, 80) }}...</p>
              <div class="guide-meta">
                <span class="date">{{ formatDate(guide.createdAt) }}</span>
                <span class="view-count">浏览: {{ guide.viewCount || 0 }}</span>
                <span class="like-count">点赞: {{ guide.likeCount || 0 }}</span>
              </div>
            </div>
          </div>
        </div>

        <!-- 空状态 -->
        <div class="empty-state" v-else>
          <p>暂无攻略数据</p>
        </div>

        <!-- 分页 -->
        <div class="pagination" v-if="totalPages > 1">
          <el-pagination
            v-model:current-page="currentPage"
            :page-size="pageSize"
            :total="total"
            layout="prev, pager, next"
            @current-change="handlePageChange"
          />
        </div>
      </div>
    </section>

    <!-- 页脚 -->
    <footer class="footer">
      <div class="container">
        <p>&copy; 2023 手游攻略站. All rights reserved.</p>
      </div>
    </footer>
  </div>
</template>

<script>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import axios from 'axios'

export default {
  name: 'GuideList',
  setup() {
    const router = useRouter()
    
    // 数据
    const guides = ref([])
    const categories = ref([])
    const searchKeyword = ref('')
    const selectedCategory = ref(null)
    
    // 分页数据
    const currentPage = ref(1)
    const pageSize = ref(12)
    const total = ref(0)
    const totalPages = ref(0)

    // 获取攻略分类
    const fetchCategories = async () => {
      try {
        const response = await axios.get('/categories/active')
        if (response.data.success) {
          categories.value = response.data.data || []
        }
      } catch (error) {
        console.error('获取分类失败:', error)
      }
    }

    // 获取攻略列表
    const fetchGuides = async () => {
      try {
        const params = {
          page: currentPage.value,
          size: pageSize.value
        }
        
        if (searchKeyword.value) {
          params.keyword = searchKeyword.value
        }
        
        if (selectedCategory.value) {
          params.categoryId = selectedCategory.value
        }
        
        const response = await axios.get('/api/guides', { params })
        if (response.data.success) {
          const data = response.data.data
          guides.value = data.list || []
          total.value = data.total || 0
          totalPages.value = data.pages || 0
        }
      } catch (error) {
        console.error('获取攻略列表失败:', error)
      }
    }

    // 搜索攻略
    const searchGuides = () => {
      currentPage.value = 1
      fetchGuides()
    }

    // 按分类筛选
    const filterByCategory = () => {
      currentPage.value = 1
      fetchGuides()
    }

    // 处理分页变化
    const handlePageChange = (page) => {
      currentPage.value = page
      fetchGuides()
    }

    // 跳转到详情页
    const goToDetail = (id) => {
      router.push(`/guides/${id}`)
    }

    // 格式化日期
    const formatDate = (dateString) => {
      if (!dateString) return ''
      const date = new Date(dateString)
      return date.toLocaleDateString('zh-CN')
    }

    // 组件挂载时获取数据
    onMounted(() => {
      fetchCategories()
      fetchGuides()
    })

    return {
      guides,
      categories,
      searchKeyword,
      selectedCategory,
      currentPage,
      pageSize,
      total,
      totalPages,
      searchGuides,
      filterByCategory,
      handlePageChange,
      goToDetail,
      formatDate
    }
  }
}
</script>

<style lang="scss" scoped>
.guide-list {
  .header {
    background-color: #fff;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
    padding: 1rem 0;
    
    .container {
      display: flex;
      justify-content: space-between;
      align-items: center;
      max-width: 1200px;
      margin: 0 auto;
      padding: 0 1rem;
    }
    
    .logo {
      color: #409eff;
      font-size: 1.5rem;
      font-weight: bold;
    }
    
    .nav {
      display: flex;
      gap: 2rem;
      
      .nav-item {
        text-decoration: none;
        color: #333;
        font-weight: 500;
        transition: color 0.3s;
        
        &.active,
        &:hover {
          color: #409eff;
        }
      }
    }
  }
  
  .section {
    padding: 3rem 0;
    
    .page-header {
      text-align: center;
      margin-bottom: 2rem;
      
      h1 {
        font-size: 2rem;
        color: #333;
        margin-bottom: 0.5rem;
      }
    }
    
    .filter-bar {
      display: flex;
      gap: 1rem;
      margin-bottom: 2rem;
      flex-wrap: wrap;
      
      .search-box {
        flex: 1;
        min-width: 300px;
      }
      
      .filters {
        min-width: 200px;
      }
    }
    
    .guide-grid {
      display: grid;
      grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
      gap: 2rem;
      
      .guide-card {
        background: white;
        border-radius: 8px;
        overflow: hidden;
        box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
        transition: transform 0.3s, box-shadow 0.3s;
        cursor: pointer;
        
        &:hover {
          transform: translateY(-5px);
          box-shadow: 0 4px 20px rgba(0, 0, 0, 0.15);
        }
        
        .guide-cover {
          height: 200px;
          overflow: hidden;
          
          img {
            width: 100%;
            height: 100%;
            object-fit: cover;
          }
          
          .placeholder {
            width: 100%;
            height: 100%;
            display: flex;
            align-items: center;
            justify-content: center;
            background-color: #f5f5f5;
            color: #999;
          }
        }
        
        .guide-info {
          padding: 1rem;
          
          .guide-title {
            font-size: 1.1rem;
            margin-bottom: 0.5rem;
            color: #333;
            display: -webkit-box;
            -webkit-line-clamp: 2;
            -webkit-box-orient: vertical;
            overflow: hidden;
          }
          
          .guide-summary {
            color: #666;
            margin-bottom: 1rem;
            line-height: 1.5;
            display: -webkit-box;
            -webkit-line-clamp: 3;
            -webkit-box-orient: vertical;
            overflow: hidden;
          }
          
          .guide-meta {
            display: flex;
            justify-content: space-between;
            font-size: 0.85rem;
            color: #999;
            
            .date {
              flex: 1;
            }
            
            .view-count,
            .like-count {
              margin-left: 1rem;
            }
          }
        }
      }
    }
    
    .empty-state {
      text-align: center;
      padding: 3rem;
      color: #999;
      font-size: 1.1rem;
    }
    
    .pagination {
      display: flex;
      justify-content: center;
      margin-top: 2rem;
    }
  }
  
  .footer {
    background-color: #333;
    color: white;
    text-align: center;
    padding: 2rem 0;
    margin-top: 3rem;
  }
}

.container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 1rem;
}
</style>