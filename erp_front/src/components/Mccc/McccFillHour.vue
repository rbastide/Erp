<script setup lang="ts">
import {computed, onMounted, reactive, ref} from 'vue';
import {useRouter} from 'vue-router';
import {mcccStore} from '@/services/mcccStore';
import api from '@/services/api';
import AppHeader from '../App/Header.vue';
import Sidebar from '../App/Sidebar.vue';
import CancelModal from '../Information/CancelModal.vue';

mcccStore.loadMcccStore();
const router = useRouter();
const currentCourseHoursID = ref(null);
const showModal = ref(false);

const initialState = ref({
  minCM: 0,
  minTD: 0,
  minTP: 0,
  minDS: 0,
  minDSTP: 0
});

const splitHours = reactive({
  minCM: { h: '', m: '00' },
  minTD: { h: '', m: '00' },
  minTP: { h: '', m: '00' },
  minDS: { h: '', m: '00' },
  minDSTP: { h: '', m: '00' }
});

const hourTypes = [
  { key: 'minCM', label: 'CM', color: '#4DB6AC' },
  { key: 'minTD', label: 'TD', color: '#7986CB' },
  { key: 'minTP', label: 'TP', color: '#4FC3F7' },
  { key: 'minDS', label: 'DS', color: '#FFB74D' },
  { key: 'minDSTP', label: 'DS TP', color: '#BA68C8' },
];

const convertDecimalToSplit = (minutes: number): { h: string, m: string } => {
  const min = minutes % 60;
  const normalizedMin = min < 10 ? `0${min}` : `${min}`;
  return {h: (Math.floor(minutes / 60) || 0).toString(), m: normalizedMin};
}

const convertSplitToDecimal = (time: { h: string, m: string }) => {
  const h = parseInt(time.h || '0');
  const m = parseInt(time.m || '0');
  return h * 60 + m;
};


const validateInput = (key: string, subType: 'h' | 'm', event: Event) => {
  const input = event.target as HTMLInputElement;
  let val = input.value.replace(/[^0-9]/g, '');

  if (subType === 'h') {
    if (val.length > 3) val = val.substring(0, 3);
    if (val.length > 1 && val.startsWith('0')) {
      val = val.replace(/^0+/, '');
    }
    if (val !== '' && parseInt(val) > 999) val = '999';
  } else {
    if (val.length > 2) val = val.substring(0, 2);
    if (val !== '' && parseInt(val) > 59) val = '59';
  }

  (splitHours as any)[key][subType] = val;
  input.value = val;

  const decimalValue = convertSplitToDecimal((splitHours as any)[key]);
  (mcccStore as any)[key] = decimalValue;
  mcccStore.registerMcccStore();
};

const formatMinutesOnBlur = (key: string) => {
  let val = (splitHours as any)[key].m;
  if (!val) {
    (splitHours as any)[key].m = '00';
  } else if (val.length === 1) {
    (splitHours as any)[key].m = '0' + val;
  }
};

const formatHoursOnBlur = (key: string) => {
};


const syncSplitFromStore = () => {
  splitHours.minCM = convertDecimalToSplit(mcccStore.minCM);
  splitHours.minTD = convertDecimalToSplit(mcccStore.minTD);
  splitHours.minTP = convertDecimalToSplit(mcccStore.minTP);
  splitHours.minDS = convertDecimalToSplit(mcccStore.minDS);
  splitHours.minDSTP = convertDecimalToSplit(mcccStore.minDSTP);
};

const fetchCourseHours = async () => {
  if (!mcccStore.resourceID) return;

  const targetId = String(mcccStore.resourceID);

  try {
    const response = await api.get('/mccc/mcccs');
    const currentMccc = response.data.find((m: any) =>
        m.resourceId && String(m.resourceId.resourceID) === targetId
    );

    if (currentMccc && currentMccc.courseHoursId) {
      const bddData = currentMccc.courseHoursId;
      currentCourseHoursID.value = bddData.courseHoursID;

      mcccStore.minCM = bddData.nbMinCM || 0;
      mcccStore.minTD = bddData.nbMinTD || 0;
      mcccStore.minTP = bddData.nbMinTP || 0;
      mcccStore.minDS = bddData.nbMinDS || 0;
      mcccStore.minDSTP = bddData.nbMinDSTP || 0;
    } else {
      mcccStore.minCM = 0;
      mcccStore.minTD = 0;
      mcccStore.minTP = 0;
      mcccStore.minDS = 0;
      mcccStore.minDSTP = 0;
    }
    mcccStore.registerMcccStore();
    syncSplitFromStore();
  } catch (error) {
    console.error("Erreur chargement volumes horaires :", error);
  }
};

onMounted(async () => {
  mcccStore.loadMcccStore();

  const hasLocalData = (
      (mcccStore.minCM || 0) +
      (mcccStore.minTD || 0) +
      (mcccStore.minTP || 0) +
      (mcccStore.minDS || 0) +
      (mcccStore.minDSTP || 0)
  ) > 0;

  if (hasLocalData) {
    syncSplitFromStore();
  } else {
    await fetchCourseHours();
  }

  initialState.value = {
    minCM: mcccStore.minCM || 0,
    minTD: mcccStore.minTD || 0,
    minTP: mcccStore.minTP || 0,
    minDS: mcccStore.minDS || 0,
    minDSTP: mcccStore.minDSTP || 0
  };
});

const totalHeures = computed(() => {
  let totalMinutes = 0;
  Object.values(splitHours).forEach(time => {
    const h = parseInt(time.h || '0');
    const m = parseInt(time.m || '0');
    totalMinutes += (h * 60) + m;
  });

  const h = Math.floor(totalMinutes / 60);
  const m = Math.round(totalMinutes % 60);
  return m > 0 ? `${h}h${m.toString().padStart(2, '0')}` : `${h}h`;
});

const handleValidate = async () => {
  mcccStore.registerMcccStore();
  await router.push('/mccc-menu');
};

const handleBack = () => {
  showModal.value = true;
};

const onConfirmCancel = () => {
  mcccStore.minCM = initialState.value.minCM;
  mcccStore.minTD = initialState.value.minTD;
  mcccStore.minTP = initialState.value.minTP;
  mcccStore.minDS = initialState.value.minDS;
  mcccStore.minDSTP = initialState.value.minDSTP;
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

          <div class="time-input-container">
            <input
                type="text"
                v-model="(splitHours as any)[type.key].h"
                @input="validateInput(type.key, 'h', $event)"
                @blur="formatHoursOnBlur(type.key)"
                placeholder="0"
                maxlength="3"
                class="time-input"
            >
            <span class="time-separator">h</span>
            <input
                type="text"
                v-model="(splitHours as any)[type.key].m"
                @input="validateInput(type.key, 'm', $event)"
                @blur="formatMinutesOnBlur(type.key)"
                placeholder="00"
                maxlength="2"
                class="time-input"
            >
          </div>
        </div>
      </div>

      <div class="total-section">
        <p>Total : <span class="total-number">{{ totalHeures }}</span></p>
      </div>

      <div class="actions-container">
        <button @click="handleValidate" class="btn btn-primary">Valider</button>
        <button @click="handleBack" class="btn btn-outline">Annuler</button>
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

.time-input-container {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 5px;
  background: #f8fafc;
  border: 1px solid #e2e8f0;
  border-radius: 8px;
  padding: 8px 12px;
  width: 140px;
  height: 50px;
  box-sizing: border-box;
}

.time-input {
  width: 40px;
  border: none;
  background: transparent;
  font-size: 1.4rem;
  font-weight: 700;
  text-align: center;
  color: #2d3436;
  outline: none;
  padding: 0;
}

.time-input::placeholder {
  color: #cbd5e1;
  font-weight: 400;
}

.time-separator {
  color: #94a3b8;
  font-weight: 700;
  font-size: 1.2rem;
  margin-top: -3px;
}

.time-input-container:focus-within {
  border-color: #B51621;
  box-shadow: 0 0 0 3px rgba(181, 22, 33, 0.1);
  background: #fff;
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