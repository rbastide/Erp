<script setup>
import { useRouter } from 'vue-router';

const router = useRouter();

const handleRetour = () => {
  router.push('/login');
};

const handleDeconnexion = () => {
  router.push('/deconnexion');
};

</script>

<template>
  <header class="page-header">
    <div class="container-nom">
      <img src="../assets/uploads/Logo_unilim.png"><p>Service d'aide</p>
    </div>
    <div @click="handleDeconnexion" class="quitter">
      <svg width="48" height="48" viewBox="0 0 48 48" fill="none" xmlns="http://www.w3.org/2000/svg">
        <path d="M18 42H10C8.93913 42 7.92172 41.5786 7.17157 40.8284C6.42143 40.0783 6 39.0609 6 38V10C6 8.93913 6.42143 7.92172 7.17157 7.17157C7.92172 6.42143 8.93913 6 10 6H18M32 34L42 24M42 24L32 14M42 24H18" stroke="white" stroke-width="4" stroke-linecap="round" stroke-linejoin="round"/>
      </svg>
    </div>
  </header>
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

/*main*/
.main-content {
  display: flex;
  justify-content: center;
  gap: 30%;
  margin-top: 2%;
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
  box-sizing: border-box;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding: 0px 16px;
  width: 360px;
  height: 100px;
  background: #D9D9D9;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  border-radius: 15px;
  margin: 20px auto;
  font-family: 'Roboto', sans-serif;
  font-style: normal;
  font-weight: 510;
  font-size: 40px;
  color: #B51621;
  position: relative;
  margin-top: 5%;
}

.btn-quitter:hover{
  background: #999999;
  transform: translateY(-4px);
  cursor: pointer;
}
</style>