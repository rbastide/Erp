<script setup lang="ts">
import { useRouter } from 'vue-router';
import AppHeader from '../App/Header.vue';

const router = useRouter();

const handleRetour = () => {
  router.push('/home-admin');
};

const handleAide = () => {
  router.push('/aide');
};

const handleDeconnexion = () => {
  router.push('/deconnexion');
};
</script>

<template>
  <AppHeader title="Information"/>
  <main class="main-content">
    <p class="text">Les modifications ont bien été sauvegardées !</p>
  </main>
  <footer>
    <div @click="handleRetour" class="btn-quitter">Retour au menu</div>
  </footer>
</template>

<style scoped>
.main-content{
  display: flex;
  width: 90%;
  margin: auto;
  margin-top: 254px;
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

/*Footer*/
.btn-quitter{
  width: 150px;
  padding: 0.8rem; /* 13px */
  border: none;
  text-align: center;
  border-radius: 4px;
  background-color: #B51621;
  color: #FFFFFF;
  font-size: 1rem; /* 16px */
  font-weight: bold;
  font-family: 'Roboto', sans-serif;
  cursor: pointer;
  transition: background-color 0.2s ease;
  position: relative;
  margin: 5% auto;
}

.btn-quitter:hover{
  background: #999999;
  transform: translateY(-4px);
  cursor: pointer;
}
</style>