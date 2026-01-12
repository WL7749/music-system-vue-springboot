import Vue from 'vue'
import Router from 'vue-router'
import Layout from '@/components/Layout'

Vue.use(Router)

export default new Router({
  mode: 'hash',
  routes: [
    {
      path: '/',
      component: Layout,
      redirect: '/consumer_list', // 默认跳转用户列表

      children: [
        {
          path: '/consumer_list',
          name: 'consumer_list',
          component: () => import('@/consumer/list.vue')
        },
        {
          path: '/singer_list',
          name: 'singer_list',
          component: () => import('@/singer/list.vue')
        },
        {
          path: '/song_list',
          name: 'song_list',
          component: () => import('@/song/list.vue')
        },
        {
          path: '/songlist_list',
          name: 'songlist_list',
          component: () => import('@/song_list/list.vue')
        },
        {
          path: '/song_list',
          name: 'song_list',
          component: () => import('@/song/list.vue')
        },
      ]
    }
  ]
})