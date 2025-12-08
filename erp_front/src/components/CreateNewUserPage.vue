<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import AuthService from '../services/AuthService';

const router = useRouter();

// Variables du formulaire
const username = ref('');
const password = ref('');
const confirmPassword = ref('');
const role = ref('');
const errorMessage = ref('');

const handleRetour = () => {
  router.push('/home-admin');
};

const handleAide = () => {
  router.push('/aide');
};

const handleDeconnexion = () => {
  router.push('/deconnexion');
};

const handleRegister = async () => {
  errorMessage.value = '';

  if (password.value !== confirmPassword.value) {
    errorMessage.value = "Les mots de passe ne correspondent pas.";
    return;
  }

  if (!username.value || !password.value || !role.value) {
    errorMessage.value = "Veuillez remplir tous les champs.";
    return;
  }

  const userPayload = {
    identifier: username.value,
    password: password.value,
    role: role.value
  };

  try {
    await AuthService.register(userPayload);
    router.push('/userSave');
  } catch (error) {
    console.error("Erreur Inscription:", error);
    if (error.response && error.response.data) {
      errorMessage.value = error.response.data;
    } else {
      errorMessage.value = "Une erreur est survenue lors de l'inscription.";
    }
  }
};
</script>

<template>
  <header class="page-header">
    <div class="container-connexion">
      <img src="../assets/uploads/Logo_unilim.png" alt="Logo Unilim"><p>Inscription</p>
    </div>
    <div @click="handleAide" class="aide">Service d'aide</div>
    <div @click="handleDeconnexion" class="quitter">
      <svg width="48" height="48" viewBox="0 0 48 48" fill="none" xmlns="http://www.w3.org/2000/svg">
        <path d="M18 42H10C8.93913 42 7.92172 41.5786 7.17157 40.8284C6.42143 40.0783 6 39.0609 6 38V10C6 8.93913 6.42143 7.92172 7.17157 7.17157C7.92172 6.42143 8.93913 6 10 6H18M32 34L42 24M42 24L32 14M42 24H18" stroke="white" stroke-width="4" stroke-linecap="round" stroke-linejoin="round"/>
      </svg>
    </div>
  </header>

  <main class="main-content">
    <form class="login-card" @submit.prevent="handleRegister">

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
    <div @click="handleRetour" class="btn-quitter">Quitter</div>
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

/* Vos styles originaux */
.page-header {
  position: absolute;
  width: 100%;
  height: 172px;
  left: 0px;
  top: 0px;
  background: #B51621;
  box-sizing: border-box;
}
.container-connexion img {
  position: absolute;
  width: 127px;
  height: 127px;
  left: 64px;
  top: 22.5px;
}
.container-connexion p {
  position: absolute;
  width: 723px;
  height: 124px;
  left: 209px;
  top: 24px;
  font-family: 'Roboto', sans-serif;
  font-style: normal;
  font-weight: 900;
  font-size: 56px;
  line-height: 110%;
  display: flex;
  align-items: center;
  letter-spacing: -0.03em;
  color: #FFFFFF;
}
.aide {
  position: absolute;
  width: 126px;
  height: 52px;
  right: 15%;
  top: 60px;
  font-family: 'Roboto', sans-serif;
  font-style: normal;
  font-weight: 500;
  font-size: 36px;
  line-height: 145%;
  display: flex;
  align-items: center;
  text-align: center;
  letter-spacing: -0.005em;
  text-transform: capitalize;
  color: #FFFFFF;
  cursor: pointer;
}
.quitter {
  position: absolute;
  width: 48px;
  height: 48px;
  right: 5%;
  top: 64px;
  cursor: pointer;
}
.main-content {
  background-color: #FFFFFF;
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 20px;
  font-family: 'Roboto', sans-serif;
  min-height: 100vh;
  padding-top: 10%;
  box-sizing: border-box;
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
.form-group input:focus, .form-group select:focus {
  outline: none;
  border-color: #B51621;
  box-shadow: 0 0 0 2px rgba(181, 22, 33, 0.2);
}
.form-group select {
  appearance: menulist-button;
  width: 100%;
  max-width: 280px;
  padding: 12px 16px;
  background: rgba(255,255,255,0.4);
  font-size: 16px;
  border: 1px solid #dcdcdc;
  border-radius: 4px;
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
.btn-quitter {
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
.btn-quitter:hover {
  background: #999999;
  transform: translateY(-4px);
}
</style>