<script setup>
import {onMounted, ref} from 'vue';
import { useRouter } from 'vue-router';
import AppHeader from './Header.vue';
import {mcccStore} from "@/services/mcccStore.js";

const router = useRouter();
const lastname = ref('');
const firstname = ref('');
const errorMessage = ref('');


const handleRetour = () => {
  router.push('/mccc-menu');
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
  <AppHeader title="Référents"/>
  <main class="main-content">
    <div class="description">
      Veuillez saisir le/les Référents pour cette ressource :
    </div>

    <div class="container">
      <div class="teachers-list">
        <div v-for="(teacher, index) in mcccStore.referents" :key="teacher" class="teacher-display-card">
          Référent n°{{ index + 1 }} : {{ teacher.lastname }} {{ teacher.firstname }}
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
.main-content {
  font-family: 'Roboto', sans-serif;
  min-height: 100vh;
  padding-top: 172px;
  box-sizing: border-box;
}

.description {
  font-family: 'Roboto', sans-serif;
  font-style: normal;
  font-weight: 400;
  font-size: 32px;
  color: #E92533;
  margin: 40px;
}

/* Style de la Carte teacher */

.teachers-list {
  margin-bottom: 30px;
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
  max-width: 450px;
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
  border-left: 5px solid #E92533;
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
  padding: 2.5rem; /* 40px */
  border-radius: 8px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
  border: 1px solid #dcdcdc;
  margin-bottom: 20px;
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
  font-size: 1rem; /* 16px */
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