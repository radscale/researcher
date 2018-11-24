<template>
    <dashboard v-if="$auth.check()"></dashboard>
    <landing v-else></landing>
</template>

<script>
import Landing from '@/views/Landing.vue'
import Dashboard from '@/views/Dashboard.vue'

export default {
    name: 'home',
    components: {
        Landing,
        Dashboard
    }
}
</script>

