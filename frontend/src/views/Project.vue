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
                                name: 'user',
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
                <ul class="list">
                    <action-item
                        type='user'
                        list
                        v-for="user in users"
                        :item="user"
                        :key="user.id"
                        :to="{
                            name: 'user',
                            params: {
                                id: user.id
                            }
                        }"
                    ></action-item>
                </ul>
            </section-block>
        </div>
    </section>
</template>

<script>
import _ from 'lodash'
import store from '@/store'
import bus from '@/bus'

import ActionItem from '@/components/ActionItem.vue'
import ActionButton from '@/components/ActionButton.vue'
import ActionDropdown from '@/components/ActionDropdown.vue'
import SectionBlock from '@/components/SectionBlock.vue'

import {STATES} from '@/dicts'

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
        SectionBlock
    },
    props: {
        id: {
            type: Number,
            required: true
        }
    },
    mounted () {
        this.status = this.item.status
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
        }
    },
    watch: {
        item (newItem) {
            this.status = this.item.status
        }
    },
    data () {
        return {
            status: ''
        }
    }
}
</script>

<style scoped lang="scss">
@import '~@/styles/globals';

.project {

}
</style>