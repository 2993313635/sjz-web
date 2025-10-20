<template>
  <div class="guide-detail">
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

    <!-- 攻略详情区域 -->
    <section class="section">
      <div class="container">
        <div class="guide-content" v-if="guide">
          <!-- 返回按钮 -->
          <div class="back-link">
            <el-button @click="goBack" type="primary" plain>
              <i class="el-icon-arrow-left"></i> 返回攻略列表
            </el-button>
          </div>

          <!-- 攻略标题 -->
          <div class="guide-header">
            <h1 class="guide-title">{{ guide.title }}</h1>
            <div class="guide-meta">
              <span class="date">发布时间: {{ formatDate(guide.createdAt) }}</span>
              <span class="view-count">浏览: {{ guide.viewCount || 0 }}</span>
              <span class="like-count">点赞: {{ guide.likeCount || 0 }}</span>
            </div>
          </div>

          <!-- 封面图 -->
          <div class="guide-cover" v-if="guide.coverImage">
            <img :src="guide.coverImage" :alt="guide.title">
          </div>

          <!-- 攻略内容 -->
          <div class="guide-body">
            <div class="content" v-html="guide.content"></div>
          </div>

          <!-- 视频区域 -->
          <div class="video-section" v-if="guide.videoUrl">
            <h3>相关视频</h3>
            <div class="video-container">
              <video controls width="100%">
                <source :src="guide.videoUrl" type="video/mp4">
                您的浏览器不支持视频播放。
              </video>
            </div>
          </div>

          <!-- 附件下载 -->
          <div class="attachment-section" v-if="guide.attachmentUrl">
            <h3>附件下载</h3>
            <el-button @click="downloadAttachment" type="primary">
              下载附件
            </el-button>
          </div>

          <!-- 点赞按钮 -->
          <div class="actions">
            <el-button 
              @click="likeGuide" 
              type="danger" 
              plain
              :loading="liking"
            >
              <i class="el-icon-thumb"></i> 点赞 ({{ guide.likeCount || 0 }})
            </el-button>
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
          <p>攻略不存在或加载失败</p>
          <el-button @click="goBack" type="primary">返回攻略列表</el-button>
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
  name: 'GuideDetail',
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
    const guide = ref(null)
    const loading = ref(true)
    const liking = ref(false)

    // 获取攻略详情
    const fetchGuideDetail = async () => {
      try {
        loading.value = true
        const response = await axios.get(`/api/guides/${props.id}`)
        if (response.data.success) {
          guide.value = response.data.data
        }
      } catch (error) {
        console.error('获取攻略详情失败:', error)
      } finally {
        loading.value = false
      }
    }

    // 点赞攻略
    const likeGuide = async () => {
      try {
        liking.value = true
        const response = await axios.post(`/guides/${props.id}/like`)
        if (response.data.success) {
          // 更新点赞数
          if (guide.value) {
            guide.value.likeCount = (guide.value.likeCount || 0) + 1
          }
        }
      } catch (error) {
        console.error('点赞失败:', error)
      } finally {
        liking.value = false
      }
    }

    // 下载附件
    const downloadAttachment = () => {
      if (guide.value && guide.value.attachmentUrl) {
        window.open(guide.value.attachmentUrl, '_blank')
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
      fetchGuideDetail()
    })

    return {
      guide,
      loading,
      liking,
      likeGuide,
      downloadAttachment,
      goBack,
      formatDate
    }
  }
}
</script>

<style lang="scss" scoped>
.guide-detail {
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
    
    .guide-content {
      background: white;
      border-radius: 8px;
      padding: 2rem;
      box-shadow: 0 2px 12px rgba(0, 0, 0, 0.05);
      
      .back-link {
        margin-bottom: 1.5rem;
      }
      
      .guide-header {
        margin-bottom: 2rem;
        padding-bottom: 1rem;
        border-bottom: 1px solid #eee;
        
        .guide-title {
          font-size: 2rem;
          color: #333;
          margin-bottom: 1rem;
          line-height: 1.3;
        }
        
        .guide-meta {
          display: flex;
          gap: 1.5rem;
          font-size: 0.9rem;
          color: #666;
        }
      }
      
      .guide-cover {
        width: 100%;
        margin-bottom: 2rem;
        
        img {
          width: 100%;
          border-radius: 8px;
          box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
        }
      }
      
      .guide-body {
        margin-bottom: 2rem;
        
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
      
      .video-section {
        margin-bottom: 2rem;
        
        h3 {
          font-size: 1.3rem;
          margin-bottom: 1rem;
          color: #333;
        }
        
        .video-container {
          background: #000;
          border-radius: 8px;
          overflow: hidden;
        }
      }
      
      .attachment-section {
        margin-bottom: 2rem;
        
        h3 {
          font-size: 1.3rem;
          margin-bottom: 1rem;
          color: #333;
        }
      }
      
      .actions {
        text-align: center;
        padding-top: 1rem;
        border-top: 1px solid #eee;
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