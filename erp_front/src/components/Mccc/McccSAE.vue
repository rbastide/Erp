<script setup>
import { useRouter } from 'vue-router';
import { onMounted, ref } from 'vue';
import { mcccStore } from "@/services/mcccStore.js";
import AppHeader from '../App/Header.vue';
import Sidebar from '../App/Sidebar.vue';
import api from '@/services/api';

const router = useRouter();
const errorMessage = ref('');
const selectedSaeIds = ref([]);
const availableSae = ref([]);

const fetchSAEs = async () => {
  try {
    const response = await api.get('/mccc/getSaes');

    let saeData = [];
    if (Array.isArray(response.data)) {
      saeData = response.data;
    } else if (response.data && Array.isArray(response.data.content)) {
      saeData = response.data.content;
    }

    availableSae.value = saeData.map(s => ({
      id: s.num,
      title: s.title,
      semester: s.semester
    }));

  } catch (error) {
    errorMessage.value = "Impossible de charger les SAÉs.";
  }
};

onMounted(async () => {
  mcccStore.loadMcccStore();
  await fetchSAEs();
  if (mcccStore.saeCodes && mcccStore.saeCodes.length > 0) {
    if (typeof mcccStore.saeCodes[0] === 'object') {
      selectedSaeIds.value = mcccStore.saeCodes.map(item => item.saeCode);
    } else {
      selectedSaeIds.value = [...mcccStore.saeCodes];
    }
  }
  mcccStore.loadMcccStore();
  mcccStore.saveBackup();
});

const getSaeBySemester = (semesterNumber) => {
  return availableSae.value.filter(sae => {
    if (sae.semester) {
      return sae.semester === semesterNumber;
    }
    const match = sae.id.match(/S?(\d)\./);
    return match && parseInt(match[1]) === semesterNumber;
  });
};

const toggleSae = (saeId) => {
  errorMessage.value = '';
  if (selectedSaeIds.value.includes(saeId)) {
    selectedSaeIds.value = selectedSaeIds.value.filter(id => id !== saeId);
  } else {
    selectedSaeIds.value.push(saeId);
  }
};

const handleValider = () => {
  if (selectedSaeIds.value.length === 0) {
    errorMessage.value = "Veuillez sélectionner au moins une SAÉ.";
    return;
  }
  mcccStore.saeCodes = selectedSaeIds.value.map(id => {
    const fullSae = availableSae.value.find(s => s.id === id);
    return {
      saeCode: id,
      saeName: fullSae ? fullSae.title : ''
    };
  });
  mcccStore.registerMcccStore();
  router.push('/mccc-menu');
};

const handleRetour = () => {
  router.push('/cancel-mccc');
};
</script>

<template>
  <Sidebar/>
  <AppHeader title="SAÉs pour" :inline="`la ${mcccStore.resourceCode}`"/>
  <main class="main-content">
    <div class="content-wrapper">
      <div class="semester-board">
        <div v-for="i in 6" :key="i" class="semester-column">
          <h3 class="column-title">Semestre {{ i }}</h3>
          <div class="cards-list">
            <div
                v-for="sae in getSaeBySemester(i)"
                :key="sae.id"
                class="sae-card"
                :class="{ 'is-selected': selectedSaeIds.includes(sae.id) }"
                @click="toggleSae(sae.id)"
            >
              <div class="sae-content">
                <span class="sae-number">{{ sae.id }}</span>
                <span class="sae-title">{{ sae.title }}</span>
                <div class="check-indicator" v-if="selectedSaeIds.includes(sae.id)">
                  <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="3" stroke-linecap="round" stroke-linejoin="round"><polyline points="20 6 9 17 4 12"></polyline></svg>
                </div>
              </div>
            </div>
            <div v-if="getSaeBySemester(i).length === 0" class="empty-placeholder">
              Aucune SAE
            </div>
          </div>
        </div>
      </div>
      <p v-if="errorMessage" class="error-message">{{ errorMessage }}</p>
      <div class="footer-actions">
        <button @click="handleValider" class="btn-main">Valider</button>
        <button @click="handleRetour" class="btn-sub">Annuler</button>
      </div>
    </div>
  </main>
</template>

<style scoped>
.main-content {
  padding-top: 200px;
  min-height: 100vh;
  background-color: #fcfcfc;
  font-family: 'Roboto', sans-serif;
}
.content-wrapper {
  width: 95%;
  max-width: 1600px;
  margin: 0 auto;
  padding-bottom: 50px;
}
.semester-board {
  display: grid;
  grid-template-columns: repeat(6, 1fr);
  gap: 15px;
  margin-bottom: 40px;
  overflow-x: auto;
  padding-bottom: 20px;
}
.semester-column {
  background-color: #f8f9fa;
  border-radius: 12px;
  padding: 15px 10px;
  min-width: 160px;
  border: 1px solid #eee;
  display: flex;
  flex-direction: column;
}
.column-title {
  text-align: center;
  color: #B51621;
  font-size: 18px;
  font-weight: 700;
  margin-bottom: 20px;
  text-transform: uppercase;
  border-bottom: 2px solid rgba(181, 22, 33, 0.1);
  padding-bottom: 10px;
}
.cards-list {
  display: flex;
  flex-direction: column;
  gap: 15px;
  flex-grow: 1;
}
.sae-card {
  background: white;
  border-radius: 8px;
  padding: 12px;
  box-shadow: 0 2px 5px rgba(0,0,0,0.05);
  border: 1px solid #e0e0e0;
  cursor: pointer;
  position: relative;
  transition: all 0.2s ease;
  min-height: 80px;
  display: flex;
  align-items: center;
  justify-content: center;
  text-align: center;
}
.sae-card:hover {
  transform: translateY(-2px);
  box-shadow: 0 5px 10px rgba(0,0,0,0.1);
  border-color: #ccc;
}
.sae-card.is-selected {
  border-color: #B51621;
  background-color: #fff5f5;
  box-shadow: 0 0 0 1px #B51621;
}
.sae-content {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 5px;
  width: 100%;
}
.sae-number {
  font-size: 16px;
  font-weight: 800;
  color: #333;
}
.sae-card.is-selected .sae-number {
  color: #B51621;
}
.sae-title {
  font-size: 12px;
  color: #666;
  line-height: 1.2;
}
.check-indicator {
  position: absolute;
  top: 5px;
  right: 5px;
  color: #B51621;
  background: white;
  border-radius: 50%;
  width: 18px;
  height: 18px;
  display: flex;
  align-items: center;
  justify-content: center;
  box-shadow: 0 1px 3px rgba(181, 22, 33, 0.2);
}
.empty-placeholder {
  text-align: center;
  font-size: 12px;
  color: #aaa;
  font-style: italic;
  margin-top: 20px;
}
.error-message {
  color: #E92533;
  text-align: center;
  font-weight: bold;
  margin-bottom: 30px;
}
.footer-actions {
  display: flex;
  justify-content: center;
  gap: 20px;
}
.btn-main {
  background: #B51621;
  color: white;
  border: none;
  padding: 14px 50px;
  border-radius: 10px;
  font-weight: bold;
  cursor: pointer;
  transition: background 0.2s;
}
.btn-main:hover { background: #94121b; }
.btn-sub {
  background: white;
  border: 2px solid #B51621;
  color: #B51621;
  padding: 14px 50px;
  border-radius: 10px;
  font-weight: bold;
  cursor: pointer;
  transition: background 0.2s;
}
.btn-sub:hover { background: #fff0f0; }
@media (max-width: 1200px) {
  .semester-board {
    grid-template-columns: repeat(3, 1fr);
  }
}
@media (max-width: 768px) {
  .semester-board {
    grid-template-columns: repeat(2, 1fr);
  }
}
@media (max-width: 500px) {
  .semester-board {
    grid-template-columns: 1fr;
  }
}
</style>