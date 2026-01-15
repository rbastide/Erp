<script setup lang="ts">
import {computed, onMounted, ref} from 'vue'
import {useRoute, useRouter} from 'vue-router'
import AppHeader from '../App/Header.vue'
import Sidebar from '../App/Sidebar.vue'
import api from '@/services/api'

let isAdmin = false;
const role = localStorage.getItem('user_role');
console.log(role);
isAdmin = role == 'SUPER_ADMIN';

const router = useRouter()
const route = useRoute()

const resourceCode = ref('')
const resourceDate = ref('')

const hours = ref({
  cm: 0, td: 0, tp: 0, ds: 0, ds_tp: 0, student: 0
})

const contents = ref({
  cm: [] as string[],
  td: [] as string[],
  tp: [] as string[],
  ds: [] as string[],
  ds_tp: [] as string[],
  teacherFeedback: [] as string[],
  studentFeedback: [] as string[],
  upgrades: [] as string[]
})

const fetchSheetData = async () => {
  const sheetIdFromUrl = route.query.id
  resourceCode.value = (route.query.code as string) || 'Inconnu'
  resourceDate.value = (route.query.date as string) || ''

  if (!sheetIdFromUrl) return

  try {
    const responseSheets = await api.get('/resourceSheet/getResourceSheet')
    const sheet = responseSheets.data.find((s: any) => s.sheetsID == sheetIdFromUrl)

    if (sheet) {
      contents.value.teacherFeedback = sheet.teachersFeedbacks?.map((f: any) => f.content) || []
      contents.value.studentFeedback = sheet.studentsFeedbacks?.map((f: any) => f.content) || []
      contents.value.upgrades = sheet.improvementIdeas?.map((i: any) => i.idea || i.content) || []

      if (sheet.pedagologicalContentId) {
        const pedago = sheet.pedagologicalContentId
        const getBlocks = (type: string) => pedago
            .filter((p: any) => p.classTypeId?.classType === type)
            .map((p: any) => p.content)

        contents.value.cm = getBlocks('CM')
        contents.value.td = getBlocks('TD')
        contents.value.tp = getBlocks('TP')
        contents.value.ds = getBlocks('DS')
        contents.value.ds_tp = getBlocks('DS/TP')
      }

      if (sheet.hourlyVolumeID) {
        const responseMccc = await api.get('/mccc/getMccc')
        const mcccMatch = responseMccc.data.find((m: any) => m.hourlyVolId?.hourlyVolID == sheet.hourlyVolumeID)

        if (mcccMatch && mcccMatch.hourlyVolId) {
          const v = mcccMatch.hourlyVolId
          hours.value = {
            cm: v.nbHoursCM || 0,
            td: v.nbHoursTD || 0,
            tp: v.nbHoursTP || 0,
            ds: v.nbHoursDS || 0,
            ds_tp: v.nbHoursDSTP || 0,
            student: (v.nbHoursCM || 0) + (v.nbHoursTD || 0) + (v.nbHoursTP || 0) + (v.nbHoursDS || 0) + (v.nbHoursDSTP || 0)
          }
        }
      }
    }
  } catch (error) {
    console.error("Erreur technique :", error)
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

    const fileName = `fiche-ressource-${resourceCode.value}.pdf`;
    link.setAttribute('download', fileName);

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
  <AppHeader title="Consultation Ressource" :inline="`${resourceCode} du ${resourceDate}`" />

  <main class="main-content">
    <div class="container">
      <section class="form-card">
        <h2 class="section-title">Volume horaire global</h2>
        <div class="hours-grid-wrapper">
          <div class="hours-row">
            <div class="hour-block" v-for="key in (['cm', 'td', 'ds'] as const)" :key="key">
              <label :style="{ color: hourConfig[key].color }">{{ hourConfig[key].label }}</label>
              <div class="box-static">{{ hours[key] }} h</div>
            </div>
          </div>
          <div class="hours-row mt-25">
            <div class="hour-block" v-for="key in (['tp', 'ds_tp'] as const)" :key="key">
              <label :style="{ color: hourConfig[key].color }">{{ hourConfig[key].label }}</label>
              <div class="box-static">{{ hours[key] }} h</div>
            </div>
            <div class="hour-block">
              <label style="color: #64748b;">Total Global</label>
              <div class="box-static total-highlight">{{ totalGlobal }} h</div>
            </div>
          </div>
        </div>
      </section>

      <section class="form-card">
        <h2 class="section-title">Contenu pédagogique</h2>
        <div v-for="type in (['cm', 'td', 'tp', 'ds', 'ds_tp'] as const)" :key="type" class="pedagogic-group">
          <h3 class="group-label">{{ hourConfig[type].label }}</h3>
          <template v-if="contents[type].length > 0">
            <div v-for="(block, idx) in contents[type]" :key="idx" class="content-item">
              <label class="item-index-label">{{ hourConfig[type].label }} {{ idx + 1 }}</label>
              <div class="read-only-box">{{ block }}</div>
            </div>
          </template>
          <div v-else class="read-only-box empty">Aucun contenu répertorié</div>
        </div>
      </section>

      <section class="form-card accent">
        <h2 class="section-title">Bilans et Évolutions</h2>
        <div class="pedagogic-group">
          <label class="field-label">Retour pédagogique des professeurs :</label>
          <div v-for="(fb, i) in contents.teacherFeedback" :key="i" class="read-only-box mb-10">{{ fb }}</div>
        </div>
        <div class="pedagogic-group">
          <label class="field-label">Retour des étudiants :</label>
          <div v-for="(fb, i) in contents.studentFeedback" :key="i" class="read-only-box mb-10">{{ fb }}</div>
        </div>
        <div class="pedagogic-group">
          <label class="field-label">Améliorations à apporter :</label>
          <div v-for="(up, i) in contents.upgrades" :key="i" class="read-only-box mb-10">{{ up }}</div>
        </div>
      </section>

      <div class="actions-footer">
        <button @click="handleExport" class="btn btn-dark">Exporter en PDF</button>
        <button @click="handleFermer" class="btn btn-primary">Fermer et Valider</button>
        <button v-if="isAdmin" @click="handleModifier" class="btn btn-primary">Modifier</button>
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
}

.hour-block label {
  display: block;
  font-size: 0.9rem;
  font-weight: 800;
  margin-bottom: 8px;
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
  width: 100%;
}

.group-label,
.field-label {
  font-weight: 600;
  margin-bottom: 12px;
}

.content-item {
  margin-bottom: 15px;
  width: 100%;
}

.item-index-label {
  font-size: 0.8rem;
  font-weight: 700;
  color: #94a3b8;
  text-transform: uppercase;
  margin-bottom: 4px;
  display: block;
}

.read-only-box {
  padding: 15px;
  border: 1px solid #e2e8f0;
  border-radius: 12px;
  background: #fdfdfd;
  color: #546e7a;
  white-space: pre-wrap;
  min-height: 80px;
}

.read-only-box.empty {
  color: #cbd5e1;
  font-style: italic;
}

.mb-10 {
  margin-bottom: 10px;
}

.large {
  min-height: 110px;
}

.actions-footer {
  display: flex;
  justify-content: center;
  gap: 20px;
  margin-top: 20px;
}

.btn {
  padding: 15px 45px;
  border-radius: 10px;
  font-weight: 700;
  cursor: pointer;
  border: none;
}

.btn-primary {
  background: #E92533;
  color: white;
}

.btn-dark {
  background: #333333;
  color: white;
}
</style>