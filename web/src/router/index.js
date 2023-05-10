import {createRouter, createWebHistory} from 'vue-router'
import Home from '../views/home.vue';


export default createRouter({
    history: createWebHistory(),
    routes: [
        {
            path: '/',
            redirect: '/copyright',
        },
        {
            path: '/',
            name: 'Home',
            component: Home,
            children: [
                {
                    path: '/copyright',
                    name: 'copyright',
                    component: () => import('@/views/copyright.vue'),
                },
                {
                    path: '/table',
                    name: 'basetable',
                    component: () => import('@/views/table.vue'),
                },
                {
                    path: '/charts',
                    name: 'basecharts',
                    component: () => import('@/views/charts.vue'),
                },
                {
                    path: '/form',
                    name: 'baseform',
                    component: () => import('@/views/form.vue'),
                },
                {
                    path: '/tabs',
                    name: 'tabs',
                    component: () => import('@/views/tabs.vue'),
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
    ]
})

