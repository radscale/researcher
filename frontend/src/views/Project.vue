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
                            name: 'user',
                            params: {
                                id: user.id
                            }
                        }"
                    ></action-item>
                </ul>
            </section-block>
        </div>

        <modal
            name="projectAssign"
            cancel="Done"
        >
            <template slot="header">
                <h2>Assign users to project</h2>
            </template>
            <template slot="content">
                <p>Ea nulla labore ut fugiat qui nulla anim quis. Nulla dolore laborum ullamco incididunt officia quis fugiat elit. Irure culpa aliquip irure sit consequat duis. Deserunt sint non esse non non commodo ad aliqua culpa aliqua veniam. Magna anim eu laboris laborum ad laboris. Elit cillum culpa dolor aliquip anim velit reprehenderit est aliqua dolore sit consequat quis. Quis cillum anim veniam aute irure veniam enim excepteur aute magna Lorem exercitation.</p>
                <p>Laborum consectetur incididunt ea voluptate aliqua officia fugiat adipisicing. Commodo id esse esse ullamco exercitation laboris exercitation mollit sunt. Duis do nulla cupidatat dolore eu nulla consequat deserunt cillum ex reprehenderit aliqua est. Adipisicing aliquip consectetur mollit quis adipisicing ut Lorem sit laborum deserunt incididunt commodo aliquip pariatur. Dolor et qui duis incididunt culpa sunt dolore. Enim laborum culpa consectetur esse.</p>
                <p>Et est nostrud velit commodo id nostrud. Consectetur ullamco aliqua sit amet Lorem ullamco sit. Nulla amet est fugiat pariatur dolore laborum in. Irure magna Lorem aute nulla consequat quis sunt voluptate minim sunt elit et labore occaecat. Proident aute irure velit ad et enim adipisicing. Pariatur nulla magna ipsum in mollit pariatur Lorem commodo dolor tempor ex ullamco. Deserunt qui proident in cillum eu ad ullamco ea anim adipisicing velit enim qui consectetur.</p>
                <p>Commodo occaecat aliqua aliquip est occaecat ex. Aliquip irure pariatur elit nostrud incididunt exercitation officia eu deserunt culpa minim. Lorem amet laborum cupidatat officia aliquip exercitation voluptate ex est quis ut nisi aliquip veniam. Sunt consequat duis ut in Lorem enim mollit eiusmod cillum incididunt ullamco dolor. Amet ut fugiat nostrud duis magna duis commodo mollit sit ut aute commodo commodo esse. Voluptate ut commodo velit dolore voluptate.</p>
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