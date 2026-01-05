<script setup>
import { useRouter } from 'vue-router';
import { onMounted, ref, nextTick } from 'vue';
import { mcccStore } from "@/services/mcccStore.js";
import AppHeader from './Header.vue';

const router = useRouter();
const errorMessage = ref('');
const saeList = ref([]);
const inputRefs = ref([]);

onMounted(() => {
  mcccStore.loadMcccStore();

  if (mcccStore.saeCodes && mcccStore.saeCodes.length > 0) {
    saeList.value = [...mcccStore.saeCodes];
  } else {
    saeList.value = [''];
  }

  nextTick(() => {
    if (inputRefs.value[0]) inputRefs.value[0].focus();
  });
});

const handleValider = () => {
  const filtered = saeList.value
      .map(s => s.trim().toUpperCase())
      .filter(s => s !== '');

  if (filtered.length === 0) {
    errorMessage.value = "Veuillez saisir au moins une SAÉ.";
    return;
  }

  mcccStore.saeCodes = [...new Set(filtered)];
  mcccStore.registerMcccStore();
  router.push('/mccc-menu');
};

const addSaeField = () => {
  errorMessage.value = '';
  const lastVal = saeList.value[saeList.value.length - 1];

  if (lastVal.trim() === '') {
    errorMessage.value = "Le champ actuel est vide.";
    if (inputRefs.value[saeList.value.length - 1]) {
      inputRefs.value[saeList.value.length - 1].focus();
    }
    return;
  }

  saeList.value.push('');

  nextTick(() => {
    const newIndex = saeList.value.length - 1;
    if (inputRefs.value[newIndex]) {
      inputRefs.value[newIndex].focus();
    }
  });
};

const removeSaeField = (index) => {
  if (saeList.value.length > 1) {
    saeList.value.splice(index, 1);
  } else {
    saeList.value[0] = '';
  }
};
</script>

<template>
  <AppHeader title="Remplissage des SAÉ" :inline="mcccStore.resourceCode"/>

  <main class="main-content">
    <div class="content-wrapper">
      <div class="sae-grid">
        <div v-for="(sae, index) in saeList" :key="index" class="sae-card">
          <div class="input-container">
            <input
                v-model="saeList[index]"
                ref="inputRefs"
                class="sae-input"
                placeholder="Ex: 1.01"
                maxlength="4"
                @keyup.enter="addSaeField"
            />
            <button v-if="saeList.length > 1 || saeList[0] !== ''"
                    @click="removeSaeField(index)"
                    class="delete-btn">
              &times;
            </button>
          </div>
        </div>

        <button class="add-card" @click="addSaeField">
          <span class="plus-icon">+</span>
          <span class="add-text">Ajouter</span>
        </button>
      </div>

      <p v-if="errorMessage" class="error-message">{{ errorMessage }}</p>

      <div class="footer-actions">
        <button @click="handleValider" class="btn-main">Valider</button>
        <button @click="router.push('/mccc-menu')" class="btn-sub">Annuler</button>
      </div>
    </div>
  </main>
</template>

<style scoped>
.main-content {
  padding-top: 200px;
  min-height: 100vh;
  background-color: #fcfcfc;
  font-family: 'Roboto', sans-serif;
}

.content-wrapper {
  max-width: 1000px;
  margin: 0 auto;
  padding: 0 40px;
  margin-top: 50px;
}

.title {
  font-size: 26px;
  color: #E92533;
  margin-bottom: 40px;
  font-weight: 500;
}

.sae-grid {
  display: flex;
  flex-wrap: wrap;
  gap: 25px;
  margin-bottom: 50px;
}

.sae-card {
  background: white;
  width: 180px;
  height: 100px;
  border-radius: 15px;
  display: flex;
  align-items: center;
  justify-content: center;
  box-shadow: 0 8px 20px rgba(0,0,0,0.06);
  border: 1px solid #eee;
  position: relative;
  transition: transform 0.2s, border-color 0.2s;
}

.sae-card:focus-within {
  border-color: #B51621;
  transform: translateY(-4px);
}

.sae-input {
  width: 80%;
  border: none;
  background: #f8f9fa;
  padding: 10px;
  border-radius: 8px;
  font-size: 24px;
  font-weight: 800;
  text-align: center;
  color: #333;
  text-transform: uppercase;
}

.sae-input:focus {
  outline: none;
  background: #fff;
}

.delete-btn {
  position: absolute;
  top: -10px;
  right: -10px;
  background: #B51621;
  color: white;
  border: none;
  width: 25px;
  height: 25px;
  border-radius: 50%;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 18px;
  box-shadow: 0 2px 5px rgba(0,0,0,0.2);
}

.add-card {
  width: 180px;
  height: 100px;
  border: 2px dashed #B51621;
  border-radius: 15px;
  background: transparent;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  color: #B51621;
  transition: all 0.2s;
}

.add-card:hover {
  background: #fff5f5;
  transform: translateY(-2px);
}

.plus-icon { font-size: 30px; font-weight: bold; }
.add-text { font-weight: 600; font-size: 14px; }

.error-message {
  color: #E92533;
  text-align: center;
  font-weight: bold;
  margin-bottom: 30px;
}

.footer-actions {
  display: flex;
  justify-content: center;
  gap: 20px;
}

.btn-main {
  background: #B51621;
  color: white;
  border: none;
  padding: 14px 50px;
  border-radius: 10px;
  font-weight: bold;
  cursor: pointer;
}

.btn-sub {
  background: white;
  border: 2px solid #B51621;
  color: #B51621;
  padding: 14px 50px;
  border-radius: 10px;
  font-weight: bold;
  cursor: pointer;
}
</style>