<template>
<div
    class="action-dropdown__wrapper"
    :class="{
        'action-dropdown__wrapper--no-margin': noMargin
    }"
>
    <div 
        class="action-dropdown"
        :class="{
            'action-dropdown--disabled': disabled,
            'action-dropdown--open': open
        }"
        :value="value"
        :id="name"
        @click="onClick"
    >
        <span>{{ getOption(value) | capitalize }}</span>
        <div 
            class="action-dropdown__icon"
            v-if="!disabled"
        >
            <i class="fas fa-chevron-down"></i>
        </div>
        <transition name="drop-down">
            <div
                class="action-dropdown__list"
                :class="{
                    'action-dropdown__list--open': open
                }"
                v-if="!disabled && open"
            >
                <ul>
                    <li 
                        class="action-dropdown__option"
                        :class="{
                            'action-dropdown__option--selected': key == value
                        }"
                        v-for="(label, key) in dropdownOptions"
                        :key="key"
                        v-if="placeholderSelectable || key != ''"
                        @click="optionClick(key)"
                    >{{ label | capitalize }}</li>
                </ul>
            </div>
        </transition>
    </div>
    <!-- <select
        :name="name"
        ref="select-node"
    >
        <option
            v-for="(label, key) in dropdownOptions"
            :key="key"
            :value="key"
        >{{ label | capitalize }}</option>
    </select> -->
    <slot
        name="label"
    ></slot>
    <!-- <div class="action-dropdown__validation">
        {{ validationString }}
    </div> -->
</div>
</template>

<script>
export default {
    name: 'action-dropdown',
    props: {
        disabled: {
            type: Boolean,
            default: false
        },
        noMargin: {
            type: Boolean,
            default: false
        },
        options: {
            type: Object,
            default: () => {
                return {}
            }
        },
        placeholder: {
            type: String,
            default: 'Select...'
        },
        placeholderSelectable: {
            type: Boolean,
            default: false
        },
        value: {
            type: String,
            default: ''
        },
        name: {
            type: String,
            required: true
        },
        validations: {
            type: Object,
            default: () => {
                return {}
            }  
        }
    },
    methods: {
        getOption (key) {
            if (this.dropdownOptions.hasOwnProperty(this.value)) {
                return this.dropdownOptions[key]
            } else {
                return this.dropdownOptions['']
            }
        },
        toggle () {
            this.open = !this.open
        },
        onClick ($event) {
            this.toggle ()
        },
        optionClick (key) {
            this.$emit('input', key)
            this.$emit('change', key)
        },
        globalClick ($event) {
            this.open = false
        }
    },
    watch: {
        open (newState) {
            if (newState) {
                // Opened
                document.addEventListener('click', this.globalClick)
            } else {
                // Closed
                document.removeEventListener('click', this.globalClick)
            }
        }
    },
    computed: {
        dropdownOptions () {
            return {
                '': this.placeholder,
                ...this.options
            }
        },
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
    },
    data () {
        return {
            open: false
        }
    }
}
</script>

<style lang="scss" scoped>
    @import '~@/styles/globals';

    .action-dropdown {
        display: inline-block;
        padding: 4px 32px 0 8px;
        line-height: 28px;
        min-width: 32px;
        background-color: $background-dropdown;
        border: $border-dropdown;
        color: $foreground-dropdown;
        border-radius: 8px;
        outline: none;
        width: 100%;
        font-family: $font-global;
        cursor: pointer;

        transition:
            color 150ms ease-in,
            background-color 150ms ease-in,
            border-color 150ms ease-in,
            border-radius 150ms ease-in;

        &--disabled {
            background-color: $background-dropdown--disabled;
            border: $border-dropdown--disabled;
            color: $foreground-dropdown--disabled;
            cursor: default;
        }

        &__icon {
            position: absolute;
            right: 11px;
            top: 6px;
        }

        &__list {
            position: absolute;
            top: calc(100% - 4px);
            left: 0;
            right: 0;
            z-index: 3;
            background-color: $background-dropdown__list;
            border: $border-dropdown__list;
            border-top: transparent;
            border-radius: 0 0 8px 8px;
            display: none;
            cursor: default;

            &--open {
                display: block;
            }

            ul {
                list-style: none;
                margin: 14px 0 8px;
            }
        }

        &__option {
            padding: 0 8px;

            background-color: $background-dropdown__option;
            color: $foreground-dropdown__option;
            cursor: pointer;

            transition: 
                color 150ms ease-out,
                background-color 150ms ease-out;

            &--selected {
                background-color: $background-dropdown__option--selected;
                color: $foreground-dropdown__option--selected;
            }

            &:hover {
                background-color: $background-dropdown__option--hover;
                color: $foreground-dropdown__option--hover;
            }
        }

        &--open {
            background-color: $background-dropdown--focus;
            border: $border-dropdown--focus;
            color: $foreground-dropdown--focus;
            border-radius: 8px 8px 0 0;
            border-bottom: 1px solid transparent;

            + label {
                color: $foreground-input--focus !important;
                left: 4px;
            }
        }

        &__wrapper {
            margin: 32px 0px 4px;
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

            &--no-margin {
                margin: 0;
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
