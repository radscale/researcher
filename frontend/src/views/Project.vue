<template>
    <div class="project">
        <h1>{{item.name}}</h1>
        <!-- <h2>Project list</h2>
        <ul class="list">
            <action-item
                type='project'
                list
                v-for="project in projects"
                :item="project"
                :key="project.id"
            ></action-item>
        </ul> -->
    </div>
</template>

<script>
import ActionItem from '@/components/ActionItem.vue'
import ActionButton from '@/components/ActionButton.vue'
import _ from 'lodash'

export default {
    name: 'project',
    components: {
        ActionItem,
        ActionButton
    },
    props: {
        id: {
            type: Number,
            required: true
        }
    },
    mounted () {
        this.$store.dispatch('getProject', {id: this.id})
    },
    computed: {
        item () {
            return this.$store.state.project
        }
    }
}
</script>

<style scoped lang="scss">
@import '~@/styles/globals';

.project {
    margin: 50px auto;
    padding: 8px 24px;
}
</style>