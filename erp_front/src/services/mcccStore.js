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

    // Le backup commence null. S'il est chargé du disque, il sera écrasé par la chaîne JSON.
    backup: null,

    registerMcccStore() {
        // Sauvegarde l'état EXACT actuel, y compris le backup s'il existe
        localStorage.setItem("mcccStore", JSON.stringify(this));
    },

    loadMcccStore() {
        const saved = localStorage.getItem("mcccStore");
        if (saved) {
            const data = JSON.parse(saved);
            // On fait confiance au disque. Si le backup y est, on le prend.
            Object.assign(this, data);
        }
    },

    saveBackup() {
        // 1. Si j'ai déjà un backup en mémoire, JE M'ARRÊTE.
        // (La longueur > 10 évite de considérer "null" ou "{}" comme valide)
        if (this.backup && this.backup.length > 10) {
            return;
        }

        // 2. Sinon, je crée la photo V0
        const dataToSave = {
            hoursCM: this.hoursCM,
            hoursTD: this.hoursTD,
            hoursTP: this.hoursTP,
            hoursDS: this.hoursDS,
            hoursDSTP: this.hoursDSTP,
            acsGrouped: JSON.parse(JSON.stringify(this.acsGrouped)),
            saeCodes: JSON.parse(JSON.stringify(this.saeCodes)),
            referents: JSON.parse(JSON.stringify(this.referents)),
            resourceCode: this.resourceCode
        };

        this.backup = JSON.stringify(dataToSave);

        // 3. Je grave ça sur le disque immédiatement
        this.registerMcccStore();
        console.log("🔒 Backup V0 créé et sauvegardé.");
    },

    restoreBackup() {
        if (!this.backup) return;

        try {
            const oldData = JSON.parse(this.backup);

            // Restauration des valeurs
            this.hoursCM = oldData.hoursCM;
            this.hoursTD = oldData.hoursTD;
            this.hoursTP = oldData.hoursTP;
            this.hoursDS = oldData.hoursDS;
            this.hoursDSTP = oldData.hoursDSTP;
            this.acsGrouped = oldData.acsGrouped || [];
            this.saeCodes = oldData.saeCodes || [];
            this.referents = oldData.referents || [];
            this.resourceCode = oldData.resourceCode;

            // Une fois restauré, on détruit la preuve pour repartir à zéro
            this.backup = null;

            // On sauvegarde l'état "propre" sur le disque
            this.registerMcccStore();
            console.log("✅ Restauration effectuée.");
        } catch (e) {
            console.error("Erreur Restauration:", e);
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
        this.saeCodes = [];
        this.acsGrouped = [];
        this.referents = [];
        this.backup = null;
        localStorage.removeItem("mcccStore");
    }
});