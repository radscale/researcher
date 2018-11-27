import Vue from 'vue'
import store from './store'
import bus from './bus.js'
import Router from 'vue-router'

import Home from '@/views/Home.vue'
import Profile from '@/views/Profile.vue'
import Project from '@/views/Project.vue'
import Task from '@/views/Task.vue'

Vue.use(Router)

const storeFunction = function(storeAction, errorMessage, callback) {
    return function (to, from, next) {
        store.dispatch(storeAction, {id: to.params.id}).then(res => {
            if (typeof callback == 'function') {
                callback()
            }
            next()
        }, error => {
            bus.pushMessage({
                type: 'error',
                content: errorMessage
            })
        })
    }
}

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
