import Vue from 'vue'
import Vuex from 'vuex'
import _ from 'lodash'

Vue.use(Vuex)

export default new Vuex.Store({
    state: {
        projects: [],
        users: [],
        messages: [],
        user: false,
        project: false
    },
    mutations: {
        setProjects (state, projects) {
            state.projects = projects
        },
        setUser (state, user) {
            state.user = user
        },
        setProject (state, project) {
            state.project = project
        },
        setMessages (state, messages) {
            state.messages = messages
        }
    },
    actions: {
        updateProjects (context) {
            Vue.axios.get('projects').then((res) => {
                context.commit('setProjects', res.data)
            })
        },
        getUser (context, payload = {}) {
            Vue.axios.get('users/' + payload.id).then((res) => {
                context.commit('setUser', res.data)
            })
        },
        getProject (context, payload = {}) {
            Vue.axios.get('projects/' + payload.id).then((res) => {
                context.commit('setProject', res.data)
            })
        },
        updateMessages (context) {
            Vue.axios.get('messages').then((res) => {
                context.commit('setMessages', res.data)
            })
        }
    }
})
