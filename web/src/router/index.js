import {createRouter, createWebHistory} from 'vue-router'
import Main from '../views/Main.vue';
import NotFound from '../views/404.vue';


const router = createRouter({
    history: createWebHistory(),
    routes: [
        {
            path: '/',
            redirect: '/copyright',
        },
        {
            path: '/',
            name: 'Main',
            component: Main,
            children: [
                {
                    path: '/copyright',
                    name: 'copyright',
                    component: () => import('@/views/copyright.vue'),
                },
                {
                    path: '/identify',
                    name: 'identify',
                    component: () => import('@/views/Identify.vue'),
                },
                {
                    path: '/upload',
                    name: 'upload',
                    component: () => import('@/views/upload.vue'),
                },
            ],
        },
        {
            path: '/login',
            name: 'Login',
            component: () => import('@/views/login.vue'),
        },
        {
            path: '/403',
            name: '403',
            component: () => import('@/views/403.vue'),
        },
        // 添加一个通配符路由，用于匹配所有未定义的路由
        {
            path: '/:catchAll(.*)',
            name: 'NotFound',
            component: NotFound
        }
    ]
})

export default router;