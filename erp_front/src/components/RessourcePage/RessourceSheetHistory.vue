<script setup lang="ts">
import { useRouter } from 'vue-router'
import AppHeader from '../App/Header.vue'

const router = useRouter()

const handleFermer = () => {router.push('/history')};
</script>

<template>
  <AppHeader title="Récapitulatif Fiche Ressource" inline="RX.XX 00/00/0000" />

  <main class="main-content">
    <div class="container">

      <section class="form-card">
        <h2 class="card-title">Récapitulatif des heures</h2>

        <div class="hours-grid-container">
          <div class="hours-row">
            <div class="hour-item">
              <label>CM</label>
              <div class="value-box">0</div>
            </div>
            <div class="hour-item">
              <label>TD</label>
              <div class="value-box">0</div>
            </div>
            <div class="hour-item">
              <label>DS</label>
              <div class="value-box">0</div>
            </div>
          </div>

          <div class="hours-row">
            <div class="hour-item">
              <label>TP</label>
              <div class="value-box">0</div>
            </div>
            <div class="hour-item">
              <label>DS/TP</label>
              <div class="value-box">0</div>
            </div>
            <div class="hour-item total">
              <label>Total Global</label>
              <div class="value-box highlight">0</div>
            </div>
          </div>
        </div>

        <div class="student-ratio">
          <label>Heures par étudiant :</label>
          <span class="ratio-badge">0 h</span>
        </div>
      </section>

      <section class="form-card">
        <h2 class="card-title">Contenu pédagogique</h2>
        <div class="text-grid">
          <div class="text-group">
            <label>Cours Magistraux (CM)</label>
            <div class="read-only-box">Contenu des CM...</div>
          </div>
          <div class="text-group">
            <label>Travaux Dirigés (TD)</label>
            <div class="read-only-box">Contenu des TD...</div>
          </div>
          <div class="text-group">
            <label>Travaux Pratiques (TP)</label>
            <div class="read-only-box">Contenu des TP...</div>
          </div>
          <div class="text-group">
            <label>Devoirs Surveillés (DS)</label>
            <div class="read-only-box">Modalités d'évaluation...</div>
          </div>
        </div>
      </section>

      <section class="form-card accent">
        <h2 class="card-title">Feedbacks & Évolutions</h2>

        <div class="full-text-group">
          <label>Retour pédagogique des professeurs</label>
          <div class="read-only-box large">Points forts, points faibles du semestre...</div>
        </div>

        <div class="full-text-group">
          <label>Retour des étudiants</label>
          <div class="read-only-box large">Synthèse des retours...</div>
        </div>

        <div class="full-text-group">
          <label>Améliorations à apporter</label>
          <div class="read-only-box large">Modifications prévues...</div>
        </div>
      </section>

      <div class="sticky-actions">
        <button @click="handleFermer" class="btn btn-primary">Fermer la fiche</button>
      </div>

    </div>
  </main>
</template>

<style scoped>
/* STRUCTURE */
.main-content {
  padding-top: 220px;
  padding-bottom: 50px;
  background-color: #f4f7f9;
  min-height: 100vh;
  font-family: 'Roboto', sans-serif;
}

.container {
  max-width: 1000px;
  margin: 0 auto;
}

/* CARDS */
.form-card {
  background: white;
  border-radius: 16px;
  padding: 30px;
  margin-bottom: 30px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.05);
  border: 1px solid #e1e8ed;
}

.card-title {
  color: #B51621;
  font-size: 1.5rem;
  font-weight: 700;
  margin-bottom: 25px;
  border-left: 5px solid #B51621;
  padding-left: 15px;
}

/* GRILLE D'HEURES */
.hours-grid-container {
  display: flex;
  flex-direction: column;
  gap: 20px;
  margin-bottom: 25px;
}

.hours-row {
  display: flex;
  justify-content: space-between;
  gap: 15px;
}

.hour-item {
  flex: 1;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.hour-item label {
  font-weight: 700;
  color: #546e7a;
  margin-bottom: 8px;
  font-size: 0.9rem;
}

.value-box {
  width: 100%;
  background: #f8fafb;
  border: 2px solid #cfd8dc;
  border-radius: 10px;
  padding: 15px;
  text-align: center;
  font-size: 1.4rem;
  font-weight: 800;
  color: #263238;
}

.highlight {
  border-color: #B51621;
  color: #B51621;
  background: #fff5f5;
}

.student-ratio {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 15px;
  padding-top: 20px;
  border-top: 1px dashed #cfd8dc;
}

.ratio-badge {
  background: #263238;
  color: white;
  padding: 8px 20px;
  border-radius: 20px;
  font-weight: 700;
}

/* AFFICHAGE TEXTE (READ-ONLY) */
.text-grid {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 20px;
}

.text-group, .full-text-group {
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.text-group label, .full-text-group label {
  font-weight: 600;
  color: #455a64;
  font-size: 1rem;
}

.read-only-box {
  width: 100%;
  min-height: 100px;
  border: 2px solid #e1e8ed;
  border-radius: 10px;
  padding: 12px;
  font-size: 1rem;
  background: #fdfdfd;
  color: #546e7a;
  line-height: 1.5;
  white-space: pre-wrap; /* Conserve les sauts de ligne */
  cursor: default;
}

.large {
  min-height: 80px;
  margin-bottom: 10px;
}

/* BOUTONS */
.sticky-actions {
  display: flex;
  justify-content: center;
  gap: 20px;
  margin-top: 40px;
}

.btn {
  padding: 15px 40px;
  border-radius: 12px;
  font-weight: 700;
  font-size: 1.1rem;
  cursor: pointer;
}

.btn-primary {
  background: #B51621;
  color: white;
  border: none;
}

.btn-outline {
  background: transparent;
  color: #B51621;
  border: 2px solid #B51621;
}

/* RESPONSIVE */
@media (max-width: 768px) {
  .hours-row { flex-wrap: wrap; }
  .text-grid { grid-template-columns: 1fr; }
  .hour-item { min-width: 45%; }
}
</style>