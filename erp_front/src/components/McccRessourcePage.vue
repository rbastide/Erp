<script setup lang="ts">
import { useRouter } from 'vue-router';
import { mcccStore } from '@/services/mcccStore';
import AppHeader from './Header.vue';

const router = useRouter();

const handleRetour = () => {
  router.push('/home-admin');
};

const handleMccc = (code: string) => {
  mcccStore.loadMcccStore();
  mcccStore.resourceCode = code;
  mcccStore.registerMcccStore();
  router.push('/mccc-menu');
}
</script>

<template>
  <AppHeader title="Choix ressources"/>
  <main class="main-content">
    <div class="choisir-ressource">Veuillez choisir la ressource : </div>

    <div class="container-button">
      <div @click="handleMccc('R1.01')" class="push-button">
        <p class="text-button">R1.01</p>
      </div>
      <div @click="handleMccc('R1.02')" class="push-button">
        <p class="text-button">R1.02</p>
      </div>
      <div @click="handleMccc('R2.01')" class="push-button">
        <p class="text-button">R2.01</p>
      </div>
      <div @click="handleMccc('R2.02')" class="push-button">
        <p class="text-button">R2.02</p>
      </div>
    </div>

    <div @click="handleRetour" class="btn-quitter">Retour</div>
  </main>
</template>
<style scoped>
.main-content{
  position: relative;
  padding-top: 172px;
}
.choisir-ressource {
  width: 100%;
  height: 66px;
  margin-top: 20px;
  margin-left: 20px;


  font-family: 'Roboto', sans-serif;
  font-style: normal;
  font-weight: 400;
  font-size: 32px;
  line-height: 38px;

  color: #E92533;
}

.container-button {
  position: relative;
  display: grid;
  grid-template-columns: auto auto auto;
  padding: 10px;
}

.push-button {
  /* Push Button */
  box-sizing: border-box;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding: 0 16px;
  isolation: isolate;
  margin : 10px;
  font-size: 30px;
  text-align: center;
  background: #D9D9D9;
  border: 1px solid rgba(0, 0, 0, 0.25);
  box-shadow: 0 4px 4px rgba(0, 0, 0, 0.25);
  border-radius: 15px;
}
.push-button:hover {
  cursor: pointer;
  background: #999999;
}

.text-button {
  font-family: 'Roboto', sans-serif;
  font-weight: 510;
  font-size: 64px;
  text-align: center;
  color: #B51621;
  margin: 0;
}

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
  cursor: pointer;
  font-family: 'Roboto', sans-serif;
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