<script setup lang="ts">
import { ref, nextTick } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

const handleRetour = () => router.push('/home')
const handleAide = () => router.push('/aide')
const handleValider = () => {router.push('/home');
};
const handleDeconnexion = () => router.push('/deconnexion')

// CM
const cmContents = ref<string[]>([''])
const cmRefs = ref<HTMLTextAreaElement[]>([])
const addCM = async () => {
  cmContents.value.push('')
  await nextTick()
  cmRefs.value[cmRefs.value.length - 1]?.focus()
}

// TD
const tdContents = ref<string[]>([''])
const tdRefs = ref<HTMLTextAreaElement[]>([])
const addTD = async () => {
  tdContents.value.push('')
  await nextTick()
  tdRefs.value[tdRefs.value.length - 1]?.focus()
}

// TP
const tpContents = ref<string[]>([''])
const tpRefs = ref<HTMLTextAreaElement[]>([])
const addTP = async () => {
  tpContents.value.push('')
  await nextTick()
  tpRefs.value[tpRefs.value.length - 1]?.focus()
}

// DS
const dsContents = ref<string[]>([''])
const dsRefs = ref<HTMLTextAreaElement[]>([])
const addDS = async () => {
  dsContents.value.push('')
  await nextTick()
  dsRefs.value[dsRefs.value.length - 1]?.focus()
}

//Upgrades
const upgradesContents = ref<string[]>([''])
const upgradesRefs = ref<HTMLTextAreaElement[]>([])
const addUpgrades = async () => {
  upgradesContents.value.push('')
  await nextTick()
  upgradesRefs.value[upgradesRefs.value.length - 1]?.focus()
}

//Retours Professeurs
const educationalFeedbackContents = ref<string[]>([''])
const educationalFeedbackRefs = ref<HTMLTextAreaElement[]>([])
const addEducationalFeedback = async () => {
  educationalFeedbackContents.value.push('')
  await nextTick()
  educationalFeedbackRefs.value[educationalFeedbackRefs.value.length - 1]?.focus()
}

//Retours Étudiants
const studentFeedbackContents = ref<string[]>([''])
const studentFeedbackRefs = ref<HTMLTextAreaElement[]>([])
const addStudentFeedback = async () => {
  studentFeedbackContents.value.push('')
  await nextTick()
  studentFeedbackRefs.value[studentFeedbackRefs.value.length - 1]?.focus()
}
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
      <p class = "title">Veuillez saisir vos heures de CM, TD et TP : *</p>
      <div class = "hour-summary">
        <div class = "listing-hours">
          <div class="grid">
            <div class = "type-of-hour">
              <p class = "title-hour">CM</p>
              <input class="grey-square" type="number">
            </div>
            <div class = "type-of-hour">
              <p class = "title-hour">TD</p>
              <input class="grey-square" type="number">
            </div>
            <div class = "type-of-hour">
              <p class = "title-hour">DS</p>
              <input class="grey-square" type="number">
            </div>
          </div>
          <div class="grid">
            <div class= "type-of-hour">
              <p class= "title-hour">TP</p>
              <input class="grey-square" type="number">
            </div>
            <div class= "type-of-hour">
              <p class= "title-hour">DS/TP</p>
              <input class="grey-square" type="number">
            </div>
            <div class= "type-of-hour">
              <p class= "title-hour">Total</p>
              <input class="grey-square" type="number">
            </div>
          </div>
        </div>
      </div>
      <div class="hour-per-students">
        <p class = "title">Veuillez saisir le nombre d'heures par étudiant : *</p>
        <input class="grey-square" type="number">
      </div>
      <p class="title">Veuillez saisir le contenu pédagogique : *</p>

      <div class="pedagogic-contents">

        <div class="hour-type">
          <p class="title">CM</p>
          <textarea
              v-for="(content, index) in cmContents"
              :key="index"
              v-model="cmContents[index]"
              class="grey-big-square"
              :ref="el => { if (el) cmRefs[index] = el as HTMLTextAreaElement }"
              rows="4"
          ></textarea>
          <svg class="add-button" @click="addCM" width="72" height="70" viewBox="0 0 72 70" fill="none" xmlns="http://www.w3.org/2000/svg">
            <circle cx="36" cy="35" r="34.5" fill="#E92533" stroke="#B51621"/>
            <path d="M35.617 19.3333V51.4074M19.5393 35.3704H51.6133" stroke="white" stroke-width="6" stroke-linecap="round"/>
          </svg>
        </div>

        <div class="hour-type">
          <p class="title">TD</p>
          <textarea
              v-for="(content, index) in tdContents"
              :key="index"
              v-model="tdContents[index]"
              class="grey-big-square"
              :ref="el => { if (el) tdRefs[index] = el as HTMLTextAreaElement }"
              rows="4"
          ></textarea>
          <svg class="add-button" @click="addTD" width="72" height="70" viewBox="0 0 72 70" fill="none" xmlns="http://www.w3.org/2000/svg">
            <circle cx="36" cy="35" r="34.5" fill="#E92533" stroke="#B51621"/>
            <path d="M35.617 19.3333V51.4074M19.5393 35.3704H51.6133" stroke="white" stroke-width="6" stroke-linecap="round"/>
          </svg>
        </div>

        <div class="hour-type">
          <p class="title">TP</p>
          <textarea
              v-for="(content, index) in tpContents"
              :key="index"
              v-model="tpContents[index]"
              class="grey-big-square"
              :ref="el => { if (el) tpRefs[index] = el as HTMLTextAreaElement }"
              rows="4"
          ></textarea>
          <svg class="add-button" @click="addTP" width="72" height="70" viewBox="0 0 72 70" fill="none" xmlns="http://www.w3.org/2000/svg">
            <circle cx="36" cy="35" r="34.5" fill="#E92533" stroke="#B51621"/>
            <path d="M35.617 19.3333V51.4074M19.5393 35.3704H51.6133" stroke="white" stroke-width="6" stroke-linecap="round"/>
          </svg>
        </div>

        <div class="hour-type">
          <p class="title">DS</p>
          <textarea
              v-for="(content, index) in dsContents"
              :key="index"
              v-model="dsContents[index]"
              class="grey-big-square"
              :ref="el => { if (el) dsRefs[index] = el as HTMLTextAreaElement }"
              rows="4"
          ></textarea>
          <svg class="add-button" @click="addDS" width="72" height="70" viewBox="0 0 72 70" fill="none" xmlns="http://www.w3.org/2000/svg">
            <circle cx="36" cy="35" r="34.5" fill="#E92533" stroke="#B51621"/>
            <path d="M35.617 19.3333V51.4074M19.5393 35.3704H51.6133" stroke="white" stroke-width="6" stroke-linecap="round"/>
          </svg>
        </div>
      </div>

      <div class="educational-feedback">
        <p class = "title">Veuillez saisir le retour pédagogique des professeurs :</p>
        <textarea
            v-for="(content, index) in educationalFeedbackContents"
            :key="index"
            v-model="educationalFeedbackContents[index]"
            class="grey-big-square"
            :ref="el => { if (el) educationalFeedbackRefs[index] = el as HTMLTextAreaElement }"
            rows="4"
        ></textarea>
        <svg class="add-button" @click="addEducationalFeedback" width="72" height="70" viewBox="0 0 72 70" fill="none" xmlns="http://www.w3.org/2000/svg">
          <circle cx="36" cy="35" r="34.5" fill="#E92533" stroke="#B51621"/>
          <path d="M35.617 19.3333V51.4074M19.5393 35.3704H51.6133" stroke="white" stroke-width="6" stroke-linecap="round"/>
        </svg>
      </div>

      <div class="student-feedback">
        <p class = "title">Veuillez saisir le retour des étudiants :</p>
        <textarea
            v-for="(content, index) in studentFeedbackContents"
            :key="index"
            v-model="studentFeedbackContents[index]"
            class="grey-big-square"
            :ref="el => { if (el) studentFeedbackRefs[index] = el as HTMLTextAreaElement }"
            rows="4"
        ></textarea>
        <svg class="add-button" @click="addStudentFeedback" width="72" height="70" viewBox="0 0 72 70" fill="none" xmlns="http://www.w3.org/2000/svg">
          <circle cx="36" cy="35" r="34.5" fill="#E92533" stroke="#B51621"/>
          <path d="M35.617 19.3333V51.4074M19.5393 35.3704H51.6133" stroke="white" stroke-width="6" stroke-linecap="round"/>
        </svg>
      </div>

      <div class="upgrades">
        <p class = "title">Veuillez saisir les améliorations à apporter :</p>
        <textarea
            v-for="(content, index) in upgradesContents"
            :key="index"
            v-model="upgradesContents[index]"
            class="grey-big-square"
            :ref="el => { if (el) upgradesRefs[index] = el as HTMLTextAreaElement }"
            rows="4"
        ></textarea>
        <svg class="add-button" @click="addUpgrades" width="72" height="70" viewBox="0 0 72 70" fill="none" xmlns="http://www.w3.org/2000/svg">
          <circle cx="36" cy="35" r="34.5" fill="#E92533" stroke="#B51621"/>
          <path d="M35.617 19.3333V51.4074M19.5393 35.3704H51.6133" stroke="white" stroke-width="6" stroke-linecap="round"/>
        </svg>
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
.hour-per-students input{
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
  display: block;
  width: 100%;
  margin-top: 50px;
}

.educational-feedback .title, .student-feedback .title, .upgrades .title {
  /* Le style général .title s'applique ici et assure l'alignement à gauche */
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