<script setup lang="ts">
import { useRouter } from 'vue-router';
import AppHeader from '../App/Header.vue';
import {ref} from "vue";

const router = useRouter();
const userRole = ref('');

const handleBack = () => {
  if (userRole.value === 'admin') {
    router.push('/home-admin');
  } else {
    router.push('/home');
  }
};

</script>

<template>
  <AppHeader title="Information"/>
  <main class="main-content">
    <p class="text">Votre fiche ressource a bien été sauvegardée !</p>
  </main>
  <footer>
    <div @click="handleBack" class="quit-btn">Retour au menu</div>
  </footer>
</template>

<style scoped>
.main-content{
  display: flex;
  width: 90%;
  margin: 254px auto auto;
  justify-content: center;
  align-items: center;
  font-family: 'Roboto', sans-serif;
}

.text{
  text-align: center;
  margin-top: 5%;
  color: green;
  font-size: 40px;
}
</style>