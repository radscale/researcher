import Vue from 'vue'
import App from './App.vue'

// Media queries
import VueMq from 'vue-mq'
Vue.use(VueMq, {
    breakpoints: {
        sm: 470,
        md: 1250,
        lg: Infinity
    }
});

// Router
import router from './router'
Vue.router = router

// Vuex
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

// Validation
import Vuelidate from 'vuelidate'
Vue.use(Vuelidate)

// Event bus
import bus from './bus.js'
Object.defineProperties(Vue.prototype, {
    $bus: {
        get: function() {
            return bus;
        }
    }
});

// Config
Vue.config.productionTip = false

new Vue({
    router,
    store,
    render: h => h(App)
}).$mount('#app')
