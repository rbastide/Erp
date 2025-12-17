<script setup>
import { useRouter } from 'vue-router';
import { onMounted, nextTick } from 'vue';
import {mcccStore} from "@/services/mcccStore.js";
import AppHeader from './Header.vue';

const router = useRouter();

const handleRetour = () => {
  router.push('/mccc-menu');
};

const handleValider = () => {
  const boxes = document.querySelectorAll('.grey-square');

  const uniqueSaeCodes = new Set();

  boxes.forEach(box => {
    const saeText = box.textContent.trim();

    if (box.textContent.length !== 0) {
      uniqueSaeCodes.add(saeText);
    }
  });

  mcccStore.saeCodes = Array.from(uniqueSaeCodes);
  mcccStore.registerMcccStore();

  router.push('/mccc-menu');
};

const handleAide = () => {
  router.push('/aide');
};

const handleDeconnexion = () => {
  router.push('/deconnexion');
};

const setupInputLimits = () => {
  const boxes = document.querySelectorAll('.grey-square');
  const maxChars = 5;

  boxes.forEach(box => {
    box.removeEventListener('input', handleInput);
    box.removeEventListener('keydown', handleKeydown);

    box.addEventListener('input', handleInput);
    box.addEventListener('keydown', handleKeydown);
  });

  function handleInput(e) {
    const box = e.target;
    let text = box.textContent;

    if (text.length > maxChars) {
      box.textContent = text.substring(0, maxChars);

      const range = document.createRange();
      const sel = window.getSelection();

      if (box.firstChild) {
        range.setStart(box.firstChild, box.textContent.length);
        range.collapse(true);
        sel.removeAllRanges();
        sel.addRange(range);
      }
    }
  }

  function handleKeydown(e) {
    if (e.key === "Enter") e.preventDefault();
  }
};

onMounted(() => {
  setupInputLimits();

  mcccStore.loadMcccStore();
});

function duplicateGreySquare(containerId){
  const container = document.getElementById(containerId);

  if (!container) {
    console.error(`Conteneur '${containerId}' introuvable.`);
    return;
  }

  const originalObject = container.querySelector('.grey-square:last-child');

  if (originalObject) {
    const newObject = originalObject.cloneNode(true);
    newObject.textContent = '';
    container.appendChild(newObject);
    newObject.focus();
    nextTick(() => {
      setupInputLimits();
    });
  }
}
</script>

<template>
  <AppHeader title="Remplissage des SAE" :inline="mcccStore.resourceCode"/>
  <main class = "main-content">
    <div class="sae">
      <p class="title">Veuillez saisir la/les SAÉ(s) concernée(s) :</p>
      <div id="sae-text">
        <div id="sae-test" v-for="saeCode in mcccStore.saeCodes" :key="saeCode">
          <div class = "grey-square" contenteditable="true">{{ saeCode }}</div>
        </div>
        <div class = "grey-square" contenteditable="true"></div>
      </div>
      <svg class = "add-button" @click="duplicateGreySquare('sae-text')" width="72" height="70" viewBox="0 0 72 70" xmlns="http://www.w3.org/2000/svg">
        <path d="M36 23.3333V46.6667M24 35H48M15 8.75H57C60.3137 8.75 63 11.3617 63 14.5833V55.4167C63 58.6383 60.3137 61.25 57 61.25H15C11.6863 61.25 9 58.6383 9 55.4167V14.5833C9 11.3617 11.6863 8.75 15 8.75Z" stroke="#1E1E1E" stroke-width="4" stroke-linecap="round" stroke-linejoin="round"/>
      </svg>
    </div>

    <div class="container-btn">
      <div @click="handleValider" class="btn-sys">Valider</div>
      <div @click="handleRetour" class="btn-sys">Annuler</div>
    </div>
  </main>
</template>

<style scoped>
.main-content{
  position: relative;
  padding-top: 172px;
}
.title{
  width: 100%;
  height: 66px;
  margin-top: 20px;
  margin-left: 8%;

  font-family: 'Roboto', sans-serif;
  font-style: normal;
  font-weight: 400;
  font-size: 32px;
  line-height: 38px;

  color: #E92533;
}


#sae-text{
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 15px;
}

.grey-square{
  box-sizing: border-box;
  width: 400px;
  background: #D9D9D9;
  padding: 30px 60px;
  border: 1px solid rgba(0, 0, 0, 0.25);
  box-shadow: 0 4px 4px rgba(0, 0, 0, 0.25);
  border-radius: 15px;
  font-size: 30px;
  text-align: center;
  margin-top: 20px;
  font-family: 'Roboto', sans-serif;
}

.add-button {
  background: none;
  border: none;
  cursor: pointer;
  padding: 0;
  transition: transform 0.2s;
  display: block;
  margin: 30px auto 50px;
}
.add-button svg {
  height: 50px;
  width: 50px;
}
.add-button:hover {
  transform: scale(1.1);
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