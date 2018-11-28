<template>
<div class="popup__wrapper">
    <div
        class="popup"
        :class="[
            'popup--' + type,    
            {
                'popup--forced': forced
            }
        ]"
    >
        <div class="popup__content">
            <slot></slot>
        </div>
        <action-button
            icon
            class="popup__close"
            @click="clickClose"
            @if="!forced"
            faded
        >
            <i class="fa fa-times"></i>
        </action-button>
    </div>
</div>
</template>

<script>
import ActionButton from '@/components/ActionButton.vue'
import ActionItem from '@/components/ActionItem.vue'

export default {
    name: 'popup',
    components: {
        ActionButton,
        ActionItem
    },
    props: {
        forced: {
            type: Boolean,
            default: false
        },
        type: {
            type: String,
            default: 'info'
        }
    },
    methods: {
        clickClose ($event) {
            this.$emit('close', $event)
        }
    },
    computed: {
        
    }
}
</script>

<style scoped lang="scss">
    @import '~@/styles/globals';

    .popup {
        margin: 14px;
        padding: 14px;
        background-color: $background-popup;
        color: $foreground-popup;
        border: $border-popup;
        border-radius: 8px;

        display: flex;
        align-items: baseline;

        &--error {
            color: $foreground-popup--error;
            background-color: $background-popup--error;
            border: $border-popup--error;

            .popup__close {
                color: $foreground-popup--error;
            }
        }

        &__wrapper {
            margin: 0 auto;
            width: 100%;
            max-width: 640px;
        }

        &__content {
            padding: 0 14px;
        }

        &__close {
            margin-left: auto;
        }
    }
</style>