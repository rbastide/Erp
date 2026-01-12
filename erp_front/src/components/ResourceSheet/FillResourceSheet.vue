<script setup lang="ts">
import { ref, nextTick, computed, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import AppHeader from '../App/Header.vue';
import Sidebar from '../App/Sidebar.vue';
import api from '@/services/api';

const router = useRouter()
const route = useRoute()

const resourceCode = ref('');
const currentResourceId = ref<number | null>(null);

const fetchResourceData = async () => {
  try {
    const response = await api.get('/resources/resources');

    if (response.data && Array.isArray(response.data)) {
      const targetCode = route.query.code as string;

      if (targetCode) {
        const found = response.data.find((r: any) => r.num === targetCode);

        if (found) {
          resourceCode.value = found.num;
          currentResourceId.value = found.resourceID || found.id;
        } else {
          console.warn(`Ressource avec le code ${targetCode} non trouvée.`);
        }
      } else {
        if (response.data.length > 0) {
          const first = response.data[0];
          resourceCode.value = first.num;
          currentResourceId.value = first.resourceID || first.id;
        }
      }
    }
  } catch (error) {
    console.error("Erreur lors de la récupération de la ressource :", error);
  }
};

onMounted(() => {
  fetchResourceData();
});

const handleRetour = () =>{ router.back()};

const handleExport = () => {
  console.log("Export PDF demandé...");
};

const hours = ref({
  cm: 0,
  td: 0,
  ds: 0,
  tp: 0,
  ds_tp: 0,
  student: 0
})

const hourConfig = {
  cm: { label: 'CM', color: '#4DB6AC' },
  td: { label: 'TD', color: '#7986CB' },
  tp: { label: 'TP', color: '#4FC3F7' },
  ds: { label: 'DS', color: '#FFB74D' },
  ds_tp: { label: 'DS TP', color: '#BA68C8' }
}

const totalGlobal = computed(() => {
  return (hours.value.cm || 0) +
      (hours.value.td || 0) +
      (hours.value.ds || 0) +
      (hours.value.tp || 0) +
      (hours.value.ds_tp || 0)
})

const blockNegative = (e: KeyboardEvent) => { if (e.key === '-') e.preventDefault() }

const validatePositive = (key: keyof typeof hours.value) => {
  const value = hours.value[key] as any;

  if (value === "" || value === null || value === undefined || value < 0) {
    hours.value[key] = 0;
  }
}

const createFieldManager = (contentRef: any, elementRefs: any) => {
  return async () => {
    contentRef.value.push('')
    await nextTick()
    const lastIndex = elementRefs.value.length - 1
    elementRefs.value[lastIndex]?.focus()
  }
}

const cmContents = ref(['']), cmRefs = ref<HTMLTextAreaElement[]>([])
const addCM = createFieldManager(cmContents, cmRefs)

const tdContents = ref(['']), tdRefs = ref<HTMLTextAreaElement[]>([])
const addTD = createFieldManager(tdContents, tdRefs)

const tpContents = ref(['']), tpRefs = ref<HTMLTextAreaElement[]>([])
const addTP = createFieldManager(tpContents, tpRefs)

const dsContents = ref(['']), dsRefs = ref<HTMLTextAreaElement[]>([])
const addDS = createFieldManager(dsContents, dsRefs)

const dstpContents = ref(['']), dstpRefs = ref<HTMLTextAreaElement[]>([])
const addDSTP = createFieldManager(dstpContents, dstpRefs)

const edFBContents = ref(['']), edFBRefs = ref<HTMLTextAreaElement[]>([])
const addEducationalFeedback = createFieldManager(edFBContents, edFBRefs)

const stFBContents = ref(['']), stFBRefs = ref<HTMLTextAreaElement[]>([])
const addStudentFeedback = createFieldManager(stFBContents, stFBRefs)

const upgradesContents = ref(['']), upgradesRefs = ref<HTMLTextAreaElement[]>([])
const addUpgrades = createFieldManager(upgradesContents, upgradesRefs)

const handleValider = async () => {
  const buildContent = () => {
    return JSON.stringify({
      CM: cmContents.value.filter(t => t.trim()),
      TD: tdContents.value.filter(t => t.trim()),
      TP: tpContents.value.filter(t => t.trim()),
      DS: dsContents.value.filter(t => t.trim()),
      DSTP: dstpContents.value.filter(t => t.trim())
    });
  };

  const payload = {
    semester: 1,
    year: new Date().getFullYear(),
    mainGoal: "Objectif par défaut",
    content: buildContent(),
    hoursCM: hours.value.cm,
    hoursTD: hours.value.td,
    hoursTP: hours.value.tp,
    hoursDS: hours.value.ds,
    hoursDSTP: hours.value.ds_tp,
    teacherFeedbackContent: edFBContents.value.join('\n'),
    studentFeedbackContent: stFBContents.value.join('\n'),
    improvementIdeaContent: upgradesContents.value.join('\n'),

    resourceID: currentResourceId.value,
    referencialTeacherID: null,
    linkedSaeID: null
  };

  try {
    await api.post('/resourceSheet/resourceSheet', [payload]);
    alert("Fiche ressource enregistrée avec succès !");
    router.push('/home');
  } catch (error) {
    console.error("Erreur sauvegarde :", error);
    alert("Erreur lors de l'enregistrement de la fiche.");
  }
};
</script>

<template>
  <Sidebar/>

  <AppHeader :title="`Fiche de la Ressource ${resourceCode}`" />

  <main class="main-content">
    <div class="container">

      <div class="required-legend">
        <span>* champs obligatoires</span>
      </div>

      <section class="form-card">
        <h2 class="section-title">Voici le nombre d'heures de CM, TD et TP : *</h2>

        <div class="hours-grid-wrapper">
          <div class="hours-row">
            <div class="hour-block" v-for="key in (['cm', 'td', 'ds'] as const)" :key="key">
              <label :style="{ color: hourConfig[key].color }">{{ hourConfig[key].label }}</label>
              <input
                  type="number"
                  v-model.number="hours[key]"
                  class="box-input"
                  min="0"
                  @keypress="blockNegative"
                  @input="validatePositive(key)"
              >
            </div>
          </div>

          <div class="hours-row mt-25">
            <div class="hour-block" v-for="key in (['tp', 'ds_tp'] as const)" :key="key">
              <label :style="{ color: hourConfig[key].color }">{{ hourConfig[key].label }}</label>
              <input
                  type="number"
                  v-model.number="hours[key]"
                  class="box-input"
                  min="0"
                  @keypress="blockNegative"
                  @input="validatePositive(key)"
              >
            </div>
            <div class="hour-block">
              <label style="color: #64748b;">Total Global</label>
              <div class="box-static total-highlight">{{ totalGlobal }} h</div>
            </div>
          </div>
        </div>

        <div class="student-hour-row">
          <label>Nombre d'heures par étudiant : *</label>
          <input
              type="number"
              v-model.number="hours.student"
              class="wide-input"
              min="0"
              @keypress="blockNegative"
              @input="validatePositive('student')"
          >
        </div>
      </section>

      <section class="form-card">
        <h2 class="section-title">Voici le contenu pédagogique : *</h2>

        <div class="pedagogic-group">
          <h3 class="group-label">CM</h3>
          <textarea v-for="(c, i) in cmContents" :key="'cm-'+i" v-model="cmContents[i]" class="modern-textarea" :ref="el => { if (el) cmRefs[i] = el as HTMLTextAreaElement }" placeholder="Saisissez ici..."></textarea>
          <button class="btn-add-line" @click="addCM">+ Ajouter un bloc CM</button>
        </div>

        <div class="pedagogic-group">
          <h3 class="group-label">TD</h3>
          <textarea v-for="(c, i) in tdContents" :key="'td-'+i" v-model="tdContents[i]" class="modern-textarea" :ref="el => { if (el) tdRefs[i] = el as HTMLTextAreaElement }" placeholder="Saisissez ici..."></textarea>
          <button class="btn-add-line" @click="addTD">+ Ajouter un bloc TD</button>
        </div>

        <div class="pedagogic-group">
          <h3 class="group-label">TP</h3>
          <textarea v-for="(c, i) in tpContents" :key="'tp-'+i" v-model="tpContents[i]" class="modern-textarea" :ref="el => { if (el) tpRefs[i] = el as HTMLTextAreaElement }" placeholder="Saisissez ici..."></textarea>
          <button class="btn-add-line" @click="addTP">+ Ajouter un bloc TP</button>
        </div>

        <div class="pedagogic-group">
          <h3 class="group-label">DS</h3>
          <textarea v-for="(c, i) in dsContents" :key="'ds-'+i" v-model="dsContents[i]" class="modern-textarea" :ref="el => { if (el) dsRefs[i] = el as HTMLTextAreaElement }" placeholder="Saisissez ici..."></textarea>
          <button class="btn-add-line" @click="addDS">+ Ajouter un bloc DS</button>
        </div>

        <div class="pedagogic-group">
          <h3 class="group-label">DS/TP</h3>
          <textarea v-for="(c, i) in dstpContents" :key="'dstp-'+i" v-model="dstpContents[i]" class="modern-textarea" :ref="el => { if (el) dstpRefs[i] = el as HTMLTextAreaElement }" placeholder="Saisissez ici..."></textarea>
          <button class="btn-add-line" @click="addDSTP">+ Ajouter un bloc DS/TP</button>
        </div>
      </section>

      <section class="form-card accent">
        <h2 class="section-title">Bilans et Évolutions</h2>

        <div class="pedagogic-group">
          <label class="field-label">Voici le retour pédagogique des professeurs :</label>
          <textarea v-for="(c, i) in edFBContents" :key="'ed-'+i" v-model="edFBContents[i]" class="modern-textarea large" :ref="el => { if (el) edFBRefs[i] = el as HTMLTextAreaElement }"></textarea>
          <button class="btn-add-line" @click="addEducationalFeedback">+ Ajouter une ligne</button>
        </div>

        <div class="pedagogic-group">
          <label class="field-label">Voici le retour des étudiants :</label>
          <textarea v-for="(c, i) in stFBContents" :key="'st-'+i" v-model="stFBContents[i]" class="modern-textarea large" :ref="el => { if (el) stFBRefs[i] = el as HTMLTextAreaElement }"></textarea>
          <button class="btn-add-line" @click="addStudentFeedback">+ Ajouter une ligne</button>
        </div>

        <div class="pedagogic-group">
          <label class="field-label">Voici les améliorations à apporter :</label>
          <textarea v-for="(c, i) in upgradesContents" :key="'up-'+i" v-model="upgradesContents[i]" class="modern-textarea large" :ref="el => { if (el) upgradesRefs[i] = el as HTMLTextAreaElement }"></textarea>
          <button class="btn-add-line" @click="addUpgrades">+ Ajouter une ligne</button>
        </div>
      </section>

      <div class="actions-footer">
        <button @click="handleValider" class="btn btn-primary">Valider la saisie</button>
        <button @click="handleExport" class="btn btn-dark">Terminer et exporter en PDF</button>
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

.box-input, .wide-input {
  width: 100%;
  background: #f8fafc;
  border: 1px solid #e2e8f0;
  border-radius: 8px;
  height: 45px;
  font-size: 1.2rem;
  font-weight: 800;
  text-align: center;
  box-sizing: border-box;
}

.box-input:focus, .wide-input:focus {
  outline: none;
  border-color: #E92533;
  background: white;
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

.student-hour-row {
  display: flex;
  align-items: center;
  gap: 20px;
  border-top: 1px dashed #e2e8f0;
  padding-top: 25px;
}

.student-hour-row label {
  flex: 0 0 auto;
  white-space: nowrap;
  font-weight: 500;
  font-size: 1.1rem;
}

.pedagogic-group {
  margin-bottom: 35px;
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
}

.group-label, .field-label {
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
  flex-wrap: wrap;
}
.btn {
  padding: 15px 45px;
  border-radius: 10px;
  font-weight: 700;
  cursor: pointer;
  border: none;
  transition: transform 0.2s;
}
.btn:hover {
  transform: translateY(-2px);
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
.btn-dark {
  background: #333333;
  color: white;
  border: 2px solid #333333;
}
.btn-dark:hover {
  background: #555555;
  border-color: #555555;
}
</style>