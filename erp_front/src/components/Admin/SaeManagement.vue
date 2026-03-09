<script setup>
import {computed, onMounted, reactive, ref} from 'vue';
import {useRouter} from 'vue-router';
import AppHeader from '../App/Header.vue';
import Sidebar from '../App/Sidebar.vue';
import api from '@/services/api';
import DeleteSaeModal from '../Information/DeleteSaeModal.vue';

const router = useRouter();

const saes = ref([]);
const editingIndex = ref(null);
const showAddForm = ref(false);
const searchQuery = ref('');

const showDeleteModal = ref(false);
const saeToDelete = ref(null);

const newSae = reactive({
  num: '',
  title: ''
});

const editedSae = reactive({
  saeID: null,
  num: '',
  title: ''
});

const fetchSaes = async () => {
  try {
    const response = await api.get('/sae/saes');
    if (Array.isArray(response.data)) {
      saes.value = response.data;
    } else if (response.data && Array.isArray(response.data.content)) {
      saes.value = response.data.content;
    }
  } catch (error) {
    console.error(error);
  }
};

onMounted(fetchSaes);

const filteredSaes = computed(() => {
  const query = searchQuery.value.toLowerCase().trim();
  if (!query) return saes.value;
  return saes.value.filter(sae =>
      (sae.num && sae.num.toLowerCase().includes(query)) ||
      (sae.title && sae.title.toLowerCase().includes(query))
  );
});

const handleValidate = () => router.back();

const handleAddSae = () => {
  showAddForm.value = true;
  editingIndex.value = null;
  newSae.num = '';
  newSae.title = '';
};

const handleModif = (sae, index) => {
  if (editingIndex.value === index) {
    handleCancel();
  } else {
    showAddForm.value = false;
    editingIndex.value = index;

    editedSae.saeID = sae.saeID;
    editedSae.num = sae.num;
    editedSae.title = sae.title;
  }
};

const handleCancel = () => {
  editingIndex.value = null;
  showAddForm.value = false;
  editedSae.saeID = null;
  editedSae.num = '';
  editedSae.title = '';
};

const handleDelete = (sae) => {
  saeToDelete.value = sae;
  showDeleteModal.value = true;
};

const confirmDeletion = async () => {
  if (!saeToDelete.value) return;
  const id = saeToDelete.value.saeID;

  try {
    if (id) {
      await api.delete(`/sae/${id}`);
    }
    const indexToDelete = saes.value.findIndex(s => s.saeID === id);
    if (indexToDelete !== -1) {
      saes.value.splice(indexToDelete, 1);
    }
    handleCancel();
    showDeleteModal.value = false;
    saeToDelete.value = null;
  } catch (error) {
    console.error(error);
    alert("Erreur lors de la suppression.");
  }
};

const saveSae = async (isNew = false) => {
  const saeObject = isNew
      ? {num: newSae.num, title: newSae.title}
      : { saeID: editedSae.saeID, num: editedSae.num, title: editedSae.title };

  if (!saeObject.num.trim() || !saeObject.title.trim()) {
    alert("Veuillez remplir le Numéro (ex: S2.01) et l'Intitulé.");
    return;
  }

  if(saes.value.some(sae => sae.num === saeObject.num && sae.title === saeObject.title)){
    alert("la SAE existe déjà")
    return;
  }

  try {
    await api.post('/sae/edit-sae', saeObject);
    await fetchSaes();

    if (isNew) {
      showAddForm.value = false;
      newSae.num = '';
      newSae.title = '';
    } else {
      editingIndex.value = null;
    }
  } catch (error) {
    console.error(error);
    alert("Erreur lors de l'enregistrement.");
  }
};
</script>

<template>
  <Sidebar/>
  <div class="page-container">
    <AppHeader title="Gestion des SAE"/>

    <main class="main-content">

      <div v-if="saes.length === 0 && !showAddForm" class="empty-state">
        Aucune SAE trouvée ou chargement en cours...
      </div>

      <div class="grid-container">

        <div v-if="!showAddForm && searchQuery === ''" class="resource-card add-card" @click="handleAddSae">
          <div class="icon-circle plus">
            <svg width="40" height="40" viewBox="0 0 24 24"  stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" style="fill: none;"><line x1="12" y1="5" x2="12" y2="19"></line><line x1="5" y1="12" x2="19" y2="12"></line></svg>
          </div>
          <p>Ajouter une SAE</p>
        </div>

        <div v-if="showAddForm" class="resource-card is-editing">
          <div class="card-content edit-mode">
            <div class="edit-header">
              <h4>Nouvelle SAE</h4>
              <button class="close-icon" @click="handleCancel">✕</button>
            </div>
            <div class="input-group">
              <label class="input-label">Numéro SAE</label>
              <input type="text" v-model="newSae.num" placeholder="Ex: S2.01" class="card-input" maxlength="7">
            </div>
            <div class="input-group">
              <label class="input-label">Intitulé max(25)</label>
              <input type="text" v-model="newSae.title" placeholder="Ex: Découverte..." class="card-input" maxlength="25">
            </div>
            <button class="save-btn" @click="saveSae(true)">
              <svg width="20" height="20" viewBox="0 0 24 24"  stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" style="fill: none;"><line x1="12" y1="5" x2="12" y2="19"></line><line x1="5" y1="12" x2="19" y2="12"></line></svg>
              Ajouter
            </button>
          </div>
        </div>

        <div v-for="(sae, index) in filteredSaes" :key="sae.saeID || index" class="resource-card" :class="{ 'is-editing': editingIndex === index }">

          <div v-if="editingIndex !== index" class="card-content view-mode">
            <div class="icon-circle big-icon">
              <svg width="24" height="24" viewBox="0 0 24 24"  xmlns="http://www.w3.org/2000/svg" style="fill: none;"><path fill-rule="evenodd" clip-rule="evenodd" d="M12.4388 7L14.8387 4H7V10H14.8387L12.4388 7ZM19 12H7V22H5V2H19L15 7L19 12Z" fill="currentColor"/></svg>
            </div>
            <h3 class="resource-id">{{ sae.num }}</h3>
            <p class="resource-title">{{ sae.title }}</p>
            <div class="card-actions">
              <button class="action-btn edit" @click="handleModif(sae, index)" title="Modifier">
                <svg width="20" height="20" viewBox="0 0 24 24"  stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" style="fill: none;"><path d="M11 4H4a2 2 0 0 0-2 2v14a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2v-7"></path><path d="M18.5 2.5a2.121 2.121 0 0 1 3 3L12 15l-4 1 1-4 9.5-9.5z"></path></svg>
              </button>

              <button class="action-btn delete" @click="handleDelete(sae)" title="Supprimer">
                <svg width="20" height="20" viewBox="0 0 24 24"  stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" style="fill: none;"><polyline points="3 6 5 6 21 6"></polyline><path d="M19 6v14a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2V6m3 0V4a2 2 0 0 1 2-2h4a2 2 0 0 1 2 2v2"></path></svg>
              </button>
            </div>
          </div>

          <div v-else class="card-content edit-mode">
            <div class="edit-header">
              <h4>Modification</h4>
              <button class="close-icon" @click="handleCancel">✕</button>
            </div>
            <div class="input-group">
              <label class="input-label">Numéro SAE</label>
              <input type="text" v-model="editedSae.num" placeholder="Ex: S2.01" class="card-input" maxlength="7">
            </div>
            <div class="input-group">
              <label class="input-label">Intitulé max(25)</label>
              <input type="text" v-model="editedSae.title" placeholder="Ex: Implémenter..." class="card-input" maxlength="25">
            </div>
            <button class="save-btn" @click="saveSae(false)">
              <svg width="20" height="20" viewBox="0 0 24 24"  stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" style="fill: none;"><polyline points="20 6 9 17 4 12"></polyline></svg>
              Enregistrer
            </button>
          </div>
        </div>

      </div>
    </main>

    <footer class="sticky-bar">
      <div class="sticky-wrapper">
        <div class="search-container">
          <svg class="search-icon" width="20" height="20" viewBox="0 0 24 24"  stroke="currentColor" stroke-width="2" style="fill: none;">
            <circle cx="11" cy="11" r="8"></circle>
            <line x1="21" y1="21" x2="16.65" y2="16.65"></line>
          </svg>
          <input type="text" v-model="searchQuery" placeholder="Rechercher une SAE..." class="search-input" />
        </div>
        <button @click="handleValidate" class="btn-sys primary">Terminer</button>
      </div>
    </footer>

    <DeleteSaeModal
        v-if="showDeleteModal"
        :saeNum="saeToDelete?.num"
        @confirm="confirmDeletion"
        @close="showDeleteModal = false"
    />

  </div>
</template>

<style scoped>
.page-container {
  min-height: 100vh;
  background-color: #f8f9fa;
  font-family: 'Roboto', sans-serif;
  padding-bottom: 100px;
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

.empty-state {
  margin-bottom: 30px;
  font-size: 18px;
  color: #999;
  font-style: italic;
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

.edit-header h4 { margin: 0; color: #B51621; font-size: 20px; }

.close-icon {
  background: none;
  border: none;
  font-size: 24px;
  cursor: pointer;
  color: #999;
  transition: color 0.2s;
}
.close-icon:hover { color: #333; }

.input-group { width: 100%; text-align: left; }
.input-label { display: block; font-size: 12px; color: #888; font-weight: bold; margin-bottom: 5px; margin-left: 5px; }

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
.card-input:focus { outline: none; border-color: #B51621; }

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
.save-btn:hover { background: #94121b; transform: translateY(-2px); }

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
.add-card:hover p { color: #B51621; }

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

.sticky-bar {
  position: fixed;
  bottom: 0;
  left: 0;
  width: 100%;
  background: white;
  box-shadow: 0 -5px 15px rgba(0,0,0,0.1);
  padding: 12px 0;
  z-index: 100;
}

.sticky-wrapper {
  max-width: 1200px;
  margin: 0 auto;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 40px;
}

.search-container { position: relative; width: 320px; }
.search-icon { position: absolute; left: 15px; top: 50%; transform: translateY(-50%); color: #999; }
.search-input { width: 100%; padding: 10px 15px 10px 40px; border-radius: 50px; border: 1px solid #ddd; outline: none; font-size: 14px; }

.btn-sys.primary {
  background: linear-gradient(135deg, #B51621 0%, #d92533 100%);
  color: white;
  padding: 12px 35px;
  border-radius: 50px;
  font-weight: bold;
  border: none;
  cursor: pointer;
  min-width: 160px;
  font-size: 15px;
}
.btn-sys.primary:hover {
  background: linear-gradient(135deg, #94121b 0%, #B51621 100%);
  transform: translateY(-2px);
  box-shadow: 0 4px 10px rgba(181, 22, 33, 0.3);
}

@media (max-width: 600px) {
  .grid-container {
    grid-template-columns: 1fr;
  }
  .sticky-wrapper {
    flex-direction: column;
    gap: 10px;
  }
  .search-container { width: 100%; }
  .btn-sys.primary { width: 100%; }
}
</style>