<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import AuthService from '../../services/AuthService.js';
import AppHeader from './Header.vue';
import '../../assets/css/variable.css';

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

      if (credentials.identifier === 'admin') {
        await router.push('/home-admin');
      } else {
        await router.push('/home');
      }

    } catch (error) {
      console.error("Erreur API:", error);
      if (error.response && error.response.status === 401) {
        errorMessage.value = "Identifiant ou mot de passe incorrect.";
      } else {
        errorMessage.value = "Impossible de contacter le serveur.";
      }
    }
};

const handleAide = () => {
  router.push('/aide');
};
</script>

<template>
  <AppHeader title="Connexion" :deconnexion="false"/>
  <main class="main-content">
    <form class="login-card" @submit.prevent="handleLogin">

      <div class="form-group">
        <label for="username">Identifiant</label>
        <input
          type="text"
          id="username"
          placeholder="Identifiant"
          v-model="username"
          required
        />
      </div>

      <div class="form-group">
        <label for="password">Mot de passe</label>
        <input
          type="password"
          id="password"
          placeholder="Mot de passe"
          v-model="password"
          required
        />
      </div>

      <p v-if="errorMessage" class="error-message">
        {{ errorMessage }}
      </p>

      <button type="submit" class="login-button">Se connecter</button>
    </form>
  </main>
</template>

<style scoped>

.main-content {
  display: flex;
  justify-content: center;
  align-items: center;
  font-family: 'Roboto', sans-serif;
  padding: 172px 20px 20px;
  min-height: 100vh;
  box-sizing: border-box;
}

.error-message {
  color: #B51621;
  background-color: #ffe6e6;
  padding: 10px;
  border-radius: 4px;
  margin-bottom: 15px;
  font-weight: bold;
  text-align: center;
  font-size: 0.9rem;
}

.login-card {
  background-color: #FFFFFF;
  padding: 2.5rem;
  border-radius: 8px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
  border: 1px solid #dcdcdc;
}
.form-group {
  margin-bottom: 1.5rem;
}
.form-group label {
  display: block;
  margin-bottom: 0.5rem;
  font-weight: bold;
  font-size: 0.9rem;
  color: black;
}
.form-group input {
  width: 100%;
  padding: 0.75rem;
  border: 1px solid #dcdcdc;
  border-radius: 4px;
  box-sizing: border-box;
  font-size: 1rem;
}
.form-group input:focus {
  outline: none;
  border-color: #B51621;
  box-shadow: 0 0 0 2px rgba(181, 22, 33, 0.2);
}
.login-button {
  width: 100%;
  padding: 0.8rem;
  border: none;
  border-radius: 4px;
  background-color: #B51621;
  color: #FFFFFF;
  font-size: 1rem;
  font-weight: bold;
  cursor: pointer;
  transition: background-color 0.2s ease;
}
.login-button:hover {
  background-color: #9c121b;
}
</style>