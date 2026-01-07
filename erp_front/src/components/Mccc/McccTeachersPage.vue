<script setup>
import {onMounted, ref} from 'vue';
import { useRouter } from 'vue-router';
import AppHeader from '../App/Header.vue';
import Sidebar from '../App/Sidebar.vue';
import {mcccStore} from "@/services/mcccStore.js";

const router = useRouter();
const lastname = ref('');
const firstname = ref('');
const errorMessage = ref('');


const handleRetour = () => {
  router.back();
};

// Fonction pour supprimer un référent
const handleRemove = (index) => {
  mcccStore.referents.splice(index, 1);
  mcccStore.registerMcccStore();
};

const handleAdd = () => {
  const trimmedLastname = lastname.value.trim();
  const trimmedFirstname = firstname.value.trim();

  errorMessage.value = '';

  if (trimmedLastname === '' || trimmedFirstname === '') {
    errorMessage.value = "Veuillez saisir le nom et le prénom."
    return;
  }

  const teacherExists = mcccStore.referents.some(teacher => {
    const existingLastname = teacher.lastname.trim().toLowerCase();
    const existingFirstname = teacher.firstname.trim().toLowerCase();

    const newLastname = trimmedLastname.toLowerCase();
    const newFirstname = trimmedFirstname.toLowerCase();

    return (existingLastname === newLastname) && (existingFirstname === newFirstname);
  });

  if (teacherExists) {
    errorMessage.value = `Le professeur ${trimmedLastname} ${trimmedFirstname} existe déjà.`;
    return;
  }

  const newTeacher = {
    lastname: trimmedLastname,
    firstname: trimmedFirstname
  };

  mcccStore.referents.push(newTeacher);

  lastname.value = '';
  firstname.value = '';

  mcccStore.registerMcccStore();
};

const handleValider = () => {
  router.push('/mccc-menu')
};

onMounted(() => {
  mcccStore.loadMcccStore();
});

</script>

<template>
  <Sidebar/>
  <AppHeader title="Référents de la" :inline="mcccStore.resourceCode"/>
  <main class="main-content">
    <div class="container">

      <div class="teachers-list">
        <div v-for="(teacher, index) in mcccStore.referents" :key="index" class="teacher-display-card">
          <div class="teacher-info">
            Référent n°{{ index + 1 }} : {{ teacher.lastname }} {{ teacher.firstname }}
          </div>
          <button class="delete-btn" @click="handleRemove(index)" title="Supprimer ce référent">
            <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
              <polyline points="3 6 5 6 21 6"></polyline>
              <path d="M19 6v14a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2V6m3 0V4a2 2 0 0 1 2-2h4a2 2 0 0 1 2 2v2"></path>
              <line x1="10" y1="11" x2="10" y2="17"></line>
              <line x1="14" y1="11" x2="14" y2="17"></line>
            </svg>
          </button>
        </div>
      </div>

      <form class="teacher-card" @submit.prevent>
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

      <button @click="handleAdd" class="add-teacher-button" title="Ajouter ce référent">
        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="24" height="24" fill="none"
             stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
          <rect x="3" y="3" width="18" height="18" rx="4" ry="4"/>
          <polyline points="9 12 12 15 15 9"/>
        </svg>
      </button>

      <p v-if="errorMessage" class="error-message">{{ errorMessage }}</p>

      <div class="container-btn">
        <div @click="handleValider" class="btn-sys">Valider les référents</div>
        <div @click="handleRetour" class="btn-sys">Annuler</div>
      </div>
    </div>
  </main>
</template>

<style scoped>
/* Styles existants conservés */
.main-content {
  font-family: 'Roboto', sans-serif;
  min-height: 100vh;
  padding-top: 200px;
  box-sizing: border-box;
}

.teachers-list {
  margin-bottom: 30px;
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
  max-width: 450px;
}

/* Modification de la carte d'affichage pour aligner le texte et la poubelle */
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
  border-left: 5px solid #E92533;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.teacher-info {
  flex: 1;
}

/* Style spécifique pour le bouton de suppression */
.delete-btn {
  background: none;
  border: none;
  color: #999;
  cursor: pointer;
  padding: 5px;
  display: flex;
  align-items: center;
  transition: color 0.2s, transform 0.2s;
}

.delete-btn:hover {
  color: #E92533;
  transform: scale(1.1);
}

/* Reste du CSS inchangé */
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
  color: green;
}

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
  background-color: #FFFFFF;
  padding: 2.5rem;
  border-radius: 8px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
  border: 1px solid #dcdcdc;
  margin-bottom: 20px;
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

.container-btn{
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: center;
}

.btn-sys{
  width: 150px;
  padding: 13px;
  border: none;
  text-align: center;
  border-radius: 4px;
  background-color: #B51621;
  color: #FFFFFF;
  font-size: 1rem;
  font-weight: bold;
  cursor: pointer;
  font-family: 'Roboto', sans-serif;
  transition: background-color 0.2s ease;
  position: relative;
  margin : 1%;
}

.btn-sys:hover{
  background: #999999;
  transform: translateY(-4px);
  cursor: pointer;
}

.error-message {
  color: #E92533;
  font-weight: bold;
  margin-top: -10px;
  margin-bottom: 20px;
  text-align: center;
}
</style>