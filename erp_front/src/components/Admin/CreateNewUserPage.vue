<script setup>
import {onMounted, ref} from 'vue';
import {useRouter} from 'vue-router';
import AuthService from '../../services/AuthService.js';
import AppHeader from '../App/Header.vue';
import Sidebar from '../App/Sidebar.vue';
import api from "@/services/api.js";

const router = useRouter();

const username = ref('');
const firstname = ref('');
const universityDepartment = ref('');
const lastname = ref('');
const email = ref('');
const password = ref('');
const confirmPassword = ref('');
const role = ref('');
const errorMessage = ref('');
const universityDepartments = ref([]);

const handleBack = () => {
  router.back();
};

const handleRegister = async () => {
  errorMessage.value = '';

  if (password.value !== confirmPassword.value) {
    errorMessage.value = "Les mots de passe ne correspondent pas.";
    return;
  }

  if (!username.value || !password.value || !role.value || !firstname.value || !lastname.value || !email.value || !universityDepartment.value) {
    errorMessage.value = "Veuillez remplir tous les champs.";
    return;
  }

  const userPayload = {
    identifier: username.value,
    firstname: firstname.value,
    lastname: lastname.value,
    email: email.value,
    password: password.value,
    role: role.value,
    universityDepartment : universityDepartment.value
  };

  try {
    console.log(userPayload);
    await AuthService.register(userPayload);
    await router.push('/users-management');
  } catch (error) {
    console.error("Erreur Inscription:", error);
    if (error.response && error.response.data) {
      errorMessage.value = error.response.data;
    } else {
      errorMessage.value = "Une erreur est survenue lors de l'inscription.";
    }
  }
};

onMounted(async () => {
  const response = await api.get('/universityDepartment/getUniversityDepartments');
  universityDepartments.value = response.data;
})
</script>

<template>
  <Sidebar/>
  <AppHeader title="Inscription"/>
  <main class="main-content">
    <form class="login-card" @submit.prevent="handleRegister">

      <div class="form-group">
        <label for="lastname">Nom</label>
        <input
            type="text"
            id="lastname"
            placeholder="Nom"
            v-model="lastname"
            required
        />
      </div>

      <div class="form-group">
        <label for="firstname">Prénom</label>
        <input
            type="text"
            id="firstname"
            placeholder="Prénom"
            v-model="firstname"
            required
        />
      </div>

      <div class="form-group">
        <label for="universityDepartment">Département de l'utilisateur</label>
        <select id="universityDepartment" v-model="universityDepartment" required>
          <option value="" disabled selected>-- Veuillez choisir un département --</option>
          <option v-for="dept in universityDepartments" :key="dept" :value="dept">
            {{ dept.universityDepartmentName }}
          </option>
        </select>
      </div>

      <div class="form-group">
        <label for="email">Adresse mail</label>
        <input
            type="email"
            id="email"
            placeholder="exemple@email.com"
            v-model="email"
            required
        />
      </div>

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
        <label for="password">Mot de passe</label>
        <input
            type="password"
            id="password"
            placeholder="Mot de passe"
            v-model="password"
            required
        />
      </div>

      <div class="form-group">
        <label for="confirm_password">Confirmer le mot de passe</label>
        <input
            type="password"
            id="confirm_password"
            placeholder="Confirmer le mot de passe"
            v-model="confirmPassword"
            required
        />
      </div>

      <div class="form-group">
        <label for="role">Rôle de l'utilisateur</label>
        <select id="role" v-model="role" required>
          <option value="" disabled selected>-- Veuillez choisir un rôle --</option>
          <option value="SUPER_ADMIN">Superadmin</option>
          <option value="ADMIN">Administrateur</option>
          <option value="TEMP_TEACHER">Vacataire</option>
          <option value="TEACHER">Professeur</option>
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
.form-group input {
  width: 100%;
  padding: 0.75rem;
  border: 1px solid #dcdcdc;
  border-radius: 4px;
  box-sizing: border-box;
  font-size: 1rem;
}
.form-group input:focus, .form-group select:focus {
  outline: none;
  border-color: #B51621;
  box-shadow: 0 0 0 2px rgba(181, 22, 33, 0.2);
}
.form-group select {
  appearance: menulist-button;
  width: 100%;
  padding: 12px 16px;
  background: rgba(255,255,255,0.4);
  font-size: 16px;
  border: 1px solid #dcdcdc;
  border-radius: 4px;
  box-sizing: border-box;
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
  transition: background-color 0.2s ease;
  position: relative;
  margin: 5% auto;
  font-family: 'Roboto', sans-serif;
}
.quit-btn:hover {
  background: #999999;
  transform: translateY(-4px);
}
</style>