<template>
    <section class="dashboard">
        <h1>Welcome, {{$auth.user().firstName}}.</h1>
        <div class="project__tasks">
            <section-block>
                <template slot="title">
                    <h2>Project list</h2>
                </template>
                <ul class="list">
                    <action-item
                        type='project'
                        list
                        v-for="project in projects"
                        :item="project"
                        :key="project.id"
                        :to="{
                            name: 'project',
                            params: {
                                id: project.id
                            }
                        }"
                    ></action-item>
                </ul>
            </section-block>
        </div>
    </section>
</template>

<script>
import ActionItem from '@/components/ActionItem.vue'
import ActionButton from '@/components/ActionButton.vue'
import SectionBlock from '@/components/SectionBlock.vue'

export default {
    name: 'dashboard',
    components: {
        ActionItem,
        ActionButton,
        SectionBlock
    },
    mounted () {
        // this.$store.dispatch('updateProjects')
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
    
}
</style>