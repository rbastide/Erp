import { reactive } from 'vue';

export const mcccStore = reactive({
    resourceCode: '',
    resourceName: '',
    mainGoal: '',
    year: null,
    semester: null,

    hoursCM: 0,
    hoursTD: 0,
    hoursTP: 0,
    hoursDS: 0,
    hoursDSTP: 0,


    saeCode: '',
    competenceCode: '' ,
    competence : [],

    registerMcccStore(){
        localStorage.setItem("mcccStore",JSON.stringify(this));
    },

    loadMcccStore() {
        const storedData = localStorage.getItem("mcccStore");
        if (storedData) {
            const parsedData = JSON.parse(storedData);
            Object.assign(this, parsedData);
        }
    },




});

