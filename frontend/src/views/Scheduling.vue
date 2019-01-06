<template>
    <section class="scheduling section">
        <!-- <div class="section__header">
           <h1>Scheduling</h1>
        </div> -->
        <div class="section__main">
            <section-block>
                <template slot="title">
                   <h2>Scheduler</h2>
                </template>
                
                <div class="calendar">
                    <kalendar :configuration="kalendarSettings" :appointments="appointments">
                        <template slot="first-date" slot-scope="{ date }">
                            <!-- Displays first date in week navigation header -->
                            {{ date | normalizeDate('MM-DD') }}
                        </template>
                        <template slot="last-date" slot-scope="{ date }">
                            <!-- Displays last date in week navigation header -->
                            {{ date | normalizeDate('MM-DD, YYYY') }}
                        </template>
                    </kalendar>
                </div>
           </section-block>
        </div>
        <div class="section__side">
            <section-block>
                <template slot="title">
                    <h2>Upcoming meetings</h2>
                </template>
                <ul>
                    <action-item 
                        v-for="meeting in meetings"
                        :key="meeting.id"
                        :item="meeting"
                        type="meeting"
                        list
                    >
                    </action-item>
                </ul>
            </section-block>
        </div>
    </section>
</template>

<script>
import _ from 'lodash'
import bus from '@/bus'
import store from '@/store'

import { Kalendar } from 'kalendar-vue'
import 'kalendar-vue/dist/KalendarVue.css'

import SectionBlock from '@/components/SectionBlock.vue'
import ActionMessage from '@/components/ActionMessage.vue'
import ActionButton from '@/components/ActionButton.vue'
import ActionInput from '@/components/ActionInput.vue'
import ActionItem from '@/components/ActionItem.vue'

import {required} from 'vuelidate/lib/validators'

const _fetchData = function (params, callback) {
    if (bus.$auth.check()) {
        store.dispatch('getMeetings').then(callback, error => {
            bus.pushMessage({
                type: 'error',
                content: 'Could not load meeting data.'
            })
        })
    } else {
        if (typeof callback == 'function') {
            callback()
        }
    }
}

export default {
    name: 'scheduling',
    components: {
        SectionBlock,
        ActionMessage,
        ActionButton,
        ActionInput,
        ActionItem,
        Kalendar
    },
    beforeRouteEnter: function (to, from, next) {
        _fetchData(to.params, next)
    },
    beforeRouteUpdate: function (to, from, next) {
        _fetchData(to.params, next)
    },
    computed: {
        appointments () {
            return _.map(this.meetings, item => {
                return {
                    title: item.name,
                    description: item.description,
                    from: new Date(item.startDate),
                    to: new Date(item.endDate),
                    date: new Date(item.startDate)
                }
            })
        },
        meetings () {
            return this.$store.state.meetings
        }
    },
    data () {
        return {
            kalendarSettings: {
                style: 'flat_design', // ['flat_design', 'material_design']
                view_type: 'Month', // ['Month', 'Day']
                split_value: 60, // Value % 60 === 0
                cell_height: 16, // !isNaN(Value)
                scrollToNow: false, // Boolean
                current_day: new Date(), // Valid date
            }
        }
    }
}
</script>

<style lang="scss">
    @import '~@/styles/globals';

    .scheduling {

    }

    .sticky-top {
        top: 64px;

        .days {
            margin-left: 0;
            padding-left: 50px;
        }
    }
</style>
