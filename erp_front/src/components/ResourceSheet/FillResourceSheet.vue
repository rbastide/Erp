<script setup lang="ts">
import { ref, nextTick, computed, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import AppHeader from '../App/Header.vue';
import Sidebar from '../App/Sidebar.vue';
import api from '@/services/api';

const router = useRouter()
const route = useRoute()

const resourceCode = ref('');
const currentHourlyVolId = ref<number | null>(null);
const currentResourceId = ref<number | null>(null);

let hours = ref({
  cm: 0,
  td: 0,
  ds: 0,
  tp: 0,
  ds_tp: 0,
  student: 0
})

const cmContents = ref(['']), cmRefs = ref<HTMLTextAreaElement[]>([])
const tdContents = ref(['']), tdRefs = ref<HTMLTextAreaElement[]>([])
const tpContents = ref(['']), tpRefs = ref<HTMLTextAreaElement[]>([])
const dsContents = ref(['']), dsRefs = ref<HTMLTextAreaElement[]>([])
const dstpContents = ref(['']), dstpRefs = ref<HTMLTextAreaElement[]>([])

const edFBContents = ref(['']), edFBRefs = ref<HTMLTextAreaElement[]>([])
const stFBContents = ref(['']), stFBRefs = ref<HTMLTextAreaElement[]>([])
const upgradesContents = ref(['']), upgradesRefs = ref<HTMLTextAreaElement[]>([])

const fetchResourceData = async () => {
  try {
    const response = await api.get('/resources/resources');
    if (response.data && Array.isArray(response.data)) {
      const targetCode = route.query.code as string;
      const found = targetCode
          ? response.data.find((r: any) => r.num === targetCode)
          : response.data[0];

      if (found) {
        resourceCode.value = found.num;
        currentResourceId.value = found.resourceID || found.id;
      }
    }
  } catch (error) {
    console.error("Erreur ressources :", error);
  }
};

const fetchHoursData = async () => {
  try {
    const response = await api.get('/mccc/getMccc');
    if (response.data && Array.isArray(response.data)) {
      const mcccFound = response.data.find((m: any) =>
          m.resourceId && m.resourceId.num === resourceCode.value
      );

      if (mcccFound && mcccFound.hourlyVolId) {
        const vol = mcccFound.hourlyVolId;
        hours.value = {
          cm: vol.nbHoursCM,
          td: vol.nbHoursTD,
          tp: vol.nbHoursTP,
          ds: vol.nbHoursDS,
          ds_tp: vol.nbHoursDSTP,
          student: vol.nbHoursDSTP + vol.nbHoursDS + vol.nbHoursTP + vol.nbHoursTD + vol.nbHoursCM
        };
        currentHourlyVolId.value = vol.hourlyVolID;
      }
    }
  } catch (error) {
    console.error("Erreur MCCC :", error);
  }
};

onMounted(async () => {
  await fetchResourceData();
  await fetchHoursData();
});

const createFieldManager = (contentRef: any, elementRefs: any) => {
  return async () => {
    contentRef.value.push('')
    await nextTick()
    const lastIndex = contentRef.value.length - 1
    elementRefs.value[lastIndex]?.focus()
  }
}

const addCM = createFieldManager(cmContents, cmRefs)
const addTD = createFieldManager(tdContents, tdRefs)
const addTP = createFieldManager(tpContents, tpRefs)
const addDS = createFieldManager(dsContents, dsRefs)
const addDSTP = createFieldManager(dstpContents, dstpRefs)
const addEducationalFeedback = createFieldManager(edFBContents, edFBRefs)
const addStudentFeedback = createFieldManager(stFBContents, stFBRefs)
const addUpgrades = createFieldManager(upgradesContents, upgradesRefs)

const handleValider = async () => {
  const formatForBackend = (type: string, list: string[]) => {
    return list
        .map((content, index) => content.trim() ? `${type} ${index + 1} : ${content.trim()}` : null)
        .filter((item): item is string => item !== null);
  };

  const allPedagogicalContent = [
    ...formatForBackend('CM', cmContents.value),
    ...formatForBackend('TD', tdContents.value),
    ...formatForBackend('TP', tpContents.value),
    ...formatForBackend('TD', dsContents.value),
    ...formatForBackend('TP', dstpContents.value)
  ];

  const payload = {
    resourceID: currentResourceId.value,
    hourlyVolumeID: currentHourlyVolId.value,
    teachersFeedbackID: edFBContents.value.filter(t => t.trim()),
    studentFeedbackID: stFBContents.value.filter(t => t.trim()),
    improvementsIdeaID: upgradesContents.value.filter(t => t.trim()),
    pedagologicalContent: allPedagogicalContent,
    semester: 1,
    year: new Date().getFullYear(),
    mainGoal: "Objectif pédagogique principal"
  };

  try {
    await api.post('/resourceSheet/resource-sheet', payload);
    alert("Fiche ressource enregistrée avec succès !");
    await router.push('/home');
  } catch (error: any) {
    const errorMsg = error.response?.data || "Erreur lors de l'enregistrement";
    console.error("Détails erreur :", error);
    alert(`Erreur : ${errorMsg}`);
  }
};

const handleRetour = () => router.back();

const totalGlobal = computed(() => {
  return hours.value.cm + hours.value.td + hours.value.ds + hours.value.tp + hours.value.ds_tp;
})

const validatePositive = (key: keyof typeof hours.value) => {
  if (hours.value[key] < 0 || hours.value[key] === null) hours.value[key] = 0;
}

const hourConfig = {
  cm: { label: 'CM', color: '#4DB6AC' },
  td: { label: 'TD', color: '#7986CB' },
  tp: { label: 'TP', color: '#4FC3F7' },
  ds: { label: 'DS', color: '#FFB74D' },
  ds_tp: { label: 'DS TP', color: '#BA68C8' }
}
</script>

<template>
  <Sidebar />
  <AppHeader :title="`Fiche de la Ressource ${resourceCode}`" />

  <main class="main-content">
    <div class="container">
      <div class="required-legend"><span>* champs obligatoires</span></div>

      <section class="form-card">
        <h2 class="section-title">Voici le nombre d'heures de CM, TD et TP : *</h2>
        <div class="hours-grid-wrapper">
          <div class="hours-row">
            <div class="hour-block" v-for="key in (['cm', 'td', 'ds'] as const)" :key="key">
              <label :style="{ color: hourConfig[key].color }">{{ hourConfig[key].label }}</label>
              <input type="number" v-model.number="hours[key]" class="box-input" min="0" @input="validatePositive(key)">
            </div>
          </div>
          <div class="hours-row mt-25">
            <div class="hour-block" v-for="key in (['tp', 'ds_tp'] as const)" :key="key">
              <label :style="{ color: hourConfig[key].color }">{{ hourConfig[key].label }}</label>
              <input type="number" v-model.number="hours[key]" class="box-input" min="0" @input="validatePositive(key)">
            </div>
            <div class="hour-block">
              <label style="color: #64748b;">Total Global</label>
              <div class="box-static total-highlight">{{ totalGlobal }} h</div>
            </div>
          </div>
        </div>
      </section>

      <section class="form-card">
        <h2 class="section-title">Voici le contenu pédagogique : *</h2>

        <div v-for="(list, type) in { CM: cmContents, TD: tdContents, TP: tpContents, DS: dsContents, 'DS/TP': dstpContents }" :key="type" class="pedagogic-group">
          <h3 class="group-label">{{ type }}</h3>
          <textarea v-for="(index) in list" :key="index" v-model="list[index]" class="modern-textarea" placeholder="Saisissez ici..."></textarea>
          <button class="btn-add-line" @click="type === 'CM' ? addCM() : type === 'TD' ? addTD() : type === 'TP' ? addTP() : type === 'DS' ? addDS() : addDSTP()">
            + Ajouter un bloc {{ type }}
          </button>
        </div>
      </section>

      <section class="form-card accent">
        <h2 class="section-title">Bilans et Évolutions</h2>

        <div class="pedagogic-group">
          <label class="field-label">Voici le retour pédagogique des professeurs :</label>
          <textarea v-for="(i) in edFBContents" :key="'ed-' + i" v-model="edFBContents[i]" class="modern-textarea large"></textarea>
          <button class="btn-add-line" @click="addEducationalFeedback">+ Ajouter une ligne</button>
        </div>

        <div class="pedagogic-group">
          <label class="field-label">Voici le retour des étudiants :</label>
          <textarea v-for="(i) in stFBContents" :key="'st-' + i" v-model="stFBContents[i]" class="modern-textarea large"></textarea>
          <button class="btn-add-line" @click="addStudentFeedback">+ Ajouter une ligne</button>
        </div>

        <div class="pedagogic-group">
          <label class="field-label">Voici les améliorations à apporter :</label>
          <textarea v-for="(i) in upgradesContents" :key="'up-' + i" v-model="upgradesContents[i]" class="modern-textarea large"></textarea>
          <button class="btn-add-line" @click="addUpgrades">+ Ajouter une ligne</button>
        </div>
      </section>

      <div class="actions-footer">
        <button @click="handleValider" class="btn btn-primary">Valider la saisie</button>
        <button @click="handleRetour" class="btn btn-outline">Annuler</button>
      </div>
    </div>
  </main>
</template>

<style scoped>
.main-content {
  background-color: #f4f7f9;
  min-height: 100vh;
  padding: 240px 20px 60px;
  font-family: 'Roboto', sans-serif;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.container {
  width: 100%;
  max-width: 900px;
  margin: 0 auto;
}

.required-legend {
  text-align: right;
  color: #E92533;
  font-size: 0.9rem;
  margin-bottom: 10px;
}

.form-card {
  background: white;
  border-radius: 12px;
  padding: 35px;
  margin-bottom: 30px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.05);
  border: 1px solid #e2e8f0;
}

.section-title {
  color: #E92533;
  font-size: 1.4rem;
  font-weight: 500;
  margin-bottom: 25px;
  border-left: 5px solid #E92533;
  padding-left: 15px;
}

.hours-row {
  display: flex;
  justify-content: space-between;
  gap: 20px;
}

.mt-25 {
  margin-top: 25px;
}

.hour-block {
  flex: 1;
  text-align: center;
}

.hour-block label {
  display: block;
  font-size: 0.9rem;
  font-weight: 800;
  margin-bottom: 8px;
  text-transform: uppercase;
}

.box-input {
  width: 100%;
  background: #f8fafc;
  border: 1px solid #e2e8f0;
  border-radius: 8px;
  height: 45px;
  font-size: 1.2rem;
  font-weight: 800;
  text-align: center;
}

.box-static {
  height: 45px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 1.3rem;
  font-weight: 800;
  border-radius: 8px;
  background: #f8fafc;
  border: 1px solid #e2e8f0;
}

.total-highlight {
  border: 2px solid #E92533;
  color: #E92533;
  background: #fff5f5;
}

.pedagogic-group {
  margin-bottom: 35px;
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
}

.group-label,
.field-label {
  align-self: flex-start;
  font-weight: 600;
  margin-bottom: 12px;
  color: #1e293b;
}

.modern-textarea {
  width: 100%;
  box-sizing: border-box;
  padding: 15px;
  border: 1px solid #e2e8f0;
  border-radius: 12px;
  font-size: 1rem;
  margin-bottom: 10px;
  resize: vertical;
}

.modern-textarea:focus {
  border-color: #E92533;
  outline: none;
}

.large {
  min-height: 110px;
}

.btn-add-line {
  background: transparent;
  border: none;
  font-weight: 700;
  cursor: pointer;
  padding: 8px 15px;
  color: #E92533;
}

.actions-footer {
  display: flex;
  justify-content: center;
  gap: 20px;
  margin-top: 20px;
  margin-bottom: 40px;
}

.btn {
  padding: 15px 45px;
  border-radius: 10px;
  font-weight: 700;
  cursor: pointer;
  border: none;
  transition: transform 0.2s;
}

.btn-primary {
  background: #E92533;
  color: white;
}

.btn-outline {
  background: white;
  color: #E92533;
  border: 2px solid #E92533;
}
</style>