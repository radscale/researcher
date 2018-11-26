<template>
    <section class="landing">
        <img class="logo" alt="Researcher logo" src="../assets/logo-transparent-512px.png">
        <h1>Researcher</h1>
        <p>Research group management suite</p>
        <div class="highlight">
            <action-button
                @click="$bus.openModal('login')"
            >
                Log in
            </action-button>
            or
            <action-button
                cta
                @click="$bus.openModal('signup')"
            >
                Sign up
            </action-button>
        </div>
    </section>
</template>

<script>
import ActionButton from '@/components/ActionButton.vue'

export default {
    name: 'landing',
    components: {
        ActionButton
    }
}
</script>

<style scoped lang="scss">
@import '~@/styles/globals';

.landing {
    margin: calc(50px + 5vh) auto;
    text-align: center;
}

.logo {
    max-height: 30vh;
}
</style>