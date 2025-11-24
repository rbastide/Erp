<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';

const router = useRouter();
const lastname = ref('');
const firstname = ref('');
const teachers = ref([]);


// Fonctions de navigation
const handleDeconnexion = () => {
  router.push('/deconnexion');
};

const handleAide = () => {
  router.push('/aide');
};

const handleAdd = () => {

  if (lastname.value.trim() === '' || firstname.value.trim() === '') {
    console.error("Veuillez saisir le nom et le prénom.");
    return;
  }

  const newTeacher = {
    lastname: lastname.value.trim(),
    firstname: firstname.value.trim(),
    id: Date.now()
  };

  // 3. VÉRIFICATION AVEC some() :
  // Recherche si un référent existe déjà avec ce nom ET ce prénom
  const isDuplicate = teachers.value.some(teacher => {
    return teacher.lastname.toLowerCase() === lastname.value.toLowerCase() &&
        teacher.firstname.toLowerCase() === firstname.value.toLowerCase()
  });

  if (isDuplicate) {
    console.warn(`Le référent ${firstname} ${lastname} est déjà dans la liste.`);
    console.error("ce référent existe déjà");
    // Empêcher l'ajout en doublon
    return;
  }
  teachers.value.push(newTeacher);

  lastname.value = '';
  firstname.value = '';
};

const handleValider = () => {
  if (teachers.value.length === 0) {
    console.error("Veuillez ajouter au moins un référent avant de valider.");
    return;
  }
  console.log("Liste des référents validée :", teachers.value);
  // Ici, vous ajouteriez la logique pour envoyer les données au backend ou naviguer
};
</script>

<template>
  <header class="page-header">
    <div class="container-connexion">
      <img src="../assets/uploads/Logo_unilim.png" alt="Logo Unilim"><p>Référents pour RX.XX</p>
    </div>
    <div @click="handleAide" class="aide">Service d'aide</div>
    <div @click="handleDeconnexion" class="quitter">
      <svg width="48" height="48" viewBox="0 0 48 48" fill="none" xmlns="http://www.w3.org/2000/svg">
        <path d="M18 42H10C8.93913 42 7.92172 41.5786 7.17157 40.8284C6.42143 40.0783 6 39.0609 6 38V10C6 8.93913 6.42143 7.92172 7.17157 7.17157C7.92172 6.42143 8.93913 6 10 6H18M32 34L42 24M42 24L32 14M42 24H18" stroke="white" stroke-width="4" stroke-linecap="round" stroke-linejoin="round"/>
      </svg>
    </div>
  </header>
  <main class="main-content">
    <div class="description-container">
      <div class="description">
        Veuillez saisir le/les Référents pour cette ressource :
      </div>
    </div>

    <!-- Zone d'affichage des référents ajoutés -->
    <div class="teachers-list">
      <!-- Itération sur la liste des référents -->
      <div v-for="(teacher, index) in teachers" :key="teacher.id" class="teacher-display-card">
        Référent n°{{ index + 1 }} : {{ teacher.lastname }} {{ teacher.firstname }}
      </div>
    </div>

    <!-- Container du formulaire d'ajout et du bouton de validation -->
    <div class="container">
      <form class="teacher-card" @submit.prevent> <!-- On utilise @submit.prevent pour éviter le rechargement -->
        <div class="form-group">
          <label for="lastname">Nom</label>
          <input
              type="text"
              id="lastname"
              placeholder="Nom"
              v-model="lastname"
              required />
        </div>
        <div class="form-group">
          <label for="firstname">Prénom</label>
          <input
              type="text"
              id="firstname"
              placeholder="Prénom"
              v-model="firstname"
              required />
        </div>
      </form>

      <!-- Changement de click="handleAdd" en @click="handleAdd" -->
      <!-- Rendu du SVG sous forme de bouton d'ajout -->
      <button @click="handleAdd" class="add-teacher-button" title="Ajouter ce référent">
        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="24" height="24" fill="none"
             stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
          <rect x="3" y="3" width="18" height="18" rx="4" ry="4"/>
          <polyline points="9 12 12 15 15 9"/>
        </svg>
      </button>

      <button @click="handleValider" class="submit-btn">Valider</button>
    </div>
  </main>
</template>

<style scoped>

/* --- Styles spécifiques aux nouvelles fonctionnalités --- */

.teachers-list {
  margin-bottom: 30px;
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
  max-width: 450px; /* Align with teacher-card max-width */
}

.teacher-display-card {
  width: 100%;
  max-width: 400px;
  background-color: #f5f5f5;
  padding: 10px 20px;
  border-radius: 6px;
  margin-bottom: 10px;
  font-size: 1rem;
  font-weight: 500;
  color: #1E1E1E;
  border-left: 5px solid #E92533; /* Barre de couleur pour distinguer */
}

.add-teacher-button {
  background: none;
  border: none;
  cursor: pointer;
  padding: 0;
  margin: 20px;
  transition: transform 0.2s;
}
.add-teacher-button svg {
  height: 50px;
  width: 50px;
}
.add-teacher-button:hover {
  transform: scale(1.1);
}

.plus-icon {
  margin: 0;
}

/* --- Le reste de vos styles existants --- */

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
.container-connexion p{

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

.quitter {
  position: absolute;
  width: 48px;
  height: 48px;
  right: 5%;
  top: 64px;
  cursor: pointer;
}
.quitter:hover{
  opacity: 0.8;
}

.aide{
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

.aide:hover{
  opacity: 0.8;
}

/* Style du Contenu Principal */

.main-content {
  background-color: #FFFFFF;
  margin:auto;
  padding: 20px;
  font-family: 'Roboto', sans-serif;
  min-height: 100vh;
  padding-top: 172px;
  box-sizing: border-box;

  /* Ajout pour centrer le contenu comme demandé précédemment */
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
}

.description-container {
  margin-bottom: 20px;
  text-align: center;
}

.description {
  font-family: 'Roboto', sans-serif;
  font-style: normal;
  font-weight: 400;
  font-size: 32px;
  color: #E92533;
  margin-top: 20px;
  margin-bottom: 0; /* Supprimé car le conteneur gère déjà la marge */
}

/* Style de la Carte de Connexion */
.container{
  position: relative;
  width: 25%;
  margin: 0 auto;
  display :flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;
}

.teacher-card {
  width: 100%;
  max-width: 400px;
  background-color: #FFFFFF;
  padding: 2.5rem; /* 40px */
  border-radius: 8px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
  border: 1px solid #dcdcdc;
  margin-bottom: 0; /* La marge est maintenant autour du bouton d'ajout */
}

.form-group {
  margin-bottom: 1.5rem; /* 24px */
}

.form-group label {
  display: block;
  margin-bottom: 0.5rem; /* 8px */
  font-weight: bold;
  font-size: 0.9rem; /* 14px */
  color: black;
}

.form-group input {
  width: 100%;
  padding: 0.75rem; /* 12px */
  border: 1px solid #dcdcdc;
  border-radius: 4px;
  box-sizing: border-box;
  font-size: 1rem; /* 16px */
}

.form-group input:focus {
  outline: none;
  border-color: #B51621;
  box-shadow: 0 0 0 2px rgba(181, 22, 33, 0.2);
}

.submit-btn {
  width: 100%;
  max-width: 400px;
  padding: 0.8rem; /* 13px */
  border: none;
  border-radius: 4px;
  background-color: #B51621;
  color: #FFFFFF;
  font-size: 1rem; /* 16px */
  font-weight: bold;
  cursor: pointer;
  transition: background-color 0.2s ease;
}

.submit-btn:hover {
  background-color: #9c121b;
}

</style>