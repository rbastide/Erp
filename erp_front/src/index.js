import { createRouter, createWebHistory } from 'vue-router';
import LoginPage from './components/App/LoginPage.vue';
import RessourcePage from './components/RessourcePage/RessourcePage.vue';
import HomePage from './components/User/HomePage.vue';
import HistoryPage from './components/User/HistoryPage.vue';
import HomeAdminPage from './components/Admin/HomeAdminPage.vue';
import CreateNewUserPage from './components/Admin/CreateNewUserPage.vue';
import ServicesPage from './components/App/ServiceAidePage.vue';
import DeconnexionPage from './components/App/DeconnexionPage.vue';
import ErrorSave from "./components/Information/ErrorSave.vue";
import SuccessfullySaved from "./components/Information/SuccessfullySaved.vue";
import FillRessourcePage from "./components/RessourcePage/FillRessourcePage.vue";
import UserSavePage from "./components/Information/UserSavePage.vue";
import CancelPage from "./components/Information/CancelPage.vue";
import HistoryAdminPage from "./components/Admin/HistoryAdminPage.vue";
import SuccessfullyDeletePage from "./components/Information/SuccessfullyDelete.vue";
import McccRessourcePage from "./components/Mccc/McccRessourcePage.vue";
import McccFillHourPage from "./components/Mccc/McccFillHour.vue";
import ModifSaved from "./components/Information/ModifSaved.vue";
import McccMenuPage from "./components/Mccc/McccMenuPage.vue";
import McccTeachersPage from "./components/Mccc/McccTeachersPage.vue";
import McccSAE from "./components/Mccc/McccSAE.vue";
import McccCompetences from "./components/Mccc/McccCompetences.vue";
import McccSummaryPage from "./components/Mccc/McccSummary.vue";
import CompetencesCreatingPage from "./components/Admin/CompetencesCreatingPage.vue";
import RessourceSheetHistory from "./components/RessourcePage/RessourceSheetHistory.vue";
import RessourcesGestion from "./components/RessourcePage/RessourcesGestion.vue";
import UsersGestion from "./components/Mccc/UsersGestion.vue";


const routes = [
    {
        path: '/',
        name: 'Default',
        component: LoginPage // La page par défaut
    },
    {
        path: '/home',
        name: 'Home',
        component: HomePage
    },
    {
        path: '/ressource',
        name: 'Ressource',
        component: RessourcePage
    },
    {
        path:'/history',
        name: 'History',
        component: HistoryPage
    },
    {
        path: '/login',
        name: 'Login',
        component: LoginPage
    },
    {
        path: '/home-admin',
        name: 'HomeAdmin',
        component: HomeAdminPage
    },
    {
        path: '/new-user',
        name: 'CreateNewUserPage',
        component: CreateNewUserPage
    },
    {
        path: '/aide',
        name: 'ServicePage',
        component: ServicesPage
    },
    {
        path: '/deconnexion',
        name: 'DeconnexionPage',
        component: DeconnexionPage
    },
    {
        path: '/errorSave',
        name: 'ErrorSavePage',
        component: ErrorSave
    },
    {
        path: '/successfullySaved',
        name: 'SuccessfullySavedPage',
        component: SuccessfullySaved
    },
    {
        path: '/fillRessourcePage',
        name: 'FilRessourcePage',
        component: FillRessourcePage
    },
    {
        path: '/userSave',
        name: 'UserSavePage',
        component: UserSavePage
    },
    {
        path: '/cancel',
        name: 'CancelPage',
        component: CancelPage
    },
    {
        path: '/mccc-menu',
        name: 'McccMenuPage',
        component: McccMenuPage
    },
    {
        path: '/history-admin',
        name: 'HistoryAdminPage',
        component: HistoryAdminPage
    },
    {
        path: '/supp-fiche',
        name: 'SuccessfullyDeletePage',
        component: SuccessfullyDeletePage
    },
    {
        path: '/mccc-ressource',
        name: 'McccRessourcePage',
        component: McccRessourcePage
    },
    {
        path: '/mccc-hours',
        name: 'McccFillHourPage',
        component: McccFillHourPage
    },
    {
        path: '/modif-saved',
        name: 'ModifSaved',
        component: ModifSaved
    },
    {
        path: '/mccc-teachers',
        name: 'McccTeachersPage',
        component: McccTeachersPage
    },
    {
        path: '/mccc-sae',
        name: 'McccSaePage',
        component: McccSAE
    },
    {
        path: '/mccc-competences',
        name: 'McccCompetencesPage',
        component: McccCompetences
    },
    {
        path: '/mccc-summary',
        name: 'McccSummaryPage',
        component: McccSummaryPage
    },
    {
        path: '/competence-creating',
        name: 'CompetencesCreatingPage',
        component: CompetencesCreatingPage
    },
    {
        path: '/ressource-sheet-history',
        name: 'RessourceSheetHistory',
        component: RessourceSheetHistory
    },
    {
        path: '/resources-gestion',
        name: 'RessourcesGestion',
        component: RessourcesGestion
    },
    {
        path: '/users-gestion',
        name: 'UsersGestion',
        component: UsersGestion
    }
];

const router = createRouter({
    history: createWebHistory(),
    routes
});

export default router;