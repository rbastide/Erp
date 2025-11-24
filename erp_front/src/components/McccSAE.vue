<script setup>
import { useRouter } from 'vue-router';

const router = useRouter();

const handleValider = () => {
  router.push('/mccc-menu');
};

const handleAide = () => {
  router.push('/aide');
};

const handleDeconnexion = () => {
  router.push('/deconnexion');
};
</script>
<template>
  <!-- Conteneur principal de RessourcePage -->
  <div class = "ressource-page" >

    <!-- Header de la page contenant la bande rouge -->
    <header class="page-header">
      <div class="container-nom">
        <img src="../assets/uploads/Logo_unilim.png" alt="Logo Unilim"><p>SAÉ/Compétences <br> pour RX.XX</p>
      </div>
      <div @click="handleAide" class="aide">Service d'aide</div>
      <div @click="handleDeconnexion" class="quitter">
        <svg width="48" height="48" viewBox="0 0 48 48" fill="none" xmlns="http://www.w3.org/2000/svg">
          <path d="M18 42H10C8.93913 42 7.92172 41.5786 7.17157 40.8284C6.42143 40.0783 6 39.0609 6 38V10C6 8.93913 6.42143 7.92172 7.17157 7.17157C7.92172 6.42143 8.93913 6 10 6H18M32 34L42 24M42 24L32 14M42 24H18" stroke="white" stroke-width="4" stroke-linecap="round" stroke-linejoin="round"/>
        </svg>
      </div>
    </header>

    <main>
      <div class="sae">
        <p class="titre">Veuillez saisir la/les SAÉ(s) concernée(s) :</p>
        <div class = "grey-square" contenteditable="true"></div>

      </div>
      <div class="competence">
        <p class="titre">Veuillez saisir la/les compétence(s) concernée(s) :</p>
        <div class = "grey-square" contenteditable="true"></div>
      </div>

      <div @click="handleValider" class="btn-valider">Valider</div>
    </main>

  </div>

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
  height: 120px;
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

/*Main*/
.titre{
  width: 100%;
  height: 66px;
  margin-top: 20px;
  margin-left: 8%;


  font-family: 'Roboto', sans-serif;
  font-style: normal;
  font-weight: 400;
  font-size: 32px;
  line-height: 38px;

  color: #E92533;
}
.grey-square{
  box-sizing: border-box;
  width: 400px;
  background: #D9D9D9;
  padding: 30px 60px;
  border: 1px solid rgba(0, 0, 0, 0.25);
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  border-radius: 15px;
  font-size: 30px;
  text-align: center;
  margin-left: auto;
  margin-right: auto;
  margin-top: 20px;
}
.btn-valider{
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
  font-family: 'Roboto', sans-serif;
  transition: background-color 0.2s ease;
  position: relative;
  margin: 5% auto;

}

.btn-valider:hover{
  background: #999999;
  transform: translateY(-4px);
  cursor: pointer;
}


</style>