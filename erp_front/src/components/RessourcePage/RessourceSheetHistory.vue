<script setup lang="ts">
import { useRouter } from 'vue-router'
import AppHeader from '../App/Header.vue'
import Sidebar from '../App/Sidebar.vue'

const router = useRouter()

const handleFermer = () => { router.push('/history') };

const hourConfig = {
  cm: { label: 'CM', color: '#4DB6AC' },
  td: { label: 'TD', color: '#7986CB' },
  tp: { label: 'TP', color: '#4FC3F7' },
  ds: { label: 'DS', color: '#FFB74D' },
  ds_tp: { label: 'DS/TP', color: '#BA68C8' }
}
</script>

<template>
  <Sidebar/>
  <AppHeader title="Fiche Ressource" inline="RX.XX du 00/00/0000" />

  <main class="main-content">
    <div class="container">

      <section class="form-card">
        <h2 class="section-title">Volume horaire global</h2>

        <div class="hours-grid-wrapper">
          <div class="hours-row">
            <div class="hour-block" v-for="key in (['cm', 'td', 'ds'] as const)" :key="key">
              <label :style="{ color: hourConfig[key].color }">{{ hourConfig[key].label }}</label>
              <div class="box-static">{{ 0 }}</div>
            </div>
          </div>

          <div class="hours-row mt-25">
            <div class="hour-block" v-for="key in (['tp', 'ds_tp'] as const)" :key="key">
              <label :style="{ color: hourConfig[key].color }">{{ hourConfig[key].label }}</label>
              <div class="box-static">{{ 0 }}</div>
            </div>
            <div class="hour-block">
              <label style="color: #64748b;">Total Global</label>
              <div class="box-static total-highlight">{{ 0 }} h</div>
            </div>
          </div>
        </div>

        <div class="student-hour-row">
          <label>Nombre d'heures par étudiant :</label>
          <div class="box-static student-box">0 h</div>
        </div>
      </section>

      <section class="form-card">
        <h2 class="section-title">Contenu pédagogique</h2>

        <div class="pedagogic-group">
          <h3 class="group-label">CM</h3>
          <div class="read-only-box">Contenu des CM...</div>
        </div>

        <div class="pedagogic-group">
          <h3 class="group-label">TD</h3>
          <div class="read-only-box">Contenu des TD...</div>
        </div>

        <div class="pedagogic-group">
          <h3 class="group-label">TP</h3>
          <div class="read-only-box">Contenu des TP...</div>
        </div>

        <div class="pedagogic-group">
          <h3 class="group-label">DS</h3>
          <div class="read-only-box">Modalités d'évaluation...</div>
        </div>

        <div class="pedagogic-group">
          <h3 class="group-label">DS/TP</h3>
          <div class="read-only-box">Contenu DS/TP...</div>
        </div>
      </section>

      <section class="form-card accent">
        <h2 class="section-title">Bilans et Évolutions</h2>

        <div class="pedagogic-group">
          <label class="field-label">Retour pédagogique des professeurs :</label>
          <div class="read-only-box large">Points forts, points faibles du semestre...</div>
        </div>

        <div class="pedagogic-group">
          <label class="field-label">Retour des étudiants :</label>
          <div class="read-only-box large">Synthèse des retours...</div>
        </div>

        <div class="pedagogic-group">
          <label class="field-label">Améliorations à apporter :</label>
          <div class="read-only-box large">Modifications prévues...</div>
        </div>
      </section>

      <div class="actions-footer">
        <button @click="handleFermer" class="btn btn-primary">Fermer la fiche</button>
      </div>

    </div>
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

.container { width: 100%;
  max-width: 900px;
  margin: 0 auto;
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
  color: #1e293b;
}

.total-highlight {
  border: 2px solid #E92533;
  color: #E92533;
  background: #fff5f5;
}

.student-hour-row {
  display: flex;
  align-items: center;
  gap: 20px;
  border-top: 1px dashed #e2e8f0;
  padding-top: 25px;
}
.student-hour-row label {
  flex: 0 0 auto;
  white-space: nowrap;
  font-weight: 500;
  font-size: 1.1rem;
}
.student-box {
  flex: 1;
}

.pedagogic-group {
  margin-bottom: 35px;
  display: flex;
  flex-direction: column;
  width: 100%;
}

.group-label, .field-label {
  align-self: flex-start;
  font-weight: 600;
  margin-bottom: 12px;
  color: #1e293b;
}

.read-only-box {
  width: 100%;
  box-sizing: border-box;
  padding: 15px;
  border: 1px solid #e2e8f0;
  border-radius: 12px;
  font-size: 1rem;
  background: #fdfdfd;
  color: #546e7a;
  line-height: 1.5;
  white-space: pre-wrap;
  cursor: default;
  min-height: 80px;
}

.large {
  min-height: 110px;
}

.actions-footer {
  display: flex;
  justify-content: center;
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
</style>