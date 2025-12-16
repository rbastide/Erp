<script setup>
import { useRouter } from 'vue-router';
import AppHeader from './Header.vue';

const router = useRouter();

const handleRetour = () => {
  router.push('/login');
};

</script>

<template>
  <AppHeader title="Service d'aide" :aide="false"/>
  <main class="main-content">

    <div class="box">
      <div class="title">Téléphone :</div>
      <div class="text">Contactez un de nos informaticiens au : <br>
        +33 1 84 95 56 00 <br>
        LUNDI - SAMEDI : 9H - 19H CET
      </div>
    </div>


    <div class="box">
      <div class="title">Email :</div>
      <div class="text">svp-dsi@unilim.fr <br>
        Nous nous efforcons de vous répondre sous 48h
      </div>
    </div>
  </main>
  <footer>
    <div @click="handleRetour" class="btn-quitter">Quitter</div>
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
  gap: 30%;
  font-family: 'Roboto', sans-serif;
}

.title{
  font-size: 40px;
}

.text {
  font-size: 25px;
  margin-top: 10px;
  line-height: 40px;
}

.btn-quitter{
  width: 150px;
  padding: 0.8rem;
  border: none;
  text-align: center;
  border-radius: 4px;
  background-color: #B51621;
  color: #FFFFFF;
  font-size: 1rem;
  font-weight: bold;
  cursor: pointer;
  transition: background-color 0.2s ease;
  position: relative;
  margin: 5% auto;
  font-family: 'Roboto', sans-serif;
}

.btn-quitter:hover{
  background: #999999;
  transform: translateY(-4px);
  cursor: pointer;
}
</style>