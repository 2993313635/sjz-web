import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/Home.vue'
import GuideList from '../views/GuideList.vue'
import GuideDetail from '../views/GuideDetail.vue'
import NewsList from '../views/NewsList.vue'
import NewsDetail from '../views/NewsDetail.vue'

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/guides',
    name: 'GuideList',
    component: GuideList
  },
  {
    path: '/guides/:id',
    name: 'GuideDetail',
    component: GuideDetail,
    props: true
  },
  {
    path: '/news',
    name: 'NewsList',
    component: NewsList
  },
  {
    path: '/news/:id',
    name: 'NewsDetail',
    component: NewsDetail,
    props: true
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router