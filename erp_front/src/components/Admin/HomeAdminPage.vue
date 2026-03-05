<script setup>
import { useRouter } from 'vue-router';
import AppHeader from '../App/Header.vue';
import Sidebar from '../App/Sidebar.vue';

const router = useRouter();

const handleFill = () => {
  router.push('/mccc-resource');
};

const handleHistory = () => {
  router.push('/history-admin');
};

const handleSignIn = () => {
  router.push('/users-management');
};

const handleCompetenceCreating = () => {
  router.push('/skill-creating');
};

const handleResources = () => {
  router.push('/resources-management');
};

const handleAddSae = () => {
  router.push('/sae-management');
};

const handleRole = () => {
  router.push('/role-management')
};

const handleRecall = () => {
  router.push('/recall-management')
};

</script>

<template>
  <Sidebar :showDepartments="true" :dashboardAdminActive="true" />
  <div class="page-container">
    <AppHeader title="Bonjour ADMIN"/>

    <main class="main-content">
      <div class="grid-container">

        <div class="card-action" @click="handleFill">
          <div class="icon-circle">
            <svg width="40" height="40" viewBox="0 0 24 24" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" style="fill: none;"><path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"></path><polyline points="14 2 14 8 20 8"></polyline><line x1="16" y1="13" x2="8" y2="13"></line><line x1="16" y1="17" x2="8" y2="17"></line><polyline points="10 9 9 9 8 9"></polyline></svg>
          </div>
          <p>Remplir les MCCC</p>
        </div>

        <div class="card-action" @click="handleHistory">
          <div class="icon-circle">
            <svg width="40" height="40" viewBox="0 0 24 24" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" style="fill: none;"><rect x="3" y="3" width="18" height="18" rx="2" ry="2"></rect><line x1="9" y1="3" x2="9" y2="21"></line></svg>
          </div>
          <p>Afficher les fiches ressource</p>
        </div>

        <div class="card-action" @click="handleSignIn">
          <div class="icon-circle">
            <svg width="40" height="40" viewBox="0 0 24 24" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" style="fill: none;"><path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"></path><circle cx="9" cy="7" r="4"></circle><path d="M23 21v-2a4 4 0 0 0-3-3.87"></path><path d="M16 3.13a4 4 0 0 1 0 7.75"></path></svg>
          </div>
          <p>Gestion des utilisateurs</p>
        </div>

        <div class="card-action" @click="handleCompetenceCreating">
          <div class="icon-circle">
            <svg width="40" height="40" viewBox="0 0 24 24" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" style="fill: none;"><path d="M22 11.08V12a10 10 0 1 1-5.93-9.14"></path><polyline points="22 4 12 14.01 9 11.01"></polyline></svg>
          </div>
          <p>Gestion des compétences</p>
        </div>

        <div class="card-action" @click="handleResources">
          <div class="icon-circle">
            <svg width="40" height="40" viewBox="0 0 24 24" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" style="fill: none;"><path d="M21 16V8a2 2 0 0 0-1-1.73l-7-4a2 2 0 0 0-2 0l-7 4A2 2 0 0 0 3 8v8a2 2 0 0 0 1 1.73l7 4a2 2 0 0 0 2 0l7-4A2 2 0 0 0 21 16z"></path><polyline points="3.27 6.96 12 12.01 20.73 6.96"></polyline><line x1="12" y1="22.08" x2="12" y2="12"></line></svg>
          </div>
          <p>Gestion des ressources</p>
        </div>

        <div class="card-action" @click="handleAddSae">
          <div class="icon-circle">
            <svg width="40" height="40" viewBox="0 0 24 24" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" style="fill: none;"><polygon points="12 2 2 7 12 12 22 7 12 2"></polygon><polyline points="2 17 12 22 22 17"></polyline><polyline points="2 12 12 17 22 12"></polyline></svg>
          </div>
          <p>Gestion des SAEs</p>
        </div>

        <div class="card-action" @click="handleRole">
          <div class="icon-circle">
            <svg width="40" height="40" viewBox="0 0 24 24" stroke="currentColor" stroke-width="2" style="fill: none;" stroke-linecap="round" stroke-linejoin="round">
              <path d="M12 22s8-4 8-10V5l-8-3-8 3v7c0 6 8 10 8 10z"></path>
            </svg>
          </div>
          <p>Gestion des roles</p>
        </div>

        <div class="card-action" @click="handleRecall">
          <div class="icon-circle">
            <svg width="40" height="40" viewBox="0 0 24 24" stroke="currentColor" stroke-width="2" style="fill: none;" stroke-linecap="round" stroke-linejoin="round">
              <path d="M4 4h16c1.1 0 2 .9 2 2v12c0 1.1-.9 2-2 2H4c-1.1 0-2-.9-2-2V6c0-1.1.9-2 2-2z"></path>
              <polyline points="22,6 12,13 2,6"></polyline>
            </svg>
          </div>
          <p>Périodicité des rappels</p>
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
}

.main-content {
  display: flex;
  justify-content: center;
  padding: 40px 20px;
  margin-top: 175px;
}

.grid-container {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(320px, 1fr));
  gap: 30px;
  width: 100%;
  max-width: 1200px;
  justify-items: center;
}

.card-action {
  cursor: pointer;
  background: #ffffff;
  width: 340px;
  height: 220px;
  border-radius: 15px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  text-align: center;
  padding: 20px;
  transition: all 0.3s cubic-bezier(0.25, 0.8, 0.25, 1);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
  border: 1px solid rgba(181, 22, 33, 0.1);
}

.card-action:hover {
  transform: translateY(-8px);
  box-shadow: 0 12px 24px rgba(181, 22, 33, 0.15);
  border-color: #B51621;
}

.icon-circle {
  width: 70px;
  height: 70px;
  background: rgba(181, 22, 33, 0.05);
  border-radius: 50%;
  display: flex;
  justify-content: center;
  align-items: center;
  margin-bottom: 15px;
  color: #B51621;
  transition: all 0.3s ease;
}

.card-action:hover .icon-circle {
  background: #B51621;
  color: #ffffff;
}

.card-action p {
  margin: 0;
  font-size: 24px;
  font-weight: 500;
  line-height: 1.2;
  color: #333333;
  transition: color 0.3s ease;
}

.card-action:hover p {
  color: #B51621;
}

@media (max-width: 1100px) {
  .grid-container {
    grid-template-columns: repeat(2, 1fr);
    max-width: 800px;
  }
}

@media (max-width: 750px) {
  .grid-container {
    grid-template-columns: 1fr;
  }
  .main-content {
    margin-top: 150px;
  }
}
</style>