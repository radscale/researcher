import Vue from 'vue'

export default new Vue({
    methods: {
        openModal (name) {
            if (name in this.modals) {
                this.modals[name].open = true;
            } else {
                console.error('Invalid modal invocation: %o', name)
            }
        },
        closeModals() {
            for (name in this.modals) {
                this.modals[name].open = false;
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
                },
                logout: {
                    open: false
                }
            },
            loading: true,
            loadingLabel: 'Loading...'
        }
    }
});
