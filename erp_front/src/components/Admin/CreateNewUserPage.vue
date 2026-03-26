<script setup>
import { onMounted, ref } from 'vue';
import { useRouter } from 'vue-router';
import AuthService from '../../services/AuthService.js';
import AppHeader from '../App/Header.vue';
import Sidebar from '../App/Sidebar.vue';
import api from "@/services/api.js";

const router = useRouter();

const username = ref('');
const role = ref('');
const errorMessage = ref('');
const roles = ref([]);

const handleBack = () => {
  router.back();
};

const handleRegister = async () => {
  errorMessage.value = '';

  if (!username.value || !role.value) {
    errorMessage.value = "Veuillez remplir tous les champs.";
    return;
  }

  const userPayload = {
    identifier: username.value,
    role: role.value
  };

  try {
    await AuthService.register(userPayload);
    await router.push('/users-management');
  } catch (error) {
    if (error.response && error.response.data) {
      errorMessage.value = String(error.response.data);
    } else {
      errorMessage.value = "Une erreur est survenue lors de l'inscription.";
    }
  }
};

const fetchRoles = async () => {
  try {
    const response = await api.get('/role/all');
    roles.value = Array.isArray(response.data) ? response.data : (response.data.content || []);
  } catch (error) {
    console.error(error);
  }
};

onMounted(async () => {
  await fetchRoles();
});
</script>

<template>
  <Sidebar />
  <AppHeader title="Inscription" />
  <main class="main-content">
    <form class="login-card" @submit.prevent="handleRegister">

      <div class="form-group">
        <label for="username">Identifiant (Login)</label>
        <input
            type="text"
            id="username"
            placeholder="Identifiant"
            v-model="username"
            required
        />
      </div>

      <div class="form-group">
        <label for="role">Rôle de l'utilisateur</label>
        <select id="role" v-model="role" required>
          <option value="" disabled selected>-- Veuillez choisir un rôle --</option>
          <option v-for="roleItem in roles" :key="roleItem.id" :value="roleItem.name">
            {{ roleItem.name }}
          </option>
        </select>
      </div>

      <p v-if="errorMessage" class="error-message">
        {{ errorMessage }}
      </p>

      <button type="submit" class="login-button">Inscrire</button>
    </form>
  </main>

  <footer>
    <div @click="handleBack" class="quit-btn">Retour</div>
  </footer>
</template>

<style scoped>
.error-message {
  color: #B51621;
  background-color: #ffe6e6;
  padding: 10px;
  border-radius: 4px;
  margin-bottom: 15px;
  font-weight: bold;
  text-align: center;
}

.main-content {
  margin-top: 200px;
  background-color: #FFFFFF;
  display: flex;
  justify-content: center;
  align-items: center;
  font-family: 'Roboto', sans-serif;
  padding: 0 20px 20px;
}

.login-card {
  background-color: #FFFFFF;
  padding: 2.5rem;
  border-radius: 8px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
  border: 1px solid #dcdcdc;
  width: 100%;
  max-width: 400px;
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

.form-group input, .form-group select {
  width: 100%;
  padding: 0.75rem;
  border: 1px solid #dcdcdc;
  border-radius: 4px;
  box-sizing: border-box;
  font-size: 1rem;
}

.form-group input:focus,
.form-group select:focus {
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

.quit-btn {
  width: 150px;
  padding: 0.8rem;
  border: none;
  text-align: center;
  border-radius: 4px;
  background-color: #B51621;
  color: #FFFFFF;
  font-size: 1rem;
  font-weight: bold;
  cursor: pointer;
  margin: 5% auto;
}

.quit-btn:hover {
  background: #999999;
  transform: translateY(-4px);
}
</style>