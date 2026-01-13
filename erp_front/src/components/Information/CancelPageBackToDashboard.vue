<script setup>
import { useRouter } from 'vue-router';
import AppHeader from '../App/Header.vue';
import Sidebar from "../App/Sidebar.vue";
import {onMounted, ref} from "vue";
import {mcccStore} from "@/services/mcccStore.js";

const router = useRouter();
const userRole = ref('');

const handleNon = () => {
  router.back();
}

onMounted(() => {
  userRole.value = localStorage.getItem('user_role') || 'user';
});

const handleOui = () => {
  mcccStore.restoreBackup();
  if (userRole.value === "ADMIN" || userRole.value === 'SUPER_ADMIN'){
    router.push('/home-admin');
  } else {
    router.push('/home');
  }
}
</script>

<template>
  <div class="screen-layout">
    <AppHeader title="Confirmation" inline="Annulation"/>

    <div class="content-wrapper">
      <Sidebar />

      <main class="main-content">
        <div class="warning-card">

          <div class="icon-circle">
            <svg width="60" height="60" viewBox="0 0 24 24" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
              <path d="M10.29 3.86L1.82 18a2 2 0 0 0 1.71 3h16.94a2 2 0 0 0 1.71-3L13.71 3.86a2 2 0 0 0-3.42 0z"></path>
              <line x1="12" y1="9" x2="12" y2="13"></line>
              <line x1="12" y1="17" x2="12.01" y2="17"></line>
            </svg>
          </div>

          <h2>Êtes-vous sûr de vouloir annuler ?</h2>
          <p class="sub-text">Toutes vos modifications non sauvegardées seront définitivement perdues.</p>

          <div class="actions">
            <button @click="handleOui" class="btn-sys primary">
              Oui, abandonner les modifications
            </button>

            <button @click="handleNon" class="btn-sys secondary">
              Non, continuer l'édition
            </button>
          </div>

        </div>
      </main>
    </div>
  </div>
</template>

<style scoped>
/* --- Layout global (identique à Deconnexion) --- */
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
  /* Marge pour la sidebar si elle est fixed/absolue, sinon flex gère */
  margin-left: 80px;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 20px;
}

/* --- Carte d'avertissement --- */
.warning-card {
  background: #ffffff;
  width: 100%;
  max-width: 500px;
  padding: 50px 40px;
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

/* --- Icône --- */
.icon-circle {
  width: 100px;
  height: 100px;
  background: rgba(181, 22, 33, 0.08); /* Fond rouge très pâle */
  border-radius: 50%;
  display: flex;
  justify-content: center;
  align-items: center;
  color: #B51621; /* Rouge charte */
  margin-bottom: 30px;
}

/* --- Typographie --- */
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
  line-height: 1.5;
}

/* --- Boutons --- */
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