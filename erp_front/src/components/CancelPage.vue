<script setup>
import { useRouter } from 'vue-router';

const router = useRouter();

const handleAide = () => {
  router.push('/aide');
};

const handleNon = () => {
  router.push('/home'); // TODO temporaire, je ne sais pas comment faire pour le moment
}

const handleOui = () => {
  router.push('/home'); // TODO temporaire, je ne sais pas comment faire pour le moment
}

const handleDeconnexion = () => {
  router.push('/deconnexion');
}
</script>

<template>
  <header class="page-header">
    <div class="container-nom">
      <img src="../assets/uploads/Logo_unilim.png" alt="Logo Unilim"><p>Annulation à<br>confirmer</p>
    </div>
    <div @click="handleAide" class="aide">Service d'aide</div>
    <div @click="handleDeconnexion" class="quitter">
      <svg width="48" height="48" viewBox="0 0 48 48" xmlns="http://www.w3.org/2000/svg">
        <path d="M18 42H10C8.93913 42 7.92172 41.5786 7.17157 40.8284C6.42143 40.0783 6 39.0609 6 38V10C6 8.93913 6.42143 7.92172 7.17157 7.17157C7.92172 6.42143 8.93913 6 10 6H18M32 34L42 24M42 24L32 14M42 24H18" stroke="white" stroke-width="4" stroke-linecap="round" stroke-linejoin="round"/>
      </svg>
    </div>
  </header>
  <div class="main-content">
    <p>Êtes vous sûr de vouloir annuler ?</p>
    <div class="container-btn">
      <div @click="handleOui" class="btn-oui">Oui</div>
      <div @click="handleNon" class="btn-non">Non</div>
    </div>
  </div>

</template>
<style scoped>
.page-header {
  position: absolute;
  width: 100%;
  height: 172px;
  left: 0;
  top: 0;
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

/*Contenu de la page*/

.main-content{
  display: flex;
  flex-direction: column;
  width: 90%;
  margin: auto;
  margin-top: 254px;
  justify-content: center;
  align-items: center;
  font-family: 'Roboto', sans-serif;
}

.main-content p{
  height: 200px;
  font-weight: 600;
  font-size: 36px;
  line-height: 50px;
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1;
}

.container-btn{
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: center;
}

.btn-oui, .btn-non {
  box-sizing: border-box;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding: 0 16px;
  width: 360px;
  height: 200px;
  background: #D9D9D9;
  box-shadow: 0 4px 4px rgba(0, 0, 0, 0.25);
  border-radius: 15px;
  margin: 50px;
  /* Police */
  font-weight: 600;
  font-size: 36px;
  color: #E92533;
  z-index: 1;
}
.btn-non:hover,
.btn-oui:hover{
  cursor: pointer;
  background: #999999;
  transform: translateY(-4px);
}
</style>