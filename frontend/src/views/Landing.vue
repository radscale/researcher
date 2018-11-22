<template>
    <div class="landing">
        <img class="logo" alt="Researcher logo" src="../assets/logo-transparent-512px.png">
        <h1>Researcher</h1>
        <p>Research group management suite</p>
        <div class="highlight">
            <action-button>
                Log in
            </action-button>
            or
            <action-button cta>
                Sign up
            </action-button>
        </div>
    </div>
</template>

<script>
import ActionButton from '@/components/ActionButton.vue';

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
    margin: 50px auto;
    text-align: center;
}

.logo {
    max-height: 30vh;
}
</style>