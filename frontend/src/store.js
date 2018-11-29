import Vue from 'vue'
import Vuex from 'vuex'
import _ from 'lodash'

Vue.use(Vuex)

export default new Vuex.Store({
    state: {
        projects: [],  // Available projects
        users: [],  // Available users
        groups: [],  // Available groups
        messages: [],  // Message history for the logged in user

        currentUser: {  // Currently viewed user
            item: false,
            groups: [],
            projects: [],
            tasks: []
            // Add other context based data here
        },
        currentProject: {  // Currently viewed project
            item: false,
            tasks: [],
            creator: null,
            users: []
            // Add other context based data here
        },
        currentTask: {  // Currently viewed task
            item: false
        }
    },
    mutations: {
        setProjects (state, projects) {
            state.projects = projects
        },
        setUsers (state, users) {
            state.users = users
        },
        setGroups (state, groups) {
            state.groups = groups
        },

        setUser (state, user) {
            // Clear user data
            state.currentUser.item = user
            state.currentUser.groups = []
            state.currentUser.projects = []
            state.currentUser.tasks = []
        },
        setUserGroups (state, groups) {  // TODO: filter on backend
            state.currentUser.groups = _.filter(
                groups,
                item => item.members.includes(state.currentUser.item.id)
            )
        },
        setUserProjects (state, projects) {  // TODO: filter on backend
            state.currentUser.projects = _.filter(
                projects,
                item => item.members.includes(state.currentUser.item.id)
            )
        },
        setUserTasks (state, tasks) {  // TODO: filter on backend
            state.currentUser.tasks = _.filter(
                tasks,
                item => item.members.includes(state.currentUser.item.id)
            )
        },
        

        setProject (state, project) {
            state.currentProject.item = project
        },
        setTask (state, task) {
            state.currentTask.item = task
        },
        setTasksForProject (state, tasks) {
            state.currentProject.tasks = tasks
        },
        setMessages (state, messages) {
            state.messages = messages
        }
    },
    actions: {
        // Dashboard
        updateDashboard (context) {
            return new Promise((resolve, reject) => {
                Vue.axios.get('projects').then(res => {
                    context.commit('setProjects', res.data)
                    resolve(res)
                }, err => {
                    reject(err)
                })
                // Supplemental
                Vue.axios.get('users').then(res => {
                    context.commit('setUsers', res.data)
                })
                Vue.axios.get('groups').then(res => {
                    context.commit('setGroups', res.data)
                })
            })
        },
        // User
        getUser (context, payload = {}) {
            return new Promise((resolve, reject) => {
                Vue.axios.get('users/' + payload.id + '?_embed=projects').then(res => {
                    context.commit('setUser', res.data)

                    // Subsequent
                    Vue.axios.get('groups').then(res => { // TODO: filter
                        context.commit('setUserGroups', res.data)
                    })
                    Vue.axios.get('projects').then(res => { // TODO: filter
                        context.commit('setUserProjects', res.data)
                    })
                    Vue.axios.get('tasks').then(res => { // TODO: filter
                        context.commit('setUserTasks', res.data)
                    })

                    resolve(res)
                }, err => {
                    reject(err)
                })
            })
        },
        // Project
        getProject (context, payload = {}) {
            return new Promise((resolve, reject) => {
                Vue.axios.get('projects/' + payload.id).then(res => {
                    context.commit('setProject', res.data)
                    resolve(res)
                }, err => {
                    reject(err)
                })
                // Supplemental
                Vue.axios.get('projects/' + payload.id + '/tasks').then((res) => {
                    context.commit('setTasksForProject', res.data)
                })
            })
        },
        setProjectStatus (context, payload = {}) {
            return new Promise((resolve, reject) => {
                Vue.axios.patch('projects/' + payload.id, {
                    status: payload.status
                }).then(res => {
                    context.commit('setProject', res.data)
                    resolve(res)
                }, err => {
                    reject(err)
                })
            })
        },
        // Task
        getTask(context, payload = {}) {
            return new Promise((resolve, reject) => {
                Vue.axios.get('tasks/' + payload.id + '?_expand=project').then(res => {
                    context.commit('setTask', res.data)
                    resolve(res)
                }, err => {
                    reject(err)
                })
            })
        },
        // Messaging
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
