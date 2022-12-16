import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [
    {
        path: '/',
        name: 'Main',
        component: () => import('@/views/Main.vue'),
        redirect: '/home',
        children: [ ]
    },
    {
        path: '/login',
        name: 'login',
        component: () => import('@/views/login.vue')
    },
    
]

const router = new VueRouter({
    mode: 'history',
    routes
})

export default router