<script setup lang="ts">
import { useRouter } from 'vue-router';

const router = useRouter();

const handleAide = () => {
  router.push('/aide');
};

const handleDeconnexion = () => {
  router.push('/deconnexion');
};

const handleValider = () => {
  router.push('/modif-saved');
};

</script>

<template>
  <header class="page-header">
    <div class="container-nom">
      <img src="../assets/uploads/Logo_unilim.png" alt="Logo Unilim"><p>Récapitulatif</p>
    </div>
    <div @click="handleAide" class="aide">Service d'aide</div>
    <div @click="handleDeconnexion" class="quitter">
      <svg width="48" height="48" viewBox="0 0 48 48" fill="none" xmlns="http://www.w3.org/2000/svg">
        <path d="M18 42H10C8.93913 42 7.92172 41.5786 7.17157 40.8284C6.42143 40.0783 6 39.0609 6 38V10C6 8.93913 6.42143 7.92172 7.17157 7.17157C7.92172 6.42143 8.93913 6 10 6H18M32 34L42 24M42 24L32 14M42 24H18" stroke="white" stroke-width="4" stroke-linecap="round" stroke-linejoin="round"/>
      </svg>
    </div>
  </header>
  <main class = "main-content">
    <div class = "summary">
      Récapitulatif de la ressource : RX.XX
    </div>
    <div class = "hour-summary">
      <div class = "title">
        Heures :
      </div>
      <div class = "top-grid">
        <div class = "type-of-hour">
          <p class = "title-hour">CM</p>
          <p class="grey-square" type="number">4H </p>
        </div>
        <div class = "type-of-hour">
          <p class = "title-hour">TD</p>
          <p class="grey-square" type="number">5H </p>
        </div>
        <div class = "type-of-hour">
          <p class = "title-hour">DS</p>
          <p class="grey-square" type="number">7H </p>
        </div>
      </div>
      <div class= "bottom-grid">
        <div class= "type-of-hour">
          <p class= "title-hour">TP</p>
          <p class="grey-square" type="number">5H </p>
        </div>
        <div class= "type-of-hour">
          <p class= "title-hour">DS TP</p>
          <p class="grey-square" type="number">3H </p>
        </div>
      </div>
      <div class= "total"><p>Total : <span id="tot">0</span></p></div>
    </div>
    <div class = "sae-summary">
      <p class = "title-centered">
        SAE(s) concernées :
      </p>
      <p class="grey-square">SX.XX </p>
      <p class = "title-centered">
        Compétence(s) concernée(s) :
      </p>
      <p class="grey-square">UE1.1</p>
      <p class = "title-centered">
        Version fiche ressource :
      </p>
      <p class="grey-square">V1</p>
    </div>
    <p class="title-centered" id="comp">Compétences / Objectifs</p>
    <div class="big-square">

    </div>
    <div class= "title"><p>Date de création : <span id="creation">13/04/2025</span></p></div>
    <div class= "title"><p>Date de modification : <span id="creation">18/04/2025</span></p></div>
    <div @click="handleValider" class="btn-sys">Valider</div>
  </main>
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
  align-items: center;
}
.summary {
  width: 100%;
  height: 66px;
  margin-top: 20px;
  font-family: 'Roboto', sans-serif;
  font-style: normal;
  font-weight: 400;
  font-size: 32px;
  line-height: 38px;
  text-align: center;

  color: #E92533;
}

/* Hour */
.title{
  width: 100%;
  height: 66px;
  margin-top: 20px;
  font-family: 'Roboto', sans-serif;
  font-style: normal;
  font-weight: 400;
  font-size: 32px;
  line-height: 38px;
  text-align: left;
  margin-left: 100px;

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

/* SAE */
.sae-summary{
  text-align: center;
}
.title-centered{
  width: 100%;
  height: 66px;
  margin-top: 20px;
  font-family: 'Roboto', sans-serif;
  font-style: normal;
  font-weight: 400;
  font-size: 32px;
  line-height: 38px;
  text-align: left;
  margin-left: 100px;

  color: #E92533;

  text-align: center;
  margin: 0;
}

.sae-summary p{
  display: inline-block;
  margin-bottom: 40px;
}


/* Compétences / Objectifs */
#comp{
  margin-top: 40px;
}

.big-square{
  border: black 1px solid;
}


/* Date de création */
#creation{
  color: black;
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
  margin : auto;
  margin-bottom: 30px;
  margin-top: 40px;
}

</style>