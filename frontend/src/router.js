import Vue from 'vue'
import Router from 'vue-router'

import Home from '@/views/Home.vue'
import Profile from '@/views/Profile.vue'
import About from '@/views/About.vue'

Vue.use(Router)

export default new Router({
    routes: [
        {
            path: '/',
            name: 'home',
            component: Home,
        },
        {
            path: '/profile/:id',
            name: 'profile',
            props (route) {
                return {
                    id: +route.params.id
                }
            },
            component: Profile
        },
        {
            path: '/about',
            name: 'about',
            component: About
        }

        // {
        //     path: '/about',
        //     name: 'about',
        //     // route level code-splitting
        //     // this generates a separate chunk (about.[hash].js) for this route
        //     // which is lazy-loaded when the route is visited.
        //     component: () => import(/* webpackChunkName: "about" */ './views/About.vue')
        // }
    ]
})
