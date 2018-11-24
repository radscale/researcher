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
import axios from 'axios'
import VueAxios from 'vue-axios'
Vue.use(VueAxios, axios)
Vue.axios.defaults.baseURL = 'http://' + document.location.hostname + ':3000'; // debugging

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

// Config
Vue.config.productionTip = false

new Vue({
    router,
    store,
    render: h => h(App)
}).$mount('#app')
