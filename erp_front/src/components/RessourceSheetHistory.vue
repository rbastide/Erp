<script setup lang="ts">
import { useRouter } from 'vue-router'

const router = useRouter()

const handleRetour = () =>{ router.push('/home')};
const handleAide = () => {router.push('/aide')};
const handleValider = () => {router.push('/home')};

const handleDeconnexion = () => {router.push('/deconnexion')};

</script>

<template>
  <header class="page-header">
    <div class="container-nom">
      <img src="../assets/uploads/Logo_unilim.png" alt="Logo Unilim"><p>Remplir votre fiche ressource</p>
    </div>
    <div @click="handleAide" class="aide">Service d'aide</div>
    <div @click="handleDeconnexion" class="quitter">
      <svg width="48" height="48" viewBox="0 0 48 48" fill="none" xmlns="http://www.w3.org/2000/svg">
        <path d="M18 42H10C8.93913 42 7.92172 41.5786 7.17157 40.8284C6.42143 40.0783 6 39.0609 6 38V10C6 8.93913 6.42143 7.92172 7.17157 7.17157C7.92172 6.42143 8.93913 6 10 6H18M32 34L42 24M42 24L32 14M42 24H18" stroke="white" stroke-width="4" stroke-linecap="round" stroke-linejoin="round"/>
      </svg>
    </div>
  </header>

  <div class = "ressource-page">
    <main class = "main-content">
      <div class="required_file">
        <p id="star">* </p>
        <p>champs obligatoires</p>
      </div>
      <p class = "title">Voici le nombre d'heures de CM, TD et TP : *</p>
      <div class = "hour-summary">
        <div class = "listing-hours">
          <div class="grid">
            <div class = "type-of-hour">
              <p class = "title-hour">CM</p>
              <p class="grey-square" type="number"></p>
            </div>
            <div class = "type-of-hour">
              <p class = "title-hour">TD</p>
              <p class="grey-square" type="number"></p>
            </div>
            <div class = "type-of-hour">
              <p class = "title-hour">DS</p>
              <p class="grey-square" type="number"></p>
            </div>
          </div>
          <div class="grid">
            <div class= "type-of-hour">
              <p class= "title-hour">TP</p>
              <p class="grey-square" type="number"></p>
            </div>
            <div class= "type-of-hour">
              <p class= "title-hour">DS/TP</p>
              <p class="grey-square" type="number"></p>
            </div>
            <div class= "type-of-hour">
              <p class= "title-hour">Total</p>
              <p class="grey-square" type="number"></p>
            </div>
          </div>
        </div>
      </div>
      <div class="hour-per-students">
        <p class = "title">Voici le nombre d'heures par étudiant : *</p>
        <p class="grey-square" id="number_of_hour" type="number"></p>
      </div>
      <p class="title">Voici le contenu pédagogique : *</p>

      <div class="pedagogic-contents">

        <div class="hour-type">
          <p class="title">CM</p>
          <textarea class="grey-square"></textarea>
        </div>

        <div class="hour-type">
          <p class="title">TD</p>
          <textarea class="grey-square"></textarea>
        </div>

        <div class="hour-type">
          <p class="title">TP</p>
          <textarea class="grey-square"></textarea>
        </div>

        <div class="hour-type">
          <p class="title">DS</p>
          <textarea class="grey-square"></textarea>
        </div>
      </div>

      <div class="educational-feedback">
        <p class = "title">Voici le retour pédagogique des professeurs :</p>
        <textarea class="grey-square"></textarea>
      </div>

      <div class="student-feedback">
        <p class = "title">Voici le retour des étudiants :</p>
        <textarea class="grey-square"></textarea>
      </div>

      <div class="upgrades">
        <p class = "title">Voici les améliorations à apporter :</p>
        <textarea class="grey-square"></textarea>

      </div>
      <div class="container-btn">
        <div @click="handleValider" class="btn-sys">Valider</div>
        <div @click="handleRetour" class="btn-sys">Annuler</div>
      </div>
    </main>
  </div>

</template>

<style scoped>

.ressource-page {
  width: 100%;
  overflow-x: hidden;
}

.main-content{
  width: 100%;
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



/* Hour */
.required_file{
  text-align: right;
  margin-right: 20px;
  margin-top: 10px;
}
.required_file p{
  display: inline;
  font-family: 'Roboto', sans-serif;
}

#star{
  color: #E92533;
  font-size: 25px;
}

.title{
  width: 100%;
  margin-top: 40px;
  font-family: 'Roboto', sans-serif;
  font-style: normal;
  font-weight: 400;
  font-size: 32px;
  line-height: 38px;
  text-align: left;
  margin-left: 30px;

  color: #E92533;
}
.grid {
  position: relative;
  display: grid;
  grid-template-columns: auto auto auto;
  padding: 10px;
  justify-content: center;
}

.listing-hours{
  padding: 20px;
}

.type-of-hour {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 10px;
}

.title-hour{
  font-size: 30px;
  margin-bottom: 10px;
  font-family: 'Roboto', sans-serif;
}

.grey-square{
  box-sizing: border-box;
  width: 200px;
  background: #D9D9D9;
  padding: 30px 60px;
  border: 1px solid rgba(0, 0, 0, 0.25);
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  border-radius: 15px;
  font-size: 30px;
  text-align: center;
}


.total{
  margin-top: 50px;
  font-size: 50px;
  font-family: 'Roboto', sans-serif;
  margin-bottom: 80px;
  text-align: center;
  margin-left: 70px;
}


/* Hour students */
#number_of_hour{
  margin-left: 750px;
  margin-bottom: 50px;

}

/* Pedagogic content */
.pedagogic-contents{
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
}

.hour-type{
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
  max-width: 900px;
  margin-bottom: 40px;
}

.hour-type .title{
  text-align: center;
  margin-left: 0;
  color: black;
  margin-bottom: 20px;
}


/* Educational-feedback, Student-feedback, Upgrades */
.educational-feedback, .student-feedback, .upgrades {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.grey-big-square{
  box-sizing: border-box;
  width: 800px;
  background: #D9D9D9;
  padding: 20px;
  border: 1px solid rgba(0, 0, 0, 0.25);
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  border-radius: 15px;
  font-size: 24px;
  text-align: left;
  margin-bottom: 20px;
  resize: vertical;
  display: block;
  margin-left: auto;
  margin-right: auto;
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
</style>