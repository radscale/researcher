<template>
    <div
        class="action-item"
        :class="[
            'action-item--' + type,
            {
                'action-item--list': list,
                'action-item--no-background': noBackground,
                'action-item--highlight': highlight,
                'action-item--link': to,
                'action-item--alert': alert
            }
        ]"
        @click="onClick($event)"
    >
        <span class="action-item__icon">
            <i :class="display.icon"></i>
        </span>
        <span
            class="action-item__name"
        >{{display.name}}</span>
        <span
            v-if="type == 'user'"
            class="action-item__suffix"
        >
            {{display.suffix}}
        </span>
        <span
            v-if="list && ['project', 'task', 'ad-hoc'].includes(type)"
            class="action-item__status"
        >
            {{display.status | capitalize}}
        </span>
        <transition name="fade-fast">
            <div 
                v-if="typeof alert == 'number'"
                class="action-item__alert"
            >{{alert}}</div>
        </transition>
    </div>
</template>

<script>
import {STATES} from '@/dicts.js'
import {parseSuffix} from '@/utils.js'

export default {
    name: 'action-item',
    props: {
        type: {
            type: String,
            required: true
        },
        item: {
            type: Object,
            default: () => {
                return {}
            }
        },
        list: {
            type: Boolean,
            default: false
        },
        noBackground: {
            type: Boolean,
            default: false
        },
        highlight: {
            type: Boolean,
            default: false
        },
        to: {
            type: Object,
            required: false
        },
        alert: {
            type: Number,
            default: null
        }
    },
    methods: {
        onClick ($event) {
            this.$emit('click', $event)
            if (this.to) {
                this.$router.push(this.to)
            }
        }
    },
    computed: {
        display () {
            let display = {
                icon: 'fas '
            }

            if (this.type == 'user') {
                display.icon += 'fa-user'
                display.name = this.item.firstName + ' ' + this.item.lastName
                display.suffix = parseSuffix(this.item)
            } else if (this.type == 'project') {
                display.icon += 'fa-project-diagram'
                display.name = this.item.name
            } else if (this.type == 'task' || this.type == 'adhoc') {
                display.icon += 'fa-scroll'
                display.name = this.item.name
            } else if (this.type == 'group') {
                display.icon += 'fa-users'
                display.name = this.item.name
            }

            if (['project', 'task', 'ad-hoc'].includes(this.type)) {
                display.status = STATES[this.item.status]
            }

            return display
        }
    }
}
</script>

<style lang="scss" scoped>
    @import '~@/styles/globals';

    .action-item {
        display: inline-block;
        position: relative;
        font-family: $font-global;
        outline: none;
        margin: 8px 4px;
        padding: 8px;
        line-height: 16px;
        min-width: 32px;
        text-align: center;
        border-radius: 8px;
        background-color: $background-item;
        cursor: default;

        transition:
            background-color 150ms ease-out,
            color 150ms ease-out,
            border-color 150ms ease-out;

        &__icon {
            color: $foreground-item__icon;
            margin-right: 8px;
        }

        &--no-background {
            background-color: transparent;
        }

        &--link {
            border: $border-item--link;
            cursor: pointer;

            &:not(.action-item--highlight) {
                border: $border-item--link--no-highlight;
            }

            &:hover {
                .action-item {
                    &__name {
                        text-decoration: underline;
                    }
                }
            }
        }

        &--highlight {
            .action-item {
                &__icon {
                    color: $foreground-item--highlight__icon;
                }

                &__name {
                    color: $foreground-item--highlight__name;
                }

                &__suffix {
                    color: $foreground-item--highlight__suffix;
                }
            }
        }

        &--list {
            width: 100%;
            margin: 8px 0;
            text-align: left;
            display: flex;
        }

        &__name {
            margin-right: auto;
            color: $foreground-item;
            font-weight: 600;
        }

        &__suffix {
            font-size: 12px;
            color: $foreground-item__suffix;
        }

        &__status {
            width: 30%;
            color: $foreground-item__status;
        }

        &__alert {
            position: absolute;
            top: -5px;
            right: -5px;
            padding: 0 4px;
            background-color: $background-alert;
            color: $foreground-alert;
            min-width: 16px;
            height: 16px;
            line-height: 16px;
            text-align: center;
            border-radius: 8px;
            font-size: 12px;
            font-weight: 600;
        }

        &:hover {
            
        }
    }
</style>
