<template>
  <div class="news-list">
    <!-- 头部导航 -->
    <header class="header">
      <div class="container">
        <h1 class="logo">手游攻略站</h1>
        <nav class="nav">
          <router-link to="/" class="nav-item">首页</router-link>
          <router-link to="/guides" class="nav-item">攻略</router-link>
          <router-link to="/news" class="nav-item active">资讯</router-link>
        </nav>
      </div>
    </header>

    <!-- 资讯列表区域 -->
    <section class="section">
      <div class="container">
        <div class="page-header">
          <h1>游戏资讯</h1>
        </div>

        <!-- 搜索区域 -->
        <div class="search-bar">
          <div class="search-box">
            <el-input
              v-model="searchKeyword"
              placeholder="搜索资讯..."
              clearable
              @keyup.enter="searchNews"
            >
              <template #append>
                <el-button @click="searchNews">
                  <i class="el-icon-search"></i>
                </el-button>
              </template>
            </el-input>
          </div>
        </div>

        <!-- 资讯列表 -->
        <div class="news-grid" v-if="newsList.length > 0">
          <div 
            class="news-card" 
            v-for="news in newsList" 
            :key="news.id"
            @click="goToDetail(news.id)"
          >
            <div class="news-cover">
              <img :src="news.coverImage" :alt="news.title" v-if="news.coverImage">
              <div class="placeholder" v-else>暂无图片</div>
            </div>
            <div class="news-info">
              <h3 class="news-title">{{ news.title }}</h3>
              <p class="news-summary">{{ news.summary }}</p>
              <div class="news-meta">
                <span class="date">{{ formatDate(news.createdAt) }}</span>
              </div>
            </div>
          </div>
        </div>

        <!-- 空状态 -->
        <div class="empty-state" v-else>
          <p>暂无资讯数据</p>
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
  name: 'NewsList',
  setup() {
    const router = useRouter()
    
    // 数据
    const newsList = ref([])
    const searchKeyword = ref('')
    
    // 分页数据
    const currentPage = ref(1)
    const pageSize = ref(10)
    const total = ref(0)
    const totalPages = ref(0)

    // 获取资讯列表
    const fetchNews = async () => {
      try {
        const params = {
          page: currentPage.value,
          size: pageSize.value
        }
        
        if (searchKeyword.value) {
          params.keyword = searchKeyword.value
        }
        
        const response = await axios.get('/api/news', { params })
        if (response.data.success) {
          const data = response.data.data
          newsList.value = data.list || []
          total.value = data.total || 0
          totalPages.value = data.pages || 0
        }
      } catch (error) {
        console.error('获取资讯列表失败:', error)
      }
    }

    // 搜索资讯
    const searchNews = () => {
      currentPage.value = 1
      fetchNews()
    }

    // 处理分页变化
    const handlePageChange = (page) => {
      currentPage.value = page
      fetchNews()
    }

    // 跳转到详情页
    const goToDetail = (id) => {
      router.push(`/news/${id}`)
    }

    // 格式化日期
    const formatDate = (dateString) => {
      if (!dateString) return ''
      const date = new Date(dateString)
      return date.toLocaleDateString('zh-CN')
    }

    // 组件挂载时获取数据
    onMounted(() => {
      fetchNews()
    })

    return {
      newsList,
      searchKeyword,
      currentPage,
      pageSize,
      total,
      totalPages,
      searchNews,
      handlePageChange,
      goToDetail,
      formatDate
    }
  }
}
</script>

<style lang="scss" scoped>
.news-list {
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
    
    .search-bar {
      margin-bottom: 2rem;
      
      .search-box {
        max-width: 500px;
        margin: 0 auto;
      }
    }
    
    .news-grid {
      display: grid;
      grid-template-columns: repeat(auto-fill, minmax(350px, 1fr));
      gap: 2rem;
      
      .news-card {
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
        
        .news-cover {
          height: 200px;
          
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
        
        .news-info {
          padding: 1.5rem;
          
          .news-title {
            font-size: 1.2rem;
            margin-bottom: 0.5rem;
            color: #333;
            display: -webkit-box;
            -webkit-line-clamp: 2;
            -webkit-box-orient: vertical;
            overflow: hidden;
          }
          
          .news-summary {
            color: #666;
            margin-bottom: 1rem;
            line-height: 1.6;
            display: -webkit-box;
            -webkit-line-clamp: 3;
            -webkit-box-orient: vertical;
            overflow: hidden;
          }
          
          .news-meta {
            .date {
              font-size: 0.85rem;
              color: #999;
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