<template>
    <div
        class="action-message"
        :class="[
            'action-message--' + type
        ]"
    >
        <div class="action-message__header">
            <div class="action-message__authors">
                <div class="action-message__sender">{{ sender.firstName }} {{ sender.lastName }}</div>
                <i class="fas fa-chevron-right"></i>
                <div class="action-message__receiver">{{ receiver.firstName }} {{ receiver.lastName }}</div>
            </div>
            <div class="action-message__timestamp" :title="$moment(item.date * 1000)">{{ item.date | moment('from') }}</div>
        </div>
        <div class="action-message__content">
            {{ item.content }}
        </div>
    </div>
</template>

<script>
import _ from 'lodash'

import {STATES} from '@/dicts.js'
import {parseSuffix} from '@/utils.js'

export default {
    name: 'action-message',
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
        }
    },
    computed: {
        sender () {
            return this.item.id ? this.getUserData(this.item.senderId) : {}
        },
        receiver () {
            return this.item.id ? this.getUserData(this.item.receiverId) : {}
        },
        user () {
            return this.$auth.user()
        }
    },
    methods: {
        getUserData (userId) {
            if (userId == this.user.id) {
                return this.user
            } else if (this.$store.state.currentUser.item && userId == this.$store.state.currentUser.item.id) {
                return this.$store.state.currentUser.item
            } else {
                return _.find(this.$store.state.users, item => userId == this.item.id) || {}
            }
        }
    }
}
</script>

<style lang="scss" scoped>
    @import '~@/styles/globals';

    .action-message {
        display: inline-block;
        position: relative;
        font-family: $font-global;
        outline: none;
        margin: 6px 4px;
        padding: 8px;
        line-height: 16px;
        min-width: 32px;
        text-align: center;
        border-radius: 8px;
        background-color: $background-item;
        align-items: baseline;
        cursor: default;
        width: 100%;
        text-align: left;

        transition:
            background-color 150ms ease-out,
            color 150ms ease-out,
            border-color 150ms ease-out;

        &__header {
            display: flex;
        }

        &__authors {
            display: flex;
            align-items: baseline;

            > * {
                margin-right: 8px;
            }

            i {
                color: $foreground-message__status;
                font-size: 10px;
            }
        }

        &__sender {
            font-weight: 600;
        }

        &__receiver {
            color: $foreground-message__status;
        }

        &__timestamp {
            margin-left: auto;
            color: $foreground-message__status;
        }

        &__content {
            margin: 8px 0;
            line-height: 16px;
        }
    }
</style>
