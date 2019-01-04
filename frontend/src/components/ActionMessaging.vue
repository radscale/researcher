<template>
    <div class="action-messaging">
        <div
            class="action-messaging__list--wrapper"
            :class="{
                'action-messaging__list--wrapper--scrolled': scrollOffset > 4
            }"
        >
            <div class="action-messaging__list" ref="scroller">
                <ul class="list">
                    <action-message
                        v-for="message in messages"
                        :key="message.id"
                        type="message"
                        :item="message"
                    >
                    </action-message>
                </ul>
            </div>
        </div>
        <div
            class="action-messaging__panel"
            ref="panel"
        >
            <action-input
                v-model.trim="userMessage"
                name="userMessage"
                placeholder="Type your message here..."
                autofocus
                @enter="sendMessage"
                :disabled="sending"
            >
                <label slot="label" for="userQuery">
                    Send message
                </label>
            </action-input>
            <action-button
                class="action-messaging__panel__button"
                @click="sendMessage"
                :disabled="$v.userMessage.$error || sending"
                cta
            >
                <template v-if="!sending">
                    Send
                </template>
                <i v-else class="fas fa-cog fa-spin"></i>
            </action-button>
        </div>
    </div>
</template>

<script>
import _ from 'lodash'

import ActionMessage from '@/components/ActionMessage.vue'
import ActionButton from '@/components/ActionButton.vue'
import ActionInput from '@/components/ActionInput.vue'

import {required} from 'vuelidate/lib/validators'

export default {
    name: 'action-messaging',
    components: {
        ActionMessage,
        ActionButton,
        ActionInput
    },
    props: {
        type: {
            type: String,
            required: true
        },
        item: {
            type: Object,
            default: () => {
                return {}
            }
        }
    },
    computed: {
        messages () {
            if (this.type == 'user') {
                return this.$store.state.currentUser.messages
            } else if (this.type == 'project') {
                // TODO: Project comments
                return this.$store.state.currentProject.comments
            } else if (this.type == 'task') {
                // TODO: Task comments
                return this.$store.state.currentTask.comments
            } else if (this.type == 'adhoc') {
                // TODO: Adhoc comments
                return this.$store.state.currentAdhoc.comments
            } else if (this.type == 'public') {
                // TODO: Public comments
                return this.$store.state.comments
            } 
        }
    },
    mounted () {
        this.messagingCheck()
        this.$refs.scroller.addEventListener('scroll', this._scrollHandler)
        this.$v.userMessage.$touch()
    },
    beforeDestroy () {
        this.unmountMessaging()
        this.$refs.scroller.removeEventListener('scroll', this._scrollHandler)
    },
    methods: {
        _scrollHandler (evt) {
            this.scrollOffset = evt.target.scrollTop
        },
        mountMessaging () {
            this.unmountMessaging()
            this._messagingTimeout = setTimeout(this.messagingCheck, this.$bus.messagingPollTimeout)
        },
        messagingCheck () {
            this.$store.dispatch('getMessagesForUser', {
                id: this.item.id
            }).then(res => {
                if (
                    !this.loaded
                ) {
                    this.$refs.scroller.scroll(0, this.$refs.scroller.scrollHeight)
                    this.loaded = true
                }
            }, err => {
                bus.pushMessage({
                    type: 'error',
                    content: 'Could not fetch messages for <strong>' + this.item.firstName + ' ' + this.item.lastName + '</strong>.'
                })
                this.unmountMessaging()
            })

            this.mountMessaging()
        },
        unmountMessaging () {
            if (this._messagingTimeout !== null) {
                clearTimeout(this._messagingTimeout)
                this._messagingTimeout = null
            }
        },
        sendMessage () {
            if (!this.sending && !this.$v.userMessage.$error) {
                console.log('Sending message: %o', this.userMessage)
                this.sending = true

                if (this.type == 'user') {
                    this.$store.dispatch('sendMessage', {
                        senderId: this.$auth.user().id,
                        receiverId: this.item.id,
                        content: this.userMessage
                    }).then(res => {
                        this.userMessage = ''

                        this.finishMessaging()
                    }, err => {
                        this.finishMessaging()
                    })
                } else if (this.type == 'project') {
                    // TODO: Send project comments
                } else if (this.type == 'task') {
                    // TODO: Send task comments
                } else if (this.type == 'adhoc') {
                    // TODO: Send adhoc comments
                } else if (this.type == 'public') {
                    // TODO: Send public comments
                } 
            }
        },
        finishMessaging () {
            this.sending = false
            this.mountMessaging()

            let elem = this.$refs.panel.querySelector('[autofocus]')

            if (!elem) {
                elem = this.$refs.panel.querySelector('input,textarea')
            }

            if (elem) {
                this.$nextTick(() => {
                    elem.focus()
                })                
            }
        }
    },
    validations: {
        userMessage: {
            required
        }
    },
    watch: {
        userMessage () {
            this.$v.userMessage.$touch()
        },
        messages (newMessages, oldMessages) {
            if (newMessages.length) {
                if (!oldMessages.length || newMessages[newMessages.length - 1].id != oldMessages[oldMessages.length - 1].id) {
                    console.log('Messages updated: %o -> %o', oldMessages, newMessages)
                    this.$nextTick(() => {
                        this.$SmoothScroll(this.$refs.scroller.scrollHeight, 200, () => {}, this.$refs.scroller)
                    })
                }
            }
        }
    },
    data () {
        return {
            sending: false,
            loaded: false,
            scrollOffset: 0,
            userMessage: ''
        }
    }
}
</script>

<style lang="scss" scoped>
    @import '~@/styles/globals';

    .action-messaging {
        height: 360px;
        display: flex;
        flex-direction: column;
        margin-top: 8px;

        &__list--wrapper {
            position: relative;
            height: 50%;
            flex-grow: 1;

            &:before {
                content: '';
                position: absolute;
                top: 0;
                left: 0;
                right: 0;
                height: 64px;
                z-index: 5;
                background-image: linear-gradient(to bottom, $background-body, rgba($background-body, 0));

                transition: 100ms opacity ease-in;

                opacity: 0;
            }

            &--scrolled {
                &:before {
                    opacity: 1;
                }
            }
        }

        &__list {
            width: 100%;
            height: 100%;
            overflow-x: hidden;
            overflow-y: scroll;
        }

        &__panel {
            height: 60px;
            display: flex;
            margin: 8px -8px;

            &__button {
                margin-top: 32px;
                height: 34px;
                flex-shrink: 0;
                width: 60px;
            }
        }
    }
</style>
