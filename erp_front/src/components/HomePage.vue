<script setup>
import { useRouter } from 'vue-router';
import AppHeader from './Header.vue';

const router = useRouter();

const handleRemplir = () => {
  router.push('/ressource');
};

const handleAfficher = () => {
  router.push('/history');
};

</script>

<template>
  <AppHeader title="Bonjour," inline="Prénom NOM" />


  <div class="main-content">
    <div @click="handleRemplir" class="remplir"><p>Remplir une fiche ressource</p></div>
    <div @click="handleAfficher" class="afficher"><p>Afficher les fiches ressource</p></div>
  </div>
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

.remplir,
.afficher {
  box-sizing: border-box;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding: 0 16px;
  width: 360px;
  height: 200px;
  background: #D9D9D9;
  box-shadow: 0 4px 4px rgba(0, 0, 0, 0.25);
  border-radius: 15px;
  margin: 50px;
}

.remplir:hover,
.afficher:hover{
  background: #999999;
  transform: translateY(-4px);
  cursor: pointer;
}

.remplir, .afficher p {
  width: 328px;
  height: 200px;
  font-family: 'Roboto', sans-serif;
  font-style: normal;
  font-weight: 400;
  font-size: 36px;
  line-height: 50px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #E92533;
  z-index: 1;
}
</style>

