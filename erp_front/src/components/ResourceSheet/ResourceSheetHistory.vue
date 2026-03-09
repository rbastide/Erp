<script setup lang="ts">
import {computed, onMounted, ref} from 'vue'
import {useRoute, useRouter} from 'vue-router'
import AppHeader from '../App/Header.vue'
import Sidebar from '../App/Sidebar.vue'
import api from '@/services/api'

let isAdmin = false;
const role = localStorage.getItem('user_role');
isAdmin = role == 'Super-Admin';

const router = useRouter()
const route = useRoute()

const resourceCode = ref('')
const resourceDate = ref('')
const academicYear = ref<number>(2025)

const hours = ref({
  cm: 0, td: 0, tp: 0, ds: 0, ds_tp: 0, student: 0
})

const contents = ref({
  cm: [] as string[],
  td: [] as string[],
  tp: [] as string[],
  ds: [] as string[],
  ds_tp: [] as string[],
  teacherFeedback: "" as string,
  studentFeedback: "" as string,
  upgrades: "" as string
})

const formatHour = (decimal: number) => {
  const val = decimal || 0;
  const h = Math.floor(val / 60);
  const m = val % 60;
  return `${h}h${m.toString().padStart(2, '0')}`;
};

const fetchSheetData = async () => {
  const sheetIdFromUrl = route.query.id
  resourceCode.value = (route.query.code as string) || 'Inconnu'
  resourceDate.value = (route.query.date as string) || ''

  const now = new Date();
  const defaultYear = now.getMonth() < 8 ? now.getFullYear() - 1 : now.getFullYear();
  academicYear.value = route.query.year ? parseInt(route.query.year as string) : defaultYear;

  if (!sheetIdFromUrl) return

  try {
    const response = await api.get(`/resourceSheet/resource-sheet/${sheetIdFromUrl}`)
    const sheet = response.data

    contents.value.teacherFeedback = sheet.teacherFeedbacks || '';
    contents.value.studentFeedback = sheet.studentFeedbacks || '';
    contents.value.upgrades = sheet.improvementIdeas || '';

    // Si l'année est stockée dans la fiche reçue, on met à jour l'affichage
    if (sheet.academicYearStart) {
      academicYear.value = sheet.academicYearStart;
    }

    const pedago = sheet.educationalContentID || []
    const getBlocks = (type: string) => pedago
        .filter((p: any) => p.classType === type)
        .sort((a: any, b: any) => (a.courseNumber || 0) - (b.courseNumber || 0))
        .map((p: any) => p.content)

    contents.value.cm = getBlocks('CM')
    contents.value.td = getBlocks('TD')
    contents.value.tp = getBlocks('TP')
    contents.value.ds = getBlocks('DS')
    contents.value.ds_tp = getBlocks('DS/TP')

    let sheetHours = sheet.courseHours || {}
    hours.value = {
      cm: sheetHours.cm || 0,
      td: sheetHours.td || 0,
      tp: sheetHours.tp || 0,
      ds: sheetHours.ds || 0,
      ds_tp: sheetHours.ds_tp || 0,
      student: (sheetHours.nbHoursCM || 0) + (sheetHours.nbHoursTD || 0) + (sheetHours.nbHoursTP || 0) + (sheetHours.nbHoursDS || 0) + (sheetHours.nbHoursDSTP || 0)
    }
  } catch (error) {
    console.error("Erreur technique lors de la récupération de la fiche :", error)
  }
}

onMounted(() => {
  fetchSheetData()
})

const totalGlobal = computed(() => {
  return hours.value.cm + hours.value.td + hours.value.ds + hours.value.tp + hours.value.ds_tp
})

const handleFermer = () => router.back()

const handleExport = async () => {
  try {
    const response = await api.get(`/pdf/resource-sheet/${route.query.id}`, {
      responseType: 'blob'
    });
    const file = new Blob([response.data], {type: 'application/pdf'});
    const fileURL = URL.createObjectURL(file);
    const link = document.createElement('a');
    link.href = fileURL;
    link.setAttribute('download', `fiche-ressource-${resourceCode.value}-${academicYear.value}.pdf`);
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
    URL.revokeObjectURL(fileURL);
  } catch (error) {
    console.error("Erreur lors de la génération du PDF", error);
  }
}

const handleModifier = () => router.push("fill-resource-sheet?code=" + resourceCode.value);

const hourConfig = {
  cm: { label: 'CM', color: '#4DB6AC' },
  td: { label: 'TD', color: '#7986CB' },
  tp: { label: 'TP', color: '#4FC3F7' },
  ds: { label: 'DS', color: '#FFB74D' },
  ds_tp: { label: 'DS/TP', color: '#BA68C8' }
}
</script>

<template>
  <Sidebar />
  <AppHeader title="Consultation Ressource" :inline="`${resourceCode}`" />

  <main class="main-content">
    <div class="container">

      <section class="form-card year-display-card">
        <div class="year-info">
          <span class="label">Période :</span>
          <span class="year-badge">Année Universitaire {{ academicYear }} - {{ academicYear + 1 }}</span>
        </div>
      </section>

      <section class="form-card">
        <h2 class="section-title">Volume horaire global</h2>
        <div class="hours-grid-wrapper">
          <div class="hours-row">
            <div class="hour-block" v-for="key in (['cm', 'td', 'ds'] as const)" :key="key">
              <label :style="{ color: hourConfig[key].color }">{{ hourConfig[key].label }}</label>
              <div class="box-static">{{ formatHour(hours[key]) }}</div>
            </div>
          </div>
          <div class="hours-row mt-25">
            <div class="hour-block" v-for="key in (['tp', 'ds_tp'] as const)" :key="key">
              <label :style="{ color: hourConfig[key].color }">{{ hourConfig[key].label }}</label>
              <div class="box-static">{{ formatHour(hours[key]) }}</div>
            </div>
            <div class="hour-block">
              <label style="color: #64748b;">Total Global</label>
              <div class="box-static total-highlight">{{ formatHour(totalGlobal) }}</div>
            </div>
          </div>
        </div>
      </section>

      <section class="form-card">
        <h2 class="section-title">Contenu pédagogique</h2>
        <div v-for="type in (['cm', 'td', 'tp', 'ds', 'ds_tp'] as const)" :key="type" class="pedagogic-group">
          <h3 class="group-label">{{ hourConfig[type].label }}</h3>

          <template v-if="contents[type].length > 0">
            <div v-for="(block, idx) in contents[type]" :key="idx" class="content-block">
              <label class="item-label">{{ hourConfig[type].label }} {{ idx + 1 }}</label>
              <div class="input-wrapper">
                <div class="simple-line-input read-only-input">{{ block }}</div>
              </div>
            </div>
          </template>

          <div v-else class="content-block">
            <div class="input-wrapper">
              <div class="simple-line-input read-only-input empty">Aucun contenu répertorié</div>
            </div>
          </div>
        </div>
      </section>

      <section class="form-card accent">
        <h2 class="section-title">Bilans et Évolutions</h2>

        <div class="pedagogic-group">
          <label class="field-label">Retour pédagogique des professeurs :</label>
          <div class="rich-editor-wrapper">
            <div class="rich-editor-content" v-html="contents.teacherFeedback || '<i>Aucun retour.</i>'"></div>
          </div>
        </div>

        <div class="pedagogic-group">
          <label class="field-label">Retour des étudiants :</label>
          <div class="rich-editor-wrapper">
            <div class="rich-editor-content" v-html="contents.studentFeedback || '<i>Aucun retour.</i>'"></div>
          </div>
        </div>

        <div class="pedagogic-group">
          <label class="field-label">Améliorations à apporter :</label>
          <div class="rich-editor-wrapper">
            <div class="rich-editor-content" v-html="contents.upgrades || '<i>Aucune amélioration listée.</i>'"></div>
          </div>
        </div>
      </section>

      <div class="actions-footer">
        <button @click="handleExport" class="btn btn-dark">Exporter en PDF</button>
        <button @click="handleFermer" class="btn btn-outline">Retour</button>
        <button v-if="isAdmin" @click="handleModifier" class="btn btn-primary">Modifier la fiche</button>
      </div>
    </div>
  </main>
</template>

<style scoped>
.main-content {
  background-color: #f4f7f9;
  min-height: 100vh;
  padding: 240px 20px 60px;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.container {
  width: 100%;
  max-width: 900px;
}

/* Styles spécifique Année */
.year-display-card {
  padding: 20px 35px !important;
  margin-bottom: 20px;
}
.year-info {
  display: flex;
  align-items: center;
  gap: 15px;
}
.year-info .label {
  font-weight: 600;
  color: #64748b;
  font-size: 1rem;
}
.year-badge {
  background: #E92533;
  color: white;
  padding: 6px 16px;
  border-radius: 20px;
  font-weight: 800;
  font-size: 1rem;
  box-shadow: 0 2px 8px rgba(233, 37, 51, 0.2);
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
  border-left: 5px solid #E92533;
  padding-left: 15px;
  margin-bottom: 25px;
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
  display: flex;
  flex-direction: column;
  align-items: center;
}

.hour-block label {
  display: block;
  font-size: 0.9rem;
  font-weight: 800;
  margin-bottom: 8px;
  text-transform: uppercase;
}

.box-static {
  height: 45px;
  width: 100%;
  min-width: 140px;
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
}

.item-label {
  font-size: 0.85rem;
  font-weight: 700;
  color: #64748b;
  margin-bottom: 5px;
  text-transform: uppercase;
  margin-left: 2px;
}

.simple-line-input {
  flex: 1;
  box-sizing: border-box;
  padding: 10px 15px;
  border: 1px solid #e2e8f0;
  border-radius: 8px;
  font-size: 1rem;
  background: #fbfbfb;
  color: #333;
  min-height: 42px;
  display: flex;
  align-items: center;
}

.simple-line-input.empty {
  color: #94a3b8;
  font-style: italic;
}

.rich-editor-wrapper {
  width: 100%;
  border: 1px solid #e2e8f0;
  border-radius: 12px;
  overflow: hidden;
  background: #fbfbfb;
}

.rich-editor-content {
  min-height: 80px;
  padding: 20px;
  font-size: 1rem;
  line-height: 1.6;
  color: #334155;
}

:deep(.rich-editor-content b), :deep(.rich-editor-content strong) { font-weight: bold !important; }
:deep(.rich-editor-content i), :deep(.rich-editor-content em) { font-style: italic !important; }
:deep(.rich-editor-content u) { text-decoration: underline !important; }
:deep(.rich-editor-content ul) { list-style: disc inside !important; margin-left: 10px; }
:deep(.rich-editor-content ol) { list-style: decimal inside !important; margin-left: 10px; }

.actions-footer {
  display: flex;
  justify-content: center;
  gap: 20px;
  margin-top: 20px;
  margin-bottom: 50px;
}

.btn {
  padding: 15px 40px;
  border-radius: 10px;
  font-weight: 700;
  cursor: pointer;
  border: none;
  transition: all 0.2s;
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
  background: #334155;
  color: white;
}

.btn:hover {
  transform: translateY(-2px);
  filter: brightness(1.1);
}
</style>