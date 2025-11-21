import { createRouter, createWebHistory } from 'vue-router';
import LoginPage from './components/LoginPage.vue';
import RessourcePage from './components/RessourcePage.vue';
import HomePage from './components/HomePage.vue';
import HistoryPage from './components/HistoryPage.vue';
import HomeAdminPage from './components/HomeAdminPage.vue';
import CreateNewUserPagePage from './components/CreateNewUserPage.vue';
import ServicesPage from './components/ServiceAidePage.vue';
import DeconnexionPage from './components/DeconnexionPage.vue';
import ErrorSave from "./components/ErrorSave.vue";
import SuccessfullySaved from "./components/SuccessfullySaved.vue";
import FillRessourcePage from "./components/FillRessourcePage.vue";
import UserSavePage from "./components/UserSavePage.vue";
import CancelPage from "./components/CancelPage.vue";
import McccMenuPage from "./components/McccMenuPage.vue";
import HistoryAdminPage from "./components/HistoryAdminPage.vue";
import SuccessfullyDeletePage from "./components/SuccessfullyDelete.vue";
import McccRessourcePage from "./components/McccRessourcePage.vue";
import McccFillHourPage from "./components/McccFillHour.vue";

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
        component: CreateNewUserPagePage
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
        path: '/filRessourcePage',
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
        path: '/mccc-hour',
        name: 'McccFillHourPage',
        component: McccFillHourPage
    }
];

const router = createRouter({
    history: createWebHistory(),
    routes
});

export default router;