import { reactive } from 'vue';

// On utilise 'reactive' pour que Vue détecte les changements automatiquement
export const mcccStore = reactive({
    // Page 1 : Informations Générales
    resourceCode: '',
    resourceName: '',      // ex: Développement Web
    mainGoal: '',          // Objectif
    year: null,
    semester: null,

    // Page 2 : Heures
    hoursCM: 0,
    hoursTD: 0,
    hoursTP: 0,
    hoursDS: 0,
    hoursDSTP: 0,

    // Page 3 : SAE
    saeCode: '',           // ex: S1.01

    // Page 4 : Compétences
    competenceCode: '' ,    // ex: UE1.1

    competence : []
});