<script setup lang="ts">
import { useRouter } from 'vue-router';
import {mcccStore} from "@/services/mcccStore.js";
import AppHeader from './Header.vue';

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

const handleRetour = () => {
  router.push('/mccc-menu');
};

import { onMounted } from 'vue';

onMounted(() => {
  const buttons = document.querySelectorAll('.tab-button');
  const contents = document.querySelectorAll('.tab-content');

  mcccStore.loadMcccStore();

  buttons.forEach(button => {
    button.addEventListener('click', () => {
      const tabId = button.getAttribute('data-tab');

      buttons.forEach(btn => btn.classList.remove('active'));

      button.classList.add('active');

      contents.forEach(content => content.classList.remove('active'));

      document.querySelector(`#content-${tabId}`)?.classList.add('active');
    });
  });
});

</script>

<template>
  <AppHeader title="Récapitulatif"/>
  <main class = "main-content">
    <div class = "summary">
      Récapitulatif de la ressource : {{mcccStore.resourceCode}}
    </div>
    <div class="grid">
      <div class = "hour-summary">
        <div class = "title">
          Heures
        </div>
        <div class = "listing-hours">
          <div class = "type-of-hour" id="cm">
            <p class = "title-hour">CM</p>
            <p class="grey-square" type="number">{{mcccStore.hoursCM}} </p>
          </div>
          <div class = "type-of-hour">
            <p class = "title-hour">TD</p>
            <p class="grey-square" type="number">{{mcccStore.hoursTD}}</p>
          </div>
          <div class = "type-of-hour">
            <p class = "title-hour">DS</p>
            <p class="grey-square" type="number">{{mcccStore.hoursTD}}</p>
          </div>
          <div class= "type-of-hour">
            <p class= "title-hour">TP</p>
            <p class="grey-square" type="number">{{mcccStore.hoursTP}}</p>
          </div>
          <div class= "type-of-hour" id="ds_tp">
            <p class= "title-hour">DS/TP</p>
            <p class="grey-square" type="number">{{mcccStore.hoursDSTP}} </p>
          </div>
          <div class= "total"><p>Total : <span id="tot">{{mcccStore.hoursTotal}}</span></p></div>
        </div>
      </div>
      <div class = "sae-summary">
        <p class = "title-centered" >
          SAE(s) concernées :
        </p>
        <p class="grey-square" v-for="saeCode in mcccStore.saeCodes" :key="saeCode">
          {{ saeCode }}
        </p>
        <p class = "title-centered">
          Compétence(s) concernée(s) :
        </p>
        <p class="grey-square" id="comp_conc">UE1.1</p>
      </div>
    </div>

    <p class="title-centered" id="comp">Compétences / Objectifs</p>
    <div class="big-square">
      <div class="grid-split-in-two">
        <p class = "title">Intitulé de l'UE 1 : </p>
        <p class="grey-square" id="intitule">Ceci est l'intitulé de l'UE : c'est une description qui nous permet de voire le bon fonctionnement</p>
      </div>
      <div class="container">
        <div class="tabs">
          <button class="tab-button active" data-tab="1">1</button>
          <button class="tab-button" data-tab="2">2</button>
          <button class="tab-button" data-tab="3">3</button>
        </div>

        <div class="content-panel">
          <div id="content-1" class="tab-content active">
            <p class="title-label">Intitulé du niveau :</p>
            <p class="description">Concevoir et mettre en place une base de données à partir d'un cahier des charges client</p>

            <p class="title-label">Apprentissages critiques pour le niveau :</p>
            <p class="description">Mettre à jour une base de données relationnelles (en requêtes directes ou à travers une application)</p>
          </div>

          <div id="content-2" class="tab-content">
            <p class="title-label">Intitulé du niveau :</p>
            <p class="description">Établir des scénarios d'utilisation pour des tests d'acceptance.</p>

            <p class="title-label">Apprentissages critiques pour le niveau :</p>
            <p class="description">Rédiger des spécifications fonctionnelles détaillées.</p>
          </div>

          <div id="content-3" class="tab-content">
            <p class="title-label">Intitulé du niveau :</p>
            <p class="description">Organiser l'architecture d'une application web en Microservices.</p>

            <p class="title-label">Apprentissages critiques pour le niveau :</p>
            <p class="description">Créer des conteneurs Docker pour chaque Microservice.</p>
          </div>
        </div>
      </div>
    </div>
    <div class= "title"><p>Professeur(s) référent : <span id="creation">Mr Test</span></p></div>
    <div class= "title"><p>Version fiche ressource : <span id="creation">1</span></p></div>
    <div class= "title"><p>Date de création : <span id="creation">13/04/2025</span></p></div>
    <div class= "title"><p>Date de modification : <span id="creation">18/04/2025</span></p></div>
    <div class="container-btn">
      <div @click="handleValider" class="btn-sys">Valider</div>
      <div @click="handleRetour" class="btn-sys">Annuler</div>
    </div>
  </main>
</template>

<style>
.main-content{
  width: 90%;
  margin: auto;
  margin-top: 254px;
  justify-content: center;
  align-items: center;
  font-family: 'Roboto', sans-serif;
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

.title{
  width: 100%;
  height: 66px;
  margin-top: 20px;
  font-family: 'Roboto', sans-serif;
  font-style: normal;
  font-weight: 400;
  font-size: 32px;
  line-height: 38px;
  text-align: center;
  margin-left: 30px;

  color: #E92533;
}
.grid {
  position: relative;
  display: grid;
  grid-template-columns: 20% 30%;
  padding: 10px;
  justify-content: center;
  gap: 150px;
}

.type-of-hour{
  box-sizing: border-box;
  display: grid;
  grid-template-columns: auto auto;
  gap: 30px;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding: 0px 16px;
  isolation: isolate;
  margin-bottom: 40px;
}

.listing-hours{
  padding: 20px;
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

#cm{
  margin-left: -10px;
}

#ds_tp{
  margin-left: -50px;
}

.total{
  margin-top: 50px;
  font-size: 50px;
  font-family: 'Roboto', sans-serif;
  margin-bottom: 80px;
  text-align: center;
  margin-left: 70px;
}

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
  color: #E92533;
  text-align: center;
}

#sae_conc, #comp_conc, #vers_f{
  margin-bottom: 65px;
}

.sae-summary p{
  display: inline-block;
  margin-bottom: 20px;
}

#comp{
  margin-top: 40px;
}

.big-square{
  margin: auto;
  margin-bottom: 50px;
  padding: 20px;
  width: 1100px;
  border: black 3px solid;
}

.grid-split-in-two{
  margin-top: 30px;
  margin-bottom: 20px;
  display: grid;
  grid-template-columns: auto auto;
  justify-content: center;
  align-items: center;
  column-gap: 40px;
}

#intitule{
  width: 700px;
}

.container {
  max-width: 900px;
  margin: 20px auto;
  margin-top: 60px;
  font-family: Arial, sans-serif;
  display: flex;
  flex-direction: column;
}

.tabs {
  display: flex;
  margin-bottom: -1px;
}

.tab-button {
  padding: 15px 20px;
  font-size: 1.5em;
  font-weight: bold;
  text-align: center;
  cursor: pointer;
  border: none;
  outline: none;
  transition: background-color 0.3s, color 0.3s;
  flex-grow: 1;
  background-color: #e0e0e0;
  color: black;
}


.tab-button.active {
  background-color: #888;
  color: white;
  padding-bottom: 20px;
  padding-top: 20px;
}

.content-panel {
  background-color: #888;
  color: white;
  padding: 30px;
  min-height: 250px;
  border-radius: 0 5px 5px 5px;
}

.title-label {
  color: black;
  font-weight: bold;
  margin-top: 15px;
  margin-bottom: 5px;
  font-size: 1.3em;
}

.description {
  font-size: 1.1em;
  line-height: 1.5;
  margin-bottom: 20px;
}

.tab-content {
  display: none;
}

.tab-content.active {
  display: block;
}

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

.container-btn{
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: center;
}

.container-btn div:hover{
  background: #999999;
  transform: translateY(-4px);
  cursor: pointer;
}
</style>