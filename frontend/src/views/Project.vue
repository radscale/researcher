<template>
    <section class="project section" :key="item.id">
        <div class="section__header">
            <h1>
                <i
                    class="section__header__icon fas fa-project-diagram"
                    title="Project"
                ></i>
                {{item.name}}
            </h1>
        </div>
        <div class="section__main">
            <section-block>
                <template slot="title">
                    <h2>Description</h2>
                </template>
                <p>{{item.description}}</p>
            </section-block>
            <section-block>
                <template slot="title">
                    <h2>Task list</h2>
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
                    ></action-item>
                </ul>
            </section-block>
        </div>
        <div class="section__side">
            <section-block highlight>
                <!-- <template slot="title">
                    <h2>Project</h2>
                </template> -->
                <div class="row">
                    <div class="col-40">
                        Status:
                    </div>
                    <div class="col-60">
                        <action-dropdown
                            :options="statusOptions"
                            no-margin
                            v-model="status"
                            name="project-status"
                            @change="onStatusChange"
                        ></action-dropdown>
                    </div>
                </div>
                <div class="row">
                    <div class="col-40">
                        Created by:
                    </div>
                    <div class="col-60">
                        <action-item
                            type='user'
                            fill
                            no-margin
                            :item="item.creator"
                            :to="{
                                name: 'profile',
                                params: {
                                    id: item.creator.id
                                }
                            }"
                        ></action-item>
                    </div>
                </div>
            </section-block>
            <section-block>
                <template slot="title">
                    <h2>Assigned users</h2>
                </template>
                <template slot="actions">
                    <action-button
                        @click="$bus.openModal('projectAssign')"
                        v-if="canManage"
                    >
                        Add...
                    </action-button>
                </template>
                <ul class="list">
                    <action-item
                        type='user'
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
                        @click="$bus.closeModal('projectAssign')"
                    >
                        <template
                            slot="actions"
                            v-if="canManage"
                        >
                            <action-button
                                small
                                @click.stop=""
                            >
                                <i class="fas fa-times"></i>
                            </action-button>
                        </template>
                    </action-item>
                </ul>
            </section-block>
        </div>

        <modal
            name="projectAssign"
            cancel="Done"
            full-size
        >
            <template slot="header">
                <h2>Assign users to project</h2>
            </template>
            <template slot="content">
                <div class="row">
                    <action-input
                        v-model.trim="userQuery"
                        name="userQuery"
                        placeholder="Type first, last names, titles..."
                        autofocus
                    >
                        <label slot="label" for="userQuery">
                            Filter users
                        </label>
                    </action-input>
                    <transition-group
                        name="slide"
                        tag="ul"
                        class="assign-list"
                    >
                        <li
                            v-for="user in userQueryResults"
                            :key="user.id"
                        >
                            <action-item
                                type="user"
                                list
                                :item="user"
                                :to="{
                                    name: 'profile',
                                    params: {
                                        id: user.id
                                    }
                                }"
                            >
                            <template
                                slot="actions"
                                v-if="canManage"
                            >
                                <action-button
                                    small
                                    @click.stop=""
                                >
                                    <i class="fas fa-plus"></i>
                                </action-button>
                            </template>
                            </action-item>
                        </li>
                    </transition-group>
                </div>
            </template>
        </modal>
    </section>
</template>

<script>
import _ from 'lodash'
import store from '@/store'
import bus from '@/bus'

import Modal from '@/components/Modal.vue'
import ActionItem from '@/components/ActionItem.vue'
import ActionButton from '@/components/ActionButton.vue'
import ActionDropdown from '@/components/ActionDropdown.vue'
import ActionInput from '@/components/ActionInput.vue'
import SectionBlock from '@/components/SectionBlock.vue'

import {STATES} from '@/dicts'
import {parseSuffix} from '@/utils'

const _fetchData = function (params, callback) {
    store.dispatch('getProject', {id: params.id}).then(callback, error => {
        bus.pushMessage({
            type: 'error',
            content: 'Could not load project data.'
        })
    })
}

export default {
    name: 'project',
    components: {
        ActionItem,
        ActionButton,
        ActionDropdown,
        ActionInput,
        SectionBlock,
        Modal
    },
    props: {
        id: {
            type: Number,
            required: true
        }
    },
    mounted () {
        this.status = this.item.status
        this.fetchUsersForQuery()
    },
    beforeRouteEnter: function (to, from, next) {
        _fetchData(to.params, next)
    },
    beforeRouteUpdate: function (to, from, next) {
        _fetchData(to.params, next)
    },
    methods: {
        onStatusChange (status) {
            if (STATES.hasOwnProperty(status)) {
                this.$store.dispatch('setProjectStatus', {
                    id: this.item.id,
                    status: status
                })
            }
        },
        fetchUsersForQuery (query) {
            // TODO: Do fetching and filtering on the backend
            this.axios.get('users').then(res => {
                this.userQueryResults = _.filter(
                    res.data,
                    item => {
                        let notMember = !_.some(this.users, user => {
                            return user.id == item.id
                        })
                        if (!query) {
                            return notMember
                        } else {
                            let name = item.firstName + ' ' + item.lastName + ' ' + parseSuffix(item)
                            return notMember && (name.toLowerCase().indexOf(query.toLowerCase()) > -1)
                        }
                    }
                )
            })
        }
    },
    computed: {
        item () {
            return this.$store.state.currentProject.item
        },
        tasks () {
            return this.$store.state.currentProject.tasks
        },
        users () {
            return this.$store.state.currentProject.users
        },
        statusOptions () {
            return STATES
        },
        canManage () {
            // TODO: Access rights
            return true
        }
    },
    watch: {
        item (newItem) {
            this.status = this.item.status
        },
        userQuery: 
            _.debounce(function (query) {
                this.fetchUsersForQuery(query)
            }, 200)
    },
    data () {
        return {
            status: '',
            userQuery: '',
            userQueryResults: []
        }
    }
}
</script>

<style scoped lang="scss">
@import '~@/styles/globals';

</style>