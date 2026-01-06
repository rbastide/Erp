<script setup>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import AppHeader from './Header.vue';
import Sidebar from './Sidebar.vue';

const router = useRouter();
const isDarkMode = ref(false);
const notificationsEnabled = ref(true);

onMounted(() => {
  const savedTheme = localStorage.getItem('user_theme');
  isDarkMode.value = savedTheme === 'dark';
});

const toggleTheme = () => {
  isDarkMode.value = !isDarkMode.value;
  if (isDarkMode.value) {
    document.body.classList.add('dark-mode');
    localStorage.setItem('user_theme', 'dark');
  } else {
    document.body.classList.remove('dark-mode');
    localStorage.setItem('user_theme', 'light');
  }
};

const handleSave = () => {
  alert("Paramètres enregistrés");
};
</script>

<template>
  <Sidebar :settings="true" />
  <AppHeader title="Paramètres" />

  <div class="main-content">

    <div class="settings-group">

      <div class="setting-card">
        <div class="card-header">
          <div class="icon-circle">
            <svg width="28" height="28" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><circle cx="12" cy="12" r="5"></circle><line x1="12" y1="1" x2="12" y2="3"></line><line x1="12" y1="21" x2="12" y2="23"></line><line x1="4.22" y1="4.22" x2="5.64" y2="5.64"></line><line x1="18.36" y1="18.36" x2="19.78" y2="19.78"></line><line x1="1" y1="12" x2="3" y2="12"></line><line x1="21" y1="12" x2="23" y2="12"></line><line x1="4.22" y1="19.78" x2="5.64" y2="18.36"></line><line x1="18.36" y1="5.64" x2="19.78" y2="4.22"></line></svg>
          </div>
          <h3>Apparence</h3>
        </div>

        <div class="setting-row">
          <div class="text-info">
            <span class="label">Mode Sombre</span>
            <span class="desc">Interface gris foncé (plus doux pour les yeux)</span>
          </div>

          <label class="switch">
            <input type="checkbox" :checked="isDarkMode" @change="toggleTheme">
            <span class="slider round"></span>
          </label>
        </div>
      </div>

      <div class="setting-card">
        <div class="card-header">
          <div class="icon-circle">
            <svg width="28" height="28" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M18 8A6 6 0 0 0 6 8c0 7-3 9-3 9h18s-3-2-3-9"></path><path d="M13.73 21a2 2 0 0 1-3.46 0"></path></svg>
          </div>
          <h3>Notifications</h3>
        </div>

        <div class="setting-row">
          <div class="text-info">
            <span class="label">Alertes email</span>
            <span class="desc">Recevoir un email lors d'une modification de SAE</span>
          </div>

          <label class="switch">
            <input type="checkbox" v-model="notificationsEnabled">
            <span class="slider round"></span>
          </label>
        </div>
      </div>

      <div class="actions">
        <button class="btn-save" @click="handleSave">Enregistrer</button>
      </div>

    </div>
  </div>
</template>

<style scoped>
.main-content {
  margin-top: 180px;
  margin-left: 100px;
  margin-right: 20px;
  display: flex;
  justify-content: center;
  font-family: 'Roboto', sans-serif;
}

.settings-group {
  width: 100%;
  max-width: 800px;
  display: flex;
  flex-direction: column;
  gap: 30px;
}

.setting-card {
  background: #ffffff;
  border-radius: 15px;
  padding: 30px;
  box-shadow: 0 5px 15px rgba(0,0,0,0.03);
  border: 1px solid rgba(0,0,0,0.05);
  transition: all 0.3s ease;
}

.card-header {
  display: flex;
  align-items: center;
  margin-bottom: 25px;
  border-bottom: 1px solid rgba(0,0,0,0.05);
  padding-bottom: 15px;
}

.icon-circle {
  width: 45px;
  height: 45px;
  background: rgba(181, 22, 33, 0.08);
  border-radius: 50%;
  display: flex;
  justify-content: center;
  align-items: center;
  margin-right: 20px;
  color: #B51621;
}

h3 {
  font-size: 20px;
  color: #333;
  margin: 0;
  font-weight: 600;
}

.setting-row {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.text-info {
  display: flex;
  flex-direction: column;
  gap: 5px;
}

.label {
  font-size: 17px;
  font-weight: 500;
  color: #333;
}

.desc {
  font-size: 14px;
  color: #888;
}

.switch {
  position: relative;
  display: inline-block;
  width: 56px;
  height: 30px;
}

.switch input {
  opacity: 0;
  width: 0;
  height: 0;
}

.slider {
  position: absolute;
  cursor: pointer;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: #ccc;
  transition: .4s;
}

.slider:before {
  position: absolute;
  content: "";
  height: 22px;
  width: 22px;
  left: 4px;
  bottom: 4px;
  background-color: white;
  transition: .4s;
}

input:checked + .slider {
  background-color: #B51621;
}

input:checked + .slider:before {
  transform: translateX(26px);
}

.slider.round {
  border-radius: 34px;
}

.slider.round:before {
  border-radius: 50%;
}

.actions {
  display: flex;
  justify-content: flex-end;
  margin-top: 10px;
}

.btn-save {
  padding: 12px 40px;
  border-radius: 50px;
  border: none;
  font-size: 16px;
  font-weight: 700;
  cursor: pointer;
  background: #B51621;
  color: white;
  transition: all 0.2s;
}

.btn-save:hover {
  background: #94121b;
  transform: translateY(-2px);
  box-shadow: 0 4px 10px rgba(181, 22, 33, 0.2);
}
</style>