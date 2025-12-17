<script setup lang="ts">
import { computed } from 'vue';
import { useRouter } from 'vue-router';
import { mcccStore } from '@/services/mcccStore';
import AppHeader from './Header.vue';


mcccStore.loadMcccStore();
const router = useRouter();

const totalHeures = computed(() => {
  return (mcccStore.hoursCM || 0) +
      (mcccStore.hoursTD || 0) +
      (mcccStore.hoursDS || 0) +
      (mcccStore.hoursTP || 0) +
      (mcccStore.hoursDSTP || 0);
});

const handleValider = () => {
  mcccStore.hoursTotal = totalHeures.value;
  mcccStore.registerMcccStore();
  router.push('/mccc-menu');
};

const handleRetour = () => {
  router.back();
};

const handleAide = () => {
  router.push('/aide');
};

const handleDeconnexion = () => {
  router.push('/deconnexion');
};

</script>

<template>
  <AppHeader title="Bonjour, " inline="ADMIN"/>
  <main class = "main-content">
    <div class = "choisir-ressource">
      Veuillez saisir les heures à remplir automatiquement :
    </div>
    <div class = "top-grid">
      <div class = "type-of-hour">
        <p class = "title-hour">CM</p>
        <input class="grey-square" type="number" v-model.number="mcccStore.hoursCM">
      </div>
      <div class = "type-of-hour">
        <p class = "title-hour">TD</p>
        <input class="grey-square" type="number" v-model.number="mcccStore.hoursTD">
      </div>
      <div class = "type-of-hour">
        <p class = "title-hour">DS</p>
        <input class="grey-square" type="number" v-model.number="mcccStore.hoursDS">
      </div>
    </div>
    <div class= "bottom-grid">
      <div class= "type-of-hour">
        <p class= "title-hour">TP</p>
        <input class="grey-square" type="number" v-model.number="mcccStore.hoursTP">
      </div>
      <div class= "type-of-hour">
        <p class= "title-hour">DS TP</p>
        <input class="grey-square" type="number" v-model.number="mcccStore.hoursDSTP">
      </div>
    </div>
    <div class= "total"><p>Total : <span id="tot">{{ totalHeures }}</span>h</p></div>

    <div class="container-btn">
      <div @click="handleValider" class="btn-sys">Valider</div>
      <div @click="handleRetour" class="btn-sys">Annuler</div>
    </div>
  </main>
</template>

<style scoped>
.main-content{
  position: relative;
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

.top-grid {
  position: relative;
  display: grid;
  grid-template-columns: auto auto auto;
  padding: 10px;
}

.type-of-hour{
  box-sizing: border-box;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding: 0px 16px;
  isolation: isolate;
}

.title-hour{
  font-size: 30px;
  margin-bottom: 10px;
  font-family: 'Roboto', sans-serif;
}

.grey-square{
  box-sizing: border-box;
  width: 200px;
  background: #D9D9D9;
  padding: 30px 60px;
  border: 1px solid rgba(0, 0, 0, 0.25);
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  border-radius: 15px;
  font-size: 30px;
  text-align: center;
}

.bottom-grid {
  margin-top: 40px;
  position: relative;
  display: grid;
  grid-template-columns: auto auto;
  padding: 10px;
}

.total{
  margin-top: 50px;
  margin-left: 40%;
  font-size: 50px;
  font-family: 'Roboto', sans-serif;
  margin-bottom: 80px;
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