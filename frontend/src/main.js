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

// Requests
import VueAxios from 'vue-axios'
import axios from './axios'
Vue.use(VueAxios, axios)

// vue-auth
Vue.use(require('@websanova/vue-auth'), {
    auth: {
        request: function (req, token) {
            this.options.http._setHeaders.call(this, req, {Authorization: 'Bearer ' + token});
        },
        response: function (res) {
            var headers = this.options.http._getHeaders.call(this, res),
                token = headers.Authorization || headers.authorization;
    
            if (token) {
                token = token.split(/Bearer\:?\s?/i);
                
                return token[token.length > 1 ? 1 : 0].trim();
            }
            // return 'testToken'
        }
    },
    http: require('@websanova/vue-auth/drivers/http/axios.1.x'),
    router: require('@websanova/vue-auth/drivers/router/vue-router.2.x'),
    authRedirect: {
        path: '/'
    }
    // parseUserData: function (data) {
    //     debugger
    //     return {}
    // }
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

// Filters
Vue.filter('capitalize', function (value) {
    if (!value) return ''
    value = value.toString()
    return value.charAt(0).toUpperCase() + value.slice(1)
})

// Config
Vue.config.productionTip = false

new Vue({
    router,
    store,
    render: h => h(App)
}).$mount('#app')
