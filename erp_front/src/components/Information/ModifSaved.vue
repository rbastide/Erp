<script setup lang="ts">
import { useRouter } from 'vue-router';
import AppHeader from '../App/Header.vue';
import Sidebar from '../App/Sidebar.vue';
import { onMounted, ref } from "vue";

const router = useRouter();
const userRole = ref('');

onMounted(() => {
  const role = localStorage.getItem('user_role');
  userRole.value = role ? role.toUpperCase() : 'USER';
});

const handleRetour = () => {
  if (userRole.value === 'ADMIN' || userRole.value === 'SUPER_ADMIN' || userRole.value === 'ROLE_ADMIN') {
    router.push('/home-admin');
  }
  else {
    router.push('/home');
  }
};
</script>

<template>
  <div class="screen-layout">
    <AppHeader title="Information" inline="Sauvegarde"/>

    <div class="content-wrapper">
      <Sidebar/>

      <main class="main-content">

        <div class="success-card">

          <div class="icon-circle success">
            <svg width="60" height="60" viewBox="0 0 24 24" stroke="currentColor" stroke-width="3" stroke-linecap="round" stroke-linejoin="round" fill="none">
              <polyline points="20 6 9 17 4 12"></polyline>
            </svg>
          </div>

          <h2>Sauvegarde réussie !</h2>
          <p class="sub-text">Vos modifications ont bien été enregistrées dans le système.</p>

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

.success-card {
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

.icon-circle.success {
  background: rgba(76, 175, 80, 0.1);
  color: #4CAF50;
}

h2 {
  font-size: 28px;
  color: #333;
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