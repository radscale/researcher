<template>
    <section class="dashboard section">
        <div class="section__header">
           <h1>Welcome, {{$auth.user().firstName}}.</h1>
        </div>
        <div class="section__main">
            <section-block>
                <template slot="title">
                    <h2>Projects</h2>
                </template>
                <ul class="list">
                    <action-item
                        type="project"
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
                    <h2>Users</h2>
                </template>
                <ul class="list">
                    <action-item
                        type="user"
                        list
                        v-for="user in users"
                        :item="user"
                        :key="user.id"
                        :to="{
                            name: 'profile',
                            params: {
                                id: user.id
                            }
                        }"
                        :alert="user.messages.unread"
                    ></action-item>
                </ul>
            </section-block>
        </div>
    </section>
</template>

<script>
import _ from 'lodash'

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
        },
        users () {
            // Get the list of users and enrich it with message data,
            // then sort it by unread message counts and last names
            return _.sortBy(
                _.map(this.$store.state.users, item => {
                    let messageData = _.find(
                        this.usersWithMessages,
                        msgData => {
                            return msgData.id == item.id
                        }
                    ) || {}

                    return {
                        ...item,
                        messages: {
                            last: messageData.last,
                            unread: messageData.unread
                        }
                    }
                }),
                [
                    item => item.messages.last,
                    item => item.lastName
                ]
            )
        },
        groups () {
            return _.map(this.$store.state.groups, item => {
                return {
                    ...item,
                    isMember: item.members.includes(this.$auth.user().id),
                    isCreator: item.creatorId == this.$auth.user().id
                }
            })
        },
        messages () {
            return this.$store.state.messages
        },
        usersWithMessages () {
            return [{
                id: 3,
                last: 1543044264,
                unread: 1
            }]
        }
    }
}
</script>

<style scoped lang="scss">
@import '~@/styles/globals';

.dashboard {
    
}
</style>