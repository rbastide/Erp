<script setup lang="ts">
import { useRouter } from 'vue-router';
import AppHeader from '../App/Header.vue';


const router = useRouter();

const handleRetour = () => {
  router.back();
};

const handleNewUser = () => {
  router.push('/new-user');
}
</script>

<template>
  <AppHeader title="Information"/>
  <main class="main-content">
    <p class="text">Votre ajout d’utilisateur a bien été effectué !</p>
  </main>
  <footer>
    <div class="btn-container">
      <div @click="handleRetour" class="btn-quitter">Retour au menu</div>
      <div @click="handleNewUser" class="btn-new-user">Ajouter un autre utilisateur</div>
    </div>
  </footer>
</template>

<style scoped>
.main-content{
  margin-top: 24px;
  position: relative;
}

.text{
  text-align: center;
  margin-top: 5%;
  color: green;
  font-size: 40px;
  font-family: 'Roboto', sans-serif;
}

.btn-container{
  display: flex;
  justify-content: center;
  align-items: center;
}

.btn-quitter, .btn-new-user{
  width: fit-content;
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

.btn-quitter:hover, .btn-new-user:hover{
  background: #999999;
  transform: translateY(-4px);
  cursor: pointer;
}
</style>