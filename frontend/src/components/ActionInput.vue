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
        @keydown.enter="onEnter($event)"
        :value="value"
        :id="name"
        :placeholder="placeholder"
        :autofocus="autofocus"
        :disabled="disabled"
        ref="input"
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
        },
        autofocus: {
            type: Boolean,
            default: false
        }
    },
    methods: {
        onChange ($event) {
            if (typeof this.validations.$touch == 'function')
                this.validations.$touch()
            this.$emit('change', $event)
        },
        onInput ($event) {
            if (this.disabled) {
                $event.preventDefault()
                return
            }
            this.$emit('input', $event.target.value)    
        },
        onEnter ($event) {
            this.$emit('enter', $event.target.value)
        }
    },
    watch: {
        value () {
            if (!this.value) {
                this.$refs.input.value = ''
            }
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
        font-size: 14px;

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
            margin: 32px 0px 4px;
            width: 100%;
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
