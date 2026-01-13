<script setup lang="ts">
import { useRouter } from 'vue-router';
import { mcccStore } from "@/services/mcccStore.js";
import AppHeader from '../App/Header.vue';
import { onMounted, ref } from 'vue';
import api from '@/services/api';
import Sidebar from "../App/Sidebar.vue";

const router = useRouter();
const allSaes = ref([]);

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
      saeCodes: mcccStore.saeCodes,
      acsGrouped: mcccStore.acsGrouped,
      referents: mcccStore.referents
    };

    console.log("Envoi des données au back ", payload);

    const response = await api.post('/mccc/save', payload);

    if (response.status === 200 || response.status === 201) {
      console.log("Sauvegarde réussie !");
      mcccStore.clearMcccStore();
      await router.push('/modif-saved');
    }

  } catch (error) {
    console.error("Erreur lors de la sauvegarde des MCCC :", error);
    await router.push("/error-save");
  }
};

const fetchSaes = async () => {
  try {
    const response = await api.get('/sae/saes');
    if (response.data) {
      allSaes.value = response.data;
    }
  } catch (error) {
    console.error("Erreur lors du chargement des SAE :", error);
  }
};

const getSaeDisplay = (saeItem) => {
  if (typeof saeItem === 'object' && saeItem !== null) {
    const code = saeItem.saeCode || saeItem.code || saeItem.num;
    const name = saeItem.saeName || saeItem.title || saeItem.name || '';

    if (code) {
      return `${code} - ${name}`;
    }
  }
  const found = allSaes.value.find((s) => s.num === saeItem || s.code === saeItem);

  if (found) {
    const title = found.title || found.name || '';
    return `${found.num || found.code} - ${title}`;
  }
  return String(saeItem);
};

onMounted(async () => {
  mcccStore.loadMcccStore();

  const today = new Date().toLocaleDateString('fr-FR');

  if (!mcccStore.creationDate) {
    mcccStore.creationDate = today;
  }

  mcccStore.editDate = today;

  mcccStore.registerMcccStore();

  await fetchSaes();
});

const getGroupsForUE = (ueName) => {
  if (!mcccStore.acsGrouped) return [];
  return mcccStore.acsGrouped.filter(group => group.ue === ueName);
};
</script>

<template>
  <Sidebar/>
  <AppHeader title="Récapitulatif"/>

  <main class="main-content">
    <div class="summary">
      <span class="summary-label">Ressource :</span>
      <span class="summary-code">{{ mcccStore.resourceCode }}</span>
    </div>

    <div class="grid">
      <div class="card hour-summary">
        <div class="section-header">Volume Horaire</div>
        <div class="listing-hours">
          <div class="type-of-hour" id="cm">
            <p class="title-hour">CM</p>
            <div class="hour-badge">{{ mcccStore.hoursCM }}h</div>
          </div>
          <div class="type-of-hour">
            <p class="title-hour">TD</p>
            <div class="hour-badge">{{ mcccStore.hoursTD }}h</div>
          </div>
          <div class="type-of-hour">
            <p class="title-hour">TP</p>
            <div class="hour-badge">{{ mcccStore.hoursTP }}h</div>
          </div>
          <div class="type-of-hour">
            <p class="title-hour">DS</p>
            <div class="hour-badge">{{ mcccStore.hoursDS }}h</div>
          </div>
          <div class="type-of-hour" id="ds_tp">
            <p class="title-hour">DS/TP</p>
            <div class="hour-badge">{{ mcccStore.hoursDSTP }}h</div>
          </div>
          <div class="total-separator"></div>
          <div class="total">
            <p>Total estimé</p>
            <span id="tot" class="total-number">{{ mcccStore.hoursTotal }}h</span>
          </div>
        </div>
      </div>

      <div class="card sae-summary">
        <p class="section-header">SAE(s) concernées</p>
        <div class="sae-list">
          <div class="sae-chip" v-for="saeCode in mcccStore.saeCodes" :key="saeCode">
            {{ getSaeDisplay(saeCode) }}
          </div>
          <div v-if="!mcccStore.saeCodes || mcccStore.saeCodes.length === 0" class="empty-msg">
            Aucune SAE liée
          </div>
        </div>
      </div>
    </div>

    <p class="section-main-title" id="comp">Compétences & Objectifs</p>

    <div class="big-square" v-for="(ueItem, ueIndex) in mcccStore.ue" :key="ueIndex">

      <div class="ue-header" >
        <span class="ue-prefix">{{ ueItem.includes(':') ? ueItem.split(":")[0] : 'UE' }}</span>
        <h3 class="ue-title">{{ ueItem.includes(':') ? ueItem.split(":")[1] : ueItem }}</h3>
      </div>

      <div class="table-container">
        <table class="competence-table">
          <thead>
          <tr>
            <th class="col-lvl">Niv.</th>
            <th class="col-content">Détails des Apprentissages Critiques</th>
          </tr>
          </thead>
          <tbody>

          <tr v-for="(group, grpIndex) in getGroupsForUE(ueItem)" :key="grpIndex">
            <td class="lvl-number">
              <span class="circle-number">{{ grpIndex + 1 }}</span>
            </td>

            <td class="lvl-data">
              <div v-if="group.niveau" class="lvl-block">
                <p class="lvl-label">Intitulé du niveau</p>
                <p class="lvl-desc">{{ group.niveau }}</p>
              </div>

              <div v-if="group.acs && group.acs.length > 0" class="acs-block">
                <p class="lvl-label">Apprentissages critiques</p>
                <ul class="ac-list">
                  <li v-for="(ac, acIdx) in group.acs" :key="acIdx">
                    <span class="ac-tag">AC 0{{ ac.learningNum }}</span>
                    {{ ac.learningTitle }}
                  </li>
                </ul>
              </div>
            </td>
          </tr>

          <tr v-if="getGroupsForUE(ueItem).length === 0">
            <td colspan="2" class="empty-table">Aucun niveau défini pour cette UE.</td>
          </tr>

          </tbody>
        </table>
      </div>
    </div>

    <div class="info-footer card">
      <div class="footer-item">
        <span class="footer-label">Professeur(s) référent(s) :</span>
        <span class="footer-value" v-for="(referent, index) in mcccStore.referents" :key="referent">
          {{ referent.firstname + " " + referent.lastname + (mcccStore.referents && mcccStore.referents.length == index + 1 ? "" : ", ") }}
        </span>
      </div>
      <div class="footer-item"><span class="footer-label">Version :</span> <span class="footer-value">1.0</span></div>
      <div class="footer-item"><span class="footer-label">Création :</span> <span class="footer-value">{{mcccStore.creationDate}}</span></div>
      <div class="footer-item"><span class="footer-label"> Dernière Modification :</span> <span class="footer-value">{{mcccStore.editDate}}</span></div>
    </div>

    <div class="container-btn">
      <button @click="handleValider" class="btn-sys btn-validate">Valider</button>
      <button @click="handleRetour" class="btn-sys btn-cancel">Annuler</button>
    </div>
  </main>
</template>

<style scoped>

.main-content {
  width: 90%;
  max-width: 1200px;
  margin: 180px auto 80px;
  font-family: 'Roboto', sans-serif;
  color: #333;
}

.section-header {
  font-size: 1.4rem;
  font-weight: 700;
  color: #B51621;
  margin-bottom: 20px;
  border-bottom: 2px solid #f0f0f0;
  padding-bottom: 10px;
}

.section-main-title {
  font-size: 2rem;
  font-weight: 700;
  text-align: center;
  color: #333;
  margin: 60px 0 30px;
  position: relative;
  display: inline-block;
  width: 100%;
}

.summary {
  text-align: center;
  margin-top: 250px;
  margin-bottom: 40px;
  background: white;
  padding: 20px;
  border-radius: 12px;
  box-shadow: 0 4px 15px rgba(0,0,0,0.05);
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 15px;
}
.summary-label {
  font-size: 1.5rem;
  color: #666;
}
.summary-code {
  font-size: 2rem;
  font-weight: 800;
  color: #B51621;
}

.grid {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 40px;
  margin-bottom: 40px;
}

.card {
  background: white;
  padding: 30px;
  border-radius: 16px;
  box-shadow: 0 4px 20px rgba(0,0,0,0.08);
  border: 1px solid rgba(0,0,0,0.02);
}


.listing-hours {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.type-of-hour {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 8px 0;
}

.title-hour {
  font-size: 1.2rem;
  font-weight: 500;
  margin: 0;
  color: #555;
}

.hour-badge {
  background: #f4f6f8;
  color: #333;
  padding: 8px 16px;
  border-radius: 8px;
  font-weight: 700;
  font-size: 1.1rem;
  min-width: 60px;
  text-align: center;
}

.total-separator {
  height: 1px;
  background: #e0e0e0;
  margin: 10px 0;
}

.total {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 1.3rem;
  font-weight: bold;
  color: #B51621;
}

.total p { margin: 0; }
.total-number { font-size: 1.8rem; }


.sae-list {
  display: flex;
  flex-wrap: wrap;
  gap: 15px;
  justify-content: flex-start;
}

/* Modification CSS pour gérer les titres longs */
.sae-chip {
  background-color: #ffebee;
  color: #c62828;
  padding: 10px 20px;
  border-radius: 50px;
  font-weight: 600;
  font-size: 1rem; /* Police légèrement réduite pour le texte long */
  border: 1px solid #ffcdd2;
  transition: transform 0.2s;
  /* Ajouts pour le texte long */
  white-space: normal;
  text-align: center;
  max-width: 100%;
}

.sae-chip:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 8px rgba(181, 22, 33, 0.15);
}

.empty-msg {
  color: #999;
  font-style: italic;
}

.big-square {
  background-color: #fff;
  border-radius: 16px;
  box-shadow: 0 10px 30px rgba(0,0,0,0.08);
  margin-bottom: 50px;
  overflow: hidden;
  border: none;
}

.ue-header {
  background: linear-gradient(135deg, #B51621 0%, #E92533 100%);
  padding: 20px 30px;
  display: flex;
  align-items: center;
  gap: 15px;
  color: white;
}

.ue-prefix {
  background: rgba(255,255,255,0.2);
  padding: 5px 12px;
  border-radius: 6px;
  font-weight: bold;
  text-transform: uppercase;
  font-size: 0.9rem;
}

.ue-title {
  margin: 0;
  font-size: 1.5rem;
  font-weight: 500;
}

.table-container {
  padding: 0;
}

.competence-table {
  width: 100%;
  border-collapse: collapse;
}

.competence-table th {
  background-color: #f9fafb;
  color: #444;
  padding: 18px 25px;
  text-align: left;
  font-weight: 700;
  text-transform: uppercase;
  font-size: 0.85rem;
  border-bottom: 2px solid #e5e7eb;
}

.col-lvl { width: 80px; text-align: center; }

.competence-table td {
  padding: 25px;
  border-bottom: 1px solid #e5e7eb;
  vertical-align: top;
}

.competence-table tr:last-child td {
  border-bottom: none;
}

.lvl-number {
  background-color: #fafafa;
  border-right: 1px solid #f0f0f0;
  text-align: center;
  vertical-align: middle !important;
}

.circle-number {
  display: inline-flex;
  justify-content: center;
  align-items: center;
  width: 40px;
  height: 40px;
  background: #333;
  color: white;
  border-radius: 50%;
  font-size: 1.2rem;
  font-weight: bold;
}

.lvl-block, .acs-block {
  margin-bottom: 20px;
}

.lvl-label {
  font-size: 0.85rem;
  text-transform: uppercase;
  letter-spacing: 1px;
  color: #999;
  font-weight: 700;
  margin: 0 0 8px 0;
}

.lvl-desc {
  font-size: 1.1rem;
  color: #222;
  margin: 0;
  line-height: 1.5;
}

.ac-list {
  margin: 0;
  padding: 0;
  list-style: none;
}

.ac-list li {
  margin-bottom: 8px;
  display: flex;
  align-items: baseline;
  gap: 10px;
  line-height: 1.5;
}

.ac-tag {
  color: #B51621;
  font-weight: bold;
  font-size: 0.95rem;
  white-space: nowrap;
}

.empty-table {
  text-align: center;
  padding: 40px;
  color: #888;
  font-style: italic;
}

.info-footer {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
  gap: 20px;
  margin-top: 40px;
  background: #fdfdfd;
}

.footer-item {
  display: flex;
  flex-direction: column;
  gap: 5px;
}

.footer-label {
  font-size: 0.9rem;
  color: #888;
  text-transform: uppercase;
  font-weight: 700;
}

.footer-value {
  font-size: 1.1rem;
  font-weight: 500;
  color: #333;
}

.container-btn {
  display: flex;
  justify-content: center;
  gap: 30px;
  margin-top: 60px;
  margin-bottom: 60px;
}

.btn-sys {
  padding: 16px 40px;
  border-radius: 50px;
  font-weight: 600;
  font-size: 1.1rem;
  cursor: pointer;
  transition: all 0.2s ease;
  border: none;
  box-shadow: 0 4px 10px rgba(0,0,0,0.2);
}

.btn-validate {
  background: linear-gradient(135deg, #B51621 0%, #D32F2F 100%);
  color: white;
}
.btn-validate:hover {
  transform: translateY(-2px);
  box-shadow: 0 6px 15px rgba(181, 22, 33, 0.4);
}

.btn-cancel {
  background-color: white;
  color: #555;
  border: 1px solid #ddd;
}
.btn-cancel:hover {
  background-color: #f5f5f5;
  color: #333;
  transform: translateY(-2px);
}

@media (max-width: 900px) {
  .grid { grid-template-columns: 1fr; }
  .main-content { margin-top: 150px; width: 95%; }
}
</style>