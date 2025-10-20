<template>
  <div class="home">
    <!-- 头部导航 -->
    <header class="header">
      <div class="container">
        <h1 class="logo">手游攻略站</h1>
        <nav class="nav">
          <router-link to="/" class="nav-item active">首页</router-link>
          <router-link to="/guides" class="nav-item">攻略</router-link>
          <router-link to="/news" class="nav-item">资讯</router-link>
        </nav>
      </div>
    </header>

    <!-- 轮播图区域 -->
    <section class="banner-section">
      <div class="container">
        <el-carousel 
          height="400px" 
          indicator-position="outside"
          :interval="4000"
          arrow="hover"
        >
          <el-carousel-item v-for="banner in banners" :key="banner.id">
            <div class="banner-item" :style="{ backgroundImage: `url(${banner.imageUrl})` }">
              <div class="banner-content">
                <h2>{{ banner.title }}</h2>
              </div>
            </div>
          </el-carousel-item>
        </el-carousel>
      </div>
    </section>

    <!-- 热门攻略 -->
    <section class="section">
      <div class="container">
        <div class="section-header">
          <h2>热门攻略</h2>
          <router-link to="/guides" class="more-link">更多 ></router-link>
        </div>
        <div class="guide-grid">
          <div 
            class="guide-card" 
            v-for="guide in hotGuides" 
            :key="guide.id"
            @click="goToGuide(guide.id)"
          >
            <div class="guide-cover">
              <img :src="guide.coverImage" :alt="guide.title" v-if="guide.coverImage">
              <div class="placeholder" v-else>暂无图片</div>
            </div>
            <div class="guide-info">
              <h3 class="guide-title">{{ guide.title }}</h3>
              <div class="guide-meta">
                <span class="view-count">浏览: {{ guide.viewCount || 0 }}</span>
                <span class="like-count">点赞: {{ guide.likeCount || 0 }}</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>

    <!-- 最新攻略 -->
    <section class="section bg-light">
      <div class="container">
        <div class="section-header">
          <h2>最新攻略</h2>
          <router-link to="/guides" class="more-link">更多 ></router-link>
        </div>
        <div class="guide-list">
          <div 
            class="guide-item" 
            v-for="guide in latestGuides" 
            :key="guide.id"
            @click="goToGuide(guide.id)"
          >
            <div class="guide-item-content">
              <h3 class="guide-title">{{ guide.title }}</h3>
              <p class="guide-summary">{{ guide.content?.substring(0, 100) }}...</p>
              <div class="guide-meta">
                <span class="date">{{ formatDate(guide.createdAt) }}</span>
                <span class="view-count">浏览: {{ guide.viewCount || 0 }}</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>

    <!-- 最新资讯 -->
    <section class="section">
      <div class="container">
        <div class="section-header">
          <h2>最新资讯</h2>
          <router-link to="/news" class="more-link">更多 ></router-link>
        </div>
        <div class="news-list">
          <div 
            class="news-item" 
            v-for="news in latestNews" 
            :key="news.id"
            @click="goToNews(news.id)"
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
  name: 'Home',
  setup() {
    const router = useRouter()
    
    // 数据
    const banners = ref([])
    const hotGuides = ref([])
    const latestGuides = ref([])
    const latestNews = ref([])

    // 获取首页数据
    const fetchHomeData = async () => {
      try {
        const response = await axios.get('/api/home')
        if (response.data.success) {
          const data = response.data.data
          banners.value = data.banners || []
          hotGuides.value = data.hotGuides || []
          latestGuides.value = data.latestGuides || []
          latestNews.value = data.latestNews || []
        }
      } catch (error) {
        console.error('获取首页数据失败:', error)
      }
    }

    // 跳转到攻略详情
    const goToGuide = (id) => {
      router.push(`/guides/${id}`)
    }

    // 跳转到资讯详情
    const goToNews = (id) => {
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
      fetchHomeData()
    })

    return {
      banners,
      hotGuides,
      latestGuides,
      latestNews,
      goToGuide,
      goToNews,
      formatDate
    }
  }
}
</script>

<style lang="scss" scoped>
.home {
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
  
  .banner-section {
    margin: 2rem 0;
    
    .banner-item {
      width: 100%;
      height: 400px;
      background-size: cover;
      background-position: center;
      position: relative;
      border-radius: 8px;
      overflow: hidden;
      
      .banner-content {
        position: absolute;
        bottom: 0;
        left: 0;
        right: 0;
        background: linear-gradient(transparent, rgba(0, 0, 0, 0.7));
        padding: 2rem;
        color: white;
        
        h2 {
          font-size: 2rem;
          margin-bottom: 0.5rem;
        }
      }
    }
  }
  
  .section {
    padding: 3rem 0;
    
    &.bg-light {
      background-color: #f8f9fa;
    }
    
    .section-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin-bottom: 2rem;
      
      h2 {
        font-size: 1.8rem;
        color: #333;
        position: relative;
        padding-left: 1rem;
        
        &::before {
          content: '';
          position: absolute;
          left: 0;
          top: 50%;
          transform: translateY(-50%);
          width: 4px;
          height: 24px;
          background-color: #409eff;
          border-radius: 2px;
        }
      }
      
      .more-link {
        text-decoration: none;
        color: #666;
        font-size: 0.9rem;
        transition: color 0.3s;
        
        &:hover {
          color: #409eff;
        }
      }
    }
  }
  
  .guide-grid {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
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
        height: 180px;
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
        
        .guide-meta {
          display: flex;
          justify-content: space-between;
          font-size: 0.85rem;
          color: #999;
        }
      }
    }
  }
  
  .guide-list {
    .guide-item {
      background: white;
      border-radius: 8px;
      padding: 1.5rem;
      margin-bottom: 1rem;
      box-shadow: 0 2px 12px rgba(0, 0, 0, 0.05);
      cursor: pointer;
      transition: box-shadow 0.3s;
      
      &:hover {
        box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
      }
      
      .guide-title {
        font-size: 1.2rem;
        margin-bottom: 0.5rem;
        color: #333;
      }
      
      .guide-summary {
        color: #666;
        margin-bottom: 1rem;
        line-height: 1.6;
      }
      
      .guide-meta {
        display: flex;
        justify-content: space-between;
        font-size: 0.85rem;
        color: #999;
      }
    }
  }
  
  .news-list {
    .news-item {
      display: flex;
      background: white;
      border-radius: 8px;
      overflow: hidden;
      margin-bottom: 1.5rem;
      box-shadow: 0 2px 12px rgba(0, 0, 0, 0.05);
      cursor: pointer;
      transition: box-shadow 0.3s;
      
      &:hover {
        box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
      }
      
      .news-cover {
        width: 200px;
        height: 150px;
        
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
        flex: 1;
        padding: 1.5rem;
        
        .news-title {
          font-size: 1.2rem;
          margin-bottom: 0.5rem;
          color: #333;
        }
        
        .news-summary {
          color: #666;
          margin-bottom: 1rem;
          line-height: 1.6;
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