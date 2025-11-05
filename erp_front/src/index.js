import { createRouter, createWebHistory } from 'vue-router';
import LoginPage from './components/LoginPage.vue';
import RessourcePage from './components/RessourcePage.vue';
import HomePage from './components/HomePage.vue';
import HistoryPage from './components/HistoryPage.vue';
import DashboardAdminPage from './components/DashboardAdminPage.vue';
import CreateNewUserPage from './components/CreateNewUser.vue';

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
        component: CreateNewUserPage
    }
];

const router = createRouter({
    history: createWebHistory(),
    routes
});

export default router;