<script setup>
import { useRouter } from 'vue-router';
import AppHeader from '../App/Header.vue';
import Sidebar from '../App/Sidebar.vue';
import {onMounted} from "vue";
import api from "@/services/api.js";
import AuthService from "@/services/AuthService.js";

const router = useRouter();

const handleFill = () => {
  router.push('/resource');
};

const handleAfficher = () => {
  router.push('/history');
};

const fetchUserData = async () => {
}

onMounted(() => {
  fetchUserData();
  AuthService.loadAuthService();
});
</script>

<template>
  <Sidebar :dashboardActive="true"/>
  <div class="page-container">
    <AppHeader title="Bonjour," :inline="AuthService.lastName + ' ' + AuthService.firstName" />

    <main class="main-content">
      <div class="card-action" @click="handleFill">
        <div class="icon-circle">
          <svg width="48" height="48" viewBox="0 0 24 24"  stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" fill="none"><path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"></path><polyline points="14 2 14 8 20 8"></polyline><line x1="12" y1="18" x2="12" y2="12"></line><line x1="9" y1="15" x2="15" y2="15"></line></svg>
        </div>
        <p>Remplir une fiche ressource</p>
      </div>

      <div class="card-action" @click="handleAfficher">
        <div class="icon-circle">
          <svg width="48" height="48" viewBox="0 0 24 24"  stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" fill="none"><rect x="3" y="3" width="18" height="18" rx="2" ry="2"></rect><line x1="9" y1="3" x2="9" y2="21"></line></svg>
        </div>
        <p>Afficher les fiches ressources</p>
      </div>
    </main>
  </div>
</template>

<style scoped>
.page-container {
  min-height: 100vh;
  background-color: #f8f9fa;
  font-family: 'Roboto', sans-serif;
}

.main-content {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  align-items: center;
  gap: 40px;
  padding: 40px 20px;
  margin-top: 175px;
}

.card-action {
  cursor: pointer;
  background: #ffffff;
  width: 380px;
  height: 280px;
  border-radius: 20px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  text-align: center;
  padding: 30px;
  transition: all 0.3s cubic-bezier(0.25, 0.8, 0.25, 1);
  box-shadow: 0 10px 20px rgba(0,0,0,0.05), 0 6px 6px rgba(0,0,0,0.1);
  border: 1px solid rgba(233, 37, 51, 0.1);
}

.card-action:hover {
  transform: translateY(-10px);
  box-shadow: 0 15px 30px rgba(233, 37, 51, 0.15);
  background-color: #ffffff;
  border-color: #E92533;
}

.icon-circle {
  width: 90px;
  height: 90px;
  background: rgba(233, 37, 51, 0.05);
  border-radius: 50%;
  display: flex;
  justify-content: center;
  align-items: center;
  margin-bottom: 25px;
  color: #E92533;
  transition: all 0.3s ease;
}

.card-action:hover .icon-circle {
  background: #E92533;
  color: #ffffff;
  transform: scale(1.1);
}

.card-action p {
  margin: 0;
  font-size: 28px;
  font-weight: 500;
  line-height: 1.3;
  color: #333333;
  transition: color 0.3s ease;
}

.card-action:hover p {
  color: #E92533;
}

@media (max-width: 768px) {
  .main-content {
    margin-top: 140px;
    gap: 20px;
  }
  .card-action {
    width: 300px;
    height: 220px;
  }
  .card-action p {
    font-size: 20px;
  }
}
</style>