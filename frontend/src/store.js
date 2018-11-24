import Vue from 'vue'
import Vuex from 'vuex'
import _ from 'lodash'

Vue.use(Vuex)

export default new Vuex.Store({
    state: {
        projects: []
    },
    mutations: {
        setProjects (state, projects) {
            state.projects = projects
        }
    },
    actions: {
        updateProjects (context) {
            Vue.axios.get('projects').then((res) => {
                context.commit('setProjects', res.data)
            })
        }
    }
})
