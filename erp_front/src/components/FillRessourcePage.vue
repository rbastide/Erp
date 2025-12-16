<script setup lang="ts">
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

</script>

<template>
  <AppHeader title="Fiche ressource" inline="RX.XX"/>
  <main class = "main-content">
    <div class="required_file">
      <p id="star">* </p>
      <p>champs obligatoires</p>
    </div>
    <div class = "title">
      Veuillez saisir vos heures de CM, TD et TP : *
    </div>
    <div class = "hour-summary">
      <div class = "listing-hours">
        <div class="grid">
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
        <div class="grid">
          <div class= "type-of-hour">
            <p class= "title-hour">TP</p>
            <p class="grey-square" type="number">5H </p>
          </div>
          <div class= "type-of-hour">
            <p class= "title-hour">DS/TP</p>
            <p class="grey-square" type="number">3H </p>
          </div>
          <div class= "total"><p>Total : <span id="tot">0</span></p></div>
        </div>
      </div>
    </div>
  </main>

</template>

<style scoped>
.required_file{
  text-align: right;
  margin-right: 20px;
  margin-top: 10px;
}
.required_file p{
  display: inline;
  font-family: 'Roboto', sans-serif;
}

#star{
  color: #E92533;
  font-size: 25px;
}

.title{
  width: 100%;
  height: 66px;
  margin-top: 40px;
  font-family: 'Roboto', sans-serif;
  font-style: normal;
  font-weight: 400;
  font-size: 32px;
  line-height: 38px;
  text-align: left;
  margin-left: 30px;

  color: #E92533;
}
.grid {
  position: relative;
  display: grid;
  grid-template-columns: auto auto auto;
  padding: 10px;
  justify-content: center;
}

.listing-hours{
  padding: 20px;
}

.type-of-hour {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 10px;
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


.total{
  margin-top: 50px;
  font-size: 50px;
  font-family: 'Roboto', sans-serif;
  margin-bottom: 80px;
  text-align: center;
  margin-left: 70px;
}


</style>