import Vue from 'vue'
import store from './store'
import bus from './bus.js'
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
            beforeEnter: function (to, from, next) {
                store.dispatch('updateProjects', {id: to.params.id}).then(next, error => {
                    bus.pushMessage({
                        type: 'error',
                        content: 'Could not load data.'
                    })
                })
            }
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
                    bus.pushMessage({
                        type: 'error',
                        content: 'Could not load profile data.'
                    })
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
            component: Project,
            beforeEnter: function (to, from, next) {
                store.dispatch('getProject', {id: to.params.id}).then(next, error => {
                    bus.pushMessage({
                        type: 'error',
                        content: 'Could not load project data.'
                    })
                })
            }
        },
        {
            path: '/task/:id',
            name: 'task',
            props (route) {
                return {
                    id: +route.params.id
                }
            },
            component: Task,
            beforeEnter: function (to, from, next) {
                store.dispatch('getTask', {id: to.params.id}).then(next, error => {
                    bus.pushMessage({
                        type: 'error',
                        content: 'Could not load task data.'
                    })
                })
            }
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
