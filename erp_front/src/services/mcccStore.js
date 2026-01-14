import {reactive} from 'vue';

export const mcccStore = reactive({
    resourceID: 0,
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
            hoursCM: this.hoursCM,
            hoursTD: this.hoursTD,
            hoursTP: this.hoursTP,
            hoursDS: this.hoursDS,
            hoursDSTP: this.hoursDSTP,
            acsGrouped: JSON.parse(JSON.stringify(this.acsGrouped)),
            saeCodes: JSON.parse(JSON.stringify(this.saeCodes)),
            referents: JSON.parse(JSON.stringify(this.referents)),
            resourceCode: this.resourceCode,
            resourceID: this.resourceID
        };

        this.backup = JSON.stringify(dataToSave);

        this.registerMcccStore();
        console.log("🔒 Backup V0 créé et sauvegardé.");
    },

    restoreBackup() {
        if (!this.backup) return;

        try {
            const oldData = JSON.parse(this.backup);

            this.hoursCM = oldData.hoursCM;
            this.hoursTD = oldData.hoursTD;
            this.hoursTP = oldData.hoursTP;
            this.hoursDS = oldData.hoursDS;
            this.hoursDSTP = oldData.hoursDSTP;
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
        this.hoursCM = 0;
        this.hoursTD = 0;
        this.hoursTP = 0;
        this.hoursDS = 0;
        this.hoursDSTP = 0;
        this.hoursTotal = 0;
        this.saeCodes = [];
        this.acsGrouped = [];
        this.referents = [];
        this.backup = null;
        localStorage.removeItem("mcccStore");
    }
});