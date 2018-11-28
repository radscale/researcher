import Vue from 'vue'
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
            component: Home
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
    ]
})
