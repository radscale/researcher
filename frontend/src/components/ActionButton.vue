<template>
<component :is="type"
    class="action-button"
    :class="{
        'action-button--cta': cta,
        'action-button--disabled': disabled,
        'action-button--icon': icon
    }"
    @click="onClick"
>
    <slot></slot>
</component>
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
        },
        type: {
            type: String,
            default: 'div'
        },
        icon: {
            type: Boolean,
            default: false
        }
    },
    methods: {
        onClick (event) {
            this.$emit('click', event)
        }
    }
}
</script>

<style lang="scss" scoped>
    @import '~@/styles/globals';

    .action-button {
        display: inline-block;
        font-family: $font-global;
        outline: none;
        margin: 0 4px;
        padding: 8px;
        line-height: 16px;
        min-width: 32px;
        text-align: center;
        background-color: $background-button;
        border: $border-button;
        color: $foreground-button;
        border-radius: 8px;
        cursor: pointer;
        transition:
            background-color 150ms ease-out,
            color 150ms ease-out,
            border-color 150ms ease-out;

        &--icon {
            width: 32px;

            .span {
                display: none;
            }
        }

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
            background-color: $background-button--hover;
            border: $border-button--hover;
            color: $foreground-button--hover;

            &.action-button--cta {
                background-color: $background-button--cta--hover;
                border: $border-button--cta--hover;
                color: $foreground-button--cta--hover;
            }
        }
    }
</style>
