<template>
<div class="popup__wrapper">
    <div
        class="popup"
        :class="{
            'popup--forced': forced
        }"
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