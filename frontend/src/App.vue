<template>
    <div id="app">
        <!-- Progress bar -->
        <vue-progress-bar></vue-progress-bar>

        <!-- Main structure -->
        <navbar
            v-show="$auth.ready()"
            :scrolled="$bus.scrollHeight > 8"
        >
        </navbar>
        <transition name="slide-fast" mode="out-in" appear>
            <router-view v-if="$auth.ready()" :key="$route.fullPath" />
        </transition>
        <footer>
            Made by Adrian Hernik and Marcin Krykowski
        </footer>

        <!-- Pop-up messages -->
        <div class="popup-list">
            <transition-group name="pop-up" appear>
                <popup
                    v-for="(message, key) in messages"
                    :key="key"
                    :type="message.type"
                    :forced="message.forced"
                    @close="$bus.removeMessage(key)"
                >
                    {{ message.content }}
                </popup>
            </transition-group>
        </div>

        <!-- Login modal -->
        <modal
            name="login"
            :submit="submitLogin"
            v-if="!$auth.check()"
        >
            <template slot="header">
                <h2>Log in</h2>
            </template>
            <template slot="content">
                <action-input
                    type="text"
                    name="login-email"
                    v-model.trim="login.email"
                    placeholder="Type your e-mail here..."
                    :validations="$v.login.email"
                >
                    <label slot="label" for="login">
                        E-mail
                    </label>
                </action-input>
                <action-input
                    type="password"
                    name="login-password"
                    v-model.trim="login.password"
                    placeholder="Type your password here..."
                    :validations="$v.login.password"
                >
                    <label slot="label" for="password">
                        Password
                    </label>
                </action-input>
            </template>
            <template slot="actions">
                <action-button
                    type="button"
                    cta
                    :disabled="$v.login.$invalid"
                >
                    <span>Log in</span>
                </action-button>
            </template>
        </modal>
        <!-- Signup modal -->
        <modal
            name="signup"
            :submit="submitSignup"
            v-if="!$auth.check()"
        >
            <template slot="header">
                <h2>Sign up</h2>
            </template>
            <template slot="content">
                <action-input
                    type="text"
                    name="signup-login"
                    v-model.trim="signup.email"
                    placeholder="Type your e-mail here..."
                    :validations="$v.signup.email"
                >
                    <label slot="label" for="login">
                        E-mail
                    </label>
                </action-input>
                <action-input
                    type="text"
                    name="signup-firstName"
                    v-model.trim="signup.firstName"
                    placeholder="Type your first name here..."
                    :validations="$v.signup.firstName"
                >
                    <label slot="label" for="login">
                        First name
                    </label>
                </action-input>
                <action-input
                    type="text"
                    name="signup-lastName"
                    v-model.trim="signup.lastName"
                    placeholder="Type your last name here..."
                    :validations="$v.signup.lastName"
                >
                    <label slot="label" for="login">
                        Last name
                    </label>
                </action-input>
                <action-input
                    type="password"
                    name="signup-password1"
                    v-model.trim="signup.password1"
                    placeholder="Type your password here..."
                    :validations="$v.signup.password1"
                >
                    <label slot="label" for="password">
                        Password
                    </label>
                </action-input>
                <action-input
                    type="password"
                    name="signup-password2"
                    v-model.trim="signup.password2"
                    placeholder="Repeat your password here..."
                    :validations="$v.signup.password2"
                >
                    <label slot="label" for="password">
                        Verify password
                    </label>
                </action-input>
            </template>
            <template slot="actions">
                <action-button
                    type="button"
                    cta
                    :disabled="$v.signup.$invalid"
                >
                    <span>Sign up</span>
                </action-button>
            </template>
        </modal>
        <!-- Logout modal -->
        <modal
            name="logout"
            :submit="submitLogout"
            v-if="$auth.check()"
        >
            <template slot="header">
                <h2>Confirm logout</h2>
            </template>
            <template slot="content">
                <p>Are you sure you want to log out?</p>
            </template>
            <template slot="actions">
                <action-button
                    type="button"
                    cta
                >
                    <span>Log out</span>
                </action-button>
            </template>
        </modal>

        <!-- Loader -->
        <transition name="fade-fast" mode="out-in">
            <div class="loader" v-show="$bus.loading">
                <div class="loader__container">
                    <span>{{ $bus.loadingLabel }}</span>
                    <!-- <i class="fas fa-cog fa-spin"></i> -->
                </div>
            </div>
        </transition>
    </div>
</template>

<script>
import axios from '@/axios'

import Navbar from '@/components/Navbar.vue'
import Modal from '@/components/Modal.vue'
import ActionButton from '@/components/ActionButton.vue'
import ActionInput from '@/components/ActionInput.vue'
import Popup from '@/components/Popup.vue'

import {required, email, minLength} from 'vuelidate/lib/validators'

export default {
    name: 'app',
    components: {
       Navbar,
       Modal,
       ActionButton,
       ActionInput,
       Popup
    },
    created () {
        // Check for premature $auth ready state
        if (this.$auth.ready())
            this.$bus.loading = false

        // Set up axios interceptors
        axios.interceptors.request.use(config => {
            this.$Progress.start()
            return config
        })
        axios.interceptors.response.use(response => {
            this.$Progress.finish()
            return response
        }, error => {
            this.$bus.pushMessage({
                type: 'error',
                content: 'Network error. Is the server up?'
            })
            return Promise.reject(error)
        })

        window.addEventListener('scroll', this._scrollHandler);
    },
    beforeDestroy () {
        window.removeEventListener('scroll', this._scrollHandler)
    },
    methods: {
        _scrollHandler () {
            this.$bus.scrollHeight = window.scrollY
        },
        submitLogin (event) {
            event.preventDefault();
            if (!this.$v.login.$invalid) {
                this.$auth.login({
                    email: this.login.email,
                    password: this.login.password
                }).then(res => {
                    console.log('Login success: %o', res)
                }, error => {
                    console.warn('Login error: %o', error)
                    if (error.status) {
                        this.$bus.pushMessage({
                            type: 'error',
                            content: 'Invalid login. Please verify your data.'
                        })
                    }
                })
            } else {
                this.$v.login.email.$touch()
                this.$v.login.password.$touch()
            }
        },
        submitSignup (event) {
            event.preventDefault();
            if (!this.$v.signup.$invalid) {
                this.$auth.register({
                    firstName: this.signup.firstName,
                    lastName: this.signup.lastName,
                    email: this.signup.email,
                    password: this.signup.password
                }).then(res => {
                    console.log('Register success: %o', res)
                }, error => {
                    console.warn('Register error: %o', error)
                })
            } else {
                this.$v.signup.email.$touch()
                this.$v.signup.firstName.$touch()
                this.$v.signup.lastName.$touch()
                this.$v.signup.password1.$touch()
                this.$v.signup.password2.$touch()
            }
        },
        submitLogout (event) {
            event.preventDefault();
            this.$auth.logout();
        }
    },
    watch: {
        user () {
            this.$bus.closeModals()
        },
        authReady () {
            this.$bus.loading = false
        }
    },
    computed: {
        user () {
            return this.$auth.user()
        },
        authReady () {
            return this.$auth.ready()
        },
        authCheck () {
            return this.$auth.check()
        },
        messages () {
            return this.$bus.messages
        }
    },
    validations: {
        login: {
            email: {
                required,
                email
            },
            password: {
                required
            }
        },
        signup: {
            email: {
                required,
                email
            },
            firstName: {
                required
            },
            lastName: {
                required
            },
            password1: {
                required,
                minLengthPassword: minLength(6)
            },
            password2: {
                required,
                passwordMatch (value) {
                    return this.signup.password1 == value
                }
            }
        }
    },
    data () {
        return {
            login: {
                email: '',
                password: ''
            },
            signup: {
                email: '',
                firstName: '',
                lastName: '',
                password1: '',
                password2: ''
            }
        }
    }
}
</script>

<style lang="scss">
@import '~@/styles/globals';

* {
    box-sizing: border-box;
}

body {
    font-family: 'Work Sans', 'Avenir', Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;

    background: $background-body;
    color: $foreground-body;

    margin: 0;
    padding: 0;

    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    min-height: 100%;


    padding-bottom: 64px;
}

section {
    margin: 64px auto;
    padding: 8px 24px;
}

.section {
    display: flex;
    flex-flow: row wrap;
    justify-content: space-between;

    &__header {
        width: 100%;
        flex-basis: 100%;

        display: flex;
        flex-direction: row;
        align-items: center;

        &__icon {
            font-size: 20px;
            margin: 0 8px 0 8px;
            color: $foreground-entity-icon;
        }

        h1 {
            font-weight: 400;
        }
    }

    &__main {
        flex-basis: 0;
        flex-grow: 1;
    }

    &__side {
        margin-left: 16px;
        flex-basis: 360px;
        min-width: 360px;
    }
}

footer {
    position: absolute;
    bottom: 0;
    left: 0;
    right: 0;
    height: 64px;
    line-height: 64px;
    text-align: center;
    padding: 0 20px;
    color: $foreground-footer;
}

.loader {
    position: fixed;
    top: 0;
    bottom: 0;
    left: 0;
    right: 0;
    display: flex;
    cursor: default;
    justify-content: center;
    align-items: center;
    background-color: $background-loader;
    z-index: 20;

    &__container {
        color: $foreground-loader;
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;

        > * {
            margin: 8px;
        }

        svg {
            font-size: 24px;
        }
    }
}

a,
a:active,
a:visited {
    color: $foreground-link;
    text-decoration: none;

    transition: 150ms color ease-in;
}

a:hover {
    color: $foreground-link--hover;
}

h1 {
    color: $foreground-header;
    font: $font-header;
    margin: 8px 0;
}

h2 {
    color: $foreground-header--sub;
    font: $font-header--sub;
    margin: 8px 0;
}

.highlight {
    background-color: $background-highlight;
    margin: 24px 0;
    padding: 24px 32px;
}

// Rows and cols
.row {
    position: relative;
    margin: 8px 0;

    > * {
        display: inline-block;
    }
}

.col-40 {
    width: 40%;
}
.col-50 {
    width: 50%;
}
.col-60 {
    width: 60%;
}


// Pop-ups
.popup-list {
    position: fixed;
    bottom: 0;
    left: 0;
    right: 0;
    z-index: 80;
}

// Lists
ul {
    padding: 0;
}

// Animations
/* Horizontal slide */
.slide-enter-active {
    transition: 400ms opacity ease-in-out, 400ms transform ease-out;
}
.slide-leave-active {
    transition: 400ms opacity ease-in-out, 400ms transform ease-in;
}
.slide-enter {
    opacity: 0;
    transform: translate3d(40px, 0, 0);
}
.slide-leave-to {
    opacity: 0;
    transform: translate3d(-40px, 0, 0);
}

/* Horizontal slide - fast */
.slide-fast-enter-active {
    transition: 200ms opacity ease-in-out, 200ms transform ease-out;
}
.slide-fast-leave-active {
    transition: 200ms opacity ease-in-out, 200ms transform ease-in;
}
.slide-fast-enter {
    opacity: 0;
    transform: translate3d(0, -10px, 0);
}
.slide-fast-leave-to {
    opacity: 0;
    transform: translate3d(0, 10px, 0);
}

/* Vertical slide */
.pop-up-enter-active {
    transition: 200ms opacity ease-in-out, 200ms transform ease-out;
}
.pop-up-leave-active {
    transition: 200ms opacity ease-in-out, 200ms transform ease-in;
}
.pop-up-enter {
    opacity: 0;
    transform: translate3d(0, 40px, 0);
}
.pop-up-leave-to {
    opacity: 0;
    transform: translate3d(0, 40px, 0);
}

.drop-down-enter-active {
    transition: 200ms opacity ease-in-out, 200ms transform ease-out;
}
.drop-down-leave-active {
    transition: 200ms opacity ease-in-out, 200ms transform ease-in;
}
.drop-down-enter {
    opacity: 0;
    transform: translate3d(0, -8px, 0);
}
.drop-down-leave-to {
    opacity: 0;
    transform: translate3d(0, -8px, 0);
}

/* Standard fade */
.fade-enter-active,
.fade-leave-active {
    transition: 400ms opacity ease-in-out;
}
.fade-enter,
.fade-leave-to {
    opacity: 0;
}

/* Fast fade */
.fade-fast-enter-active,
.fade-fast-leave-active {
    transition: 200ms opacity ease-in-out;
}
.fade-fast-enter,
.fade-fast-leave-to {
    opacity: 0;
}
</style>
