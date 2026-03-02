<script setup>
import { onMounted } from 'vue';
import './assets/css/darkMode.css';

onMounted(() => {
  const savedTheme = localStorage.getItem('user_theme');
  if (savedTheme === 'dark') {
    document.body.classList.add('dark-mode');
  } else {
    document.body.classList.remove('dark-mode');
  }
});
</script>

<template>
  <router-view />
</template>