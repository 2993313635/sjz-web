<template>
  <div class="news-detail">
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

    <!-- 资讯详情区域 -->
    <section class="section">
      <div class="container">
        <div class="news-content" v-if="news">
          <!-- 返回按钮 -->
          <div class="back-link">
            <el-button @click="goBack" type="primary" plain>
              <i class="el-icon-arrow-left"></i> 返回资讯列表
            </el-button>
          </div>

          <!-- 资讯标题 -->
          <div class="news-header">
            <h1 class="news-title">{{ news.title }}</h1>
            <div class="news-meta">
              <span class="date">发布时间: {{ formatDate(news.createdAt) }}</span>
            </div>
          </div>

          <!-- 封面图 -->
          <div class="news-cover" v-if="news.coverImage">
            <img :src="news.coverImage" :alt="news.title">
          </div>

          <!-- 资讯摘要 -->
          <div class="news-summary" v-if="news.summary">
            <p>{{ news.summary }}</p>
          </div>

          <!-- 资讯内容 -->
          <div class="news-body">
            <div class="content" v-html="news.content"></div>
          </div>
        </div>

        <!-- 加载状态 -->
        <div class="loading" v-else-if="loading">
          <el-skeleton animated>
            <template #template>
              <el-skeleton-item variant="h1" style="width: 50%" />
              <div style="margin-top: 20px;">
                <el-skeleton-item variant="p" style="width: 100%" />
                <el-skeleton-item variant="p" style="width: 80%; margin-top: 10px;" />
                <el-skeleton-item variant="p" style="width: 90%; margin-top: 10px;" />
              </div>
            </template>
          </el-skeleton>
        </div>

        <!-- 错误状态 -->
        <div class="error-state" v-else>
          <p>资讯不存在或加载失败</p>
          <el-button @click="goBack" type="primary">返回资讯列表</el-button>
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
import { useRoute, useRouter } from 'vue-router'
import axios from 'axios'

export default {
  name: 'NewsDetail',
  props: {
    id: {
      type: [String, Number],
      required: true
    }
  },
  setup(props) {
    const route = useRoute()
    const router = useRouter()
    
    // 数据
    const news = ref(null)
    const loading = ref(true)

    // 获取资讯详情
    const fetchNewsDetail = async () => {
      try {
        loading.value = true
        const response = await axios.get(`/api/news/${props.id}`)
        if (response.data.success) {
          news.value = response.data.data
        }
      } catch (error) {
        console.error('获取资讯详情失败:', error)
      } finally {
        loading.value = false
      }
    }

    // 返回上一页
    const goBack = () => {
      router.go(-1)
    }

    // 格式化日期
    const formatDate = (dateString) => {
      if (!dateString) return ''
      const date = new Date(dateString)
      return date.toLocaleDateString('zh-CN')
    }

    // 组件挂载时获取数据
    onMounted(() => {
      fetchNewsDetail()
    })

    return {
      news,
      loading,
      goBack,
      formatDate
    }
  }
}
</script>

<style lang="scss" scoped>
.news-detail {
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
    
    .news-content {
      background: white;
      border-radius: 8px;
      padding: 2rem;
      box-shadow: 0 2px 12px rgba(0, 0, 0, 0.05);
      
      .back-link {
        margin-bottom: 1.5rem;
      }
      
      .news-header {
        margin-bottom: 2rem;
        padding-bottom: 1rem;
        border-bottom: 1px solid #eee;
        
        .news-title {
          font-size: 2rem;
          color: #333;
          margin-bottom: 1rem;
          line-height: 1.3;
        }
        
        .news-meta {
          .date {
            font-size: 0.9rem;
            color: #666;
          }
        }
      }
      
      .news-cover {
        width: 100%;
        margin-bottom: 2rem;
        
        img {
          width: 100%;
          border-radius: 8px;
          box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
        }
      }
      
      .news-summary {
        margin-bottom: 2rem;
        padding: 1rem;
        background-color: #f8f9fa;
        border-left: 4px solid #409eff;
        border-radius: 4px;
        
        p {
          font-size: 1.1rem;
          color: #555;
          line-height: 1.6;
        }
      }
      
      .news-body {
        .content {
          line-height: 1.8;
          font-size: 1.05rem;
          color: #333;
          
          :deep(img) {
            max-width: 100%;
            height: auto;
            border-radius: 4px;
            margin: 1rem 0;
          }
          
          :deep(p) {
            margin-bottom: 1rem;
          }
          
          :deep(h1),
          :deep(h2),
          :deep(h3) {
            margin: 1.5rem 0 1rem;
            color: #333;
          }
          
          :deep(ul),
          :deep(ol) {
            padding-left: 1.5rem;
            margin-bottom: 1rem;
          }
          
          :deep(li) {
            margin-bottom: 0.5rem;
          }
        }
      }
    }
    
    .loading {
      background: white;
      border-radius: 8px;
      padding: 2rem;
      box-shadow: 0 2px 12px rgba(0, 0, 0, 0.05);
    }
    
    .error-state {
      background: white;
      border-radius: 8px;
      padding: 3rem;
      text-align: center;
      box-shadow: 0 2px 12px rgba(0, 0, 0, 0.05);
      
      p {
        font-size: 1.1rem;
        color: #666;
        margin-bottom: 1.5rem;
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