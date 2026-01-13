<script setup lang="ts">
import { useRouter } from 'vue-router';
import AppHeader from '../App/Header.vue';
import Sidebar from '../App/Sidebar.vue';

const router = useRouter();

const handleRetour = () => {
  router.push("/home-admin");
};
</script>

<template>
  <div class="screen-layout">
    <AppHeader title="Information"/>

    <div class="content-wrapper">
      <Sidebar/>

      <main class="main-content">

        <div class="error-card">

          <div class="icon-circle error">
            <svg width="60" height="60" viewBox="0 0 24 24" stroke="currentColor" stroke-width="3" stroke-linecap="round" stroke-linejoin="round">
              <line x1="18" y1="6" x2="6" y2="18"></line>
              <line x1="6" y1="6" x2="18" y2="18"></line>
            </svg>
          </div>

          <h2>Une erreur est survenue !</h2>
          <p class="sub-text">La sauvegarde n'a pas pu être effectuée. Veuillez réessayer.</p>

          <div class="actions">
            <button @click="handleRetour" class="btn-sys primary">
              Retour au menu
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

.error-card {
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

.icon-circle {
  width: 100px;
  height: 100px;
  border-radius: 50%;
  display: flex;
  justify-content: center;
  align-items: center;
  margin-bottom: 30px;
}

.icon-circle.error {
  background: rgba(239, 83, 80, 0.1);
  color: #ef5350;
}

h2 {
  font-size: 28px;
  color: #B51621;
  margin: 0 0 10px 0;
  font-weight: 700;
}

.sub-text {
  font-size: 18px;
  color: #666;
  margin: 0 0 40px 0;
}

.actions {
  width: 100%;
  display: flex;
  justify-content: center;
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
}
</style>