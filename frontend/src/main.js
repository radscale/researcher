import Vue from 'vue'
import App from './App.vue'

// router
import router from './router'
Vue.router = router

// vuex
import store from './store'

// vue-resource
import VueResource from 'vue-resource'
Vue.use(VueResource)
Vue.http.options.root = 'https://api-demo.websanova.com/api/v1'; // debugging

// vue-auth
Vue.use(require('@websanova/vue-auth'), {
    auth: require('@websanova/vue-auth/drivers/auth/bearer'),
    http: require('@websanova/vue-auth/drivers/http/vue-resource.1.x'),
    router: require('@websanova/vue-auth/drivers/router/vue-router.2.x')
})

// config
Vue.config.productionTip = false

new Vue({
    router,
    store,
    render: h => h(App)
}).$mount('#app')
