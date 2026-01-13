<script setup lang="ts">
import { ref, computed, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import AppHeader from '../App/Header.vue';
import Sidebar from '../App/Sidebar.vue';
import api from '@/services/api';

const router = useRouter();
const searchQuery = ref('');
const historyItems = ref([]);
const isLoading = ref(true);

const formatDate = (dateString: string) => {
  if (!dateString) return '';
  const date = new Date(dateString);
  return date.toLocaleDateString('fr-FR');
};

const fetchHistory = async () => {
  try {
    isLoading.value = true;
    const response = await api.get('/resourceSheet/getHistory');
    historyItems.value = response.data;
  } catch (error) {
    console.error("Erreur chargement historique :", error);
  } finally {
    isLoading.value = false;
  }
};

onMounted(() => {
  fetchHistory();
});

const filteredVersions = computed(() => {
  const query = searchQuery.value.toLowerCase().trim();
  return historyItems.value.filter((item: any) =>
      (item.resourceCode && item.resourceCode.toLowerCase().includes(query)) ||
      (item.resourceName && item.resourceName.toLowerCase().includes(query)) ||
      (item.date && formatDate(item.date).includes(query))
  );
});

const handleRetour = () => router.back();

const handleShow = (item: any) => {
  router.push({
    path: '/resource-sheet-history',
    query: {
      code: item.resourceCode,
      date: formatDate(item.date)
    }
  });
};

const handleExportPdf = (code: string) => {
  console.log(`Export PDF demandé pour ${code}`);
};

const clearSearch = () => searchQuery.value = '';
</script>

<template>
  <Sidebar/>
  <AppHeader title="Historique des fiches ressources" />

  <main class="main-content">
    <div class="container">
      <div class="version-list-container">

        <div v-if="isLoading" class="no-result">
          <p>Chargement de l'historique...</p>
        </div>

        <div v-else-if="filteredVersions.length === 0" class="no-result">
          <p v-if="searchQuery">Aucune fiche trouvée pour "<strong>{{ searchQuery }}</strong>"</p>
          <p v-else>Aucun historique disponible.</p>
          <button v-if="searchQuery" @click="clearSearch" class="btn-clear-link">Réinitialiser la recherche</button>
        </div>

        <ul v-else class="version-list">
          <li
              v-for="(item, index) in filteredVersions"
              :key="index"
              class="version-item"
              @click="handleShow(item)"
          >
            <div class="info-group">
              <span class="version-code">{{ item.resourceCode }}</span>
              <span class="version-title">{{ item.resourceName }}</span>
              <span class="version-date">{{ formatDate(item.date) }}</span>
            </div>

            <button class="btn-icon-container" @click.stop="handleExportPdf(item.resourceCode)" title="Exporter en PDF">
              <svg xmlns="http://www.w3.org/2000/svg" class="bi bi-file-earmark-arrow-down btn-icon" viewBox="0 0 16 16">
                <path d="M8.5 6.5a.5.5 0 0 0-1 0v3.793L6.354 9.146a.5.5 0 1 0-.708.708l2 2a.5.5 0 0 0 .708 0l2-2a.5.5 0 0 0-.708-.708L8.5 10.293z"/>
                <path d="M14 14V4.5L9.5 0H4a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h8a2 2 0 0 0 2-2M9.5 3A1.5 1.5 0 0 0 11 4.5h2V14a1 1 0 0 1-1 1H4a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1h5.5z"/>
              </svg>
            </button>
          </li>
        </ul>
      </div>
    </div>

    <footer class="sticky-footer">
      <div class="footer-content">
        <div class="search-wrapper">
          <span class="search-icon">🔍</span>
          <input
              v-model="searchQuery"
              type="text"
              placeholder="Chercher par code, nom ou date..."
              class="search-input"
          />
          <button v-if="searchQuery" @click="clearSearch" class="clear-input-btn">✕</button>
        </div>
        <button @click="handleRetour" class="btn-quitter">Retour</button>
      </div>
    </footer>
  </main>
</template>

<style scoped>
.main-content {
  background-color: #f4f7f9;
  min-height: 100vh;
  padding: 220px 20px 140px;
  font-family: 'Roboto', sans-serif;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.container {
  width: 100%;
  max-width: 900px;
}

.version-list {
  list-style: none;
  padding: 0;
  margin: 0;
}

.version-item {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 20px 30px;
  margin-bottom: 15px;
  background: white;
  border: 1px solid #e2e8f0;
  border-radius: 12px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.05);
  transition: all 0.2s ease;
  cursor: pointer;
}

.version-item:hover {
  transform: translateY(-3px);
  border-color: #B51621;
}

.info-group {
  display: flex;
  align-items: center;
  gap: 20px;
  flex: 1;
}

.version-code {
  font-size: 1.8rem;
  font-weight: 700;
  color: #B51621;
  min-width: 100px;
}

.version-title {
  font-size: 1.1rem;
  color: #333;
  font-weight: 600;
  flex: 1;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  margin-right: 15px;
}

.version-date {
  font-size: 1rem;
  color: #64748b;
  font-weight: 500;
  white-space: nowrap;
}

.btn-icon-container {
  background: none;
  border: none;
  cursor: pointer;
  padding: 10px;
  border-radius: 50%;
  transition: background-color 0.2s;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-left: 10px;
}

.btn-icon-container:hover {
  background-color: #e3f2fd;
}

.btn-icon {
  width: 28px;
  height: 28px;
  fill: #64748b;
  transition: fill 0.2s;
}

.btn-icon-container:hover .btn-icon {
  fill: #1976D2;
}

.no-result {
  text-align: center;
  margin-top: 60px;
  color: #64748b;
}

.btn-clear-link {
  background: none;
  border: none;
  color: #B51621;
  text-decoration: underline;
  cursor: pointer;
  font-weight: bold;
}

.sticky-footer {
  position: fixed;
  bottom: 0;
  left: 0;
  width: 100%;
  background: white;
  box-shadow: 0 -4px 20px rgba(0, 0, 0, 0.15);
  padding: 20px 0;
  z-index: 100;
}

.footer-content {
  max-width: 900px;
  margin: 0 auto;
  padding: 0 20px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 20px;
}

.search-wrapper {
  position: relative;
  flex: 1;
  max-width: 550px;
}

.search-input {
  width: 100%;
  padding: 12px 45px;
  border-radius: 10px;
  border: 2px solid #e2e8f0;
  font-size: 1rem;
  background: #f8fafc;
  transition: all 0.2s;
}

.search-input:focus {
  outline: none;
  border-color: #B51621;
  background: white;
  box-shadow: 0 0 0 4px rgba(181, 22, 33, 0.1);
}

.search-icon {
  position: absolute;
  left: 15px;
  top: 50%;
  transform: translateY(-50%);
  opacity: 0.5;
}

.clear-input-btn {
  position: absolute;
  right: 15px;
  top: 50%;
  transform: translateY(-50%);
  background: #e2e8f0;
  border: none;
  border-radius: 50%;
  width: 22px;
  height: 22px;
  font-size: 10px;
  cursor: pointer;
}

.quit-btn {
  background-color: #B51621;
  color: white;
  padding: 12px 40px;
  border: none;
  border-radius: 10px;
  font-weight: bold;
  cursor: pointer;
  white-space: nowrap;
}

.quit-btn:hover {
  background-color: #8e111a;
}
</style>