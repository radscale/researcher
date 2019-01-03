<template>
<div
    class="navbar"
    :class="{
        'navbar--free': free,
        'navbar--scrolled': scrolled
    }"
>
    <div class="navbar__left">
        <div
            v-if="$auth.check()"
            class="logo"
            :class="{
                'logo--hidden': hiddenLogo
            }"
            @click="$router.push({path: '/'})"
        ></div>
        <action-button
            v-if="$auth.check() && $route.name != 'home'"
            @click="$router.push({path: '/'})"
        >
            Dashboard
        </action-button>
    </div>
    <div class="navbar__right">
        <template v-if="!$auth.check()">
            <action-button
                class="navbar-button"
                @click="$bus.openModal('login')"
            >
                Log in
            </action-button>
            <action-button
                class="navbar-button"
                cta
                @click="$bus.openModal('signup')"
            >
                Sign up
            </action-button>
        </template>
        <template v-if="user.email">
            <action-item
                no-background
                :highlight="!isOnOwnProfile"
                type="user"
                :item="user"
                :to="{
                    name: 'profile',
                    params: {
                        id: user.id
                    }
                }"
            ></action-item>
            <action-button
                class="navbar-button"
                icon
                faded
                @click="$bus.openModal('logout')"
            >
                <i class="fas fa-power-off"></i>
            </action-button>
        </template>
    </div>
</div>
</template>

<script>
import ActionButton from '@/components/ActionButton.vue'
import ActionItem from '@/components/ActionItem.vue'

export default {
    name: 'navbar',
    components: {
        ActionButton,
        ActionItem
    },
    props: {
        free: {
            type: Boolean,
            default: false
        },
        hiddenLogo: {
            type: Boolean,
            default: false
        },
        scrolled: {
            type: Boolean,
            default: false
        }
    },
    computed: {
        user () {
            return this.$auth.user()
        },
        isOnOwnProfile () {
            return this.$route.name == 'profile'
                && this.user.id == this.$route.params.id
        }
    },
    watch: {
        user (user) {
            if (user.id) {
                // User is logged in, let's begin message polling
                this.globalMessagingCheck()
            }
        }
    },
    beforeDestroy () {
        unmountGlobalMessaging()
    },
    methods: {
        mountGlobalMessaging () {
            this.unmountGlobalMessaging()
            this._globalMessagingTimeout = setTimeout(this.globalMessagingCheck, this.$bus.globalMessagingPollTimeout)
        },
        globalMessagingCheck () {
            this.$store.dispatch('updateMessages').then(() => {}, err => {
                this.$bus.pushMessage({
                    type: 'error',
                    content: 'Could not fetch any messages.'
                })
                this.unmountGlobalMessaging()
            })

            this.mountGlobalMessaging()
        },
        unmountGlobalMessaging () {
            if (this._globalMessagingTimeout !== null) {
                clearTimeout(this._globalMessagingTimeout)
                this._globalMessagingTimeout = null
            }
        }
    },
    data () {
        return {
            _globalMessagingTimeout: null
        }
    }
}
</script>

<style scoped lang="scss">
    @import '~@/styles/globals';

    .navbar {
        padding: 4px 20px;
        position: fixed;
        top: 0;
        left: 0;
        right: 0;
        height: 64px;
        display: flex;
        margin-bottom: 64px;
        align-items: center;
        line-height: 32px;
        z-index: 20;
        box-shadow: $shadow-navbar;
        background-color: $background-navbar;

        transition: box-shadow 200ms ease-out;

        &--free {
            position: absolute;
        }

        &--scrolled {
            box-shadow: $shadow-navbar--scrolled;
        }

        &__left {
            margin-right: auto;
            display: flex;
            align-items: center;
        }
    }

    .navbar-button {
        margin-left: 16px;
    }

    .logo {
        background-image: url('~@/assets/logo-transparent-nb-420px.png');
        background-size: 34px;
        background-position: center;
        background-repeat: no-repeat;
        width: 48px;
        height: 48px;
        transition: opacity 250ms ease-out;
        cursor: pointer;
        margin-right: 14px;

        &--hidden {
            opacity: 0;
            pointer-events: none;
        }
    }
</style>