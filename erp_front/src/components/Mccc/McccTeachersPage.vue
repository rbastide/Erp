<script setup>
import {computed, onMounted, ref} from 'vue';
import {useRouter} from 'vue-router';
import AppHeader from '../App/Header.vue';
import Sidebar from '../App/Sidebar.vue';
import api from '@/services/api';
import {mcccStore} from "@/services/mcccStore.js";
import CancelModal from '../Information/CancelModal.vue';

const router = useRouter();
const searchQuery = ref('');
const allTeachers = ref([]);

const selectedTeacherIds = ref([]);
const referentTeacherIds = ref([]);

const errorMessage = ref('');
const showModal = ref(false);

const initialState = ref({ selected: [], referents: [] });

const fetchTeachers = async () => {
  try {
    const response = await api.get('/teachers');
    const teachersData = Array.isArray(response.data) ? response.data : [];

    allTeachers.value = teachersData
        .filter(t => t.teacherID != null)
        .map(t => ({
          ...t,
          teacherID: Number(t.teacherID)
        }));
  } catch (error) {
    console.error("Erreur API Teachers :", error);
    errorMessage.value = "Impossible de charger les enseignants.";
  }
};

const updateStoreReferents = () => {
  mcccStore.referents = selectedTeacherIds.value.map(teacherId => {
    const user = allTeachers.value.find(u => u.teacherID === teacherId);
    return user ? {
      teacherID: user.teacherID,
      lastname: user.lastname,
      firstname: user.firstname,
      isReferent: referentTeacherIds.value.includes(teacherId)
    } : null;
  }).filter(Boolean);

  mcccStore.teacherIds = selectedTeacherIds.value.map(teacherId => {
    const user = allTeachers.value.find(u => u.teacherID === teacherId);
    return user ? {
      teacherID: user.teacherID,
      isManager: referentTeacherIds.value.includes(teacherId),
    } : null;
  }).filter(Boolean);
};

onMounted(async () => {
  mcccStore.loadMcccStore();
  await fetchTeachers();

  if (mcccStore.referents && mcccStore.referents.length > 0) {
    selectedTeacherIds.value = mcccStore.referents.map(t => {
      const id = typeof t === 'object' ? (t.teacherID || t.id) : t;
      return Number(id);
    });

    referentTeacherIds.value = mcccStore.referents
        .filter(t => typeof t === 'object' && (t.isManager || t.isReferent))
        .map(t => {
          const id = typeof t === 'object' ? (t.teacherID || t.id) : t;
          return Number(id);
        });
  }
  else if (mcccStore.teacherIds && mcccStore.teacherIds.length > 0) {
    selectedTeacherIds.value = mcccStore.teacherIds.map(t => {
      const id = typeof t === 'object' ? (t['teacherID'] || t['id']) : t;
      return Number(id);
    });

    referentTeacherIds.value = mcccStore.teacherIds
        .filter(t => typeof t === 'object' && (t['isManager'] || t['isReferent']))
        .map(t => {
          const id = typeof t === 'object' ? (t['teacherID'] || t['id']) : t;
          return Number(id);
        });
  }
  else {
    selectedTeacherIds.value = [];
    referentTeacherIds.value = [];
  }
  updateStoreReferents();

  mcccStore.saveBackup();
  initialState.value = {
    selected: [...selectedTeacherIds.value],
    referents: [...referentTeacherIds.value]
  };
});

const selectedTeachersList = computed(() => {
  return allTeachers.value.filter(t => selectedTeacherIds.value.includes(t.teacherID));
});

const availableTeachersList = computed(() => {
  const query = searchQuery.value.toLowerCase().trim();
  return allTeachers.value.filter(teacher => {
    const isNotSelected = !selectedTeacherIds.value.includes(teacher.teacherID);

    const matchesSearch = !query ||
        (teacher.lastname && teacher.lastname.toLowerCase().includes(query)) ||
        (teacher.firstname && teacher.firstname.toLowerCase().includes(query));

    return isNotSelected && matchesSearch;
  });
});

const toggleTeacher = (id) => {
  const idNumber = Number(id);
  if (selectedTeacherIds.value.includes(idNumber)) {
    selectedTeacherIds.value = selectedTeacherIds.value.filter(itemId => itemId !== idNumber);
    referentTeacherIds.value = referentTeacherIds.value.filter(rId => rId !== idNumber);
  } else {
    selectedTeacherIds.value.push(idNumber);
  }
  updateStoreReferents();
  mcccStore.registerMcccStore();
};

const toggleReferent = (id) => {
  const idNumber = Number(id);
  if (referentTeacherIds.value.includes(idNumber)) {
    referentTeacherIds.value = referentTeacherIds.value.filter(rId => rId !== idNumber);
  } else {
    referentTeacherIds.value.push(idNumber);
  }
  updateStoreReferents();
  mcccStore.registerMcccStore();
};

const handleValidate = () => {
  if (selectedTeacherIds.value.length === 0) {
    errorMessage.value = "Veuillez sélectionner au moins un enseignant.";
    return;
  }
  updateStoreReferents();
  mcccStore.registerMcccStore();
  router.push('/mccc-menu');
};

const handleBack = () => {
  showModal.value = true;
};

const onConfirmCancel = () => {
  selectedTeacherIds.value = [...initialState.value.selected];
  referentTeacherIds.value = [...initialState.value.referents];
  updateStoreReferents();
  mcccStore.registerMcccStore();
  router.push('/mccc-menu');
};

const clearSearch = () => searchQuery.value = '';
</script>

<template>
  <Sidebar />
  <AppHeader title="Enseignants de la" :inline="mcccStore.resourceCode" />

  <main class="main-content">
    <div class="content-wrapper">
      <p v-if="errorMessage" class="error-message">{{ errorMessage }}</p>

      <div class="selection-section" v-if="selectedTeachersList.length > 0">
        <h2 class="section-title selected-title">Enseignant(s) sélectionné(s) ({{ selectedTeachersList.length }}) :</h2>
        <div class="grid-container">
          <div
              v-for="teacher in selectedTeachersList"
              :key="teacher.teacherID"
              class="teacher-card is-selected"
              @click="toggleTeacher(teacher.teacherID)"
          >
            <div class="card-content">
              <div class="avatar-circle selected-avatar">
                {{ teacher.firstname ? teacher.firstname[0] : '' }}{{ teacher.lastname ? teacher.lastname[0] : '' }}
              </div>
              <h3 class="teacher-name">{{ teacher.firstname }} {{ teacher.lastname }}</h3>
              <div class="manager" @click.stop>
                <p>Référent</p>
                <div
                    class="toggle-switch"
                    :class="{ 'active': referentTeacherIds.includes(teacher.teacherID) }"
                    @click.stop="toggleReferent(teacher.teacherID)"
                >
                  <div class="toggle-knob"></div>
                </div>
              </div>
              <div class="check-indicator">
                <svg width="16" height="16" viewBox="0 0 24 24" stroke="currentColor" stroke-width="3" stroke-linecap="round" stroke-linejoin="round" style="fill: none;">
                  <polyline points="20 6 9 17 4 12"></polyline>
                </svg>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div class="selection-section">
        <h2 class="section-title">Enseignants disponibles :</h2>
        <div class="grid-container">
          <div
              v-for="teacher in availableTeachersList"
              :key="teacher.teacherID"
              class="teacher-card"
              @click="toggleTeacher(teacher.teacherID)"
          >
            <div class="card-content">
              <div class="avatar-circle">
                {{ teacher.firstname ? teacher.firstname[0] : '' }}{{ teacher.lastname ? teacher.lastname[0] : '' }}
              </div>
              <h3 class="teacher-name">{{ teacher.firstname }} {{ teacher.lastname }}</h3>
            </div>
          </div>

          <div v-if="availableTeachersList.length === 0 && searchQuery" class="no-result">
            Aucun autre enseignant trouvé pour "{{ searchQuery }}"
          </div>
          <div v-if="availableTeachersList.length === 0 && !searchQuery && selectedTeachersList.length === allTeachers.length" class="no-result">
            Tous les enseignants sont sélectionnés ou aucun enseignant trouvé.
          </div>
        </div>
      </div>
    </div>

    <footer class="sticky-footer">
      <div class="footer-content">
        <div class="search-wrapper">
          <span class="search-icon">🔍</span>
          <input
              v-model="searchQuery"
              type="text"
              placeholder="Rechercher par nom ou prénom"
              class="search-input"
          />
          <button v-if="searchQuery" @click="clearSearch" class="clear-input-btn">✕</button>
        </div>

        <div class="footer-buttons">
          <button @click="handleValidate" class="btn-sys primary">Valider</button>
          <button @click="handleBack" class="btn-sys secondary">Annuler</button>
        </div>
      </div>
    </footer>

    <CancelModal v-if="showModal" @confirm="onConfirmCancel" @close="showModal = false" />
  </main>
</template>

<style scoped>
.main-content {
  background-color: #fcfcfc;
  min-height: 100vh;
  padding: 200px 20px 100px;
  display: flex;
  flex-direction: column;
  align-items: center;
  font-family: 'Roboto', sans-serif;
}

.content-wrapper {
  width: 100%;
  max-width: 1200px;
}

.selection-section {
  margin-bottom: 40px;
}

.section-title {
  color: #666;
  font-size: 1.3rem;
  margin-bottom: 20px;
  font-weight: 500;
}

.selected-title {
  color: #B51621;
  font-weight: 700;
}

.grid-container {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(220px, 1fr));
  gap: 20px;
  width: 100%;
}

.toggle-switch {
  width: 38px;
  height: 20px;
  background-color: #ddd;
  border-radius: 20px;
  position: relative;
  cursor: pointer;
  transition: background-color 0.3s;
  flex-shrink: 0;
}

.manager{
  margin-top: 20px;
  display: flex;
  gap: 10px;
  align-items: center;
}

.manager p{
  font-family: 'Roboto', sans-serif;
  font-size: 13px;
  margin: 0;
}

.toggle-switch.active { background-color: #B51621; }

.toggle-knob {
  width: 16px;
  height: 16px;
  background-color: white;
  border-radius: 50%;
  position: absolute;
  top: 2px;
  left: 2px;
  transition: transform 0.3s;
  box-shadow: 0 1px 3px rgba(0,0,0,0.2);
}

.toggle-switch.active .toggle-knob { transform: translateX(18px); }

.teacher-card {
  background: white;
  border-radius: 12px;
  padding: 20px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.05);
  border: 1px solid #e0e0e0;
  cursor: pointer;
  position: relative;
  transition: all 0.3s cubic-bezier(0.25, 0.8, 0.25, 1);
  display: flex;
  flex-direction: column;
  align-items: center;
  text-align: center;
  height: 140px;
  justify-content: center;
}

.teacher-card:hover {
  transform: translateY(-3px);
  box-shadow: 0 8px 20px rgba(0, 0, 0, 0.1);
  border-color: #ccc;
}

.teacher-card.is-selected {
  border-color: #B51621;
  background-color: #fff5f5;
  box-shadow: 0 4px 12px rgba(181, 22, 33, 0.15);
}

.card-content {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
}

.avatar-circle {
  width: 50px;
  height: 50px;
  background: #f0f0f0;
  color: #666;
  border-radius: 50%;
  display: flex;
  justify-content: center;
  align-items: center;
  font-weight: bold;
  font-size: 1.2rem;
  margin-bottom: 15px;
  text-transform: uppercase;
  transition: all 0.3s ease;
}

.selected-avatar {
  background: rgba(181, 22, 33, 0.1);
  color: #B51621;
}

.teacher-name {
  font-size: 16px;
  font-weight: 600;
  color: #333;
  margin: 0;
  word-break: break-word;
}

.check-indicator {
  position: absolute;
  top: 10px;
  right: 10px;
  color: #B51621;
  background: white;
  border-radius: 50%;
  width: 20px;
  height: 20px;
  display: flex;
  align-items: center;
  justify-content: center;
  box-shadow: 0 2px 4px rgba(181, 22, 33, 0.2);
  animation: popIn 0.2s ease-out;
}

@keyframes popIn {
  0% { transform: scale(0); }
  100% { transform: scale(1); }
}

.no-result {
  grid-column: 1 / -1;
  text-align: center;
  color: #888;
  font-style: italic;
  padding: 20px;
}

.error-message {
  color: #E92533;
  font-weight: bold;
  text-align: center;
  margin-bottom: 20px;
  background: #fff5f5;
  padding: 10px;
  border-radius: 8px;
}

.sticky-footer {
  position: fixed;
  bottom: 0;
  left: 0;
  width: 100%;
  background: white;
  box-shadow: 0 -4px 20px rgba(0, 0, 0, 0.15);
  padding: 15px 0;
  z-index: 100;
}

.footer-content {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 20px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 20px;
}

@media (max-width: 768px) {
  .footer-content {
    flex-direction: column;
    gap: 15px;
  }
  .search-wrapper,
  .footer-buttons {
    width: 100%;
  }
  .footer-buttons {
    display: flex;
    justify-content: space-between;
  }
}

.search-wrapper {
  position: relative;
  flex: 1;
  max-width: 500px;
}

.search-input {
  width: 100%;
  padding: 12px 45px;
  border-radius: 50px;
  border: 2px solid #e2e8f0;
  font-size: 1rem;
  background: #f8fafc;
  transition: all 0.2s;
  box-sizing: border-box;
}

.search-input:focus {
  outline: none;
  border-color: #B51621;
  background: white;
  box-shadow: 0 0 0 4px rgba(181, 22, 33, 0.1);
}

.search-icon {
  position: absolute;
  left: 15px;
  top: 50%;
  transform: translateY(-50%);
  opacity: 0.5;
}

.clear-input-btn {
  position: absolute;
  right: 15px;
  top: 50%;
  transform: translateY(-50%);
  background: #e2e8f0;
  border: none;
  border-radius: 50%;
  width: 22px;
  height: 22px;
  font-size: 10px;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
}

.footer-buttons {
  display: flex;
  gap: 15px;
}

.btn-sys {
  padding: 12px 30px;
  border-radius: 50px;
  border: none;
  font-size: 16px;
  font-weight: 700;
  cursor: pointer;
  transition: all 0.2s;
  white-space: nowrap;
}

.btn-sys:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.15);
}

.btn-sys.primary {
  background: linear-gradient(135deg, #B51621 0%, #d92533 100%);
  color: white;
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
</style>