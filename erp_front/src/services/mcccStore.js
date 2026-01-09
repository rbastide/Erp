import { reactive } from 'vue';

export const mcccStore = reactive({
    resourceCode: '',
    hoursCM: 0,
    hoursTD: 0,
    hoursTP: 0,
    hoursDS: 0,
    hoursDSTP: 0,
    hoursTotal: 0,
    creationDate: '',
    editDate: '',
    saeCodes: [],
    niveaux: [],
    ue: [],
    acs: [],
    acsGrouped: [],
    referents: [],

    backup: null,

    registerMcccStore() {
        localStorage.setItem("mcccStore", JSON.stringify(this));
    },

    loadMcccStore() {
        const saved = localStorage.getItem("mcccStore");
        if (saved) {
            const data = JSON.parse(saved);
            Object.assign(this, data);
        }
    },


    saveBackup() {
        const { backup, ...dataToSave } = this;
        this.backup = JSON.stringify(dataToSave);
    },

    restoreBackup() {
        if (this.backup) {
            const oldData = JSON.parse(this.backup);
            Object.assign(this, oldData);
            this.backup = null;
            this.registerMcccStore();
        }
    },

    clearMcccStore() {
        this.resourceCode = '';
        this.hoursCM = 0;
        this.hoursTD = 0;
        this.hoursTP = 0;
        this.hoursDS = 0;
        this.hoursDSTP = 0;
        this.hoursTotal = 0;
        this.creationDate = '';
        this.editDate = '';
        this.saeCodes = [];
        this.niveaux = [];
        this.ue = [];
        this.acs = [];
        this.acsGrouped = [];
        this.referents = [];
        this.backup = null;

        localStorage.removeItem("mcccStore");
    }
});