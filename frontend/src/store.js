import Vue from 'vue'
import Vuex from 'vuex'
import _ from 'lodash'

Vue.use(Vuex)

export default new Vuex.Store({
    state: {
        projects: [],  // Available projects
        tasks: [],  // Tasks for the currently viewed project
        users: [],  // Users attached to the project
        messages: [],  // Message history for the logged in user

        currentUser: {  // Currently viewed user
            item: false
            // Add other context based data here
        },
        currentProject: {  // Currentl viewed project
            item: false,
            tasks: []
            // Add other context based data here
        }
    },
    mutations: {
        setProjects (state, projects) {
            state.projects = projects
        },
        setUser (state, user) {
            state.currentUser.item = user
        },
        setProject (state, project) {
            state.currentProject.item = project
        },
        setTasksForProject (state, tasks) {
            state.currentProject.tasks = tasks
        },
        setMessages (state, messages) {
            state.messages = messages
        }
    },
    actions: {
        updateProjects (context) {
            return new Promise((resolve, reject) => {
                Vue.axios.get('projects').then(res => {
                    context.commit('setProjects', res.data)
                    resolve(res)
                }, err => {
                    reject(err)
                })
            })
        },
        getUser (context, payload = {}) {
            return new Promise((resolve, reject) => {
                Vue.axios.get('users/' + payload.id).then(res => {
                    context.commit('setUser', res.data)
                    resolve(res)
                }, err => {
                    reject(err)
                })
            })
        },
        getProject (context, payload = {}) {
            return new Promise((resolve, reject) => {
                Vue.axios.get('projects/' + payload.id).then(res => {
                    context.commit('setProject', res.data)
                    resolve(res)
                }, err => {
                    reject(err)
                })
                // Do the supplemental data outside of the resolve flow
                Vue.axios.get('tasks/?projectId=' + payload.id).then((res) => {
                    context.commit('setTasksForProject', res.data)
                })
            })
        },
        updateMessages (context) {
            return new Promise((resolve, reject) => {
                Vue.axios.get('messages').then(res => {
                    context.commit('setMessages', res.data)
                    resolve(res)
                }, err => {
                    reject(err)
                })
            })
        }
    }
})
