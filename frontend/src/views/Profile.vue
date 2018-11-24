<template>
    <div class="profile">
        <h1>{{item.firstName}} {{item.lastName}}</h1>
        <h2 v-if="isMe">It's you!</h2>
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
    name: 'profile',
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
        this.$store.dispatch('getUser', {id: this.id})
    },
    computed: {
        item () {
            return this.$store.state.user
        },
        isMe () {
            return this.item.id == this.$auth.user().id
        }
        // projects () {
        //     return this.$store.state.projects
        // }
    }
}
</script>

<style scoped lang="scss">
@import '~@/styles/globals';

.profile {
    margin: 50px auto;
    padding: 8px 24px;
}
</style>