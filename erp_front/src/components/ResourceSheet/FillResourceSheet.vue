<script setup lang="ts">
import {computed, nextTick, onMounted, ref} from 'vue'
import {useRoute, useRouter} from 'vue-router'
import AppHeader from '../App/Header.vue';
import Sidebar from '../App/Sidebar.vue';
import api from '@/services/api';
import CancelModal from '../Information/CancelModal.vue';
import ModifSavedModal from '../Information/ModifSavedModal.vue';
import ErrorSaveModal from '../Information/ErrorSaveModal.vue';

const router = useRouter()
const route = useRoute()

const showModal = ref(false);
const showSuccessModal = ref(false);
const showErrorModal = ref(false);

const resourceCode = ref('');
const currentHourlyVolId = ref<number | null>(null);
const currentResourceId = ref<number | null>(null);

const hours = ref({ cm: 0, td: 0, ds: 0, tp: 0, ds_tp: 0, student: 0 })
const cmContents = ref([''])
const tdContents = ref([''])
const tpContents = ref([''])
const dsContents = ref([''])
const dstpContents = ref([''])
const edFBContents = ref([''])
const stFBContents = ref([''])
const upgradesContents = ref([''])
const cmRefs = ref<HTMLTextAreaElement[]>([])
const tdRefs = ref<HTMLTextAreaElement[]>([])
const tpRefs = ref<HTMLTextAreaElement[]>([])
const dsRefs = ref<HTMLTextAreaElement[]>([])
const dstpRefs = ref<HTMLTextAreaElement[]>([])
const edFBRefs = ref<HTMLTextAreaElement[]>([])
const stFBRefs = ref<HTMLTextAreaElement[]>([])
const upgradesRefs = ref<HTMLTextAreaElement[]>([])

const canAdd = (list: string[]) => {
  if (!list || list.length === 0) return true;
  const lastItem = list[list.length - 1];
  return (lastItem || '').trim().length > 0;
}

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
    const response = await api.get('/mccc/mcccs');
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

const fetchResourceSheetData = async () => {
  try {
    const response = await api.get('/resourceSheet/getResourceSheet');

    if (response.data && Array.isArray(response.data)) {

      const matchingSheets = response.data.filter((f: any) =>
          f.resourceID === currentResourceId.value
      );

      matchingSheets.sort((a: any, b: any) => {
        return new Date(b.creationDate).getTime() - new Date(a.creationDate).getTime();
      });

      const resourceData = matchingSheets.length > 0 ? matchingSheets.reverse()[0] : null;

      if (resourceData) {
        const getContentByType = (type: string) => {
          if (!resourceData.pedagologicalContentId) return [];
          return resourceData.pedagologicalContentId
              .filter((item: any) => item.classTypeId?.classType === type)
              .map((item: any) => item.content || '');
        };

        const cm = getContentByType('CM');
        cmContents.value = cm.length > 0 ? cm : [''];
        const td = getContentByType('TD');
        tdContents.value = td.length > 0 ? td : [''];
        const tp = getContentByType('TP');
        tpContents.value = tp.length > 0 ? tp : [''];
        const ds = getContentByType('DS');
        dsContents.value = ds.length > 0 ? ds : [''];

        const dstpRaw = resourceData.pedagologicalContentId?.filter((item: any) =>
            ['DSTP', 'DS TP', 'DS/TP'].includes(item.classTypeId?.classType)
        ) || [];
        const dstp = dstpRaw.map((item: any) => item.content || '');
        dstpContents.value = dstp.length > 0 ? dstp : [''];

        if (resourceData.teachersFeedbacks) {
          const tFeedbacks = resourceData.teachersFeedbacks.map((f: any) => f.content || '');
          edFBContents.value = tFeedbacks.length > 0 ? tFeedbacks : [''];
        }
        if (resourceData.studentsFeedbacks) {
          const sFeedbacks = resourceData.studentsFeedbacks.map((f: any) => f.content || '');
          stFBContents.value = sFeedbacks.length > 0 ? sFeedbacks : [''];
        }
        if (resourceData.improvementIdeas) {
          const ideas = resourceData.improvementIdeas.map((i: any) => i.ideaContent || i.content || '');
          upgradesContents.value = ideas.length > 0 ? ideas : [''];
        }
      }
    }
  } catch (error) {
    console.error("Erreur fiches ressources :", error);
  }
};

onMounted(async () => {
  await fetchResourceData();
  await fetchHoursData();
  await fetchResourceSheetData();
});

const createFieldManager = (contentRef: any, elementRefs: any) => {
  return async () => {
    if (!canAdd(contentRef.value)) return;
    contentRef.value.push('')
    await nextTick()
    const lastIndex = contentRef.value.length - 1
    if (elementRefs.value && elementRefs.value[lastIndex]) {
      elementRefs.value[lastIndex].focus()
    }
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
        .map((content, index) => (content || '').trim() ? `${type} ${index + 1} : ${(content || '').trim()}` : null)
        .filter((item): item is string => item !== null);
  };

  const allPedagogicalContent = [
    ...formatForBackend('CM', cmContents.value),
    ...formatForBackend('TD', tdContents.value),
    ...formatForBackend('TP', tpContents.value),
    ...formatForBackend('DS', dsContents.value),
    ...formatForBackend('DS/TP', dstpContents.value)
  ];

  const payload = {
    resourceID: currentResourceId.value,
    hourlyVolumeID: currentHourlyVolId.value,
    teachersFeedbackID: edFBContents.value.filter(t => (t || '').trim()),
    studentFeedbackID: stFBContents.value.filter(t => (t || '').trim()),
    improvementsIdeaID: upgradesContents.value.filter(t => (t || '').trim()),
    educationalContent: allPedagogicalContent,
    semester: 1,
    year: new Date().getFullYear(),
    mainGoal: "Objectif pédagogique principal"
  };

  try {
    await api.post('/resourceSheet/resource-sheet', payload);
    showSuccessModal.value = true;
  } catch (error: any) {
    const errorMsg = error.response?.data || "Erreur lors de l'enregistrement";
    console.error("Détails erreur :", error);
    showErrorModal.value = true;
  }
};

const handleRetour = () => {
  showModal.value = true;
};

const onConfirmCancel = () => {
  router.back();
};


const totalGlobal = computed(() => {
  return (hours.value.cm || 0) + (hours.value.td || 0) + (hours.value.ds || 0) + (hours.value.tp || 0) + (hours.value.ds_tp || 0);
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

const pedagogicalSections = computed(() => [
  { type: 'CM', list: cmContents.value, addFn: addCM, refs: cmRefs },
  { type: 'TD', list: tdContents.value, addFn: addTD, refs: tdRefs },
  { type: 'TP', list: tpContents.value, addFn: addTP, refs: tpRefs },
  { type: 'DS', list: dsContents.value, addFn: addDS, refs: dsRefs },
  { type: 'DS/TP', list: dstpContents.value, addFn: addDSTP, refs: dstpRefs },
]);
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
        <div v-for="section in pedagogicalSections" :key="section.type" class="pedagogic-group">
          <h3 class="group-label">{{ section.type }}</h3>
          <div v-for="(content, index) in section.list" :key="index" class="content-block">
            <label class="item-label">{{ section.type }} {{ index + 1 }}</label>
            <textarea
                v-model="section.list[index]"
                :ref="(el) => { if(el) (section.refs.value as any)[index] = el }"
                class="modern-textarea"
                placeholder="Saisissez le contenu...">
            </textarea>
          </div>
          <button
              class="btn-add-line"
              @click="section.addFn()"
              :disabled="!canAdd(section.list)"
              :class="{ 'disabled-btn': !canAdd(section.list) }"
          >
            + Ajouter un bloc {{ section.type }}
          </button>
        </div>
      </section>

      <section class="form-card accent">
        <h2 class="section-title">Bilans et Évolutions</h2>
        <div class="pedagogic-group">
          <label class="field-label">Voici le retour pédagogique des professeurs :</label>
          <div v-for="(content, index) in edFBContents" :key="index" class="content-block">
             <textarea
                 v-model="edFBContents[index]"
                 :ref="(el) => { if(el) edFBRefs[index] = el as HTMLTextAreaElement }"
                 class="modern-textarea large">
            </textarea>
          </div>
          <button class="btn-add-line" @click="addEducationalFeedback" :disabled="!canAdd(edFBContents)" :class="{ 'disabled-btn': !canAdd(edFBContents) }">
            + Ajouter une ligne
          </button>
        </div>
        <div class="pedagogic-group">
          <label class="field-label">Voici le retour des étudiants :</label>
          <div v-for="(content, index) in stFBContents" :key="index" class="content-block">
            <textarea
                v-model="stFBContents[index]"
                :ref="(el) => { if(el) stFBRefs[index] = el as HTMLTextAreaElement }"
                class="modern-textarea large">
            </textarea>
          </div>
          <button class="btn-add-line" @click="addStudentFeedback" :disabled="!canAdd(stFBContents)" :class="{ 'disabled-btn': !canAdd(stFBContents) }">
            + Ajouter une ligne
          </button>
        </div>

        <div class="pedagogic-group">
          <label class="field-label">Voici les améliorations à apporter :</label>
          <div v-for="(content, index) in upgradesContents" :key="index" class="content-block">
            <textarea
                v-model="upgradesContents[index]"
                :ref="(el) => { if(el) upgradesRefs[index] = el as HTMLTextAreaElement }"
                class="modern-textarea large">
            </textarea>
          </div>
          <button class="btn-add-line" @click="addUpgrades" :disabled="!canAdd(upgradesContents)" :class="{ 'disabled-btn': !canAdd(upgradesContents) }">
            + Ajouter une ligne
          </button>
        </div>
      </section>

      <div class="actions-footer">
        <button @click="handleValider" class="btn btn-primary">Valider la saisie</button>
        <button @click="handleRetour" class="btn btn-outline">Annuler</button>
      </div>
    </div>

    <CancelModal
        v-if="showModal"
        @confirm="onConfirmCancel"
        @close="showModal = false"
    />

    <ModifSavedModal
        v-if="showSuccessModal"
    />

    <ErrorSaveModal
        v-if="showErrorModal"
        @close="showErrorModal = false"
    />

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
  align-items: flex-start;
  width: 100%;
}
.group-label,
.field-label {
  font-weight: 600;
  margin-bottom: 15px;
  color: #1e293b;
  width: 100%;
}
.content-block {
  width: 100%;
  margin-bottom: 15px;
  display: flex;
  flex-direction: column;
}
.item-label {
  font-size: 0.85rem;
  font-weight: 700;
  color: #64748b;
  margin-bottom: 5px;
  text-transform: uppercase;
  margin-left: 2px;
}
.modern-textarea {
  width: 100%;
  box-sizing: border-box;
  padding: 15px;
  border: 1px solid #e2e8f0;
  border-radius: 12px;
  font-size: 1rem;
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
  align-self: center;
  margin-top: 5px;
  transition: all 0.2s;
}
.disabled-btn {
  color: #cbd5e1;
  cursor: not-allowed;
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