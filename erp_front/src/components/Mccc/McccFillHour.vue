<script setup lang="ts">
import {computed, onMounted, ref} from 'vue';
import {useRouter} from 'vue-router';
import {mcccStore} from '@/services/mcccStore';
import api from '@/services/api';
import AppHeader from '../App/Header.vue';
import Sidebar from '../App/Sidebar.vue';
import CancelModal from '../Information/CancelModal.vue';

mcccStore.loadMcccStore();
const router = useRouter();
const currentHourlyVolID = ref(null);
const showModal = ref(false);

const initialState = ref({
  hoursCM: 0,
  hoursTD: 0,
  hoursTP: 0,
  hoursDS: 0,
  hoursDSTP: 0
});

const hourTypes = [
  { key: 'hoursCM', label: 'CM', color: '#4DB6AC' },
  { key: 'hoursTD', label: 'TD', color: '#7986CB' },
  { key: 'hoursTP', label: 'TP', color: '#4FC3F7' },
  { key: 'hoursDS', label: 'DS', color: '#FFB74D' },
  { key: 'hoursDSTP', label: 'DS TP', color: '#BA68C8' },
];

const fetchHourlyVolumes = async () => {
  if (!mcccStore.resourceID) return;

  const targetId = String(mcccStore.resourceID);
  console.log("Récupération des heures pour resourceID:", targetId);

  try {
    const response = await api.get('/mccc/mcccs');

    const currentMccc = response.data.find((m: any) =>
        m.resourceId && String(m.resourceId.resourceID) === targetId
    );

    if (currentMccc && currentMccc.hourlyVolId) {
      console.log("Heures trouvées en BDD :", currentMccc.hourlyVolId);
      const bddData = currentMccc.hourlyVolId;

      currentHourlyVolID.value = bddData.hourlyVolID;

      mcccStore.hoursCM = bddData.nbHoursCM || 0;
      mcccStore.hoursTD = bddData.nbHoursTD || 0;
      mcccStore.hoursTP = bddData.nbHoursTP || 0;
      mcccStore.hoursDS = bddData.nbHoursDS || 0;
      mcccStore.hoursDSTP = bddData.nbHoursDSTP || 0;
    } else {
      console.log("Pas d'heures trouvées, mise à zéro.");
      mcccStore.hoursCM = 0;
      mcccStore.hoursTD = 0;
      mcccStore.hoursTP = 0;
      mcccStore.hoursDS = 0;
      mcccStore.hoursDSTP = 0;
    }
    mcccStore.saveBackup();
  } catch (error) {
    console.error("Erreur chargement volumes horaires :", error);
  }
};

onMounted(async () => {
  mcccStore.loadMcccStore();

  const hasLocalData = (
      (mcccStore.hoursCM || 0) +
      (mcccStore.hoursTD || 0) +
      (mcccStore.hoursTP || 0) +
      (mcccStore.hoursDS || 0) +
      (mcccStore.hoursDSTP || 0)
  ) > 0;

  if (hasLocalData) {
    console.log("Utilisation des heures du store local");
  } else {
    await fetchHourlyVolumes();
  }

  initialState.value = {
    hoursCM: mcccStore.hoursCM || 0,
    hoursTD: mcccStore.hoursTD || 0,
    hoursTP: mcccStore.hoursTP || 0,
    hoursDS: mcccStore.hoursDS || 0,
    hoursDSTP: mcccStore.hoursDSTP || 0
  };
});

const totalHeures = computed(() => {
  return (mcccStore.hoursCM || 0) +
      (mcccStore.hoursTD || 0) +
      (mcccStore.hoursDS || 0) +
      (mcccStore.hoursTP || 0) +
      (mcccStore.hoursDSTP || 0);
});

const updateHours = (key: string, delta: number) => {
  const current = (mcccStore as any)[key] || 0;
  (mcccStore as any)[key] = Math.max(0, current + delta);
  mcccStore.registerMcccStore();
};

const validateInput = (key: string) => {
  const value = (mcccStore as any)[key];
  if (value === "" || value === null || value === undefined) {
    (mcccStore as any)[key] = 0;
  } else if (value < 0) {
    (mcccStore as any)[key] = 0;
  }
};

const handleValider = async () => {
  mcccStore.registerMcccStore();
  await router.push('/mccc-menu');
};

const handleRetour = () => {
  showModal.value = true;
};

const onConfirmCancel = () => {
  mcccStore.hoursCM = initialState.value.hoursCM;
  mcccStore.hoursTD = initialState.value.hoursTD;
  mcccStore.hoursTP = initialState.value.hoursTP;
  mcccStore.hoursDS = initialState.value.hoursDS;
  mcccStore.hoursDSTP = initialState.value.hoursDSTP;

  mcccStore.registerMcccStore();

  router.push('/mccc-menu');
};
</script>

<template>
  <Sidebar/>
  <AppHeader title="Heures par élève pour la" :inline="mcccStore.resourceCode"/>

  <main class="main-content">
    <div class="content-wrapper">

      <div class="hours-flex-container">
        <div v-for="type in hourTypes" :key="type.key" class="hour-card">
          <span class="card-label" :style="{ color: type.color }">{{ type.label }}</span>

          <div class="input-container">
            <button type="button" class="step-btn" @click="updateHours(type.key, -1)">−</button>

            <input
                class="hour-input"
                type="number"
                v-model.number="(mcccStore as any)[type.key]"
                min="0"
                @input="() => { validateInput(type.key); mcccStore.registerMcccStore(); }"
            >

            <button type="button" class="step-btn" @click="updateHours(type.key, 1)">+</button>
          </div>
        </div>
      </div>

      <div class="total-section">
        <p>Total : <span class="total-number">{{ totalHeures }}</span> h</p>
      </div>

      <div class="actions-container">
        <button @click="handleValider" class="btn btn-primary">Valider</button>
        <button @click="handleRetour" class="btn btn-outline">Annuler</button>
      </div>

    </div>

    <CancelModal
        v-if="showModal"
        @confirm="onConfirmCancel"
        @close="showModal = false"
    />

  </main>
</template>

<style scoped>
.main-content {
  padding-top: 220px;
  min-height: 100vh;
  background-color: #fcfcfc;
  font-family: 'Roboto', sans-serif;
}

.content-wrapper {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 40px;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
}

.hours-flex-container {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  width: 100%;
  gap: 30px;
  margin-bottom: 60px;
}

.hour-card {
  background: white;
  border-radius: 16px;
  padding: 25px;
  width: 260px;
  display: flex;
  flex-direction: column;
  align-items: center;
  box-shadow: 0 10px 25px rgba(0, 0, 0, 0.05);
  border: 1px solid #f0f0f0;
  transition: all 0.3s ease;
}

.hour-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 15px 35px rgba(0, 0, 0, 0.1);
}

.card-label {
  font-weight: 700;
  font-size: 1.4rem;
  margin-bottom: 20px;
  text-transform: uppercase;
}

.input-container {
  display: flex;
  align-items: center;
  gap: 10px;
  background: #f1f3f5;
  border-radius: 12px;
  padding: 8px 12px;
}

.hour-input {
  width: 70px;
  border: none;
  background: transparent;
  text-align: center;
  font-size: 1.8rem;
  font-weight: 800;
  color: #2d3436;
}

.hour-input::-webkit-outer-spin-button,
.hour-input::-webkit-inner-spin-button {
  -webkit-appearance: none;
  margin: 0;
}

.step-btn {
  background: white;
  border: none;
  border-radius: 50%;
  width: 40px;
  height: 40px;
  cursor: pointer;
  font-size: 1.5rem;
  box-shadow: 0 2px 5px rgba(0,0,0,0.1);
  display: flex;
  align-items: center;
  justify-content: center;
  color: #B51621;
  transition: all 0.2s;
}

.step-btn:hover {
  background: #B51621;
  color: white;
}

.total-section {
  width: 100%;
  text-align: center;
  margin: 50px 0;
  font-size: 40px;
}

.total-number {
  font-weight: 900;
  color: #B51621;
}

.actions-container {
  width: 100%;
  display: flex;
  justify-content: center;
  gap: 25px;
  padding-bottom: 50px;
}

.btn {
  padding: 15px 50px;
  border-radius: 10px;
  font-size: 1.1rem;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.2s;
}

.btn-primary {
  background-color: #B51621;
  color: white;
  border: none;
}

.btn-primary:hover {
  background-color: #8e111a;
  transform: scale(1.05);
}

.btn-outline {
  background-color: transparent;
  color: #B51621;
  border: 2px solid #B51621;
}

.btn-outline:hover {
  background-color: #fff5f5;
}
</style>