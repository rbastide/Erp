<script setup lang="js">
import {useRouter} from 'vue-router';
import {mcccStore} from "@/services/mcccStore.js";
import AppHeader from '../App/Header.vue';
import {computed, onMounted, ref} from 'vue';
import api from '@/services/api';
import Sidebar from "../App/Sidebar.vue";
import ErrorSaveModal from '../Information/ErrorSaveModal.vue';
import ModifSavedModal from '../Information/ModifSavedModal.vue';

const router = useRouter();
const showErrorModal = ref(false);
const showSuccessModal = ref(false);

// Initialisation au montage pour éviter les erreurs d'accès aux propriétés undefined
onMounted(() => {
  mcccStore.loadMcccStore();

  if(!mcccStore.creationDate){
    mcccStore.creationDate = new Date().toLocaleDateString('fr-FR');
  }
  mcccStore.editDate = new Date().toLocaleDateString('fr-FR');

});

// Fonction utilitaire pour formater les heures décimales en HhMM (ex: 1.5 -> 1h30)
const formatHour = (decimalVal) => {
  const val = decimalVal || 0;
  const h = Math.floor(val);
  const m = Math.round((val - h) * 60);

  if (m === 0) return `${h} h`; // Affiche "12 h" si pile
  return `${h}h${m.toString().padStart(2, '0')}`; // Affiche "12h30"
};

// Calcul du total formaté
const formattedTotalHours = computed(() => {
  const decimalTotal = (mcccStore.minCM || 0) +
      (mcccStore.minDS || 0) +
      (mcccStore.minTP || 0) +
      (mcccStore.minTD || 0) +
      (mcccStore.minDSTP || 0);

  return formatHour(decimalTotal);
});

const handleBack = () => router.back();

const handleValidate = async () => {
  try {
    const safeAcsGrouped = mcccStore.acsGrouped || [];

    const payload = {
      resourceID: mcccStore.resourceID,

      minCM: mcccStore.minCM,
      minTD: mcccStore.minTD,
      minTP: mcccStore.minTP,
      minDS: mcccStore.minDS,
      minDSTP: mcccStore.minDSTP,

      saeCodes: (mcccStore.saeCodes || []).map(s => ({
        saeCode: s.saeNum || s.saeCode
      })),

      acsGrouped: safeAcsGrouped.flatMap(skill =>
          (skill.allLevels || []).map((lvl, lvlIdx) => ({
            ue: `UE${skill.skillNum}`,
            levels: `Niveau ${lvlIdx + 1} - ${lvl.title}`,
            acs: (lvl.acs || []).map(ac => ({
              conceptNum: ac.learningNum,
              conceptTitle: ac.learningTitle
            }))
          }))
      ),

      referents: (mcccStore.referents || []).map(r => ({
        firstname: r.firstname || r.firstName,
        lastname: r.lastname || r.lastName
      }))
    };

    console.log("Payload envoyé:", payload);
    await api.post('/mccc/save', payload);
    showSuccessModal.value = true;

  } catch (error) {
    console.error("Erreur sauvegarde:", error);
    showErrorModal.value = true;
  }
};

const handleCloseSuccess = () => {
  showSuccessModal.value = false;
  router.push('/home-admin');
};
</script>

<template>
  <Sidebar/>
  <AppHeader title="Récapitulatif MCCC" :inline="mcccStore.resourceCode"/>

  <main class="main-content">
    <div class="content-wrapper">

      <div class="summary-card">
        <h2 class="card-title">Compétences & ACs sélectionnés</h2>
        <div class="table-responsive">
          <table class="summary-table">
            <thead>
            <tr>
              <th>UE</th>
              <th>Niveau</th>
              <th>Apprentissages Critiques</th>
            </tr>
            </thead>
            <tbody>
            <template v-for="(skill, sIdx) in (mcccStore.acsGrouped || [])" :key="sIdx">
              <template v-for="(lvl, lIdx) in skill.allLevels" :key="lIdx">
                <tr>
                  <td class="ue-cell">UE {{ skill.skillNum }} : {{ skill.ue }}</td>
                  <td class="level-cell">Niveau {{ lIdx + 1 }} : {{ lvl.title }}</td>
                  <td class="acs-cell">
                    <ul>
                      <li v-for="ac in lvl.acs" :key="ac.learningNum">
                        AC {{ ac.learningNum }} : {{ ac.learningTitle }}
                      </li>
                    </ul>
                  </td>
                </tr>
              </template>
            </template>
            <tr v-if="!mcccStore.acsGrouped || mcccStore.acsGrouped.length === 0">
              <td colspan="3" class="empty-table">Aucune compétence sélectionnée.</td>
            </tr>
            </tbody>
          </table>
        </div>
      </div>

      <div class="summary-card">
        <h2 class="card-title">SAÉs Associées</h2>
        <div v-if="mcccStore.saeCodes && mcccStore.saeCodes.length > 0" class="sae-list">
          <div v-for="sae in mcccStore.saeCodes" :key="sae.saeCode" class="sae-tag">
            {{ sae.saeCode.split('.')[0] + '.' + sae.saeCode.split('.')[1].padStart(2, '0') }} - {{ sae.saeName }}
          </div>
        </div>
        <div v-else class="empty-table">Aucune SAÉ associée.</div>
      </div>

      <div class="info-footer">
        <div class="footer-item">
          <span class="footer-label">Heures CM</span>
          <span class="footer-value">{{ formatHour(mcccStore.minCM) }}</span>
        </div>
        <div class="footer-item">
          <span class="footer-label">Heures TD</span>
          <span class="footer-value">{{ formatHour(mcccStore.minTD) }}</span>
        </div>
        <div class="footer-item">
          <span class="footer-label">Heures TP</span>
          <span class="footer-value">{{ formatHour(mcccStore.minTP) }}</span>
        </div>
        <div class="footer-item">
          <span class="footer-label">Heures DS</span>
          <span class="footer-value">{{ formatHour(mcccStore.minDS) }}</span>
        </div>
        <div class="footer-item">
          <span class="footer-label">Heures DS TP</span>
          <span class="footer-value">{{ formatHour(mcccStore.minDSTP) }}</span>
        </div>
        <div class="footer-item total-item">
          <span class="footer-label">Total Étudiant</span>
          <span class="footer-value total-value">{{ formattedTotalHours }}</span>
        </div>
      </div>

      <div class="summary-card" style="margin-top: 40px;">
        <h2 class="card-title">Enseignants Référents</h2>
        <div v-if="mcccStore.referents && mcccStore.referents.length > 0" class="sae-list">
          <div v-for="(prof, index) in mcccStore.referents" :key="index" class="sae-tag">
            {{ prof.firstname }} {{ prof.lastname }}
          </div>
        </div>
        <div v-else class="empty-table">Aucun enseignant associé.</div>
      </div>

      <div class="info-footer">
        <div class="footer-item">
          <span class="footer-label">Créé le</span>
          <span class="footer-value">{{ mcccStore.creationDate || '--/--/----' }}</span>
        </div>
        <div class="footer-item">
          <span class="footer-label">Dernière modification</span>
          <span class="footer-value">{{ mcccStore.editDate || '--/--/----' }}</span>
        </div>
      </div>



      <div class="container-btn">
        <button @click="handleValidate" class="btn-sys primary">Valider et Sauvegarder</button>
        <button @click="handleBack" class="btn-sys secondary">Retour</button>
      </div>

    </div>

    <ErrorSaveModal
        v-if="showErrorModal"
        @close="showErrorModal = false"
    />

    <ModifSavedModal
        v-if="showSuccessModal"
        @close="handleCloseSuccess"
    />
  </main>
</template>

<style scoped>
.main-content {
  padding-top: 10%;
  min-height: 100vh;
  background-color: #f8f9fa;
  font-family: 'Roboto', sans-serif;
  padding-bottom: 80px;
}

.content-wrapper {
  max-width: 1100px;
  margin: 0 auto;
  padding: 50px 20px;
}

.summary-card {
  background: white;
  border-radius: 12px;
  padding: 25px;
  box-shadow: 0 4px 15px rgba(0,0,0,0.05);
  margin-bottom: 30px;
  border: 1px solid #eee;
}

.card-title {
  font-size: 1.2rem;
  color: #B51621;
  margin-bottom: 20px;
  border-left: 4px solid #B51621;
  padding-left: 10px;
  font-weight: 700;
}

.table-responsive {
  overflow-x: auto;
}

.summary-table {
  width: 100%;
  border-collapse: collapse;
}

.summary-table th, .summary-table td {
  padding: 12px 15px;
  text-align: left;
  border-bottom: 1px solid #f0f0f0;
}

.summary-table th {
  background-color: #fafafa;
  color: #666;
  font-weight: 700;
  text-transform: uppercase;
  font-size: 0.85rem;
}

.ue-cell {
  font-weight: 700;
  color: #333;
  width: 20%;
}

.level-cell {
  color: #555;
  width: 25%;
}

.acs-cell ul {
  padding-left: 20px;
  margin: 0;
}

.acs-cell li {
  margin-bottom: 4px;
  color: #444;
  font-size: 0.9rem;
}

.sae-list {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
}

.sae-tag {
  background: #fff5f5;
  padding: 8px 15px;
  border-radius: 20px;
  border: 1px solid #ffccd0;
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
  background: white;
  padding: 25px;
  border-radius: 12px;
  box-shadow: 0 4px 15px rgba(0,0,0,0.05);
  border: 1px solid #eee;
}

.footer-item {
  display: flex;
  flex-direction: column;
  gap: 5px;
  min-width: 100px;
}

.footer-label {
  font-size: 0.8rem;
  color: #888;
  text-transform: uppercase;
  font-weight: 700;
}

.footer-value {
  font-size: 1.2rem;
  font-weight: 700;
  color: #333;
}

.total-item .footer-label {
  color: #B51621;
}

.total-value {
  color: #B51621;
  font-size: 1.4rem;
}

.container-btn {
  display: flex;
  justify-content: center;
  gap: 30px;
  margin-top: 60px;
  margin-bottom: 60px;
}

.btn-sys {
  padding: 14px 40px;
  border-radius: 50px;
  font-size: 1rem;
  font-weight: 700;
  cursor: pointer;
  border: none;
  transition: all 0.2s;
}

.btn-sys.primary {
  background: #B51621;
  color: white;
}

.btn-sys.primary:hover {
  background: #96121b;
  transform: translateY(-2px);
  box-shadow: 0 4px 10px rgba(181, 22, 33, 0.3);
}

.btn-sys.secondary {
  background: white;
  color: #555;
  border: 2px solid #e0e0e0;
}

.btn-sys.secondary:hover {
  background: #f5f5f5;
  border-color: #d0d0d0;
}
</style>