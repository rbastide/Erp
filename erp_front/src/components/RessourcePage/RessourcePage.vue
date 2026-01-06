<script setup lang="ts">
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import AppHeader from '../App/Header.vue';
import Sidebar from '../App/Sidebar.vue';

const router = useRouter();

const ressources = ref([
  { code: 'R1.01', name: 'Initiation au développement' },
  { code: 'R1.02', name: 'Développement interfaces' },
  { code: 'R2.01', name: 'Architecture Réseaux' },
  { code: 'R2.03', name: 'Qualité de dév' },
  { code: 'R3.01', name: 'Services Web' },
  { code: 'R3.04', name: 'Management de projet' },
]);

const handleRetour = () => router.push('/home');
const handleFill = () => router.push('/fillressourcepage');
</script>

<template>
  <Sidebar/>
  <AppHeader title="Choix de la ressource" />

  <main class="main-content">
    <div class="container">
      <h2 class="description">Veuillez choisir votre ressource :</h2>

      <div class="container-button">
        <div
            v-for="res in ressources"
            :key="res.code"
            @click="handleFill"
            class="push-button"
        >
          <p class="text-button">{{ res.code }}</p>
          <p class="sub-text-button">{{ res.name }}</p>
        </div>
      </div>

      <div class="actions-container">
        <button @click="handleRetour" class="btn-quitter">Quitter</button>
      </div>
    </div>
  </main>
</template>

<style scoped>
.main-content {
  background-color: #f4f7f9;
  min-height: 100vh;
  padding: 220px 20px 60px;
  font-family: 'Roboto', sans-serif;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.container {
  width: 100%;
  max-width: 1000px;
}

.description {
  color: #E92533;
  font-size: 1.8rem;
  font-weight: 500;
  margin-bottom: 40px;
  text-align: left;
}

/* GRILLE */
.container-button {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
  gap: 25px;
  width: 100%;
  margin-bottom: 50px;
}

.push-button {
  background: white;
  border: 1px solid #e2e8f0;
  border-radius: 16px;
  padding: 40px 20px;
  display: flex;
  flex-direction: column;
  align-items: center;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.05);
  transition: all 0.3s ease;
  cursor: pointer;
}

.push-button:hover {
  transform: translateY(-8px);
  border-color: #B51621;
  box-shadow: 0 12px 25px rgba(181, 22, 33, 0.1);
}

.text-button {
  font-size: 2.8rem;
  font-weight: 800;
  color: #B51621;
  margin: 0;
}

.sub-text-button {
  font-size: 1rem;
  color: #64748b;
  margin-top: 10px;
  text-align: center;
  font-weight: 500;
}

.actions-container {
  display: flex;
  justify-content: center;
  margin-top: 20px;
}

.btn-quitter {
  background: #B51621;
  color: white;
  padding: 15px 60px;
  border: none;
  border-radius: 10px;
  font-weight: 700;
  font-size: 1.1rem;
  cursor: pointer;
  transition: background 0.2s, transform 0.2s;
}

.btn-quitter:hover {
  background: #8e111a;
  transform: translateY(-2px);
}
</style>