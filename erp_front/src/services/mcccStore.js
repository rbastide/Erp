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
    hoursTotal: 0,


    saeCodes: [],
    competence : [],

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

    clearMcccStore(){
        this.resourceCode = '';
        this.resourceName = '';
        this.mainGoal = '';
        this.year = null;
        this.semester = null;

        this.hoursCM = 0;
        this.hoursTD = 0;
        this.hoursTP = 0;
        this.hoursDS = 0;
        this.hoursDSTP = 0;
        this.hoursTotal = 0;

        this.saeCodes = [];
        this.competence = [];
        this.referents = [];
    },
});
