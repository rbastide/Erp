<script setup>
import { ref, reactive } from 'vue';
import { useRouter } from 'vue-router';
import AppHeader from '../App/Header.vue';
import Sidebar from '../App/Sidebar.vue';

const router = useRouter();

const resources = reactive([
  { id: 'R1.01', title: 'Intitulé de la ressource' },
  { id: 'R1.02', title: 'Intitulé' }
]);

const editingIndex = ref(null);

const newResource = reactive({
  id: '',
  title: ''
});

const editedResource = reactive({
  id: '',
  title: ''
});

const showAddForm = ref(false);

const handleRetour = () => {
  router.back();
};

const handleValider = () => {
  router.push('/modif-saved');
};

const handleDelete = (index) => {
  if(confirm("Supprimer cette ressource ?")) {
    resources.splice(index, 1);
  }
};

const handleCancel = () => {
  editingIndex.value = null;
  showAddForm.value = false;
  newResource.id = '';
  newResource.title = '';
  editedResource.id = '';
  editedResource.title = '';
};

const handleAddResource = () => {
  showAddForm.value = true;
  editingIndex.value = null;
};

const addResourceToList = () => {
  if (newResource.id.trim() !== '' && newResource.title.trim() !== '') {
    resources.push({
      id: newResource.id.trim(),
      title: newResource.title.trim()
    });
    showAddForm.value = false;
    newResource.id = '';
    newResource.title = '';
  } else {
    alert("Veuillez remplir le 'Numéro de la ressource' et l''Intitulé'.");
  }
};

const handleModif = (index) => {
  if (editingIndex.value === index) {
    handleCancel();
  } else {
    showAddForm.value = false;
    editingIndex.value = index;
    editedResource.id = resources[index].id;
    editedResource.title = resources[index].title;
  }
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
  <Sidebar :dashboardAdmin="true" :dashboard="false"/>
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
                <polyline points="10 9 9 9 8 9"></polyline>
              </svg>
            </div>

            <h3 class="resource-id">{{ resource.id }}</h3>
            <p class="resource-title">{{ resource.title }}</p>

            <div class="card-actions">
              <button class="action-btn edit" @click="handleModif(index)" title="Modifier">
                <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M11 4H4a2 2 0 0 0-2 2v14a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2v-7"></path><path d="M18.5 2.5a2.121 2.121 0 0 1 3 3L12 15l-4 1 1-4 9.5-9.5z"></path></svg>
              </button>
              <button class="action-btn delete" @click="handleDelete(index)" title="Supprimer">
                <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><polyline points="3 6 5 6 21 6"></polyline><path d="M19 6v14a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2V6m3 0V4a2 2 0 0 1 2-2h4a2 2 0 0 1 2 2v2"></path></svg>
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

            <button class="save-btn" @click="saveModification(index)">
              <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><polyline points="20 6 9 17 4 12"></polyline></svg>
              Enregistrer
            </button>
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

            <button class="save-btn" @click="addResourceToList">
              <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><line x1="12" y1="5" x2="12" y2="19"></line><line x1="5" y1="12" x2="19" y2="12"></line></svg>
              Ajouter
            </button>
          </div>
        </div>

        <div v-else class="resource-card add-card" @click="handleAddResource">
          <div class="icon-circle plus">
            <svg width="40" height="40" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><line x1="12" y1="5" x2="12" y2="19"></line><line x1="5" y1="12" x2="19" y2="12"></line></svg>
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
.page-container {
  min-height: 100vh;
  background-color: #f8f9fa;
  font-family: 'Roboto', sans-serif;
}

.main-content {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 40px 20px;
  margin-top: 180px;
}

.grid-container {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  gap: 30px;
  width: 100%;
  max-width: 1200px;
  margin-bottom: 50px;
}

.resource-card {
  background: #ffffff;
  border-radius: 20px;
  min-height: 350px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding: 30px;
  box-shadow: 0 10px 25px rgba(0,0,0,0.05);
  transition: all 0.3s ease;
  position: relative;
  overflow: hidden;
  border: 1px solid transparent;
}

.resource-card:hover {
  transform: translateY(-8px);
  box-shadow: 0 15px 35px rgba(0,0,0,0.1);
  border-color: rgba(181, 22, 33, 0.1);
}

.resource-card.is-editing {
  border: 2px solid #B51621;
  transform: none;
  box-shadow: none;
}

.card-content {
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  text-align: center;
  height: 100%;
  justify-content: space-between;
}

.icon-circle {
  border-radius: 50%;
  display: flex;
  justify-content: center;
  align-items: center;
  transition: all 0.3s ease;
}

.icon-circle.big-icon {
  width: 130px;
  height: 130px;
  background: rgba(181, 22, 33, 0.05);
  color: #B51621;
  margin-bottom: 25px;
}

.icon-circle.big-icon svg {
  width: 75px;
  height: 75px;
}

.resource-id {
  margin: 0 0 5px 0;
  color: #2c3e50;
  font-size: 32px;
  font-weight: 900;
  letter-spacing: -0.5px;
}

.resource-title {
  font-size: 20px;
  color: #B51621;
  margin: 0 0 30px 0;
  font-weight: 500;
}

.card-actions {
  display: flex;
  gap: 20px;
  margin-top: auto;
}

.action-btn {
  width: 45px;
  height: 45px;
  border-radius: 50%;
  border: none;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: all 0.2s;
  background: #f0f2f5;
  color: #555;
}

.action-btn.edit:hover {
  background: #e3f2fd;
  color: #1976d2;
  transform: scale(1.1);
}

.action-btn.delete:hover {
  background: #ffebee;
  color: #c62828;
  transform: scale(1.1);
}

.edit-mode {
  justify-content: center;
  gap: 15px;
}

.edit-header {
  width: 100%;
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 15px;
  border-bottom: 1px solid #eee;
  padding-bottom: 10px;
}

.edit-header h4 {
  margin: 0;
  color: #B51621;
  font-size: 20px;
}

.close-icon {
  background: none;
  border: none;
  font-size: 24px;
  cursor: pointer;
  color: #999;
  transition: color 0.2s;
}
.close-icon:hover { color: #333; }

.input-group {
  width: 100%;
}

.card-input {
  width: 100%;
  padding: 12px 15px;
  border: 2px solid #eee;
  border-radius: 8px;
  font-size: 16px;
  box-sizing: border-box;
  font-family: 'Roboto', sans-serif;
  transition: border-color 0.3s;
}

.card-input:focus {
  outline: none;
  border-color: #B51621;
}

.save-btn {
  margin-top: 15px;
  width: 100%;
  padding: 12px;
  background: #B51621;
  color: white;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 10px;
  font-weight: bold;
  font-size: 16px;
  transition: background 0.3s, transform 0.2s;
}

.save-btn:hover {
  background: #94121b;
  transform: translateY(-2px);
}

.add-card {
  border: 3px dashed #e0e0e0;
  cursor: pointer;
  background: transparent;
  box-shadow: none;
}

.add-card:hover {
  background: rgba(181, 22, 33, 0.02);
  border-color: #B51621;
  transform: translateY(-5px);
}

.add-card p {
  font-weight: 700;
  color: #888;
  font-size: 20px;
  transition: color 0.3s;
}

.add-card:hover p {
  color: #B51621;
}

.icon-circle.plus {
  width: 80px;
  height: 80px;
  color: #888;
  background: #e0e0e0;
  margin-bottom: 15px;
}

.add-card:hover .icon-circle.plus {
  background: #B51621;
  color: white;
  transform: scale(1.1);
}

.global-actions {
  display: flex;
  gap: 25px;
  margin-top: 40px;
}

.btn-sys {
  padding: 15px 40px;
  border-radius: 50px;
  border: none;
  font-size: 18px;
  font-weight: 700;
  cursor: pointer;
  transition: all 0.2s;
  letter-spacing: 0.5px;
}

.btn-sys:hover {
  transform: translateY(-3px);
  box-shadow: 0 8px 15px rgba(0,0,0,0.1);
}

.btn-sys.primary {
  background: linear-gradient(135deg, #B51621 0%, #d92533 100%);
  color: white;
}

.btn-sys.primary:hover {
  background: linear-gradient(135deg, #94121b 0%, #B51621 100%);
}

.btn-sys.secondary {
  background-color: white;
  color: #555;
  border: 2px solid #e0e0e0;
}
.btn-sys.secondary:hover {
  border-color: #ccc;
  background-color: #f8f9fa;
}

@media (max-width: 600px) {
  .grid-container {
    grid-template-columns: 1fr;
  }
}
</style>