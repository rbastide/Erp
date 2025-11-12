<script setup>

import { useRouter } from 'vue-router';

const router = useRouter();

const handleRetour = () => {
  router.push('/home');
};

const handleAide = () => {
  router.push('/aide');
};

const handleDeconnexion = () => {
  router.push('/deconnexion');
};
</script>

<template>
  <header class="page-header">
    <div class="container-nom">
      <img src="../assets/uploads/Logo_unilim.png" alt="Logo Unilim"><p>Bonjour, <br>NOM Prénom</p>
    </div>
    <div @click="handleAide" class="aide">Service d'aide</div>
    <div @click="handleDeconnexion" class="quitter">
      <svg width="48" height="48" viewBox="0 0 48 48" fill="none" xmlns="http://www.w3.org/2000/svg">
        <path d="M18 42H10C8.93913 42 7.92172 41.5786 7.17157 40.8284C6.42143 40.0783 6 39.0609 6 38V10C6 8.93913 6.42143 7.92172 7.17157 7.17157C7.92172 6.42143 8.93913 6 10 6H18M32 34L42 24M42 24L32 14M42 24H18" stroke="white" stroke-width="4" stroke-linecap="round" stroke-linejoin="round"/>
      </svg>
    </div>
  </header>
  <div class="main-content">
    <p class="description">Voici l'historique des fiches de ressource : </p>
    <div class="version-list-container">
      <ul class="version-list">
        <li class="version-item">
          <span class="version-code">R1.01</span>
          <span class="version-date">30/10/2022</span>
        </li>
        <li class="version-item">
          <span class="version-code">R1.02</span>
          <span class="version-date">15/11/2019</span>
        </li>
        <li class="version-item">
          <span class="version-code">R1.03</span>
          <span class="version-date">01/01/2018</span>
        </li>
        <li class="version-item">
          <span class="version-code">R2.01</span>
          <span class="version-date">05/08/2017</span>
        </li>
        <li class="version-item">
          <span class="version-code">R2.02</span>
          <span class="version-date">06/06/2017</span>
        </li>
        <li class="version-item">
          <span class="version-code">R2.04</span>
          <span class="version-date">08/05/2017</span>
        </li>
        <li class="version-item">
          <span class="version-code">R2.05</span>
          <span class="version-date">01/12/2016</span>
        </li>
        <li class="version-item">
          <span class="version-code">R3.02</span>
          <span class="version-date">15/05/2016</span>
        </li>
        <li class="version-item">
          <span class="version-code">R3.03</span>
          <span class="version-date">18/04/2016</span>
        </li>
        <li class="version-item last-item">
          <span class="version-code">R4.01</span>
          <span class="version-date">14/02/2016</span>
        </li>
        <div @click="handleRetour" class="btn-quitter">Quitter</div>
      </ul>
    </div>
  </div>
</template>

<style scoped>

.page-header {
  position: absolute;
  width: 100%; /* Modifié de 1280px à 100% pour prendre toute la largeur */
  height: 172px;
  left: 0px;
  top: 0px;
  background: #B51621;
  /* J'ajoute box-sizing pour éviter des problèmes de padding/border qui affectent la largeur totale */
  box-sizing: border-box;
}

.container-nom img {
  position: absolute;
  width: 127px;
  height: 127px;
  left: 64px;
  top: 22.5px;
}
.container-nom p{
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

/* Contenue de la page */

.main-content {
  display: flex;
  width: 90%;
  margin: auto;
  margin-top: 254px;
  justify-content: center;
  align-items: center;
}

.description{
  position: absolute;
  width: 672px;
  height: 66px;
  left: 38px;
  top: 196px;
  font-family: 'Roboto', sans-serif;
  font-style: normal;
  font-weight: 400;
  font-size: 32px;
  line-height: 38px;
  color: #E92533;
  margin-bottom: 20px;
}

.version-list-container {
  display: flex;
  align-items: center;
  justify-content: center;
  margin: auto;
  width: 500px;
  font-family: 'Roboto', sans-serif;
  padding: 10px;
}

.version-list {
  width: 100%;
  list-style: none;
  padding: 0;
  margin: 0;
}

.version-item {
  display: flex;
  align-items: center;
  justify-content: space-between;
  flex-direction: row;
  padding: 10px 16px;
  isolation: isolate;
  margin-bottom: 15px;
  background: #D9D9D9;
  border: 1px solid rgba(0, 0, 0, 0.25);
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  border-radius: 6px;
  min-height: 80px;
}

.version-item.last-item {
  background-color: #dcdcdc;
  margin-bottom: 0;
}

.version-code, .version-date {
  width: auto;
  height: auto;
  font-family: 'Roboto', sans-serif;
  font-style: normal;
  font-weight: 510;
  font-size: 40px;
  line-height: normal;
  color: #B51621;
  padding: 0 10px;
}

.version-code {
  text-align: left;
}

.version-date {
  text-align: right;
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