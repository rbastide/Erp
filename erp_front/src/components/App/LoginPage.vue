<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import AuthService from '../../services/AuthService.js';
import AppHeader from './Header.vue';

const router = useRouter();
const username = ref('');
const password = ref('');
const errorMessage = ref('');

const handleLogin = async () => {
  AuthService.logout();
  errorMessage.value = '';

  const credentials = {
    identifier: username.value,
    password: password.value
  };

  try {
    await AuthService.login(credentials);

    const role = credentials.identifier === 'admin' ? 'admin' : 'user';
    localStorage.setItem('user_role', role);

    if (role === 'admin') {
      await router.push('/home-admin');
    } else {
      await router.push('/home');
    }

  } catch (error) {
    errorMessage.value = error.response?.status === 401
        ? "Identifiant ou mot de passe incorrect."
        : "Impossible de contacter le serveur.";
  }
};
</script>

<template>
  <AppHeader title="Connexion" :deconnexion="false"/>
  <main class="main-content">
    <form class="login-card" @submit.prevent="handleLogin">
      <div class="form-group">
        <label for="username">Identifiant</label>
        <input type="text" id="username" v-model="username" required />
      </div>
      <div class="form-group">
        <label for="password">Mot de passe</label>
        <input type="password" id="password" v-model="password" required />
      </div>
      <p v-if="errorMessage" class="error-message">{{ errorMessage }}</p>
      <button type="submit" class="login-button">Se connecter</button>
    </form>
  </main>
</template>

<style scoped>
.main-content {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  padding-top: 70px;
  background-color: #f4f7f9;
}

.login-card {
  background-color: #FFFFFF;
  padding: 2.5rem;
  border-radius: 8px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
  border: 1px solid #dcdcdc;
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
  font-weight: bold;
  width: 100%;
  max-width: 300px;
}

.form-group input {
  width: 100%;
  max-width: 380px;
  padding: 0.75rem;
  border: 1px solid #dcdcdc;
  border-radius: 4px;
}

.login-button {
  width: 100%;
  max-width: 300px;
  padding: 0.8rem;
  background-color: #B51621;
  color: #FFFFFF;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}
.error-message {
  color: #B51621;
  background: #ffe6e6;
  padding: 10px;
  margin-bottom: 15px;
  text-align: center;
}
</style>