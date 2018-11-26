<template>
    <section class="project">
        <div class="project__header">
            <i
                class="fas fa-project-diagram"
                title="Project"
            ></i>
            <h1>{{item.name}}</h1>
        </div>
        <div class="project__description">
            <section-block>
                <template slot="title">
                    <h2>Description</h2>
                </template>
                <p>{{item.description}}</p>
            </section-block>
        </div>
        <div class="project__tasks">
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
    </section>
</template>

<script>
import ActionItem from '@/components/ActionItem.vue'
import ActionButton from '@/components/ActionButton.vue'
import SectionBlock from '@/components/SectionBlock.vue'
import _ from 'lodash'

export default {
    name: 'project',
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
        // this.$store.dispatch('getProject', {id: this.id})
    },
    computed: {
        item () {
            return this.$store.state.currentProject.item
        },
        tasks () {
            return this.$store.state.currentProject.tasks
        }
    }
}
</script>

<style scoped lang="scss">
@import '~@/styles/globals';

.project {
    &__header {
        display: flex;
        flex-direction: row;
        align-items: center;

        i {
            font-size: 20px;
            margin: 0 18px 0 8px;
            color: $foreground-entity-icon;
        }

        h1 {
            font-weight: 400;
        }
    }
}
</style>