<script setup>
import {computed, onMounted, ref} from 'vue';
import {useRouter} from 'vue-router';
import AppHeader from '../App/Header.vue';
import Sidebar from '../App/Sidebar.vue';
import api from '@/services/api';
import {mcccStore} from "@/services/mcccStore.js";
import CancelModal from '../Information/CancelModal.vue';
import ErrorSkillModal from '../Information/ErrorSkillModal.vue';

const router = useRouter();
const searchQuery = ref('');
const errorMessage = ref('');
const isLoading = ref(true);
const allSkills = ref([]);
const showModal = ref(false);
const showSkillErrorModal = ref(false);
const initialState = ref([]);

const fetchReferential = async () => {
  try {
    isLoading.value = true;
    const response = await api.get('/skill/skills');
    allSkills.value = response.data;
  } catch (error) {
    errorMessage.value = "Erreur lors du chargement des données.";
  } finally {
    isLoading.value = false;
  }
};

const fetchLinkedSkills = async () => {
  if (!mcccStore.resourceID) {
    console.warn("Aucun ResourceID dans le store, abandon.");
    return;
  }

  const targetId = String(mcccStore.resourceID);
  console.log("Recherche des compétences pour resourceID :", targetId);

  try {
    const response = await api.get('/mccc/mcccs');

    const currentMccc = response.data.find(m => {
      return m.resourceId && String(m.resourceId.resourceID) === targetId;
    });

    if (!currentMccc) {
      console.warn("Aucun MCCC trouvé dans la liste pour cet ID.");
      return;
    }

    if (!currentMccc.criticalLearningsId || currentMccc.criticalLearningsId.length === 0) {
      console.warn("MCCC trouvé, mais liste criticalLearningsId vide.");
      return;
    }

    console.log("ACs trouvés en BDD :", currentMccc.criticalLearningsId);

    const acsFromBdd = currentMccc.criticalLearningsId;
    const groupedResult = [];

    acsFromBdd.forEach(acItem => {
      if (!acItem.rankID || !acItem.rankID.skillID) return;

      const skillName = acItem.rankID.skillID.skillName;
      const skillNum = acItem.rankID.skillID.skillNum;
      const rankTitle = acItem.rankID.rankTitle || `Niveau ${acItem.rankID.rankNum}`;

      let existingSkill = groupedResult.find(g => g.ue === skillName);
      if (!existingSkill) {
        existingSkill = {
          resourceCode: mcccStore.resourceCode,
          ue: skillName,
          skillNum: skillNum,
          allLevels: []
        };
        groupedResult.push(existingSkill);
      }

      let existingLevel = existingSkill.allLevels.find(l => l.title === rankTitle);
      if (!existingLevel) {
        existingLevel = {
          title: rankTitle,
          acs: []
        };
        existingSkill.allLevels.push(existingLevel);
      }

      existingLevel.acs.push({
        learningNum: acItem.learningNum,
        learningTitle: acItem.learningTitle || "Sans titre"
      });
    });

    mcccStore.acsGrouped = groupedResult;
    mcccStore.registerMcccStore();
    console.log("Store ACs mis à jour depuis BDD :", mcccStore.acsGrouped);

  } catch (error) {
    console.error("Erreur chargement des compétences liées :", error);
  }
};

onMounted(async () => {
  mcccStore.loadMcccStore();

  if (!Array.isArray(mcccStore.acsGrouped)) {
    mcccStore.acsGrouped = [];
  }

  await fetchReferential();

  if (mcccStore.acsGrouped.length === 0) {
    console.log("Store vide, chargement depuis la BDD...");
    await fetchLinkedSkills();
  } else {
    console.log("Compétences chargées depuis le Store local");
  }

  mcccStore.saveBackup();
  initialState.value = JSON.parse(JSON.stringify(mcccStore.acsGrouped));
});

const filteredSkills = computed(() => {
  const query = searchQuery.value.toLowerCase().trim();

  return allSkills.value.filter(s => {
    const isAlreadySelected = mcccStore.acsGrouped.some(g => g.ue === s.skillName);
    if (isAlreadySelected) return false;

    if (!query) return true;

    if (query.startsWith('#')) {
      const numToSearch = query.substring(1);
      return s.skillNum?.toString().includes(numToSearch);
    }

    return (
        s.skillName.toLowerCase().includes(query) ||
        s.skillNum?.toString().includes(query)
    );
  });
});

const addSkillDirectly = (skill) => {
  if (!skill.levels || skill.levels.length === 0) {
    showSkillErrorModal.value = true;
    return;
  }

  const newSelection = {
    resourceCode: mcccStore.resourceCode,
    ue: skill.skillName,
    skillNum: skill.skillNum,
    allLevels: skill.levels.map(level => ({
      title: level.title || level.name || level.label || level.levelTitle || level.rankTitle || "Niveau sans titre",
      acs: level.acs.map(ac => ({
        learningNum: ac.num || ac.acNum || ac.learningNum,
        learningTitle: ac.title || ac.name || ac.label || ac.acTitle || ac.learningTitle || ac.libelle || "Titre manquant"
      }))
    }))
  };

  mcccStore.acsGrouped.unshift(newSelection);
  mcccStore.registerMcccStore();
};

const removeGroup = (index) => {
  mcccStore.acsGrouped.splice(index, 1);
  mcccStore.registerMcccStore();
};

const handleValider = () => {
  mcccStore.registerMcccStore();
  router.push('/mccc-menu');
};

const handleRetour = () => {
  showModal.value = true;
};

const onConfirmCancel = () => {
  if (initialState.value) {
    mcccStore.acsGrouped = JSON.parse(JSON.stringify(initialState.value));
  }
  mcccStore.registerMcccStore();
  router.push('/mccc-menu');
};

const clearSearch = () => searchQuery.value = '';
</script>

<template>
  <Sidebar/>
  <AppHeader title="Compétences" :inline="`pour la ${mcccStore.resourceCode}`"/>

  <main class="main-content">
    <div class="content-wrapper">

      <div class="selection-section" v-if="mcccStore.acsGrouped.length > 0">
        <h2 class="section-title selected-title">Compétences sélectionnées :</h2>
        <div class="grid-container">
          <div v-for="(group, idx) in mcccStore.acsGrouped" :key="idx" class="admin-card is-selected-summary">
            <button @click="removeGroup(idx)" class="btn-remove-absolute" title="Supprimer">✕</button>

            <div class="icon-circle selected-icon">
              {{ group.skillNum || '?' }}
            </div>

            <h3 class="card-title">{{ group.ue }}</h3>

            <div v-for="(lvl, lIdx) in group.allLevels" :key="lIdx" class="level-entry">
              <span class="rank-info-bold">Niveau {{ lIdx + 1 }} : {{ lvl.title }}</span>
              <div class="ac-details-list">
                <div v-for="ac in lvl.acs" :key="ac.learningNum" class="ac-detail-item">
                  <strong>AC {{ ac.learningNum }} :</strong> {{ ac.learningTitle }}
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div class="separator-line" v-if="mcccStore.acsGrouped.length > 0"></div>

      <div class="selection-section">
        <h2 class="section-title">Compétences disponibles :</h2>
        <div v-if="isLoading" class="loading">Chargement du référentiel...</div>

        <div class="grid-container">
          <div v-for="skill in filteredSkills"
               :key="skill.id"
               class="admin-card"
               @click="addSkillDirectly(skill)">

            <div class="icon-circle">
              {{ skill.skillNum }}
            </div>
            <h3 class="card-title">{{ skill.skillName }}</h3>

            <div v-if="skill.levels && skill.levels.length > 0">
              <div v-for="(level, lIdx) in skill.levels" :key="lIdx" class="level-entry">
                <span class="rank-info-bold">
                  Niveau {{ lIdx + 1 }} : {{ level.title || level.name || level.label || level.levelTitle || "Sans titre" }}
                </span>

                <div class="ac-details-list">
                  <div v-for="ac in level.acs" :key="ac.id || ac.num" class="ac-detail-item">
                    <strong>AC {{ ac.num || ac.acNum || ac.learningNum }} :</strong>
                    {{ ac.title || ac.name || ac.label || ac.learningTitle }}
                  </div>
                </div>
              </div>
            </div>
            <div v-else class="no-content-warning">
              Aucun niveau associé
            </div>

            <div class="btn-add-footer">
              + Ajouter
            </div>
          </div>
        </div>

        <div v-if="!isLoading && filteredSkills.length === 0" class="no-result">
          <span v-if="searchQuery">Aucune compétence ne correspond à "{{ searchQuery }}"</span>
          <span v-else>Toutes les compétences sont sélectionnées.</span>
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
              placeholder="Rechercher par nom ou numéro (ex : #3)"
              class="search-input"
          />
          <button v-if="searchQuery" @click="clearSearch" class="clear-input-btn">✕</button>
        </div>
        <div class="footer-buttons">
          <button @click="handleValider" class="btn-sys primary">Valider</button>
          <button @click="handleRetour" class="btn-sys secondary">Annuler</button>
        </div>
      </div>
    </footer>

    <CancelModal
        v-if="showModal"
        @confirm="onConfirmCancel"
        @close="showModal = false"
    />

    <ErrorSkillModal
        v-if="showSkillErrorModal"
        @close="showSkillErrorModal = false"
    />
  </main>
</template>

<style scoped>
.main-content {
  background-color: #fcfcfc;
  min-height: 100vh;
  padding: 180px 20px 120px;
  font-family: 'Roboto', sans-serif;
  display: flex;
  flex-direction: column;
  align-items: center;
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
  font-size: 1.1rem;
  margin-bottom: 20px;
  font-weight: 600;
  text-transform: uppercase;
}

.selected-title {
  color: #B51621;
}

.separator-line {
  width: 100%;
  height: 1px;
  background: #eee;
  margin-bottom: 40px;
}

.grid-container {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
  gap: 20px;
}

.admin-card {
  background: white;
  border-radius: 15px;
  padding: 25px;
  box-shadow: 0 4px 12px rgba(0,0,0,0.05);
  border: 1px solid #e0e0e0;
  cursor: pointer;
  display: flex;
  flex-direction: column;
  align-items: center;
  transition: all 0.3s ease;
  min-height: 220px;
  position: relative;
  justify-content: flex-start;
}

.admin-card:hover {
  border-color: #B51621;
  transform: translateY(-3px);
  background-color: #fff5f5;
}

.is-selected-summary {
  border-color: #B51621;
  background-color: #fff5f5;
  cursor: default;
}

.icon-circle {
  width: 45px;
  height: 45px;
  background: #f0f0f0;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-weight: bold;
  margin-bottom: 15px;
  color: #666;
  flex-shrink: 0;
  transition: all 0.3s ease;
}

.selected-icon, .admin-card:hover .icon-circle {
  background: #B51621;
  color: white;
}

.card-title {
  font-size: 1rem;
  font-weight: 700;
  text-align: center;
  margin: 0 0 15px 0;
  color: #333;
}

.level-entry {
  width: 100%;
  padding: 10px 0;
}

.level-entry:not(:first-child) {
  margin-top: 25px;
  border-top: 1px dashed #ddd;
  padding-top: 20px;
}

.rank-info-bold {
  font-size: 0.95rem;
  color: #B51621;
  font-weight: 900;
  text-transform: uppercase;
  display: block;
  margin-bottom: 10px;
}

.ac-details-list {
  width: 100%;
  margin-top: 5px;
  padding-left: 5px;
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.ac-detail-item {
  font-size: 0.75rem;
  color: #444;
  text-align: left;
  line-height: 1.4;
}

.ac-detail-item strong {
  color: #B51621;
}

.btn-remove-absolute {
  position: absolute;
  top: 10px;
  right: 10px;
  background: #B51621;
  color: white;
  border: none;
  width: 24px;
  height: 24px;
  border-radius: 50%;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 12px;
  z-index: 10;
}

.btn-add-footer {
  margin-top: 25px;
  padding: 8px 20px;
  background-color: transparent;
  color: #B51621;
  border: 1px solid #B51621;
  border-radius: 20px;
  font-weight: 700;
  font-size: 0.85rem;
  transition: all 0.3s ease;
  width: 100%;
  text-align: center;
  max-width: 120px;
}

.admin-card:hover .btn-add-footer {
  background-color: #B51621;
  color: white;
}

.no-content-warning {
  font-size: 0.8rem;
  color: #999;
  font-style: italic;
  margin-top: 10px;
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

.search-wrapper {
  position: relative;
  flex: 1;
  max-width: 500px;
}

.search-icon {
  position: absolute;
  left: 18px;
  top: 50%;
  transform: translateY(-50%);
  font-size: 1.1rem;
  opacity: 0.5;
  pointer-events: none;
  z-index: 2;
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
  outline: none;
}

.search-input:focus {
  border-color: #B51621;
  background: white;
  box-shadow: 0 0 0 4px rgba(181, 22, 33, 0.1);
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

.btn-sys.primary {
  background: linear-gradient(135deg, #B51621 0%, #d92533 100%);
  color: white;
}

.btn-sys.secondary {
  background-color: white;
  color: #555;
  border: 2px solid #e0e0e0;
}

.btn-sys:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 8px rgba(0,0,0,0.15);
}

.loading, .no-result {
  text-align: center;
  padding: 40px;
  color: #999;
  font-style: italic;
  grid-column: 1 / -1;
}

@media (max-width: 768px) {
  .footer-content {
    flex-direction: column;
    gap: 15px;
  }
  .search-wrapper, .footer-buttons {
    width: 100%;
  }
  .footer-buttons {
    justify-content: space-between;
  }
}

:global(body.dark-mode) .main-content {
  background-color: #252525;
}
:global(body.dark-mode) .section-title {
  color: #ef5350;
}
:global(body.dark-mode) .separator-line {
  background: #444;
}
:global(body.dark-mode) .admin-card {
  background: #333;
  border-color: #444;
}
:global(body.dark-mode) .admin-card:hover {
  background-color: #383838;
  border-color: #ef5350;
}
:global(body.dark-mode) .is-selected-summary {
  background-color: rgba(239, 83, 80, 0.15);
  border-color: #ef5350;
}
:global(body.dark-mode) .icon-circle {
  background: rgba(255,255,255,0.05);
  color: #ef5350;
}
:global(body.dark-mode) .selected-icon,
:global(body.dark-mode) .admin-card:hover .icon-circle {
  background: #B51621;
  color: white;
}
:global(body.dark-mode) .card-title {
  color: #ffffff;
}
:global(body.dark-mode) .level-entry:not(:first-child) {
  border-top-color: #444;
}
:global(body.dark-mode) .rank-info-bold {
  color: #ef5350;
}
:global(body.dark-mode) .ac-detail-item {
  color: #bbb;
}
:global(body.dark-mode) .ac-detail-item strong {
  color: #ef5350;
}
:global(body.dark-mode) .btn-add-footer {
  color: #ef5350;
  border-color: #ef5350;
}
:global(body.dark-mode) .admin-card:hover .btn-add-footer {
  background-color: #B51621;
  color: white;
}
:global(body.dark-mode) .sticky-footer {
  background: #333;
  box-shadow: 0 -4px 20px rgba(0,0,0,0.3);
}
:global(body.dark-mode) .search-input {
  background: #444;
  border-color: #555;
  color: white;
}
:global(body.dark-mode) .search-input:focus {
  border-color: #ef5350;
  background: #333;
}
:global(body.dark-mode) .clear-input-btn {
  background: #555;
  color: #fff;
}
:global(body.dark-mode) .loading,
:global(body.dark-mode) .no-result {
  color: #777;
}
</style>