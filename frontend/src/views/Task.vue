<template>
    <section class="task">
        <div class="task__header task__header--parent">
            <action-item
                type="project"
                :item="item.project"
                no-background
                :to="{
                    name: 'project',
                    params: {
                        id: item.project.id
                    }
                }"
            ></action-item>
        </div>
        <div class="task__header">
            <i class="fas fa-level-up-alt fa-rotate-90 task__header__directory-arrow"></i>
            <i
                class="fas fa-scroll"
                title="Task"
            ></i>
            <h1>{{item.name}}</h1>
        </div>
        <div class="task__description">
            <section-block>
                <template slot="title">
                    <h2>Description</h2>
                </template>
                <p>{{item.description}}</p>
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

const _fetchData = function (params, callback) {
    store.dispatch('getTask', {id: params.id}).then(callback, error => {
        bus.pushMessage({
            type: 'error',
            content: 'Could not load task data.'
        })
    })
}

export default {
    name: 'task',
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
        // this.$store.dispatch('getTask', {id: this.id})
    },
    beforeRouteEnter: function (to, from, next) {
        _fetchData(to.params, next)
    },
    beforeRouteUpdate: function (to, from, next) {
        _fetchData(to.params, next)
    },
    computed: {
        item () {
            return this.$store.state.currentTask.item
        }
    }
}
</script>

<style scoped lang="scss">
@import '~@/styles/globals';

.task {
    &__header {
        display: flex;
        flex-direction: row;
        align-items: center;

        i {
            font-size: 20px;
            margin: 0 18px 0 8px;
            color: $foreground-entity-icon;

            &.task__header__directory-arrow {
                margin: 0 20px 0 24px;
                opacity: 0.5;
            }
        }

        h1 {
            font-weight: 400;
        }

        &--parent {
            margin-bottom: -8px;

            i {
                font-size: 16px;
                margin: 0 10px 0 4px;
            }

            h2 {
                font-size: 16px;
            }
        }
    }
}
</style>