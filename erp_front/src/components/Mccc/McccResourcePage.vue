<script setup lang="ts">
import {onMounted, ref, watch} from 'vue';
import {useRouter} from 'vue-router';
import {mcccStore} from '@/services/mcccStore';
import AppHeader from '../App/Header.vue';
import Sidebar from '../App/Sidebar.vue';
import api from '@/services/api';

const router = useRouter();

interface Resource {
  resourceID: number;
  num: string;
  name: string;
  semester: string;
}

const nextYear = ref<number>(new Date().getFullYear() + 1);
const selectedYear = ref<number>(nextYear.value);
const resources = ref<Resource[]>([]);
const availableYears = ref<number[]>([nextYear.value]);

const fileInput = ref<HTMLInputElement | null>(null);
const isImporting = ref(false);

const fetchYears = async () => {
  try {
    const response = await api.get('/mccc/available-years');
    availableYears.value = availableYears.value.concat(response.data)
    availableYears.value.sort((a, b) => a - b);
    if (availableYears.value.length > 0 && !availableYears.value.includes(selectedYear.value)) {
      selectedYear.value = availableYears.value[0];
    }
  } catch (error) {
    console.error("Erreur années:", error);
  }
};

const fetchMcccsByYear = async () => {
  try {
    const response = await api.get(`/mccc/mcccs/${selectedYear.value}`);

    resources.value = response.data.map((item: any) => {
      return {
        resourceID: item.resourceID,
        num: item.num,
        name: item.name
      };
    });
  } catch (error) {
    console.error("Erreur ressources:", error);
    resources.value = [];
  }
};

const fetchResources = async () => {
  try {
    const response = await api.get('/resources/resources');
    resources.value = response.data.map((item: any) => {
      return {
        resourceID: item.resourceID,
        num: item.num,
        name: item.name
      };
    });
  } catch (error) {
    console.error("Erreur lors du chargement des ressources:", error);
  }
}

onMounted(async () => {
  await fetchResources();
  fetchYears();
  mcccStore.loadMcccStore();
  mcccStore.saveBackup();
});

watch(selectedYear, async (newYear) => {
  mcccStore.year = newYear;
  console.log(selectedYear.value, nextYear.value)
  if (selectedYear.value !== nextYear.value) {
    await fetchMcccsByYear();
  } else {
    await fetchResources();
  }
});

const handleBack = () => {
  router.back();
};

const retrieveData = async (year: number, resourceID: number) => {
  try {
    const mcccResponse = await api.get(`/mccc/${year}/${resourceID}`);
    const mcccData = mcccResponse.data;
    mcccStore.resourceID = mcccData.resourceID;
    mcccStore.resourceCode = mcccData.resourceCode;
    mcccStore.minCM = mcccData.minCM;
    mcccStore.minTD = mcccData.minTD;
    mcccStore.minTP = mcccData.minTP;
    mcccStore.minDS = mcccData.minDS;
    mcccStore.minDSTP = mcccData.minDSTP;
    mcccStore.saeIds = mcccData.saeIDs || [];
    mcccStore.skillIds = mcccData.skillIDs || [];
    mcccStore.teacherIds = mcccData.teachers || [];
    mcccStore.creationDate = mcccData.creationDate;
    mcccStore.editDate = mcccData.editDate;
  } catch (error) {
    console.log("Création d'un MCCC non existant : On reset les données sauf l'ID");
    const currentRes = resources.value.find(r => r.resourceID === resourceID);
    mcccStore.clearMcccStore();
    mcccStore.resourceID = resourceID;
    mcccStore.year = year;
    if (currentRes) mcccStore.resourceCode = currentRes.num;
  }
}

const handleMccc = async (id: number) => {
  mcccStore.loadMcccStore();
  mcccStore.resourceID = id;
  mcccStore.year = selectedYear.value;
  await retrieveData(selectedYear.value, id);
  mcccStore.registerMcccStore();
  await router.push('/mccc-menu');
}

const triggerFileInput = () => {
  fileInput.value?.click();
};

const handleFileUpload = async (event: Event) => {
  const target = event.target as HTMLInputElement;
  const file = target.files?.[0];

  if (!file) return;

  const formData = new FormData();
  formData.append('file', file);
  formData.append('year', selectedYear.value.toString());

  try {
    isImporting.value = true;
    const response = await api.post('/mccc/import', formData, {
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    });

    alert(response.data || "Les MCCC ont été importées et sauvegardées avec succès !");
    await fetchMcccsByYear();
  } catch (error: any) {
    console.error("Erreur lors de l'importation :", error);
    alert(error.response?.data || "Une erreur est survenue lors de l'importation du fichier.");
  } finally {
    isImporting.value = false;
    if (fileInput.value) {
      fileInput.value.value = '';
    }
  }
};
</script>

<template>
  <Sidebar/>
  <div class="page-container">
    <AppHeader title="Choix ressources" />

    <main class="main-content">
      <div class="top-controls">
        <div class="section-title">Veuillez choisir la ressource :</div>
        <div class="year-filter-wrapper">
          <div class="year-filter">
            <label for="year-select">Année :</label>
            <select id="year-select" v-model="selectedYear" class="year-select">
              <option v-for="year in availableYears" :key="year" :value="year">
                {{ year }} / {{ year + 1 }}
              </option>
            </select>
          </div>
        </div>
      </div>

      <div class="grid-container">
        <div
            v-for="res in resources"
            :key="res.resourceID"
            class="card-action"
            @click="handleMccc(res.resourceID)"
        >
          <div class="icon-circle">
            <svg width="40" height="40" viewBox="0 0 24 24" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" style="fill: none;">
              <path d="M4 19.5A2.5 2.5 0 0 1 6.5 17H20"></path>
              <path d="M6.5 2H20v20H6.5A2.5 2.5 0 0 1 4 19.5v-15A2.5 2.5 0 0 1 6.5 2z"></path>
            </svg>
          </div>
          <p>{{ res.num }}</p>
          <span class="res-name">{{ res.name }}</span>
        </div>
      </div>

      <div v-if="resources.length === 0" class="empty-msg">
        Aucune ressource disponible en base de données.
      </div>

      <div class="footer-actions">
        <input
            type="file"
            ref="fileInput"
            @change="handleFileUpload"
            accept=".xlsx, .xls"
            style="display: none"
        />

        <button @click="triggerFileInput" class="import-btn" :disabled="isImporting">
          {{ isImporting ? 'Importation en cours...' : 'Importer un fichier Excel' }}
        </button>

        <button @click="handleBack" class="quit-btn">Retour</button>
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
  margin-top: 175px;
}

.top-controls {
  width: 100%;
  max-width: 1200px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 40px;
}

.section-title {
  font-size: 32px;
  font-weight: 400;
  color: #E92533;
}

.year-filter-wrapper {
  display: flex;
  justify-content: flex-end;
}

.year-filter {
  display: flex;
  align-items: center;
  gap: 10px;
  background: white;
  padding: 8px 15px;
  border-radius: 8px;
  border: 1px solid #e2e8f0;
  box-shadow: 0 2px 8px rgba(0,0,0,0.05);
}

.year-filter label {
  font-size: 0.9rem;
  font-weight: 600;
  color: #64748b;
}

.year-select {
  padding: 5px 10px;
  border-radius: 6px;
  border: 1px solid #cbd5e1;
  background-color: #f8fafc;
  color: #1e293b;
  font-weight: 700;
  cursor: pointer;
  outline: none;
}

.grid-container {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
  gap: 30px;
  width: 100%;
  max-width: 1200px;
  justify-items: center;
}

.card-action {
  cursor: pointer;
  background: #ffffff;
  width: 100%;
  max-width: 340px;
  height: 220px;
  border-radius: 15px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  text-align: center;
  padding: 20px;
  transition: all 0.3s cubic-bezier(0.25, 0.8, 0.25, 1);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
  border: 1px solid rgba(181, 22, 33, 0.1);
}

.card-action:hover {
  transform: translateY(-8px);
  box-shadow: 0 12px 24px rgba(181, 22, 33, 0.15);
  border-color: #B51621;
}

.icon-circle {
  width: 70px;
  height: 70px;
  background: rgba(181, 22, 33, 0.05);
  border-radius: 50%;
  display: flex;
  justify-content: center;
  align-items: center;
  margin-bottom: 15px;
  color: #B51621;
  transition: all 0.3s ease;
}

.card-action:hover .icon-circle {
  background: #B51621;
  color: #ffffff;
}

.card-action p {
  margin: 0;
  font-size: 32px;
  font-weight: 600;
  color: #333333;
  transition: color 0.3s ease;
}

.res-name {
  font-size: 14px;
  color: #666;
  margin-top: 5px;
}

.empty-msg {
  margin-top: 40px;
  color: #999;
  font-style: italic;
}

.footer-actions {
  margin-top: 60px;
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 15px;
}

.import-btn {
  width: 250px;
  padding: 12px;
  border: 2px solid #217346;
  text-align: center;
  border-radius: 8px;
  background-color: #217346;
  color: #FFFFFF;
  font-size: 1.1rem;
  font-weight: bold;
  cursor: pointer;
  transition: all 0.3s ease;
}

.import-btn:hover:not(:disabled) {
  background-color: transparent;
  color: #217346;
}

.import-btn:disabled {
  opacity: 0.7;
  cursor: not-allowed;
}

.quit-btn {
  width: 250px;
  padding: 12px;
  border: 2px solid #B51621;
  text-align: center;
  border-radius: 8px;
  background-color: #B51621;
  color: #FFFFFF;
  font-size: 1.1rem;
  font-weight: bold;
  cursor: pointer;
  transition: all 0.3s ease;
}

.quit-btn:hover {
  background-color: transparent;
  color: #B51621;
}

@media (max-width: 750px) {
  .top-controls {
    flex-direction: column;
    gap: 20px;
  }
  .section-title {
    font-size: 24px;
  }
}
</style>