<script setup lang="ts">
import { ref, reactive, computed } from 'vue';
import { useRouter } from 'vue-router';
import AppHeader from '../App/Header.vue';
import Sidebar from '../App/Sidebar.vue';

const router = useRouter();
const showAddForm = ref(false);
const editingIndex = ref<number | null>(null);
const searchQuery = ref('');

const reminders = ref([
  { id: 1, label: 'EX 1', day: '12', time: '08:00', isOneTime: false, month: '' },
  { id: 2, label: 'EX 2', day: '15', time: '14:30', isOneTime: true, month: 'Mars' }
]);

const editedReminder = reactive({
  id: null as number | null,
  label: '',
  day: null,
  time: '',
  isOneTime: false,
  month: ''
});

const validateNumInput = () => {
  let val = editedReminder.day;
  if (val === '' || val === null) return;

  if (val > 28) {
    editedReminder.day = 28;
  }
  else if (val < 1) {
    editedReminder.day = 1;
  }
  else {
    editedReminder.day = Math.floor(val);
  }
};

const blockInvalidChars = (e) => {
  if (['e', 'E', '+', '-'].includes(e.key)) {
    e.preventDefault();
  }
};

const months = ['Janvier', 'Février', 'Mars', 'Avril', 'Mai', 'Juin', 'Juillet', 'Août', 'Septembre', 'Octobre', 'Novembre', 'Décembre'];

const filteredReminders = computed(() => {
  const query = searchQuery.value.toLowerCase().trim();
  if (!query) return reminders.value;
  return reminders.value.filter(r => r.label.toLowerCase().includes(query));
});

const handleAddReminder = () => {
  resetForm();
  showAddForm.value = true;
};

const resetForm = () => {
  editingIndex.value = null;
  showAddForm.value = false;
  Object.assign(editedReminder, { id: null, label: '', day: '', time: '', isOneTime: false, month: '' });
};

const saveReminder = () => {
  if (!editedReminder.label || !editedReminder.day || !editedReminder.time) return;

  const dayNum = parseInt(editedReminder.day);
  if (dayNum < 1) editedReminder.day = '1';
  if (dayNum > 28) editedReminder.day = '28';

  if (editedReminder.id) {
    const index = reminders.value.findIndex(r => r.id === editedReminder.id);
    reminders.value[index] = { ...editedReminder } as any;
  } else {
    reminders.value.push({
      ...editedReminder,
      id: Date.now()
    } as any);
  }
  resetForm();
};

const handleEdit = (reminder: any, index: number) => {
  resetForm();
  editingIndex.value = index;
  Object.assign(editedReminder, JSON.parse(JSON.stringify(reminder)));
};

const handleDelete = (id: number) => {
  if (confirm("Supprimer ce rappel ?")) {
    reminders.value = reminders.value.filter(r => r.id !== id);
  }
};

const handleValidate = () => router.push('/home');
</script>

<template>
  <Sidebar />
  <div class="page-container">
    <AppHeader title="Mes Rappels" />

    <main class="main-content">
      <div class="grid-container">

        <div v-if="!showAddForm" class="resource-card add-card" @click="handleAddReminder">
          <div class="icon-circle plus">
            <svg width="40" height="40" viewBox="0 0 24 24" style="fill: none;" stroke="currentColor" stroke-width="2">
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
              <label class="input-label">Titre du rappel</label>
              <input type="text" v-model="editedReminder.label" class="card-input">
            </div>
            <div class="row">
              <div class="input-group">
                <label class="input-label">Heure</label>
                <input type="time" v-model="editedReminder.time" class="card-input">
              </div>
              <div class="input-group">
                <label class="input-label">Option ponctuelle</label>
                <div class="toggle-container" @click="editedReminder.isOneTime = !editedReminder.isOneTime">
                  <div class="toggle-switch" :class="{ 'active': editedReminder.isOneTime }"><div class="toggle-knob"></div></div>
                  <span class="desc">Rappel Ponctuel</span>
                </div>
              </div>
            </div>
            <div class="row">
              <div class="input-group">
                <label class="input-label">Jour (1 à 28)</label>
                <input type="number" min="1" max="28" v-model.number="editedReminder.day" class="card-input" @input="validateNumInput" @keydown="blockInvalidChars">
              </div>
              <div v-if="editedReminder.isOneTime" class="input-group">
                <label class="input-label">Mois</label>
                <select v-model="editedReminder.month" class="card-input">
                  <option v-for="m in months" :key="m" :value="m">{{ m }}</option>
                </select>
              </div>
            </div>
            <button class="save-btn" @click="saveReminder">Enregistrer</button>
          </div>
        </div>

        <div v-for="(reminder, index) in filteredReminders" :key="reminder.id" class="resource-card" :class="{ 'is-editing': editingIndex === index }">

          <div v-if="editingIndex === index" class="card-content edit-mode">
            <div class="edit-header">
              <h4>Modifier le rappel</h4>
              <button class="cancel-text-btn" @click="resetForm">Annuler</button>
            </div>
            <div class="input-group">
              <label class="input-label">Titre du rappel</label>
              <input type="text" v-model="editedReminder.label" class="card-input">
            </div>
            <div class="row">
              <div class="input-group">
                <label class="input-label">Heure</label>
                <input type="time" v-model="editedReminder.time" class="card-input">
              </div>
              <div class="input-group">
                <label class="input-label">Option ponctuelle</label>
                <div class="toggle-container" @click="editedReminder.isOneTime = !editedReminder.isOneTime">
                  <div class="toggle-switch" :class="{ 'active': editedReminder.isOneTime }"><div class="toggle-knob"></div></div>
                  <span class="desc">Rappel Ponctuel</span>
                </div>
              </div>
            </div>
            <div class="row">
              <div class="input-group">
                <label class="input-label">Jour (1 à 28)</label>
                <input type="number" min="1" max="28" v-model.number="editedReminder.day" class="card-input" @input="validateNumInput">
              </div>
              <div v-if="editedReminder.isOneTime" class="input-group">
                <label class="input-label">Mois</label>
                <select v-model="editedReminder.month" class="card-input">
                  <option v-for="m in months" :key="m" :value="m">{{ m }}</option>
                </select>
              </div>
            </div>
            <button class="save-btn" @click="saveReminder">Enregistrer</button>
          </div>

          <div v-else class="card-content view-mode">
            <div class="header-view">
              <div class="icon-circle big-icon" :class="{ 'one-time': reminder.isOneTime }">
                <svg width="24" height="24" viewBox="0 0 24 24" style="fill: none;" stroke="currentColor" stroke-width="2">
                  <rect x="3" y="4" width="18" height="18" rx="2" ry="2"></rect>
                  <line x1="16" y1="2" x2="16" y2="6"></line>
                  <line x1="8" y1="2" x2="8" y2="6"></line>
                  <line x1="3" y1="10" x2="21" y2="10"></line>
                </svg>
              </div>
              <h3 class="resource-id">{{ reminder.label }}</h3>
              <p class="resource-title">{{ reminder.isOneTime ? 'Ponctuel' : 'Récurrent (Mensuel)' }}</p>
            </div>
            <div class="info-container">
              <div class="info-badge"><span class="time-text">{{ reminder.time }}</span></div>
              <div class="date-detail"><span>Le {{ reminder.day }} {{ reminder.month }}</span></div>
            </div>
            <div class="card-actions">
              <button class="action-btn edit" @click="handleEdit(reminder, index)"><svg width="20" height="20" viewBox="0 0 24 24" style="fill: none;" stroke="currentColor" stroke-width="2"><path d="M11 4H4a2 2 0 0 0-2 2v14a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2v-7"></path><path d="M18.5 2.5a2.121 2.121 0 0 1 3 3L12 15l-4 1 1-4 9.5-9.5z"></path></svg></button>
              <button class="action-btn delete" @click="handleDelete(reminder.id)"><svg width="20" height="20" viewBox="0 0 24 24" style="fill: none;" stroke="currentColor" stroke-width="2"><polyline points="3 6 5 6 21 6"></polyline><path d="M19 6v14a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2V6m3 0V4a2 2 0 0 1 2-2h4a2 2 0 0 1 2 2v2"></path></svg></button>
            </div>
          </div>
        </div>
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
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  gap: 30px;
  width: 100%;
  max-width: 1200px;
}

.resource-card {
  background: #ffffff;
  border-radius: 20px;
  min-height: 380px;
  display: flex;
  flex-direction: column;
  padding: 25px;
  box-sizing: border-box;
  box-shadow: 0 10px 25px rgba(0,0,0,0.05);
  transition: all 0.3s ease;
  border: 2px solid transparent;
}

.resource-card.is-editing {
  border-color: #B51621;
}

.header-view {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-bottom: 20px;
}

.icon-circle.big-icon {
  width: 70px;
  height: 70px;
  background: rgba(181, 22, 33, 0.1);
  color: #B51621;
  border-radius: 50%;
  display: flex;
  justify-content: center;
  align-items: center;
  margin-bottom: 15px;
}

.icon-circle.big-icon.one-time {
  background: rgba(33, 150, 243, 0.1);
  color: #2196F3;
}

.resource-id {
  font-size: 22px;
  font-weight: 900;
  margin: 0;
  text-align: center;
}

.info-container {
  text-align: center;
  margin: 20px 0;
}

.info-badge {
  background: #f0f2f5;
  display: inline-block;
  padding: 8px 20px;
  border-radius: 50px;
  margin-bottom: 10px;
}

.time-text {
  font-size: 24px;
  font-weight: 700;
  color: #333;
}

.date-detail {
  font-size: 16px;
  color: #666;
  font-weight: 500;
}

.card-actions {
  display: flex;
  gap: 15px;
  margin-top: auto;
  justify-content: center;
}

.row {
  display: flex;
  gap: 15px;
}

.input-group {
  flex: 1;
  margin-bottom: 15px;
  text-align: left;
}

.input-label {
  display: block;
  font-size: 12px;
  font-weight: bold;
  color: #888;
  margin-bottom: 5px;
}

.card-input {
  width: 100%;
  padding: 12px;
  border: 2px solid #eee;
  border-radius: 10px;
  font-family: inherit;
  box-sizing: border-box;
}

.toggle-container {
  display: flex;
  align-items: center;
  gap: 10px;
  cursor: pointer;
  padding: 10px 0;
}

.toggle-switch {
  width: 40px;
  height: 22px;
  background: #ddd;
  border-radius: 20px;
  position: relative;
  transition: 0.3s;
}

.toggle-switch.active { background: #B51621; }

.toggle-knob {
  width: 18px;
  height: 18px;
  background: white;
  border-radius: 50%;
  position: absolute;
  top: 2px;
  left: 2px;
  transition: 0.3s;
}

.toggle-switch.active .toggle-knob { transform: translateX(18px); }

.add-card {
  border: 3px dashed #e0e0e0;
  justify-content: center;
  align-items: center;
  cursor: pointer;
}

.add-card:hover {
  border-color: #B51621;
  background: rgba(181, 22, 33, 0.02);
}

.save-btn {
  display: flex;
  width: 100%;
  padding: 15px;
  background: #B51621;
  color: white;
  border: none;
  border-radius: 10px;
  font-weight: bold;
  cursor: pointer;
  margin-top: 10px;
  justify-content: center;
  align-items: center;
  gap: 10px;
}

.cancel-text-btn {
  background: none;
  border: none;
  color: #B51621;
  font-weight: bold;
  font-size: 14px;
  cursor: pointer;
  padding: 5px 10px;
  border-radius: 5px;
}

.sticky-bar {
  position: fixed;
  bottom: 0;
  left: 0;
  width: 100%;
  background: white;
  box-shadow: 0 -5px 15px rgba(0,0,0,0.1);
  padding: 12px 0;
  z-index: 100;
}

.sticky-wrapper {
  max-width: 1200px;
  margin: 0 auto;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 40px;
}

.search-container {
  position: relative;
  width: 320px;
}

.search-icon {
  position: absolute;
  left: 15px;
  top: 50%;
  transform: translateY(-50%);
  color: #999;
}

.search-input {
  width: 100%;
  padding: 10px 15px 10px 40px;
  border-radius: 50px;
  border: 1px solid #ddd;
  outline: none;
  font-size: 14px;
}

.btn-sys.primary {
  background: linear-gradient(135deg, #B51621 0%, #d92533 100%);
  color: white;
  padding: 12px 35px;
  border-radius: 50px;
  font-weight: bold;
  border: none;
  cursor: pointer;
  min-width: 160px;
  font-size: 15px;
}

.action-btn {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  border: none;
  background: #f0f2f5;
  cursor: pointer;
}
</style>