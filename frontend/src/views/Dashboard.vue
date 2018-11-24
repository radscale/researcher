<template>
    <div class="dashboard">
        <h1>Welcome, {{$auth.user().firstName}}.</h1>
        <h2>Project list</h2>
        <ul class="list">
            <action-item
                type='project'
                list
                v-for="project in projects"
                :item="project"
                :key="project.id"
            ></action-item>
        </ul>
    </div>
</template>

<script>
import ActionItem from '@/components/ActionItem.vue'
import ActionButton from '@/components/ActionButton.vue'

export default {
    name: 'dashboard',
    components: {
        ActionItem,
        ActionButton
    },
    mounted () {
        this.$store.dispatch('updateProjects')
    },
    computed: {
        projects () {
            return this.$store.state.projects
        }
    }
}
</script>

<style scoped lang="scss">
@import '~@/styles/globals';

.dashboard {
    margin: 50px auto;
    padding: 8px 24px;
}
</style>