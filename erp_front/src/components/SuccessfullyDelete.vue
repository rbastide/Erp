<script setup lang="ts">
import { useRouter } from 'vue-router';
import AppHeader from './Header.vue';

const router = useRouter();

const handleRetour = () => {
  router.push('/home-admin');
};

const handleAutreFiche = () => {
  router.push('/history-admin');
};

</script>

<template>
  <AppHeader title="Information" />
  <main class="main-content">
    <p class="text">La fiche ressource a bien été supprimée !</p>
  </main>
  <footer>
    <div class="btn-container">
      <div @click="handleRetour" class="btn-quitter">Retour au menu</div>
      <div @click="handleAutreFiche" class="btn-quitter">Supprimer une nouvelle fiche</div>
    </div>
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

.btn-container{
  display: flex;
  justify-content: center;
  align-items: center;
}

.btn-quitter, .btn-retour{
  width: fit-content;
  padding: 0.8rem; /* 13px */
  border: none;
  text-align: center;
  border-radius: 4px;
  background-color: #B51621;
  color: #FFFFFF;
  font-size: 1rem;
  font-weight: bold;
  font-family: 'Roboto', sans-serif;
  cursor: pointer;
  transition: background-color 0.2s ease;
  position: relative;
  margin: 5% auto;
}

.btn-quitter:hover, .btn-retour:hover{
  background: #999999;
  transform: translateY(-4px);
  cursor: pointer;
}
</style>