<!--
  Ce fichier est un composant Vue en "Single File Component" (SFC).
  Il contient 3 parties :
  1. <template> : La structure HTML de votre composant.
  2. <script setup> : La logique JavaScript (état, méthodes) de votre composant.
  3. <style scoped> : Le style CSS qui s'applique UNIQUEMENT à ce composant.
-->

<template>
  <!-- Conteneur principal de la page de connexion -->
  <div class="login-page">

    <!-- 1. Le Header (bandeau rouge) -->
    <header class="main-header">

      <!-- Section gauche du header (Logo + Titre) -->
      <div class="header-left">
        <!-- J'utilise un simple SVG en attendant votre vrai logo -->
        <svg class="logo" width="40" height="40" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
          <path d="M12 2L2 7V17L12 22L22 17V7L12 2Z" fill="white"/>
          <path d="M12 12L22 7" stroke="#BA181B" stroke-width="2"/>
          <path d="M12 12V22" stroke="#BA181B" stroke-width="2"/>
          <path d="M12 12L2 7" stroke="#BA181B" stroke-width="2"/>
        </svg>
        <span class="header-title">Connexion</span>
      </div>

      <!-- Section droite du header (Contact + Date/Heure) -->
      <div class="header-right">
        <a href="#" class="header-contact">Contact</a>
        <div class="datetime">
          <span class="date">{{ currentDate }}</span>
          <span class="time">{{ currentTime }}</span>
        </div>
      </div>
    </header>

    <!-- 2. Le Contenu Principal (zone blanche) -->
    <main class="main-content">

      <!-- 3. La Carte de Connexion (formulaire) -->
      <!-- @submit.prevent empêche la page de se recharger lors de la soumission -->
      <form class="login-card" @submit.prevent="handleLogin">

        <!-- Groupe pour l'identifiant -->
        <div class="form-group">
          <label for="username">Identifiant</label>
          <!--
            v-model="username" lie cet input à la variable 'username'
            dans notre script.
          -->
          <input
            type="text"
            id="username"
            placeholder="Identifiant"
            v-model="username"
            required
          />
        </div>

        <!-- Groupe pour le mot de passe -->
        <div class="form-group">
          <label for="password">Mot de passe</label>
          <!--
            v-model="password" lie cet input à la variable 'password'
            dans notre script.
          -->
          <input
            type="password"
            id="password"
            placeholder="Mot de passe"
            v-model="password"
            required
          />
        </div>

        <!-- Bouton de soumission -->
        <button type="submit" class="login-button">Se connecter</button>

      </form>
    </main>

  </div>
</template>

<script setup>
// --- Logique JavaScript (Vue 3 Composition API) ---
import { ref, onMounted, onUnmounted } from 'vue';

// 1. État pour les champs du formulaire
// 'ref' crée une variable réactive (qui met à jour l'interface si elle change)
const username = ref('');
const password = ref('');

// 2. État pour la date et l'heure
const currentDate = ref('');
const currentTime = ref('');
let timerInterval = null; // Pour stocker notre intervalle de mise à jour

// 3. Méthode pour formater un nombre (ex: 9 -> "09")
function pad(num) {
  return num < 10 ? '0' + num : num;
}

// 4. Méthode pour mettre à jour l'heure
function updateDateTime() {
  const now = new Date();
  currentDate.value = `${pad(now.getDate())}/${pad(now.getMonth() + 1)}/${pad(now.getFullYear().toString().slice(-2))}`;
  currentTime.value = `${pad(now.getHours())}:${pad(now.getMinutes())}`;
}

// 5. Méthode appelée lors de la soumission du formulaire
function handleLogin() {
  // Pour l'instant, on affiche juste les données dans la console.
  // Plus tard, vous enverrez ces données à votre backend (API) ici.
  console.log('Tentative de connexion avec :');
  console.log('Identifiant:', username.value);
  console.log('Mot de passe:', password.value);

  // Vous pouvez ajouter une logique de chargement ou de redirection ici
  alert(`Connexion avec : ${username.value}`);
}

// 6. Hooks de cycle de vie
// 'onMounted' est appelé quand le composant est affiché à l'écran
onMounted(() => {
  updateDateTime(); // Mettre à jour une première fois
  // Mettre à jour l'heure chaque seconde
  timerInterval = setInterval(updateDateTime, 1000);
});

// 'onUnmounted' est appelé quand le composant est détruit
// C'est important pour nettoyer l'intervalle et éviter les fuites de mémoire
onUnmounted(() => {
  clearInterval(timerInterval);
});
</script>

<style scoped>
/* 'scoped' signifie que ces styles ne s'appliqueront qu'aux éléments
  de ce composant (template ci-dessus).
*/

/* --- Variables de couleur (basées sur votre screenshot) --- */
:root {
  --brand-red: #BA181B; /* Rouge profond pour le header et bouton */
  --text-light: #FFFFFF; /* Texte blanc sur fond rouge */
  --text-dark: #333;    /* Texte noir/gris pour le formulaire */
  --bg-light: #FFFFFF;  /* Fond blanc de la carte */
  --bg-page: #f0f2f5;   /* Fond gris très clair de la page */
  --border-color: #dcdcdc; /* Couleur de la bordure des inputs */
}

/* --- Structure globale de la page --- */
.login-page {
  display: flex;
  flex-direction: column; /* Organise les enfants (header, main) verticalement */
  height: 100vh; /* Prend 100% de la hauteur de la fenêtre */
  font-family: Arial, sans-serif;
  color: var(--text-dark);
}

/* --- Style du Header --- */
.main-header {
  background-color: var(--brand-red);
  color: var(--text-light);
  padding: 10px 20px;
  display: flex;
  justify-content: space-between; /* Espace les enfants de gauche et de droite */
  align-items: center;
  flex-shrink: 0; /* Empêche le header de rétrécir */
}

.header-left, .header-right {
  display: flex;
  align-items: center;
  gap: 15px; /* Espace entre les éléments */
}

.logo {
  margin-right: 5px;
}

.header-title {
  font-size: 1.5rem; /* 24px */
  font-weight: bold;
}

.header-contact {
  color: var(--text-light);
  text-decoration: none;
  font-size: 1.1rem; /* 18px */
  font-weight: bold;
}
.header-contact:hover {
  text-decoration: underline;
}

.datetime {
  display: flex;
  flex-direction: column; /* Date au-dessus de l'heure */
  align-items: flex-end;
  font-size: 0.8rem; /* 12px */
  line-height: 1.3;
}

/* --- Style du Contenu Principal --- */
.main-content {
  flex-grow: 1; /* Prend tout l'espace vertical restant */
  background-color: var(--bg-page);
  display: flex;
  justify-content: center; /* Centre la carte horizontalement */
  align-items: center; /* Centre la carte verticalement */
  padding: 20px;
}

/* --- Style de la Carte de Connexion --- */
.login-card {
  background-color: var(--bg-light);
  padding: 2.5rem; /* 40px */
  border-radius: 8px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
  width: 100%;
  max-width: 400px; /* Largeur maximale de la carte */
  border: 1px solid var(--border-color);
}

.form-group {
  margin-bottom: 1.5rem; /* 24px */
}

.form-group label {
  display: block;
  margin-bottom: 0.5rem; /* 8px */
  font-weight: bold;
  font-size: 0.9rem; /* 14px */
}

.form-group input {
  width: 100%;
  padding: 0.75rem; /* 12px */
  border: 1px solid var(--border-color);
  border-radius: 4px;
  box-sizing: border-box; /* Assure que le padding n'augmente pas la largeur */
  font-size: 1rem; /* 16px */
}

.form-group input:focus {
  outline: none;
  border-color: var(--brand-red);
  box-shadow: 0 0 0 2px rgba(186, 24, 27, 0.2);
}

.login-button {
  width: 100%;
  padding: 0.8rem; /* 13px */
  border: none;
  border-radius: 4px;
  background-color: var(--brand-red);
  color: var(--text-light);
  font-size: 1rem; /* 16px */
  font-weight: bold;
  cursor: pointer;
  transition: background-color 0.2s ease;
}

.login-button:hover {
  background-color: #a01013; /* Un rouge légèrement plus foncé au survol */
}
</style>
