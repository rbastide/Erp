<script setup lang="ts">
import { useRouter } from 'vue-router';
import AppHeader from './Header.vue';

const router = useRouter();

const handleRetour = () => {
  router.push('/mccc-ressource');
};

const handleAide = () => {
  router.push('/aide');
};

const handleDeconnexion = () => {
  router.push('/deconnexion');
};

const handleHours = () => {
  router.push('/mccc-hours');
};

const handleTeachers = () => {
  router.push('/mccc-teachers');
}

const handleValider = () => {
  router.push('/mccc-summary');
};

const handleSae = () => {
  router.push('/mccc-sae');
};

const handleCompetences = () => {
  router.push('/mccc-competences');
}

</script>

<template>
  <AppHeader title="RX.XX"/>
  <main class= "main-content">
    <div @click="handleHours" class ="heure">
      <p class= "text-button">Heures</p>
    </div>
    <div @click="handleCompetences" class ="competence">
      <p class= "text-button">Objectif / Compétences</p>
    </div>
    <div @click="handleSae" class ="sae">
      <p class= "text-button">SAE</p>
    </div>
    <div @click="handleTeachers" class ="referent">
      <p class= "text-button">Référent(s)</p>
    </div>
  </main>
  <div class="container-btn">
    <div @click="handleValider" class="btn-sys">Valider</div>
    <div @click="handleRetour" class="btn-sys">Retour</div>
  </div>
</template>

<style scoped>
.main-content{
  display: grid;
  grid-template-columns: 1fr 1fr;
  grid-template-rows: auto auto;
  width: fit-content;
  margin: auto;
  margin-top: 254px;
  justify-content: center;
  align-items: center;
  font-family: 'Roboto', sans-serif;
}

.heure,
.competence,
.referent,
.sae{
  box-sizing: border-box;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding: 0 16px;
  width: 450px;
  height: 200px;
  background: #D9D9D9;
  box-shadow: 0 4px 4px rgba(0, 0, 0, 0.25);
  border-radius: 15px;
  margin: 50px;
}

.heure:hover,
.competence:hover,
.referent:hover,
.sae:hover {
  background: #999999;
  transform: translateY(-4px);
  cursor: pointer;
}

.heure p,
.competence p,
.referent p,
.sae p {
  width: 450px;
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

.container-btn{
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: center;
}

.btn-sys{
  width: 150px;
  padding: 13px;
  border: none;
  text-align: center;
  border-radius: 4px;
  background-color: #B51621;
  color: #FFFFFF;
  font-size: 1rem; /* 16px */
  font-weight: bold;
  cursor: pointer;
  font-family: 'Roboto', sans-serif;
  transition: background-color 0.2s ease;
  position: relative;
  margin : 1%;
}

.btn-sys:hover{
  background: #999999;
  transform: translateY(-4px);
  cursor: pointer;
}

</style>