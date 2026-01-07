<script setup>
import {computed, onMounted, ref} from 'vue';
import {useRouter} from 'vue-router';
import AppHeader from '../App/Header.vue';
import Sidebar from '../App/Sidebar.vue';
import {mcccStore} from "@/services/mcccStore.js";

const router = useRouter();

onMounted(() => {
  mcccStore.loadMcccStore();
  if (!Array.isArray(mcccStore.ue)) mcccStore.ue = [];
  if (!Array.isArray(mcccStore.niveaux)) mcccStore.niveaux = [];
  if (!Array.isArray(mcccStore.acs)) mcccStore.acs = [];
  if (!Array.isArray(mcccStore.acsGrouped)) mcccStore.acsGrouped = [];
});

const handleValider = () => {
  mcccStore.registerMcccStore();
  router.push('/mccc-menu');
};
const handleRetour = () => router.back();

const groupedCompetences = computed(() => {
  const groups = {};

  if (!mcccStore.acsGrouped) return {};

  mcccStore.acsGrouped.forEach(item => {
    if (item.resourceCode === mcccStore.resourceCode) {
      if (!groups[item.ue]) {
        groups[item.ue] = [];
      }
      groups[item.ue].push({
        niveau: item.niveau,
        acs: item.acs
      });
    }
  });

  return groups;
});

const currentCompetence = ref({
  ue: '',
  niveaux: [{niveau: '', acs: ['']}],
});

const handleAddNiveau = () => {
  const lastNiveauIndex = currentCompetence.value.niveaux.length - 1;
  const lastNiveau = currentCompetence.value.niveaux[lastNiveauIndex];
  const hasValidAc = lastNiveau.acs.some(ac => ac !== '');

  if (lastNiveau.niveau !== '' && hasValidAc) {
    currentCompetence.value.niveaux.push({niveau: '', acs: ['']});
  } else {
    alert("Veuillez choisir le niveau actuel ET au moins un AC associé.");
  }
};

const handleAddAc = (niveauIndex) => {
  const niveau = currentCompetence.value.niveaux[niveauIndex];
  if (niveau.acs[niveau.acs.length - 1] !== '') {
    niveau.acs.push('');
  } else {
    alert("Veuillez choisir l'AC actuel avant d'en ajouter un autre.");
  }
};

const handleSaveCompetence = () => {
  if (!currentCompetence.value.ue) {
    alert('Veuillez sélectionner une UE.');
    return;
  }

  const parseAcString = (acString) => {
    const match = acString.match(/\.(\d+)\s*:\s*(.*)/);
    if (match) {
      return {
        learningNum: parseInt(match[1], 10),
        learningTitle: match[2].trim()
      };
    }
    return {learningNum: 0, learningTitle: acString};
  };

  const niveauxValides = currentCompetence.value.niveaux
      .filter(n => n.niveau !== '')
      .map(n => ({
        niveau: n.niveau,
        acs: n.acs.filter(ac => ac !== '').map(parseAcString)
      }))
      .filter(n => n.acs.length > 0);

  if (niveauxValides.length === 0) {
    alert('Veuillez sélectionner au moins un Niveau avec au moins un AC associé.');
    return;
  }

  if (!mcccStore.ue.includes(currentCompetence.value.ue)) {
    mcccStore.ue.push(currentCompetence.value.ue);
  }

  niveauxValides.forEach(nouveau => {
    const groupeExistant = mcccStore.acsGrouped.find(g =>
        g.resourceCode === mcccStore.resourceCode &&
        g.ue === currentCompetence.value.ue &&
        g.niveau === nouveau.niveau
    );

    if (groupeExistant) {
      nouveau.acs.forEach(newAc => {
        if (!groupeExistant.acs.some(existingAc => existingAc.learningNum === newAc.learningNum)) {
          groupeExistant.acs.push(newAc);
        }
      });
    } else {
      mcccStore.acsGrouped.push({
        resourceCode: mcccStore.resourceCode,
        ue: currentCompetence.value.ue,
        niveau: nouveau.niveau,
        acs: [...nouveau.acs]
      });
    }
  });

  mcccStore.registerMcccStore();

  currentCompetence.value = {
    ue: '',
    niveaux: [{niveau: '', acs: ['']}],
  };
};
</script>

<template>
  <Sidebar/>
  <AppHeader :title="'Compétences ' + (mcccStore.resourceCode || '')"/>

  <main class="main-div">
    <div class="description">Veuillez saisir la/les compétences pour cette ressource :</div>

    <div class="container-global">

      <div v-if="Object.keys(groupedCompetences).length > 0" class="skill-card skill-card-resume">
        <p id="card-title">Récapitulatif des saisies ({{ mcccStore.resourceCode }})</p>

        <div class="resume-item">
          <strong>UEs ajoutées :</strong>
          <ul class="resume-list">
            <li v-for="(ueName, i) in Object.keys(groupedCompetences)" :key="'ue-'+i">{{ ueName }}</li>
          </ul>
        </div>

        <div v-for="(groups, ueName) in groupedCompetences" :key="ueName" class="ue-block">
          <div class="resume-item">
            <strong style="font-size: 1.1em; color: #B51621; text-decoration: underline;">{{ ueName }}</strong>

            <ul class="resume-list main-level">
              <li v-for="(group, idx) in groups" :key="idx">
                <span style="font-weight: bold;">{{ group.niveau }}</span>
                <ul class="resume-list sub-level">
                  <li v-for="(ac, acIdx) in group.acs" :key="acIdx">
                    AC 0{{ ac.learningNum }} : {{ ac.learningTitle }}
                  </li>
                </ul>
              </li>
            </ul>
          </div>
          <hr class="ue-separator" v-if="Object.keys(groupedCompetences).length > 1">
        </div>
      </div>

      <div class="skill-card">
        <p id="card-title">Ajouter une compétence</p>

        <div class="form-item container-ue">
          <label for="ue">Veuillez choisir une UE associée à {{ mcccStore.resourceCode }} </label>
          <select name="ue" id="ue" v-model="currentCompetence.ue">
            <option value="" selected disabled>Rien de sélectionné</option>
            <option value="UE 1 : Réaliser">UE 1 : Réaliser</option>
            <option value="UE 2 : Optimiser">UE 2 : Optimiser</option>
            <option value="UE 3 : Administrer">UE 3 : Administrer</option>
            <option value="UE 4 : Gérer">UE 4 : Gérer</option>
            <option value="UE 5 : Conduire">UE 5 : Conduire</option>
            <option value="UE 6 : Collaborer">UE 6 : Collaborer</option>
          </select>
        </div>

        <hr class="separator"/>
        <p class="section-title">Définir les Niveaux et leurs ACs associés</p>

        <div v-for="(niveauGroup, nIndex) in currentCompetence.niveaux" :key="'niv-' + nIndex" class="form-item container-niv-group">
          <div class="niveau-group">
            <label :for="'niv-' + nIndex">Niveau {{ nIndex + 1 }} attendu </label>
            <div class="input-with-plus">
              <select :name="'niv-select-' + nIndex" :id="'niv-select-' + nIndex" v-model="niveauGroup.niveau">
                <option value="" selected disabled>Rien de sélectionné</option>
                <option value="Niveau 1 : Développement">Niveau 1 : Développement</option>
                <option value="Niveau 2 : Optimisation">Niveau 2 : Optimisation</option>
                <option value="Niveau 3 : Administration">Niveau 3 : Administration</option>
              </select>
              <svg v-if="nIndex === currentCompetence.niveaux.length - 1" @click="handleAddNiveau" width="35" height="35" viewBox="0 0 57 52" xmlns="http://www.w3.org/2000/svg" class="svg-active">
                <path d="M28.5 17.3333V34.6667M19 26H38M11.875 6.5H45.125C47.7484 6.5 49.875 8.4401 49.875 10.8333V41.1667C49.875 43.5599 47.7484 45.5 45.125 45.5H11.875C9.25165 45.5 7.125 43.5599 7.125 41.1667V10.8333C7.125 8.4401 9.25165 6.5 11.875 6.5Z" stroke-width="4" stroke-linecap="round" stroke-linejoin="round" fill="none"/>
              </svg>
            </div>

            <div class="nested-acs">
              <div class="ac-title-with-plus">
                <p class="nested-title">ACs pour {{ niveauGroup.niveau || `Niveau ${nIndex + 1}` }}</p>
              </div>
              <div v-for="(_, aIndex) in niveauGroup.acs" :key="'ac-' + nIndex + '-' + aIndex"
                   class="form-item container-ac ac-input-group">
                <label :for="'ac-select-' + nIndex + '-' + aIndex" class="nested-label">AC {{ aIndex + 1 }} associé</label>
                <div class="select-with-plus">
                  <select :name="'ac-select-' + nIndex + '-' + aIndex" :id="'ac-select-' + nIndex + '-' + aIndex" v-model="niveauGroup.acs[aIndex]">
                    <option value="" selected disabled>Rien de sélectionné</option>
                    <option value="AC 11.01 : Implémenter">AC 11.01 : Implémenter</option>
                    <option value="AC 11.02 : Concevoir">AC 11.02 : Concevoir</option>
                    <option value="AC 11.03 : Tester">AC 11.03 : Tester</option>
                    <option value="AC 11.04 : Déployer">AC 11.04 : Déployer</option>
                  </select>
                  <svg v-if="aIndex === niveauGroup.acs.length - 1" @click="handleAddAc(nIndex)" width="35" height="35" viewBox="0 0 57 52" xmlns="http://www.w3.org/2000/svg" class="svg-active">
                    <path d="M28.5 17.3333V34.6667M19 26H38M11.875 6.5H45.125C47.7484 6.5 49.875 8.4401 49.875 10.8333V41.1667C49.875 43.5599 47.7484 45.5 45.125 45.5H11.875C9.25165 45.5 7.125 43.5599 7.125 41.1667V10.8333C7.125 8.4401 9.25165 6.5 11.875 6.5Z" stroke-width="4" stroke-linecap="round" stroke-linejoin="round" fill="none"/>
                  </svg>
                </div>
              </div>
            </div>
          </div>
        </div>

        <button @click="handleSaveCompetence" class="card-ok" title="Valider cette compétence">
          <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="24" height="24" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" fill="none">
            <rect x="3" y="3" width="18" height="18" rx="4" ry="4"/>
            <polyline points="9 12 12 15 15 9"/>
          </svg>
        </button>
      </div>
      <div class="container-btn">
        <div @click="handleValider" class="btn-sys">Valider les compétences</div>
        <div @click="handleRetour" class="btn-sys">Annuler</div>
      </div>
    </div>
  </main>
</template>

<style scoped>
.container-card img {
  position: absolute;
  width: 127px;
  height: 127px;
  left: 64px;
  top: 22.5px;
}

.container-card p {
  position: absolute;
  width: 723px;
  height: 124px;
  left: 209px;
  top: 24px;
  font-family: 'Roboto', sans-serif;
  font-style: normal;
  font-weight: 900;
  font-size: 56px;
  line-height: 110%;
  display: flex;
  align-items: center;
  letter-spacing: -0.03em;
  color: #FFFFFF;
}

.main-div {
  font-family: 'Roboto', sans-serif;
  min-height: 100vh;
  padding-top: 172px;
  box-sizing: border-box;
  background-color: #f7f7f7;
}

.container-global {
  position: relative;
  width: 450px;
  margin: 0 auto;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;
  padding-bottom: 50px;
}

.description {
  font-family: 'Roboto', sans-serif;
  font-style: normal;
  font-weight: 400;
  font-size: 32px;
  color: #B51621;
  margin: 40px;
  text-align: center;
}

#card-title {
  text-align: center;
  color: #B51621;
  font-size: 32px;
  font-weight: bold;
  margin: 20px 0;
  border-bottom: 2px solid #eee;
  padding-bottom: 10px;
}

.skill-card {
  width: 100%;
  background-color: #FFFFFF;
  padding: 2.5rem;
  border-radius: 8px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
  border: 1px solid #dcdcdc;
  margin-bottom: 30px;
}

.skill-card-resume {
  background-color: #ffeaea;
  border: 1px solid #B51621;
}

.skill-card-resume #card-title {
  color: #B51621;
  border-color: #B51621;
}

.resume-item {
  font-size: 18px;
  margin-bottom: 15px;
  padding-left: 10px;
}

.resume-list {
  list-style-type: disc;
  padding-left: 20px;
  margin-top: 5px;
}

.resume-list li {
  margin-bottom: 4px;
  line-height: 1.4;
}

.resume-list.sub-level {
  list-style-type: circle;
  padding-left: 30px;
  margin-top: 5px;
}

.resume-list.main-level li {
  margin-bottom: 10px;
}

.ue-separator {
  border: 0;
  border-top: 1px dashed #B51621;
  margin: 20px 0;
  opacity: 0.5;
}

.form-item {
  margin-bottom: 1.5rem;
}

.form-item label {
  display: block;
  margin-bottom: 0.5rem;
  font-weight: bold;
  color: black;
  font-size: 20px;
}

.select-with-plus {
  display: flex;
  align-items: center;
  gap: 10px;
}

.form-item select {
  appearance: menulist-button;
  width: 100%;
  max-width: 280px;
  padding: 12px 16px;
  border-radius: 10px;
  background: rgba(255, 255, 255, 0.4);
  border: 2px solid rgb(0, 0, 0);
  font-size: 16px;
}

.select-with-plus svg,
.input-with-plus svg {
  stroke: black;
  margin: 5px;
  transition: stroke 0.2s, transform 0.2s;
  cursor: pointer;
}

.select-with-plus .svg-active:hover,
.input-with-plus .svg-active:hover {
  stroke: green;
  transform: scale(1.1);
}

.section-title {
  font-size: 24px;
  font-weight: 700;
  color: #444;
  margin: 25px 0 15px 0;
  text-align: center;
}

.separator {
  border: 0;
  height: 1px;
  background-image: linear-gradient(to right, rgba(0, 0, 0, 0), rgba(181, 22, 33, 0.5), rgba(0, 0, 0, 0));
  margin: 30px 0;
}

.niveau-group {
  padding: 15px;
  border: 1px solid #ddd;
  border-radius: 8px;
  background-color: #fcfcfc;
  margin-bottom: 25px;
}

.nested-acs {
  padding: 15px;
  border-left: 3px solid #B51621;
  margin-top: 15px;
  background-color: #fff5f5;
}

.ac-title-with-plus {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 10px;
}

.nested-title {
  font-weight: 700 !important;
  color: #B51621;
  font-size: 18px !important;
  margin: 0 !important;
}

.nested-label {
  font-size: 16px !important;
  font-weight: 500 !important;
}

.ac-input-group {
  border-top: 1px dashed #ccc;
  padding-top: 15px;
  margin-bottom: 20px;
}

.nested-acs .ac-input-group:first-child {
  border-top: none;
  padding-top: 0;
  margin-bottom: 20px;
}

.card-ok {
  position: relative;
  left: 50%;
  transform: translateX(-50%);
  background: none;
  border: none;
  cursor: pointer;
  padding: 0;
  margin: 20px;
  transition: transform 0.2s;
}

.card-ok svg {
  height: 50px;
  width: 50px;
}

.card-ok:hover {
  color: green;
}

/* Boutons Système (Bas de page) */
.container-btn {
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: center;
}

.btn-sys {
  width: 150px;
  padding: 13px;
  border: none;
  text-align: center;
  border-radius: 4px;
  background-color: #B51621;
  color: #FFFFFF;
  font-size: 1rem;
  font-weight: bold;
  cursor: pointer;
  font-family: 'Roboto', sans-serif;
  transition: background-color 0.2s ease;
  position: relative;
  margin: 1%;
}

.btn-sys:hover {
  background: #999999;
  transform: translateY(-4px);
  cursor: pointer;
}
</style>