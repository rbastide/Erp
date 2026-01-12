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
        if (this.backup) return;

        const dataToSave = {
            resourceCode: this.resourceCode,
            hoursCM: this.hoursCM,
            hoursTD: this.hoursTD,
            hoursTP: this.hoursTP,
            hoursDS: this.hoursDS,
            hoursDSTP: this.hoursDSTP,
            acsGrouped: this.acsGrouped,
            saeCodes: this.saeCodes,
            referents: this.referents,
        };
        this.backup = JSON.stringify(dataToSave);
    },

    restoreBackup() {
        if (this.backup) {
            try {
                const oldData = JSON.parse(this.backup);
                Object.assign(this, oldData);
                this.backup = null;
                this.registerMcccStore();
                console.log("Restauration réussie !");
            } catch (e) {
                console.error("Erreur lors de la restauration du backup", e);
            }
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