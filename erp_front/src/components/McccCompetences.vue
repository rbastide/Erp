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
  router.push('/mccc-menu');
};

const handleRetour = () => {
  router.push('/mccc-menu');
}

// Logique de Compétences

const currentCompetence = ref({
  ue: '',
  niveaux: [''],
  acs: [''],
});

const competencesFinalisees = ref([]);

const handleAddNiveau = () => {
  if (currentCompetence.value.niveaux[currentCompetence.value.niveaux.length - 1] !== '') {
    currentCompetence.value.niveaux.push('');
  } else {
    alert("Veuillez choisir le niveau actuel avant d'en ajouter un autre.");
  }
};

const handleAddAc = () => {
  if (currentCompetence.value.acs[currentCompetence.value.acs.length - 1] !== '') {
    currentCompetence.value.acs.push('');
  } else {
    alert("Veuillez choisir l'AC actuel avant d'en ajouter un autre.");
  }
};

const handleSaveCompetence = () => {
  const niveauxFiltres = currentCompetence.value.niveaux.filter(n => n !== '');
  const acsFiltres = currentCompetence.value.acs.filter(ac => ac !== '');

  if (!currentCompetence.value.ue || niveauxFiltres.length === 0 || acsFiltres.length === 0) {
    alert('Veuillez sélectionner une UE, au moins un Niveau et au moins un AC avant de valider.');
    return;
  }

  const nouvelleCompetence = {
    ue: currentCompetence.value.ue,
    niveaux: niveauxFiltres,
    acs: acsFiltres
  };

  competencesFinalisees.value.push(nouvelleCompetence);

  currentCompetence.value = {
    ue: '',
    niveaux: [''],
    acs: [''],
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
    <div class="description">Veuillez saisir la/les compétences pour cette ressource :</div>

    <div class="container-global">

      <div v-for="(comp, index) in competencesFinalisees" :key="index" class="skill-card skill-card-resume">
        <p id="card-title">Compétence {{ index + 1 }}</p>
        <div class="resume-item"><strong>UE :</strong> {{ comp.ue }}</div>
        <div class="resume-item"><strong>Niveaux associés :</strong> {{ comp.niveaux.join(' / ') }}</div>
        <div class="resume-item"><strong>ACs associés :</strong> {{ comp.acs.join(' / ') }}</div>
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

        <div v-for="(niveau, index) in currentCompetence.niveaux" :key="'niv-' + index" class="form-item container-niv">
          <label :for="'niv-' + index">Veuillez choisir un niveau attendu </label>
          <div class="select-with-plus">
            <select :name="'niv-' + index" :id="'niv-' + index" v-model="currentCompetence.niveaux[index]">
              <option value="" selected disabled>Rien de sélectionné</option>
              <option value="Niveau 1">Niveau 1 : Lorem Ipsum</option>
              <option value="Niveau 2">Niveau 2 : Lorem Ipsum</option>
              <option value="Niveau 3">Niveau 3 : Lorem Ipsum</option>
            </select>
            <svg
                v-if="index === currentCompetence.niveaux.length - 1"
                @click="handleAddNiveau"
                width="35" height="35" viewBox="0 0 57 52" fill="none" xmlns="http://www.w3.org/2000/svg" class="svg-active">
              <path d="M28.5 17.3333V34.6667M19 26H38M11.875 6.5H45.125C47.7484 6.5 49.875 8.4401 49.875 10.8333V41.1667C49.875 43.5599 47.7484 45.5 45.125 45.5H11.875C9.25165 45.5 7.125 43.5599 7.125 41.1667V10.8333C7.125 8.4401 9.25165 6.5 11.875 6.5Z"  stroke-width="4" stroke-linecap="round" stroke-linejoin="round"/>
            </svg>
          </div>
        </div>

        <div v-for="(ac, index) in currentCompetence.acs" :key="'ac-' + index" class="form-item container-ac">
          <label :for="'ac-' + index">AC {{ index + 1 }} associé</label>
          <div class="select-with-plus">
            <select :name="'ac-' + index" :id="'ac-' + index" v-model="currentCompetence.acs[index]">
              <option value="" selected disabled>Rien de sélectionné</option>
              <option value="AC 11.01">AC 11.01 : Lorem Ipsum</option>
              <option value="AC 11.02">AC 11.02 : Lorem Ipsum</option>
              <option value="AC 11.03">AC 11.03 : Lorem Ipsum</option>
              <option value="AC 11.04">AC 11.04 : Lorem Ipsum</option>
            </select>
            <svg
                v-if="index === currentCompetence.acs.length - 1"
                @click="handleAddAc"
                width="35" height="35" viewBox="0 0 57 52" fill="none" xmlns="http://www.w3.org/2000/svg" class="svg-active">
              <path d="M28.5 17.3333V34.6667M19 26H38M11.875 6.5H45.125C47.7484 6.5 49.875 8.4401 49.875 10.8333V41.1667C49.875 43.5599 47.7484 45.5 45.125 45.5H11.875C9.25165 45.5 7.125 43.5599 7.125 41.1667V10.8333C7.125 8.4401 9.25165 6.5 11.875 6.5Z"  stroke-width="4" stroke-linecap="round" stroke-linejoin="round"/>
            </svg>
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
/*
* ======================================
* HEADER STYLES
* ======================================
*/
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

/*
* ======================================
* MAIN CONTENT & FORM STYLES
* ======================================
*/

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
  margin: 5px;
  stroke: black;
  transition: stroke 0.2s, transform 0.2s;
}

.select-with-plus .svg-active:hover {
  cursor: pointer;
  stroke: green;
  transform: scale(1.1);
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
  font-size: 1rem; /* 16px */
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