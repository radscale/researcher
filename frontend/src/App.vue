<template>
    <div id="app">
        <navbar>
        </navbar>
        <router-view v-if="$auth.ready()"/>
        <footer>
            Made by Adrian Hernik and Marcin Krykowski
        </footer>
        <modal
            name="login"
            :submit="submitLogin"
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
        <modal
            name="signup"
            :submit="submitSignup"
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
    </div>
</template>

<script>
import Navbar from '@/components/Navbar.vue'
import Modal from '@/components/Modal.vue'
import ActionButton from '@/components/ActionButton.vue'
import ActionInput from '@/components/ActionInput.vue'

import {required, email, minLength} from 'vuelidate/lib/validators'

export default {
    name: 'app',
    components: {
       Navbar,
       Modal,
       ActionButton,
       ActionInput
    },
    created () {

    },
    methods: {
        submitLogin (event) {
            event.preventDefault();
            if (!this.$v.login.$invalid) {
                this.$auth.login({
                    email: this.login.email,
                    password: this.login.password
                })
            } else {
                this.$v.login.email.$touch()
                this.$v.login.password.$touch()
            }
        },
        submitSignup (event) {
            event.preventDefault();
            if (!this.$v.signup.$invalid) {
                this.$auth.register(this.signup.email, this.signup.password)
            } else {
                this.$v.signup.email.$touch()
                this.$v.signup.password1.$touch()
                this.$v.signup.password2.$touch()
            }
        }
    },
    watch: {
        user () {
            this.$bus.closeModals()
        }
    },
    computed: {
        user () {
            return this.$auth.user()
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

    padding-bottom: 64px;
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
}

h2 {
    color: $foreground-header--sub;
    font: $font-header--sub;
}

.highlight {
    background-color: $background-highlight;
    margin: 24px 0;
    padding: 24px 32px;
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

/* Standard fade */
.fade-enter-active,
.fade-leave-active {
    transition: 400ms opacity ease-in-out;
}
.fade-enter,
.fade-leave-to {
    opacity: 0;
}
</style>
