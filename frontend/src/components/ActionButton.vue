<template>
<div
    class="action-button"
    :class="{
        'action-button--cta': cta,
        'action-button--disabled': disabled
    }"
>
    <slot></slot>
</div>
</template>

<script>
export default {
    name: 'action-button',
    props: {
        cta: {
            type: Boolean,
            default: false
        },
        disabled: {
            type: Boolean,
            default: false
        }
    }
}
</script>

<style lang="scss" scoped>
    @import '~@/styles/globals';

    .action-button {
        display: inline-block;
        margin: 0 4px;
        padding: 8px;
        line-height: 24px;
        background-color: $background-button;
        border: $border-button;
        color: $foreground-button;
        border-radius: 8px;
        cursor: pointer;
        transition: opacity 150ms ease-in;

        &--cta {
            background-color: $background-button--cta;
            border: $border-button--cta;
            color: $foreground-button--cta;
        }

        &--disabled {
            background-color: $background-button--disabled;
            border: $border-button--disabled;
            color: $foreground-button--disabled;
            cursor: default;

            &.action-button--cta {
                background-color: $background-button--disabled--cta;
                border: $border-button--disabled--cta;
                color: $foreground-button--disabled--cta;
            }
        }

        &:hover {
            opacity: 0.75;
        }
    }
</style>
