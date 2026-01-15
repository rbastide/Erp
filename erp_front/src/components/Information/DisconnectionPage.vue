<script setup>
import {useRouter} from 'vue-router';
import AuthService from "../../services/AuthService.js";
import AppHeader from "../App/Header.vue";
import Sidebar from "../App/Sidebar.vue";

const router = useRouter();

const handleNon = () => {
  router.back();
}

const handleOui = () => {
  localStorage.removeItem('user_token');
  localStorage.removeItem('user_role');
  AuthService.logout();
  router.push('/login');
}
</script>

<template>
  <div class="screen-layout">
    <AppHeader title="Déconnexion" />

    <div class="content-wrapper">

      <Sidebar :quitActive="true"/>

      <main class="main-content">

        <div class="logout-card">

          <div class="icon-circle">
            <svg width="60" height="60" viewBox="0 0 24 24" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" fill="none">
              <path d="M9 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h4"></path>
              <polyline points="16 17 21 12 16 7"></polyline>
              <line x1="21" y1="12" x2="9" y2="12"></line>
            </svg>
          </div>

          <h2>Êtes-vous sûr de vouloir vous déconnecter ?</h2>
          <p class="sub-text">Vous serez redirigé vers la page de connexion.</p>

          <div class="actions">
            <button @click="handleOui" class="btn-sys primary">
              Oui, me déconnecter
            </button>

            <button @click="handleNon" class="btn-sys secondary">
              Non, annuler
            </button>
          </div>

        </div>

      </main>
    </div>
  </div>
</template>

<style scoped>
.screen-layout {
  display: flex;
  flex-direction: column;
  height: 100vh;
  overflow: hidden;
  background-color: #f8f9fa;
  font-family: 'Roboto', sans-serif;
}

.content-wrapper {
  flex: 1;
  display: flex;
  position: relative;
  overflow: hidden;
}

.main-content {
  flex: 1;
  margin-left: 80px;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 20px;
}

.logout-card {
  background: #ffffff;
  margin-top: 100px;
  width: 100%;
  max-width: 400px;
  padding: 30px 40px;
  border-radius: 20px;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.08);
  display: flex;
  flex-direction: column;
  align-items: center;
  text-align: center;
  border: 1px solid rgba(0,0,0,0.05);
  animation: popIn 0.3s cubic-bezier(0.25, 0.8, 0.25, 1);
}

@keyframes popIn {
  from { opacity: 0; transform: scale(0.95); }
  to { opacity: 1; transform: scale(1); }
}

.icon-circle {
  width: 100px;
  height: 100px;
  background: rgba(181, 22, 33, 0.08);
  border-radius: 50%;
  display: flex;
  justify-content: center;
  align-items: center;
  color: #B51621;
  margin-bottom: 30px;
}

h2 {
  font-size: 24px;
  color: #333;
  margin: 0 0 10px 0;
  font-weight: 700;
}

.sub-text {
  font-size: 16px;
  color: #666;
  margin: 0 0 40px 0;
}

.actions {
  display: flex;
  flex-direction: column;
  gap: 15px;
  width: 100%;
}

.btn-sys {
  width: 100%;
  padding: 15px;
  border-radius: 50px;
  border: none;
  font-size: 18px;
  font-weight: 700;
  cursor: pointer;
  transition: all 0.2s;
  letter-spacing: 0.03125em;
  font-family: 'Roboto', sans-serif;
}

.btn-sys.primary {
  background: linear-gradient(135deg, #B51621 0%, #d92533 100%);
  color: white;
  box-shadow: 0 4px 10px rgba(181, 22, 33, 0.3);
}

.btn-sys.primary:hover {
  transform: translateY(-2px);
  box-shadow: 0 6px 15px rgba(181, 22, 33, 0.4);
  background: linear-gradient(135deg, #94121b 0%, #B51621 100%);
}

.btn-sys.secondary {
  background-color: white;
  color: #555;
  border: 2px solid #e0e0e0;
}

.btn-sys.secondary:hover {
  background-color: #f5f5f5;
  border-color: #ccc;
  color: #333;
}
</style>