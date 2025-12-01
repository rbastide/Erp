<script setup>
import { useRouter } from 'vue-router';
import { onMounted, nextTick } from 'vue'; // Importation nécessaire

const router = useRouter();

const handleRetour = () => {
  router.push('/mccc-menu');
};

const handleValider = () => {
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
    // Supprime les écouteurs existants pour éviter la duplication lors de la réexécution
    box.removeEventListener('input', handleInput);
    box.removeEventListener('keydown', handleKeydown);

    // Ajout des nouveaux écouteurs
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

      // Assurez-vous qu'il y a des nœuds enfants avant d'essayer de les sélectionner
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
  // Le DOM est garanti d'être prêt ici
  setupInputLimits();
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

    // Après l'ajout du nouvel élément, on s'assure que le DOM est mis à jour
    // puis on applique les limites au nouveau carré gris.
    nextTick(() => {
      setupInputLimits();
    });
  }
  else{
    console.error("Impossible de trouver le carré gris à dupliquer.");
  }
}
</script>

<template>
  <div class = "ressource-page" >

    <header class="page-header">
      <div class="container-nom">
        <img src="../assets/uploads/Logo_unilim.png" alt="Logo Unilim">
        <p>SAÉ/Compétences <br> pour RX.XX</p>
      </div>
      <div @click="handleAide" class="aide">Service d'aide</div>
      <div @click="handleDeconnexion" class="quitter">
        <svg width="48" height="48" viewBox="0 0 48 48" fill="none" xmlns="http://www.w3.org/2000/svg">
          <path d="M18 42H10C8.93913 42 7.92172 41.5786 7.17157 40.8284C6.42143 40.0783 6 39.0609 6 38V10C6 8.93913 6.42143 7.92172 7.17157 7.17157C7.92172 6.42143 8.93913 6 10 6H18M32 34L42 24M42 24L32 14M42 24H18" stroke="white" stroke-width="4" stroke-linecap="round" stroke-linejoin="round"/>
        </svg>
      </div>
    </header>

    <main class = "main-content">
      <div class="sae">
        <p class="title">Veuillez saisir la/les SAÉ(s) concernée(s) :</p>
        <div id="sae-text">
          <div class = "grey-square" contenteditable="true"></div>
        </div>
        <svg class = "add-button" @click="duplicateGreySquare('sae-text')" width="72" height="70" viewBox="0 0 72 70" fill="none" xmlns="http://www.w3.org/2000/svg">
          <path d="M36 23.3333V46.6667M24 35H48M15 8.75H57C60.3137 8.75 63 11.3617 63 14.5833V55.4167C63 58.6383 60.3137 61.25 57 61.25H15C11.6863 61.25 9 58.6383 9 55.4167V14.5833C9 11.3617 11.6863 8.75 15 8.75Z" stroke="#1E1E1E" stroke-width="4" stroke-linecap="round" stroke-linejoin="round"/>
        </svg>
      </div>

      <div class="container-btn">
        <div @click="handleValider" class="btn-sys">Valider</div>
        <div @click="handleRetour" class="btn-sys">Quitter</div>
      </div>
    </main>
  </div>
</template>

<style scoped>
.ressource-page {
  width: 100%;
  overflow-x: hidden;
}

/* header */
.page-header {
  position: relative;
  width: 100%;
  height: 172px;
  background: #B51621;
}

.container-nom {
  display: flex;
  align-items: center;
  position: absolute;
  left: 64px;
  top: 22.5px;
}

.container-nom img {
  width: 127px;
  height: 127px;
}

.container-nom p {
  margin-left: 15px;
  width: auto;
  max-width: 600px;
  height: 120px;

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
}
.quitter:hover{
  cursor: pointer;
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
}
.aide:hover{
  cursor: pointer;
}

/*Main*/
.main-content{
  position: relative;
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
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
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
  margin-top: 30px;
  transition: transform 0.2s;
  display: block;
  margin-left: auto;
  margin-right: auto;
  margin-bottom: 50px;
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