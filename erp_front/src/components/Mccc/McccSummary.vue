<script setup lang="ts">
import { onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { mcccStore } from "@/services/mcccStore.js";
import AppHeader from '../App/Header.vue';
import Sidebar from "../App/Sidebar.vue";
import api from '@/services/api';

const router = useRouter();

const handleRetour = () => router.back();

const handleValider = async () => {
  try {
    const payload = {
      resourceCode: mcccStore.resourceCode,
      creationDate: mcccStore.creationDate,
      editDate: mcccStore.editDate,
      hoursCM: mcccStore.hoursCM,
      hoursTD: mcccStore.hoursTD,
      hoursTP: mcccStore.hoursTP,
      hoursDS: mcccStore.hoursDS,
      hoursDSTP: mcccStore.hoursDSTP,
      hoursTotal: mcccStore.hoursTotal,
      saeCodes: mcccStore.saeCodes,
      ue: mcccStore.ue,
      referents: mcccStore.referents
    };

    const response = await api.post('/mccc/mcccResponse', payload);
    if (response.status === 200 || response.status === 201) {
      mcccStore.clearMcccStore();
      await router.push('/modif-saved');
    }
  } catch (error) {
    console.error("Erreur :", error);
    alert("Une erreur est survenue.");
  }
};

onMounted(() => {
  mcccStore.loadMcccStore();
  const today = new Date().toLocaleDateString('fr-FR');
  if (!mcccStore.creationDate) mcccStore.creationDate = today;
  mcccStore.editDate = today;
});

const hourConfig = {
  cm: { label: 'CM', color: '#4DB6AC', value: () => mcccStore.hoursCM },
  td: { label: 'TD', color: '#7986CB', value: () => mcccStore.hoursTD },
  ds: { label: 'DS', color: '#FFB74D', value: () => mcccStore.hoursDS },
  tp: { label: 'TP', color: '#4FC3F7', value: () => mcccStore.hoursTP },
  ds_tp: { label: 'DS/TP', color: '#BA68C8', value: () => mcccStore.hoursDSTP },
};

const getGroupsForUE = (ueName) => {
  if (!mcccStore.acsGrouped) return [];
  return mcccStore.acsGrouped.filter(group => group.ue === ueName);
};
</script>

<template>
  <Sidebar :dashboard="false" :dashboardAdmin="true"/>
  <AppHeader title="Récapitulatif Final" :inline="mcccStore.resourceCode"/>

  <main class="main-content">
    <div class="container">

      <div class="info-banner">
        <div class="banner-item">
          <span class="label">Ressource</span>
          <span class="value">{{ mcccStore.resourceCode }}</span>
        </div>
        <div class="banner-item">
          <span class="label">Dernière modification</span>
          <span class="value">{{ mcccStore.editDate }}</span>
        </div>
      </div>

      <div class="grid-top">
        <section class="recap-card">
          <h2 class="card-title">Volumes Horaires</h2>
          <div class="hours-display-grid">
            <div v-for="(cfg, key) in hourConfig" :key="key" class="hour-pill">
              <span class="pill-label" :style="{ backgroundColor: cfg.color }">{{ cfg.label }}</span>
              <span class="pill-value">{{ cfg.value() || 0 }} h</span>
            </div>
          </div>
          <div class="total-bar">
            <span>Total Global</span>
            <span class="total-val">{{ mcccStore.hoursTotal }} h</span>
          </div>
        </section>

        <section class="recap-card">
          <h2 class="card-title">SAE Associées</h2>
          <div class="sae-tags">
            <span v-for="sae in mcccStore.saeCodes" :key="sae" class="sae-badge">
              {{ sae }}
            </span>
            <p v-if="!mcccStore.saeCodes?.length" class="empty">Aucune SAE sélectionnée</p>
          </div>
        </section>
      </div>

      <h2 class="section-divider">Compétences & Objectifs</h2>

      <div v-for="(ueItem, ueIndex) in mcccStore.ue" :key="ueIndex" class="ue-card">
        <div class="ue-header">
          <div class="ue-code">{{ ueItem.includes(':') ? ueItem.split(":")[0] : 'UE' }}</div>
          <div class="ue-name">{{ ueItem.includes(':') ? ueItem.split(":")[1] : ueItem }}</div>
        </div>

        <div class="ue-content">
          <table class="minimal-table">
            <thead>
            <tr>
              <th style="width: 80px">Niv.</th>
              <th>Apprentissages Critiques (AC)</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="(group, grpIndex) in getGroupsForUE(ueItem)" :key="grpIndex">
              <td class="td-level">{{ grpIndex + 1 }}</td>
              <td class="td-details">
                <div class="ac-item">
                  <div class="ac-sub-label">Objectif :</div>
                  <p>{{ group.niveau }}</p>
                </div>
                <div class="ac-item" v-if="group.acs?.length">
                  <div class="ac-sub-label">AC associés :</div>
                  <ul class="clean-list">
                    <li v-for="ac in group.acs" :key="ac">{{ ac }}</li>
                  </ul>
                </div>
              </td>
            </tr>
            </tbody>
          </table>
        </div>
      </div>

      <section class="referent-section">
        <p><strong>Enseignant(s) référent(s) :
        </strong>
          <span v-for="(ref, i) in mcccStore.referents" :key="i">
            {{ ref.firstname }} {{ ref.lastname.toUpperCase() }}{{ i < mcccStore.referents.length - 1 ? ', ' : '' }}
          </span>
        </p>
      </section>

      <div class="actions-container">
        <button @click="handleRetour" class="btn btn-secondary">Modifier les informations</button>
        <button @click="handleValider" class="btn btn-primary">Valider l'enregistrement</button>
      </div>

    </div>
  </main>
</template>

<style scoped>
.main-content {
  background-color: #f4f7f9;
  min-height: 100vh;
  padding: 220px 20px 80px;
  font-family: 'Roboto', sans-serif;
}

.container {
  max-width: 1100px;
  margin: 0 auto;
}

.info-banner {
  background: white;
  display: flex;
  gap: 40px;
  padding: 20px 30px;
  border-radius: 12px;
  margin-bottom: 30px;
  box-shadow: 0 4px 15px rgba(0,0,0,0.05);
  border-left: 6px solid #B51621;
}

.banner-item {
  display: flex;
  flex-direction: column;
}
.banner-item .label {
  font-size: 0.8rem;
  color: #64748b;
  text-transform: uppercase;
  font-weight: bold;
}
.banner-item .value {
  font-size: 1.2rem;
  font-weight: 700;
  color: #1e293b;
}

.grid-top {
  display: grid;
  grid-template-columns: 1.5fr 1fr;
  gap: 30px;
  margin-bottom: 40px;
}

.recap-card {
  background: white;
  padding: 25px;
  border-radius: 16px;
  box-shadow: 0 4px 20px rgba(0,0,0,0.05);
}

.card-title {
  font-size: 1.1rem;
  font-weight: 700;
  color: #B51621;
  margin-bottom: 20px;
  text-transform: uppercase;
  letter-spacing: 1px;
}

.hours-display-grid {
  display: flex;
  flex-wrap: wrap;
  gap: 12px;
  margin-bottom: 20px;
}

.hour-pill {
  display: flex;
  border: 1px solid #e2e8f0;
  border-radius: 8px;
  overflow: hidden;
  background: #f8fafc;
}

.pill-label {
  padding: 8px 12px;
  color: white;
  font-weight: 900;
  font-size: 0.9rem;
}

.pill-value {
  padding: 8px 15px;
  font-weight: 700;
  color: #1e293b;
}

.total-bar {
  border-top: 2px dashed #e2e8f0;
  padding-top: 15px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-weight: 900;
  font-size: 1.2rem;
  color: #B51621;
}

.sae-tags {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
}
.sae-badge {
  background: #1e293b;
  color: white;
  padding: 8px 16px;
  border-radius: 20px;
  font-weight: 600;
}

.section-divider {
  text-align: center;
  color: #64748b;
  margin: 50px 0 30px;
  font-size: 1.5rem;
  font-weight: 300;
}

.ue-card {
  background: white;
  border-radius: 16px;
  margin-bottom: 30px;
  overflow: hidden;
  box-shadow: 0 10px 30px rgba(0,0,0,0.08);
}

.ue-header {
  background: #1e293b;
  color: white;
  padding: 20px 30px;
  display: flex;
  align-items: center;
  gap: 20px;
}

.ue-code {
  background: #B51621;
  padding: 5px 15px;
  border-radius: 6px;
  font-weight: 900;
}

.ue-name {
  font-size: 1.2rem;
  font-weight: 500;
}

.minimal-table {
  width: 100%;
  border-collapse: collapse;
}

.minimal-table th {
  text-align: left;
  padding: 15px 30px;
  background: #f8fafc;
  color: #64748b;
  font-size: 0.8rem;
  text-transform: uppercase;
}

.td-level {
  text-align: center;
  font-size: 2.5rem;
  font-weight: 900;
  color: #e2e8f0;
  border-right: 1px solid #f1f5f9;
}

.td-details {
  padding: 25px 30px;
}
.ac-item {
  margin-bottom: 15px;
}
.ac-sub-label {
  font-size: 0.75rem;
  color: #B51621;
  font-weight: 800;
  text-transform: uppercase;
  margin-bottom: 4px;
}
.clean-list {
  padding-left: 20px;
  margin: 0;
}
.clean-list li {
  color: #475569;
  margin-bottom: 4px;
}

.referent-section {
  text-align: center;
  padding: 30px;
  color: #64748b;
  border-top: 1px solid #e2e8f0;
  margin-top: 20px;
  font-size: 20px;
}

.actions-container {
  display: flex;
  justify-content: center;
  gap: 25px;
  padding: 40px 0;
}

.btn {
  padding: 15px 45px;
  border-radius: 10px;
  font-weight: 700;
  cursor: pointer;
  transition: all 0.2s;
  border: none;
  font-size: 1rem;
}

.btn-primary {
  background: #B51621;
  color: white;
}
.btn-primary:hover {
  background: #8e111a;
  transform: translateY(-3px);
  box-shadow: 0 4px 12px rgba(181, 22, 33, 0.2);
}

.btn-secondary {
  background: #e2e8f0;
  color: #475569;
}
.btn-secondary:hover {
  background: #cbd5e1;
  transform: translateY(-2px);
}

@media (max-width: 850px) {
  .grid-top {
    grid-template-columns: 1fr;
  }
  .actions-container {
    flex-direction: column-reverse;
    align-items: stretch;
  }
}
</style>