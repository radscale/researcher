import Vue from 'vue'
import store from './store'
import Router from 'vue-router'

import Home from '@/views/Home.vue'
import Profile from '@/views/Profile.vue'
import Project from '@/views/Project.vue'
import Task from '@/views/Task.vue'

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
            component: Profile,
            beforeEnter: function (to, from, next) {
                
                store.dispatch('getUser', {id: to.params.id}).then(next, error => {

                })
            }
        },
        {
            path: '/project/:id',
            name: 'project',
            props (route) {
                return {
                    id: +route.params.id
                }
            },
            component: Project
        },
        {
            path: '/task/:id',
            name: 'task',
            props (route) {
                return {
                    id: +route.params.id
                }
            },
            component: Task
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
