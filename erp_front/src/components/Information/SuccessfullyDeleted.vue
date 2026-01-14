<script setup lang="ts">
import {useRouter} from 'vue-router';
import AppHeader from '../App/Header.vue';

const router = useRouter();

const handleBack = () => {
  router.back();
};

const handleOtherSheet = () => {
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
      <div @click="handleBack" class="quit-btn">Retour au menu</div>
      <div @click="handleOtherSheet" class="quit-btn">Supprimer une nouvelle fiche</div>
    </div>
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

/*Footer*/

.btn-container{
  display: flex;
  justify-content: center;
  align-items: center;
}

.quit-btn{
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

.quit-btn:hover, .back-btn:hover{
  background: #999999;
  transform: translateY(-4px);
  cursor: pointer;
}
</style>