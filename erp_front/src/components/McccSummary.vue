<script setup lang="ts">
import { useRouter } from 'vue-router';
import { mcccStore } from "@/services/mcccStore.js";
import AppHeader from './Header.vue';
import { onMounted } from 'vue';

const router = useRouter();

const handleAide = () => {
  router.push('/aide');
};

const handleDeconnexion = () => {
  router.push('/deconnexion');
};

const handleValider = () => {
  router.push('/modif-saved');
  mcccStore.clearMcccStore();
};

const handleRetour = () => {
  router.push('/mccc-menu');
};

onMounted(() => {
  mcccStore.loadMcccStore();
});
</script>

<template>
  <AppHeader title="Récapitulatif"/>
  <main class="main-content">
    <div class="summary">
      Récapitulatif de la ressource : {{ mcccStore.resourceCode }}
    </div>

    <div class="grid">
      <div class="hour-summary">
        <div class="title">Heures</div>
        <div class="listing-hours">
          <div class="type-of-hour" id="cm">
            <p class="title-hour">CM</p>
            <p class="grey-square">{{ mcccStore.hoursCM }}</p>
          </div>
          <div class="type-of-hour">
            <p class="title-hour">TD</p>
            <p class="grey-square">{{ mcccStore.hoursTD }}</p>
          </div>
          <div class="type-of-hour">
            <p class="title-hour">DS</p>
            <p class="grey-square">{{ mcccStore.hoursTD }}</p>
          </div>
          <div class="type-of-hour">
            <p class="title-hour">TP</p>
            <p class="grey-square">{{ mcccStore.hoursTP }}</p>
          </div>
          <div class="type-of-hour" id="ds_tp">
            <p class="title-hour">DS/TP</p>
            <p class="grey-square">{{ mcccStore.hoursDSTP }}</p>
          </div>
          <div class="total">
            <p>Total : <span id="tot">{{ mcccStore.hoursTotal }}</span></p>
          </div>
        </div>
      </div>

      <div class="sae-summary">
        <p class="title-centered">SAE(s) concernées :</p>
        <p class="grey-square" v-for="saeCode in mcccStore.saeCodes" :key="saeCode">
          {{ saeCode }}
        </p>
        <p class="title-centered">Compétence(s) concernée(s) :</p>
        <p class="grey-square" id="comp_conc">UE1.1</p>
      </div>
    </div>

    <p class="title-centered" id="comp">Compétences / Objectifs</p>

    <div class="big-square">
      <div class="grid-split-in-two">
        <p class="title">Intitulé de l'UE 1 : </p>
        <p class="grey-square" id="intitule">Ceci est l'intitulé de l'UE : description globale du fonctionnement</p>
      </div>

      <div class="table-container">
        <table class="competence-table">
          <thead>
          <tr>
            <th class="col-lvl">Niveau</th>
            <th class="col-content">Apprentissages Critiques</th>
          </tr>
          </thead>
          <tbody>
          <tr>
            <td class="lvl-number">1</td>
            <td class="lvl-data">
              <p class="lvl-label">Intitulé :</p>
              <p class="lvl-desc">Concevoir et mettre en place une base de données à partir d'un cahier des charges client</p>
              <p class="lvl-label">Apprentissages :</p>
              <ul class="ac-list">
                <li>Mettre à jour une base de données relationnelles (en requêtes directes ou à travers une application)</li>
              </ul>
            </td>
          </tr>
          <tr>
            <td class="lvl-number">2</td>
            <td class="lvl-data">
              <p class="lvl-label">Intitulé :</p>
              <p class="lvl-desc">Établir des scénarios d'utilisation pour des tests d'acceptance.</p>
              <p class="lvl-label">Apprentissages :</p>
              <ul class="ac-list">
                <li>Rédiger des spécifications fonctionnelles détaillées.</li>
              </ul>
            </td>
          </tr>
          <tr>
            <td class="lvl-number">3</td>
            <td class="lvl-data">
              <p class="lvl-label">Intitulé :</p>
              <p class="lvl-desc">Organiser l'architecture d'une application web en Microservices.</p>
              <p class="lvl-label">Apprentissages :</p>
              <ul class="ac-list">
                <li>Créer des conteneurs Docker pour chaque Microservice.</li>
              </ul>
            </td>
          </tr>
          </tbody>
        </table>
      </div>
    </div>

    <div class="info-footer">
      <div class="title"><p>Professeur(s) référent :
        <span id="creation" v-for="(referent, index) in mcccStore.referents" :key="referent">
          {{ referent.firstname + " " + referent.lastname + (mcccStore.referents.length == index + 1 ? "" : ", ") }}
        </span>
      </p></div>
      <div class="title"><p>Version fiche ressource : <span id="creation">1</span></p></div>
      <div class="title"><p>Date de création : <span id="creation">13/04/2025</span></p></div>
      <div class="title"><p>Date de modification : <span id="creation">18/04/2025</span></p></div>
    </div>

    <div class="container-btn">
      <div @click="handleValider" class="btn-sys">Valider</div>
      <div @click="handleRetour" class="btn-sys">Annuler</div>
    </div>
  </main>
</template>

<style scoped>
.main-content {
  width: 90%;
  margin: 254px auto 50px;
  font-family: 'Roboto', sans-serif;
}

.summary {
  width: 100%;
  font-size: 32px;
  text-align: center;
  color: #E92533;
  margin-bottom: 30px;
}

.title {
  font-size: 32px;
  color: #E92533;
  margin-left: 30px;
  margin-top: 20px;
}

.grid {
  display: grid;
  grid-template-columns: auto auto;
  justify-content: center;
  gap: 100px;
  margin-bottom: 40px;
}

.listing-hours { padding: 20px; }

.type-of-hour {
  display: grid;
  grid-template-columns: 80px 200px;
  gap: 20px;
  align-items: center;
  margin-bottom: 20px;
}

.title-hour { font-size: 28px; margin: 0; }

.grey-square {
  background: #D9D9D9;
  padding: 15px;
  border: 1px solid rgba(0, 0, 0, 0.25);
  box-shadow: 0 4px 4px rgba(0, 0, 0, 0.25);
  border-radius: 15px;
  font-size: 24px;
  text-align: center;
  min-width: 100px;
}

.total {
  font-size: 40px;
  margin-top: 30px;
  text-align: right;
}

.title-centered {
  font-size: 32px;
  color: #E92533;
  text-align: center;
  margin: 20px 0;
}

.big-square {
  margin: 40px auto;
  padding: 30px;
  max-width: 1100px;
  border: black 3px solid;
  background-color: #fff;
  border-radius: 20px;
  overflow: hidden;
}

.grid-split-in-two {
  display: grid;
  grid-template-columns: auto 1fr;
  align-items: center;
  gap: 20px;
  margin-bottom: 40px;
}

/* STYLES DU NOUVEAU TABLEAU */
.table-container {
  margin-top: 20px;
}

.competence-table {
  width: 100%;
  border-collapse: collapse;
  border: 2px solid #333;
}

.competence-table th {
  background-color: #E92533;
  color: white;
  padding: 15px;
  font-size: 1.2rem;
  border: 1px solid #333;
}

.col-lvl { width: 100px; }

.lvl-number {
  font-size: 3rem;
  font-weight: bold;
  text-align: center;
  border: 1px solid #333;
  background-color: #f5f5f5;
}

.lvl-data {
  padding: 20px;
  border: 1px solid #333;
}

.lvl-label {
  font-weight: bold;
  text-decoration: underline;
  margin-bottom: 5px;
  color: #333;
}

.lvl-desc {
  margin-bottom: 15px;
  font-size: 1.1rem;
}

.ac-list {
  margin: 0;
  padding-left: 20px;
}

.ac-list li {
  margin-bottom: 5px;
  list-style-type: disc;
}

#creation { color: black; }

.container-btn {
  display: flex;
  justify-content: center;
  gap: 40px;
  margin-top: 50px;
}

.btn-sys {
  width: 180px;
  padding: 15px;
  background-color: #B51621;
  color: white;
  text-align: center;
  border-radius: 8px;
  font-weight: bold;
  cursor: pointer;
  transition: all 0.3s ease;
}

.btn-sys:hover {
  background: #999999;
  transform: translateY(-4px);
}
</style>