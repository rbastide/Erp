<script setup>
import { useRouter } from 'vue-router';
import AuthService from "../../services/AuthService.js";
import AppHeader from "./Header.vue";

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
  <AppHeader title="Déconnexion à" inline="confirmer"/>
  <div class="main-content">
    <p>Êtes vous sûr de vouloir vous déconnecter ?</p>
    <div class="container-btn">
      <div @click="handleOui" class="btn-oui">Oui</div>
      <div @click="handleNon" class="btn-non">Non</div>
    </div>
  </div>

</template>
<style scoped>
.main-content{
  display: flex;
  flex-direction: column;
  width: 90%;
  margin: 254px auto auto;
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