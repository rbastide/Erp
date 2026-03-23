<script setup lang="ts">
import { useRouter } from 'vue-router';
import { onMounted, ref } from "vue";

const router = useRouter();
const userRole = ref('');

onMounted(() => {
  const role = localStorage.getItem('user_role');
  userRole.value = role ? role.toUpperCase() : 'USER';
});

const handleBack = () => {
  router.push('/home');
};
</script>

<template>
  <div class="modal-overlay">
    <div class="modal-card">

      <div class="icon-circle success">
        <svg width="60" height="60" viewBox="0 0 24 24" stroke="currentColor" stroke-width="3" stroke-linecap="round" stroke-linejoin="round" style="fill: none;">
          <polyline points="20 6 9 17 4 12"></polyline>
        </svg>
      </div>

      <h2>Sauvegarde réussie !</h2>
      <p class="sub-text">La validation a bien été effectuée.</p>

      <div class="modal-actions">
        <button @click="handleBack" class="btn-modal primary">
          Retour au menu
        </button>
      </div>

    </div>
  </div>
</template>

<style scoped>
.modal-overlay {
  position: fixed;
  top: 0; left: 0;
  width: 100%; height: 100%;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 2000;
  backdrop-filter: blur(3px);
}

.modal-card {
  background: white;
  padding: 40px;
  border-radius: 20px;
  width: 90%;
  max-width: 450px;
  text-align: center;
  box-shadow: 0 20px 50px rgba(0,0,0,0.2);
  animation: popIn 0.3s cubic-bezier(0.18, 0.89, 0.32, 1.28);
}

@keyframes popIn {
  from { transform: scale(0.8); opacity: 0; }
  to { transform: scale(1); opacity: 1; }
}

.icon-circle {
  width: 80px; height: 80px;
  border-radius: 50%;
  display: flex; justify-content: center; align-items: center;
  margin: 0 auto 20px;
}

.icon-circle.success {
  background: rgba(76, 175, 80, 0.1);
  color: #4CAF50;
}

.modal-card h2 {
  margin: 0 0 10px;
  color: #2e7d32; /* Vert foncé pour le titre */
}

.sub-text {
  color: #666;
  margin-bottom: 30px;
}

.modal-actions {
  display: flex;
  justify-content: center;
  width: 100%;
}

.btn-modal {
  padding: 12px 30px;
  border-radius: 50px;
  font-size: 16px;
  font-weight: bold;
  cursor: pointer;
  border: none;
  font-family: 'Roboto', sans-serif;
  width: 100%;
}

.btn-modal.primary {
  background: linear-gradient(135deg, #B51621 0%, #d92533 100%);
  color: white;
  box-shadow: 0 4px 10px rgba(181, 22, 33, 0.3);
}

.btn-modal.primary:hover {
  transform: translateY(-2px);
  box-shadow: 0 6px 15px rgba(181, 22, 33, 0.4);
}
</style>