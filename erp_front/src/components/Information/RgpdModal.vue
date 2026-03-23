<script setup>
const emit = defineEmits(['accept']);

const onAccept = () => emit('accept');
</script>

<template>
  <div class="modal-overlay">
    <div class="modal-card">
      <div class="icon-circle">
        <svg width="50" height="50" viewBox="0 0 24 24" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" style="fill: none;">
          <path d="M12 22s8-4 8-10V5l-8-3-8 3v7c0 6 8 10 8 10z"></path>
          <path d="M9 12l2 2 4-4"></path>
        </svg>
      </div>

      <h2>Information sur vos données</h2>
      <p class="sub-text">
        Dans le respect du RGPD, nous vous informons que cette application nécessite la collecte et le traitement de certaines de vos données personnelles pour fonctionner correctement.
        <br><br>
        En continuant, vous acceptez cette politique de collecte.
      </p>

      <div class="modal-actions">
        <button @click="onAccept" class="btn-modal primary">Ok, j'ai compris</button>
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
  font-size: 15px;
  line-height: 1.5;
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
  transition: all 0.2s ease;
}

.btn-modal.primary {
  background: #B51621;
  color: white;
}

.btn-modal.primary:hover {
  background: #96121b;
  transform: translateY(-2px);
}
</style>