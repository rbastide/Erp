<script setup>
import { ref, onMounted, computed } from 'vue';
import { useRouter } from 'vue-router';
import AppHeader from '../App/Header.vue';
import Sidebar from '../App/Sidebar.vue';
import api from '@/services/api';
import { mcccStore } from "@/services/mcccStore.js";

const router = useRouter();
const searchQuery = ref('');
const errorMessage = ref('');
const isLoading = ref(true);

const allSkills = ref([]);

const fetchReferentiel = async () => {
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

onMounted(() => {
  mcccStore.loadMcccStore();
  if (!Array.isArray(mcccStore.acsGrouped)) mcccStore.acsGrouped = [];
  fetchReferentiel();
  mcccStore.loadMcccStore();
  mcccStore.saveBackup();
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
  if (!skill.niveaux || skill.niveaux.length === 0) {
    alert("Cette compétence n'a pas de niveaux définis en base.");
    return;
  }

  const defaultRank = skill.niveaux[0];

  const newSelection = {
    resourceCode: mcccStore.resourceCode,
    ue: skill.skillName,
    niveau: defaultRank.intitule,
    skillNum: skill.skillNum,
    acs: defaultRank.acs.map(ac => ({
      learningNum: parseInt(ac.num),
      learningTitle: ac.intitule
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

const handleCancel = () => router.push('/cancel-mccc');
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
            <span class="rank-info">{{ group.niveau }}</span>

            <div class="ac-details-list">
              <div v-for="ac in group.acs" :key="ac.learningNum" class="ac-detail-item">
                <strong>AC {{ ac.learningNum }} :</strong> {{ ac.learningTitle }}
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
            <span class="click-info">+ Ajouter</span>
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
          <button @click="handleCancel" class="btn-sys secondary">Annuler</button>
        </div>
      </div>
    </footer>
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
  min-height: 160px;
  position: relative;
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
  min-height: 220px;
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
  margin: 0 0 5px 0;
  color: #333;
}

.rank-info {
  font-size: 0.8rem;
  color: #666;
  font-weight: 500;
  margin-bottom: 10px;
}

.ac-details-list {
  width: 100%;
  margin-top: 15px;
  padding-top: 10px;
  border-top: 1px dashed rgba(181, 22, 33, 0.2);
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.ac-detail-item {
  font-size: 0.75rem;
  color: #444;
  text-align: left;
  line-height: 1.3;
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

.sticky-footer {
  position: fixed;
  bottom: 0;
  left: 0;
  width: 100%;
  background: white;
  box-shadow: 0 -4px 20px rgba(0, 0, 0, 0.15); /* Ombre vers le haut */
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
</style>