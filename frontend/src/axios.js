import Vue from 'vue'
import axios from 'axios'

import VueProgressBar from 'vue-progressbar'

Vue.use(VueProgressBar, {
    color: '#bffaf3',
    //color: '#c5deff',
    failedColor: '#874b4b',
    thickness: '5px',
    transition: {
        speed: '0.2s',
        opacity: '0.6s',
        termination: 300
    },
    autoRevert: true,
    location: 'top',
    inverse: false
})

const instance = axios.create({
    baseURL: 'http://' + document.location.hostname + ':3000'
})

export default instance
