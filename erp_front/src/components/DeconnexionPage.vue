<script setup>
import { onMounted } from "vue";
import { useRouter } from 'vue-router';
import AuthService from "../services/AuthService";

const router = useRouter();

const handleAide = () => {
  router.push('/aide');
};

const handleNon = () => {
  router.back();
}

const handleOui = () => {
  AuthService.logout();

  router.push('/login');
}
</script>

<template>
  <header class="page-header">
    <div class="container-nom">
      <img src="../assets/uploads/Logo_unilim.png" alt="Logo Unilim"><p>Deconnexion à<br>confirmer</p>
    </div>
    <div @click="handleAide" class="aide">Service d'aide</div>
  </header>
  <div class="main-content">
    <p>Êtes vous sûr de vouloir vous déconnecter ?</p>
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
  padding: 0px 16px;
  width: 360px;
  height: 200px;
  background: #D9D9D9;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
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