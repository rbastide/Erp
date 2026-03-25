<script setup>
import {onMounted, ref} from 'vue';
import {useRouter} from 'vue-router';
import AuthService, {authStore} from '../../services/AuthService.js';
import AppHeader from './Header.vue';
import api, {connectWithCas} from "@/services/api.js";
import RgpdModal from '../Information/RgpdModal.vue';

const router = useRouter();
const username = ref('');
const errorMessage = ref('');
const role = ref('');
const isDev = window.location.hostname === 'localhost';

const showRgpdModal = ref(false);

const proceedToLogin = () => {
  if(!isDev){
    connectWithCas();
  }
};

const handleRgpdAccept = () => {
  localStorage.setItem('rgpd_accepted', 'true');
  showRgpdModal.value = false;
  proceedToLogin();
};

const handleLogin = async () => {
  await AuthService.logout();
  errorMessage.value = '';

  /** @type {LoginRequest} */
  const credentials = {
    identifier: username.value
  };

  try {
    const userData = await AuthService.login(credentials);
    role.value = userData.role;
    const response = await api.get(`auth/user-info/${username.value}`);
    authStore.firstName = response.data.firstname;
    authStore.lastName = response.data.lastname;
    authStore.save();
    await router.push('/home');

  } catch (error) {
    errorMessage.value = error.response?.status === 401
        ? "Identifiant ou mot de passe incorrect."
        : "Impossible de contacter le serveur.";
  }
};

const loginWithCAS = () => {
  connectWithCas()
};

onMounted(() => {
  const hasAcceptedRgpd = (localStorage.getItem('rgpd_accepted') === 'true');

  if (hasAcceptedRgpd) {
    proceedToLogin();
  } else {
    showRgpdModal.value = true;
  }
});

</script>

<template>
  <AppHeader title="Connexion" :login-page="true"/>

  <main class="main-content">
    <div class="brand-header">
      <div class="logo-container">
        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="brand-logo" fill="none">
          <path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z" stroke="#B51621" fill="white"></path>
          <polyline points="14 2 14 8 20 8" stroke="#B51621"></polyline>
          <line x1="16" y1="13" x2="8" y2="13" stroke="#B51621"></line>
          <line x1="16" y1="17" x2="8" y2="17" stroke="#B51621"></line>
          <path d="M10 9H8" stroke="#B51621"></path>
        </svg>
      </div>
      <h1 class="app-title">IUT<span class="highlight">'xpress</span></h1>
    </div>

    <form class="login-card" @submit.prevent="handleLogin">
      <div class="form-group">
        <label for="username">Identifiant</label>
        <input type="text" id="username" v-model="username" required />
      </div>
      <p v-if="errorMessage" class="error-message">{{ errorMessage }}</p>
      <button type="submit" class="login-button">Se connecter</button>
    </form>

    <div class="cas-login-section">
      <div class="divider">ou</div>
      <button type="button" @click="loginWithCAS" class="cas-button">
        Se connecter avec le CAS
      </button>
    </div>

    <RgpdModal
        v-if="showRgpdModal"
        @accept="handleRgpdAccept"
    />
  </main>
</template>

<style scoped>
.main-content {
  display: flex;
  margin-top: 60px;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  padding-top: 50px;
  background-color: #f4f7f9;
}

.brand-header {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-bottom: 2rem;
  animation: fadeIn 0.8s ease-out;
}

.logo-container {
  background: white;
  padding: 15px;
  border-radius: 50%;
  box-shadow: 0 4px 10px rgba(181, 22, 33, 0.15);
  margin-bottom: 10px;
  display: flex;
  justify-content: center;
  align-items: center;
}

.brand-logo {
  width: 60px;
  height: 60px;
}

.app-title {
  font-family: 'Roboto', sans-serif;
  font-size: 3rem;
  font-weight: 700;
  color: #333;
  margin: 0;
  letter-spacing: -0.5px;
}

.highlight {
  background: linear-gradient(90deg, #96111a 0%, #b51621 40%, #D47200 100%);
  -webkit-background-clip: text;
  background-clip: text;
  -webkit-text-fill-color: transparent;
  color: transparent;
  font-style: italic;
  padding-bottom: 2px;
}

.login-card {
  background-color: #FFFFFF;
  padding: 2.5rem;
  border-radius: 12px;
  box-shadow: 0 10px 25px rgba(0, 0, 0, 0.05);
  border: 1px solid #eef2f5;
  width: 100%;
  max-width: 400px;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.form-group {
  margin-bottom: 1.5rem;
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.form-group label {
  align-self: flex-start;
  margin-bottom: 0.5rem;
  font-weight: 600;
  color: #555;
  width: 100%;
  max-width: 300px;
}

.form-group input {
  width: 100%;
  max-width: 380px;
  padding: 0.85rem;
  border: 1px solid #dcdcdc;
  border-radius: 6px;
  background-color: #fafafa;
  transition: border-color 0.2s, box-shadow 0.2s;
}

.form-group input:focus {
  outline: none;
  border-color: #B51621;
  box-shadow: 0 0 0 3px rgba(181, 22, 33, 0.1);
  background-color: white;
}

.login-button {
  width: 100%;
  max-width: 300px;
  padding: 0.9rem;
  background: linear-gradient(135deg, #B51621 0%, #900f18 100%);
  color: #FFFFFF;
  border: none;
  border-radius: 6px;
  cursor: pointer;
  font-weight: bold;
  font-size: 1rem;
  transition: transform 0.2s, box-shadow 0.2s;
  margin-top: 10px;
}

.login-button:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(181, 22, 33, 0.3);
}

.error-message {
  color: #B51621;
  background: #fff5f5;
  padding: 12px;
  border-radius: 6px;
  border-left: 4px solid #B51621;
  margin-bottom: 20px;
  text-align: left;
  width: 100%;
  font-size: 0.9rem;
  box-sizing: border-box;
}

.cas-login-section {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
  max-width: 400px;
  margin-top: 20px;
}

.divider {
  color: #888;
  font-size: 0.9rem;
  margin-bottom: 15px;
  position: relative;
  width: 100%;
  text-align: center;
}

.divider::before, .divider::after {
  content: '';
  position: absolute;
  top: 50%;
  width: 40%;
  height: 1px;
  background-color: #dcdcdc;
}

.divider::before { left: 0; }
.divider::after { right: 0; }

.cas-button {
  width: 100%;
  max-width: 300px;
  padding: 0.9rem;
  background: white;
  color: #333;
  border: 2px solid #dcdcdc;
  border-radius: 6px;
  cursor: pointer;
  font-weight: bold;
  font-size: 1rem;
  transition: all 0.2s;
}

.cas-button:hover {
  background: #f8f9fa;
  border-color: #bbb;
}

@keyframes fadeIn {
  from { opacity: 0; transform: translateY(-10px); }
  to { opacity: 1; transform: translateY(0); }
}
</style>