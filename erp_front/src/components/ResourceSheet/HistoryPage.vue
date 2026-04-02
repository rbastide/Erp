<script setup lang="ts">
import {computed, onMounted, ref, watch} from 'vue';
import {useRouter} from 'vue-router';
import AppHeader from '../App/Header.vue';
import Sidebar from '../App/Sidebar.vue';
import api from '@/services/api';
import DeleteHistoryModal from "@/components/Information/DeleteHistoryModal.vue";
import ErrorValidationModal from "@/components/Information/ErrorValidationModal.vue";
import ValidationSavedModal from "@/components/Information/ValidationSavedModal.vue";

const router = useRouter();
const searchQuery = ref('');
const historyItems = ref([]);
const isLoading = ref(true);

const showDeleteModal = ref(false);
const itemToDelete = ref<{ id: number, code: string } | null>(null);
const showSuccess = ref(false);
const showError = ref(false);

const userRole = ref('');

const selectedYear = ref<number | string>(new Date().getFullYear());
const availableYears = ref<number[]>([]);
const statusFilter = ref('ALL');

const formatDate = (dateString: string) => {
  if (!dateString) return '';
  const date = new Date(dateString);
  return date.toLocaleDateString('fr-FR');
};

const fetchAvailableYears = async () => {
  try {
    const response = await api.get('/resourceSheet/available-years');
    const yearsData = response.data.filter(Boolean);
    const yearsNumbers = yearsData.map((item: any) => item.year === undefined ? item : item.year);
    const uniqueYears = [...new Set(yearsNumbers)].sort((a: any, b: any) => b - a) as number[];
    availableYears.value = uniqueYears;

    if (uniqueYears.length > 0 && !uniqueYears.includes(selectedYear.value as number)) {
      selectedYear.value = "ALL";
    }
  } catch (error) {
    console.error("Erreur lors de la récupération des années disponibles :", error);
    availableYears.value = [new Date().getFullYear()];
  }
};

const getRole = () => {
  const role = localStorage.getItem('user_role');
  userRole.value = role?.toUpperCase() || 'Not Found';
};

const fetchHistory = async () => {
  try {
    isLoading.value = true;
    const response = await api.get(`/resourceSheet/getHistory/${selectedYear.value}`);
    historyItems.value = response.data;
  } catch (error) {
    console.error("Erreur chargement historique :", error);
    historyItems.value = [];
  } finally {
    isLoading.value = false;
  }
};

onMounted(async () => {
  await fetchAvailableYears();
  await fetchHistory();
  getRole();
});

watch(selectedYear, () => {
  fetchHistory();
});

const filteredVersions = computed(() => {
  const query = searchQuery.value.toLowerCase().trim();
  return historyItems.value.filter((item: any) => {
    const matchSearch = (item.resourceCode && item.resourceCode.toLowerCase().includes(query)) ||
        (item.resourceName && item.resourceName.toLowerCase().includes(query)) ||
        (item.date && formatDate(item.date).includes(query));

    let matchStatus = true;
    if (statusFilter.value === 'VALIDATED') {
      matchStatus = !!item.isValidate;
    } else if (statusFilter.value === 'PENDING') {
      matchStatus = !item.isValidate;
    }

    return matchSearch && matchStatus;
  });
});

const handleBack = () => router.back();

const handleShow = (item: any) => {
  router.push({
    path: '/resource-sheet-history',
    query: {
      id: item.sheetID,
      code: item.resourceCode,
      date: formatDate(item.date),
      year: item.academicYearStart
    }
  });
};

const handleValidateSheet = async (item: any) => {
  const id = item.sheetID || item.id;

  if(!confirm(`Êtes-vous sûr de vouloir valider la fiche ${item.resourceCode} ?`)){
    return;
  }

  try {
    await api.put(`resourceSheet/validate/${id}`);
    showSuccess.value = true;

    const index = historyItems.value.findIndex((hItem: any) => (hItem.sheetID || hItem.id) === id);
    if (index !== -1) {
      historyItems.value[index].isValidate = true;
    }
  } catch(error){
    console.error("Erreur lors de la validation de la fiche :", error);
    showError.value = true;
  }
};

const handleDelete = (id: number, code: string) => {
  itemToDelete.value = { id, code };
  showDeleteModal.value = true;
};

const confirmDeletion = async () => {
  if (!itemToDelete.value) return;
  const id = itemToDelete.value.id;

  try {
    await api.delete(`/resourceSheet/${id}`);
    historyItems.value = historyItems.value.filter((item: any) => (item.sheetID || item.id) !== id);
    showDeleteModal.value = false;
    itemToDelete.value = null;
  } catch (error) {
    console.error("Erreur lors de la suppression :", error);
    alert("Une erreur est survenue lors de la suppression.");
  }
};

const handleExportPdf = async (id: number, resourceCode: string) => {
  try {
    const response = await api.get(`/pdf/resource-sheet/${id}`, { responseType: 'blob' });
    const file = new Blob([response.data], {type: 'application/pdf'});
    const fileURL = URL.createObjectURL(file);
    const link = document.createElement('a');
    link.href = fileURL;
    link.setAttribute('download', `fiche-ressource-${resourceCode}.pdf`);
    document.body.appendChild(link);
    link.click();
    link.remove();
    URL.revokeObjectURL(fileURL);
  } catch (error) {
    console.error("Erreur lors de la génération du PDF", error);
  }
};

const clearSearch = () => searchQuery.value = '';
</script>

<template>
  <Sidebar/>
  <AppHeader title="Gestion des fiches ressources" />

  <main class="main-content">
    <ErrorValidationModal v-if="showError" @close="showError = false" />
    <ValidationSavedModal v-if="showSuccess" @close="showSuccess = false" />

    <div class="container">
      <div class="filters-container">
        <div class="filter-group">
          <label for="status-select">Statut :</label>
          <select id="status-select" v-model="statusFilter" class="filter-select">
            <option value="ALL">Toutes les fiches</option>
            <option value="VALIDATED">Validées</option>
            <option value="PENDING">À valider</option>
          </select>
        </div>

        <div class="filter-group">
          <label for="year-select">Année académique :</label>
          <select id="year-select" v-model="selectedYear" class="filter-select">
            <option value="ALL">Toutes les années</option>
            <option v-for="year in availableYears" :key="year" :value="year">
              {{ year }} / {{ year + 1 }}
            </option>
          </select>
        </div>
      </div>

      <div class="version-list-container">
        <div v-if="isLoading" class="no-result">
          <p>Chargement des fiches ressources...</p>
        </div>

        <div v-else-if="filteredVersions.length === 0" class="no-result">
          <p v-if="searchQuery">Aucune fiche trouvée pour "<strong>{{ searchQuery }}</strong>"</p>
          <p v-else>Aucune fiche disponible pour les critères sélectionnés.</p>
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
              <div class="text-group">
                <span class="version-title">
                  {{ item.resourceName }}
                  <span class="status-badge" :class="item.isValidate ? 'validated' : 'pending'">
                    {{ item.isValidate ? 'Validée' : 'À valider' }}
                  </span>
                </span>
                <span class="version-date">Modifiée le {{ formatDate(item.date) }}</span>
              </div>
            </div>

            <div v-if="selectedYear === 'ALL'" class="year-badge">
              {{ item.academicYearStart }} / {{ item.academicYearStart + 1 }}
            </div>

            <button
                v-if="!item.isValidate && (userRole === 'ADMINISTRATEUR' || userRole === 'SUPER-ADMIN')"
                class="btn-icon-container validate"
                @click.stop="handleValidateSheet(item)"
                title="Valider la fiche"
            >
              <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" style="fill: none;" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="btn-icon">
                <polyline points="20 6 9 17 4 12"></polyline>
              </svg>
            </button>

            <button class="btn-icon-container" @click.stop="handleExportPdf(item.sheetID, item.resourceCode)" title="Exporter en PDF">
              <svg xmlns="http://www.w3.org/2000/svg" fill="currentColor" width="24" height="24" class="bi bi-file-earmark-arrow-down btn-icon" viewBox="0 0 16 16">
                <path d="M8.5 6.5a.5.5 0 0 0-1 0v3.793L6.354 9.146a.5.5 0 1 0-.708.708l2 2a.5.5 0 0 0 .708 0l2-2a.5.5 0 0 0-.708-.708L8.5 10.293z"/>
                <path d="M14 14V4.5L9.5 0H4a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h8a2 2 0 0 0 2-2M9.5 3A1.5 1.5 0 0 0 11 4.5h2V14a1 1 0 0 1-1 1H4a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1h5.5z"/>
              </svg>
            </button>

            <button
                v-if="userRole === 'ADMIN' || userRole === 'SUPER-ADMIN'"
                class="btn-icon-container delete"
                @click.stop="handleDelete(item.sheetID || item.id, item.resourceCode)"
                title="Supprimer la fiche"
            >
              <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" style="fill: none;" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="btn-icon">
                <polyline points="3 6 5 6 21 6"></polyline>
                <path d="M19 6v14a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2V6m3 0V4a2 2 0 0 1 2-2h4a2 2 0 0 1 2 2v2"></path>
                <line x1="10" y1="11" x2="10" y2="17"></line>
                <line x1="14" y1="11" x2="14" y2="17"></line>
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
        <button @click="handleBack" class="quit-btn">Retour</button>
      </div>
    </footer>

    <DeleteHistoryModal
        v-if="showDeleteModal"
        :code="itemToDelete?.code"
        @confirm="confirmDeletion"
        @close="showDeleteModal = false"
    />
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

.filters-container {
  display: flex;
  justify-content: flex-end;
  gap: 15px;
  margin-bottom: 20px;
}

.filter-group {
  display: flex;
  align-items: center;
  gap: 12px;
  background: white;
  padding: 8px 15px;
  border-radius: 10px;
  border: 1px solid #e2e8f0;
  box-shadow: 0 2px 5px rgba(0,0,0,0.05);
}

.filter-group label {
  font-size: 0.9rem;
  font-weight: 600;
  color: #64748b;
}

.filter-select {
  padding: 5px 10px;
  border-radius: 6px;
  border: 1px solid #cbd5e1;
  background-color: #f8fafc;
  color: #B51621;
  font-weight: 700;
  cursor: pointer;
  outline: none;
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
  gap: 25px;
  flex: 1;
}

.text-group {
  display: flex;
  flex-direction: column;
  gap: 6px;
}

.version-code {
  font-size: 1.8rem;
  font-weight: 700;
  color: #B51621;
  min-width: 110px;
}

.version-title {
  display: flex;
  align-items: center;
  font-size: 1.1rem;
  color: #333;
  font-weight: 600;
}

.status-badge {
  font-size: 0.75rem;
  padding: 4px 10px;
  border-radius: 12px;
  font-weight: bold;
  margin-left: 10px;
  text-transform: uppercase;
  letter-spacing: 0.5px;
}

.status-badge.validated {
  background-color: #e8f5e9;
  color: #2e7d32;
}

.status-badge.pending {
  background-color: #fff3e0;
  color: #e65100;
}

.version-date {
  font-size: 0.9rem;
  color: #64748b;
  font-weight: 400;
}

.year-badge {
  background-color: #e2e8f0;
  color: #475569;
  font-size: 0.8rem;
  font-weight: bold;
  padding: 4px 10px;
  border-radius: 20px;
  margin-left: 15px;
  margin-right: 10px;
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
  background-color: #ffebee;
}

.btn-icon {
  width: 28px;
  height: 28px;
  color: #64748b;
  transition: color 0.2s;
}

.btn-icon-container:hover .btn-icon {
  color: #B51621;
}

.btn-icon-container.validate:hover {
  background-color: #e8f5e9;
}
.btn-icon-container.validate:hover .btn-icon {
  color: #2e7d32;
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