import Vue from 'vue'
import Vuex from 'vuex'
import _ from 'lodash'

Vue.use(Vuex)

export default new Vuex.Store({
    state: {
        projects: [],
        users: [],
        messages: [],
        user: false
    },
    mutations: {
        setProjects (state, projects) {
            state.projects = projects
        },
        setUser (state, user) {
            state.user = user
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
            Vue.axios.get('users?id=' + payload.id).then((res) => {
                context.commit('setUser', res.data[0])
            })
        },
        updateMessages (context) {
            Vue.axios.get('messages').then((res) => {
                context.commit('setMessages', res.data)
            })
        }
    }
})
