<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import AppHeader from './Header.vue';

const router = useRouter();

const handleDeconnexion = () => {
  router.push('/deconnexion');
};

const handleAide = () => {
  router.push('/aide');
};

const handleValider = () => {
  router.push('/mccc-menu');
};

const handleRetour = () => {
  router.push('/mccc-menu');
}

const currentCompetence = ref({
  ue: '',
  niveaux: [{
    niveau: '',
    acs: ['']
  }],
});

const competencesFinalisees = ref([]);

const handleAddNiveau = () => {
  const lastNiveauIndex = currentCompetence.value.niveaux.length - 1;
  const lastNiveau = currentCompetence.value.niveaux[lastNiveauIndex];

  const hasValidAc = lastNiveau.acs.some(ac => ac !== '');

  if (lastNiveau.niveau !== '' && hasValidAc) {
    currentCompetence.value.niveaux.push({
      niveau: '',
      acs: ['']
    });
  } else {
    alert("Veuillez choisir le niveau actuel ET au moins un AC associé avant d'en ajouter un autre.");
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

  const niveauxFinalises = currentCompetence.value.niveaux
      .filter(n => n.niveau !== '')
      .map(n => ({
        niveau: n.niveau,
        acs: n.acs.filter(ac => ac !== '')
      }))
      .filter(n => n.acs.length > 0);

  if (niveauxFinalises.length === 0) {
    alert('Veuillez sélectionner au moins un Niveau avec au moins un AC associé.');
    return;
  }

  const acsForSimpleResume = niveauxFinalises.flatMap(n => n.acs);

  competencesFinalisees.value.push({
    ue: currentCompetence.value.ue,
    niveaux: niveauxFinalises.map(n => n.niveau),
    acs: acsForSimpleResume,
    acsGrouped: niveauxFinalises.map(n => ({ niveau: n.niveau, acs: n.acs }))
  });

  currentCompetence.value = {
    ue: '',
    niveaux: [{ niveau: '', acs: [''] }],
  };

  console.log('Compétence validée et ajoutée :', {
    ue: currentCompetence.value.ue,
    niveaux: niveauxFinalises.map(n => n.niveau),
    acsGrouped: niveauxFinalises.map(n => ({ niveau: n.niveau, acs: n.acs }))
  });
};

</script>

<template>
  <AppHeader title="Compétences RX.XX"/>
  <main class="main-div">
    <div class="description">Veuillez saisir la/les compétences pour cette ressource :</div>

    <div class="container-global">

      <div v-for="(comp, index) in competencesFinalisees" :key="index" class="skill-card skill-card-resume">
        <p id="card-title">Compétence {{ index + 1 }}</p>
        <div class="resume-item"><strong>UE :</strong> {{ comp.ue }}</div>

        <div class="resume-item">
          <strong>Structure des Niveaux/ACs :</strong>
          <ul class="resume-list main-level">
            <li v-for="(niveauGroup, nIndex) in comp.acsGrouped" :key="'n-g-' + index + '-' + nIndex">
              <p style="font-weight: bold; margin-bottom: 5px;">Niveau : {{ niveauGroup.niveau }}</p>

              <ul class="resume-list sub-level">
                <li v-for="(ac, aIndex) in niveauGroup.acs" :key="'a-' + index + '-' + nIndex + '-' + aIndex">
                  {{ ac }}
                </li>
              </ul>
            </li>
          </ul>
        </div>

        <div class="resume-item"><strong>Niveaux sélectionnés :</strong> {{ comp.niveaux.join(' / ') }}</div>
      </div>

      <div class="skill-card">
        <p id="card-title">Ajouter une compétence</p>

        <div class="form-item container-ue">
          <label for="ue">Veuillez choisir une UE associée à RX.XX </label>
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
                <option value="Niveau 1">Niveau 1 : Lorem Ipsum</option>
                <option value="Niveau 2">Niveau 2 : Lorem Ipsum</option>
                <option value="Niveau 3">Niveau 3 : Lorem Ipsum</option>
              </select>

              <svg
                  v-if="nIndex === currentCompetence.niveaux.length - 1"
                  @click="handleAddNiveau"
                  width="35" height="35" viewBox="0 0 57 52" fill="none" xmlns="http://www.w3.org/2000/svg" class="svg-active">
                <path d="M28.5 17.3333V34.6667M19 26H38M11.875 6.5H45.125C47.7484 6.5 49.875 8.4401 49.875 10.8333V41.1667C49.875 43.5599 47.7484 45.5 45.125 45.5H11.875C9.25165 45.5 7.125 43.5599 7.125 41.1667V10.8333C7.125 8.4401 9.25165 6.5 11.875 6.5Z"  stroke-width="4" stroke-linecap="round" stroke-linejoin="round"/>
              </svg>
            </div>

            <div class="nested-acs">

              <div class="ac-title-with-plus">
                <p class="nested-title">ACs pour {{ niveauGroup.niveau || `Niveau ${nIndex + 1}` }} :</p>
              </div>

              <div v-for="(ac, aIndex) in niveauGroup.acs" :key="'ac-' + nIndex + '-' + aIndex" class="form-item container-ac ac-input-group">
                <label :for="'ac-select-' + nIndex + '-' + aIndex" class="nested-label">AC {{ aIndex + 1 }} associé</label>

                <div class="select-with-plus">
                  <select :name="'ac-select-' + nIndex + '-' + aIndex" :id="'ac-select-' + nIndex + '-' + aIndex" v-model="niveauGroup.acs[aIndex]">
                    <option value="" selected disabled>Rien de sélectionné</option>
                    <option value="AC 11.01">AC 11.01 : Lorem Ipsum</option>
                    <option value="AC 11.02">AC 11.02 : Lorem Ipsum</option>
                    <option value="AC 11.03">AC 11.03 : Lorem Ipsum</option>
                    <option value="AC 11.04">AC 11.04 : Lorem Ipsum</option>
                  </select>

                  <svg
                      v-if="aIndex === niveauGroup.acs.length - 1"
                      @click="handleAddAc(nIndex)"
                      width="35" height="35" viewBox="0 0 57 52" fill="none" xmlns="http://www.w3.org/2000/svg" class="svg-active">
                    <path d="M28.5 17.3333V34.6667M19 26H38M11.875 6.5H45.125C47.7484 6.5 49.875 8.4401 49.875 10.8333V41.1667C49.875 43.5599 47.7484 45.5 45.125 45.5H11.875C9.25165 45.5 7.125 43.5599 7.125 41.1667V10.8333C7.125 8.4401 9.25165 6.5 11.875 6.5Z" stroke-width="4" stroke-linecap="round" stroke-linejoin="round"/>
                  </svg>
                </div>
              </div>
            </div>
          </div>
        </div>


        <button @click="handleSaveCompetence" class="card-ok" title="Valider cette compétence">
          <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="24" height="24" fill="none"
               stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
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
.main-div{
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

#card-title{
  text-align: center;
  color: #B51621;
  font-size: 32px;
  font-weight: bold;
  margin: 20px 0;
  border-bottom: 2px solid #eee;
  padding-bottom: 10px;
}

.skill-card{
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
  margin-bottom: 10px;
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

.form-item{
  margin-bottom: 1.5rem;
}

.form-item label{
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

.form-item select{
  appearance: menulist-button;
  width: 100%;
  max-width: 280px;
  padding: 12px 16px;
  border-radius: 10px;
  background: rgba(255,255,255,0.4);
  border: 2px solid rgb(0,0,0);
  font-size: 16px;
}

.select-with-plus svg {
  stroke: black;
  transition: stroke 0.2s, transform 0.2s;
  flex-shrink: 0;
}
.input-with-plus svg {
  stroke: black;
  margin: 5px;
  transition: stroke 0.2s, transform 0.2s;
}

.select-with-plus .svg-active:hover, .input-with-plus .svg-active:hover {
  cursor: pointer;
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
  padding: 15px 15px 20px 15px;
  border: 1px solid #ddd;
  border-radius: 8px;
  background-color: #fcfcfc;
  margin-bottom: 25px;
  position: relative;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.03);
}

.nested-acs {
  padding: 15px 15px 5px 15px;
  border-left: 3px solid #B51621;
  margin-top: 15px;
  background-color: #fff5f5;
  border-radius: 0 0 4px 4px;
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
  transform: translateX(-50%) scale(1.1);
  color: green;
}

.container-btn{
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: center;
}

.btn-sys{
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
  margin : 1%;
}

.btn-sys:hover{
  background: #999999;
  transform: translateY(-4px);
  cursor: pointer;
}
</style>