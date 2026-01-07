<script setup>
import { ref, reactive } from 'vue';
import { useRouter } from 'vue-router';
import AppHeader from '../App/Header.vue';
import Sidebar from '../App/Sidebar.vue';

const router = useRouter();

// Données initiales incluant le semestre
const resources = reactive([
  { id: 'R1.01', title: 'Initiation au développement', semestre: 1 },
  { id: 'R1.02', title: 'Développement interfaces', semestre: 1 }
]);

const editingIndex = ref(null);
const showAddForm = ref(false);

const newResource = reactive({
  id: '',
  title: '',
  semestre: null
});

const editedResource = reactive({
  id: '',
  title: '',
  semestre: null
});

// Empêche la saisie de caractères non numériques (e, +, -, ., ,)
const blockNonNumeric = (e) => {
  if (['e', 'E', '+', '-', '.', ','].includes(e.key)) {
    e.preventDefault();
  }
};

const handleRetour = () => router.push('/cancel');
const handleValider = () => router.push('/modif-saved');

const handleDelete = (index) => {
  if(confirm("Supprimer cette ressource ?")) {
    resources.splice(index, 1);
  }
};

const handleCancel = () => {
  editingIndex.value = null;
  showAddForm.value = false;
  Object.assign(newResource, { id: '', title: '', semestre: null });
};

const handleAddResource = () => {
  showAddForm.value = true;
  editingIndex.value = null;
};

const addResourceToList = () => {
  if (newResource.id && newResource.title && newResource.semestre) {
    resources.push({ ...newResource });
    handleCancel();
  } else {
    alert("Veuillez remplir tous les champs (Numéro, Intitulé et Semestre).");
  }
};

const handleModif = (index) => {
  showAddForm.value = false;
  editingIndex.value = index;
  Object.assign(editedResource, resources[index]);
};

const saveModification = (index) => {
  if (editedResource.id && editedResource.title && editedResource.semestre) {
    resources[index] = { ...editedResource };
    editingIndex.value = null;
  } else {
    alert("Veuillez remplir tous les champs.");
  }
};
</script>

<template>
  <Sidebar/>
  <div class="page-container">
    <AppHeader title="Gestion des ressources"/>

    <main class="main-content">
      <div class="grid-container">

        <div v-for="(resource, index) in resources" :key="index" class="resource-card" :class="{ 'is-editing': editingIndex === index }">

          <div v-if="editingIndex !== index" class="card-content view-mode">
            <div class="icon-circle big-icon">
              <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round">
                <path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"></path>
                <polyline points="14 2 14 8 20 8"></polyline>
                <line x1="16" y1="13" x2="8" y2="13"></line>
                <line x1="16" y1="17" x2="8" y2="17"></line>
              </svg>
            </div>

            <h3 class="resource-id">{{ resource.id }}</h3>
            <p class="resource-title">{{ resource.title }}</p>
            <span class="semester-badge">Semestre {{ resource.semestre }}</span>

            <div class="card-actions">
              <button class="action-btn edit" @click="handleModif(index)" title="Modifier">
                <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M11 4H4a2 2 0 0 0-2 2v14a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2v-7"></path><path d="M18.5 2.5a2.121 2.121 0 0 1 3 3L12 15l-4 1 1-4 9.5-9.5z"></path></svg>
              </button>
              <button class="action-btn delete" @click="handleDelete(index)" title="Supprimer">
                <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><polyline points="3 6 5 6 21 6"></polyline><path d="M19 6v14a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2V6m3 0V4a2 2 0 0 1 2-2h4a2 2 0 0 1 2 2v2"></path></svg>
              </button>
            </div>
          </div>

          <div v-else class="card-content edit-mode">
            <div class="edit-header">
              <h4>Modifier {{ editedResource.id }}</h4>
              <button class="close-icon" @click="handleCancel">✕</button>
            </div>

            <div class="input-group">
              <input type="text" v-model="editedResource.id" placeholder="Numéro (ex: R1.01)" class="card-input">
            </div>

            <div class="input-group">
              <input type="text" v-model="editedResource.title" placeholder="Intitulé" class="card-input">
            </div>

            <div class="input-group">
              <label class="field-label">Semestre</label>
              <input
                  type="number"
                  v-model.number="editedResource.semestre"
                  placeholder="Ex: 1"
                  class="card-input"
                  @keypress="blockNonNumeric"
              >
            </div>

            <button class="save-btn" @click="saveModification(index)">Enregistrer</button>
          </div>
        </div>

        <div v-if="showAddForm" class="resource-card is-editing">
          <div class="card-content edit-mode">
            <div class="edit-header">
              <h4>Nouvelle Ressource</h4>
              <button class="close-icon" @click="handleCancel">✕</button>
            </div>

            <div class="input-group">
              <input type="text" v-model="newResource.id" placeholder="Numéro (ex: R1.01)" class="card-input">
            </div>

            <div class="input-group">
              <input type="text" v-model="newResource.title" placeholder="Intitulé" class="card-input">
            </div>

            <div class="input-group">
              <label class="field-label">Semestre</label>
              <input
                  type="number"
                  v-model.number="newResource.semestre"
                  placeholder="Ex: 1"
                  class="card-input"
                  @keypress="blockNonNumeric"
              >
            </div>

            <button class="save-btn" @click="addResourceToList">Ajouter</button>
          </div>
        </div>

        <div v-else class="resource-card add-card" @click="handleAddResource">
          <div class="icon-circle plus">
            <svg width="40" height="40" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><line x1="12" y1="5" x2="12" y2="19"></line><line x1="5" y1="12" x2="19" y2="12"></line></svg>
          </div>
          <p>Ajouter une ressource</p>
        </div>

      </div>

      <div class="global-actions">
        <button @click="handleValider" class="btn-sys primary">Terminer</button>
        <button @click="handleRetour" class="btn-sys secondary">Retour</button>
      </div>

    </main>
  </div>
</template>

<style scoped>
.page-container { min-height: 100vh; background-color: #f8f9fa; font-family: 'Roboto', sans-serif; }
.main-content { display: flex; flex-direction: column; align-items: center; padding: 40px 20px; margin-top: 180px; }
.grid-container { display: grid; grid-template-columns: repeat(auto-fit, minmax(300px, 1fr)); gap: 30px; width: 100%; max-width: 1200px; margin-bottom: 50px; }

.resource-card {
  background: #ffffff; border-radius: 20px; min-height: 380px; display: flex; flex-direction: column;
  justify-content: center; align-items: center; padding: 30px; box-shadow: 0 10px 25px rgba(0,0,0,0.05);
  transition: all 0.3s ease; position: relative; border: 1px solid transparent;
}

.resource-card:hover:not(.is-editing) { transform: translateY(-8px); box-shadow: 0 15px 35px rgba(0,0,0,0.1); border-color: rgba(181, 22, 33, 0.1); }
.resource-card.is-editing { border: 2px solid #B51621; }

.card-content { width: 100%; display: flex; flex-direction: column; align-items: center; text-align: center; height: 100%; justify-content: space-between; }

.icon-circle.big-icon { width: 110px; height: 110px; background: rgba(181, 22, 33, 0.05); color: #B51621; border-radius: 50%; display: flex; justify-content: center; align-items: center; margin-bottom: 20px; }
.icon-circle.big-icon svg { width: 60px; height: 60px; }

.resource-id { margin: 0; color: #2c3e50; font-size: 32px; font-weight: 900; }
.resource-title { font-size: 18px; color: #B51621; margin: 5px 0 10px 0; font-weight: 500; }
.semester-badge { background: #f0f2f5; color: #64748b; padding: 5px 15px; border-radius: 20px; font-weight: bold; font-size: 14px; margin-bottom: 15px; }

.card-actions { display: flex; gap: 15px; margin-top: auto; }
.action-btn { width: 45px; height: 45px; border-radius: 50%; border: none; cursor: pointer; display: flex; align-items: center; justify-content: center; transition: all 0.2s; background: #f0f2f5; color: #555; }
.action-btn:hover { transform: scale(1.1); }
.action-btn.edit:hover { background: #e3f2fd; color: #1976d2; }
.action-btn.delete:hover { background: #ffebee; color: #c62828; }

.edit-mode { gap: 10px; }
.edit-header { width: 100%; display: flex; justify-content: space-between; border-bottom: 1px solid #eee; padding-bottom: 8px; margin-bottom: 10px; }
.edit-header h4 { margin: 0; color: #B51621; }
.close-icon { background: none; border: none; font-size: 20px; cursor: pointer; color: #999; }

.input-group { width: 100%; text-align: left; }
.field-label { display: block; font-size: 11px; font-weight: bold; color: #888; text-transform: uppercase; margin: 0 0 4px 4px; }
.card-input { width: 100%; padding: 10px 15px; border: 2px solid #eee; border-radius: 8px; font-size: 15px; box-sizing: border-box; }
.card-input:focus { outline: none; border-color: #B51621; }

/* Suppression des flèches sur input type number */
input::-webkit-outer-spin-button, input::-webkit-inner-spin-button { -webkit-appearance: none; margin: 0; }

.save-btn { width: 100%; padding: 12px; background: #B51621; color: white; border: none; border-radius: 8px; cursor: pointer; font-weight: bold; margin-top: 10px; }

.add-card { border: 3px dashed #e0e0e0; cursor: pointer; background: transparent; box-shadow: none; }
.add-card:hover { border-color: #B51621; background: rgba(181, 22, 33, 0.02); }
.add-card p { font-weight: 700; color: #888; font-size: 18px; margin-top: 15px; }
.icon-circle.plus { width: 70px; height: 70px; background: #e0e0e0; color: #888; border-radius: 50%; display: flex; justify-content: center; align-items: center; transition: 0.3s; }
.add-card:hover .icon-circle.plus { background: #B51621; color: white; }

.global-actions { display: flex; gap: 20px; margin-top: 40px; }
.btn-sys { padding: 15px 40px; border-radius: 50px; border: none; font-size: 16px; font-weight: 700; cursor: pointer; transition: 0.2s; }
.btn-sys.primary { background: #B51621; color: white; }
.btn-sys.secondary { background: white; color: #555; border: 1px solid #ddd; }
</style>