<script setup>
  import { useRouter } from 'vue-router';
  import AppHeader from './Header.vue';

  const router = useRouter();

  const handleRemplir = () => {
    router.push('/mccc-ressource');
  };

  const handleAfficher = () => {
    router.push('/history-admin');
  };

  const handleInscrire = () => {
    router.push('/new-user');
  };

  const handleAide = () => {
    router.push('/aide');
  };

  const handleDeconnexion = () => {
    router.push('/deconnexion');
  };

  const handleCompetenceCreating = () => {
    router.push('/competence-creating');
  };

  const handleResources = () => {
    router.push('/resources-gestion');
  }

</script>

<template>
  <AppHeader title="Bonjour, " inline="ADMIN"/>
  <div class="main-content">
    <div class="btn-container">
      <div @click="handleRemplir" class="btn"><p>Remplir les MCCC</p></div>
      <div @click="handleAfficher" class="btn"><p>Afficher les fiches ressource</p></div>
      <div @click="handleInscrire" class="btn"><p>Ajouter des utilisateurs</p></div>
      <div @click="handleCompetenceCreating" class="btn"><p>Ajouter les compétences</p></div>
      <div @click="handleResources" class="btn"><p>Gestion des ressources</p></div>
    </div>
  </div>
</template>

<style scoped>
.main-content{
  display: flex;
  width: 90%;
  margin: 254px auto auto;
  justify-content: center;
  align-items: center;
}

.btn-container{
  display: grid;
  grid-template-columns: 1fr 1fr;
  grid-template-rows: auto auto;
  width: fit-content;
  margin-left: auto;
  margin-right: auto;
  justify-content: center;
  align-items: center;
}

.btn {
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
  margin: 0 50px 100px;
}

.btn:hover {
  background: #999999;
  transform: translateY(-4px);
  cursor: pointer;
}

.btn p {
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

