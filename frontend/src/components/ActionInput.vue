<template>
<div class="action-input__wrapper">
    <component :is="element"
        :type="type"
        class="action-input"
        :class="{
            'action-input--disabled': disabled
        }"
        @change="onChange($event)"
        @input="onInput($event)"
        :value="value"
        :id="name"
        :placeholder="placeholder"
    >
        <template v-if="type == 'textarea'">{{value}}</template>
    </component>
    <slot
        name="label"
    ></slot>
    <div class="action-input__validation">
        {{ validationString }}
    </div>
</div>
</template>

<script>
export default {
    name: 'action-input',
    props: {
        disabled: {
            type: Boolean,
            default: false
        },
        element: {
            type: String,
            default: 'input'
        },
        type: {
            type: String,
            default: 'text'
        },
        value: {
            type: String,
            default: ''
        },
        name: {
            type: String,
            required: true
        },
        placeholder: {
            type: String,
            required: false
        },
        validations: {
            type: Object,
            default: () => {
                return {}
            }  
        }
    },
    methods: {
        onChange ($event) {
            this.validations.$touch()
            this.$emit('change', $event)
        },
        onInput ($event) {
            this.$emit('input', $event.target.value)    
        }
    },
    computed: {
        errors () {
            let errors = []
                if (this.validations.$dirty) {
                    if (this.validations.required == false)
                        errors.push('This field is required.')
                    if (this.validations.email == false)
                        errors.push('Please input a valid e-mail address.')
                    if (this.validations.passwordMatch == false)
                        errors.push('Both passwords need to match.')
                    if (this.validations.minLengthPassword == false)
                        errors.push('The password needs to be at least 6 characters long.')
                }

            return errors;
        },
        validationString () {
            return this.errors.length ? this.errors[0] : ''
        }
    }
}
</script>

<style lang="scss" scoped>
    @import '~@/styles/globals';

    .action-input {
        display: inline-block;
        padding: 4px 8px 0 8px;
        line-height: 28px;
        min-width: 32px;
        background-color: $background-input;
        border: $border-input;
        color: $foreground-input;
        border-radius: 8px;
        outline: none;
        width: 100%;
        font-family: $font-global;

        transition:
            color 150ms ease-in,
            background-color 150ms ease-in,
            border-color 150ms ease-in;

        &--icon {
            width: 32px;

            .span {
                display: none;
            }
        }

        &--disabled {
            background-color: $background-input--disabled;
            border: $border-input--disabled;
            color: $foreground-input--disabled;
            cursor: default;
        }

        &::placeholder {
            color: $foreground-placeholder;
        }

        &:focus {
            background-color: $background-input--focus;
            border: $border-input--focus;
            color: $foreground-input--focus;

            + label {
                color: $foreground-input--focus !important;
                left: 4px;
            }
        }

        &__wrapper {
            margin: 16px 0px;
            margin-bottom: 32px;
            position: relative;

            label {
                color: $foreground-label;
                display: inline-block;
                font-size: 16px;
                line-height: 18px;
                position: absolute;
                top: -22px;
                left: 0;
                right: 0;

                transition:
                    color 150ms ease-out,
                    left 150ms ease-out;
            }
        }

        &__validation {
            color: $foreground-validation;
            font-size: 12px;
            line-height: 18px;
            min-height: 18px;
            margin-top: 4px;
        }
    }
</style>