# 手游攻略网站前端

这是一个基于Vue 3 + Vite的手游攻略网站前端项目，用于与后端Java API进行交互。

## 项目结构

```
src/
├── api/          # API接口封装
├── assets/       # 静态资源文件
├── components/   # 公共组件
├── router/       # 路由配置
├── views/        # 页面组件
├── App.vue       #   根组件
└── main.js       # 入口文件
```

## 功能模块

1. **首页模块**
   - 轮播图展示
   - 热门攻略推荐
   - 最新攻略列表
   - 最新资讯列表

2. **攻略模块**
   - 攻略列表展示（支持分页）
   - 攻略详情查看
   - 攻略搜索功能
   - 攻略分类筛选
   - 攻略点赞功能

3. **资讯模块**
   - 资讯列表展示（支持分页）
   - 资讯详情查看
   - 资讯搜索功能

## 技术栈

- Vue 3 (Composition API)
- Vue Router 4
- Axios (HTTP客户端)
- Element Plus (UI组件库)
- Sass (CSS预处理器)
- Vite (构建工具)

## 开发环境搭建

1. 安装依赖：
```bash
npm install
```

2. 启动开发服务器：
```bash
npm run dev
```

3. 构建生产版本：
```bash
npm run build
```

## 项目配置

### 代理配置
在 `vite.config.js` 中配置了API代理：
```javascript
server: {
  proxy: {
    '/api': {
      target: 'http://localhost:8080',
      changeOrigin: true,
      rewrite: (path) => path.replace(/^\/api/, '')
    }
  }
}
```

### 环境变量
可以在项目根目录创建 `.env` 文件来配置环境变量：
```
VITE_API_BASE_URL=http://localhost:8080
```

## API接口

所有API接口都封装在 `src/api` 目录下，包括：
- 攻略相关接口 (guide.js)
- 资讯相关接口 (news.js)
- 分类相关接口 (category.js)
- Banner相关接口 (banner.js)
- 首页相关接口 (home.js)

## 路由配置

路由配置在 `src/router/index.js` 文件中，包括以下路径：
- `/` - 首页
- `/guides` - 攻略列表
- `/guides/:id` - 攻略详情
- `/news` - 资讯列表
- `/news/:id` - 资讯详情

## 组件说明

### 页面组件
- `Home.vue` - 首页
- `GuideList.vue` - 攻略列表页
- `GuideDetail.vue` - 攻略详情页
- `NewsList.vue` - 资讯列表页
- `NewsDetail.vue` - 资讯详情页

## 样式规范

项目使用Sass作为CSS预处理器，遵循以下规范：
1. 使用BEM命名规范
2. 组件样式使用scoped属性隔离
3. 公共样式定义在App.vue中
4. 响应式设计适配移动端

## 部署说明

1. 构建项目：
```bash
npm run build
```

2. 将 `dist` 目录下的文件部署到Web服务器

## 注意事项

1. 确保后端服务正常运行
2. 注意跨域配置
3. 图片资源需要正确配置路径
4. 视频播放需要支持相应格式