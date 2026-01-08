<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import AppHeader from '../App/Header.vue';
import Sidebar from '../App/Sidebar.vue';

const router = useRouter();

const handleValider = () => {
  router.push('/modif-saved');
};

const handleRetour = () => {
  router.back();
};

const competencesFinalisees = ref([
  {
    ue: "1. Réaliser",
    niveaux: [
      {
        num: "Niveau 1",
        intitule: "Développer des applications informatiques simples",
        acs: ["AC11.01: Implémenter une conception", "AC11.02: Tester les applications"]
      }
    ]
  }
]);

const currentCompetence = ref({
  ueNum: '',
  ueIntitule: '',
  niveaux: [{ intitule: '', acs: [{ num: '', intitule: '' }] }],
});

const editingIndex = ref(null);
const editedCompetence = ref({
  ueNum: '',
  ueIntitule: '',
  niveaux: []
});

const handleAddNiveau = () => {
  if (currentCompetence.value.niveaux.length >= 3) {
    alert("Limite atteinte : 3 niveaux maximum par compétence.");
    return;
  }

  const lastNiveau = currentCompetence.value.niveaux[currentCompetence.value.niveaux.length - 1];
  const hasValidAC = lastNiveau.acs.some(ac => ac.num.trim() && ac.intitule.trim());

  if (lastNiveau.intitule.trim() !== '' && hasValidAC) {
    currentCompetence.value.niveaux.push({ intitule: '', acs: [{ num: '', intitule: '' }] });
  } else {
    alert("Veuillez compléter le niveau actuel avant d'en ajouter un autre.");
  }
};

const handleAddAc = (niveauIndex) => {
  const niveau = currentCompetence.value.niveaux[niveauIndex];
  const lastAc = niveau.acs[niveau.acs.length - 1];
  if (lastAc.num.trim() !== '' && lastAc.intitule.trim() !== '') {
    niveau.acs.push({ num: '', intitule: '' });
  } else {
    alert("Complétez l'AC précédent avant d'ajouter.");
  }
};

const handleSaveNewCompetence = () => {
  if (!currentCompetence.value.ueNum.trim() || !currentCompetence.value.ueIntitule.trim()) {
    alert('Veuillez saisir le numéro et l\'intitulé de l\'UE.');
    return;
  }

  const ueComplete = `${currentCompetence.value.ueNum.trim()}. ${currentCompetence.value.ueIntitule.trim()}`;

  const niveauxResume = currentCompetence.value.niveaux
      .filter(n => n.intitule.trim() !== '')
      .map((n, index) => {
        const acsFiltres = n.acs
            .filter(ac => ac.num.trim() !== '' && ac.intitule.trim() !== '')
            .map(ac => `AC${ac.num}: ${ac.intitule.trim()}`);

        return {
          num: `Niveau ${index + 1}`,
          intitule: n.intitule.trim(),
          acs: acsFiltres
        };
      })
      .filter(n => n.acs.length > 0);

  if (niveauxResume.length === 0) {
    alert('Format invalide (Niveaux ou ACs manquants).');
    return;
  }

  competencesFinalisees.value.push({ ue: ueComplete, niveaux: niveauxResume });

  currentCompetence.value = { ueNum: '', ueIntitule: '', niveaux: [{ intitule: '', acs: [{ num: '', intitule: '' }] }] };
};

const handleDelete = (index) => {
  if(confirm("Êtes-vous sûr de vouloir supprimer cette compétence ?")) {
    competencesFinalisees.value.splice(index, 1);
    if (editingIndex.value === index) handleCancelEdit();
  }
};

const handleModif = (index) => {
  if (editingIndex.value === index) {
    handleCancelEdit();
    return;
  }

  editingIndex.value = index;
  const original = competencesFinalisees.value[index];

  const splitUE = original.ue.split('. ');
  const uNum = splitUE[0] || '';
  const uInt = splitUE.slice(1).join('. ') || '';

  const niveauxEditable = original.niveaux.map(n => {
    return {
      intitule: n.intitule,
      acs: n.acs.map(acString => {
        const parts = acString.split(': ');
        const acNumClean = parts[0].replace('AC', '');
        return { num: acNumClean, intitule: parts[1] || '' };
      })
    };
  });

  editedCompetence.value = {
    ueNum: uNum,
    ueIntitule: uInt,
    niveaux: niveauxEditable
  };
};

const handleCancelEdit = () => {
  editingIndex.value = null;
  editedCompetence.value = { ueNum: '', ueIntitule: '', niveaux: [] };
};

const saveModification = (index) => {
  const editData = editedCompetence.value;

  if (!editData.ueNum.trim() || !editData.ueIntitule.trim()) {
    alert("L'UE doit avoir un numéro et un intitulé.");
    return;
  }

  const ueComplete = `${editData.ueNum.trim()}. ${editData.ueIntitule.trim()}`;

  const niveauxResume = editData.niveaux
      .filter(n => n.intitule.trim() !== '')
      .map((n, i) => {
        const acsFormatted = n.acs
            .filter(ac => ac.num.trim() && ac.intitule.trim())
            .map(ac => `AC${ac.num}: ${ac.intitule.trim()}`);

        return {
          num: `Niveau ${i + 1}`,
          intitule: n.intitule.trim(),
          acs: acsFormatted
        };
      })
      .filter(n => n.acs.length > 0);

  if (niveauxResume.length === 0) {
    alert("Impossible de sauvegarder : données incomplètes.");
    return;
  }

  competencesFinalisees.value[index] = { ue: ueComplete, niveaux: niveauxResume };
  editingIndex.value = null;
};

const addLevelToEdit = () => {
  if (editedCompetence.value.niveaux.length >= 3) {
    alert("Limite atteinte : 3 niveaux maximum par compétence.");
    return;
  }
  editedCompetence.value.niveaux.push({ intitule: '', acs: [{ num: '', intitule: '' }] });
};

const addAcToEdit = (lvlIndex) => {
  editedCompetence.value.niveaux[lvlIndex].acs.push({ num: '', intitule: '' });
};

const removeAcFromEdit = (lvlIndex, acIndex) => {
  editedCompetence.value.niveaux[lvlIndex].acs.splice(acIndex, 1);
};
</script>

<template>
  <Sidebar/>
  <div class="page-container">
    <AppHeader title="Gestion des compétences"/>

    <main class="main-content">

      <div v-if="competencesFinalisees.length > 0" class="grid-container">

        <div
            v-for="(comp, index) in competencesFinalisees"
            :key="index"
            class="skill-card"
            :class="{ 'is-editing': editingIndex === index }"
        >

          <div v-if="editingIndex !== index" class="view-mode-container">
            <div class="card-header-view">
              <div class="icon-circle">
                <span class="comp-number">{{ index + 1 }}</span>
              </div>
              <h3>{{ comp.ue }}</h3>
            </div>

            <div class="card-body-scroll">
              <div v-for="(niveau, nIndex) in comp.niveaux" :key="'n-' + index + '-' + nIndex" class="level-block">
                <p class="level-title">{{ niveau.num }}: {{ niveau.intitule }}</p>
                <ul class="ac-list">
                  <li v-for="(ac, aIndex) in niveau.acs" :key="'a-' + index + '-' + nIndex + '-' + aIndex">
                    {{ ac }}
                  </li>
                </ul>
              </div>
            </div>

            <div class="card-actions-overlay">
              <button class="action-btn edit" @click="handleModif(index)" title="Modifier">
                <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M11 4H4a2 2 0 0 0-2 2v14a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2v-7"></path><path d="M18.5 2.5a2.121 2.121 0 0 1 3 3L12 15l-4 1 1-4 9.5-9.5z"></path></svg>
              </button>
              <button class="action-btn delete" @click="handleDelete(index)" title="Supprimer">
                <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><polyline points="3 6 5 6 21 6"></polyline><path d="M19 6v14a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2V6m3 0V4a2 2 0 0 1 2-2h4a2 2 0 0 1 2 2v2"></path></svg>
              </button>
            </div>
          </div>

          <div v-else class="edit-mode-container">
            <div class="edit-header">
              <h4>Modification</h4>
              <button class="close-icon" @click="handleCancelEdit">✕</button>
            </div>

            <div class="edit-scroll-area">
              <div class="row-inputs">
                <div class="input-group small">
                  <label>N°</label>
                  <input type="text" v-model="editedCompetence.ueNum" class="card-input compact">
                </div>
                <div class="input-group large">
                  <label>Intitulé UE</label>
                  <input type="text" v-model="editedCompetence.ueIntitule" class="card-input compact">
                </div>
              </div>

              <div v-for="(lvl, lIdx) in editedCompetence.niveaux" :key="'edit-l-'+lIdx" class="edit-level-group">
                <div class="level-edit-row">
                  <label>Niv {{ lIdx + 1 }}</label>
                  <input type="text" v-model="lvl.intitule" class="card-input compact">
                </div>

                <div v-for="(ac, acIdx) in lvl.acs" :key="'edit-ac-'+lIdx+'-'+acIdx" class="ac-edit-row">
                  <input type="text" v-model="ac.num" placeholder="N°" class="card-input tiny">
                  <input type="text" v-model="ac.intitule" placeholder="AC..." class="card-input compact">
                  <span class="remove-ac" @click="removeAcFromEdit(lIdx, acIdx)">✕</span>
                </div>

                <div class="add-mini-btn" @click="addAcToEdit(lIdx)">+ Ajouter AC</div>
              </div>

              <button v-if="editedCompetence.niveaux.length < 3" class="btn-dashed" @click="addLevelToEdit">+ Nouveau Niveau</button>
            </div>

            <button class="save-btn" @click="saveModification(index)">
              <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><polyline points="20 6 9 17 4 12"></polyline></svg>
              Sauvegarder
            </button>
          </div>

        </div>
      </div>

      <div v-else class="empty-state">
        <p>Aucune compétence ajoutée pour le moment.</p>
      </div>

      <div class="separator-container">
        <div class="separator-line"></div>
        <span class="separator-text">Nouvelle Saisie</span>
        <div class="separator-line"></div>
      </div>

      <div class="form-card" v-if="editingIndex === null">
        <div class="form-header">
          <h4>Ajouter une compétence</h4>
        </div>

        <div class="row-inputs">
          <div class="input-group small">
            <label class="field-label">N° UE</label>
            <input type="text" v-model="currentCompetence.ueNum" placeholder="Ex: 1" class="card-input">
          </div>
          <div class="input-group large">
            <label class="field-label">Intitulé UE</label>
            <input type="text" v-model="currentCompetence.ueIntitule" placeholder="Ex: Réaliser" class="card-input">
          </div>
        </div>

        <hr class="divider"/>

        <div v-for="(niveau, nIndex) in currentCompetence.niveaux" :key="'niv-' + nIndex" class="niveau-container">
          <div class="niveau-header">
            <label class="group-label">Niveau {{ nIndex + 1 }}</label>
            <div class="input-with-action">
              <input type="text" v-model="currentCompetence.niveaux[nIndex].intitule" placeholder="Intitulé du niveau..." class="card-input">
              <button v-if="nIndex === currentCompetence.niveaux.length - 1 && currentCompetence.niveaux.length < 3" @click="handleAddNiveau" class="action-icon-btn add" title="Ajouter un niveau">
                <svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><line x1="12" y1="5" x2="12" y2="19"></line><line x1="5" y1="12" x2="19" y2="12"></line></svg>
              </button>
            </div>
          </div>

          <div class="acs-container">
            <div class="ac-header">
              <span class="sub-label">ACs associés</span>
              <button
                  v-if="niveau.acs.length > 0 && niveau.acs[niveau.acs.length - 1].num.trim() && niveau.acs[niveau.acs.length - 1].intitule.trim()"
                  @click="handleAddAc(nIndex)"
                  class="text-button"
              >+ Ajouter AC</button>
            </div>

            <div v-for="(ac, aIndex) in niveau.acs" :key="'ac-' + nIndex + '-' + aIndex" class="ac-row">
              <input type="text" v-model="niveau.acs[aIndex].num" placeholder="N°" class="card-input ac-num">
              <input type="text" v-model="niveau.acs[aIndex].intitule" placeholder="Intitulé AC" class="card-input ac-name">
            </div>
          </div>
        </div>

        <button class="save-btn big-save" @click="handleSaveNewCompetence">
          <svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><polyline points="20 6 9 17 4 12"></polyline></svg>
          Valider cette compétence
        </button>
      </div>

      <div class="global-actions">
        <button @click="handleValider" class="btn-sys primary">Terminer et Enregistrer</button>
        <button @click="handleRetour" class="btn-sys secondary">Annuler</button>
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
  flex-direction: column;
  align-items: center;
  padding: 40px 20px;
  margin-top: 150px;
  max-width: 1000px;
  margin-left: auto;
  margin-right: auto;
}

.grid-container {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(320px, 1fr));
  gap: 25px;
  width: 100%;
  margin-bottom: 40px;
}

.empty-state {
  text-align: center;
  color: #999;
  font-style: italic;
  margin-bottom: 30px;
}

.skill-card {
  background: #ffffff;
  border-radius: 15px;
  padding: 20px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
  border: 1px solid rgba(0,0,0,0.05);
  display: flex;
  flex-direction: column;
  height: 380px;
  position: relative;
  transition: all 0.3s ease;
  overflow: hidden;
}

.skill-card.is-editing {
  border: 2px solid #B51621;
  transform: translateY(-5px);
  box-shadow: 0 12px 24px rgba(0, 0, 0, 0.1);
}

.view-mode-container {
  display: flex;
  flex-direction: column;
  height: 100%;
}

.card-header-view {
  display: flex;
  align-items: center;
  gap: 15px;
  margin-bottom: 15px;
  border-bottom: 1px solid #eee;
  padding-bottom: 10px;
}

.icon-circle {
  width: 40px;
  height: 40px;
  background: rgba(181, 22, 33, 0.1);
  border-radius: 50%;
  display: flex;
  justify-content: center;
  align-items: center;
  color: #B51621;
  font-weight: bold;
  flex-shrink: 0;
}

h3 {
  margin: 0;
  font-size: 18px;
  color: #333;
  line-height: 1.2;
}

.card-body-scroll {
  flex: 1;
  overflow-y: auto;
  padding-right: 5px;
  margin-bottom: 10px;
}

.card-body-scroll::-webkit-scrollbar,
.edit-scroll-area::-webkit-scrollbar { width: 4px; }
.card-body-scroll::-webkit-scrollbar-thumb,
.edit-scroll-area::-webkit-scrollbar-thumb { background: #ccc; border-radius: 4px; }

.level-block { margin-bottom: 15px; }
.level-title { font-weight: bold; font-size: 14px; color: #555; margin-bottom: 5px; }
.ac-list { padding-left: 20px; margin: 0; list-style-type: disc; color: #666; font-size: 13px; }
.ac-list li { margin-bottom: 3px; }

.card-actions-overlay {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
  margin-top: auto;
  padding-top: 10px;
  border-top: 1px dashed #eee;
}

.action-btn {
  width: 35px;
  height: 35px;
  border-radius: 50%;
  border: none;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: all 0.2s;
}
.action-btn.edit { background: #e3f2fd; color: #1976d2; }
.action-btn.delete { background: #ffebee; color: #c62828; }
.action-btn:hover { transform: scale(1.1); }


.edit-mode-container {
  display: flex;
  flex-direction: column;
  height: 100%;
}

.edit-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 10px;
}
.edit-header h4 { margin: 0; color: #B51621; font-size: 16px; }

.close-icon {
  background: none;
  border: none;
  font-size: 18px;
  cursor: pointer;
  color: #999;
}

.edit-scroll-area {
  flex: 1;
  overflow-y: auto;
  padding: 5px;
  border: 1px solid #f0f0f0;
  border-radius: 6px;
  background: #fdfdfd;
  margin-bottom: 10px;
}

.edit-level-group {
  background: #fff;
  border: 1px solid #eee;
  padding: 8px;
  border-radius: 6px;
  margin-bottom: 8px;
}

.level-edit-row {
  display: flex;
  align-items: center;
  gap: 5px;
  margin-bottom: 5px;
}
.level-edit-row label { font-size: 11px; font-weight: bold; width: 40px; }

.ac-edit-row {
  display: flex;
  gap: 5px;
  margin-bottom: 5px;
  align-items: center;
}
.remove-ac { cursor: pointer; color: #999; font-size: 12px; }
.remove-ac:hover { color: red; }

.add-mini-btn {
  font-size: 11px;
  color: #B51621;
  cursor: pointer;
  text-align: right;
  margin-top: 2px;
}
.add-mini-btn:hover { text-decoration: underline; }

.btn-dashed {
  width: 100%;
  border: 1px dashed #ccc;
  background: none;
  padding: 5px;
  color: #666;
  font-size: 12px;
  cursor: pointer;
  border-radius: 4px;
  margin-top: 5px;
}
.btn-dashed:hover { border-color: #B51621; color: #B51621; }

.card-input.compact { padding: 6px; font-size: 13px; }
.card-input.tiny { padding: 6px; font-size: 13px; width: 50px; }


.separator-container {
  display: flex;
  align-items: center;
  width: 100%;
  margin: 10px 0 30px 0;
}
.separator-line { flex: 1; height: 1px; background: #ddd; }
.separator-text { padding: 0 15px; color: #999; font-weight: 500; text-transform: uppercase; font-size: 12px; letter-spacing: 1px; }


.form-card {
  background: #ffffff;
  border-radius: 15px;
  padding: 30px;
  width: 100%;
  box-shadow: 0 8px 20px rgba(0, 0, 0, 0.08);
  border: 2px solid #ddd;
  margin-bottom: 40px;
}

.form-header h4 { margin: 0 0 20px 0; color: #333; font-size: 20px; text-align: center; }

.row-inputs { display: flex; gap: 20px; margin-bottom: 15px; }
.input-group { display: flex; flex-direction: column; gap: 5px; }
.input-group.small { width: 80px; flex-shrink: 0; }
.input-group.large { flex: 1; }

.field-label { font-size: 12px; font-weight: bold; color: #555; text-transform: uppercase; }

.card-input {
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 8px;
  font-size: 14px;
  font-family: 'Roboto', sans-serif;
  width: 100%;
  box-sizing: border-box;
}
.card-input:focus { outline: none; border-color: #B51621; }

.divider { border: 0; height: 1px; background: #eee; margin: 25px 0; }

.niveau-container {
  background: #fdfdfd;
  border: 1px solid #eee;
  border-radius: 10px;
  padding: 20px;
  margin-bottom: 20px;
}

.niveau-header { margin-bottom: 15px; }
.group-label { display: block; font-weight: bold; color: #333; margin-bottom: 8px; font-size: 14px; }
.input-with-action { display: flex; align-items: center; gap: 10px; }

.action-icon-btn {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  border: none;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: transform 0.2s;
  flex-shrink: 0;
}
.action-icon-btn.add { background: #e8f5e9; color: #2e7d32; }
.action-icon-btn:hover { transform: scale(1.1); }

.acs-container { border-left: 3px solid #ffebee; padding-left: 15px; margin-top: 15px; }
.ac-header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 10px; }
.sub-label { font-size: 12px; color: #888; font-weight: 500; }
.text-button { background: none; border: none; color: #B51621; font-weight: bold; font-size: 13px; cursor: pointer; }
.text-button:hover { text-decoration: underline; }

.ac-row { display: flex; gap: 10px; margin-bottom: 10px; }
.ac-num { width: 80px; flex-shrink: 0; }
.ac-name { flex: 1; }

.save-btn {
  width: 100%;
  padding: 10px;
  background: #B51621;
  color: white;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 8px;
  font-weight: bold;
  font-size: 14px;
  margin-top: 10px;
  transition: background 0.2s;
}
.save-btn.big-save { padding: 15px; font-size: 16px; margin-top: 20px; }
.save-btn:hover { background: #94121b; }

.global-actions { display: flex; justify-content: center; gap: 20px; margin-top: 20px; width: 100%; }
.btn-sys {
  padding: 12px 40px;
  border-radius: 30px;
  border: none;
  font-size: 16px;
  font-weight: bold;
  cursor: pointer;
  transition: transform 0.2s, box-shadow 0.2s;
}
.btn-sys:hover { transform: translateY(-2px); box-shadow: 0 4px 8px rgba(0,0,0,0.15); }
.btn-sys.primary { background-color: #B51621; color: white; }
.btn-sys.secondary { background-color: #e0e0e0; color: #333; }

@media (max-width: 768px) {
  .row-inputs, .ac-row { flex-direction: column; gap: 10px; }
  .input-group.small, .ac-num, .card-input.tiny { width: 100%; }
}
</style>