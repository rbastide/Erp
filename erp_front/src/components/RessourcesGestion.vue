<script setup>
import { ref, reactive } from 'vue';
import { useRouter } from 'vue-router';
import AppHeader from './Header.vue';

const router = useRouter();

const resources = reactive([
  { id: 'R1.01', title: 'Intitulé de la ressource' },
  { id: 'R1.02', title: 'Intitulé' }
]);

const showAddForm = ref(false);
const editingIndex = ref(null);
const newResource = reactive({
  id: '',
  title: ''
});
const editedResource = reactive({
  id: '',
  title: ''
});

const handleRetour = () => {
  router.push('/home-admin');
};

const handleValider = () => {
  router.push('/modif-saved');
}

const handleAide = () => {
  router.push('/aide');
};

const handleDeconnexion = () => {
  router.push('/deconnexion');
};

const handleDelete = (index) => {
  resources.splice(index, 1);
};

const handleCancel = () => {
  showAddForm.value = false;
  editingIndex.value = null;
  newResource.id = '';
  newResource.title = '';
  editedResource.id = '';
  editedResource.title = '';
};

const handleAddResource = () => {
  showAddForm.value = true;
  editingIndex.value = null;
  newResource.id = '';
  newResource.title = '';
};

const addResourceToList = () => {
  if (newResource.id.trim() !== '' && newResource.title.trim() !== '') {
    resources.push({
      id: newResource.id.trim(),
      title: newResource.title.trim()
    });
    showAddForm.value = false;
  } else {
    alert("Veuillez remplir le 'Numéro de la ressource' et l''Intitulé'.");
  }
};

// --- Logique de Modification ---
const handleModif = (index) => {
  if (editingIndex.value === index) {
    editingIndex.value = null;
    return;
  }

  editingIndex.value = index;
  showAddForm.value = false;

  editedResource.id = resources[index].id;
  editedResource.title = resources[index].title;
};

const saveModification = (index) => {
  if (editedResource.id.trim() !== '' && editedResource.title.trim() !== '') {
    resources[index].id = editedResource.id.trim();
    resources[index].title = editedResource.title.trim();

    editingIndex.value = null;
  } else {
    alert("Veuillez remplir le 'Numéro de la ressource' et l''Intitulé'.");
  }
};
</script>

<template>
  <AppHeader title="Bonjour," inline="Prénom NOM"/>
  <div class="main-content">
    <p class="description">Voici la liste des ressources </p>
    <div class="version-list-container">
      <ul class="version-list">

        <template v-for="(resource, index) in resources" :key="index">
          <li class="version-item">
            <span class="resource">{{ resource.id }} : {{ resource.title }}</span>
            <span class="icon-container">
              <svg @click="handleDelete(index)" class="del-icon" width="40" height="40" viewBox="0 0 50 63" fill="none" xmlns="http://www.w3.org/2000/svg">
                <path d="M44.2307 5.90283H5.7691C2.58794 5.90283 0 8.55077 0 11.8057V19.6764C0 20.7647 0.861309 21.6438 1.92282 21.6438H3.84563V57.0612C3.84563 60.3155 6.43409 62.964 9.61474 62.964H40.3842C43.5649 62.964 46.1533 60.3155 46.1533 57.0612L46.1539 21.6438H48.0767C49.1382 21.6438 49.9995 20.7641 49.9995 19.6764L50 11.8057C50 8.55077 47.4115 5.90283 44.2309 5.90283H44.2307ZM42.3073 57.0622C42.3073 58.1484 41.444 59.0296 40.3845 59.0296H9.615C8.55546 59.0296 7.69218 58.1483 7.69218 57.0622L7.69167 21.6449H42.3065L42.3073 57.0622ZM46.1536 17.7091H3.84538V11.8062C3.84538 10.72 4.70871 9.83881 5.76819 9.83881H44.2297C45.2893 9.83881 46.1526 10.7201 46.1526 11.8062L46.1536 17.7091Z" fill="black"/>
              <path d="M17.3076 3.93479H32.6917C33.7532 3.93479 34.6145 3.05506 34.6145 1.96739C34.6145 0.879152 33.7532 0 32.6917 0H17.3076C16.246 0 15.3848 0.879723 15.3848 1.96739C15.3848 3.05563 16.2461 3.93479 17.3076 3.93479Z" fill="black"/>
              <path d="M34.6152 55.0929C35.6768 55.0929 36.538 54.2132 36.538 53.1255V27.5465C36.538 26.4583 35.6767 25.5791 34.6152 25.5791C33.5536 25.5791 32.6924 26.4588 32.6924 27.5465V53.1255C32.6919 54.2138 33.5537 55.0929 34.6152 55.0929Z" fill="black"/>
              <path d="M25 55.0929C26.0615 55.0929 26.9228 54.2132 26.9228 53.1255V27.5465C26.9228 26.4583 26.0615 25.5791 25 25.5791C23.9385 25.5791 23.0771 26.4588 23.0771 27.5465V53.1255C23.0766 54.2138 23.9385 55.0929 25 55.0929Z" fill="black"/>
              <path d="M15.3842 55.0929C16.4458 55.0929 17.3071 54.2132 17.3071 53.1255V27.5465C17.3071 26.4583 16.4458 25.5791 15.3842 25.5791C14.3227 25.5791 13.4614 26.4588 13.4614 27.5465V53.1255C13.4609 54.2138 14.3227 55.0929 15.3842 55.0929Z" fill="black"/>
            </svg>
            <svg @click="handleModif(index)" class="pen-icon" width="40" height="40" viewBox="0 0 59 63" fill="none" xmlns="http://www.w3.org/2000/svg">
              <path d="M40.0531 0.767145L5.63823 37.4929C5.44144 37.7049 5.28276 37.9536 5.1708 38.2271C5.1403 38.2993 5.11552 38.3715 5.08455 38.4503C5.05405 38.5292 5.01069 38.6141 4.99259 38.7061L0.0760928 59.6926C-0.138328 60.5916 0.10754 61.5446 0.723647 62.1995C1.33976 62.8539 2.23367 63.1127 3.0751 62.8798L22.7411 57.6332C22.8273 57.6332 22.9007 57.561 22.9807 57.5351L23.1899 57.443C23.4463 57.3236 23.6793 57.1542 23.878 56.9447L58.2928 20.219H58.2933C58.7493 19.7242 59.0038 19.0571 59 18.363C59 13.4927 57.1869 8.8217 53.9597 5.37823C50.7329 1.93425 46.3558 2.79973e-06 41.7924 2.79973e-06C41.1401 -0.00101424 40.5144 0.275098 40.0532 0.767308L40.0531 0.767145ZM9.25135 42.1752C11.8254 42.6023 14.2041 43.8939 16.0433 45.8622C17.8821 47.8306 19.0853 50.3735 19.4778 53.1209L5.84024 56.7281L9.25135 42.1752ZM23.7977 49.5991C23.2021 47.5977 22.2872 45.7208 21.0936 44.051C21.2027 43.9844 21.3071 43.9101 21.4072 43.8283L52.0247 11.1483C53.1792 12.9865 53.8754 15.1079 54.0464 17.3132L23.7977 49.5991ZM48.5516 7.44255L17.9279 40.116C17.8512 40.2223 17.7812 40.3341 17.7188 40.4501C16.154 39.1763 14.3952 38.2005 12.5198 37.5649L42.7677 5.28459C44.8362 5.46612 46.8269 6.20903 48.5509 7.44208L48.5516 7.44255Z" fill="black"/>
            </svg>
          </span>
          </li>

          <li v-if="editingIndex === index" class="add-resource-item">
            <svg @click="handleCancel" class="cancel-icon" viewBox="0 0 100 100" fill="none" xmlns="http://www.w3.org/2000/svg">
              <path d="M30 30L70 70M70 30L30 70" stroke="black" stroke-width="5" stroke-linecap="round"/>
            </svg>
            <div class="input-container">
              <div class="input-group-field">
                <label for="edit-resource-id">Numéro de la ressource</label>
                <input id="edit-resource-id" type="text" v-model="editedResource.id" :class="{ 'input-field': true }">
              </div>
              <div class="input-group-field">
                <label for="edit-resource-title">Intitulé</label>
                <input id="edit-resource-title" type="text" v-model="editedResource.title" :class="{ 'input-field': true }">
              </div>
            </div>
            <svg @click="saveModification(index)" class="add-arrow-icon" width="48" height="48" viewBox="0 0 100 100" fill="none" xmlns="http://www.w3.org/2000/svg">
              <circle cx="50" cy="50" r="45" stroke="black" stroke-width="3"/>
              <path d="M40 30L65 50L40 70" stroke="black" stroke-width="5" stroke-linecap="round" stroke-linejoin="round"/>
            </svg>
          </li>
        </template>

        <li v-if="showAddForm" class="add-resource-item">
          <svg @click="handleCancel" class="cancel-icon" viewBox="0 0 100 100" fill="none" xmlns="http://www.w3.org/2000/svg">
            <path d="M30 30L70 70M70 30L30 70" stroke="black" stroke-width="5" stroke-linecap="round"/>
          </svg>
          <div class="input-container">
            <div class="input-group-field">
              <label for="resource-id">Numéro de la ressource</label>
              <input id="resource-id" type="text" v-model="newResource.id" :class="{ 'input-field': true }">
            </div>
            <div class="input-group-field">
              <label for="resource-title">Intitulé</label>
              <input id="resource-title" type="text" v-model="newResource.title" :class="{ 'input-field': true }">
            </div>
          </div>
          <svg @click="addResourceToList" class="add-arrow-icon" width="48" height="48" viewBox="0 0 100 100" fill="none" xmlns="http://www.w3.org/2000/svg">
            <circle cx="50" cy="50" r="45" stroke="black" stroke-width="3"/>
            <path d="M40 30L65 50L40 70" stroke="black" stroke-width="5" stroke-linecap="round" stroke-linejoin="round"/>
          </svg>
        </li>

        <div class="btn-container">
          <svg @click="handleAddResource" v-if="!showAddForm && editingIndex === null" class="plus-icon" width="65" height="65" viewBox="0 0 65 65" fill="none" xmlns="http://www.w3.org/2000/svg">
            <path d="M36 23.3333V46.6667M24 35H48M15 8.75H57C60.3137 8.75 63 11.3617 63 14.5833V55.4167C63 58.6383 60.3137 61.25 57 61.25H15C11.6863 61.25 9 58.6383 9 55.4167V14.5833C9 11.3617 11.6863 8.75 15 8.75Z" stroke="#1E1E1E" stroke-width="4" stroke-linecap="round" stroke-linejoin="round"/>
          </svg>
          <div class="container-btn">
            <div @click="handleValider" class="btn-sys">Valider</div>
            <div @click="handleRetour" class="btn-sys">Annuler</div>
          </div>
        </div>
      </ul>
    </div>
  </div>
</template>

<style scoped>
.main-content {
  display: flex;
  width: 90%;
  margin: auto;
  margin-top: 254px;
  justify-content: center;
  align-items: center;
}

.description{
  position: absolute;
  width: 672px;
  height: 66px;
  left: 38px;
  top: 196px;
  font-family: 'Roboto', sans-serif;
  font-style: normal;
  font-weight: 400;
  font-size: 32px;
  line-height: 38px;
  color: #E92533;
  margin-bottom: 20px;
}

.version-list-container {
  display: flex;
  align-items: center;
  justify-content: center;
  margin: auto;
  width: 500px;
  font-family: 'Roboto', sans-serif;
  padding: 10px;
}

.version-list {
  width: 100%;
  list-style: none;
  padding: 0;
  margin: 0;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
}

.version-item .last-item {
  background-color: #dcdcdc;
  margin-bottom: 0;
}

.version-item {
  display: inline-flex;
  align-items: center;
  justify-content: space-between;
  flex-wrap:nowrap;
  width: fit-content;
  max-width: 10000%;
  padding: 10px 20px;
  margin: 0 0 15px 0;
  gap: 24px;
  background: #D9D9D9;
  border: 1px solid rgba(0, 0, 0, 0.25);
  box-shadow: 0 4px 4px rgba(0, 0, 0, 0.25);
  border-radius: 6px;
  min-height: 80px;
}

.resource{
  white-space: nowrap;
  font-family: 'Roboto', sans-serif;
  font-weight: 510;
  font-size: 40px;
  padding-right: 20px;
}

.icon-container{
  display: flex;
  align-items: center;
  gap: 5px;
}

.pen-icon:hover, .del-icon:hover{
  cursor: pointer;
  transform: scale(1.1);
}

.plus-icon:hover{
  cursor: pointer;
  transform: scale(1.1);
}

.btn-container{
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 24px;
  margin-top: 40px;
  align-self: center;
}

.btn-quitter{
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
}

.btn-quitter:hover{
  background: #999999;
  transform: translateY(-4px);
  cursor: pointer;
}

.add-resource-item {
  display: flex;
  align-items: center;
  justify-content: flex-start;
  width: 480px;
  padding: 15px 20px 15px 15px;
  margin: 0 0 15px 0;
  gap: 5px;
  background: #FFFFFF;
  border-radius: 10px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.5);
  min-height: 100px;
}

.input-container {
  display: flex;
  flex-direction: column;
  flex-grow: 1;
  gap: 10px;
}

.input-group-field {
  display: flex;
  align-items: center;
  gap: 10px;
}

.input-group-field label {
  font-family: 'Roboto', sans-serif;
  font-size: 16px;
  font-weight: 400;
  color: #1E1E1E;
  text-align: right;
  width: 150px;
  flex-shrink: 0;
}

.input-group-field input.input-field {
  padding: 5px;
  border: none;
  border-radius: 0;
  font-size: 18px;
  background-color: #D9D9D9;
  height: 25px;
  flex-grow: 1;
}

.add-arrow-icon {
  width: 45px;
  height: 45px;
  cursor: pointer;
  transition: transform 0.2s ease;
  flex-shrink: 0;
  border-radius: 50%;
  padding: 2px;
  margin-left: 5px;
}
.add-arrow-icon:hover {
  transform: scale(1.1);
}

.cancel-icon {
  width: 30px;
  height: 30px;
  cursor: pointer;
  transition: transform 0.2s ease;
  flex-shrink: 0;
  padding: 2px;
  position: relative;
  left: -5px;
  top: -40px;
}
.cancel-icon:hover {
  transform: scale(1.1);
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
</style>