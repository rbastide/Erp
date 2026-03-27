<script setup>
const emit = defineEmits(['close']);

const onClose = () => emit('close');
</script>

<template>
  <div class="modal-overlay" @click.self="onClose">
    <div class="modal-card">

      <div class="icon-circle error">
        <svg width="60" height="60" viewBox="0 0 24 24" stroke="currentColor" stroke-width="3" stroke-linecap="round" stroke-linejoin="round" style="fill: none;">
          <line x1="18" y1="6" x2="6" y2="18"></line>
          <line x1="6" y1="6" x2="18" y2="18"></line>
        </svg>
      </div>

      <h2>Une erreur est survenue !</h2>
      <p class="sub-text">La supression n'a pas pu être effectuée. Un utilisateur est encore assigné à ce rôle.</p>

      <div class="modal-actions">
        <button @click="onClose" class="btn-modal primary">
          Fermer et réessayer
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

/* Style spécifique Erreur (Rouge) */
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
  color: #B51621; /* Rouge charte */
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