<script setup>
// On définit les événements que ce composant peut envoyer au parent
const emit = defineEmits(['confirm', 'close']);

// Actions simples : on prévient le parent
const onConfirm = () => emit('confirm');
const onClose = () => emit('close');
</script>

<template>
  <div class="modal-overlay">
    <div class="modal-card">
      <div class="icon-circle">
        <svg width="50" height="50" viewBox="0 0 24 24" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" fill="none">
          <path d="M10.29 3.86L1.82 18a2 2 0 0 0 1.71 3h16.94a2 2 0 0 0 1.71-3L13.71 3.86a2 2 0 0 0-3.42 0z"></path>
          <line x1="12" y1="9" x2="12" y2="13"></line>
          <line x1="12" y1="17" x2="12.01" y2="17"></line>
        </svg>
      </div>
      <h2>Êtes-vous sûr de vouloir annuler ?</h2>
      <p class="sub-text">Toutes vos modifications non sauvegardées seront définitivement perdues.</p>

      <div class="modal-actions">
        <button @click="onConfirm" class="btn-modal primary">Oui, abandonner</button>
        <button @click="onClose" class="btn-modal secondary">Non, continuer</button>
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
  background: rgba(181, 22, 33, 0.1);
  color: #B51621;
  border-radius: 50%;
  display: flex; justify-content: center; align-items: center;
  margin: 0 auto 20px;
}
.modal-card h2 {
  margin: 0 0 10px;
  color: #333;
}
.sub-text {
  color: #666;
  margin-bottom: 30px;
}
.modal-actions {
  display: flex;
  flex-direction: column;
  gap: 10px;
}
.btn-modal {
  padding: 12px;
  border-radius: 8px;
  font-size: 16px;
  font-weight: bold;
  cursor: pointer;
  border: none;
  font-family: 'Roboto', sans-serif;
}
.btn-modal.primary {
  background: #B51621;
  color: white;
}
.btn-modal.primary:hover {
  background: #96121b;
}
.btn-modal.secondary {
  background: white;
  border: 2px solid #ddd;
  color: #555;
}
.btn-modal.secondary:hover {
  background: #f5f5f5;
  border-color: #bbb;
}
</style>