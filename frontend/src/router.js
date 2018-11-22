import Vue from 'vue'
import Router from 'vue-router'

import Landing from './views/Landing.vue'
import About from '@/views/About.vue'

Vue.use(Router)

export default new Router({
    routes: [
        {
            path: '/',
            name: 'landing',
            component: Landing,
            meta: {
                hiddenLogo: true,
                freeNavbar: true
            }
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
