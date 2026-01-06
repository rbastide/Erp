<script setup>
import { useRouter } from 'vue-router';
import { onMounted, ref } from "vue";

const props = defineProps({
  dashboardAdmin: { type: Boolean, default: false },
  dashboard: { type: Boolean, default: true },
  settings: { type: Boolean, default: true },
  help: { type: Boolean, default: true },
  quit: { type: Boolean, default: true },

  dashboardAdminActive: { type: Boolean, default: false },
  dashboardActive: { type: Boolean, default: false },
  settingsActive: { type: Boolean, default: false },
  helpActive: { type: Boolean, default: false },
  quitActive: { type: Boolean, default: false },
});

const router = useRouter();
const isExpanded = ref(false);

const handleDashboard = () => router.push('/home');
const handleAdmin = () => router.push('/home-admin');
const handleSettings = () => router.push('/settings');
const handleAide = () => router.push('/aide');
const handleDeconnexion = () => {
  localStorage.removeItem('user_token');
  router.push('/deconnexion');
};

onMounted(() => {
})
</script>

<template>
  <aside
      class="sidebar"
      :class="{ 'expanded': isExpanded }"
      @mouseenter="isExpanded = true"
      @mouseleave="isExpanded = false"
  >
    <div class="sidebar-header">
      <div class="hamburger-icon">
        <svg width="32" height="32" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
          <line x1="3" y1="12" x2="21" y2="12"></line>
          <line x1="3" y1="6" x2="21" y2="6"></line>
          <line x1="3" y1="18" x2="21" y2="18"></line>
        </svg>
      </div>
      <span class="nav-text" style="font-size: x-large; font-weight: bold; color: #B51621;">Menu</span>
    </div>

    <nav class="sidebar-nav">

      <div v-if="dashboard" class="nav-item" :class="{ active: props.dashboardActive }" @click="handleDashboard">
        <div class="icon-wrapper">
          <svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><rect x="3" y="3" width="7" height="7"></rect><rect x="14" y="3" width="7" height="7"></rect><rect x="14" y="14" width="7" height="7"></rect><rect x="3" y="14" width="7" height="7"></rect></svg>
        </div>
        <span class="nav-text">Dashboard</span>
      </div>

      <div v-if="dashboardAdmin" class="nav-item" :class="{ active: props.dashboardAdminActive }" @click="handleAdmin">
        <div class="icon-wrapper">
          <svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><rect x="3" y="3" width="7" height="7"></rect><rect x="14" y="3" width="7" height="7"></rect><rect x="14" y="14" width="7" height="7"></rect><rect x="3" y="14" width="7" height="7"></rect></svg>
        </div>
        <span class="nav-text">Dashboard</span>
      </div>

      <div v-if="settings" class="nav-item" :class="{ active: props.settingsActive }" @click="handleSettings">
        <div class="icon-wrapper">
          <svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><circle cx="12" cy="12" r="3"></circle><path d="M19.4 15a1.65 1.65 0 0 0 .33 1.82l.06.06a2 2 0 0 1 0 2.83 2 2 0 0 1-2.83 0l-.06-.06a1.65 1.65 0 0 0-1.82-.33 1.65 1.65 0 0 0-1 1.51V21a2 2 0 0 1-2 2 2 2 0 0 1-2-2v-.09A1.65 1.65 0 0 0 9 19.4a1.65 1.65 0 0 0-1.82.33l-.06.06a2 2 0 0 1-2.83 0 2 2 0 0 1 0-2.83l.06-.06a1.65 1.65 0 0 0 .33-1.82 1.65 1.65 0 0 0-1.51-1H3a2 2 0 0 1-2-2 2 2 0 0 1 2-2h.09A1.65 1.65 0 0 0 4.6 9a1.65 1.65 0 0 0-.33-1.82l-.06-.06a2 2 0 0 1 0-2.83 2 2 0 0 1 2.83 0l.06.06a1.65 1.65 0 0 0 1.82.33H9a1.65 1.65 0 0 0 1-1.51V3a2 2 0 0 1 2-2 2 2 0 0 1 2 2v.09a1.65 1.65 0 0 0 1 1.51 1.65 1.65 0 0 0 1.82-.33l.06-.06a2 2 0 0 1 2.83 0 2 2 0 0 1 0 2.83l-.06.06a1.65 1.65 0 0 0-.33 1.82V9a1.65 1.65 0 0 0 1.51 1H21a2 2 0 0 1 2 2 2 2 0 0 1-2 2h-.09a1.65 1.65 0 0 0-1.51 1z"></path></svg>
        </div>
        <span class="nav-text">Paramètres</span>
      </div>

    </nav>

    <div class="sidebar-footer">
      <div v-if="help" class="nav-item small" :class="{ active: props.helpActive }" @click="handleAide">
        <div class="icon-wrapper">
          <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><circle cx="12" cy="12" r="10"></circle><path d="M9.09 9a3 3 0 0 1 5.83 1c0 2-3 3-3 3"></path><line x1="12" y1="17" x2="12.01" y2="17"></line></svg>
        </div>
        <span class="nav-text">Aide</span>
      </div>
      <div v-if="quit" class="nav-item small logout" :class="{ active: props.quitActive }" @click="handleDeconnexion">
        <div class="icon-wrapper">
          <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M9 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h4"></path><polyline points="16 17 21 12 16 7"></polyline><line x1="21" y1="12" x2="9" y2="12"></line></svg>
        </div>
        <span class="nav-text">Déconnexion</span>
      </div>
    </div>
  </aside>
</template>

<style scoped>
.sidebar {
  width: 80px;
  height: 100%;
  position: fixed;
  left: 0;
  top: 0;
  background-color: rgba(255, 255, 255, 1);
  display: flex;
  flex-direction: column;
  box-shadow: 2px 0 10px rgba(0,0,0,0.05);
  z-index: 1000;
  font-family: 'Roboto', sans-serif;
  transition: width 0.3s cubic-bezier(0.4, 0, 0.2, 1);
  overflow: hidden;
}

.sidebar.expanded {
  width: 260px;
}

.sidebar-header {
  height: 172px;
  display: flex;
  align-items: center;
  justify-content: flex-start;
  padding-left: 24px;
  border-bottom: 1px solid #f0f0f0;
  overflow: hidden;
  white-space: nowrap;
  background-color: #96111a;
  transition: background-color 0.3s ease;
}

.sidebar.expanded .sidebar-header {
  background-color: #ffffff;
}

.hamburger-icon {
  color: #ffffff;
  cursor: pointer;
  min-width: 32px;
  transition: color 0.3s ease;
}

.sidebar.expanded .hamburger-icon {
  color: #96111a;
}

.logo {
  height: 40px;
  margin-left: 20px;
}

.fade-enter-active, .fade-leave-active { transition: opacity 0.2s; }
.fade-enter-from, .fade-leave-to { opacity: 0; }

.sidebar-nav {
  flex-grow: 1;
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.nav-item {
  display: flex;
  align-items: center;
  padding: 15px 28px;
  cursor: pointer;
  color: #555;
  transition: all 0.2s ease;
  font-size: 16px;
  font-weight: 500;
  border-left: 4px solid transparent;
  white-space: nowrap;
  height: 50px;
}

.icon-wrapper {
  min-width: 24px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.nav-text {
  margin-left: 20px;
  opacity: 0;
  transition: opacity 0.2s ease-in-out;
  pointer-events: none;
}

.sidebar.expanded .nav-text {
  opacity: 1;
  pointer-events: auto;
}

.nav-item:hover {
  background-color: #f9f9f9;
  color: #B51621;
}

.nav-item.active {
  background-color: #fff0f1;
  color: #B51621;
  border-left-color: #B51621;
}

.sidebar-footer {
  padding-bottom: 20px;
  border-top: 1px solid #f0f0f0;
}

.nav-item.small {
  font-size: 15px;
}

.nav-item.logout:hover {
  color: #d32f2f;
  background-color: #ffebee;
}
</style>