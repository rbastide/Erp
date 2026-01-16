<script setup>
const emit = defineEmits(['close']);

const onClose = () => emit('close');
</script>

<template>
  <div class="modal-overlay" @click.self="onClose">
    <div class="modal-card">

      <div class="icon-circle error">
        <svg width="60" height="60" viewBox="0 0 24 24" stroke="currentColor" stroke-width="3" stroke-linecap="round" stroke-linejoin="round" fill="none">
          <circle cx="12" cy="12" r="10"></circle>
          <line x1="12" y1="8" x2="12" y2="12"></line>
          <line x1="12" y1="16" x2="12.01" y2="16"></line>
        </svg>
      </div>

      <h2>Impossible d'ajouter</h2>
      <p class="sub-text">Cette compétence n'a pas de niveaux définis dans la base de données.</p>

      <div class="modal-actions">
        <button @click="onClose" class="btn-modal primary">
          Compris
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

.icon-circle.error {
  background: rgba(239, 83, 80, 0.1);
  color: #ef5350;
}

.modal-card h2 {
  margin: 0 0 10px;
  color: #B51621;
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
  background: #B51621;
  color: white;
}

.btn-modal.primary:hover {
  background: #96121b;
}
</style>