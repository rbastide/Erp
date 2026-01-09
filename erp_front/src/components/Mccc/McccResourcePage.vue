<script setup lang="ts">
import { ref, onMounted } from 'vue'; // Ajout de ref et onMounted
import { useRouter } from 'vue-router';
import { mcccStore } from '@/services/mcccStore';
import AppHeader from '../App/Header.vue';
import Sidebar from '../App/Sidebar.vue';
import api from '@/services/api'; // Import de votre instance axios

const router = useRouter();

// 1. Création d'une variable réactive pour stocker les ressources de la BDD
// On définit une interface pour typer la ressource selon votre ResourceResponse.java
interface Resource {
  resourceID: number;
  num: string;
  name: string;
  semester: string;
}

const resources = ref<Resource[]>([]);

// 2. Fonction pour charger les données depuis le contrôleur Java
const fetchResources = async () => {
  try {
    // Appel de la route @GetMapping("/resources") définie dans ResourceController
    const response = await api.get('/resources/resources');
    resources.value = response.data;
  } catch (error) {
    console.error("Erreur lors du chargement des ressources:", error);
  }
};

// 3. Appel de la fonction au chargement du composant
onMounted(() => {
  fetchResources();
});

const handleRetour = () => {
  router.back();
};

const handleMccc = (code: string) => {
  mcccStore.loadMcccStore();
  mcccStore.resourceCode = code;
  mcccStore.registerMcccStore();
  router.push('/mccc-menu');
}
</script>

<template>
  <Sidebar/>
  <div class="page-container">
    <AppHeader title="Choix ressources" />

    <main class="main-content">
      <div class="section-title">Veuillez choisir la ressource :</div>

      <div class="grid-container">
        <div
            v-for="res in resources"
            :key="res.resourceID"
            class="card-action"
            @click="handleMccc(res.num)"
        >
          <div class="icon-circle">
            <svg width="40" height="40" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
              <path d="M4 19.5A2.5 2.5 0 0 1 6.5 17H20"></path>
              <path d="M6.5 2H20v20H6.5A2.5 2.5 0 0 1 4 19.5v-15A2.5 2.5 0 0 1 6.5 2z"></path>
            </svg>
          </div>
          <p>{{ res.num }}</p>
          <span class="res-name">{{ res.name }}</span>
        </div>
      </div>

      <div v-if="resources.length === 0" class="empty-msg">
        Aucune ressource disponible en base de données.
      </div>

      <div class="footer-actions">
        <button @click="handleRetour" class="btn-quitter">Retour</button>
      </div>
    </main>
  </div>
</template>

<style scoped>
/* Vos styles existants conservés */
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
  margin-top: 175px;
}

.section-title {
  width: 100%;
  max-width: 1200px;
  margin-bottom: 40px;
  font-size: 32px;
  font-weight: 400;
  color: #E92533;
  text-align: left;
}

.grid-container {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
  gap: 30px;
  width: 100%;
  max-width: 1200px;
  justify-items: center;
}

.card-action {
  cursor: pointer;
  background: #ffffff;
  width: 100%;
  max-width: 340px;
  height: 220px;
  border-radius: 15px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  text-align: center;
  padding: 20px;
  transition: all 0.3s cubic-bezier(0.25, 0.8, 0.25, 1);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
  border: 1px solid rgba(181, 22, 33, 0.1);
}

.card-action:hover {
  transform: translateY(-8px);
  box-shadow: 0 12px 24px rgba(181, 22, 33, 0.15);
  border-color: #B51621;
}

.icon-circle {
  width: 70px;
  height: 70px;
  background: rgba(181, 22, 33, 0.05);
  border-radius: 50%;
  display: flex;
  justify-content: center;
  align-items: center;
  margin-bottom: 15px;
  color: #B51621;
  transition: all 0.3s ease;
}

.card-action:hover .icon-circle {
  background: #B51621;
  color: #ffffff;
}

.card-action p {
  margin: 0;
  font-size: 32px;
  font-weight: 600;
  color: #333333;
  transition: color 0.3s ease;
}

/* Nouveau style pour le nom de la ressource sous le code */
.res-name {
  font-size: 14px;
  color: #666;
  margin-top: 5px;
}

.empty-msg {
  margin-top: 40px;
  color: #999;
  font-style: italic;
}

.card-action:hover p {
  color: #B51621;
}

.footer-actions {
  margin-top: 60px;
  width: 100%;
  display: flex;
  justify-content: center;
}

.btn-quitter {
  width: 180px;
  padding: 12px;
  border: 2px solid #B51621;
  text-align: center;
  border-radius: 8px;
  background-color: #B51621;
  color: #FFFFFF;
  font-size: 1.1rem;
  font-weight: bold;
  cursor: pointer;
  transition: all 0.3s ease;
}

.btn-quitter:hover {
  background-color: transparent;
  color: #B51621;
  transform: scale(1.05);
}

@media (max-width: 750px) {
  .section-title {
    text-align: center;
    font-size: 24px;
  }
  .main-content {
    margin-top: 150px;
  }
}
</style>