<template>
    <div
        class="section-block"
        :class="{
            'section-block--highlight': highlight
        }"
    >
        <div class="section-block__title">
            <slot name="title"></slot>
        </div>
        <div class="section-block__actions">
            <slot name="actions"></slot>
        </div>
        <div class="section-block__content">
            <slot></slot>
        </div>
    </div>
</template>

<script>
export default {
    name: 'section-block',
    props: {
        highlight: {
            type: Boolean,
            default: false
        }
    }
}
</script>

<style lang="scss" scoped>
    @import '~@/styles/globals';

    .section-block {
        position: relative;
        padding-top: 20px;
        margin-bottom: 8px;

        &__title {
            position: absolute;
            left: 8px;
            top: 0;
            background-color: $background-block__title;
            padding: 0 8px;
        }

        &__actions {
            position: absolute;
            right: 8px;
            top: 2px;
            background-color: $background-block__actions;
            padding: 0 8px;
        }

        &__content {
            border-radius: 8px;
            border: $border-block;
            padding: 8px 16px;
            background-color: $background-block;
            height: 100%;
        }

        &--highlight {
            .section-block__content {
                background-color: $background-block--highlight;
                border: $border-block--highlight;
            }
        }
    }
</style>

