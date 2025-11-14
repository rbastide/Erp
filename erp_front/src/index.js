import { createRouter, createWebHistory } from 'vue-router';
import LoginPage from './components/LoginPage.vue';
import RessourcePage from './components/RessourcePage.vue';
import HomePage from './components/HomePage.vue';
import HistoryPage from './components/HistoryPage.vue';
import DashboardAdminPage from './components/DashboardAdminPage.vue';
import CreateNewUserPagePage from './components/CreateNewUserPage.vue';
import ServicesPage from './components/ServiceAidePage.vue';
import DeconnexionPage from './components/DeconnexionPage.vue';
import ErrorSave from "./components/ErrorSave.vue";
import SuccessfullySaved from "./components/SuccessfullySaved.vue";
import FillRessourcePage from "./components/FillRessourcePage.vue";
import UserSavePage from "./components/UserSavePage.vue";
import CancelPage from "./components/CancelPage.vue";

const routes = [
    {
        path: '/',
        name: 'Login',
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
        path: '/dashboard-admin',
        name: 'DashboardAdmin',
        component: DashboardAdminPage
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
        name: 'SucessfullySavedPage',
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
    }
];

const router = createRouter({
    history: createWebHistory(),
    routes
});

export default router;