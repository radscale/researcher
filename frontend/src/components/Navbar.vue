<template>
<div
    class="navbar"
    :class="{
        'navbar--free': free
    }"
>
    <div
        class="logo"
        :class="{
            'logo--hidden': hiddenLogo
        }"
        @click="$router.push({path: '/'})"
    >
    </div>
    <action-button
        v-if="!$auth.check()"
        class="navbar-button"
        @click="$bus.openModal('login')"
    >
        Log in
    </action-button>
    <action-button
        v-if="!$auth.check()"
        class="navbar-button"
        cta
        @click="$bus.openModal('signup')"
    >
        Sign up
    </action-button>
    <action-item
        v-if="user.email"
        no-background
        highlight
        type="user"
        :item="user"
        :to="{
            name: 'profile',
            params: {
                id: user.id
            }
        }"
    ></action-item>
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
        }
    },
    computed: {
        user () {
            return this.$auth.user()
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

        &--free {
            position: absolute;
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

        margin-right: auto; // flex spacer

        &--hidden {
            opacity: 0;
            pointer-events: none;
        }
    }
</style>