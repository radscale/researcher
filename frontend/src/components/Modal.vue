<template>
    <div
        class="modal"
        :class="{
            'modal--open': openState
        }"
    >
        <transition name="fade">
            <div
                class="modal__overlay"
                @click="clickOverlay"
                v-if="openState"
            ></div>
        </transition>
        <transition name="modal-transition">
            <div
                class="modal__wrapper"
                :class="{
                    'modal__wrapper--full-size': fullSize
                }"
                v-if="openState"
            >
                <form
                    ref="form"
                    @submit="submit"
                >
                    <div class= "modal__header">
                        <action-button
                            icon
                            class="modal__close"
                            @click="clickClose"
                            @if="!forced"
                            faded
                        >
                            <i class="fa fa-times"></i>
                        </action-button>
                        <slot name="header"></slot>
                    </div>
                    <div class="modal__content">
                        <slot name="content" default></slot>
                    </div>
                    <div class="modal__actions">
                        <slot name="actions"></slot>
                        <action-button
                            @click="clickClose"
                            v-if="!forced && cancel"
                        >
                            <span>{{ cancel }}</span>
                        </action-button>
                    </div>
                </form>
            </div>
        </transition>
    </div>
</template>

<script>
import Vue from 'vue'

import ActionButton from '@/components/ActionButton.vue'

export default {
    name: 'modal',
    components: {
        ActionButton
    },
    props: {
        name: {
            type: String,
            required: true
        },
        forced: {
            type: Boolean,
            default: false
        },
        submit: {
            type: Function,
            default: ($event) => {
                $event.preventDefault()
                return false
            }
        },
        cancel: {
            type: String,
            default: 'Cancel'
        },
        fullSize: {
            type: Boolean,
            default: false
        }
    },
    computed: {
        openState: {
            get: function () {
                return this.$bus.modals[this.name].open
            },
            set: function (value) {
                this.$bus.modals[this.name].open = value
            }
        }
    },
    watch: {
        openState (state) {
            
            if (state) {
                Vue.nextTick(() => {
                    let elem = this.$refs.form.querySelector('[autofocus]')

                    if (!elem) {
                        elem = this.$refs.form.querySelector('input,textarea')
                    }

                    if (elem) {
                        elem.focus()
                    }
                })
            }
        }
    },
    methods: {
        clickOverlay () {
            if (!this.forced)
                this.close()
        },
        clickClose () {
            this.close()
        },

        open () {
            this.openState = true
        },
        close () {
            this.openState = false
        }
    }
}
</script>

<style lang="scss">
    @import '~@/styles/globals';

    .modal {
        z-index: 40;

        form {
            height: 100%;
        }

        .modal__overlay {
            z-index: 40;
            position: fixed;
            left: 0;
            top: 0;
            bottom: 0;
            right: 0;
            background-color: $background-overlay;
        }

        .modal__wrapper {
            z-index: 45;
            position: fixed;
            float: left;
            left: 50%;
            top: 50%;
            transform: translate(-50%, -50%);
            background-color: $background-modal;
            border: $border-modal;
            border-radius: 8px;
            display: flex;
            flex-direction: column;
            max-width: 80%;
            width: $width-modal;

            &--full-size {
                height: calc(100vh - #{$offset-modal--full-size});
            }
        }

        .modal__header {
            position: absolute;
            top: 0;
            right: 0;
            left: 0;
            padding: 14px 18px 8px;
            display: flex;
            flex-direction: row-reverse;
            align-items: center;

            h2 {
                margin: 14px 0;
            }
        }

        .modal__close {
            float: right;
            height: 32px;
            margin-right: 0;
            margin-left: auto;
        }

        .modal__content {
            padding: 14px 18px;
            overflow-y: scroll;
            max-height: calc(100vh - #{$height-modal__header} - #{$height-modal__actions} - #{$offset-modal--full-size});
            margin: $height-modal__header 0 $height-modal__actions;

            h1 {
                color: $foreground-modal;
                margin: 0;
                margin-bottom: 1em;
                font-size: 24px;
                line-height: 28px;
            }

            p {
                line-height: 20px;
                font-size: 14px;
            }

            strong {
                color: $foreground-modal;
            }
        }

        .modal__actions {
            position: absolute;
            bottom: 0;
            right: 0;
            left: 0;
            padding: 12px 18px 18px;
            display: flex;
            flex-direction: row-reverse;

            >* {
                margin-left: 16px;
            }
        }
    }

    /* Modal transition */
    .modal-transition-enter-active {
        transition: 200ms opacity ease-in-out, 200ms transform ease-out;
    }
    .modal-transition-leave-active {
        transition: 200ms opacity ease-in-out, 200ms transform ease-in;
    }
    .modal-transition-enter {
        opacity: 0;
    }
    .modal-transition-leave-to {
        opacity: 0;
    }
</style>
