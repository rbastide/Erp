<script setup>
  import { useRouter } from 'vue-router';

  const router = useRouter();

  const handleRemplir = () => {
    router.push('/mccc-ressource');
  };

  const handleAfficher = () => {
    router.push('/history-admin');
  };

  const handleInscrire = () => {
    router.push('/new-user');
  };

  const handleAide = () => {
    router.push('/aide');
  };

  const handleDeconnexion = () => {
    router.push('/deconnexion');
  };

  const handleCompetenceCreating = () => {
    router.push('/competence-creating');
  };

  const handleResources = () => {
    router.push('/resources-gestion');
  }

</script>

<template>
  <header class="page-header">
    <div class="container-nom">
      <img src="../assets/uploads/Logo_unilim.png" alt="Logo Unilim"><p>Bonjour, <br>ADMIN</p>
    </div>
    <div @click="handleAide" class="aide">Service d'aide</div>

    <div @click="handleDeconnexion" class="quitter">
      <svg width="48" height="48" viewBox="0 0 48 48" fill="none" xmlns="http://www.w3.org/2000/svg">
        <path d="M18 42H10C8.93913 42 7.92172 41.5786 7.17157 40.8284C6.42143 40.0783 6 39.0609 6 38V10C6 8.93913 6.42143 7.92172 7.17157 7.17157C7.92172 6.42143 8.93913 6 10 6H18M32 34L42 24M42 24L32 14M42 24H18" stroke="white" stroke-width="4" stroke-linecap="round" stroke-linejoin="round"/>
      </svg>
    </div>
  </header>

  <div class="main-content">
    <div class="btn-container">
      <div @click="handleRemplir" class="btn"><p>Remplir les MCCC</p></div>
      <div @click="handleAfficher" class="btn"><p>Afficher les fiches ressource</p></div>
      <div @click="handleInscrire" class="btn"><p>Ajouter des utilisateurs</p></div>
      <div @click="handleCompetenceCreating" class="btn"><p>Ajouter les compétences</p></div>
      <div @click="handleResources" class="btn"><p>Gestion des ressources</p></div>
    </div>
  </div>
</template>

<style scoped>

.page-header {
  position: absolute;
  width: 100%;
  height: 172px;
  left: 0px;
  top: 0px;
  background: #B51621;
  box-sizing: border-box;
}

.container-nom img {
  position: absolute;
  width: 127px;
  height: 127px;
  left: 64px;
  top: 22.5px;
}
.container-nom p{
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

/*Contenu de la page*/

.main-content{
  display: flex;
  width: 90%;
  margin: auto;
  margin-top: 254px;
  justify-content: center;
  align-items: center;
}

.btn-container{
  display: grid;
  grid-template-columns: 1fr 1fr;
  grid-template-rows: auto auto;
  width: fit-content;
  margin-left: auto;
  margin-right: auto;
  justify-content: center;
  align-items: center;
}

.btn {
  box-sizing: border-box;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding: 0 16px;
  width: 360px;
  height: 200px;
  background: #D9D9D9;
  box-shadow: 0 4px 4px rgba(0, 0, 0, 0.25);
  border-radius: 15px;
  margin: 0 50px 100px;
}

.btn:hover {
  background: #999999;
  transform: translateY(-4px);
  cursor: pointer;
}

.btn p {
  width: 328px;
  height: 200px;
  font-family: 'Roboto', sans-serif;
  font-style: normal;
  font-weight: 400;
  font-size: 36px;
  line-height: 50px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #E92533;
  z-index: 1;
}
</style>

