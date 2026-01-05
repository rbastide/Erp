<script setup lang="ts">
import { useRouter } from 'vue-router'
import AppHeader from '../Header.vue'

const router = useRouter()

const handleRetour = () =>{ router.push('/home')};
const handleAide = () => {router.push('/aide')};
const handleValider = () => {router.push('/home')};
const handleDeconnexion = () => {router.push('/deconnexion')};


</script>

<template>

  <AppHeader title="Fiche ressource" inline="RX.XX 00/00/0000" />
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