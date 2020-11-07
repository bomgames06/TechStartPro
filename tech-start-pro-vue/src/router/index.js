import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'

Vue.use(VueRouter)

const routes = [
    {
        path: '/',
        name: 'Home',
        component: Home
    },
    {
        path: '/categories',
        name: 'CategoryList',
        component: () => import(/* webpackChunkName: "about" */ '../views/category/CategoryList.vue')
    },
    {
        path: '/products',
        name: 'ProductList',
        component: () => import(/* webpackChunkName: "about" */ '../views/product/ProductList.vue')
    },
]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})

export default router
