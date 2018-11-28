<template>
    <dashboard v-if="loggedIn"></dashboard>
    <landing v-else></landing>
</template>

<script>
import store from '@/store'
import bus from '@/bus'

import Landing from '@/views/Landing.vue'
import Dashboard from '@/views/Dashboard.vue'

const _fetchData = function (params, callback) {
    if (bus.$auth.check()) {
        store.dispatch('updateDashboard').then(callback, error => {
            bus.pushMessage({
                type: 'error',
                content: 'Could not load dashboard data.'
            })
        })
    } else {
        if (typeof callback == 'function') {
            callback()
        }
    }
}

export default {
    name: 'home',
    components: {
        Landing,
        Dashboard
    },
    computed: {
        loggedIn () {
            return this.$auth.check()
        }
    },
    beforeRouteEnter: function (to, from, next) {
        _fetchData(to.params, next)
    },
    beforeRouteUpdate: function (to, from, next) {
        _fetchData(to.params, next)
    }
}
</script>

