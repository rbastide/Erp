import {reactive} from 'vue';

export const mcccStore = reactive({
    resourceID: 0,
    resourceCode: '',
    minCM: 0,
    minTD: 0,
    minTP: 0,
    minDS: 0,
    minDSTP: 0,
    minTotal: 0,
    creationDate: '',
    editDate: '',
    saeCodes: [],
    levels: [],
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
        if (this.backup && this.backup.length > 10) {
            return;
        }

        const dataToSave = {
            minCM: this.minCM,
            minTD: this.minTD,
            minTP: this.minTP,
            minDS: this.minDS,
            minDSTP: this.minDSTP,
            acsGrouped: JSON.parse(JSON.stringify(this.acsGrouped)),
            saeCodes: JSON.parse(JSON.stringify(this.saeCodes)),
            referents: JSON.parse(JSON.stringify(this.referents)),
            resourceCode: this.resourceCode,
            resourceID: this.resourceID
        };

        this.backup = JSON.stringify(dataToSave);

        this.registerMcccStore();
    },

    restoreBackup() {
        if (!this.backup) return;

        try {
            const oldData = JSON.parse(this.backup);

            this.minCM = oldData.minCM;
            this.minTD = oldData.minTD;
            this.minTP = oldData.minTP;
            this.minDS = oldData.minDS;
            this.minDSTP = oldData.minDSTP;
            this.acsGrouped = oldData.acsGrouped || [];
            this.saeCodes = oldData.saeCodes || [];
            this.referents = oldData.referents || [];
            this.resourceID = oldData.resourceID;
            this.resourceCode = oldData.resourceCode;

            this.backup = null;

            this.registerMcccStore();
            console.log("✅ Restauration effectuée.");
        } catch (e) {
            console.error("Erreur Restauration:", e);
        }
    },

    clearMcccStore() {
        this.resourceCode = '';
        this.resourceID = 0;
        this.minCM = 0;
        this.minTD = 0;
        this.minTP = 0;
        this.minDS = 0;
        this.minDSTP = 0;
        this.minTotal = 0;
        this.saeCodes = [];
        this.acsGrouped = [];
        this.referents = [];
        this.backup = null;
        localStorage.removeItem("mcccStore");
    }
});