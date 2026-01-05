<template>
  <main class="app-content">
    <router-view />
  </main>

  <AppFooter />
</template>

<script>
import AppFooter from './components/App/Footer.vue';

export default {
  components: {
    AppFooter
  }
};
</script>

<style>

#app {
  display: flex;
  flex-direction: column;
  min-height: 100vh;
}

.app-content {
  flex-grow: 1;
}
</style>