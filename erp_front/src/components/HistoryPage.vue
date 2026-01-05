<script setup>
import { useRouter } from 'vue-router';
import AppHeader from './Header.vue';

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

const handleShow = () => {
  router.push('/ressource-sheet-history');
};

</script>

<template>
  <AppHeader title="Bonjour, " inline="Prénom NOM"/>
  <div class="main-content">
    <p class="description">Voici l'historique des fiches de ressource : </p>
    <div class="version-list-container">
      <ul class="version-list">
        <li @click="handleShow" class="version-item">
          <span class="version-code">R1.01</span>
          <span class="version-date">30/10/2022</span>
        </li>
        <li @click="handleShow" class="version-item">
          <span class="version-code">R1.02</span>
          <span class="version-date">15/11/2019</span>
        </li>
        <li @click="handleShow" class="version-item">
          <span class="version-code">R1.03</span>
          <span class="version-date">01/01/2018</span>
        </li>
        <li @click="handleShow" class="version-item">
          <span class="version-code">R2.01</span>
          <span class="version-date">05/08/2017</span>
        </li>
        <li @click="handleShow" class="version-item">
          <span class="version-code">R2.02</span>
          <span class="version-date">06/06/2017</span>
        </li>
        <li @click="handleShow" class="version-item">
          <span class="version-code">R2.04</span>
          <span class="version-date">08/05/2017</span>
        </li>
        <li @click="handleShow" class="version-item">
          <span class="version-code">R2.05</span>
          <span class="version-date">01/12/2016</span>
        </li>
        <li @click="handleShow" class="version-item">
          <span class="version-code">R3.02</span>
          <span class="version-date">15/05/2016</span>
        </li>
        <li @click="handleShow" class="version-item">
          <span class="version-code">R3.03</span>
          <span class="version-date">18/04/2016</span>
        </li>
        <li @click="handleShow" class="version-item last-item">
          <span class="version-code">R4.01</span>
          <span class="version-date">14/02/2016</span>
        </li>
        <li @click="handleRetour" class="btn-quitter">Quitter</li>
      </ul>
    </div>
  </div>
</template>

<style scoped>
.main-content {
  display: flex;
  width: 90%;
  margin: 254px auto auto;
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

.version-item.last-item {
  background-color: #dcdcdc;
  margin-bottom: 0;
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
  box-shadow: 0 4px 4px rgba(0, 0, 0, 0.25);
  border-radius: 6px;
  min-height: 80px;
  z-index: 2;
}
.version-item:hover {
  background: #999999;
  transform: translateY(-4px);
  cursor: pointer;
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