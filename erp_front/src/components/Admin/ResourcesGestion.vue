<script setup>
import { ref, reactive, onMounted, computed } from 'vue';
import { useRouter } from 'vue-router';
import AppHeader from '../App/Header.vue';
import Sidebar from '../App/Sidebar.vue';
import api from '@/services/api';
import ErrorSaveModal from "@/components/Information/ErrorSaveModal.vue";

const router = useRouter();
const resources = ref([]);
const editingIndex = ref(null);
const isAdding = ref(false);
const searchQuery = ref('');
const showErrorModal = ref(false);

const editedResource = reactive({
  resourceID: null,
  num: '',
  name: '',
  semester: null
});

const fetchResources = async () => {
  try {
    const response = await api.get('resources/resources');
    resources.value = Array.isArray(response.data) ? response.data : (response.data.content || []);
  } catch (error) {
    console.error(error);
  }
};

onMounted(fetchResources);

const filteredResources = computed(() => {
  const query = searchQuery.value.toLowerCase().trim();
  if (!query) return resources.value;

  if (query.startsWith('#')) {
    const targetSem = query.substring(1);
    if (!targetSem) return resources.value;
    return resources.value.filter(res => {
      const val = res.semester || null;
      return val && val.toString() === targetSem;
    });
  }

  return resources.value.filter(res => {
    const num = res.num ? res.num.toLowerCase() : '';
    const name = res.name ? res.name.toLowerCase() : '';
    return num.includes(query) || name.includes(query);
  });
});

const handleDelete = async (resourceID, num) => {
  if (confirm(`Supprimer la ressource ${num} ?`)) {
    try {
      await api.delete(`resources/${resourceID}`);
      resources.value = resources.value.filter(res => res.resourceID !== resourceID);
    } catch (error) {
      showErrorModal.value = true;
    }
  }
};

const saveResource = async () => {
  if (!editedResource.num || !editedResource.name || editedResource.semester === null) {
    alert("Veuillez remplir tous les champs.");
    return;
  }

  if (editedResource.num.length > 5) {
    alert("Le code ne doit pas dépasser 5 caractères.");
    return;
  }

  if (editedResource.semester < 1) {
    alert("Le semestre doit être au minimum 1.");
    return;
  }

  const resourceData = {
    num: editedResource.num,
    name: editedResource.name,
    semester: parseInt(editedResource.semester)
  };

  if (editedResource.resourceID !== null) {
    resourceData.resourceID = editedResource.resourceID;
  }

  try {
    await api.post('resources/editResource', [resourceData]);
    await fetchResources();
    handleCancel();
  } catch (error) {
    showErrorModal.value = true;
  }
};

const handleModif = (resource, index) => {
  isAdding.value = false;
  editingIndex.value = index;
  Object.assign(editedResource, {
    resourceID: resource.resourceID,
    num: resource.num,
    name: resource.name,
    semester: resource.semester || resource.semester
  });
};

const handleAddResource = () => {
  editingIndex.value = null;
  isAdding.value = true;
  Object.assign(editedResource, {
    resourceID: null,
    num: '',
    name: '',
    semester: null
  });
};

const handleCancel = () => {
  editingIndex.value = null;
  isAdding.value = false;
  Object.assign(editedResource, {
    resourceID: null,
    num: '',
    name: '',
    semester: null
  });
};

const handleValider = () => router.push('/home-admin');
</script>

<template>
  <Sidebar />
  <div class="page-container">
    <AppHeader title="Gestion des ressources" />
    <main class="main-content">
      <div class="grid-container">
        <div
            v-if="!isAdding"
            class="user-card add-card compact-add"
            @click="handleAddResource"
            v-show="searchQuery === ''"
        >
          <div class="icon-circle plus-circle">+</div>
          <p class="add-label">Ajouter une ressource</p>
        </div>

        <div v-if="isAdding || editingIndex !== null" class="user-card is-editing">
          <div class="card-content edit-mode">
            <div class="edit-header">
              <h4>{{ isAdding ? 'Nouvelle ressource' : 'Édition' }}</h4>
              <button class="close-icon" @click="handleCancel">✕</button>
            </div>
            <div class="input-group-compact">
              <label class="compact-label">Code (max 5)</label>
              <input
                  type="text"
                  v-model="editedResource.num"
                  class="card-input-compact"
                  maxlength="5"
                  placeholder="ex: R1.01"
              >
            </div>
            <div class="input-group-compact">
              <label class="compact-label">Nom</label>
              <input type="text" v-model="editedResource.name" class="card-input-compact">
            </div>
            <div class="input-group-compact">
              <label class="compact-label">Semestre</label>
              <input
                  type="number"
                  v-model.number="editedResource.semester"
                  class="card-input-compact"
                  min="1"
                  placeholder="Saisir 1, 2..."
              >
            </div>
            <button class="save-btn-compact" @click="saveResource">
              {{ isAdding ? 'Créer' : 'Enregistrer' }}
            </button>
          </div>
        </div>

        <template v-for="(resource, index) in filteredResources" :key="resource.resourceID">
          <div v-if="editingIndex !== index" class="user-card">
            <div class="card-content view-mode">
              <div class="icon-circle small-icon">
                <svg width="24" height="24" viewBox="0 0 24 24"  stroke="currentColor" stroke-width="2" fill="none">
                  <path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"></path>
                  <polyline points="14 2 14 8 20 8"></polyline>
                </svg>
              </div>
              <h3 class="res-num">{{ resource.num }}</h3>
              <p class="res-name">{{ resource.name }}</p>
              <span class="role-badge">Semestre {{ resource.semester || resource.semester }}</span>
              <div class="card-actions">
                <button class="action-btn-mini edit" @click="handleModif(resource, index)">
                  <svg width="16" height="16" viewBox="0 0 24 24"  stroke="currentColor" stroke-width="2.5" fill="none">
                    <path d="M11 4H4a2 2 0 0 0-2 2v14a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2v-7"></path>
                    <path d="M18.5 2.5a2.121 2.121 0 0 1 3 3L12 15l-4 1 1-4 9.5-9.5z"></path>
                  </svg>
                </button>
                <button class="action-btn-mini delete" @click="handleDelete(resource.resourceID, resource.num)">
                  <svg width="16" height="16" viewBox="0 0 24 24"  stroke="currentColor" stroke-width="2.5" fill="none">
                    <polyline points="3 6 5 6 21 6"></polyline>
                    <path d="M19 6v14a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2V6m3 0V4a2 2 0 0 1 2-2h4a2 2 0 0 1 2 2v2"></path>
                  </svg>
                </button>
              </div>
            </div>
          </div>
        </template>
      </div>
    </main>

    <footer class="sticky-bar">
      <div class="sticky-wrapper">
        <div class="search-container">
          <svg class="search-icon" width="20" height="20" viewBox="0 0 24 24"  stroke="currentColor" stroke-width="2" fill="none">
            <circle cx="11" cy="11" r="8"></circle>
            <line x1="21" y1="21" x2="16.65" y2="16.65"></line>
          </svg>
          <input
              type="text"
              v-model="searchQuery"
              placeholder="Recherche par code, nom ou semestre (ex: #2)"
              class="search-input"
          />
        </div>
        <button @click="handleValider" class="btn-sys primary">Terminer</button>
      </div>
    </footer>
    <ErrorSaveModal
        v-if="showErrorModal"
        @close="showErrorModal = false"
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
  padding: 20px;
  margin-top: 170px;
}

.grid-container {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(220px, 1fr));
  gap: 20px;
  width: 100%;
  max-width: 1200px;
  margin-bottom: 50px;
}

.user-card {
  background: #ffffff;
  border-radius: 12px;
  min-height: 220px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding: 15px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.05);
  transition: all 0.3s ease;
  border: 1px solid transparent;
  position: relative;
}

.user-card.is-editing {
  border: 2px solid #B51621;
  min-height: 280px;
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
  width: 50px;
  height: 50px;
  background: rgba(181, 22, 33, 0.05);
  border-radius: 50%;
  display: flex;
  justify-content: center;
  align-items: center;
  margin-bottom: 10px;
  color: #B51621;
  transition: all 0.3s ease;
}

.small-icon svg {
  width: 24px;
  height: 24px;
}

.res-num {
  margin: 0;
  color: #333;
  font-size: 18px;
  font-weight: 700;
}

.res-name {
  font-size: 14px;
  color: #B51621;
  margin: 4px 0;
  font-weight: 500;
  min-height: 40px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.role-badge {
  background: #f0f0f0;
  padding: 4px 12px;
  border-radius: 15px;
  font-size: 11px;
  color: #666;
  font-weight: bold;
}

.card-actions {
  display: flex;
  gap: 10px;
  margin-top: 15px;
}

.action-btn-mini {
  width: 34px;
  height: 34px;
  border-radius: 50%;
  border: none;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: 0.2s;
  background: #f5f5f5;
  color: #555;
}

.action-btn-mini.edit:hover {
  background: #e3f2fd;
  color: #1976d2;
}

.action-btn-mini.delete:hover {
  background: #ffebee;
  color: #c62828;
}

.add-card {
  border: 2px dashed #ddd;
  cursor: pointer;
  background: transparent;
}

.add-card:hover {
  transform: translateY(-5px);
  background: #ffffff;
  border-color: #B51621;
  box-shadow: 0 8px 20px rgba(0, 0, 0, 0.1);
}

.plus-circle {
  font-size: 20px;
  background: #eee;
  color: #666;
}

.add-card:hover .plus-circle {
  background: #B51621;
  color: #ffffff;
}

.add-label {
  font-size: 14px;
  font-weight: bold;
  color: #999;
  margin-top: 10px;
  transition: color 0.3s;
}

.add-card:hover .add-label {
  color: #B51621;
}

.edit-header {
  width: 100%;
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 5px;
}

.edit-header h4 {
  margin: 0;
  font-size: 13px;
  color: #B51621;
}

.compact-label {
  font-size: 9px;
  font-weight: bold;
  color: #999;
  text-transform: uppercase;
  display: block;
  text-align: left;
  width: 100%;
  margin-bottom: 2px;
}

.input-group-compact {
  width: 100%;
  margin-bottom: 8px;
}

.card-input-compact {
  width: 100%;
  padding: 6px 10px;
  border: 1px solid #ddd;
  border-radius: 6px;
  font-size: 12px;
  background: #fafafa;
  box-sizing: border-box;
}

.save-btn-compact {
  width: 100%;
  padding: 8px;
  background: #B51621;
  color: white;
  border: none;
  border-radius: 6px;
  font-weight: bold;
  font-size: 12px;
  cursor: pointer;
}

.sticky-bar {
  position: fixed;
  bottom: 0;
  left: 0;
  width: 100%;
  background: white;
  box-shadow: 0 -5px 15px rgba(0, 0, 0, 0.1);
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

.search-container {
  position: relative;
  width: 320px;
}

.search-icon {
  position: absolute;
  left: 15px;
  top: 50%;
  transform: translateY(-50%);
  color: #999;
}

.search-input {
  width: 100%;
  padding: 10px 15px 10px 40px;
  border-radius: 50px;
  border: 1px solid #ddd;
  outline: none;
  font-size: 14px;
}

.btn-sys.primary {
  background: #B51621;
  color: white;
  padding: 12px 35px;
  border-radius: 50px;
  font-weight: bold;
  border: none;
  cursor: pointer;
  font-size: 15px;
}

.close-icon {
  background: none;
  border: none;
  font-size: 16px;
  cursor: pointer;
  color: #999;
}
</style>