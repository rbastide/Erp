<script setup>
import {computed, onMounted, ref} from 'vue';
import {useRouter} from 'vue-router';
import AppHeader from '../App/Header.vue';
import Sidebar from '../App/Sidebar.vue';
import api from '@/services/api';

const router = useRouter();
const finalSkills = ref([]);
const editingIndex = ref(null);
const searchQuery = ref('');

const currentSkill = ref({
  skillNum: null,
  skillName: '',
  levels: [{ title: '', acs: [{ num: null, title: '' }] }],
});

const editedSkill = ref({ id: null, skillNum: null, skillName: '', levels: [] });

const fetchSkills = async () => {
  try {
    const response = await api.get('/skill/skills');
    finalSkills.value = response.data;
  } catch (error) {
    console.error(error);
  }
};

onMounted(fetchSkills);

const filteredSkills = computed(() => {
  const query = searchQuery.value.toLowerCase().trim();
  if (!query) return finalSkills.value;

  if (query.startsWith('#')) {
    const numSearch = query.substring(1);
    return finalSkills.value.filter(c =>
        c.skillNum?.toString().includes(numSearch)
    );
  }

  return finalSkills.value.filter(c =>
      c.skillName.toLowerCase().includes(query)
  );
});

const syncWithBackend = async () => {
  const payload = finalSkills.value.map(comp => ({
    id: comp.id || null,
    skillNum: comp.skillNum ? comp.skillNum.toString() : "",
    skillName: comp.skillName || "",
    levels: comp.levels.map((niv, nIdx) => ({
      id: niv.id || null,
      num: niv.num ? parseInt(niv.num) : (nIdx + 1),
      title: niv.title || "",
      acs: niv.acs.map(ac => ({
        id: ac.id || null,
        num: ac.num ? ac.num.toString() : "",
        title: ac.title || ""
      }))
    }))
  }));

  try {
    await api.post('/skill/skills', payload);
    await fetchSkills();
  } catch (error) {
    alert("Erreur synchronisation : " + (error.response?.data?.message || error.message));
  }
};

const handleSaveNewCompetence = async () => {
  if (!currentSkill.value.skillNum || !currentSkill.value.skillName.trim()) return alert('Numéro et intitulé requis.');
  finalSkills.value.unshift(JSON.parse(JSON.stringify(currentSkill.value)));
  currentSkill.value = {
    skillNum: null,
    skillName: '',
    levels: [{ title: '', acs: [{ num: null, title: '' }] }]
  };
  await syncWithBackend();
};

const saveModification = async (index) => {
  finalSkills.value[index] = JSON.parse(JSON.stringify(editedSkill.value));
  editingIndex.value = null;
  await syncWithBackend();
};

const handleDelete = async (index) => {
  const target = finalSkills.value[index];
  if (!confirm(`Supprimer "${target.skillName}" ?`)) return;
  try {
    if (target.id) await api.delete(`/skill/skills/${target.id}`);
    finalSkills.value.splice(index, 1);
    await syncWithBackend();
  } catch (error) {
    console.error(error);
  }
};

const handleAddNiveau = () => {
  if (currentSkill.value.levels.length < 3) {
    currentSkill.value.levels.push({ title: '', acs: [{ num: null, title: '' }] });
  }
};

const removeNiveau = (i) => {
  if (currentSkill.value.levels.length > 1) {
    currentSkill.value.levels.splice(i, 1);
  }
};

const handleAddAc = (nivIdx) => {
  if (currentSkill.value.levels[nivIdx].acs.length < 4) {
    currentSkill.value.levels[nivIdx].acs.push({ num: null, title: '' });
  }
};

const removeAc = (nivIdx, acIdx) => {
  if (currentSkill.value.levels[nivIdx].acs.length > 1) {
    currentSkill.value.levels[nivIdx].acs.splice(acIdx, 1);
  }
};

const handleModif = (comp, index) => {
  editingIndex.value = index;
  editedSkill.value = JSON.parse(JSON.stringify(comp));
};

const handleCancelEdit = () => {
  editingIndex.value = null;
};

const addLevelToEdit = () => {
  if (editedSkill.value.levels.length < 3) {
    editedSkill.value.levels.push({ title: '', acs: [{ num: null, title: '' }] });
  }
};

const removeLevelToEdit = (i) => {
  if (editedSkill.value.levels.length > 1) {
    editedSkill.value.levels.splice(i, 1);
  }
};

const addAcToEdit = (lvlI) => {
  if (editedSkill.value.levels[lvlI].acs.length < 4) {
    editedSkill.value.levels[lvlI].acs.push({ num: null, title: '' });
  }
};

const removeAcFromEdit = (lvlI, acI) => {
  editedSkill.value.levels[lvlI].acs.splice(acI, 1);
};

const handleValider = () => router.back();
</script>

<template>
  <Sidebar />
  <div class="page-container">
    <AppHeader title="Gestion des compétences" />
    <main class="main-content">

      <div class="form-card" v-if="editingIndex === null">
        <div class="form-header">
          <h4>Ajouter une nouvelle compétence</h4>
        </div>
        <div class="row-inputs">
          <div class="input-group small">
            <label class="field-label">N°</label>
            <input type="number" v-model.number="currentSkill.skillNum" min="1" placeholder="1" class="card-input">
          </div>
          <div class="input-group large">
            <label class="field-label">Intitulé de la Compétence</label>
            <input type="text" v-model="currentSkill.skillName" placeholder="Réaliser" class="card-input">
          </div>
        </div>
        <div v-for="(niveau, nIndex) in currentSkill.levels" :key="nIndex" class="niveau-container">
          <div class="niveau-header">
            <div class="header-with-remove">
              <label class="group-label">Niveau {{ nIndex + 1 }}</label>
              <button v-if="currentSkill.levels.length > 1" @click="removeNiveau(nIndex)" class="btn-remove-item">✕</button>
            </div>
            <input type="text" v-model="niveau.title" placeholder="Intitulé du niveau..." class="card-input">
          </div>
          <div class="acs-container">
            <div v-for="(ac, aIndex) in niveau.acs" :key="aIndex" class="ac-row">
              <input type="number" v-model.number="ac.num" min="1" placeholder="N°" class="card-input ac-num">
              <input type="text" v-model="ac.title" placeholder="Intitulé AC" class="card-input ac-name">
              <button v-if="niveau.acs.length > 1" @click="removeAc(nIndex, aIndex)" class="btn-remove-item">✕</button>
            </div>
            <div class="add-ac-center-wrapper" v-if="niveau.acs.length < 4">
              <button @click="handleAddAc(nIndex)" class="btn-framed-add mini">+ Ajouter un AC</button>
            </div>
          </div>
        </div>
        <div class="add-level-center-wrapper" v-if="currentSkill.levels.length < 3">
          <button @click="handleAddNiveau" class="btn-framed-add">+ Ajouter un Niveau</button>
        </div>
        <button class="save-btn big-save" @click="handleSaveNewCompetence">Valider et synchroniser</button>
      </div>

      <div class="separator-line"></div>

      <div v-if="finalSkills.length > 0" class="grid-container">
        <div v-for="(comp, index) in filteredSkills" :key="comp.id || index" class="skill-card" :class="{ 'is-editing': editingIndex === index }">

          <div v-if="editingIndex !== index" class="view-mode-container">
            <div class="card-header-view">
              <div class="icon-circle"><span>{{ comp.skillNum }}</span></div>
              <h3>{{ comp.skillName }}</h3>
            </div>
            <div class="card-body-scroll">
              <div v-for="(niv, nIdx) in comp.levels" :key="nIdx" class="level-block">
                <p class="level-title">Niveau {{ niv.num }}: {{ niv.title }}</p>
                <ul class="ac-list">
                  <li v-for="(ac, aIdx) in niv.acs" :key="aIdx">AC {{ ac.num }}: {{ ac.title }}</li>
                </ul>
              </div>
            </div>
            <div class="card-actions-overlay">
              <button class="action-btn edit" @click="handleModif(comp, index)">
                <svg width="18" height="18" viewBox="0 0 24 24"  stroke="currentColor" stroke-width="2" fill="none">
                  <path d="M11 4H4a2 2 0 0 0-2 2v14a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2v-7"></path>
                  <path d="M18.5 2.5a2.121 2.121 0 0 1 3 3L12 15l-4 1 1-4 9.5-9.5z"></path>
                </svg>
              </button>
              <button class="action-btn delete" @click="handleDelete(index)">
                <svg width="18" height="18" viewBox="0 0 24 24"  stroke="currentColor" stroke-width="2" fill="none">
                  <polyline points="3 6 5 6 21 6"></polyline>
                  <path d="M19 6v14a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2V6m3 0V4a2 2 0 0 1 2-2h4a2 2 0 0 1 2 2v2"></path>
                </svg>
              </button>
            </div>
          </div>

          <div v-else class="edit-mode-container">
            <div class="edit-header">
              <h4>Édition</h4>
              <button class="close-icon" @click="handleCancelEdit">✕</button>
            </div>
            <div class="edit-scroll-area">
              <label class="field-label">N° & Nom</label>
              <div class="ac-edit-row">
                <input type="number" v-model.number="editedSkill.skillNum" min="1" class="card-input tiny">
                <input type="text" v-model="editedSkill.skillName" class="card-input compact flex-1">
              </div>
              <div v-for="(lvl, lIdx) in editedSkill.levels" :key="lIdx" class="edit-level-group">
                <div class="header-with-remove">
                  <span class="field-label">Niveau {{ lIdx + 1 }}</span>
                  <button v-if="editedSkill.levels.length > 1" @click="removeLevelToEdit(lIdx)" class="remove-ac">✕</button>
                </div>
                <input type="text" v-model="lvl.title" class="card-input compact" placeholder="Intitulé">
                <div v-for="(ac, acIdx) in lvl.acs" :key="acIdx" class="ac-edit-row">
                  <input type="number" v-model.number="ac.num" min="1" class="card-input tiny">
                  <input type="text" v-model="ac.title" class="card-input compact flex-1">
                  <span @click="removeAcFromEdit(lIdx, acIdx)" class="remove-ac">✕</span>
                </div>
                <div class="add-ac-center-wrapper" v-if="lvl.acs.length < 4">
                  <div @click="addAcToEdit(lIdx)" class="add-mini-btn-framed">+ AC</div>
                </div>
              </div>
              <div class="add-ac-center-wrapper" v-if="editedSkill.levels.length < 3" style="margin-top: 10px;">
                <div @click="addLevelToEdit" class="add-mini-btn-framed" style="width: 100%; text-align: center;">+ Ajouter un Niveau</div>
              </div>
            </div>
            <button class="save-btn" @click="saveModification(index)">Mettre à jour</button>
          </div>
        </div>
      </div>
    </main>

    <footer class="sticky-bar">
      <div class="sticky-wrapper">
        <div class="search-container">
          <svg class="search-icon" width="20" height="20" viewBox="0 0 24 24"  stroke="currentColor" stroke-width="2" fill="none">
            <circle cx="11" cy="11" r="8"></circle>
            <line x1="21" y1="21" x2="16.65" y2="16.65"></line>
          </svg>
          <input type="text" v-model="searchQuery" placeholder="Chercher par nom ou par numéro (ex : #3)"
                 class="search-input"/>
        </div>
        <button @click="handleValider" class="btn-sys primary">Terminer</button>
      </div>
    </footer>
  </div>
</template>

<style scoped>
.page-container {
  min-height: 100vh;
  background-color: #f8f9fa;
  font-family: 'Roboto', sans-serif;
  padding-bottom: 120px;
}

.main-content {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 40px 20px;
  margin-top: 150px;
  max-width: 1000px;
  margin-left: auto;
  margin-right: auto;
}

.grid-container {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(320px, 1fr));
  gap: 20px;
  width: 100%;
  margin-top: 20px;
  margin-bottom: 40px;
}

.skill-card {
  background: white;
  border-radius: 15px;
  padding: 20px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
  height: 380px;
  display: flex;
  flex-direction: column;
  position: relative;
  transition: all 0.3s ease;
  border: 1px solid #eee;
  overflow: hidden;
}

.skill-card.is-editing {
  border: 2px solid #B51621;
  height: auto;
  min-height: 400px;
}

.view-mode-container {
  display: flex;
  flex-direction: column;
  height: 100%;
}

.card-header-view {
  display: flex;
  align-items: center;
  gap: 12px;
  margin-bottom: 15px;
  border-bottom: 1px solid #f0f0f0;
  padding-bottom: 10px;
}

.icon-circle {
  width: 35px;
  height: 35px;
  background: rgba(181, 22, 33, 0.1);
  border-radius: 50%;
  display: flex;
  justify-content: center;
  align-items: center;
  color: #B51621;
  font-weight: bold;
}

.card-body-scroll {
  flex: 1;
  overflow-y: auto;
  padding-right: 5px;
  margin-bottom: 15px;
}

.card-body-scroll::-webkit-scrollbar {
  width: 4px;
}

.card-body-scroll::-webkit-scrollbar-thumb {
  background: #ddd;
  border-radius: 4px;
}

.level-title {
  font-weight: bold;
  font-size: 13px;
  color: #444;
  margin: 8px 0 4px;
}

.ac-list {
  padding-left: 15px;
  margin: 0;
  font-size: 12px;
  color: #666;
}

.card-actions-overlay {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
  padding-top: 15px;
  border-top: 1px solid #f0f0f0;
}

.action-btn {
  width: 36px;
  height: 36px;
  border-radius: 50%;
  border: none;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: 0.2s;
}

.action-btn.edit {
  background: #e3f2fd;
  color: #1976d2;
}
.action-btn.delete {
  background: #ffebee;
  color: #c62828;
}

.action-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 8px rgba(0,0,0,0.1);
}


.form-card {
  background: white;
  border-radius: 15px;
  padding: 25px;
  width: 100%;
  box-shadow: 0 8px 20px rgba(0, 0, 0, 0.05);
  border: 1px solid #ddd;
  margin-bottom: 30px;
}

.row-inputs {
  display: flex;
  gap: 15px; margin-bottom: 15px;
  margin-top: 15px;
}
.input-group.small {
  width: 80px;
}
.input-group.large {
  flex: 1;
}

.field-label {
  font-size: 11px;
  font-weight: bold;
  color: #888;
  text-transform: uppercase;
  margin-bottom: 5px;
  display: block;
}

.card-input {
  width: 100%;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 8px;
  font-size: 14px;
  box-sizing: border-box;
}


.card-input:focus {
  border-color: #B51621;
  outline: none;
}

.niveau-container {
  background: #fdfdfd;
  border: 1px solid #eee;
  border-radius: 10px;
  padding: 15px;
  margin-bottom: 15px;
  position: relative;
}

.header-with-remove { display: flex; justify-content: space-between; align-items: center; margin-bottom: 5px; }

.acs-container {
  border-left: 2px solid #B51621;
  padding-left: 15px;
  margin-top: 10px;
}

.ac-row {
  display: flex;
  gap: 10px;
  align-items: center;
  margin-top: 8px;
}
.ac-num {
  width: 65px;
  flex-shrink: 0;
}

.btn-remove-item {
  background: none;
  border: none;
  color: #ff4d4f;
  cursor: pointer;
  font-weight: bold;
  font-size: 14px;
}

.add-level-center-wrapper, .add-ac-center-wrapper {
  display: flex;
  justify-content: center;
}

.btn-framed-add {
  background: rgba(181, 22, 33, 0.02);
  border: 2px dashed #B51621;
  color: #B51621;
  padding: 10px 30px;
  border-radius: 12px;
  font-weight: bold;
  font-size: 14px;
  cursor: pointer;
  display: flex;
  align-items: center;
  margin: 10px 0;
}

.btn-framed-add.mini {
  padding: 6px 15px;
  font-size: 11px;
}

.add-mini-btn-framed {
  font-size: 10px;
  color: #B51621;
  cursor: pointer;
  font-weight: bold;
  border: 1px dashed #B51621;
  padding: 5px 8px;
  border-radius: 4px;
  background: white;
  margin-top: 5px;
}

.separator-line {
  width: 100%;
  height: 1px;
  background: #ddd;
  margin: 30px 0;
}

.save-btn {
  background: #B51621;
  color: white;
  border: none;
  border-radius: 8px;
  padding: 10px;
  font-weight: bold;
  cursor: pointer;
  width: 100%;
  margin-top: 10px;
}

/* Sticky Bar */
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
  max-width: 1000px;
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
  padding: 10px 35px;
  border-radius: 50px;
  font-weight: bold;
  border: none;
  cursor: pointer;
  min-width: 160px;
}

.edit-mode-container {
  display: flex;
  flex-direction: column;
  height: 100%;
}
.edit-scroll-area {
  flex: 1;
  overflow-y: auto;
  margin-bottom: 10px;
  padding-right: 5px;
}
.edit-level-group {
  border: 1px solid #eee;
  padding: 8px;
  border-radius: 5px;
  margin-top: 8px;
  background: #fafafa;
}
.ac-edit-row {
  display: flex;
  gap: 5px;
  align-items: center;
  margin-top: 5px;
}
.remove-ac {
  color: #ccc;
  cursor: pointer;
  font-size: 12px;
  padding: 0 5px;
}
.flex-1 {
  flex: 1;
}
.tiny {
  width: 55px;
  flex-shrink: 0;
}
.compact {
  padding: 6px;
  font-size: 13px;
}
.close-icon {
  background: none;
  border: none;
  font-size: 18px;
  cursor: pointer;
  color: #999;
}
</style>