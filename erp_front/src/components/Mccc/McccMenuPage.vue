<script setup lang="ts">
import { useRouter } from 'vue-router';
import { onMounted, ref, watch, nextTick } from 'vue';
import AppHeader from '../App/Header.vue';
import Sidebar from '../App/Sidebar.vue';
import { mcccStore } from '@/services/mcccStore';
import CancelModal from '../Information/CancelModal.vue';
import api from '@/services/api';

const router = useRouter();
const showModal = ref(false);
const availableYears = ref<number[]>([]);
const selectedYear = ref<number>(new Date().getFullYear());
const isReady = ref(false);

const fetchResourceYears = async () => {
  try {
    const response = await api.get('/mccc/available-years');
    const allYears: number[] = response.data;
    const checkPromises = allYears.map(async (year) => {
      try {
        const res = await api.get(`/mccc/${year}/${mcccStore.resourceID}`);
        return res.status === 200 ? year : null;
      } catch (e) {
        return null;
      }
    });
    const results = await Promise.all(checkPromises);
    let filteredYears = results.filter((y): y is number => y !== null);
    const nextYear = new Date().getFullYear() + 1;
    if (!filteredYears.includes(nextYear)) {
      filteredYears.push(nextYear);
    }
    availableYears.value = filteredYears.sort((a, b) => b - a);
    if (mcccStore.year && !availableYears.value.includes(Number(mcccStore.year))) {
      availableYears.value.push(Number(mcccStore.year));
      availableYears.value.sort((a, b) => b - a);
    }

  } catch (error) {
    console.error("Erreur filtrage années:", error);
    const current = new Date().getFullYear();
    availableYears.value = [current + 1, current];
  }
};

const fetchMcccData = async (year: number) => {
  try {
    const response = await api.get(`/mccc/${year}/${mcccStore.resourceID}`);
    const data = response.data;
    mcccStore.minCM = data.minCM || 0;
    mcccStore.minTD = data.minTD || 0;
    mcccStore.minTP = data.minTP || 0;
    mcccStore.minDS = data.minDS || 0;
    mcccStore.minDSTP = data.minDSTP || 0;
    mcccStore.creationDate = data.creationDate;
    mcccStore.teacherIds = data.teacherIds || [];
    mcccStore.saeIds = data.saeIds || [];
    mcccStore.year = year;

    mcccStore.registerMcccStore();
  } catch (error) {
    if (error.response && error.response.status === 404) {
      console.log("Fiche inexistante pour cette année, création d'une nouvelle.");
      const resId = mcccStore.resourceID;
      const resCode = mcccStore.resourceCode;

      mcccStore.clearMcccStore();

      mcccStore.resourceID = resId;
      mcccStore.resourceCode = resCode;
      mcccStore.year = year;
      mcccStore.registerMcccStore();
    } else {
      console.error("Erreur technique lors du chargement:", error);
    }
  }
};

watch(selectedYear, async (newYear) => {
  if (!isReady.value) return;
  mcccStore.year = newYear;
  await fetchMcccData(newYear);
});

onMounted(async () => {
  mcccStore.loadMcccStore();
  if (mcccStore.year) {
    selectedYear.value = Number(mcccStore.year);
  }
  await fetchResourceYears();
  await fetchMcccData(selectedYear.value);
  mcccStore.saveBackup();
  await nextTick();
  isReady.value = true;
});

const handleBack = () => { showModal.value = true; };
const onConfirmCancel = () => { mcccStore.restoreBackup(); router.push('/home'); };
const handleHours = () => router.push('/mccc-hours');
const handleTeachers = () => router.push('/mccc-teachers');
const handleSae = () => router.push('/mccc-sae');
const handleCompetences = () => router.push('/mccc-skills');

const handleValidate = () => {
  mcccStore.backup = null;
  mcccStore.registerMcccStore();
  router.push('/mccc-summary');
};
</script>

<template>
  <Sidebar/>
  <div class="page-container">
    <AppHeader :title="mcccStore.resourceCode" />
    <main class="main-content">

      <div class="top-menu-bar">
        <div class="info-res">
          Configuration pour la ressource <strong>{{ mcccStore.resourceCode }}</strong>
        </div>
        <div class="year-selector-container">
          <label>Édition :</label>
          <select v-model="selectedYear" class="year-select">
            <option v-for="year in availableYears" :key="year" :value="year">
              {{ year }} / {{ year + 1 }}
            </option>
          </select>
        </div>
      </div>

      <div class="grid-container">
        <div class="card-action" @click="handleHours">
          <div class="icon-circle">
            <svg width="35" height="35" viewBox="0 0 24 24" stroke="currentColor" stroke-width="2" style="fill: none;"><circle cx="12" cy="12" r="10"></circle><polyline points="12 6 12 12 16 14"></polyline></svg>
          </div>
          <p>Heures</p>
        </div>

        <div class="card-action" @click="handleCompetences">
          <div class="icon-circle">
            <svg width="35" height="35" viewBox="0 0 24 24" stroke="currentColor" stroke-width="2" style="fill: none;"><path d="M22 11.08V12a10 10 0 1 1-5.93-9.14"></path><polyline points="22 4 12 14.01 9 11.01"></polyline></svg>
          </div>
          <p>Objectifs / Compétences</p>
        </div>

        <div class="card-action" @click="handleSae">
          <div class="icon-circle">
            <svg width="35" height="35" viewBox="0 0 24 24" stroke="currentColor" stroke-width="2" style="fill: none;"><path d="M14.7 6.3a1 1 0 0 0 0 1.4l1.6 1.6a1 1 0 0 0 1.4 0l3.77-3.77a6 6 0 0 1-7.94 7.94l-6.91 6.91a2.12 2.12 0 0 1-3-3l6.91-6.91a6 6 0 0 1 7.94-7.94l-3.76 3.76z"></path></svg>
          </div>
          <p>SAE</p>
        </div>

        <div class="card-action" @click="handleTeachers">
          <div class="icon-circle">
            <svg width="35" height="35" viewBox="0 0 24 24" stroke="currentColor" stroke-width="2" style="fill: none;"><path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"></path><circle cx="9" cy="7" r="4"></circle><path d="M23 21v-2a4 4 0 0 0-3-3.87"></path><path d="M16 3.13a4 4 0 0 1 0 7.75"></path></svg>
          </div>
          <p>Enseignant(s)</p>
        </div>
      </div>

      <div class="container-btn">
        <button @click="handleValidate" class="btn-sys primary">Valider</button>
        <button @click="handleBack" class="btn-sys secondary">Annuler</button>
      </div>

      <CancelModal v-if="showModal" @confirm="onConfirmCancel" @close="showModal = false" />
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
.top-menu-bar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
  max-width: 800px;
  margin-bottom: 30px;
  padding: 15px;
  background: white;
  border-radius: 12px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.05);
}
.info-res {
  color: #555;
  font-size: 1.1rem;
}
.year-selector-container {
  display: flex;
  align-items: center;
  gap: 10px;
}
.year-select {
  padding: 8px 12px;
  border-radius: 6px;
  border: 1px solid #ddd;
  font-weight: bold;
  color: #B51621;
  cursor: pointer;
}
.grid-container {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 30px;
  width: 100%;
  max-width: 800px;
  justify-items: center;
  margin-bottom: 50px;
}
.card-action {
  cursor: pointer;
  background: #ffffff;
  width: 340px;
  height: 200px;
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
}
.card-action:hover .icon-circle {
  background: #B51621;
  color: #ffffff;
}
.card-action p {
  margin: 0;
  font-size: 24px;
  font-weight: 500;
  color: #333333;
}
.card-action:hover p {
  color: #B51621;
}
.container-btn {
  display: flex;
  gap: 20px;
  margin-top: 20px;
}
.btn-sys {
  width: 180px;
  padding: 15px;
  border-radius: 8px;
  font-size: 18px;
  font-weight: bold;
  cursor: pointer;
  transition: all 0.3s ease;
  border: none;
}
.btn-sys.primary {
  background-color: #B51621;
  color: white;
}
.btn-sys.secondary {
  background-color: #6c757d;
  color: white;
}
@media (max-width: 800px) {
  .grid-container {
    grid-template-columns: 1fr;
  }
  .top-menu-bar {
    flex-direction: column;
    gap: 15px;
    text-align: center;
  }
}
</style>