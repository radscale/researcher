import Vue from 'vue'

const MESSAGE_TIMEOUT = 5000

export default new Vue({
    methods: {
        openModal (name) {
            if (name in this.modals) {
                this.modals[name].open = true;
            } else {
                console.error('Invalid modal invocation: %o', name)
            }
        },
        closeModals () {
            for (name in this.modals) {
                this.modals[name].open = false;
            }
        },
        pushMessage (message) {
            let messageObject = message
            let messageId = this.$data._messageCount++

            if (message.timeout !== false) {
                let delay = typeof message.timeout == 'number'
                    ? message.timeout
                    : MESSAGE_TIMEOUT
                console.log('Delay: %o', delay)

                message._timeout = setTimeout(() => {
                    this.removeMessage(messageId)
                }, delay)
            }

            this.$set(this.messages, messageId, messageObject)
        },
        removeMessage (messageId) {
            console.log('Removing message: %o', this.messages[messageId])
            clearTimeout(this.messages[messageId]._timeout)
            this.$delete(this.messages, messageId)
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
            messages: {},
            _messageCount: 0,
            loading: true,
            loadingLabel: 'Loading...'
        }
    }
});
