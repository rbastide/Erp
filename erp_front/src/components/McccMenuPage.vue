<script setup lang="ts">
import { useRouter } from 'vue-router';
import { mcccStore } from '@/services/mcccStore';

const router = useRouter();
mcccStore.loadMcccStore();
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

  <!-- Conteneur principal de RessourcePage -->
  <div class = "ressource-page" >

    <!-- Header de la page contenant la bande rouge -->
    <header class="page-header">
      <div class="container-nom">
        <img src="../assets/uploads/Logo_unilim.png">
        <p> {{ mcccStore.resourceCode }}</p>
      </div>
      <div @click="handleAide" class="aide">Service d'aide</div>
      <div @click="handleDeconnexion" class="quitter">
        <svg width="48" height="48" viewBox="0 0 48 48" fill="none" xmlns="http://www.w3.org/2000/svg">
          <path d="M18 42H10C8.93913 42 7.92172 41.5786 7.17157 40.8284C6.42143 40.0783 6 39.0609 6 38V10C6 8.93913 6.42143 7.92172 7.17157 7.17157C7.92172 6.42143 8.93913 6 10 6H18M32 34L42 24M42 24L32 14M42 24H18" stroke="white" stroke-width="4" stroke-linecap="round" stroke-linejoin="round"/>
        </svg>
      </div>
    </header>

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
  </div>
</template>

<style scoped>
/* header */
.page-header {
  position: relative;
  width: 100%;
  height: 172px;
  left: 0px;
  top: 0px;
  background: #B51621;
}

.container-nom img {
  position: absolute;
  width: 127px;
  height: 127px;
  left: 64px;
  top: 22.5px;
}

.container-nom p {

  position: absolute;
  width: 723px;
  height: 124px;
  left: 209px;
  top: 24px;

  font-family: 'Roboto', sans-serif;
  font-style: normal;
  font-weight: 900;
  font-size: 56px;
  line-height: 110%;

  display: flex;
  align-items: center;
  letter-spacing: -0.03em;

  color: #FFFFFF;
}

.quitter {
  position: absolute;
  width: 48px;
  height: 48px;
  right: 5%;
  top: 64px;
}
.quitter:hover{
  cursor: pointer;
}

.aide{
  position: absolute;
  width: 126px;
  height: 52px;
  right: 15%;
  top: 60px;
  font-family: 'Roboto', sans-serif;
  font-style: normal;
  font-weight: 500;
  font-size: 36px;
  line-height: 145%;
  display: flex;
  align-items: center;
  text-align: center;
  letter-spacing: -0.005em;
  text-transform: capitalize;
  color: #FFFFFF;
}
.aide:hover{
  cursor: pointer;
}


/* main */
.main-content{
  display: grid;
  grid-template-columns: 1fr 1fr;
  grid-template-rows: auto auto;
  width: fit-content;
  margin-left: auto;
  margin-right: auto;
  margin-top: 30px;
  justify-content: center;
  align-items: center;
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