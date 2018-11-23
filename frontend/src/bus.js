import Vue from 'vue'

export default new Vue({
    methods: {
        openModal (name) {
            if (name in this.modals) {
                this.modals[name].open = true;
            }
        }
    },
    data () {
        return {
            version: '0.1 Long Haul',
            modals: {
                login: {
                    open: false
                },
                signup: {
                    open: false
                }
            }
        }
    }
});
