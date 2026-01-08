<script setup>
import { ref, computed } from 'vue';
import { useRouter } from 'vue-router';
import AppHeader from '../App/Header.vue';
import Sidebar from '../App/Sidebar.vue';

const router = useRouter();

const searchQuery = ref('');

const handleRetour = () => {
  router.back();
};

const handleShow = () => {
  router.push('/ressource-sheet-history');
};

const versions = ref([
  { code: 'R1.01', date: '30/10/2022' },
  { code: 'R1.02', date: '15/11/2019' },
  { code: 'R1.03', date: '01/01/2018' },
  { code: 'R2.01', date: '05/08/2017' },
  { code: 'R2.02', date: '06/06/2017' },
  { code: 'R2.04', date: '08/05/2017' },
  { code: 'R2.05', date: '01/12/2016' },
  { code: 'R3.02', date: '15/05/2016' },
  { code: 'R3.03', date: '18/04/2016' },
  { code: 'R4.01', date: '14/02/2016' },
]);

const filteredVersions = computed(() => {
  const query = searchQuery.value.toLowerCase().trim();

  return versions.value.filter(item => {
    return item.code.toLowerCase().includes(query) ||
        item.date.toLowerCase().includes(query);
  });
});

const clearSearch = () => {
  searchQuery.value = '';
};
</script>

<template>
  <Sidebar/>
  <AppHeader title="Historique des fiches ressources"/>

  <main class="main-content">
    <div class="container">
      <div class="version-list-container">
        <div v-if="filteredVersions.length === 0" class="no-result">
          <p>Aucun résultat pour "<strong>{{ searchQuery }}</strong>"</p>
          <button @click="clearSearch" class="btn-clear-link">Effacer la recherche</button>
        </div>

        <ul class="version-list">
          <li
              v-for="(item, index) in filteredVersions"
              :key="index"
              @click="handleShow"
              class="version-item"
          >
            <span class="version-code">{{ item.code }}</span>
            <span class="version-date">{{ item.date }}</span>
          </li>
        </ul>
      </div>
    </div>

    <footer class="sticky-footer">
      <div class="footer-content">

        <div class="search-wrapper">
          <input
              v-model="searchQuery"
              type="text"
              placeholder="Chercher par nom (ex : R1.02) ou par date (ex : 30/01/2022)"
              class="search-input"
          />
          <span class="search-icon">🔍</span>
          <button v-if="searchQuery" @click="clearSearch" class="clear-input-btn">✕</button>
        </div>

        <button @click="handleRetour" class="btn-quitter">Quitter</button>
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

.description {
  color: #E92533;
  font-size: 1.8rem;
  font-weight: 500;
  margin-bottom: 30px;
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

.version-code {
  font-size: 1.5rem;
  font-weight: 700;
  color: #B51621;
}

.version-date {
  font-size: 1.2rem;
  color: #64748b;
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
  margin-top: 10px;
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
  padding: 12px 45px 12px 45px; /* Espace pour icône et bouton X */
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
  font-size: 1.2rem;
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
  display: flex;
  align-items: center;
  justify-content: center;
}

.clear-input-btn:hover {
  background: #cbd5e1;
}

.btn-quitter {
  background-color: #B51621;
  color: #FFFFFF;
  padding: 12px 40px;
  border: none;
  border-radius: 10px;
  font-weight: bold;
  cursor: pointer;
  white-space: nowrap;
}

.btn-quitter:hover {
  background-color: #8e111a;
}

@media (max-width: 600px) {
  .search-wrapper { max-width: 200px; }
  .btn-quitter { padding: 12px 20px; }
}
</style>