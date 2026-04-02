<script setup lang="ts">
import {computed, onMounted, reactive, ref} from 'vue';
import {useRouter} from 'vue-router';
import AppHeader from '../App/Header.vue';
import Sidebar from '../App/Sidebar.vue';

const router = useRouter();
const showAddForm = ref(false);
const editingIndex = ref<number | null>(null);
const searchQuery = ref('');

const reminders = ref<any[]>([]);

const editedReminder = reactive({
  id: null as number | null,
  label: '',
  day: null as number | null,
  time: '',
  isOneTime: false,
  selectedMonths: [] as number[],
  singleMonth: 1
});

const monthsList = [
  {value: 1, name: 'Septembre'}, {value: 2, name: 'Octobre'}, {value: 3, name: 'Novembre'},
  {value: 4, name: 'Décembre'}, {value: 5, name: 'Janvier'}, {value: 6, name: 'Février'},
  {value: 7, name: 'Mars'}, {value: 8, name: 'Avril'}, {value: 9, name: 'Mai'},
  {value: 10, name: 'Juin'}, {value: 11, name: 'Juillet'}, {value: 12, name: 'Août'}
];

const getMonthName = (val: number) => monthsList.find(m => m.value === val)?.name || '';

const fetchReminders = async () => {
  try {
    const response = await fetch('/api/mail/config');
    if (response.ok) {
      const data = await response.json();
      reminders.value = Array.isArray(data) ? data : [];
    }
  } catch (error) {
    console.error("Erreur de connexion au backend :", error);
  }
};

onMounted(() => {
  fetchReminders();
});

const validateNumInput = () => {
  let val = editedReminder.day;
  if (val === null || val === undefined) return;
  if (val > 28) editedReminder.day = 28;
  else if (val < 1) editedReminder.day = 1;
  else editedReminder.day = Math.floor(val);
};

const blockInvalidChars = (e: any) => {
  if (['e', 'E', '+', '-'].includes(e.key)) e.preventDefault();
};

const filteredReminders = computed(() => {
  if (!Array.isArray(reminders.value)) return [];
  const query = searchQuery.value.toLowerCase().trim();
  if (!query) return reminders.value;
  return reminders.value.filter(r => r.label?.toLowerCase().includes(query));
});

const handleAddReminder = () => {
  resetForm();
  showAddForm.value = true;
};

const resetForm = () => {
  editingIndex.value = null;
  showAddForm.value = false;
  Object.assign(editedReminder, {
    id: null,
    label: '',
    day: null,
    time: '',
    isOneTime: false,
    selectedMonths: [],
    singleMonth: 1
  });
};

const saveReminder = async () => {
  if (!editedReminder.label || !editedReminder.day || !editedReminder.time) return;

  const payload = {
    id: editedReminder.id,
    label: editedReminder.label,
    day: editedReminder.day,
    time: editedReminder.time,
    isOneTime: editedReminder.isOneTime,
    months: editedReminder.isOneTime ? [editedReminder.singleMonth] : editedReminder.selectedMonths
  };

  try {
    const response = await fetch('/api/mail/config', {
      method: 'POST',
      headers: {'Content-Type': 'application/json'},
      body: JSON.stringify(payload)
    });
    if (response.ok) {
      await fetchReminders();
      resetForm();
    }
  } catch (error) {
    console.error("Erreur lors de l'enregistrement :", error);
  }
};

const handleEdit = (reminder: any, index: number) => {
  resetForm();
  editingIndex.value = index;
  Object.assign(editedReminder, {
    id: reminder.id,
    label: reminder.label,
    day: reminder.day,
    time: reminder.time,
    isOneTime: reminder.isOneTime,
    singleMonth: (reminder.isOneTime && reminder.months?.length > 0) ? reminder.months[0] : 1,
    selectedMonths: (reminder.months && !reminder.isOneTime) ? [...reminder.months] : []
  });
};

const handleDelete = async (id: number) => {
  if (confirm("Supprimer ce rappel ?")) {
    try {
      const response = await fetch(`/api/mail/config/${id}`, {method: 'DELETE'});
      if (response.ok) fetchReminders();
    } catch (error) {
      console.error("Erreur lors de la suppression :", error);
    }
  }
};
</script>

<template>
  <Sidebar />
  <div class="page-container">
    <AppHeader title="Mes Rappels" />

    <main class="main-content">
      <div class="grid-container">

        <div v-if="!showAddForm" class="resource-card add-card" @click="handleAddReminder">
          <div class="icon-circle plus">
            <svg width="40" height="40" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
              <line x1="12" y1="5" x2="12" y2="19"></line>
              <line x1="5" y1="12" x2="19" y2="12"></line>
            </svg>
          </div>
          <p>Nouveau rappel</p>
        </div>

        <div v-if="showAddForm" class="resource-card is-editing">
          <div class="card-content edit-mode">
            <div class="edit-header">
              <h4>Nouveau Rappel</h4>
              <button class="cancel-text-btn" @click="resetForm">Annuler</button>
            </div>
            <div class="input-group">
              <label class="input-label">Titre</label>
              <input type="text" v-model="editedReminder.label" class="card-input">
            </div>
            <div class="row">
              <div class="input-group">
                <label class="input-label">Heure</label>
                <input type="time" v-model="editedReminder.time" class="card-input">
              </div>
              <div class="input-group">
                <label class="input-label">Ponctuel ?</label>
                <div class="toggle-container" @click="editedReminder.isOneTime = !editedReminder.isOneTime">
                  <div class="toggle-switch" :class="{ 'active': editedReminder.isOneTime }"><div class="toggle-knob"></div></div>
                </div>
              </div>
            </div>
            <div class="row">
              <div class="input-group">
                <label class="input-label">Jour (1-28)</label>
                <input type="number" v-model.number="editedReminder.day" class="card-input" @input="validateNumInput">
              </div>
              <div v-if="editedReminder.isOneTime" class="input-group">
                <label class="input-label">Mois</label>
                <select v-model="editedReminder.singleMonth" class="card-input">
                  <option v-for="m in monthsList" :key="m.value" :value="m.value">{{ m.name }}</option>
                </select>
              </div>
            </div>
            <div v-if="!editedReminder.isOneTime" class="input-group full-width">
              <div class="months-grid">
                <label v-for="m in monthsList" :key="m.value" class="month-checkbox">
                  <input type="checkbox" :value="m.value" v-model="editedReminder.selectedMonths"> {{ m.name }}
                </label>
              </div>
            </div>
            <button class="save-btn" @click="saveReminder">Créer</button>
          </div>
        </div>

        <template v-for="(reminder, index) in filteredReminders" :key="reminder.id || index">

          <div v-if="editingIndex === index" class="resource-card is-editing">
            <div class="card-content edit-mode">
              <div class="edit-header">
                <h4>Modifier</h4>
                <button class="cancel-text-btn" @click="resetForm">Annuler</button>
              </div>
              <div class="input-group">
                <label class="input-label">Titre</label>
                <input type="text" v-model="editedReminder.label" class="card-input">
              </div>
              <div class="row">
                <div class="input-group">
                  <label class="input-label">Heure</label>
                  <input type="time" v-model="editedReminder.time" class="card-input">
                </div>
                <div class="input-group">
                  <label class="input-label">Ponctuel</label>
                  <div class="toggle-container" @click="editedReminder.isOneTime = !editedReminder.isOneTime">
                    <div class="toggle-switch" :class="{ 'active': editedReminder.isOneTime }">
                      <div class="toggle-knob"></div>
                    </div>
                  </div>
                </div>
              </div>
              <div class="row">
                <div class="input-group">
                  <label class="input-label">Jour</label>
                  <input type="number" v-model.number="editedReminder.day" class="card-input" @input="validateNumInput">
                </div>
                <div v-if="editedReminder.isOneTime" class="input-group">
                  <label class="input-label">Mois</label>
                  <select v-model="editedReminder.singleMonth" class="card-input">
                    <option v-for="m in monthsList" :key="m.value" :value="m.value">{{ m.name }}</option>
                  </select>
                </div>
              </div>
              <div v-if="!editedReminder.isOneTime" class="input-group full-width">
                <div class="months-grid">
                  <label v-for="m in monthsList" :key="m.value" class="month-checkbox">
                    <input type="checkbox" :value="m.value" v-model="editedReminder.selectedMonths"> {{ m.name }}
                  </label>
                </div>
              </div>
              <button class="save-btn" @click="saveReminder">Enregistrer</button>
            </div>
          </div>

          <div v-else class="resource-card">
            <div class="card-content view-mode">
              <div class="header-view">
                <div class="icon-circle big-icon" :class="{ 'one-time': reminder.isOneTime }">
                  <svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                    <rect x="3" y="4" width="18" height="18" rx="2" ry="2"></rect>
                    <line x1="16" y1="2" x2="16" y2="6"></line>
                    <line x1="8" y1="2" x2="8" y2="6"></line>
                    <line x1="3" y1="10" x2="21" y2="10"></line>
                  </svg>
                </div>
                <h3 class="resource-id">{{ reminder.label }}</h3>
                <p class="resource-title">{{ reminder.isOneTime ? 'Ponctuel' : 'Récurrent' }}</p>
              </div>
              <div class="info-container">
                <div class="info-badge"><span class="time-text">{{ reminder.time }}</span></div>
                <div class="date-detail">
                  <span v-if="reminder.isOneTime">Le {{ reminder.day }} {{ getMonthName(reminder.months?.[0]) }}</span>
                  <span v-else>Le {{ reminder.day }} de {{ reminder.months?.length || 0 }} mois</span>
                </div>
              </div>
              <div class="card-actions">
                <button class="action-btn edit" @click="handleEdit(reminder, index)">
                  <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                    <path d="M11 4H4a2 2 0 0 0-2 2v14a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2v-7"></path>
                    <path d="M18.5 2.5a2.121 2.121 0 0 1 3 3L12 15l-4 1 1-4 9.5-9.5z"></path>
                  </svg>
                </button>
                <button class="action-btn delete" @click="handleDelete(reminder.id)">
                  <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                    <polyline points="3 6 5 6 21 6"></polyline>
                    <path d="M19 6v14a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2V6m3 0V4a2 2 0 0 1 2-2h4a2 2 0 0 1 2 2v2"></path>
                  </svg>
                </button>
              </div>
            </div>
          </div>

        </template>
      </div>
    </main>
  </div>
</template>

<style scoped>
.page-container {
  min-height: 100vh;
  background-color: #f8f9fa;
  font-family: 'Roboto', sans-serif;
  padding-bottom: 120px;
}

.main-content {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 40px 20px;
  margin-top: 150px;
}

.grid-container {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(320px, 1fr));
  gap: 30px;
  width: 100%;
  max-width: 1200px;
}

.resource-card {
  background: #ffffff;
  border-radius: 20px;
  min-height: 420px;
  display: flex;
  flex-direction: column;
  padding: 25px;
  box-shadow: 0 10px 25px rgba(0, 0, 0, 0.05);
  transition: 0.3s;
  border: 2px solid transparent;
  box-sizing: border-box;
}

.resource-card.is-editing {
  border-color: #B51621;
}

.input-group {
  flex: 1;
  margin-bottom: 15px;
  display: flex;
  flex-direction: column;
}

.input-label {
  display: block;
  font-size: 11px;
  font-weight: bold;
  color: #999;
  margin-bottom: 4px;
  text-transform: uppercase;
}

.card-input {
  width: 100%;
  padding: 0 12px;
  border: 2px solid #eee;
  border-radius: 8px;
  font-size: 14px;
  height: 42px;
  box-sizing: border-box;
}

.row {
  display: flex;
  gap: 10px;
}

.toggle-container {
  display: flex;
  align-items: center;
  height: 42px;
  cursor: pointer;
}

.months-grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 6px;
  background: #f8f9fa;
  padding: 10px;
  border-radius: 8px;
  border: 1px solid #eee;
}

.month-checkbox {
  display: flex;
  align-items: center;
  gap: 4px;
  font-size: 11px;
  color: #555;
}

.save-btn {
  width: 100%;
  padding: 12px;
  background: #B51621;
  color: white;
  border: none;
  border-radius: 8px;
  font-weight: bold;
  cursor: pointer;
  margin-top: auto;
}

.header-view {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-bottom: 10px;
}

.icon-circle.big-icon {
  width: 60px;
  height: 60px;
  background: rgba(181, 22, 33, 0.1);
  color: #B51621;
  border-radius: 50%;
  display: flex;
  justify-content: center;
  align-items: center;
  margin-bottom: 10px;
}

.resource-id {
  font-size: 20px;
  font-weight: 800;
  margin: 0;
  text-align: center;
}

.info-container {
  text-align: center;
  margin: 15px 0;
}

.time-text {
  font-size: 22px;
  font-weight: 700;
  color: #333;
}

.card-actions {
  display: flex;
  gap: 12px;
  margin-top: auto;
  justify-content: center;
}

.action-btn {
  width: 38px;
  height: 38px;
  border-radius: 50%;
  border: none;
  background: #f0f2f5;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
}

.add-card {
  border: 3px dashed #ddd;
  justify-content: center;
  align-items: center;
  cursor: pointer;
  color: #aaa;
}

.toggle-switch {
  width: 36px;
  height: 20px;
  background: #ccc;
  border-radius: 20px;
  position: relative;
}
.toggle-switch.active { background: #B51621; }

.toggle-knob {
  width: 16px;
  height: 16px;
  background: white;
  border-radius: 50%;
  position: absolute;
  top: 2px;
  left: 2px;
  transition: 0.3s;
}

.toggle-switch.active .toggle-knob {
  transform: translateX(16px);
}

.cancel-text-btn {
  background: none;
  border: none;
  color: #B51621;
  font-weight: 700;
  cursor: pointer;
}
</style>