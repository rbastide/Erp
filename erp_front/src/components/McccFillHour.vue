<script setup lang="ts">
import { computed } from 'vue';
import { useRouter } from 'vue-router';
import { mcccStore } from '@/services/mcccStore';


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
  <div class="ressource-page">
    <header class="page-header">
      <div class="container-nom">
        <img src="../assets/uploads/Logo_unilim.png" alt="Logo Unilim"><p>Bonjour, <br>ADMIN</p>
      </div>
      <div @click="handleAide" class="aide">Service d'aide</div>
      <div @click="handleDeconnexion" class="quitter">
        <svg width="48" height="48" viewBox="0 0 48 48" fill="none" xmlns="http://www.w3.org/2000/svg">
          <path d="M18 42H10C8.93913 42 7.92172 41.5786 7.17157 40.8284C6.42143 40.0783 6 39.0609 6 38V10C6 8.93913 6.42143 7.92172 7.17157 7.17157C7.92172 6.42143 8.93913 6 10 6H18M32 34L42 24M42 24L32 14M42 24H18" stroke="white" stroke-width="4" stroke-linecap="round" stroke-linejoin="round"/>
        </svg>
      </div>
    </header>

    <main class="main-content">
      <div class="choisir-ressource">
        Veuillez saisir les heures à remplir automatiquement :
      </div>

      <div class="top-grid">
        <div class="type-of-hour">
          <p class="title-hour">CM</p>
          <input class="grey-square" type="number" v-model="mcccStore.hoursCM" placeholder="0">
        </div>
        <div class="type-of-hour">
          <p class="title-hour">TD</p>
          <input class="grey-square" type="number" v-model="mcccStore.hoursTD" placeholder="0">
        </div>
        <div class="type-of-hour">
          <p class="title-hour">DS</p>
          <input class="grey-square" type="number" v-model="mcccStore.hoursDS" placeholder="0">
        </div>
      </div>

      <div class="bottom-grid">
        <div class="type-of-hour">
          <p class="title-hour">TP</p>
          <input class="grey-square" type="number" v-model="mcccStore.hoursTP" placeholder="0">
        </div>
        <div class="type-of-hour">
          <p class="title-hour">DS TP</p>
          <input class="grey-square" type="number" v-model="mcccStore.hoursDSTP" placeholder="0">
        </div>
      </div>

      <div class="total">
        <p>Total : <span id="tot">{{ totalHeures }}</span></p>
      </div>

      <div class="container-btn">
        <div @click="handleValider" class="btn-sys">Valider</div>
        <div @click="handleRetour" class="btn-sys">Annuler</div>
      </div>    </main>

  </div>
</template>

<style scoped>
/* Votre style reste 100% identique */
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