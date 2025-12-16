import { reactive } from 'vue';

const teacher = {
    lastname: '',
    firstname: ''
};

export const mcccStore = reactive({
    resourceCode: '',

    hoursCM: 0,
    hoursTD: 0,
    hoursTP: 0,
    hoursDS: 0,
    hoursDSTP: 0,
    hoursTotal: 0,


    saeCodes: [],
    competences: [],

    referents : [],

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

