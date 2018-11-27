<template>
    <section class="profile section" :key="item.id">
        <div class="section__header">
            <h1>
                {{item.firstName}} {{item.lastName}}
                <span class="suffix">{{suffix}}</span>
            </h1>
        </div>
        <div class="section__main">
            <section-block>
                <template slot="title">
                    <h2>About...</h2>
                </template>
                <p>About this user</p>
            </section-block>
            <section-block v-if="!isMe">
                <template slot="title">
                    <h2>Messages</h2>
                </template>
                <p>Message log...</p>
            </section-block>
        </div>
        <div class="section__side">
           <section-block>
                <template slot="title">
                    <h2>Research groups</h2>
                </template>
                <ul class="list">
                    <action-item
                        type="group"
                        list
                        v-for="group in groups"
                        :item="group"
                        :key="group.id"
                        :to="{
                            name: 'group',
                            params: {
                                id: group.id
                            }
                        }"
                        :highlight="group.isMember"
                    ></action-item>
                </ul>
            </section-block>
            <section-block>
                <template slot="title">
                    <h2>Current projects</h2>
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
                        :highlight="project.isMember"
                    ></action-item>
                </ul>
            </section-block>
            <section-block>
                <template slot="title">
                    <h2>Current tasks</h2>
                </template>
                <ul class="list">
                    <action-item
                        type='task'
                        list
                        v-for="task in tasks"
                        :item="task"
                        :key="task.id"
                        :to="{
                            name: 'task',
                            params: {
                                id: task.id
                            }
                        }"
                        :highlight="task.isMember"
                    ></action-item>
                </ul>
            </section-block>
        </div>
    </section>
</template>

<script>
import _ from 'lodash'
import store from '@/store'
import bus from '@/bus.js'

import ActionItem from '@/components/ActionItem.vue'
import ActionButton from '@/components/ActionButton.vue'
import SectionBlock from '@/components/SectionBlock.vue'

import {parseSuffix} from '@/utils.js'

const _fetchData = function (params, callback) {
    store.dispatch('getUser', {id: params.id}).then(callback, error => {
        bus.pushMessage({
            type: 'error',
            content: 'Could not load profile data.'
        })
    })
}

export default {
    name: 'profile',
    components: {
        ActionItem,
        ActionButton,
        SectionBlock
    },
    props: {
        id: {
            type: Number,
            required: true
        }
    },
    mounted () {
        // this.$store.dispatch('getUser', {id: this.id})
        // this.item = this.storeItem
    },
    beforeRouteEnter: function (to, from, next) {
        _fetchData(to.params, next)
    },
    beforeRouteUpdate: function (to, from, next) {
        _fetchData(to.params, next)
    },
    computed: {
        item () {
            return this.$store.state.currentUser.item
        },
        suffix () {
            return parseSuffix(this.item)
        },
        isMe () {
            return this.item.id == this.$auth.user().id
        },
        groups () {
            return _.map(
                this.$store.state.currentUser.groups,
                item => {
                    return {
                        ...item,
                        isMember: item.members.includes(this.$auth.user().id),
                        isCreator: item.creatorId == this.$auth.user().id
                    }
                }
            )
        },
        projects () {
            return _.map(
                this.$store.state.currentUser.projects,
                item => {
                    return {
                        ...item,
                        isMember: item.members.includes(this.$auth.user().id),
                        isCreator: item.creatorId == this.$auth.user().id
                    }
                }
            )
        },
        tasks () {
            return _.map(
                this.$store.state.currentUser.tasks,
                item => {
                    return {
                        ...item,
                        isMember: item.members.includes(this.$auth.user().id),
                        isCreator: item.creatorId == this.$auth.user().id
                    }
                }
            )
        }
    }
}
</script>

<style scoped lang="scss">
@import '~@/styles/globals';

.profile {
    .suffix {
        font: $font-header--sub;
        color: $foreground-header--sub;
    }
}
</style>