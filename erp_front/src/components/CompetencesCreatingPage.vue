<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';

const router = useRouter();

const handleDeconnexion = () => {
  router.push('/deconnexion');
};

const handleAide = () => {
  router.push('/aide');
};

const handleValider = () => {
  router.push('/modif-saved');
};

const handleRetour = () => {
  router.push('/home-admin');
};

const currentCompetence = ref({
  ueNum: '',
  ueIntitule: '',
  niveaux: [{
    intitule: '',
    acs: [{ num: '', intitule: '' }]
  }],
});

const competencesFinalisees = ref([]);

const generateNiveauNum = (index) => {
  return `Niveau ${index + 1}`;
};

const handleAddNiveau = () => {
  const lastNiveauIndex = currentCompetence.value.niveaux.length - 1;
  const lastNiveau = currentCompetence.value.niveaux[lastNiveauIndex];

  const hasValidAC = lastNiveau.acs.some(ac => ac.num.trim() && ac.intitule.trim());

  if (lastNiveau.intitule.trim() !== '' && hasValidAC) {
    currentCompetence.value.niveaux.push({
      intitule: '',
      acs: [{ num: '', intitule: '' }]
    });
  } else {
    alert("Veuillez saisir l'intitulé du niveau actuel ET au moins un AC complet avant d'en ajouter un autre.");
  }
};

const handleAddAc = (niveauIndex) => {
  const niveau = currentCompetence.value.niveaux[niveauIndex];
  const lastAcIndex = niveau.acs.length - 1;
  const lastAc = niveau.acs[lastAcIndex];

  if (lastAc.num.trim() !== '' && lastAc.intitule.trim() !== '') {
    niveau.acs.push({
      num: '',
      intitule: ''
    });
  } else {
    alert("Veuillez saisir le numéro ET l'intitulé de l'AC actuel avant d'en ajouter un autre.");
  }
};

const handleSaveCompetence = () => {
  if (!currentCompetence.value.ueNum.trim() || !currentCompetence.value.ueIntitule.trim()) {
    alert('Veuillez saisir le numéro et l\'intitulé de l\'UE.');
    return;
  }
  const ueComplete = `${currentCompetence.value.ueNum.trim()}. ${currentCompetence.value.ueIntitule.trim()}`;

  const niveauxFiltres = currentCompetence.value.niveaux
      .filter(n => n.intitule.trim() !== '');

  if (niveauxFiltres.length === 0) {
    alert('Veuillez saisir au moins un Niveau.');
    return;
  }

  const niveauxResume = niveauxFiltres.map((n, index) => {
    const acsFiltres = n.acs.filter(ac => ac.num.trim() !== '' && ac.intitule.trim() !== '');

    return {
      num: generateNiveauNum(index),
      intitule: n.intitule.trim(),
      acs: acsFiltres.map(ac => `AC${ac.num}: ${ac.intitule.trim()}`)
    };
  }).filter(n => n.acs.length > 0);

  if (niveauxResume.length === 0) {
    alert('Chaque niveau doit avoir au moins un AC associé.');
    return;
  }

  const nouvelleCompetence = {
    ue: ueComplete,
    niveaux: niveauxResume,
  };

  competencesFinalisees.value.push(nouvelleCompetence);

  currentCompetence.value = {
    ueNum: '',
    ueIntitule: '',
    niveaux: [{ intitule: '', acs: [{ num: '', intitule: '' }] }],
  };

  console.log('Compétence validée et ajoutée :', nouvelleCompetence);
};

</script>

<template>
  <header class="page-header">
    <div class="container-card">
      <img src="../assets/uploads/Logo_unilim.png" alt="Logo Unilim"><p>Compétences RX.XX</p>
    </div>
    <div @click="handleAide" class="aide">Service d'aide</div>
    <div @click="handleDeconnexion" class="quitter">
      <svg width="48" height="48" viewBox="0 0 48 48" fill="none" xmlns="http://www.w3.org/2000/svg">
        <path d="M18 42H10C8.93913 42 7.92172 41.5786 7.17157 40.8284C6.42143 40.0783 6 39.0609 6 38V10C6 8.93913 6.42143 7.92172 7.17157 7.17157C7.92172 6.42143 8.93913 6 10 6H18M32 34L42 24M42 24L32 14M42 24H18" stroke="white" stroke-width="4" stroke-linecap="round" stroke-linejoin="round"/>
      </svg>
    </div>
  </header>
  <main class="main-div">
    <div class="description">Veuillez saisir la/les compétences :</div>

    <div class="container-global">

      <div v-for="(comp, index) in competencesFinalisees" :key="index" class="skill-card skill-card-resume">
        <p id="card-title">Compétence {{ index + 1 }}</p>
        <div class="resume-item"><strong>UE :</strong> {{ comp.ue }}</div>

        <div class="resume-item">
          <strong>Structure des niveaux/ACs :</strong>
          <ul class="resume-list main-level">
            <li v-for="(niveau, nIndex) in comp.niveaux" :key="'n-' + index + '-' + nIndex">
              <p style="font-weight: bold; margin-bottom: 5px;">{{ niveau.num }}: {{ niveau.intitule }}</p>

              <ul class="resume-list sub-level">
                <li v-for="(ac, aIndex) in niveau.acs" :key="'a-' + index + '-' + nIndex + '-' + aIndex">
                  {{ ac }}
                </li>
              </ul>
            </li>
          </ul>
        </div>
      </div>

      <div class="skill-card">
        <p id="card-title">Ajouter une compétence</p>

        <div class="form-item container-ue-num">
          <label for="ue-num">Veuillez **saisir le numéro** de l'UE </label>
          <input type="text" name="ue-num" id="ue-num" v-model="currentCompetence.ueNum" placeholder="Ex: 1">
        </div>
        <div class="form-item container-ue-intitule">
          <label for="ue-intitule">Veuillez **saisir l'intitulé** de l'UE </label>
          <input type="text" name="ue-intitule" id="ue-intitule" v-model="currentCompetence.ueIntitule" placeholder="Ex: Réaliser">
        </div>

        <hr class="separator"/>
        <p class="section-title">Définir les Niveaux et leurs ACs associés</p>

        <div v-for="(niveau, nIndex) in currentCompetence.niveaux" :key="'niv-' + nIndex" class="form-item container-niv-group">
          <div class="niveau-group">
            <label :for="'niv-' + nIndex">Intitulé du **Niveau {{ nIndex + 1 }}** attendu </label>
            <div class="input-with-plus">
              <input type="text" :name="'niv-' + nIndex" :id="'niv-' + nIndex" v-model="currentCompetence.niveaux[nIndex].intitule" placeholder="Ex: Comprendre et utiliser les bases...">
              <svg
                  v-if="nIndex === currentCompetence.niveaux.length - 1"
                  @click="handleAddNiveau"
                  width="35" height="35" viewBox="0 0 57 52" fill="none" xmlns="http://www.w3.org/2000/svg" class="svg-active">
                <path d="M28.5 17.3333V34.6667M19 26H38M11.875 6.5H45.125C47.7484 6.5 49.875 8.4401 49.875 10.8333V41.1667C49.875 43.5599 47.7484 45.5 45.125 45.5H11.875C9.25165 45.5 7.125 43.5599 7.125 41.1667V10.8333C7.125 8.4401 9.25165 6.5 11.875 6.5Z"  stroke-width="4" stroke-linecap="round" stroke-linejoin="round"/>
              </svg>
            </div>

            <div class="nested-acs">

              <div class="ac-title-with-plus">
                <p class="nested-title">ACs pour Niveau {{ nIndex + 1 }} :</p>
                <svg
                    v-if="niveau.acs.length > 0 && niveau.acs[niveau.acs.length - 1].num.trim() && niveau.acs[niveau.acs.length - 1].intitule.trim()"
                    @click="handleAddAc(nIndex)"
                    width="35" height="35" viewBox="0 0 57 52" fill="none" xmlns="http://www.w3.org/2000/svg" class="svg-active">
                  <path d="M28.5 17.3333V34.6667M19 26H38M11.875 6.5H45.125C47.7484 6.5 49.875 8.4401 49.875 10.8333V41.1667C49.875 43.5599 47.7484 45.5 45.125 45.5H11.875C9.25165 45.5 7.125 43.5599 7.125 41.1667V10.8333C7.125 8.4401 9.25165 6.5 11.875 6.5Z"  stroke-width="4" stroke-linecap="round" stroke-linejoin="round"/>
                </svg>
              </div>

              <div v-for="(ac, aIndex) in niveau.acs" :key="'ac-' + nIndex + '-' + aIndex" class="form-item container-ac ac-input-group">

                <label :for="'ac-num-' + nIndex + '-' + aIndex" class="nested-label">Numéro de l'**AC {{ aIndex + 1 }}**</label>
                <input type="text" :name="'ac-num-' + nIndex + '-' + aIndex" :id="'ac-num-' + nIndex + '-' + aIndex" v-model="niveau.acs[aIndex].num" placeholder="Ex: 11.01">

                <label :for="'ac-intitule-' + nIndex + '-' + aIndex" class="nested-label" style="margin-top: 0.5rem;">Intitulé de l'**AC {{ aIndex + 1 }}**</label>
                <input type="text" :name="'ac-intitule-' + nIndex + '-' + aIndex" :id="'ac-intitule-' + nIndex + '-' + aIndex" v-model="niveau.acs[aIndex].intitule" placeholder="Ex: Déployer une application...">
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
.page-header {
  position: absolute;
  width: 100%;
  height: 172px;
  left: 0px;
  top: 0px;
  background: #B51621;
  box-sizing: border-box;
}

.container-card img {
  position: absolute;
  width: 127px;
  height: 127px;
  left: 64px;
  top: 22.5px;
}
.container-card p{
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

.quitter {
  position: absolute;
  width: 48px;
  height: 48px;
  right: 5%;
  top: 64px;
  cursor: pointer;
}
.quitter:hover{
  cursor: pointer;
  opacity: 0.8;
}

.aide{
  position: absolute;
  width: 126px;
  height: 52px;
  right: 15%;
  top: 60px;
  font-family: 'Roboto', sans-serif;
  font-style: normal;
  font-weight: 500;
  font-size: 36px;
  line-height: 145%;
  display: flex;
  align-items: center;
  text-align: center;
  letter-spacing: -0.005em;
  text-transform: capitalize;
  color: #FFFFFF;
  cursor: pointer;
}

.aide:hover{
  cursor: pointer;
  opacity: 0.8;
}

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

.form-item label{
  display: block;
  margin-bottom: 0.5rem;
  font-weight: bold;
  color: black;
  font-size: 20px;
}

.niveau-group {
  padding: 15px 15px 5px 15px;
  border: 1px solid #ddd;
  border-radius: 8px;
  background-color: #fcfcfc;
  margin-bottom: 25px;
  position: relative;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.03);
}

.nested-acs {
  padding: 10px 10px 5px 10px;
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

.input-with-plus {
  display: flex;
  align-items: center;
  gap: 10px;
}
.input-with-plus svg {
  stroke: black; /* Correction : assure la visibilité du SVG Niveau+ */
  margin: 5px;
  transition: stroke 0.2s, transform 0.2s;
}
.input-with-plus .svg-active:hover {
  cursor: pointer;
  stroke: green;
  transform: scale(1.1);
}

.container-ue-num input,
.container-ue-intitule input {
  width: 100% !important;
  max-width: none !important;
}

.form-item input[type="text"] {
  width: 100%;
  max-width: 400px;
  padding: 12px 16px;
  border-radius: 10px;
  background: rgba(255,255,255,0.4);
  border: 2px solid rgb(0,0,0);
  font-size: 16px;
  box-sizing: border-box;
}

.container-ac input[type="text"] {
  max-width: none;
}

.ac-title-with-plus svg {
  stroke: black;
  transition: stroke 0.2s, transform 0.2s;
  flex-shrink: 0;
}
.ac-title-with-plus .svg-active:hover {
  cursor: pointer;
  stroke: green;
  transform: scale(1.1);
}

.ac-input-group {
  border-top: 1px dashed #ccc;
  padding-top: 15px;
  margin-bottom: 20px;
}
.nested-acs .ac-input-group:first-child {
  border-top: none;
  padding-top: 5px;
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