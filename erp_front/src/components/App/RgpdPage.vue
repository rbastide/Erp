<script setup>
import { useRouter } from 'vue-router';
import AppHeader from './Header.vue';

const router = useRouter();

const handleAccept = () => {
  localStorage.setItem('rgpd_accepted', 'true');
  router.push('/login');
};
</script>

<template>
  <AppHeader title="Politique de Confidentialité" :login-page="true" />

  <main class="main-content">
    <div class="rgpd-card">
      <div class="icon-header">
        <svg xmlns="http://www.w3.org/2000/svg" width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="#B51621" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
          <path d="M12 22s8-4 8-10V5l-8-3-8 3v7c0 6 8 10 8 10z"></path>
        </svg>
      </div>

      <h1 class="title">Protection de vos données</h1>

      <div class="rgpd-content">
        <p>
          Bienvenue sur <strong>IUT'xpress</strong>. Dans le cadre de l'utilisation de cette application,
          nous sommes amenés à traiter certaines de vos données à caractère personnel.
        </p>
        <p>
          En continuant, vous acceptez que vos données soient utilisées exclusivement pour le bon fonctionnement
          des services de l'IUT (gestion des fiches ressources, rôles, etc.). Vos données ne seront en aucun cas
          cédées à des tiers.
        </p>
        <p>
          Conformément au Règlement Général sur la Protection des Données (RGPD), vous disposez d'un droit d'accès,
          de rectification et de suppression de vos informations.
        </p>
      </div>

      <div class="actions">
        <button @click="handleAccept" class="accept-btn">
          J'ai compris et j'accepte
        </button>
      </div>
    </div>
  </main>
</template>

<style scoped>
.main-content {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  padding-top: 60px;
  background-color: #f4f7f9;
}

.rgpd-card {
  background-color: #FFFFFF;
  padding: 3rem 2.5rem;
  border-radius: 12px;
  box-shadow: 0 10px 25px rgba(0, 0, 0, 0.05);
  border: 1px solid #eef2f5;
  width: 100%;
  max-width: 550px;
  display: flex;
  flex-direction: column;
  align-items: center;
  text-align: center;
  animation: fadeIn 0.5s ease-out;
}

.icon-header {
  background: #fff5f5;
  padding: 15px;
  border-radius: 50%;
  margin-bottom: 20px;
}

.title {
  font-family: 'Roboto', sans-serif;
  color: #333;
  margin-bottom: 1.5rem;
  font-size: 1.8rem;
}

.rgpd-content {
  color: #555;
  line-height: 1.6;
  text-align: left;
  margin-bottom: 2rem;
  background: #f8fafc;
  padding: 20px;
  border-radius: 8px;
  border-left: 4px solid #B51621;
}

.rgpd-content p {
  margin-bottom: 1rem;
}
.rgpd-content p:last-child {
  margin-bottom: 0;
}

.actions {
  width: 100%;
}

.accept-btn {
  width: 100%;
  padding: 1rem;
  background: linear-gradient(135deg, #B51621 0%, #900f18 100%);
  color: #FFFFFF;
  border: none;
  border-radius: 6px;
  cursor: pointer;
  font-weight: bold;
  font-size: 1.1rem;
  transition: transform 0.2s, box-shadow 0.2s;
}

.accept-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(181, 22, 33, 0.3);
}

@keyframes fadeIn {
  from { opacity: 0; transform: translateY(10px); }
  to { opacity: 1; transform: translateY(0); }
}
</style>