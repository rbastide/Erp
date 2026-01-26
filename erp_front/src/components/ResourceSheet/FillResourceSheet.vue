<script setup lang="ts">
import { ref, nextTick, computed, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
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

const cmRefs = ref<HTMLInputElement[]>([])
const tdRefs = ref<HTMLInputElement[]>([])
const tpRefs = ref<HTMLInputElement[]>([])
const dsRefs = ref<HTMLInputElement[]>([])
const dstpRefs = ref<HTMLInputElement[]>([])

const edFBRef = ref<HTMLElement | null>(null)
const stFBRef = ref<HTMLElement | null>(null)
const upgradesRef = ref<HTMLElement | null>(null)

const activeSection = ref<string>('');

const activeFormats = ref({
  bold: false,
  italic: false,
  underline: false,
  unordered: false,
  ordered: false
});

const canAdd = (list: string[]) => {
  if (!list || list.length === 0) return true;
  const lastItem = list[list.length - 1];
  const stripped = (lastItem || '').replace(/<[^>]*>?/gm, '').trim();
  return stripped.length > 0;
}

const removeLine = (list: string[], index: number) => {
  list.splice(index, 1);
};

const checkFormats = () => {
  activeFormats.value = {
    bold: document.queryCommandState('bold'),
    italic: document.queryCommandState('italic'),
    underline: document.queryCommandState('underline'),
    unordered: document.queryCommandState('insertUnorderedList'),
    ordered: document.queryCommandState('insertOrderedList')
  };
};

const handleFocus = (sectionName: string) => {
  activeSection.value = sectionName;
  checkFormats();
};

const handleBlur = () => {
  activeSection.value = '';
};

const execCmd = (command: string) => {
  document.execCommand(command, false, undefined);
  checkFormats();
};

const updateRichContent = (event: Event, list: string[]) => {
  const target = event.target as HTMLElement;
  list[0] = target.innerHTML;
  checkFormats();
};

const populateRichEditors = async () => {
  await nextTick();
  if (edFBRef.value) edFBRef.value.innerHTML = edFBContents.value[0] || '';
  if (stFBRef.value) stFBRef.value.innerHTML = stFBContents.value[0] || '';
  if (upgradesRef.value) upgradesRef.value.innerHTML = upgradesContents.value[0] || '';
};

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
          if (!resourceData.educationalContentId) return [];
          return resourceData.educationalContentId
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

        const dstpRaw = resourceData.educationalContentId?.filter((item: any) =>
            ['DSTP', 'DS TP', 'DS/TP'].includes(item.classTypeId?.classType)
        ) || [];
        const dstp = dstpRaw.map((item: any) => item.content || '');
        dstpContents.value = dstp.length > 0 ? dstp : [''];

        if (resourceData.teachersFeedbacks && resourceData.teachersFeedbacks.length > 0) {
          const content = resourceData.teachersFeedbacks.map((f: any) => f.content).join('<br>');
          edFBContents.value = [content];
        }
        if (resourceData.studentsFeedbacks && resourceData.studentsFeedbacks.length > 0) {
          const content = resourceData.studentsFeedbacks.map((f: any) => f.content).join('<br>');
          stFBContents.value = [content];
        }
        if (resourceData.improvementIdeas && resourceData.improvementIdeas.length > 0) {
          const content = resourceData.improvementIdeas.map((i: any) => i.ideaContent || i.content).join('<br>');
          upgradesContents.value = [content];
        }

        await populateRichEditors();
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

const handleValider = async () => {
  const formatForBackend = (type: string, list: string[]) => {
    return list
        .map((content, index) => (content || '').trim() ? `${type} ${index + 1} : ${(content || '').trim()}` : null)
        .filter((item): item is string => item !== null);
  };

  const allEducationalContent = [
    ...formatForBackend('CM', cmContents.value),
    ...formatForBackend('TD', tdContents.value),
    ...formatForBackend('TP', tpContents.value),
    ...formatForBackend('DS', dsContents.value),
    ...formatForBackend('DS/TP', dstpContents.value)
  ];

  const filterRich = (list: string[]) => list.filter(t => t && t.replace(/<[^>]*>?/gm, '').trim().length > 0);

  const payload = {
    resourceID: currentResourceId.value,
    hourlyVolume: hours.value,
    teachersFeedbackID: filterRich(edFBContents.value),
    studentFeedbackID: filterRich(stFBContents.value),
    improvementsIdeaID: filterRich(upgradesContents.value),
    educationalContent: allEducationalContent,
    mainGoal: "Objectif pédagogique principal"
  };
  console.log(payload);
  console.log(hours.value);

  try {
    await api.post('/resourceSheet/resource-sheet', payload);
    showSuccessModal.value = true;
  } catch (error: any) {
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
            <div class="input-wrapper">
              <input
                  type="text"
                  v-model="section.list[index]"
                  :ref="(el) => { if(el) (section.refs.value as any)[index] = el }"
                  class="simple-line-input"
                  placeholder="Saisissez le titre ou le contenu..."
              />
              <button
                  v-if="index > 0"
                  class="delete-btn"
                  @click="removeLine(section.list, index)"
                  title="Supprimer la ligne"
              >✕</button>
            </div>
          </div>
          <button
              class="btn-add-line"
              @click="section.addFn()"
              :disabled="!canAdd(section.list)"
              :class="{ 'disabled-btn': !canAdd(section.list) }"
          >
            + Ajouter une ligne {{ section.type }}
          </button>
        </div>
      </section>

      <section class="form-card accent">
        <h2 class="section-title">Bilans et Évolutions</h2>

        <div class="pedagogic-group">
          <label class="field-label">Voici le retour pédagogique des professeurs :</label>
          <div class="rich-editor-wrapper">
            <div class="rich-toolbar" :class="{ 'disabled-bar': activeSection !== 'teachers' }">
              <button @mousedown.prevent="execCmd('bold')" :class="{ 'is-active': activeFormats.bold && activeSection === 'teachers' }">
                <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5" stroke-linecap="round" stroke-linejoin="round"><path d="M6 4h8a4 4 0 0 1 4 4 4 4 0 0 1-4 4H6z"></path><path d="M6 12h9a4 4 0 0 1 4 4 4 4 0 0 1-4 4H6z"></path></svg>
              </button>
              <button @mousedown.prevent="execCmd('italic')" :class="{ 'is-active': activeFormats.italic && activeSection === 'teachers' }">
                <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5" stroke-linecap="round" stroke-linejoin="round"><line x1="19" y1="4" x2="10" y2="4"></line><line x1="14" y1="20" x2="5" y2="20"></line><line x1="15" y1="4" x2="9" y2="20"></line></svg>
              </button>
              <button @mousedown.prevent="execCmd('underline')" :class="{ 'is-active': activeFormats.underline && activeSection === 'teachers' }">
                <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5" stroke-linecap="round" stroke-linejoin="round"><path d="M6 3v7a6 6 0 0 0 6 6 6 6 0 0 0 6-6V3"></path><line x1="4" y1="21" x2="20" y2="21"></line></svg>
              </button>
              <span class="sep"></span>
              <button @mousedown.prevent="execCmd('insertUnorderedList')" :class="{ 'is-active': activeFormats.unordered && activeSection === 'teachers' }">
                <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5" stroke-linecap="round" stroke-linejoin="round"><line x1="8" y1="6" x2="21" y2="6"></line><line x1="8" y1="12" x2="21" y2="12"></line><line x1="8" y1="18" x2="21" y2="18"></line><line x1="3" y1="6" x2="3.01" y2="6"></line><line x1="3" y1="12" x2="3.01" y2="12"></line><line x1="3" y1="18" x2="3.01" y2="18"></line></svg>
              </button>
              <button @mousedown.prevent="execCmd('insertOrderedList')" :class="{ 'is-active': activeFormats.ordered && activeSection === 'teachers' }">
                <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5" stroke-linecap="round" stroke-linejoin="round"><line x1="10" y1="6" x2="21" y2="6"></line><line x1="10" y1="12" x2="21" y2="12"></line><line x1="10" y1="18" x2="21" y2="18"></line><path d="M4 6h1v4"></path><path d="M4 10h2"></path><path d="M6 18H4c0-1 2-2 2-3s-1-1.5-2-1"></path></svg>
              </button>
            </div>
            <div
                class="rich-editor-content"
                contenteditable="true"
                @input="(e) => updateRichContent(e, edFBContents)"
                @keyup="checkFormats"
                @mouseup="checkFormats"
                @focus="handleFocus('teachers')"
                @blur="handleBlur"
                ref="edFBRef"
            ></div>
          </div>
        </div>

        <div class="pedagogic-group">
          <label class="field-label">Voici le retour des étudiants :</label>
          <div class="rich-editor-wrapper">
            <div class="rich-toolbar" :class="{ 'disabled-bar': activeSection !== 'students' }">
              <button @mousedown.prevent="execCmd('bold')" :class="{ 'is-active': activeFormats.bold && activeSection === 'students' }">
                <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5" stroke-linecap="round" stroke-linejoin="round"><path d="M6 4h8a4 4 0 0 1 4 4 4 4 0 0 1-4 4H6z"></path><path d="M6 12h9a4 4 0 0 1 4 4 4 4 0 0 1-4 4H6z"></path></svg>
              </button>
              <button @mousedown.prevent="execCmd('italic')" :class="{ 'is-active': activeFormats.italic && activeSection === 'students' }">
                <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5" stroke-linecap="round" stroke-linejoin="round"><line x1="19" y1="4" x2="10" y2="4"></line><line x1="14" y1="20" x2="5" y2="20"></line><line x1="15" y1="4" x2="9" y2="20"></line></svg>
              </button>
              <button @mousedown.prevent="execCmd('underline')" :class="{ 'is-active': activeFormats.underline && activeSection === 'students' }">
                <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5" stroke-linecap="round" stroke-linejoin="round"><path d="M6 3v7a6 6 0 0 0 6 6 6 6 0 0 0 6-6V3"></path><line x1="4" y1="21" x2="20" y2="21"></line></svg>
              </button>
              <span class="sep"></span>
              <button @mousedown.prevent="execCmd('insertUnorderedList')" :class="{ 'is-active': activeFormats.unordered && activeSection === 'students' }">
                <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5" stroke-linecap="round" stroke-linejoin="round"><line x1="8" y1="6" x2="21" y2="6"></line><line x1="8" y1="12" x2="21" y2="12"></line><line x1="8" y1="18" x2="21" y2="18"></line><line x1="3" y1="6" x2="3.01" y2="6"></line><line x1="3" y1="12" x2="3.01" y2="12"></line><line x1="3" y1="18" x2="3.01" y2="18"></line></svg>
              </button>
              <button @mousedown.prevent="execCmd('insertOrderedList')" :class="{ 'is-active': activeFormats.ordered && activeSection === 'students' }">
                <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5" stroke-linecap="round" stroke-linejoin="round"><line x1="10" y1="6" x2="21" y2="6"></line><line x1="10" y1="12" x2="21" y2="12"></line><line x1="10" y1="18" x2="21" y2="18"></line><path d="M4 6h1v4"></path><path d="M4 10h2"></path><path d="M6 18H4c0-1 2-2 2-3s-1-1.5-2-1"></path></svg>
              </button>
            </div>
            <div
                class="rich-editor-content"
                contenteditable="true"
                @input="(e) => updateRichContent(e, stFBContents)"
                @keyup="checkFormats"
                @mouseup="checkFormats"
                @focus="handleFocus('students')"
                @blur="handleBlur"
                ref="stFBRef"
            ></div>
          </div>
        </div>

        <div class="pedagogic-group">
          <label class="field-label">Voici les améliorations à apporter :</label>
          <div class="rich-editor-wrapper">
            <div class="rich-toolbar" :class="{ 'disabled-bar': activeSection !== 'upgrades' }">
              <button @mousedown.prevent="execCmd('bold')" :class="{ 'is-active': activeFormats.bold && activeSection === 'upgrades' }">
                <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5" stroke-linecap="round" stroke-linejoin="round"><path d="M6 4h8a4 4 0 0 1 4 4 4 4 0 0 1-4 4H6z"></path><path d="M6 12h9a4 4 0 0 1 4 4 4 4 0 0 1-4 4H6z"></path></svg>
              </button>
              <button @mousedown.prevent="execCmd('italic')" :class="{ 'is-active': activeFormats.italic && activeSection === 'upgrades' }">
                <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5" stroke-linecap="round" stroke-linejoin="round"><line x1="19" y1="4" x2="10" y2="4"></line><line x1="14" y1="20" x2="5" y2="20"></line><line x1="15" y1="4" x2="9" y2="20"></line></svg>
              </button>
              <button @mousedown.prevent="execCmd('underline')" :class="{ 'is-active': activeFormats.underline && activeSection === 'upgrades' }">
                <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5" stroke-linecap="round" stroke-linejoin="round"><path d="M6 3v7a6 6 0 0 0 6 6 6 6 0 0 0 6-6V3"></path><line x1="4" y1="21" x2="20" y2="21"></line></svg>
              </button>
              <span class="sep"></span>
              <button @mousedown.prevent="execCmd('insertUnorderedList')" :class="{ 'is-active': activeFormats.unordered && activeSection === 'upgrades' }">
                <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5" stroke-linecap="round" stroke-linejoin="round"><line x1="8" y1="6" x2="21" y2="6"></line><line x1="8" y1="12" x2="21" y2="12"></line><line x1="8" y1="18" x2="21" y2="18"></line><line x1="3" y1="6" x2="3.01" y2="6"></line><line x1="3" y1="12" x2="3.01" y2="12"></line><line x1="3" y1="18" x2="3.01" y2="18"></line></svg>
              </button>
              <button @mousedown.prevent="execCmd('insertOrderedList')" :class="{ 'is-active': activeFormats.ordered && activeSection === 'upgrades' }">
                <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5" stroke-linecap="round" stroke-linejoin="round"><line x1="10" y1="6" x2="21" y2="6"></line><line x1="10" y1="12" x2="21" y2="12"></line><line x1="10" y1="18" x2="21" y2="18"></line><path d="M4 6h1v4"></path><path d="M4 10h2"></path><path d="M6 18H4c0-1 2-2 2-3s-1-1.5-2-1"></path></svg>
              </button>
            </div>
            <div
                class="rich-editor-content"
                contenteditable="true"
                @input="(e) => updateRichContent(e, upgradesContents)"
                @keyup="checkFormats"
                @mouseup="checkFormats"
                @focus="handleFocus('upgrades')"
                @blur="handleBlur"
                ref="upgradesRef"
            ></div>
          </div>
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

.input-wrapper {
  display: flex;
  align-items: center;
  gap: 10px;
  width: 100%;
}

.simple-line-input {
  flex: 1;
  box-sizing: border-box;
  padding: 10px 15px;
  border: 1px solid #e2e8f0;
  border-radius: 8px;
  font-size: 1rem;
  transition: all 0.2s;
  background: #fbfbfb;
}
.simple-line-input:focus {
  border-color: #E92533;
  background: white;
  outline: none;
  box-shadow: 0 0 0 3px rgba(233, 37, 51, 0.1);
}

.delete-btn {
  background: transparent;
  border: none;
  color: #ef5350;
  font-weight: bold;
  font-size: 1.2rem;
  cursor: pointer;
  padding: 5px;
  transition: transform 0.2s;
  display: flex;
  align-items: center;
  justify-content: center;
}
.delete-btn:hover {
  color: #d32f2f;
  transform: scale(1.1);
}

.rich-editor-wrapper {
  width: 100%;
  border: 1px solid #e2e8f0;
  border-radius: 12px;
  overflow: hidden;
  margin-bottom: 20px;
  background: white;
}
.rich-toolbar {
  background: #f8fafc;
  border-bottom: 1px solid #e2e8f0;
  padding: 8px;
  display: flex;
  gap: 5px;
  flex-wrap: wrap;
  align-items: center;
  transition: opacity 0.2s ease-in-out;
  opacity: 1;
}

.rich-toolbar.disabled-bar {
  opacity: 0.4;
  pointer-events: none;
}

.rich-toolbar button {
  background: white;
  border: 1px solid #ddd;
  border-radius: 4px;
  cursor: pointer;
  padding: 5px;
  height: 32px;
  width: 32px;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: all 0.2s;
  color: #555;
}
.rich-toolbar button:hover {
  background: #e9e9e9;
  color: #B51621;
  border-color: #B51621;
}
.rich-toolbar button.is-active {
  background-color: #e3f2fd;
  color: #B51621;
  border-color: #B51621;
}

.sep {
  width: 1px;
  height: 24px;
  background: #ddd;
  margin: 0 5px;
}

.rich-editor-content {
  min-height: 120px;
  padding: 15px;
  outline: none;
  font-size: 1rem;
  line-height: 1.5;
}
.rich-editor-content:focus {
  background-color: #fffdfd;
}

:deep(.rich-editor-content b), :deep(.rich-editor-content strong) { font-weight: bold !important; }
:deep(.rich-editor-content i), :deep(.rich-editor-content em) { font-style: italic !important; }
:deep(.rich-editor-content u) { text-decoration: underline !important; }
:deep(.rich-editor-content ul) { list-style: disc inside !important; margin-left: 10px; }
:deep(.rich-editor-content ol) { list-style: decimal inside !important; margin-left: 10px; }

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
  pointer-events: none;
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