<template>
    <div id="app">
        <navbar>
            
        </navbar>
        <router-view/>
        <footer>
            Made by Adrian Hernik and Marcin Krykowski
        </footer>
    </div>
</template>

<script>
import Navbar from '@/components/Navbar.vue'

export default {
    name: 'app',
    components: {
       Navbar
    }  
}
</script>

<style lang="scss">
@import '~@/styles/globals';

* {
    box-sizing: border-box;
}

body {
    font-family: 'Work Sans', 'Avenir', Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;

    background: $background-body;
    color: $foreground-body;

    margin: 0;
    padding: 0;

    padding-bottom: 64px;
}

footer {
    position: absolute;
    bottom: 0;
    left: 0;
    right: 0;
    height: 64px;
    line-height: 64px;
    text-align: center;
    padding: 0 20px;
    color: $foreground-footer;
}

a,
a:active,
a:visited {
    color: $foreground-link;
    text-decoration: none;

    transition: 150ms color ease-in;
}

a:hover {
    color: $foreground-link--hover;
}

h1 {
    color: $foreground-header;
    font: $font-header;
}

h2 {
    color: $foreground-header--sub;
    font: $font-header--sub;
}

.highlight {
    background-color: $background-highlight;
    margin: 24px 0;
    padding: 24px 32px;
}

</style>
