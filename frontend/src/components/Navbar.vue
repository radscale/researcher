<template>
<div
    class="navbar"
    :class="{
        'navbar--free': $route.meta.freeNavbar
    }"
>
    <div
        class="logo"
        :class="{
            'logo--hidden': $route.meta.hiddenLogo
        }"
        @click="$router.push({path: '/'})"
    >
    </div>
    <action-button class="navbar-button">
        Log in
    </action-button>
    <action-button class="navbar-button" cta>
        Sign up
    </action-button>
</div>
</template>

<script>
import ActionButton from '@/components/ActionButton.vue'

export default {
    name: 'navbar',
    components: {
        ActionButton
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
        background-size: 48px;
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